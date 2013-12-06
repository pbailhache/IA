import java.util.LinkedList;


public class AStar extends Algorithme{

	public AStar(int[][] tab, int x, int y, int pos_x, int pos_y, int end_x, int end_y, Noeud n) {
		super(tab, x, y, pos_x, pos_y, end_x, end_y, n);
		// Initialise le tableau des valeurs
		super.donneValeur(end_x, end_y, 0);
	}
	
	// On lui donne le noeud ou on se trouve et il rajoute ou non les noeuds autour de lui dans la liste
			// des cases à visiter
				public LinkedList<Noeud> algo( Noeud n){
					LinkedList<Noeud> liste_deplacement_depuis_position = new LinkedList<Noeud>();
					LinkedList<Noeud> liste_deplacement_depuis_arrive = new LinkedList<Noeud>();
					LinkedList<Noeud> liste_chemin = new LinkedList<Noeud>();
					Noeud noeudCourant ;				
					if (!maListe.isEmpty()){
						noeudCourant = maListe.getFirst();
						// Je fais une boucle car il faut vérifier que l'on n'a pas deja visiter cette case
						while (this.pos_visiter[noeudCourant.y][noeudCourant.x]==1 ){
							maListe.removeFirst();
							noeudCourant = maListe.getFirst();
						}
						this.pos_visiter[noeudCourant.y][noeudCourant.x]=1;
						maListe.removeFirst();				

						// Il y a cases autour, on les tests pour savoir si elle mèene a une impasse et si on peut y accèder 
						if (!isImpasse(noeudCourant.x+1,noeudCourant.y,vision,x,y) &&  isValide(noeudCourant.x+1,noeudCourant.y)){
							maListe.add(new Noeud(noeudCourant.x+1,noeudCourant.y,tableau_valeur[noeudCourant.y][noeudCourant.x+1]+1+(noeudCourant.heuristique-tableau_valeur[noeudCourant.y][noeudCourant.x]),noeudCourant));
						}
						if (!isImpasse(noeudCourant.x-1,noeudCourant.y,vision,x,y) && isValide(noeudCourant.x-1,noeudCourant.y)){
							maListe.add(new Noeud(noeudCourant.x-1,noeudCourant.y,tableau_valeur[noeudCourant.y][noeudCourant.x-1]+1+(noeudCourant.heuristique-tableau_valeur[noeudCourant.y][noeudCourant.x]),noeudCourant));
						}
						if (!isImpasse(noeudCourant.x,noeudCourant.y+1,vision,x,y)&& isValide(noeudCourant.x,noeudCourant.y+1)){
							maListe.add(new Noeud(noeudCourant.x,noeudCourant.y+1,tableau_valeur[noeudCourant.y+1][noeudCourant.x]+1+(noeudCourant.heuristique-tableau_valeur[noeudCourant.y][noeudCourant.x]),noeudCourant));
						}
						if (!isImpasse(noeudCourant.x,noeudCourant.y-1,vision,x,y) && isValide(noeudCourant.x,noeudCourant.y-1)){
							maListe.add(new Noeud(noeudCourant.x,noeudCourant.y-1,tableau_valeur[noeudCourant.y-1][noeudCourant.x]+1+(noeudCourant.heuristique-tableau_valeur[noeudCourant.y][noeudCourant.x]),noeudCourant));
						}
						
						// On ordonne notre liste
						fileOrdonne(maListe);
						// On remplit nos liste de deplacements
						liste_deplacement_depuis_position = this.retourPere(n, liste_deplacement_depuis_position);
						liste_deplacement_depuis_arrive = this.retourPere(noeudCourant,liste_deplacement_depuis_arrive );
						// On fait la liste des deplacement à effectuer pour aller du noeud où on se trouve au neud courant
						liste_chemin = this.creationListeDeplacement(liste_deplacement_depuis_position, liste_deplacement_depuis_arrive);
						return liste_chemin;		
					}
					return liste_chemin;
				}
}
