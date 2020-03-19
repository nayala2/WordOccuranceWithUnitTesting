package presentation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.Test;

public class SortbyKeyTest1 {

	@Test
	public void test() {
		GUITextAnalyzer test = new GUITextAnalyzer();
		
	    HashMap<Integer, String> testMap = new HashMap<>();
	    
	    testMap.put(4,"four");
	    testMap.put(1,"one");
	    testMap.put(3,"three");
	    testMap.put(5,"five");
	    testMap.put(2,"two");

		TreeMap<Integer, String> output = test.sortbykey(testMap);

		assertEquals("{5=five, 4=four, 3=three, 2=two, 1=one}",output.toString());
		
	}

}
