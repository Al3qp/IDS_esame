package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import klotski.Posizione;

public class PosizioneTest {

	@Test
	public void testParamConstructor() {
		Posizione p=new Posizione(2,3);
		Posizione p1=new Posizione(p);
		
		assertEquals(p.column,3);
		assertEquals(p.row,2);
		
		assertEquals(p,p1);	
	}
	
	@Test
	public void testEquals() {
		Posizione p=new Posizione(2,3);
		Posizione p1=new Posizione(2,3);
		
		Posizione p2=new Posizione(3,3);
		
		assertTrue(p.equals(p1));
		assertFalse(p.equals(p2));
	}
	
	@Test
	public void testToStringPosizione() {
		String stringa="(2,4)";
		Posizione pos=new Posizione(2,4);
		
		assertEquals(stringa,pos.toString());
	}
	
	@Test
	public void testCopyConstructor() {
		assertThrows(NullPointerException.class,() ->{ new Posizione(null);});
		
	}
}
