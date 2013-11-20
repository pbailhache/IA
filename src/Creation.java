import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Creation extends JFrame implements MouseListener{

	// Variable algo
	private CoutUniforme coutUni = null ;
	private MeilleurDAbord meilleur = null ;
	// Variable
	private JPanel my_panel ;
	private JButton button_next, button_fin ;
	private JComboBox menu_algo ;
	private Box box_bas, box_haut ;
	private int x,y,pos_x,pos_y,end_x,end_y;
	// Le tableau qui va contenir les 0/1/2
	private int[][] my_laby ;
	
	public Creation (){
		super("Porjet Labyrinthe");
		this.ReadFile("laby.txt");
		System.out.println(x+"/"+y);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE) ;
		this.setPreferredSize(new Dimension (600,300));
		
		
		// ****** Initialisation
		
		button_next = new JButton("Next");
		button_next.addMouseListener(this);
		button_fin = new JButton("Fin de Cycle");
		menu_algo = new JComboBox() ;
		box_bas = Box.createHorizontalBox();
		box_haut = Box.createHorizontalBox();
		
		// ********* Option
		menu_algo.setPreferredSize(new Dimension(50, 20));
		menu_algo.addItem("Option 1");
		menu_algo.addItem("Option 2");
		menu_algo.addItem("Option 3");

		box_bas.add(Box.createHorizontalGlue());
		box_bas.add(button_fin);
		box_bas.add(Box.createHorizontalGlue());
		box_bas.add(button_next);
		box_bas.add(Box.createHorizontalGlue());
		
		
		box_haut.add(Box.createRigidArea(new Dimension(5,5)));
		box_haut.add(menu_algo);
		box_haut.add(Box.createHorizontalGlue());
		
		//creationJPanel();
		creationJPanel();
		// ******** Ajout dans la frame
		this.add(box_haut,BorderLayout.NORTH);
		this.add(box_bas,BorderLayout.SOUTH);
		this.add(this.my_panel,BorderLayout.CENTER);
	}
	
	// ************************* Creation des JPanel *******************
	public void creationJPanel (){
		my_panel = new JPanel();
		my_panel.setLayout(new GridLayout(this.y+1,this.x+1));
		JPanel panel ;
		for (int j=0; j<this.y;j++){
			for (int i =0; i<this.x;i++){
				if ( this.my_laby[j][i]==3){
					panel = new JPanel();
					panel.setBorder(new LineBorder(Color.WHITE, 1));
					panel.setBackground(Color.RED);
					my_panel.add(panel);
				}
				else if ( this.my_laby[j][i]==2){
					panel = new JPanel();
					panel.setBorder(new LineBorder(Color.WHITE, 1));
					panel.setBackground(Color.YELLOW);
					my_panel.add(panel);
				}
				else if ( this.my_laby[j][i]==1){
					panel = new JPanel();
					panel.setBorder(new LineBorder(Color.WHITE, 1));
					panel.setBackground(Color.BLACK);
					my_panel.add(panel);
				}
				else {
					panel = new JPanel();
					panel.setBorder(new LineBorder(Color.WHITE, 1));
					panel.setBackground(Color.GRAY);
					my_panel.add(panel);
				}
			}
		}
	}
	
	
	// ************************* ReadFile *******************************
	
	public void ReadFile(String fichier){
		String ligne="";
		int i=0,j=0 ;
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			System.out.println("Debut");
			
			// *** Debut *********
			ligne=br.readLine();
			this.pos_x = Integer.parseInt(ligne)-1;
			System.out.println("Position X : " + pos_x);

			ligne=br.readLine();
			this.pos_y = Integer.parseInt(ligne)-1;
			System.out.println("Position Y : " + pos_y);

			ligne=br.readLine();
			this.x = Integer.parseInt(ligne);
			System.out.println("Valeur de x : " + x);

			ligne=br.readLine();
			this.y = Integer.parseInt(ligne);
			System.out.println("Valeur de y : " + y);
			
			// Creation du tableau qui fera 20 par 15
			this.my_laby = new int[y][x];

			char [] tab = new char[x];

			while (j<y){
				ligne=br.readLine();
				tab = ligne.toCharArray();
				i=0;
				int avancement = 0 ;
				while (i<this.x){
					if ( tab[avancement]=='-' ){
						this.my_laby[j][i]=2;
						this.end_x = i ;
						this.end_y = j ;
						avancement ++;
					}
					else {
						this.my_laby[j][i]=Integer.parseInt(String.valueOf(tab[avancement]));
					}
					avancement++;
					i++;
				}
				j++;
			}	
			this.my_laby[pos_y][pos_x]=3;
			br.close(); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
			
	}
		
		// Affiche la labyrinthe
		public void affiche(){
			for ( int j = 0 ; j<this.y;j++){
				for ( int i = 0 ; i<this.x;i++){
					System.out.print(my_laby[j][i]);
				}
				System.out.println();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Noeud noeud ;
			if( this.menu_algo.getSelectedItem()=="Option 1"){
				if ( this.coutUni==null){
					System.out.println("Option 1 NEXT");
					coutUni = new CoutUniforme(this.my_laby,this.x,this.y, this.pos_x, this.pos_y);
					noeud = coutUni.CoutUni();
					my_laby[pos_y][pos_x]=0;
					my_laby[noeud.y][noeud.x]=3;
					// Reactualisation de l'affichage
					this.creationJPanel();
				}
				else {
					noeud = coutUni.CoutUni();
					my_laby[pos_y][pos_x]=0;
					my_laby[noeud.y][noeud.x]=3;
					// Reactualisation de l'affichage
					this.my_panel.revalidate();
					
				}
			}
			else if( this.menu_algo.getSelectedItem()=="Option 2"){
				if ( this.meilleur==null){
					System.out.println("Option 2 NEXT");
					meilleur = new MeilleurDAbord(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y);
					meilleur.afficheValeur();
					noeud = meilleur.CoutUni();
					my_laby[pos_y][pos_x]=0;
					my_laby[noeud.y][noeud.x]=3;
					System.out.println("Position : " + noeud.x +"/"+ noeud.y+ "  Valeur : " + noeud.h );
					// Reactualisation de l'affichage
					this.creationJPanel();
				}
				else {
					noeud = meilleur.CoutUni();
					my_laby[pos_y][pos_x]=0;
					my_laby[noeud.y][noeud.x]=3;
					System.out.println("Position : " + noeud.x +"/"+ noeud.y + "  Valeur : " + noeud.h);

					// Reactualisation de l'affichage
					this.my_panel.revalidate();
					
				}
			}
			else if( this.menu_algo.getSelectedItem()=="Option 3"){
		
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	// ************************** MAIN *************************************
		
		public static void main ( String argv[]){	
			Creation my_lab = new Creation();
			my_lab.affiche();
			my_lab.setLocationRelativeTo (null);
			my_lab.pack ();
			my_lab.setVisible (true);
		
		}
	
	
}
