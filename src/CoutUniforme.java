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
	LinkedList<Noeud> liste_position = new LinkedList();
	LinkedList<Noeud> liste_arrive = new LinkedList();
	LinkedList<Noeud> liste_chemin = new LinkedList();

	
	public CoutUniforme ( int[][] tab,int x, int y, int pos_x, int pos_y ){
		this.laby = tab ;
		this.x=x;
		this.y=y;
		this.pos_x = pos_x ;
		this.pos_y = pos_y ;
		this.pos_visiter = new int[y][x];
		maListe.add(new Noeud(pos_x,pos_y,0,null));
		//liste_arrive.add(new Noeud(pos_x,pos_y,0,null));
		liste_position.add(new Noeud(pos_x,pos_y,0,null));
		this.CoutUni(new Noeud(pos_x,pos_y,0,null));
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
				System.out.println(x + "/" + y +" est une impasse");
				return true ;
			}
			else {
				System.out.println(x + "/" + y +" n'est pas une impasse");
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
			System.out.println("Canard ! ");
			System.out.println("d : " + d);
			System.out.println("g : " + g);
			System.out.println("h : " + h);
			System.out.println("b : " + b);
			return true ;
		}
		else {
			System.out.println("d : " + d);
			System.out.println("g : " + g);
			System.out.println("h : " + h);
			System.out.println("b : " + b);
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
	
	public LinkedList<Noeud> retourPere( Noeud n,LinkedList<Noeud> liste){
		System.out.println(liste.isEmpty());
		liste.add(new Noeud(5,3,6,null));
		if ( n.n!=null ){
			System.out.println(n.x+"/"+n.y);
			System.out.println(n.h+" et "+n.n.x);			
			liste.add(n);
			retourPere(n.n,liste);
		}
		else {	
			liste.add(n);
			return liste ;
		}
		return null ;	
	}
	
	public LinkedList<Noeud> creationListeDeplacement( LinkedList<Noeud> listePos ,LinkedList<Noeud> listeArr){
		Object[] tableau_pos = listePos.toArray();
		Object[] tableau_arr = listeArr.toArray();
		System.out.println("Tab pos : " +tableau_pos[0] );
		System.out.println("Tab arr : " +tableau_pos[1] );

		int index_arr=0, index_pos=0 ;
		for ( int i = 0 ; i < tableau_pos.length+1; i++){
			for ( int j = 0 ; j < tableau_arr.length+1; j++){
				if ( (Noeud)tableau_arr[j] == (Noeud)tableau_pos[i] ){
					index_arr = j ;
					index_pos = i ;
					System.out.println("Trouver");
					break;
				}
				System.out.println("Pas trouver ..... ************************************ ");
			}
		}
		for ( int a = 0 ; a < index_pos+1 ; a++ ){
			this.liste_chemin.add((Noeud)tableau_pos[a]);
		}
		for ( int a = index_pos+1 ; a < index_arr+1 ; a++ ){
			this.liste_chemin.add((Noeud)tableau_arr[a]);
		}
		return this.liste_position ;
	}
	
	public LinkedList<Noeud> fileOrdonne(LinkedList<Noeud> l){
		Collections.sort(l,Noeud.noeudComparator);
		return l ;
	}
	
	// On lui donne le noeud ou on se trouve
	public LinkedList<Noeud> CoutUni( Noeud n){
		Noeud noeudCourant ; 		
		if (!maListe.isEmpty()){			
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
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(this.liste_position.isEmpty());
			System.out.println(this.liste_arrive.isEmpty());

			// La liste contient tous les deplacements entre le n et le noeud courant ( donc la ou on se trouve )
			this.liste_position = this.retourPere(n, this.liste_position);
			System.out.println(this.liste_position.isEmpty());
			System.out.println(noeudCourant.h);
			System.out.println(this.liste_arrive.isEmpty());
			this.liste_arrive = this.retourPere(noeudCourant, this.liste_arrive );
			// Il faut chercher le plus près point commun !
			System.out.println(noeudCourant.h);
			if ( n.n != null ){
				System.out.println(this.liste_position.isEmpty());
				System.out.println(this.liste_arrive.isEmpty());
	
				this.liste_chemin = this.creationListeDeplacement(liste_position, liste_arrive);
				this.afficheValeurPos();
				return liste_chemin;
			}
			liste_chemin.add(noeudCourant);
		}
		//return new Noeud(0,0,0,null);
		return liste_chemin;
	}
	
	
}