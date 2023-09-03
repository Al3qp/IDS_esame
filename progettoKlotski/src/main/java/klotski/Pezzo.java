package klotski;


import javafx.scene.shape.Rectangle;

/**
 * Classe che rappresenta un pezzo.
 */
public class Pezzo extends Rectangle {
	public enum TipoPezzo {
		/** Rettangolo 2 x 1 */
		RETTANGOLO_ORIZZ,
		
		/** Rettangolo 1 x 2 */
		RETTANGOLO_VERT,
		
		/** Rettangolo 2 x 2 */
		QUADRATONE,
		
		/** Rettangolo 1 x 1 */
		QUADRATINO
	}
	
	/* ---------- ATTRIBUTI ---------- */
	/** Tipo di rettangolo. */
	private TipoPezzo tipoPezzo;
	
//	/** Reference alla griglia in cui mettere i pezzi . */
//	private GridPane grid;

	/** Indica la dimensione di 1 unità per fare il lato. */
	public static final double UNIT = 80;
	
	/** Indica quante colonne occupa il pezzo nella griglia */
	public final int SPAN_WIDTH;
	
	/** Indica quante righe occupa il pezzo nella griglia */
	public final int SPAN_HEIGHT;
	
	/** @return Tipo di pezzo */
	public TipoPezzo getTipoPezzo() {
		return tipoPezzo;
	}
	
	/**
	 * Costruttore
	 * @param tipoPezzo Tipo di pezzo da costruire
	 */
	/*
	 * @param grid Reference alla griglia in cui mettere il rettangolo
	 * @param row Coordinata riga in cui metterlo nella griglia
	 * @param column Coordinata colonna in cui metterlo nella griglia
	 */
	public Pezzo(Pezzo.TipoPezzo tipoPezzo) {
		super();
		
		/* Costruisco il rettangolo */
		this.tipoPezzo = tipoPezzo;
		switch(tipoPezzo) {
			case RETTANGOLO_ORIZZ:
				setWidth(UNIT * 2);
				setHeight(UNIT);
				getStyleClass().add("rettangolo_orizz");
				SPAN_WIDTH = 2;
				SPAN_HEIGHT = 1;
				break;
				
			case RETTANGOLO_VERT:
				setWidth(UNIT);
				setHeight(UNIT * 2);
				getStyleClass().add("rettangolo_vert");
				SPAN_WIDTH = 1;
				SPAN_HEIGHT = 2;
				break;
				
			case QUADRATONE:
				setWidth(UNIT * 2);
				setHeight(UNIT * 2);
				getStyleClass().add("quadratone");
				SPAN_WIDTH = 2;
				SPAN_HEIGHT = 2;
				break;
				
			case QUADRATINO:
				setWidth(UNIT);
				setHeight(UNIT);
				getStyleClass().add("quadratino");
				SPAN_WIDTH = 1;
				SPAN_HEIGHT = 1;
				break;
				
			default:
				throw new IllegalArgumentException("Illegal value for tipoPezzo : " + tipoPezzo);
		}
		
	}
}
