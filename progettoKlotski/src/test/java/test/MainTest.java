package test;

import klotski.Main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class MainTest{

    @Test
    public void testStartMethod() {
            	
    	assertNotNull(Main.getPrimaryStage());
    	
    	assertEquals("Klotski",Main.getPrimaryStage().getTitle());
    	
    	assertNotNull(Main.getPrimaryStage().getScene());
    }
}
