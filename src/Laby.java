import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Laby extends JPanel{
	
	private int x,y,pos_x,pos_y,end_x,end_y ;
	private int[][] old_laby;
	private int[][] my_laby ;
	private JFrame my_frame ;
	private JPanel panel ;
	private boolean start ;
	
	public Laby (int x, int y, int pos_x, int pos_y, int end_x, int end_y, int[][] tab, JFrame frame ){
		super();
		this.start = false ;
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
		//this.affichage(my_laby);
	
	}
	
	public void setLaby( int[][] tab){
		this.my_laby = tab ;
	}
	
	public int[][] getLaby(){
		return this.my_laby ;
	}
	
	public void affichage( int[][] tab){
		JPanel my_panel ;
		this.removeAll();
		if(old_laby == null)
		{
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
		}
//		else
//		{
//			for (int j=0; j<this.y;j++){
//				for (int i =0; i<this.x;i++){
//					if ( this.my_laby[j][i]!=this.old_laby[j][i])
//					{
//						//ICI ON CHERCHE LES MODIFICATIONS POUR EVITER DE TOUT REFAIRE
//						if ( this.my_laby[j][i]==4){
//							my_panel = new JPanel();
//							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
//							my_panel.setBackground(Color.GREEN);
//							this.add(my_panel);
//						}
//						else if ( this.my_laby[j][i]==3){
//							my_panel = new JPanel();
//							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
//							my_panel.setBackground(Color.RED);
//							this.add(my_panel);
//						}
//						else if ( this.my_laby[j][i]==2){
//							my_panel = new JPanel();
//							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
//							my_panel.setBackground(Color.YELLOW);
//							this.add(my_panel);
//						}
//						else if ( this.my_laby[j][i]==1){
//							my_panel = new JPanel();
//							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
//							my_panel.setBackground(Color.BLACK);
//							this.add(my_panel);
//						}
//						else if ( this.my_laby[j][i]==5){
//							my_panel = new JPanel();
//							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
//							my_panel.setBackground(Color.BLUE);
//							this.add(my_panel);
//						}
//						else {
//							my_panel = new JPanel();
//							my_panel.setBorder(new LineBorder(Color.WHITE, 1));
//							my_panel.setBackground(Color.GRAY);
//							this.add(my_panel);
//						}
//					}
//				}
//			}
//		}
		
		//old_laby = tab;
		
		this.my_frame.pack();
	}
}
