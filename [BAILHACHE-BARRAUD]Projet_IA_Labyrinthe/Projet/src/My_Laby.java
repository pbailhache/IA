import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class My_Laby extends JPanel{
	
	private int x,y ;
	private int[][] my_laby ;
	private JFrame my_frame ;
	
	// ********************************************************************************************
	// 											Constructeur
	// ********************************************************************************************
	public My_Laby (int x, int y, int[][] tab, JFrame frame ){
		super();
		this.x=x;
		this.y=y;
		this.my_laby = tab ;
		this.my_frame = frame ;
		// Passer le JPanel en gridLayout pour l'affichage des JPanel
		this.setLayout(new GridLayout(this.y+1,this.x+1));	
	}
	
	// ********************************************************************************************
	// 											getLaby
	// ********************************************************************************************
	// Retourne le labyrinthe
	public int[][] getLaby(){
		return this.my_laby ;
	}
	
	// ********************************************************************************************
	// 											Affichage
	// ********************************************************************************************
	public void affichage(){
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
}

