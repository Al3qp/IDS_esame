package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.Scene;
import klotski.scene.ChoiceScene;
import klotski.scene.GameScene;
import klotski.scene.HomeScene;

class ChoiceSceneTest {

	@Test
	public void testGetScene() {
		Scene scena=ChoiceScene.getScene();
		assertEquals(scena.getWidth(),HomeScene.WIDTH);
		assertEquals(scena.getHeight(),HomeScene.HEIGHT);
		
		if(scena instanceof ChoiceScene) {
		}else {
			fail("scena is not instance of ChoiceScene");
		}
		assertNotNull(scena);
	}

}
