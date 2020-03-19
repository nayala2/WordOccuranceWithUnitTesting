package presentation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class GUITextAnalyzer extends Application {

	static HashMap<Integer, String> repeatCheck 
	= new HashMap<>(); 

	public static final String Column1MapKey = "A";
	public static final String Column2MapKey = "B";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(300);
		stage.setHeight(500);

		final Label label = new Label("Word Count");
		label.setFont(new Font("Arial", 20));

		TableColumn<Map, String> firstDataColumn = new TableColumn<>("Number of Occurences");
		TableColumn<Map, String> secondDataColumn = new TableColumn<>("Word");

		firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
		firstDataColumn.setMinWidth(130);
		secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
		secondDataColumn.setMinWidth(130);

		TableView tableView = new TableView<>(generateDataInMap());

		tableView.setEditable(true);
		tableView.getSelectionModel().setCellSelectionEnabled(true);
		tableView.getColumns().setAll(firstDataColumn, secondDataColumn);
		Callback<TableColumn<Map, String>, TableCell<Map, String>>
		cellFactoryForMap = new Callback<TableColumn<Map, String>,
		TableCell<Map, String>>() {
			@Override
			public TableCell call(TableColumn p) {
				return new TextFieldTableCell(new StringConverter() {
					@Override
					public String toString(Object t) {
						return t.toString();
					}
					@Override
					public Object fromString(String string) {
						return string;
					}                                    
				});
			}
		};

		firstDataColumn.setCellFactory(cellFactoryForMap);
		secondDataColumn.setCellFactory(cellFactoryForMap);

		VBox vbox = new VBox();

		Button button2 = new Button("Click to Generate Word Count of MacBeth");
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));

		vbox.getChildren().addAll(button2);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);

		stage.show();

		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				vbox.getChildren().addAll(label, tableView);

				((Group) scene.getRoot()).getChildren().addAll(vbox);
				stage.setScene(scene);

				stage.show();         
			}
		});

	}


	public TreeMap<Integer,String>  WordCount(String fileName) { 

		// read whole file as String in JDK 7
		String data = "";
		try {
			data = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] textArray = count(data);

		int count = 1;
		for (int i = 0; i < textArray.length; i++) {
			if(!(repeatCheck.containsValue(textArray[i]))) {
				for (int j = 1; j < textArray.length; j++) {
					if(textArray[i].equals(textArray[j])) {
						count++;
					}

				}
			}

			String newText = textArray[i].toString();
			repeatCheck.put(count, newText);
			count = 0;
		}

		return sortbykey(repeatCheck);
	}

	/**Return an array of the words in a text file 
	 * removes all punctuation and split by spaces
	 * @param textFile, file of text separated by whitespace
	 * @return array of words in textFile
	 */
	public static String[] count(String textFile){
		if(textFile == null || textFile.isEmpty()){
			return null;
		}

		String[] words = textFile.replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+");
		return words;
	}


	// Function to sort map by Key 
	public static TreeMap<Integer,String> sortbykey(HashMap<Integer, String> unsortedMap) { 
		// TreeMap to store values of HashMap 
		TreeMap<Integer, String> sorted = new TreeMap<>(Collections.reverseOrder()); 

		// Copy all data from hashMap into TreeMap 
		sorted.putAll(unsortedMap); 
		sorted.remove(0);
		return sorted;        
	} 

	private ObservableList<Map> generateDataInMap() {


		TreeMap<Integer,String> myTreeMap = WordCount("macbeth.txt");
		ObservableList<Map> allData = FXCollections.observableArrayList();

		for (Entry<Integer, String> entry : myTreeMap.entrySet())  {           
			Map<String, String> dataRow = new HashMap<>();

			String value1 = entry.getKey().toString();
			String value2 = entry.getValue();

			dataRow.put(Column1MapKey, value1);
			dataRow.put(Column2MapKey, value2);

			allData.add(dataRow);

			System.out.println(allData);

		}

		return allData;

	}   
}
