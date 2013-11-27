import java.awt.Color;
import java.awt.List;
import java.util.Collections;
import java.util.LinkedList;


public class CoutUniforme {

	private int[][] laby ;
	private int vision=1;
	private int[][] pos_visiter ;
	private int pos_x, pos_y ,x,y;
	LinkedList<Noeud> maListe = new LinkedList() ;
	
	LinkedList<Noeud> liste_chemin = new LinkedList();

	
	public CoutUniforme ( int[][] tab,int x, int y, int pos_x, int pos_y, Noeud n){
		this.laby = tab ;
		this.x=x;
		this.y=y;
		this.pos_x = pos_x ;
		this.pos_y = pos_y ;
		this.pos_visiter = new int[y][x];
		maListe.add(n);
		this.initTabPos();
	}
	
	public void initTabPos(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				this.pos_visiter[j][i]=0;			
			}
		}		
	}
	
	public void afficheValeurPos(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				System.out.print(this.pos_visiter[j][i] +" | ");			
			}
			System.out.println();
		}
	}
	
	public boolean testBut(Noeud n){
		if (laby[n.y][n.x]==2){
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public boolean isImpasse( int x, int y, int vision){
		//boolean g=true,d=true,h=true,b = true ;
		boolean g=false,d=false,h=false,b=false ;
		
		if ( laby[y][x] == 2 ){
			return false ;
		}
		else if ( vision == 0 ){

			
		   if ( ( (laby[y][x+1]==1  ) && (laby[y][x-1]==1 ) && (laby[y-1][x]==1  ))  
				|| ((laby[y][x-1]==1 ) && (laby[y-1][x]==1 ) && (laby[y+1][x]==1  ))
				|| ((laby[y][x+1]==1 ) && (laby[y][x-1]==1 ) && (laby[y+1][x]==1  ))
				|| ((laby[y][x+1]==1 ) && (laby[y-1][x]==1 ) && (laby[y+1][x]==1  ))
					)			
			{
				return true ;
			}
			else {
				return false ;
			}
			
		}
		
		else {
			if ( isValideVision(x+1, y)){
				d=isImpasse(x+1,y , vision-1);
			}
			else {
				d=true;
			}

			if ( isValideVision(x-1, y)){
				g=isImpasse(x-1,y , vision-1);
			}
			else {
				g=true;
			}

			
			if ( isValideVision(x, y+1)){
				b=isImpasse(x,y+1 , vision-1);
			}
			else {
				b=true;
			}

			if ( isValideVision(x, y-1)){
				h=isImpasse(x,y-1, vision-1);
			}
			else {
				h=true;
			}

		}
		
		if ( (g && d && h) || ( g && d && b) || ( h && b && g) || (h && b && d)){
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public boolean isValideVision(int x, int y){
		if ( (laby[y][x]!=1)){
				return true ;
			}
			else {
				return false;
			}
	}
	
	public boolean isValide(int x, int y){
		if ( (laby[y][x]!=1) && (this.pos_visiter[y][x] == 0)){
			return true ;
		}
		else {
			return false;
		}
	}
	
	public LinkedList<Noeud> retourAuNoeudCommun( Noeud n,LinkedList<Noeud> liste, Noeud nfin){
		System.out.println("retourAuNoeudCommun");
		if (  n.n!=null  && n!=nfin ){	
				liste.add(n);
				System.out.println(n);
				return retourAuNoeudCommun(n.n,liste, nfin);
		}
		liste.add(n);
		return liste ;
		
	}
	
	public LinkedList<Noeud> retourPere( Noeud n,LinkedList<Noeud> liste){
		System.out.println("retourPere");
		if ( n.n!=null ){	
			liste.add(n);
			System.out.println(n);
			return retourPere(n.n,liste);
		}
		System.out.println(n);
		liste.add(n);
		return liste ;
		
	
	}
	
	public LinkedList<Noeud> creationListeDeplacement( LinkedList<Noeud> listePos ,LinkedList<Noeud> listeArr){
		System.out.println(listePos.isEmpty());
		System.out.println(listeArr.isEmpty());

		System.out.println("creationListeDeplacement");
		Object[] tableau_pos = listePos.toArray();
		Object[] tableau_arr = listeArr.toArray();
		for ( int i = 0 ; i < tableau_pos.length; i++){
			System.out.println("Tableau pos " + i +" : "+tableau_pos[i]);
		}
		System.out.println("Arr");
		for ( int i = 0 ; i < tableau_arr.length; i++){
			System.out.println("Tableau arr " + i +" : "+tableau_arr[i]);
		}

		int index_arr=0, index_pos=0 ;
		boolean trouver = false ;
		Noeud n = new Noeud(0,0,0,null) ;
		//for ( int i = tableau_pos.length-1 ; i >= 0 ; i--){
		for ( int i = 0 ; i < tableau_pos.length; i++){
			if ( !trouver){
				for ( int j = 0 ; j < tableau_arr.length; j++){
				//for ( int j = tableau_arr.length-1 ; j >= 0 ; j--){
					
					if ( (Noeud)tableau_arr[j] == (Noeud)tableau_pos[i] ){
						index_arr = j ;
						index_pos = i ;
						
						n = (Noeud)tableau_arr[j];
						System.out.println("Index Trouver : " + n.y +"/" +n.x );
						System.out.println("Trouver");
						trouver = true ;
						break;
					}
				}
			}else{
				break;
			}
				
		}
		LinkedList<Noeud> liste_dep_mid = new LinkedList<Noeud>();
		liste_dep_mid =	this.retourAuNoeudCommun((Noeud)tableau_pos[0], liste_dep_mid,n);
		LinkedList<Noeud> liste_mid_arr = new LinkedList<Noeud>();
		System.out.println("Index Trouver : " + n.y +"/" +n.x );

		liste_mid_arr =	this.retourAuNoeudCommun((Noeud)tableau_arr[0], liste_mid_arr,n);

//				for ( int a = 0 ; a < index_pos+1 ; a++ ){
//					liste_dep_mid.add((Noeud)tableau_pos[a]);
//				}
//				for ( int a = index_pos+1 ; a < index_arr+1 ; a++ ){
//					liste_dep_mid.add((Noeud)tableau_arr[a]);
//				}
				
//				for ( int i = 0 ; i < liste_dep_mid.size() ; i++){
//					System.out.println("Liste dep " + i +" : "+liste_dep_mid.get(i));
//				}
//
//		
		for ( int i = liste_mid_arr.size()-2 ; i>=0 ; i--){
			liste_dep_mid.add(liste_mid_arr.get(i));
		}

		
		return liste_dep_mid ;
	}
	
	public LinkedList<Noeud> fileOrdonne(LinkedList<Noeud> l){
		Collections.sort(l,Noeud.noeudComparator);
		return l ;
	}
	
	// On lui donne le noeud ou on se trouve
	public LinkedList<Noeud> CoutUni( Noeud n){
		LinkedList<Noeud> liste_position = new LinkedList<Noeud>();
		LinkedList<Noeud> liste_arrive = new LinkedList<Noeud>();
		Noeud noeudCourant ;
		
		
		if (!maListe.isEmpty()){
//			for ( int i = 0; i<maListe.size() ; i++){
//				System.out.println("Liste  " + i + " : " + maListe.get(i) );
//			}
//			System.out.println("fgdfg");
			noeudCourant = maListe.getFirst();
			while (this.pos_visiter[noeudCourant.y][noeudCourant.x]==1 ){
				maListe.removeFirst();
				noeudCourant = maListe.getFirst();
			}
			this.pos_visiter[noeudCourant.y][noeudCourant.x]=1;
			maListe.removeFirst();
			
			if (testBut(noeudCourant)){				
				System.out.println("BOUUUHHHH");
			}
			else {

				if (!isImpasse(noeudCourant.x+1,noeudCourant.y,vision) &&  isValide(noeudCourant.x+1,noeudCourant.y)){
					System.out.println(" ************************* Ajout de " + (noeudCourant.x+1 )+"/" + noeudCourant.y);
					maListe.add(new Noeud(noeudCourant.x+1,noeudCourant.y,noeudCourant.h+1,noeudCourant));
				}
				if (!isImpasse(noeudCourant.x-1,noeudCourant.y,vision) && isValide(noeudCourant.x-1,noeudCourant.y)){
					System.out.println("*************************** Ajout de " + (noeudCourant.x-1) +"/" + noeudCourant.y);
					maListe.add(new Noeud(noeudCourant.x-1,noeudCourant.y,noeudCourant.h+1,noeudCourant));
				}
				if (!isImpasse(noeudCourant.x,noeudCourant.y+1,vision)&& isValide(noeudCourant.x,noeudCourant.y+1)){
					System.out.println(" *************************** Ajout de " + noeudCourant.x +"/" + (noeudCourant.y+1));
					maListe.add(new Noeud(noeudCourant.x,noeudCourant.y+1,noeudCourant.h+1,noeudCourant));
				}
				if (!isImpasse(noeudCourant.x,noeudCourant.y-1,vision) && isValide(noeudCourant.x,noeudCourant.y-1)){
					System.out.println(" *************************** Ajout de " + noeudCourant.x +"/" + (noeudCourant.y-1));
					maListe.add(new Noeud(noeudCourant.x,noeudCourant.y-1,noeudCourant.h+1,noeudCourant));
				}
					
			}

			fileOrdonne(maListe);
//			System.out.println(n);
//			for ( int i = 0; i<maListe.size() ; i++){
//				System.out.println("Liste  " + i + " : " + maListe.get(i) );
//			}
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// La liste contient tous les deplacements entre le n et le noeud courant ( donc la ou on se trouve )
//			// En premier la liste à partir de la postion vers le noeud initial
//			System.out.println("Liste Position");
			liste_position = this.retourPere(n, liste_position);
//			for ( int i = 0; i<liste_position.size() ; i++){
//				System.out.println("liste_position  " + i + " : " + liste_position.get(i) );
//			}
//			// En 2eme la liste à partir du noeud courant jusqu'au noeud initial
//			System.out.println("Liste Arrive");
			liste_arrive = this.retourPere(noeudCourant,liste_arrive );
//			for ( int i = 0; i<liste_arrive.size() ; i++){
//				System.out.println("liste_arrive  " + i + " : " + liste_arrive.get(i) );
//			}
//			// Il faut chercher le plus près point commun !
//			System.out.println("Neoud passer en parametre : " + n.h);
//			
			// On créais le chemin à faire pour se deplace de n a noeudcourant.
			this.liste_chemin = this.creationListeDeplacement(liste_position, liste_arrive);
			this.afficheValeurPos();
			return liste_chemin;		}
		//return new Noeud(0,0,0,null);
		return liste_chemin;
	}
	
	
}