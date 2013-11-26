import java.util.Comparator;


// Creation de la classe que l'on va mettre dans la liste
// Elle contient le noeud et son heuristique
public class Noeud { 
	public int x, y, x_end, y_end;
	public float h;
	public Noeud n ;
	
	Noeud(int a, int b, float c, Noeud n){
		this.x=a;
		this.y=b;
		this.h=c;
		this.n=n;

	}
	
	public static Comparator<Noeud> noeudComparator = new Comparator<Noeud>() {

		public  int compare(Noeud n1, Noeud n2) {
		
		Float h1 = n1.h;
		Float h2 = n2.h;
		
		//ascending order
		return h1.compareTo(h2);

		}
		
	};
	
	
	
	
	
}
