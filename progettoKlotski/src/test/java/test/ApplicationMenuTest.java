package test;

import static org.junit.jupiter.api.Assertions.*;

import javafx.scene.control.MenuBar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import klotski.Main;
import klotski.menu.ApplicationMenu;


public class ApplicationMenuTest  {
	private static MenuBar applicationMenu;
	@BeforeAll
	private static void preClass() {
		applicationMenu = ApplicationMenu.getApplicationMenu();
		
	}
	
	@Test
	public void testApplicationMenu() {
		if(applicationMenu instanceof ApplicationMenu) {
		}else {
			fail("appMenu is not instance of MenuBar");
		}
		assertNotNull(applicationMenu);
	}

}
