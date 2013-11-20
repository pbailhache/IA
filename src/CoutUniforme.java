import java.awt.Color;
import java.util.Collections;
import java.util.LinkedList;


public class CoutUniforme {

	private int[][] laby ;
	private int[][] pos_visiter ;
	private int pos_x, pos_y ,x,y;
	LinkedList<Noeud> maListe = new LinkedList() ;

	
	public CoutUniforme ( int[][] tab,int x, int y, int pos_x, int pos_y ){
		this.laby = tab ;
		this.x=x;
		this.y=y;
		this.pos_x = pos_x ;
		this.pos_y = pos_y ;
		this.pos_visiter = new int[y][x];
		maListe.add(new Noeud(pos_x,pos_y,0));
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
					if ( isValide(noeudCourant.x+1,noeudCourant.y)){
						maListe.add(new Noeud(noeudCourant.x+1,noeudCourant.y,noeudCourant.h+1));
					}
					if (isValide(noeudCourant.x-1,noeudCourant.y)){
						maListe.add(new Noeud(noeudCourant.x-1,noeudCourant.y,noeudCourant.h+1));
					}
					if (isValide(noeudCourant.x,noeudCourant.y+1)){
						maListe.add(new Noeud(noeudCourant.x,noeudCourant.y+1,noeudCourant.h+1));
					}
					if (isValide(noeudCourant.x,noeudCourant.y-1)){
						maListe.add(new Noeud(noeudCourant.x,noeudCourant.y-1,noeudCourant.h+1));
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