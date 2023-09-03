package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.scene.Scene;
import klotski.scene.GameScene;
import klotski.scene.HomeScene;

class HomeSceneTest {

	@Test
	public void testGetScene() {
		Scene scena=HomeScene.getScene();
		assertEquals(scena.getWidth(),HomeScene.WIDTH);
		assertEquals(scena.getHeight(),HomeScene.HEIGHT);
		
		if(scena instanceof HomeScene) {
		}else {
			fail("scena is not instance of HomeScene");
		}
		assertNotNull(scena);
	}

}
