package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import klotski.Mossa;
import klotski.Posizione;

public class MossaTest {

	@Test
	public void testParamConstructorAndGetters() {
		Mossa m=new Mossa(2,2,1,2);
		assertEquals(new Posizione(2,2),m.getOld());
		assertEquals(new Posizione(1,2),m.getNew());
		
		Mossa m2=new Mossa(new Posizione(2,2),new Posizione(3,2));
		assertEquals(new Posizione(2,2),m2.getOld());
		assertEquals(new Posizione(3,2),m2.getNew());
	}
	
	@Test
	void testToString() {
		Mossa m=new Mossa(2,3,3,3);
		String expected="((2,3),(3,3))";
		
		assertEquals(expected, m.toString());
	}
	

}
