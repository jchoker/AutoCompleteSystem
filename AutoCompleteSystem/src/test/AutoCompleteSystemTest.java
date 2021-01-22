package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.AutoCompleteSystem;

class AutoCompleteSystemTest {

	private AutoCompleteSystem system;
	
	@BeforeEach
	public void setUp() throws Exception {
		//Initialize system and populate it with historical data
		system = new AutoCompleteSystem(new String []{"i enjoy programming","ironman", "i enjoy learning", "island"}, new int[] {5,2,2,3});
	}
	
	@Test
	public void testInput() {
		var expect =  system.input('i'); // user types 'i', current search is "i"
		assertEquals(Arrays.asList("i enjoy programming", "island","i enjoy learning") , expect);
		
		expect =  system.input(' '); // user types ' ', current search is "i "
		assertEquals(Arrays.asList("i enjoy programming","i enjoy learning") , expect);
		
		expect =  system.input('a'); // user types 'a', current search is "i a"
		assertEquals(Arrays.asList() , expect); // no history starts with "i a"
		
		expect =  system.input('#'); // user types special character '#', save current search "i a" into history and return empty
		assertEquals(Arrays.asList() , expect);
	}
}
