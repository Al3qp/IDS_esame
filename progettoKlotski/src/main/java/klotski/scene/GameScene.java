package klotski.scene;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import klotski.Gioco;
import klotski.Pezzo;
import klotski.Posizione;
import klotski.menu.ApplicationMenu;
import klotski.nbm.NextBestMove;
/**
 * Classe che rappresenta scena di gioco
 */
public class GameScene extends Scene {
	
	private static GameScene gameScene;
	
	/** Contatore delle mosse	 */
	public static int counter;
	
	/** Label del contatore	 */
	public static Label counterLabel=new Label("Mosse: 0");
	
	private static Button nbm;
	
	private static int kappa;
	
	/**
	 * Restituisce il tipo di configurazione scelta.
	 * @return int k 
	 */
	public static int getKappa() {
		return kappa;
	}
	
	/**
	 * Incrementa di uno il contatore delle mosse e lo inserisce nel Label.
	 */
	public static void incrementCounter() {
        counter++;
        counterLabel.setText("Mosse: " + counter);
    }
	
	/**
	 * Crea la scena di gioco e la restituisce
	 * @param k è la configurazione scelta dall'utente
	 * @return Scene 
	 */
	public static Scene getScene(int k) {
		// Inizializzato il contatore a 0
		counter=0;
		counterLabel.setText("Mosse: "+counter);
		kappa=k;
		Gioco.getGioco().removeAllPieces();
		BorderPane root = new BorderPane();
		// Inserito nella scena il menù di scelta
		root.setTop(ApplicationMenu.getApplicationMenu());
		
		// Griglia per posizionare i blocchi
		GridPane pane = Gioco.getGioco().getGridPane();
		
		VBox v=new VBox();
		// Griglia e counter
		v.getChildren().addAll(pane,counterLabel);
		v.setSpacing(10);
		HBox scena=new HBox();
		// Frecce e NBM
		VBox vbox=setUpButtons();
		// Undo e Reset
		VBox undoreset=setUpUndoReset();
		
		scena.getChildren().addAll(undoreset,v,vbox);
		
		Pezzo rect_orizz; 
		Pezzo rect_orizz2; 
		Pezzo r1;
		Pezzo r2;
		Pezzo r3; 
		Pezzo r4; 
		Pezzo q1;
		Pezzo q2;
		Pezzo q3;
		Pezzo q4;
		Pezzo q;
		
		switch(k) {
		
		case 1:	
			
			// --- creo il rettangolo orizzontale e lo aggiungo
			rect_orizz = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ/*, root, 2, 1*/); Gioco.getGioco().addPezzo(rect_orizz, new Posizione(2, 1));
			// --- creo i rettangoli verticali e li aggiungo
			r1 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 0, 0*/); Gioco.getGioco().addPezzo(r1, new Posizione(0, 0));
			r2 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 0, 3*/); Gioco.getGioco().addPezzo(r2, new Posizione(0, 3));
			r3 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 2, 0*/); Gioco.getGioco().addPezzo(r3, new Posizione(2, 0));
			r4 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 2, 3*/); Gioco.getGioco().addPezzo(r4, new Posizione(2, 3));
			// --- creo i quadratini e li aggiungo
			q1 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 1*/); Gioco.getGioco().addPezzo(q1, new Posizione(3, 1));
			q2 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 2*/); Gioco.getGioco().addPezzo(q2, new Posizione(3, 2));
			q3 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 4, 0*/); Gioco.getGioco().addPezzo(q3, new Posizione(4, 0));
			q4 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 4, 3*/); Gioco.getGioco().addPezzo(q4, new Posizione(4, 3));
			// --- creo il quadratone e lo aggiungo
			q = new Pezzo(Pezzo.TipoPezzo.QUADRATONE/*, root*//*, 0, 1*/); Gioco.getGioco().addPezzo(q, new Posizione(0,1 ));
			
			break;
		case 2:
			
			// --- creo i rettangoli orizzontali e li aggiungo
			rect_orizz = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ/*, root*//*, 4, 0*/); Gioco.getGioco().addPezzo(rect_orizz, new Posizione(4, 0));
			rect_orizz2 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ/*, root*//*, 4, 2*/); Gioco.getGioco().addPezzo(rect_orizz2, new Posizione(4, 2));
			// --- creo i rettangoli verticali e li aggiungo
			r1 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 1, 0*/); Gioco.getGioco().addPezzo(r1, new Posizione(1, 0));
			r2 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 1, 3*/); Gioco.getGioco().addPezzo(r2, new Posizione(1, 3));
			r3 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 2, 1*/); Gioco.getGioco().addPezzo(r3, new Posizione(2, 1));
			// --- creo i quadratini e li aggiungo
			q1 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 0, 0*/); Gioco.getGioco().addPezzo(q1, new Posizione(0, 0));
			q2 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 0, 3*/); Gioco.getGioco().addPezzo(q2, new Posizione(0, 3));
			q3 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 0*/); Gioco.getGioco().addPezzo(q3, new Posizione(3, 0));
			q4 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 3*/); Gioco.getGioco().addPezzo(q4, new Posizione(3, 3));
			// --- creo il quadratone e lo aggiungo
			q = new Pezzo(Pezzo.TipoPezzo.QUADRATONE/*, root*//*, 0, 1*/); Gioco.getGioco().addPezzo(q, new Posizione(0, 1));
			break;
		case 3:
			
			// --- creo i rettangoli orizzontali e li aggiungo
			rect_orizz = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ/*, root*//*, 3, 1*/); Gioco.getGioco().addPezzo(rect_orizz, new Posizione(3, 1));
			rect_orizz2 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ/*, root*//*, 4, 2*/); Gioco.getGioco().addPezzo(rect_orizz2, new Posizione(4, 2));
			// --- creo i rettangoli verticali e li aggiungo
			r1 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 0, 0*/); Gioco.getGioco().addPezzo(r1, new Posizione(0, 0));
			r2 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 2, 0*/); Gioco.getGioco().addPezzo(r2, new Posizione(2, 0));
			r3 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 1, 1*/); Gioco.getGioco().addPezzo(r3, new Posizione(1, 1));
			// --- creo i quadratini e li aggiungo
			q1 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 0, 1*/); Gioco.getGioco().addPezzo(q1, new Posizione(0, 1));
			q2 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 0, 2*/); Gioco.getGioco().addPezzo(q2, new Posizione(0, 2));
			q3 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 0, 3*/); Gioco.getGioco().addPezzo(q3, new Posizione(0, 3));
			q4 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 3*/); Gioco.getGioco().addPezzo(q4, new Posizione(3, 3));
			// --- creo il quadratone e lo aggiungo
			q = new Pezzo(Pezzo.TipoPezzo.QUADRATONE/*, root*//*, 1, 2*/); Gioco.getGioco().addPezzo(q, new Posizione(1, 2));
			break;
		case 4:
			
			// --- creo il rettangolo orizzontale e lo aggiungo
			rect_orizz = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ/*, root*//*, 4, 1*/); Gioco.getGioco().addPezzo(rect_orizz, new Posizione(4, 1));
		
			// --- creo i rettangoli verticali e li aggiungo
			r1 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 0, 0*/); Gioco.getGioco().addPezzo(r1, new Posizione(0, 0));
			r2 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 0, 3*/); Gioco.getGioco().addPezzo(r2, new Posizione(0, 3));
			r3 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 2, 0*/); Gioco.getGioco().addPezzo(r3, new Posizione(2, 0));
			r4 = new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT/*, root*//*, 2, 3*/); Gioco.getGioco().addPezzo(r4, new Posizione(2, 3));
			// --- creo i quadratini e li aggiungo
			q1 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 2, 1*/); Gioco.getGioco().addPezzo(q1, new Posizione(2, 1));
			q2 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 2, 2*/); Gioco.getGioco().addPezzo(q2, new Posizione(2, 2));
			q3 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 1*/); Gioco.getGioco().addPezzo(q3, new Posizione(3, 1));
			q4 = new Pezzo(Pezzo.TipoPezzo.QUADRATINO/*, root*//*, 3, 2*/); Gioco.getGioco().addPezzo(q4, new Posizione(3, 2));
			// --- creo il quadratone e lo aggiungo
			q = new Pezzo(Pezzo.TipoPezzo.QUADRATONE/*, root*//*, 0, 1*/); Gioco.getGioco().addPezzo(q, new Posizione(0, 1));
			break;
		case 5:
			// Ripristino configurazione salvata
			configurationSaved();
			break;
		
		}
		scena.setSpacing(100);
		pane.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		v.setAlignment(Pos.CENTER);
		scena.setAlignment(Pos.CENTER);
			
		root.setCenter(scena);
		// Creo nuova scena 
		gameScene = new GameScene(root, HomeScene.WIDTH, HomeScene.HEIGHT);
		gameScene.getStylesheets().add("klotski/css/stile.css");
	
		HomeScene.k=1;
		return gameScene;
	}

	private GameScene(Parent root, double width, double height) {
		super(root, width, height);
	}
	
	// Set up dei pulsanti di Undo e Reset
	private static VBox setUpUndoReset() {
		Button undo=new Button();
		undo.setGraphic(new ImageView(new Image("klotski/images/undo.png")));
		// Specifico cosa accade se ci clicco sopra
		undo.setOnAction(event-> Gioco.getGioco().undo());
		
		Button reset=new Button();
		reset.setGraphic(new ImageView(new Image("klotski/images/reset.png")));
		// Specifico cosa accade se ci clicco sopra
		reset.setOnAction(event-> Gioco.getGioco().reset());
		
		VBox undoreset=new VBox();
		undoreset.setAlignment(Pos.CENTER_RIGHT);
		undoreset.getChildren().addAll(undo,reset);
		undoreset.setSpacing(50);
		
		return undoreset;
	}
	
	// Set up delle frecce e del NBM
	private static VBox setUpButtons() {
		Button up=new Button();
		Button down=new Button();
		Button left=new Button();
		Button right=new Button();
		nbm=new Button();
		
		up.setGraphic(new ImageView(new Image(/*"/aaa/images/su.png"*/"klotski/images/su.png")));
		down.setGraphic(new ImageView(new Image(/*"/aaa/images/giu.png"*/"klotski/images/giu.png")));
		left.setGraphic(new ImageView(new Image(/*"/aaa/images/sinistra.png"*/"klotski/images/sinistra.png")));
		right.setGraphic(new ImageView(new Image(/*"/aaa/images/destra.png"*/"klotski/images/destra.png")));
		nbm.setGraphic(new ImageView(new Image(/*"/aaa/images/NBM.png"*/"klotski/images/NBM.png")));
		
		// Specifico cosa accade se ci clicco sopra
		up.setOnAction(event-> Gioco.getGioco().moveUp());
		down.setOnAction(event-> Gioco.getGioco().moveDown());
		left.setOnAction(event-> Gioco.getGioco().moveLeft());
		right.setOnAction(event-> Gioco.getGioco().moveRight());
		nbm.setOnAction(event->NextBestMove.getNextBestMove().nextBestMove());
		
		// Posizionamento frecce e NBM
		GridPane arrows=new GridPane();
		arrows.add(up, 1, 0);
		arrows.add(left, 0, 1);
		arrows.add(right, 2, 1);
		arrows.add(down, 1, 2);
		arrows.setHgap(5);
		arrows.setVgap(5);
		
		VBox v=new VBox();
		v.setSpacing(30);
		v.getChildren().addAll(nbm,arrows);
		
		return v;
	}
	
	// Set up della configurazione salvata
	private static void configurationSaved() {
		String letter;
		int row;
		int col;
		try {
            BufferedReader reader = new BufferedReader(new FileReader("salvataggio.txt"));
            
            String line;
            Integer.parseInt(reader.readLine());
            // Leggo quante mosse sono state eseguite nella partita salvata (counter)
            counter=Integer.parseInt(reader.readLine());
            while ((line = reader.readLine()) != null) {
                // Definisco un pattern per cercare la struttura "lettera (a,b)"
                Pattern pattern = Pattern.compile("[a-zA-Z]\\s\\(\\d+,\\d+\\)");
                Matcher matcher = pattern.matcher(line);
                
                if (matcher.matches()) {
                  	letter=line.substring(0,1);
                    row=Integer.parseInt(line.substring(3,4));
                	col=Integer.parseInt(line.substring(5,6));
         
                    // Posiziono i pezzi nella griglia di gioco
                    Gioco.getGioco().addPezzo(guessPiece(letter), new Posizione(row,col) );
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	// Ogni lettera rappresenta un pezzo
	private static Pezzo guessPiece(String letter) {
		Pezzo pezzo;
		switch(letter) {
		case "Q":
			pezzo=new Pezzo(Pezzo.TipoPezzo.QUADRATONE);
			return pezzo;
		case "q":
			pezzo=new Pezzo(Pezzo.TipoPezzo.QUADRATINO);
			return pezzo;
		case "R":
			pezzo=new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_ORIZZ);
			return pezzo;
		case "r":
			pezzo=new Pezzo(Pezzo.TipoPezzo.RETTANGOLO_VERT);
			return pezzo;
		default:
			pezzo=new Pezzo(Pezzo.TipoPezzo.QUADRATINO);
			return pezzo;
		}
	}
	
	/**
	 * Disabilita il pulsante NBM:
	 */
	public static void disableNbmButton() {
		nbm.setDisable(true);
	}
	
	/**
	 * Abilita il pulsante NBM.
	 */
	public static void enableNbmButton() {
		nbm.setDisable(false);
	}
	
}