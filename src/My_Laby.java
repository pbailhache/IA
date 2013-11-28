import java.awt.Color;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class My_Laby extends JPanel{

	// ****************************************************************************************************
	//											Variable
	// ****************************************************************************************************
	private int pos_x,pos_y,x,y,end_x,end_y ;
	/* my_laby ca contenir un tableau remplit de integer allant de 0 a 5 :
	 * - 0 : couloir inexploré
	 * - 1 : Mur
	 * - 2 : Sortie
	 * - 3 : Position du personnage
	 * - 4 : Lieux deja exploré
	 * - 5 : Chemin optimal vers la sortie
	*/
	public int[][] my_laby,old_laby ;
	private JFrame my_frame ;
	private Image  herbe,arbre ;
	
		
		// Constructeur de la classe !
			public My_Laby (int x, int y, int pos_x, int pos_y, int end_x, int end_y, int[][] tab, JFrame frame ) {
				super();
				this.x=x;
				this.y=y;
				this.pos_x = pos_x ;
				this.pos_y = pos_y ;
				this.end_x = end_x;
				this.end_y = end_y ;
				this.my_laby = tab ;
				this.my_frame = frame ;
				this.old_laby = null;	
				this.setLayout(new GridLayout(this.y+1,this.x+1));
			}
		
		
		
		// Change le tableau labyrinthe passer en paramètre
		public void setLaby( int[][] tab){
			this.my_laby = tab ;
		}
		
		// Retourne le labyrinthe passer en paramètre
		public int[][] getLaby(){
			return this.my_laby ;
		}
		
		// Permet de remplir mon JPanel de multiple JPanel avec une couleur particulière
		// en fonction de l'indice présent sur la case du tableau.
		public void affichage( int[][] tab){
			JPanel my_panel ;
			this.removeAll();
				for (int j=0; j<this.y;j++){
					for (int i =0; i<this.x;i++){
						if ( this.my_laby[j][i]==4){
							my_panel = new JPanel();
							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
							my_panel.setBackground(Color.GREEN);
							this.add(my_panel);
						}
						else if ( this.my_laby[j][i]==3){
							my_panel = new JPanel();
							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
							my_panel.setBackground(Color.RED);
							this.add(my_panel);
						}
						
						else if ( this.my_laby[j][i]==2){
							my_panel = new JPanel();
							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
							my_panel.setBackground(Color.YELLOW);
							this.add(my_panel);
						}
						else if ( this.my_laby[j][i]==1){
							my_panel = new JPanel();
							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
							my_panel.setBackground(Color.BLACK);
							this.add(my_panel);
						}
						else if ( this.my_laby[j][i]==5){
							my_panel = new JPanel();
							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
							my_panel.setBackground(Color.BLUE);
							this.add(my_panel);
						}
						else {
							my_panel = new JPanel();
							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
							my_panel.setBackground(Color.GRAY);
							this.add(my_panel);
						}
					}
				}
			this.my_frame.pack();
		}
		
		// ************* GETTER ******************
		public int getX(){
			return this.x;
		}
		public int getY(){
			return this.y;
		}
		public int getPosX(){
			return this.pos_x;
		}
		public int getPosY(){
			return this.pos_y;
		}
		public int getEndX(){
			return this.end_x;
		}
		public int getEndY(){
			return this.end_y;
		}
		
}