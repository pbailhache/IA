import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Laby extends JPanel{
	
	private int x,y,pos_x,pos_y,end_x,end_y ;
	private int[][] my_laby ;
	private JFrame my_frame ;
	private JPanel panel ;
	
	public Laby (int x, int y, int pos_x, int pos_y, int end_x, int end_y, int[][] tab, JFrame frame ){
		super();
		this.x=x;
		this.y=y;
		this.pos_x = pos_x ;
		this.pos_y = pos_y ;
		this.end_x = end_x;
		this.end_y = end_y ;
		this.my_laby = tab ;
		this.my_frame = frame ;
		
		this.setLayout(new GridLayout(this.y+1,this.x+1));
		JPanel my_panel ;
		this.affichage(my_laby);
	
	}
	
	public void affichage( int[][] tab){
		this.removeAll();
		JPanel my_panel ;
		for (int j=0; j<this.y;j++){
			for (int i =0; i<this.x;i++){
				if ( this.my_laby[j][i]==3){
					my_panel = new JPanel();
					my_panel.setBorder(new LineBorder(Color.WHITE, 1));
					my_panel.setBackground(Color.RED);
					this.add(my_panel);
				}
				else if ( this.my_laby[j][i]==4){
					my_panel = new JPanel();
					my_panel.setBorder(new LineBorder(Color.WHITE, 1));
					my_panel.setBackground(Color.GREEN);
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
