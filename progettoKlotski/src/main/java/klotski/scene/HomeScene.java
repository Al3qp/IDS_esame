
package klotski.scene;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import klotski.menu.ApplicationMenu;
/**
 * Classe che rappresenta la scena Home
 */
public class HomeScene extends Scene {
	private static HomeScene homeScene;
	
	public static final double WIDTH = 1300;
	public static final double HEIGHT = 600;
	
	public static int k;
	/**
	 * Crea la scena Home e la restituisce
	 * @return HomeScene
	 */
	public static HomeScene getScene() {
		k=0;
		if(homeScene == null) {
			BorderPane root = new BorderPane();
			
			root.setTop(ApplicationMenu.getApplicationMenu());
			// Titolo
			Text text = new Text("KLOTSKI");
			text.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 35));
			VBox vBox = new VBox(text);
			vBox.setAlignment(Pos.CENTER);
			root.setCenter(vBox);
			
			homeScene = new HomeScene(root, WIDTH, HEIGHT);
		}
		
		return homeScene;
	}

	private HomeScene(Parent root, double width, double height) {
		super(root, width, height);
	}
}
