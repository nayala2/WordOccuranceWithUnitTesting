package presentation;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class CountTest {

	@Test
	public void test() {
		GUITextAnalyzer test = new GUITextAnalyzer();

		String testString = "did, you/make. an array.";
		
		String[] testArray = test.count(testString);
	        
        assertEquals("[did, you, make, an, array]", Arrays.toString(testArray));


	}



}
