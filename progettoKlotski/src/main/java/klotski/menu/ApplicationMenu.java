package klotski.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.stage.WindowEvent;
import klotski.Gioco;
import klotski.Main;
import klotski.Mossa;
import klotski.scene.ChoiceScene;
import klotski.scene.GameScene;
import klotski.scene.HomeScene;

/**
 * Classe che rappresenta il menu a tendina.
 */
public class ApplicationMenu extends MenuBar {
	/**
	 * Restituisce istanza di ApplicationMenu.
	 * @return ApplicationMenu
	 */
	public static MenuBar getApplicationMenu() {
		/*
		 * Nota implementativa: poichè questo nodo è condiviso tra varie scene, questo metodo lo crea nuovo ogni volta che viene chiamato
		 * (altrimenti il nodo non viene mostrato dopo che è stata cambiata scena un po' di volte)
		 */
		return new ApplicationMenu();
	}
	
	private static int k;
	
	public static int getK() {
		return k;
	}
	
	private ApplicationMenu() {
		super();
		
		// --- Menù File
		Menu menuFile = new Menu("File");
		
		MenuItem home = new MenuItem("Home", new ImageView(new Image("klotski/images/home.png")));
		
		home.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Specifico cosa succede quando clicco su Home
			public void handle(ActionEvent event) {
				Main.getPrimaryStage().setScene(HomeScene.getScene());
				
				event.consume();
			}
		});
		
		MenuItem nuovaPartita = new MenuItem("Nuova partita");
		
		nuovaPartita.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		 // Specifico cosa succede quando clicco su Nuova Partita
		    public void handle(ActionEvent event) {
		        if (Gioco.getGioco().somethingSaved()) {
		        	// Avviso se esiste una partita salvata
		            Alert alert = new Alert(AlertType.CONFIRMATION);
		            alert.setTitle("Partita salvata");
		            alert.setHeaderText("Esiste una partita salvata");
		            alert.setContentText("Desideri continuare la partita o iniziarne una nuova?");

		            ButtonType si = new ButtonType("Continua");
		            ButtonType no = new ButtonType("Nuova");

		            alert.getButtonTypes().setAll(si, no);
		            
		            alert.getDialogPane().getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>(){
		            	
		            	public void handle(WindowEvent event) {
		            	System.out.println("chiusura finestra...");
		            	}
		            });
		            
		            

		            Optional<ButtonType> result = alert.showAndWait();

		            if (result.isPresent()) {
		                if (result.get().equals(si)) {
		                    try {
		                        Gioco.getGioco().listaMosse = new ArrayList<Mossa>(Gioco.getListaMosse());
		                        Main.getPrimaryStage().setScene(GameScene.getScene(5));
		                        // Imposto il counter con le mosse della partita precedente salvate sulla prima riga di salvataggio.txt
		                        BufferedReader read = new BufferedReader(new FileReader("salvataggio.txt"));
		                        String garbage = read.readLine();
		                        GameScene.counter = Integer.parseInt((read.readLine()));
		                        GameScene.counterLabel.setText("Mosse: " + GameScene.counter);
		                        read.close();
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		                } else if (result.get().equals(no)) {
		                    GameScene.counter = 0;
		                    GameScene.counterLabel.setText("Mosse: 0");
		                    Gioco.getGioco().listaMosse=new ArrayList<Mossa>();
		                    Main.getPrimaryStage().setScene(ChoiceScene.getScene());
		                    // Svuoto salvataggio
		                    try {
		                        FileWriter writer = new FileWriter("salvataggio.txt");
		                        writer.write("");
		                        writer.close();
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		                }else {
		                	System.out.println("Chiusa finestra");
		                }
		            
		            }
		            
		        } else {
		            GameScene.counter = 0;
		            GameScene.counterLabel.setText("Mosse: 0");
		            Main.getPrimaryStage().setScene(ChoiceScene.getScene());
		            event.consume();
		        }
		    }
		});
		
		MenuItem savePartita = new MenuItem("Salva partita");
		savePartita.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			// Specifico cosa succede quando clicco su Salva Partita
			public void handle(ActionEvent event) {
				if(HomeScene.k==1) {
					if(GameScene.getKappa() != 5) {
						k=GameScene.getKappa();
					}
					Gioco.getGioco().partitaSalvata();
				
					Alert save=new Alert(AlertType.INFORMATION);
					save.setTitle("Partita Salvata");
					save.setHeaderText("La partita è stata salvata correttamente.");
				
					ButtonType result=new ButtonType("OK");
					save.getButtonTypes().setAll(result);
					
					save.showAndWait();
				
					event.consume();
				}
				
			}
			
		});
		
		MenuItem esci = new MenuItem("Esci");
		// Specifico cosa succede quando clicco su Esci
		esci.setOnAction(event-> {
			event.consume();
			
			Alert alert=new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Esci");
			alert.setHeaderText("Sei sicuro di voler uscire?" );
			alert.setContentText("I progressi non salvati verranno persi");
			
			ButtonType result=alert.showAndWait().orElse(ButtonType.CANCEL);
			
			if(result==ButtonType.OK) {
				Main.getPrimaryStage().close();
			}
		});

		menuFile.getItems().addAll(home, new SeparatorMenuItem(), nuovaPartita, savePartita, new SeparatorMenuItem(), esci);
		
		// --- Menù Help
		Menu menuHelp = new Menu("Help");
		MenuItem help=new MenuItem("Help");
		help.setAccelerator(KeyCombination.valueOf("F1"));
		// Specifico cosa succede quando clicco su Help
		help.setOnAction(event->{
			
			Alert alert=new Alert(AlertType.INFORMATION);
			alert.setTitle("INFO:");
			alert.setHeaderText("Gioco del Klotski");
			alert.setContentText("Il rompicapo a blocchi Klotski è un gioco che coinvolge lo spostamento di blocchi di forme diverse su una tavola, e si conclude posizionando il blocco più grande in una posizione specifica."
					+ "\nPer vincere il quadrato rosso deve essere posizionato in basso al centro della griglia di gioco."
					+ "\nPer spostare un pezzo si utilizzano le frecce dopo averlo selezionato. Per altre informazioni consultare il manuale. ");
			alert.showAndWait();
			
			event.consume();
			
		});
		menuHelp.getItems().add(help);
		// --- Aggiungo i menù creati sopra
		getMenus().addAll(menuFile, menuHelp);
	}
}
