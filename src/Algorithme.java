import java.util.Collections;
import java.util.LinkedList;


public class Algorithme {

	public int[][] laby ;
	public int vision=2;
	public int[][] pos_visiter ;
	public int pos_x, pos_y ,x,y,end_x,end_y;
	public LinkedList<Noeud> maListe = new LinkedList<Noeud>() ;
	public int[][] tableau_valeur ;
	
	// ********************************************************************************************
	// 											Constructeur
	// ********************************************************************************************
	public Algorithme ( int[][] tab,int x, int y, int pos_x, int pos_y, int end_x, int end_y, Noeud n){
		this.laby = tab ;
		this.x=x;
		this.y=y;
		this.end_x = end_x;
		this.end_y = end_y;
		this.pos_x = pos_x ;
		this.pos_y = pos_y ;
		this.pos_visiter = new int[y][x];
		this.tableau_valeur = new int[y][x];
		maListe.add(n); // Ajout de n dans la liste
		this.initTabPos(); // Initialise le tableau des deplacement
		this.initTabValeur();
	}

	// ********************************************************************************************
	// 											DonneValeur
	// ********************************************************************************************
	// On initialse le tableau de valeur, pour faire la distance de Manahattan
	// Chaque case regarde ses cases adjacentes et relance l'algorithme si celle-ci ne sont pas des murs
	public void donneValeur(int x, int y, int valeur){	
		if ( y>0){
			if ((((this.tableau_valeur[y-1][x]> this.tableau_valeur[y][x]+1) || (this.tableau_valeur[y-1][x] == -1)))){
				this.tableau_valeur[y-1][x]=valeur;
				donneValeur(x,y-1,valeur+1);
			}
		}		
		if ( x>0){
			if ((((this.tableau_valeur[y][x-1]> this.tableau_valeur[y][x]+1) || (this.tableau_valeur[y][x-1] == -1)))){
				this.tableau_valeur[y][x-1]=valeur;
				donneValeur(x-1,y,valeur+1);
			}
		}
		if (x<this.x-1 ){
			if (( (this.tableau_valeur[y][x+1] > this.tableau_valeur[y][x]+1) || (this.tableau_valeur[y][x+1] == -1))){
				this.tableau_valeur[y][x+1]=valeur;
				donneValeur(x+1,y,valeur+1);
			}
		}
		if ( y<this.y-1){
			if ((((this.tableau_valeur[y+1][x]> this.tableau_valeur[y][x]+1) || (this.tableau_valeur[y+1][x] == -1)))){
				this.tableau_valeur[y+1][x]=valeur;
				donneValeur(x,y+1,valeur+1);
			}
		}
	}
	
	// ********************************************************************************************
	// 											Initialisation des tableau
	// ********************************************************************************************
	// Initialise les valeurs a -1
	public void initTabValeur(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				this.tableau_valeur[j][i]=-1;			
			}
		}		
	}
	
	// Initialise le tableau des Positions
	public void initTabPos(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				this.pos_visiter[j][i]=0;			
			}
		}		
	}
	
	// ********************************************************************************************
	// 											Affichage des tableaux
	// ********************************************************************************************
	// Permet droite'afficher la tableau des valeurs
	public void afficheValeurValeur(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				System.out.print(this.tableau_valeur[j][i] +" | ");			
			}
			System.out.println();
		}
	}
	
	// Permet d'afficher la tableau des Positions
	public void afficheValeurPos(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				System.out.print(this.pos_visiter[j][i] +" | ");			
			}
			System.out.println();
		}
	}
	
	// ********************************************************************************************
	// 											isImpasse
	// ********************************************************************************************
	// Permet de vérifier si un chemin mene à une impasse
	// Si c'est le cas, on n'emprunte pas ce chemin
	// C'est une simple récurrence
	public boolean isImpasse( int x, int y, int vision, int old_x, int old_y){
		boolean g=false,d=false,h=false,b=false ;
		// Si c'est l'arrivé, on retourne FALSE
		if ( (x==this.end_x && y==this.end_y)  ){
			return false ;
		}
		if (x==old_x && y==old_y){
			return false ;
		}
		else if ( vision == 0 ){
			/* On regarde si la case où on se trouve est entouré de mur :
			- Haut, Bas, Gauche
			- Haut, Bas, Droite
			- Droite, Gauche, Bas
			- Droite, Gauche, Haut
			Si c'est le cas on retourne TRUE
			 */
			if ( ( ( laby[y][x+1]==1 ) && (laby[y][x-1]==1 ) && (laby[y-1][x]==1  ))  
				|| ((laby[y][x-1]==1 ) && (laby[y-1][x]==1 ) && (laby[y+1][x]==1  ))
				|| ((laby[y][x+1]==1 ) && (laby[y][x-1]==1 ) && (laby[y+1][x]==1  ))
				|| ((laby[y][x+1]==1 ) && (laby[y-1][x]==1 ) && (laby[y+1][x]==1  ))
					)			
			{
				return true ;
			}// Sinon ce n'est pas une impasse, on retourne FALSE
			else {
				return false ;
			}
			
		}
		// Récurrence
		// Si la case n'est pas un mur, on rappel la récurrence avec cette case
		// Sinon on retourne TRUE car c'est un mur
		else {
			if ( isValideVision(x+1, y)){
				d=isImpasse(x+1,y , vision-1,x,y);
			}
			else {
				d=true;
			}

			if ( isValideVision(x-1, y)){
				g=isImpasse(x-1,y , vision-1,x,y);
			}
			else {
				g=true;
			}

			
			if ( isValideVision(x, y+1)){
				b=isImpasse(x,y+1 , vision-1,x,y);
			}
			else {
				b=true;
			}

			if ( isValideVision(x, y-1)){
				h=isImpasse(x,y-1, vision-1,x,y);
			}
			else {
				h=true;
			}
		}		
		// Fin de la récurrence, on regarde si la case est entouré de mur ou non
		if ( (g && d && h) || ( g && d && b) || ( h && b && g) || (h && b && d)){
			return true ;
			
		}
		else {
			return false ;
		}
	}
	
	// ********************************************************************************************
	// 											Valide
	// ********************************************************************************************
	// Permet de savoir si la case est un mur
		public boolean isValideVision(int x, int y){
			if ( (y > 0 && x > 0 && y < this.y && x < this.x && laby[y][x]!=1 )){
				return true ;
			}
			else {
				return false;
			}
		}
	
	// Permet de savoir si la case est un mur, et si on l'a deja visité si ce n'est pas un mur
	public boolean isValide(int x, int y){
		if ( y > 0 && x > 0 && y < this.y && x < this.x && (laby[y][x]!=1) && (this.pos_visiter[y][x] == 0)){
			return true ;
		}
		else {
			return false;
		}
	}
	
	// ********************************************************************************************
	// 											RetourNoeudCommun
	// ********************************************************************************************
	// Permet de retourner une liste de noeud allant de n jusqu'a trouver nfin
	public LinkedList<Noeud> retourAuNoeudCommun( Noeud n,LinkedList<Noeud> liste, Noeud nfin){
		if (  n.noeud_pere!=null  && n!=nfin ){	
				liste.add(n);
				return retourAuNoeudCommun(n.noeud_pere,liste, nfin);
		}
		liste.add(n);
		return liste ;
		
	}
	
	// ********************************************************************************************
	// 											RetourPere
	// ********************************************************************************************
	// Permet de retourner une liste de noeud en partant de n et en allant jusqu'au noeud initial
	public LinkedList<Noeud> retourPere( Noeud n,LinkedList<Noeud> liste){
	if ( n != null){
		if ( n.noeud_pere!=null ){	
			liste.add(n);
			return retourPere(n.noeud_pere,liste);
		}
		liste.add(n);
		return liste ;
		}
	return null ;
	}
	
	// ********************************************************************************************
	// 											creationListeDeplacement
	// ********************************************************************************************
	// Ici on a 2 listes, et à partir de celle-ci on veux en créais une troisième qui ira de notre position actuel
	// à la postion suivante
	public LinkedList<Noeud> creationListeDeplacement( LinkedList<Noeud> listePos ,LinkedList<Noeud> listeArr){
		// On transforme les liste en tableau
		Object[] tableau_pos = listePos.toArray();
		Object[] tableau_arr = listeArr.toArray();
		boolean trouver = false ; // Boolean qui nous permet de savoir quand on a trouver le même noeud
		Noeud n = new Noeud(0,0,0,null) ;
		// Liste qui contiendrons les deplacement du debut au milieu
		// et du milieu a la fin
		LinkedList<Noeud> liste_dep_mid = new LinkedList<Noeud>();
		LinkedList<Noeud> liste_mid_arr = new LinkedList<Noeud>();


		for ( int i = 0 ; i < tableau_pos.length; i++){
			if ( !trouver){
				for ( int j = 0 ; j < tableau_arr.length; j++){					
					if ( (Noeud)tableau_arr[j] == (Noeud)tableau_pos[i] ){
						n = (Noeud)tableau_arr[j];
						trouver = true ;
						break;
					}
				}
			}else{
				break;
			}
				
		}
		// On replit les listes
		liste_dep_mid =	this.retourAuNoeudCommun((Noeud)tableau_pos[0], liste_dep_mid,n);
		liste_mid_arr =	this.retourAuNoeudCommun((Noeud)tableau_arr[0], liste_mid_arr,n);
		// Il faut inverser la liste milieu a la fin
		for ( int i = liste_mid_arr.size()-2 ; i>=0 ; i--){
			liste_dep_mid.add(liste_mid_arr.get(i));
		}	
		return liste_dep_mid ;
	}
	

	// ********************************************************************************************
	// 											fileOrdonne
	// ********************************************************************************************
	// Permet de trier la liste passer en paramètre
	public LinkedList<Noeud> fileOrdonne(LinkedList<Noeud> l){
		Collections.sort(l,Noeud.noeudComparator);
		return l ;
	}
	
		
}
