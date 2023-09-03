package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import klotski.Gioco;
import klotski.Main;
import klotski.Mossa;
import klotski.Pezzo;
import klotski.Pezzo.TipoPezzo;
import klotski.Posizione;
import klotski.scene.GameScene;

public class GiocoTest{
	
	private static Gioco gioco;

/*	
	@Override
    public void start(Stage stage) throws Exception {
		//Main.main(new String [] {});
		stage.setScene(GameScene.getScene(1));
		//Main.setPrimaryStage(stage);
		stage.show();
    }
*/	 
	@BeforeEach
	public void setUp() throws Exception {
		gioco=Gioco.getGioco();
		gioco.removeAllPieces();
		
	}

	@Test
	public void testGetGioco() {
		assertNotNull(gioco);
	}
	
	@Test
	public void testGetGridPane() {
		GridPane grid=gioco.getGridPane();
		assertNotNull(grid);
	}
	
	@Test
	public void testRemoveAllPieces() {
		Posizione pos=new Posizione(2,2);
		gioco.addPezzo(new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ), pos);
		gioco.removeAllPieces();
		assertNull(gioco.getPezzoByPosizione(pos));
	}
	
	
	@Test
	public void testGetPosizionePezzo() {
		       
        // Creazione di un pezzo fittizio da aggiungere alla griglia
        Pezzo pezzo = new Pezzo(TipoPezzo.QUADRATINO);
        gioco.addPezzo(pezzo,new Posizione(2, 3)); // Aggiunta del pezzo alla posizione (2, 3) nella griglia
                
        // Chiamata al metodo da testare
        Posizione posizione = gioco.getPosizionePezzo(pezzo);
        
        // Verifica dell'output atteso
        assertEquals(2, posizione.row);
        assertEquals(3, posizione.column);
	}
	
	@Test
	public void testAddPezzo() {
		
		Pezzo pezzo=new Pezzo(TipoPezzo.QUADRATONE);
		
		gioco.addPezzo(pezzo, new Posizione(0,0));
		
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(0,0)));
		
	}

	@Test
	public void testSelectPiece() {
		gioco.removeAllPieces();
		Pezzo pezzo=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		gioco.addPezzo(pezzo, new Posizione(2,2));
		gioco.selectPiece(2, 2);
		assertTrue(pezzo.getStyleClass().contains("pezzoSelezionato"));
		
		Pezzo nuovo=new Pezzo(TipoPezzo.QUADRATINO);
		gioco.addPezzo(nuovo, new Posizione(2,3));
		gioco.selectPiece(2, 3);
		assertFalse(pezzo.getStyleClass().contains("pezzoSelezionato"));
		assertTrue(nuovo.getStyleClass().contains("pezzoSelezionato"));
			
	}
	
	@Test 
	public void testGetPezzoByPosizione() {
		Pezzo pezzo=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		
		Posizione p=new Posizione(2,2);
		
		
		gioco.addPezzo(pezzo,p );
		
		Pezzo piece=gioco.getPezzoByPosizione(p);
		
		assertEquals(pezzo,piece);
	}
	
	@Test
	public void testUndo() {
		Pezzo p=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
		gioco.addPezzo(p, new Posizione(3,0));
		
		gioco.selectPiece(3, 0);
		
		gioco.moveDown();
		
		gioco.undo();
		
		Posizione newPos=gioco.getPosizionePezzo(p);
		
		assertEquals(newPos.row,3);
		assertEquals(newPos.column,0);
	
	}
	
	
	@Test
	public void testReset() {
		gioco.listaMosse.add(new Mossa(4,0,4,1));
		GameScene.counter=1;
		
		gioco.reset();
		
		assertTrue(gioco.listaMosse.size()==0);
		assertTrue(GameScene.counter==0);
	}
	 
	@Test
	public void testMoveUp() {
		gioco.removeAllPieces();
		
		Pezzo p1=new Pezzo(TipoPezzo.QUADRATONE);
		Pezzo p2=new Pezzo(TipoPezzo.QUADRATINO);
		Pezzo p3=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
		Pezzo p4=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		
		gioco.addPezzo(p1, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveUp();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(0,1)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p2, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveUp();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(0,1)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p3, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveUp();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(0,1)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p4, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveUp();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(0,1)));
	
	}
	
	@Test
	public void testMoveDown() {
		gioco.removeAllPieces();
		
	
		Pezzo p1=new Pezzo(TipoPezzo.QUADRATONE);
		Pezzo p2=new Pezzo(TipoPezzo.QUADRATINO);
		Pezzo p3=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
		Pezzo p4=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		
		gioco.addPezzo(p1, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveDown();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(2,1)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p2, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveDown();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(2,1)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p3, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveDown();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(2,1)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p4, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveDown();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(2,1)));
	
	}
	
	@Test
	public void testMoveLeft() {
		gioco.removeAllPieces();
		
		Pezzo p1=new Pezzo(TipoPezzo.QUADRATONE);
		Pezzo p2=new Pezzo(TipoPezzo.QUADRATINO);
		Pezzo p3=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
		Pezzo p4=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		
		gioco.addPezzo(p1, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveLeft();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,0)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p2, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveLeft();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,0)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p3, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveLeft();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,0)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p4, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveLeft();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,0)));
	
	}
	
	@Test
	public void testMoveRight() {
		gioco.removeAllPieces();
		
		Pezzo p1=new Pezzo(TipoPezzo.QUADRATONE);
		Pezzo p2=new Pezzo(TipoPezzo.QUADRATINO);
		Pezzo p3=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
		Pezzo p4=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
		
		gioco.addPezzo(p1, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveRight();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,2)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p2, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveRight();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,2)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p3, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveRight();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,2)));
		
		gioco.removeAllPieces();
		//stesso procedimento per ogni pezzo
		gioco.addPezzo(p4, new Posizione(1,1));
		
		gioco.selectPiece(1, 1);
		
		gioco.moveRight();
		
		assertNull(gioco.getPezzoByPosizione(new Posizione(1,1)));
		assertNotNull(gioco.getPezzoByPosizione(new Posizione(1,2)));
	
	}
	@Test
	public void testIsEmpty() {
		gioco.removeAllPieces();
		
		gioco.addPezzo(new Pezzo(TipoPezzo.QUADRATONE), new Posizione(1,1));
		
		assertFalse(gioco.isEmpty(1,1));
		assertFalse(gioco.isEmpty(1,2));
		assertFalse(gioco.isEmpty(2,1));
		assertFalse(gioco.isEmpty(2,2));
		assertTrue(gioco.isEmpty(0, 0));
		
		gioco.removeAllPieces();
		assertTrue(gioco.isEmpty(1,1));
		
		
	}
	
	@Test
	public void testPartitaSalvata() {
		GameScene.getScene(1);
		gioco.listaMosse=new ArrayList<Mossa>();
		try {
			FileWriter writer1=new FileWriter("salvataggio.txt");
			writer1.write("");
			writer1.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		gioco.removeAllPieces();
		gioco=Gioco.getGioco();
        Pezzo p1=new Pezzo(TipoPezzo.QUADRATONE);
        Pezzo p2=new Pezzo(TipoPezzo.RETTANGOLO_ORIZZ);
        Pezzo p3=new Pezzo(TipoPezzo.QUADRATINO);
        Pezzo p4=new Pezzo(TipoPezzo.RETTANGOLO_VERT);
        
        gioco.addPezzo(p1,new Posizione(0,0));
        gioco.addPezzo(p2,new Posizione(3,2));
        gioco.addPezzo(p3,new Posizione(2,0));
        gioco.addPezzo(p4,new Posizione(0,3));

        // Chiamata al metodo partitaSalvata
        gioco.partitaSalvata();

        // Verifica che il file di salvataggio sia stato creato e contenga le informazioni corrette
        try (BufferedReader reader = new BufferedReader(new FileReader("salvataggio.txt"))) {
            String line;
            ArrayList<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            assertEquals(8, lines.size()); // Numero di righe previste nel file
            //Prima riga indica la configurazione, in questo caso non ci interessa
            assertEquals("0", lines.get(1)); // Contatore delle mosse
            assertEquals("Q (0,0)",lines.get(2)); // Informazioni sul pezzo 1
            assertEquals("R (3,2)",lines.get(3)); // Informazioni sul pezzo 2
            assertEquals("q (2,0)",lines.get(4)); // Informazioni sul pezzo 2
            assertEquals("r (0,3)",lines.get(5)); // Informazioni sul pezzo 2
            assertEquals("-",lines.get(6));
            assertEquals("-",lines.get(7));
            
            reader.close();
            FileWriter writer=new FileWriter("salvataggio.txt");
			writer.write("");
			writer.close();
        } catch (IOException e) {
            fail("Errore nella lettura del file di salvataggio");
        }
	}
	
	@Test
	public void testSomethingSaved() {
		gioco=Gioco.getGioco();
		Pezzo p=new Pezzo(TipoPezzo.QUADRATINO);
		
		gioco.addPezzo(p, new Posizione(2,2));
		
		gioco.partitaSalvata();
		
		assertTrue(gioco.somethingSaved());	
		
		gioco.removeAllPieces();
		
	}

}
