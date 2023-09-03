package klotski;

/**
 * Classe che rappresenta un mossa.
 */
public class Mossa {
	// Posizione precedente alla mossa
	private Posizione oldPos;
	
	// Posizione sucessiva alla mossa
	private Posizione newPos;
	
	/**
	 * Costruttore. 
	 * @param oldR coordinata riga pre-mossa
	 * @param oldC coordinata colonna pre-mossa
	 * @param newR coordinata riga post-mossa
	 * @param newC coordinata colonna post-mossa
	 */
	public Mossa(int oldR,int oldC,int newR,int newC) {
		oldPos=new Posizione(oldR,oldC);
		newPos=new Posizione(newR,newC);
	}
	/**
	 * Costruttore.
	 * @param oldP Posizione pre-mossa
	 * @param newP Posizione post-mossa
	 */
	public Mossa(Posizione oldP,Posizione newP){
		oldPos=oldP;
		newPos=newP;
	}
	
	/**
	 * Restituisce la posizione post-mossa
	 * @return newPos
	 */
	public Posizione getNew() {
		return newPos;
	}
	
	/**
	 * Restituisce la posizione pre-mossa
	 * @return oldPos
	 */
	public Posizione getOld() {
		return oldPos;
	}
	
	public String toString() {
		return "("+oldPos+","+newPos+")";
	}
}
