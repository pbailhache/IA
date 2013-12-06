
import java.util.Comparator;

	// Creation de la classe que l'on va mettre dans la liste
	// Elle contient le noeud et son heuristique
	public class Noeud { 
		public int x, y;
		public float heuristique;
		public Noeud noeud_pere ;
		
		Noeud(int a, int b, float c, Noeud n){
			this.x=a;
			this.y=b;
			this.heuristique=c;
			this.noeud_pere=n;

		}
		
		// Permet de comparer les heuristiques pour trier la liste
		public static Comparator<Noeud> noeudComparator = new Comparator<Noeud>() {

			public  int compare(Noeud n1, Noeud n2) {
			
			Float h1 = n1.heuristique;
			Float h2 = n2.heuristique;
			
			//ascending order
			return h1.compareTo(h2);

			}
			
		};
				
	}

