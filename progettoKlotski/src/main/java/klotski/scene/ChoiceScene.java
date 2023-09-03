package klotski.scene;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import klotski.Main;
import klotski.menu.ApplicationMenu;
/**
 * Classe che rappresenta scena di scelta di configurazione
 */
public class ChoiceScene extends Scene {
	private static ChoiceScene choiceScene;
		
	private static ArrayList<Image> imList=new ArrayList<>();
	
	/**
	 * Crea scena di scelta e la restituisce.
	 * @return Scene 
	 */
	public static Scene getScene() {
		if(choiceScene == null) {
			
			Image conf1=new Image("klotski/images/configurazione1.png");
			Image conf2=new Image("klotski/images/configurazione2.png");
			Image conf3=new Image("klotski/images/configurazione3.png");
			Image conf4=new Image("klotski/images/configurazione4.png");
			imList.add(conf1);
			imList.add(conf2);
			imList.add(conf3);
			imList.add(conf4);
			
			// Vengono disposte le immagini orizzontalmente distanti 50 tra di loro	
			HBox hbox=Configurazioni();
			hbox.setAlignment(Pos.CENTER);
			hbox.setSpacing(50);
						
			BorderPane border=new BorderPane();
			border.setTop(ApplicationMenu.getApplicationMenu());
			VBox root=new VBox();
			root.setAlignment(Pos.CENTER);
			root.getChildren().addAll(hbox);
			border.setCenter(root);
			
			choiceScene = new ChoiceScene(border,HomeScene.WIDTH,HomeScene.HEIGHT);
		}
		HomeScene.k=2;
		return choiceScene;
	}
	
	//metto k per modificare la configurazione
	private static void MouseCLick(ImageView imm, int k){
		// Specifico cosa succede quando clicco sopra ad un'immagine
		imm.setOnMouseClicked(event ->{
			Main.getPrimaryStage().setScene(GameScene.getScene(k));
		});
		
	}
	// Creo gli elissi con i titoli per ogni configurazione
	private static HBox Configurazioni() {
		int k=1;
		HBox hbox=new HBox();
		for(Image im : imList) {
			ImageView imgV=new ImageView(im);
			MouseCLick(imgV,k);
			//creo elisse sopra immagine
			Ellipse elisse=new Ellipse(60,24);
			elisse.setFill(Color.TRANSPARENT);
			elisse.setStroke(Color.BLACK);
			
			//metto la scritta
			Label label=new Label("configurazione "+k);
			k++;
			
			StackPane _title=new StackPane();
			_title.getChildren().addAll(label,elisse);
			VBox box=new VBox();
			box.setSpacing(10);
			box.getChildren().addAll(_title,imgV);
			
		
			hbox.getChildren().add(box);
		}
		return hbox;
	}

	private ChoiceScene(Parent root,double width,double height) {
		super(root,width,height);
	}
	
}
