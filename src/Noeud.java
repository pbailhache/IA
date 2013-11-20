import java.util.Comparator;


// Creation de la classe que l'on va mettre dans la liste
// Elle contient le noeud et son heuristique
public class Noeud { 
	public int x, y, h, x_end, y_end;
	
	Noeud(int a, int b, int c){
		this.x=a;
		this.y=b;
		this.h=c;

	}
	
	public static Comparator<Noeud> noeudComparator = new Comparator<Noeud>() {

		public  int compare(Noeud n1, Noeud n2) {
		
		Integer h1 = n1.h;
		Integer h2 = n2.h;
		
		//ascending order
		return h1.compareTo(h2);

		}
		
	};
	
	public static Comparator<Noeud> noeudComparator2 = new Comparator<Noeud>() {

		public  int compare(Noeud n1, Noeud n2) {
		
		Integer h1 = n1.h;
		Integer h2 = n2.h;
		
		//ascending order
		return h1.compareTo(h2);

		}
		
	};
	
	
	
}
