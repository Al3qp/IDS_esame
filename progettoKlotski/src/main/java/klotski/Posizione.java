package klotski;

/**
 * Classe che rappresenta la posizione di un pezzo nella griglia.<br>
 * La posizione del pezzo è quella della cella in alto a sinistra del pezzo.
 */
public class Posizione {
	/** Coordinata riga */
	public int row;
	
	/** Coordinata colonna */
	public int column;

	/**
	 * Costruttore.
	 * @param row
	 * @param column
	 */
	public Posizione(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Costruttore di copia
	 * @param p Posizione di cui eseguire la copia; non deve essere {@code null} .
	 * 
	 * @exception NullPointerException Se {@code p} � {@code null}
	 */
	public Posizione(Posizione p) {
		super();
		this.row = p.row; // se p è null, tentando di accedere a p.row viene lanciata NullPointerException
		this.column = p.column;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Posizione) {
			Posizione p = (Posizione) obj;
			return (this.row == p.row) && (this.column == p.column);
		}
		
		return false;
	}
	

	@Override
	public String toString() {
		return "(" + row + ","+ column + ")";
	}
}
