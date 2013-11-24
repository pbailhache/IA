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
		
		if ( vision == 0 ){
//			if ( (laby[y][x+1]==-1 || pos_visiter[y][x+1]==1 ) 
//					&& (laby[y][x-1]==-1 || pos_visiter[y][x-1]==1 ) 
//					&& (laby[y-1][x]==-1 || pos_visiter[y-1][x]==1 ) 
//					&& (laby[y+1][x]==-1 || pos_visiter[y+1][x+1]==1 ))
			
			System.out.println("g : "+laby[y][x-1]);
			System.out.println("d : "+laby[y][x+1]);
			System.out.println("h : "+laby[y+1][x]);
			System.out.println("b : "+laby[y-1][x]);
			
			if ( ( (laby[y][x+1]==1 || pos_visiter[y][x+1]==1 )	&& (laby[y][x-1]==1 || pos_visiter[y][x-1]==1 ) && (laby[y-1][x]==1 || pos_visiter[y-1][x]==1 ))  
				|| ((laby[y][x-1]==1 || pos_visiter[y][x-1]==1 ) && (laby[y-1][x]==1 || pos_visiter[y-1][x]==1 )	&& (laby[y+1][x]==1 || pos_visiter[y+1][x]==1 ))
				|| ((laby[y][x+1]==1 || pos_visiter[y][x+1]==1 )	&& (laby[y][x-1]==1 || pos_visiter[y][x-1]==1 ) && (laby[y+1][x]==1 || pos_visiter[y+1][x]==1 ))
				|| ((laby[y][x+1]==1 || pos_visiter[y][x+1]==1 ) && (laby[y-1][x]==1 || pos_visiter[y-1][x]==1 )	&& (laby[y+1][x]==1 || pos_visiter[y+1][x]==1 ))
					)			
			{
				System.out.println(x + "/" + y +" est une impasse");
				return true ;
			}
			else {
				System.out.println(x + "/" + y +" non une impasse");
				return false ;
			}
			
		}
		else {
			if ( isValide(x+1, y)){
				System.out.println((x+1) + "/" + y +" est valide " + laby[y][x+1] );
				d=isImpasse(x+1,y , vision-1);
			}
			else {
				d=true;
			}

			if ( isValide(x-1, y)){
				System.out.println((x-1) + "/" + y +" est valide "  + laby[y][x-1]);
				g=isImpasse(x-1,y , vision-1);
			}
			else {
				g=true;
			}

			
			if ( isValide(x, y+1)){
				System.out.println(x + "/" + (y+1) +" est valide " + laby[y+1][x]);
				b=isImpasse(x,y+1 , vision-1);
			}
			else {
				b=true;
			}

			if ( isValide(x, y-1)){
				System.out.println(x + "/" + (y-1) +" est valide " + laby[y-1][x]);
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
	
	public boolean isValide(int x, int y){
		
		if ( (laby[y][x]!=1) && (this.pos_visiter[y][x] == 0)){
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
			while (this.pos_visiter[noeudCourant.y][noeudCourant.x]==1 ){
				maListe.removeFirst();
				noeudCourant = maListe.getFirst();
			}
			this.pos_visiter[noeudCourant.y][noeudCourant.x]=1;
			maListe.removeFirst();
			
			if (testBut(noeudCourant)){
				
				maListe.clear();
				return noeudCourant ;
			}
			else {
//					if ( isValide(noeudCourant.x+1,noeudCourant.y)){
//						maListe.add(new Noeud(noeudCourant.x+1,noeudCourant.y,noeudCourant.h+1));
//					}
//					if (isValide(noeudCourant.x-1,noeudCourant.y)){
//						maListe.add(new Noeud(noeudCourant.x-1,noeudCourant.y,noeudCourant.h+1));
//					}
//					if (isValide(noeudCourant.x,noeudCourant.y+1)){
//						maListe.add(new Noeud(noeudCourant.x,noeudCourant.y+1,noeudCourant.h+1));
//					}
//					if (isValide(noeudCourant.x,noeudCourant.y-1)){
//						maListe.add(new Noeud(noeudCourant.x,noeudCourant.y-1,noeudCourant.h+1));
//					}
//				System.out.println(isImpasse(noeudCourant.x+1,noeudCourant.y,3) + "  " + noeudCourant.y + "/" +(noeudCourant.x+1) );
//				System.out.println(isImpasse(noeudCourant.x-1,noeudCourant.y,3)+ "  " + noeudCourant.y + "/" +(noeudCourant.x-1));
//				System.out.println(isImpasse(noeudCourant.x,noeudCourant.y+1,3)+ "  " + (noeudCourant.y+1) + "/" +noeudCourant.x);
//				System.out.println(isImpasse(noeudCourant.x,noeudCourant.y-1,3)+ "  " + (noeudCourant.y-1) + "/" +noeudCourant.x);

				if (!isImpasse(noeudCourant.x+1,noeudCourant.y,1) &&  isValide(noeudCourant.x+1,noeudCourant.y)){
					System.out.println(" ************************* Ajout de " + (noeudCourant.x+1 )+"/" + noeudCourant.y);
					maListe.add(new Noeud(noeudCourant.x+1,noeudCourant.y,noeudCourant.h+1));
				}
				if (!isImpasse(noeudCourant.x-1,noeudCourant.y,1) && isValide(noeudCourant.x-1,noeudCourant.y)){
					System.out.println("*************************** Ajout de " + (noeudCourant.x-1) +"/" + noeudCourant.y);
					maListe.add(new Noeud(noeudCourant.x-1,noeudCourant.y,noeudCourant.h+1));
				}
				if (!isImpasse(noeudCourant.x,noeudCourant.y+1,1)&& isValide(noeudCourant.x,noeudCourant.y+1)){
					System.out.println(" *************************** Ajout de " + noeudCourant.x +"/" + (noeudCourant.y+1));
					maListe.add(new Noeud(noeudCourant.x,noeudCourant.y+1,noeudCourant.h+1));
				}
				if (!isImpasse(noeudCourant.x,noeudCourant.y-1,1) && isValide(noeudCourant.x,noeudCourant.y-1)){
					System.out.println(" *************************** Ajout de " + noeudCourant.x +"/" + (noeudCourant.y-1));
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
