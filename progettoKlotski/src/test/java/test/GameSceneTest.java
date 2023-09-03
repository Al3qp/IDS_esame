package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;


import javafx.scene.Scene;
import javafx.stage.Stage;
import klotski.menu.ApplicationMenu;
import klotski.scene.GameScene;
import klotski.scene.HomeScene;

public class GameSceneTest extends ApplicationTest{
	
	@Test
	public void testIncrementCounter() {
		int counter=GameScene.counter;
		GameScene.incrementCounter();
		int newCounter=GameScene.counter;
		
		assertEquals(counter+1,newCounter);
	}
	
	@Test
	public void testGetScene() {
		Scene scena=GameScene.getScene(0);
		assertEquals(scena.getWidth(),HomeScene.WIDTH);
		assertEquals(scena.getHeight(),HomeScene.HEIGHT);
		
		if(scena instanceof GameScene) {
		}else {
			fail("scena is not instance of GameScene");
		}
		assertNotNull(scena);
	}

}
