import java.util.Collections;
import java.util.LinkedList;


public class MeilleurDAbord {

	private int[][] laby ;
	private int[][] pos_visiter ;
	private int[][] tableau_valeur ;
	private int pos_x, pos_y ,x,y,end_x,end_y;
	LinkedList<Noeud> maListe = new LinkedList() ;

	
	public MeilleurDAbord ( int[][] tab,int x, int y, int pos_x, int pos_y, int end_x, int end_y ){
		this.laby = tab ;
		this.x=x;
		this.y=y;
		this.end_x = end_x ;
		this.end_y = end_y ;
		this.pos_x = pos_x ;
		this.pos_y = pos_y ;
		this.pos_visiter = new int[y][x];
		this.tableau_valeur = new int[y][x];
		this.initTabValeur();
		this.initTabPos();
		this.donneValeur(end_x, end_y);
		maListe.add(new Noeud(pos_x,pos_y,tableau_valeur[pos_y][pos_x]));

	}
	
	public void donneValeur(int x, int y){
//		if (x>0 && x<this.x-1 && y>0 && y<this.y-1){
//			if (this.tableau_valeur[x][y-1] == -1){
//				this.tableau_valeur[x][y-1]= (x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
//				donneValeur(x,y-1,tab);
//			}
//				
//			if (this.tableau_valeur[x-1][y] == -1){
//				this.tableau_valeur[x-1][y]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
//				donneValeur(x-1,y,tab);
//			}
//			if (this.tableau_valeur[x+1][y] == -1){
//				this.tableau_valeur[x+1][y]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
//				donneValeur(x+1,y,tab);
//			}
//			if (this.tableau_valeur[x][y+1] == -1){
//				this.tableau_valeur[x][y+1]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
//				donneValeur(x,y+1,tab);
//			}
//		}
//		else if ( )
		if ( y>0){
			if ( this.tableau_valeur[y-1][x] == -1){
				this.tableau_valeur[y-1][x]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
				donneValeur(x,y-1);
			}
		}
		
		if ( x>0){
			if (this.tableau_valeur[y][x-1] == -1){
				this.tableau_valeur[y][x-1]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
				donneValeur(x-1,y);
			}
		}
		if (x<this.x-1 ){
			if (this.tableau_valeur[y][x+1] == -1){
				this.tableau_valeur[y][x+1]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
				donneValeur(x+1,y);
			}
		}
		if ( y<this.y-1){
			if (this.tableau_valeur[y+1][x] == -1){
				this.tableau_valeur[y+1][x]=(x-end_x)*(x-end_x) + (y-end_y)*(y-end_y);
				donneValeur(x,y+1);
			}
		}
	
}
	
	public void initTabPos(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				this.pos_visiter[j][i]=0;			
			}
		}		
	}
	
	public void initTabValeur(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				this.tableau_valeur[j][i]=-1;			
			}
		}		
	}
	
	public void afficheValeur(){
		for ( int j = 0 ; j<this.y;j++){
			for ( int i = 0 ; i<this.x;i++){
				System.out.print(this.tableau_valeur[j][i] +" | ");			
			}
			System.out.println();
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
		if (laby[n.x][n.y]==2){
			return true ;
		}
		else {
			return false ;
		}
	}
	
	public boolean isValide(int x, int y){
		
		if ( (laby[x][y]!=1) && (this.pos_visiter[x][y] == 0)){
			return true ;
		}
		else {
			return false;
		}
	}
	
	public LinkedList<Noeud> fileOrdonne(LinkedList<Noeud> l){
		Collections.sort(l,Noeud.noeudComparator);
		return l ;
	}
	
	public Noeud CoutUni(){
		Noeud noeudCourant ; 		
		if (!maListe.isEmpty()){			
			noeudCourant = maListe.getFirst();
			while (this.pos_visiter[noeudCourant.x][noeudCourant.y]==1 ){
				maListe.removeFirst();
				noeudCourant = maListe.getFirst();
			}
			this.pos_visiter[noeudCourant.x][noeudCourant.y]=1;
			maListe.removeFirst();
			
			if (testBut(noeudCourant)){
				return noeudCourant ;
			}
			else {		
					
					if (isValide(noeudCourant.x,noeudCourant.y-1)){
						maListe.add(new Noeud(noeudCourant.x,noeudCourant.y-1,tableau_valeur[noeudCourant.y-1][noeudCourant.x]));
					}
					if (isValide(noeudCourant.x-1,noeudCourant.y)){
						maListe.add(new Noeud(noeudCourant.x-1,noeudCourant.y,tableau_valeur[noeudCourant.y][noeudCourant.x-1]));
					}
					if (isValide(noeudCourant.x+1,noeudCourant.y)){
						maListe.add(new Noeud(noeudCourant.x+1,noeudCourant.y,tableau_valeur[noeudCourant.y][noeudCourant.x+1]));
					}
					if (isValide(noeudCourant.x,noeudCourant.y+1)){
						maListe.add(new Noeud(noeudCourant.x,noeudCourant.y+1,tableau_valeur[noeudCourant.y+1][noeudCourant.x]));
					}
			}

			fileOrdonne(maListe);
			try {
				Thread.currentThread().sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.afficheValeurPos(); 
			return maListe.getFirst();
		}
		return new Noeud(0,0,0);
	}
	
	
}
