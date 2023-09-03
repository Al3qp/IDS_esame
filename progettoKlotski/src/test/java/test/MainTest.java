package test;
import javafx.stage.Stage;
import klotski.Main;
import klotski.scene.HomeScene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;


public class MainTest{

   @BeforeAll
    public static void preClass() throws Exception{
	    Main main = new Main();
        main.start(new Stage());
    }

    @Test
    public void testStartMethod() {
            	
    	assertNotNull(Main.getPrimaryStage());
    	
    	assertEquals("Klotski",Main.getPrimaryStage().getTitle());
    	
    	assertNotNull(Main.getPrimaryStage().getScene());
    }
}
