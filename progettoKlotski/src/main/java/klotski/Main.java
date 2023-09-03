package klotski;

import javafx.application.Application;
import javafx.stage.Stage;
import klotski.scene.HomeScene;
/**
 * Classe da cui viene lanciato il programma
 */
public class Main extends Application {
	private static Stage primaryStage;
	
	public static void setPrimaryStage(Stage stage) {
		primaryStage=stage;
	}
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Main.primaryStage = primaryStage;
		
		primaryStage.setTitle("Klotski");
		
		primaryStage.setScene(HomeScene.getScene());
		
		primaryStage.show();
	
	}

	public static void main(String[] args) {
		launch(args);
	}

}
