package klotski.nbm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import klotski.Gioco;
import klotski.Pezzo;
import klotski.Posizione;
import klotski.scene.GameScene;

/**
 * Implementazione della Next Best Move.
 */
public class NextBestMove {
	/** Indica dove salvare i dati da dare in input alla pagina html che calcola la next best move . */
	public static final String FILE_TO_WRITE_INPUT = "rsc\\nbm\\dati.js";
	
	/** Indica il file html dove c'è il codice che realizza la funzione next best move (è la pagina web da caricare) . */
	public static final String WEBPAGE_NBM = "rsc\\nbm\\nbm.html";
	
	// indica se è la prima volta che viene chiamato nextBestMove()
	// questo perché webEngine.load() viene chiamato solo la 1° volta, mentre le altre viene chiamato webEngine.reload();
	private boolean isFirstTime;
	
	/* WebEngine */
	private WebEngine webEngine;
	
	private static NextBestMove nextBestMove;
	
	public static NextBestMove getNextBestMove() {
		if(nextBestMove == null) {
			nextBestMove = new NextBestMove();
		}
		
		return nextBestMove;
	}

	private NextBestMove() {
		super();
		this.webEngine = new WebEngine();
		this.isFirstTime = true;
		
		this.webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
				if(newState == State.SUCCEEDED) {
					String nbm = (String) webEngine.executeScript("document.getElementById(\"nbm\").innerHTML");
					//System.out.println(nbm);
					
					if(nbm.equals("")) {
						// non è riuscito a risolvere il gioco
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("NBM non trovata !");
						alert.setHeaderText("Il programma non è riuscito a determinare la next best move !");
						
						alert.showAndWait();
					} else {
						/*
						 * La next best move in nbm è del tipo (r,c,m) , dove:
						 * r : posizione riga del pezzo da spostare
						 * c : posizione colonna del pezzo da spostare
						 * m : Mossa da fare (0 down, 1 right, 2 up, 3 left)
						 */
						nbm = nbm.substring(1, nbm.length() -1); // tolgo le 2 parentesi davanti
						String a[] = nbm.split(",");
						
						int posRigaPezzoToMove = Integer.parseInt(a[0]);
						int posColonnaPezzoToMove = Integer.parseInt(a[1]);
						int mossa = Integer.parseInt(a[2]);
						
						Gioco.getGioco().selectPiece(posRigaPezzoToMove, posColonnaPezzoToMove);
						
						switch(mossa) {
							case 0:
								// down
								Gioco.getGioco().moveDown();
								break;
							case 1:
								Gioco.getGioco().moveRight();
								// right
								break;
							case 2:
								Gioco.getGioco().moveUp();
								// up
								break;
							case 3:
								// left
								Gioco.getGioco().moveLeft();
						}
					}
					
					if(!removeInputFile()) {
						System.out.println("Si è verificato un errore tentando di rimuovere il file \"" + FILE_TO_WRITE_INPUT + "\" !");
					}
					
					GameScene.enableNbmButton(); // riabilito il bottone della NBM
				}
			} // changed()
		});
	}
	
	public void nextBestMove() {
		GameScene.disableNbmButton(); // disabilito il bottone della NBM
		
		if(!prepareInputFile()) {
			System.out.println("Si è verificato un errore tentando di scrivere sul file \"" + FILE_TO_WRITE_INPUT + "\" i dati di input per la next best move !");
			return;
		}
		
		if(isFirstTime) {
			try {
				webEngine.load(new File(WEBPAGE_NBM).toURI().toURL().toString());
				isFirstTime = false;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			webEngine.reload();
		}
	}
	
	/**
	 * Crea il file di input per la pagina web che calcola la next best move.<br>
	 * Il file creato contiene:<br>
	 * <ul>
	 * 	<li>la configurazione attuale;</li>
	 * 	<li>la dimensione della griglia (numero di righe e numero di colonne);</li>
	 * 	<li>la posizione dove deve essere messo il quadratone per vincere.</li>
	 * </ul>
	 * 
	 * @return {@code true} se il file viene creato con successo, {@code false} altrimenti .
	 */
	private static boolean prepareInputFile() {
		String s = "var game = {";
		
		/* aggiungo i blocchi */
		s += "blocks: [\n";
		
		ObservableList<Node> nodi = Gioco.getGioco().getGridPane().getChildren();
		
		// aggiungo i pezzi, facendo in modo che il quadratone sia il primo (perché lo script per la next best move lo richiede)
		String tmp = "";
		String pezzi = "";
		for (int i = 0; i < nodi.size(); i++) {
			Pezzo p = (Pezzo) nodi.get(i);
			Posizione pos = Gioco.getGioco().getPosizionePezzo(p);
			
            tmp = "{";
            tmp += "\"shape\": [" + p.SPAN_HEIGHT + ", " + p.SPAN_WIDTH + "], \"position\": [" + pos.row + ", " + pos.column + "]";
            tmp += "},\n";
            
            if(p.getTipoPezzo().equals(Pezzo.TipoPezzo.QUADRATONE)) {
            	pezzi = tmp + pezzi; // aggiungo tmp in testa a pezzi
            } else {
            	pezzi += tmp; // aggiungo tmp in coda a pezzi
            }
		}
		s += pezzi;
		
		s += "], \n";
		
		/* Aggiungo la dimensione della griglia */
		s += "boardSize: [" + Gioco.HEIGHT_GRID + ", " + Gioco.WIDTH_GRID + "], \n";
		
		/* Aggiungo la posizione dove deve essere messo il quadratone per vincere */
		s += "escapePoint: [3, 1], \n";
		
		s += "};";
		
		/* Scrivo i dati sul file */
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(FILE_TO_WRITE_INPUT));
			writer.write(s);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Rimuove il file di input creato per la pagina web che calcola la next best move.
	 * 
	 * @return {@code true} se il file viene rimosso con successo, {@code false} altrimenti .
	 */
	private static boolean removeInputFile() {
		File toDelete = new File(FILE_TO_WRITE_INPUT);
		return toDelete.delete();
	}
}
