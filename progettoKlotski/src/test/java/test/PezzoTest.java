package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import klotski.Pezzo;
import klotski.Pezzo.TipoPezzo;

public class PezzoTest {

	@Test
	public void testParamConstructorAndGettersPezzo() {
		Pezzo p1=new Pezzo(TipoPezzo.QUADRATINO);
		Pezzo p2=new Pezzo(TipoPezzo.QUADRATONE);
		Pezzo p3=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
		Pezzo p4=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		
		assertEquals(p1.getTipoPezzo(),TipoPezzo.QUADRATINO);
		assertEquals(p2.getTipoPezzo(),TipoPezzo.QUADRATONE);
		assertEquals(p3.getTipoPezzo(),TipoPezzo.RETTANGOLO_ORIZZ);
		assertEquals(p4.getTipoPezzo(),TipoPezzo.RETTANGOLO_VERT);
		
		assertEquals(p1.SPAN_HEIGHT,1);
		assertEquals(p2.SPAN_HEIGHT,2);
		assertEquals(p3.SPAN_HEIGHT,1);
		assertEquals(p4.SPAN_HEIGHT,2);
		assertEquals(p1.SPAN_WIDTH,1);
		assertEquals(p2.SPAN_WIDTH,2);
		assertEquals(p3.SPAN_WIDTH,2);
		assertEquals(p4.SPAN_WIDTH,1);
		
	}

}
