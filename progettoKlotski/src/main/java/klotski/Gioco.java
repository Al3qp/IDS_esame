package klotski;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import klotski.menu.ApplicationMenu;
import klotski.scene.ChoiceScene;
import klotski.scene.GameScene;

public class Gioco {
	
	// Larghezza griglia
	public static final int WIDTH_GRID = 4;
	
	// Altezza griglia
	public static final int HEIGHT_GRID = 5;
	
	private static int k;
	/** Lista di mosse fatte nella partita.	 */
	public ArrayList<Mossa> listaMosse=new ArrayList<Mossa>();
	
	/** Reference alla griglia in cui mettere i pezzi . */
	private GridPane grid;
	
	/** Indica il pezzo selezionato, o {@code null} se nessun pezzo � selezionato */
	public static Pezzo selected;
	
	private static Gioco gioco;
	
	/**
	 * Crea e restituisce istanza della classe Gioco.
	 * @return Gioco
	 */
	public static Gioco getGioco() {
		if(gioco == null) {
			gioco = new Gioco();
		}
		return gioco;
	}
	
	/**
	 * Restituisce la griglia.
	 * @return GridPane
	 */
	public GridPane getGridPane() {
		return grid;
	}
	
	/**
	 * Restituisce la posizione di un pezzo inserito nella griglia
	 * @param p
	 * @return Posizione
	 */
	public Posizione getPosizionePezzo(Pezzo p) {
		int r = GridPane.getRowIndex(p);
		int c = GridPane.getColumnIndex(p);
		
		return new Posizione(r, c);
	}
	
	private Gioco() {
		super();
		this.grid = new GridPane();
		
		/* Imposto come vincolo che le prime Pezzo.HEIGHT_GRID righe della griglia grid sono alte Pezzo.UNIT px */
		for(int i = 0; i < Gioco.HEIGHT_GRID; i++)
			this.grid.getRowConstraints().add(new RowConstraints(Pezzo.UNIT, Pezzo.UNIT, Pezzo.UNIT));
				
		/* Imposto come vincolo che le prime Pezzo.WIDTH_GRID colonne della griglia grid sono larghe Pezzo.UNIT px */
		for(int i = 0; i < Gioco.WIDTH_GRID; i++)
			this.grid.getColumnConstraints().add(new ColumnConstraints(Pezzo.UNIT, Pezzo.UNIT, Pezzo.UNIT));
		
		this.grid.setVgap(0);
		this.grid.setHgap(0);
	}
	/**
	 * Rimuove tutti i pezzi dalla griglia
	 */
	public void removeAllPieces() {
		grid.getChildren().clear();
	}
	
	/**
	 * Aggiunge un pezzo alla griglia, nella posizione specificata.
	 * @param p Pezzo da aggiungere
	 * @param pos Posizione in cui aggiungerlo
	 */
	public void addPezzo(Pezzo p, Posizione pos) {		
		// aggiunge il pezzo alla griglia
		grid.add(p, pos.column, pos.row, p.SPAN_WIDTH, p.SPAN_HEIGHT);
	
		// specifica cosa fare quando viene cliccato
		p.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Posizione posPezzoToSelect=getPosizionePezzo(p);
				// se il pezzo viene cliccato, viene selezionato
				selectPiece(posPezzoToSelect.row,posPezzoToSelect.column);
				event.consume();
			}
		});
	}
	/**
	 * Seleziona il pezzo alla posizione (row,col), evidenziandone di verde il bordo.
	 * Se esiste già un pezzo selezionato, lo deseleziono.
	 * @param row
	 * @param col
	 */
	public void selectPiece(int row,int col) {
		if(selected != null) {
			// se c'� gi� un pezzo selezionato
			// lo deseleziono
			selected.getStyleClass().remove("pezzoSelezionato");
		}
		
		// non c'� nessun pezzo selezionato, oppure quello selezionato � stato deselezionato
		// seleziono il pezzo in posizione (row, col)
		Posizione pos = new Posizione(row, col);
		selected = getPezzoByPosizione(pos);
		selected.getStyleClass().add("pezzoSelezionato");
		selected.toFront();
		
	}
	/**
	 * Restituisce il pezzo nella posizione richiesta. Se è vuota, restituisce null. 
	 * @param posizione 
	 * @return Pezzo
	 */
	public Pezzo getPezzoByPosizione(Posizione posizione) {
		Pezzo toReturn = null;
		
		ObservableList<Node> nodi = grid.getChildren();
		for(int i = 0; i < nodi.size() && toReturn == null; i++) {
			Pezzo pezzo = (Pezzo) nodi.get(i);
			Posizione pos = getPosizionePezzo(pezzo);
			
			if(pos.equals(posizione)) {
				toReturn = pezzo;
			}
		}
		
		return toReturn;
	}
	
	/**
	 * Annulla l'ultima mossa eseguita.
	 */
	public void undo() {
		Posizione newPos;
		Posizione oldPos;
		if(listaMosse==null || listaMosse.size()==0) {
			// Se la lista delle mosse è vuota o null -> Alert 
			Alert fineMosse=new Alert(AlertType.WARNING);
			fineMosse.setTitle("UNDO non disponibile");
			fineMosse.setHeaderText("Sei arrivato alla configurazione iniziale");
			
			ButtonType result=new ButtonType("OK");
			fineMosse.getButtonTypes().setAll(result);
				
			fineMosse.showAndWait();
			return;
		}
		
		Mossa lastMove=listaMosse.get(listaMosse.size()-1);
		newPos=lastMove.getNew();
		oldPos=lastMove.getOld();
		selectPiece(newPos.row,newPos.column);
		// Riposiziono il pezzo nella posizione precedente
		GridPane.setConstraints(selected,oldPos.column,oldPos.row);
		// Rimuovo l'ultima mossa dalla listaMosse
		listaMosse.remove(listaMosse.size()-1);
	}
	
	/**
	 * Ripristina la configurazione iniziale scelta.
	 */
	public void reset() {
		if(listaMosse==null || listaMosse.size()==0 || GameScene.counter==0) {
			Alert fineMosse=new Alert(AlertType.WARNING);
			fineMosse.setTitle("RESET");
			fineMosse.setHeaderText("E' già la configurazione iniziale.");
			
			ButtonType result=new ButtonType("OK");
			fineMosse.getButtonTypes().setAll(result);
				
			fineMosse.showAndWait();
			return;
		}else {
			Alert funziona=new Alert(AlertType.CONFIRMATION);
			funziona.setTitle("RESET");
			funziona.setHeaderText("Si desidera ricominciare?");
			funziona.setContentText("I progressi non salvati verranno persi.");
		
			ButtonType result=funziona.showAndWait().orElse(ButtonType.CANCEL);

			if(result==ButtonType.OK) {
				// Se utente da conferma, ripristino configurazione iniziale e counter.
				GameScene.counter=0;
				GameScene.counterLabel.setText("Mosse: 0" );
				if(GameScene.getKappa()==5) {
					try {
						// Leggo quale configurazione iniziale da prima riga del file salvataggio.txt
						BufferedReader read=new BufferedReader(new FileReader("salvataggio.txt"));
						k=Integer.parseInt((read.readLine()));
						read.close();
						Main.getPrimaryStage().setScene(GameScene.getScene(k));
					}catch(IOException e) {
						e.printStackTrace();
					}
				}else {
					Main.getPrimaryStage().setScene(GameScene.getScene(GameScene.getKappa()));
				}
				// Deseleziono ultimo pezzo e crea nuova listaMosse.
				selected=null;
				listaMosse=new ArrayList<Mossa>();
			}
		}
		return;
	}
	
	/**
	 * Sposta il pezzo selezionato di una posizione verso l'alto.
	 * Se non c'è nessun pezzo selezionato ritorna silenziosamente.
	 */
	public void moveUp() {
		if(selected==null) {
			return;
		}
		int r=GridPane.getRowIndex(selected);
		int c=GridPane.getColumnIndex(selected);

		switch(selected.getTipoPezzo()) {
		
		case QUADRATONE:
			// Pu� essere spostato solo se le 2 celle libere sono adiacenti (orizzontali o verticali) e vicine al pezzo cliccato
			if(r>0 && isEmpty(r-1,c) && isEmpty(r-1,c+1)) {
				GridPane.setConstraints(selected, c, r-1);
				listaMosse.add(new Mossa(r,c,r-1,c));
				GameScene.incrementCounter();
				if((r-1)==3 && c==1) {
					win();
				}
			}
			break;
		case RETTANGOLO_ORIZZ:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e orizzontali (nella stessa riga) e vicine al pezzo cliccato
			if(r>0 && isEmpty(r-1,c) && isEmpty(r-1,c+1)) {
				GridPane.setConstraints(selected, c, r-1);
				listaMosse.add(new Mossa(r,c,r-1,c));
				GameScene.incrementCounter();
			}
			break;
		case RETTANGOLO_VERT:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e verticali (nella stessa colonna) e vicine al pezzo cliccato
			if(r>0 && isEmpty(r-1,c)) {
				GridPane.setConstraints(selected, c, r-1);
				listaMosse.add(new Mossa(r,c,r-1,c));
				GameScene.incrementCounter();
				
			}
			break;
		case QUADRATINO:
			// pu� essere spostato solo se c'� una cella libera adiacente
			if(r>0 && isEmpty(r-1,c)) {
				GridPane.setConstraints(selected, c, r-1);
				listaMosse.add(new Mossa(r,c,r-1,c));
				GameScene.incrementCounter();
				}
			break;
		}
	} 
	/**
	 * Sposta il pezzo selezionato di una posizione verso il basso.
	 * Se non c'è nessun pezzo selezionato ritorna silenziosamente.
	 */
	public void moveDown() {
		if(selected==null) {
			return;
		}
		int r=GridPane.getRowIndex(selected);
		int c=GridPane.getColumnIndex(selected);
		switch(selected.getTipoPezzo()) {
		
		case QUADRATONE:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti (orizzontali o verticali) e vicine al pezzo cliccato
			if(r<3 && isEmpty(r+2,c) && isEmpty(r+2,c+1)) {
				GridPane.setConstraints(selected, c, r+1);
				listaMosse.add(new Mossa(r,c,r+1,c));
				GameScene.incrementCounter();
				if((r+1)==3 && c==1) {
					win();
				}
			}
			break;
		case RETTANGOLO_ORIZZ:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e orizzontali (nella stessa riga) e vicine al pezzo cliccato
			if(r<4 && isEmpty(r+1,c) && isEmpty(r+1,c+1)) {
				GridPane.setConstraints(selected, c, r+1);
				listaMosse.add(new Mossa(r,c,r+1,c));
				GameScene.incrementCounter();
			}
			break;
		case RETTANGOLO_VERT:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e verticali (nella stessa colonna) e vicine al pezzo cliccato
			if(r<3 && isEmpty(r+2,c)) {
				GridPane.setConstraints(selected, c, r+1);
				listaMosse.add(new Mossa(r,c,r+1,c));
				GameScene.incrementCounter();
			}
			break;
		case QUADRATINO:
			// pu� essere spostato solo se c'� una cella libera adiacente
			if(r<4 && isEmpty(r+1,c)) {
				GridPane.setConstraints(selected, c, r+1);
				listaMosse.add(new Mossa(r,c,r+1,c));
				GameScene.incrementCounter();
			}			
			break;
		}
	} 
	
	/**
	 * Sposta il pezzo selezionato di una posizione verso sinistra.
	 * Se non c'è nessun pezzo selezionato ritorna silenziosamente.
	 */
	public void moveLeft() {
		if(selected==null) {
			return;
		}
		int r=GridPane.getRowIndex(selected);
		int c=GridPane.getColumnIndex(selected);
		
		switch(selected.getTipoPezzo()) {
		
		case QUADRATONE:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti (orizzontali o verticali) e vicine al pezzo cliccato
			if(c>0 && isEmpty(r,c-1) && isEmpty(r+1,c-1)) {
				GridPane.setConstraints(selected, c-1, r);
				listaMosse.add(new Mossa(r,c,r,c-1));
				GameScene.incrementCounter();
				if(r==3 && (c-1)==1) {
					win();
				}
			}
			break;
		case RETTANGOLO_ORIZZ:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e orizzontali (nella stessa riga) e vicine al pezzo cliccato
			if(c>0 && isEmpty(r,c-1)) {
				GridPane.setConstraints(selected, c-1, r);
				listaMosse.add(new Mossa(r,c,r,c-1));
				GameScene.incrementCounter();
			}
			break;
		case RETTANGOLO_VERT:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e verticali (nella stessa colonna) e vicine al pezzo cliccato
			if(c>0 && isEmpty(r,c-1) && isEmpty(r+1,c-1)) {
				GridPane.setConstraints(selected, c-1, r);
				listaMosse.add(new Mossa(r,c,r,c-1));
				GameScene.incrementCounter();
			}
			break;
		case QUADRATINO:
			// pu� essere spostato solo se c'� una cella libera adiacente
			if(c>0 && isEmpty(r,c-1)) {
				GridPane.setConstraints(selected, c-1, r);
				listaMosse.add(new Mossa(r,c,r,c-1));
				GameScene.incrementCounter();
			}
			break;
		}	
	} 
	
	
	/**
	 * Sposta il pezzo selezionato di una posizione verso destra.
	 * Se non c'è nessun pezzo selezionato ritorna silenziosamente.
	 */
	public void moveRight() {
		if(selected==null) {
			return;
		}
		int r=GridPane.getRowIndex(selected);
		int c=GridPane.getColumnIndex(selected);
		
		switch(selected.getTipoPezzo()) {
		
		case QUADRATONE:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti (orizzontali o verticali) e vicine al pezzo cliccato
			if(c<2 && isEmpty(r,c+2) && isEmpty(r+1,c+2)) {
				GridPane.setConstraints(selected, c+1, r);
				listaMosse.add(new Mossa(r,c,r,c+1));
				GameScene.incrementCounter();
				if(r==3 && (c+1)==1) {
					win();
				}
			}
			break;
		case RETTANGOLO_ORIZZ:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e orizzontali (nella stessa riga) e vicine al pezzo cliccato
			if(c<2 && isEmpty(r,c+2)) {
				GridPane.setConstraints(selected, c+1, r);
				listaMosse.add(new Mossa(r,c,r,c+1));
				GameScene.incrementCounter();
			}
			break;
		case RETTANGOLO_VERT:
			// pu� essere spostato solo se le 2 celle libere sono adiacenti e verticali (nella stessa colonna) e vicine al pezzo cliccato
			if(c<3 && isEmpty(r,c+1) && isEmpty(r+1,c+1)) {
				GridPane.setConstraints(selected, c+1, r);
				listaMosse.add(new Mossa(r,c,r,c+1));
				GameScene.incrementCounter();
			}
			break;
		case QUADRATINO:
			// pu� essere spostato solo se c'� una cella libera adiacente
			if(c<3 && isEmpty(r,c+1)) {
				GridPane.setConstraints(selected, c+1, r);
				listaMosse.add(new Mossa(r,c,r,c+1));
				GameScene.incrementCounter();
			}
			break;
		}
	}
	
	
	/**
	 * Controlla che la cella richiesta in posizione (row,col) sia vuota.
	 * @param row 
	 * @param col
	 * @return true se la cella è vuota, false altrimenti.
	 */
	public boolean isEmpty(int row, int col) {
	    for (Node p : grid.getChildren()) {
	        if (p instanceof Pezzo) {
	            Pezzo pezzo = (Pezzo) p;
	            int pezzoRow = GridPane.getRowIndex(p);
	            int pezzoCol = GridPane.getColumnIndex(p);
	            int endRow = pezzoRow + pezzo.SPAN_HEIGHT;
	            int endCol = pezzoCol + pezzo.SPAN_WIDTH;

	            if (row >= pezzoRow && row < endRow && col >= pezzoCol && col < endCol) {
	                return false; // La cella � occupata da un pezzo diverso
	            }
	        }
	    }
	    return true; // La cella � libera
	}
	
	
	// Chiamato se QUADRATONE è in posizione (3,1)
	private void win() {
		listaMosse=new ArrayList<Mossa>();
		Alert win=new Alert(AlertType.INFORMATION);
		win.setTitle("Vittoria");
		win.setHeaderText("HAI VINTO!!" );
		win.setContentText("Per iniziare una nuova partita premi OK.");
		ButtonType result=win.showAndWait().orElse(null);
		if(result==ButtonType.OK) {
			// Ritorna alla scena di scelta della configurazione.
			Main.getPrimaryStage().setScene(ChoiceScene.getScene());
		}
	}
	
	/**
	 * Salva la partita all'interno del file di testo salvataggio.txt, viene salvato il tipo di configurazione
	 * iniziale, il counter delle mosse, la disposizione dei blocchi e le mosse
	 * eseguite al momento del salvataggio.
	 */
	public void partitaSalvata() {
		try {
			BufferedWriter salvato=new BufferedWriter(new FileWriter("salvataggio.txt"));
			ObservableList<Node> nodi = grid.getChildren();
			// Scrive prima riga (tipo di configurazione iniziale)
			salvato.write(ApplicationMenu.getK()+"\n");
			//Scrive seconda riga (counter delle mosse)
			salvato.write(GameScene.counter+"\n");
			// Scrive la configurazione attuale TipoPezzo (row,col). 
			for(int i = 0; i < nodi.size(); i++) {
				Pezzo pezzo = (Pezzo) nodi.get(i);
				Posizione pos = getPosizionePezzo(pezzo);
				
				switch(pezzo.getTipoPezzo()) {
				
				case QUADRATONE:
					salvato.write("Q "+pos+"\n");
					break;					
				case RETTANGOLO_ORIZZ:
					salvato.write("R "+pos+"\n");
					break;					
				case RETTANGOLO_VERT:
					salvato.write("r "+pos+"\n");
					break;					
				case QUADRATINO:
					salvato.write("q "+pos+"\n");
					break;
				}
			}
			salvato.write("-"+"\n");
			// Scrive tutte le mosse fatte fino al momento del salvataggio
			for(Mossa list : listaMosse) {
				salvato.write(list.toString()+"\n");
			}
			salvato.write("-"+"\n");
			salvato.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Controlla se c'è una partita salvata.
	 * @return true se il file salvatggio.txt è pieno, false altrimenti.
	 */
	public boolean somethingSaved() {	
		File file=new File("salvataggio.txt");
		
		if(file.exists() && file.length()!=0) {
			listaMosse=new ArrayList<Mossa>();
			return true;
		}
		return false;
	}
	
	/**
	 * Crea una nuova lista delle mosse eseguite e la riempe con le mosse della partita salvata, restituisce 
	 * poi tale lista.
	 * @return ArrayList<Mossa> lista mosse partita salvata.
	 */
	public static ArrayList<Mossa> getListaMosse(){
		ArrayList<Mossa> oldListaMosse=new ArrayList<Mossa>();
		try {
			BufferedReader reader=new BufferedReader(new FileReader("salvataggio.txt"));
			String line=reader.readLine();
			while(!(line.equals("-"))) {
				line=reader.readLine();
			}
			// Riempe listaMosse con le mosse della partita salvata (oldR,oldC,newR,newC)
			Mossa mossa;
			while(!(line = reader.readLine()).equals("-")) {
				int oldR=Integer.parseInt(line.substring(2,3));
            	int oldC=Integer.parseInt(line.substring(4,5));
            	int newR=Integer.parseInt(line.substring(8,9));
            	int newC=Integer.parseInt(line.substring(10,11));
            	mossa=new Mossa(new Posizione(oldR,oldC),new Posizione(newR,newC));
            	oldListaMosse.add(mossa);
            }
            reader.close();

		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return oldListaMosse;
	}
}
