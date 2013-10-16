package meu.chess.pieces

import static org.junit.Assert.*
import static meu.chess.utils.ConvertUtil.*;

import org.junit.Test

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering
import com.google.common.primitives.Ints;

class CollectionTest {

	@Test
	public void orderArray() {
		def a = ["A1", "B2", "D4", "C3"]
		
		Collections.sort(a, byeAlphabetic)
		Collections.reverse(a)
		
		
		assert ["D4", "C3", "B2", "A1"] == a 
	}

	Ordering<String> byeAlphabetic = new Ordering<String>() {
		public int compare( a, b) {
			def columnA = getNumberOfColumnFromCordinate(a)
			def lineA = getLineFromCordinate(a)
			
			def columnB = getNumberOfColumnFromCordinate(b)
			def lineB = getLineFromCordinate(b)
			
			def partA = Integer.valueOf( String.valueOf(columnA)+String.valueOf(lineA))
			def partB = Integer.valueOf( String.valueOf(columnB)+String.valueOf(lineB))
			
			return partA - partB 
		};
	}
}
