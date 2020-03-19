package presentation;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.BeforeClass;
import org.junit.Test;

public class WordCountTest {


	@Test
	public void test() {
		GUITextAnalyzer test = new GUITextAnalyzer();
		
		String testString = "testfile2.txt";

		TreeMap<Integer, String> output = test.WordCount(testString);
		
		assertEquals("{5=five, 4=four, 3=three, 2=two, 1=one}",output.toString());
		

	}

}
