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
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;


public class Creation extends JFrame implements MouseListener{

	// Variable algo
	private CoutUniforme coutUni = null ;
	//private MeilleurDAbord meilleur = null ;
	//private AStar astar = null ;
	// Variable
	
	private Laby my_panel ;
	private JButton button_next, button_fin ;
	private JComboBox menu_algo ;
	private Box box_bas, box_haut ;
	private int x,y,pos_x,pos_y,end_x,end_y;
	// Le tableau qui va contenir les 0/1/2
	private int[][] my_laby ;
	public Noeud noeud ;
	
	public Creation (){
		super("Porjet Labyrinthe");
		ToolTipManager.sharedInstance().setInitialDelay(0);
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
		my_panel = new Laby(x,y,pos_x,pos_y,end_x,end_y,my_laby,this);
		// Lacnement de l'affichage
		my_panel.startAff();
		// ******** Ajout dans la frame
		this.add(box_haut,BorderLayout.NORTH);
		this.add(box_bas,BorderLayout.SOUTH);
		this.add(this.my_panel,BorderLayout.CENTER);
	}
	
	// ************************* Creation des JPanel *******************
	/*public void creationJPanel (){
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
				else if ( this.my_laby[j][i]==4){
					panel = new JPanel();
					panel.setBorder(new LineBorder(Color.WHITE, 1));
					panel.setBackground(Color.GREEN);
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
	*/
	
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
		public void affiche(int[][] tab){
			for ( int j = 0 ; j<this.y;j++){
				for ( int i = 0 ; i<this.x;i++){
					System.out.print(tab[j][i]);
				}
				System.out.println();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			Noeud tmp1, last_pos ;
			Noeud tmp = new Noeud(pos_x,pos_y,0,null);
			LinkedList<Noeud> listeDep = new LinkedList();
			// ****************************************************
			if( this.menu_algo.getSelectedItem()=="Option 1"){
				
				if ( this.coutUni==null){
					// J'initialise la classe CoutUniforme
					coutUni = new CoutUniforme(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, tmp);
					System.out.println(tmp);
					// On lance l'algo avec notre position courante et on recupere où on doit aller
					listeDep = coutUni.CoutUni(tmp);
					
					last_pos = listeDep.getFirst() ;
					noeud = listeDep.getLast() ;
					my_laby[pos_y][pos_x]=4;
					my_laby[noeud.y][noeud.x]=3;
					this.pos_x = noeud.x ;
					this.pos_y = noeud.y ;
					// Reactualisation de l'affichage ******************************************
					//this.my_panel.affichage(my_laby);
				}
				else {
					// COut uniforme deja initialiser, il suffit de lancer l'algo
					
					listeDep = coutUni.CoutUni(noeud);
					System.out.println("Lise deplacement");
					for ( int i = 0; i<listeDep.size() ; i++){
						System.out.println("Liste  " + i + " : " + listeDep.get(i) );
						System.out.println("Position de x : " + listeDep.get(i).y +"/" + listeDep.get(i).x );

					}
					last_pos = listeDep.getFirst() ;
					noeud=listeDep.getLast() ;
					my_laby[last_pos.y][last_pos.x]=3;
					System.out.println("Valeur de tmp : " + last_pos.y +"/" + last_pos.x + "    " +last_pos.h);
					System.out.println("Valeur de noeud : " + noeud.y +"/" + noeud.x + "    " +noeud.h);
					
					if (noeud.x == this.end_x && noeud.y ==end_y){
						LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
						liste_parcours_final = coutUni.retourPere(noeud, liste_parcours_final);
						for(int k =0 ; k < liste_parcours_final.size() ; k++){
							my_laby[liste_parcours_final.get(k).y][liste_parcours_final.get(k).x]=5;
						}
						// Reactualisation de l'affichage ******************************************
						//this.my_panel.affichage(my_laby);						
						this.pos_x = this.end_x ;
						this.pos_y = this.end_y ;
						System.out.println("ARRIVE");
					}
//					else {
//						my_laby[pos_y][pos_x]=4;
//						my_laby[noeud.y][noeud.x]=3;
//						this.pos_x = noeud.x ;
//						this.pos_y = noeud.y ;
//						// Reactualisation de l'affichage	
//						this.my_panel.affichage(my_laby);
//					}
					else {
						System.out.println("Creation else 3");
						
						
						System.out.println("Lise deplacementxxxxxx");
						for ( int i = 0; i<listeDep.size() ; i++){
							System.out.println("Liste  " + i + " : " + listeDep.get(i) );
							System.out.println("Positiontttt de x : " + listeDep.get(i).y +"/" + listeDep.get(i).x );
						}
						
						
						int old_x = pos_x;
						int old_y = pos_y;
						for(int k =0 ; k < listeDep.size() ; k++)
						{

							my_laby[old_y][old_x]=4;
							Noeud to_visit = listeDep.get(k);
							my_laby[to_visit.y][to_visit.x]=3;
							// Reactualisation de l'affichage ******************************************
							//this.my_panel.affichage(my_laby);
							old_x = to_visit.x;
							old_y = to_visit.y;
							my_panel.setLaby(this.my_laby);
							

							System.out.println("***********");
							System.out.println(to_visit.y + "/" + to_visit.x);
							System.out.println(my_laby[to_visit.y][to_visit.x]);
							int[][] tmpTab = my_panel.getLaby();
							System.out.println(tmpTab[to_visit.y][to_visit.x]);
							System.out.println("--------------------------------------------");
							this.affiche(my_laby);
							System.out.println("--------------------------------------------");
							this.affiche(tmpTab);
							System.out.println("--------------------------------------------");
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}						
						}

//						while ( !listeDep.isEmpty() ){
//							my_laby[pos_y][pos_x]=4;
//							tmp1 = listeDep.removeFirst();
//							my_laby[tmp1.y][tmp1.x]=3;
//							System.out.println("Position de x : " + tmp1.x +"/" + tmp.y + "     ");
//							System.out.println(tmp1);
//							this.pos_x = tmp1.x ;
//							this.pos_y = tmp1.y ;
//							this.my_panel.affichage(my_laby);
//							break;
//						
//							
//						}
					}
					
				}
			}
//			else if( this.menu_algo.getSelectedItem()=="Option 2"){
//				if ( this.meilleur==null){
//					meilleur = new MeilleurDAbord(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y);
//					meilleur.afficheValeur();
//					noeud = meilleur.meilleurDAb();
//					my_laby[pos_y][pos_x]=4;
//					my_laby[noeud.y][noeud.x]=3;
//					System.out.println("Position : " + noeud.x +"/"+ noeud.y+ "  Valeur : " + noeud.h );
//					// Reactualisation de l'affichage
//					//this.creationJPanel();
//				}
//				else {
//					noeud = meilleur.meilleurDAb();
//					if (noeud.x == this.end_x && noeud.y ==end_y){
//						System.out.println("ARRIVE");
//					}
//					else {
//						my_laby[pos_y][pos_x]=4;
//						my_laby[noeud.y][noeud.x]=3;
//						System.out.println("Position : " + noeud.x +"/"+ noeud.y + "  Valeur : " + noeud.h);
//	
//						// Reactualisation de l'affichage
//						this.my_panel.revalidate();
//					}
//				}
//			}
//			else if( this.menu_algo.getSelectedItem()=="Option 3"){
//				if ( this.astar==null){
//					astar = new AStar(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y);
//					astar.afficheValeur();
//					noeud = astar.aStar();
//					my_laby[pos_y][pos_x]=4;
//					my_laby[noeud.y][noeud.x]=3;
//					System.out.println("Position : " + noeud.x +"/"+ noeud.y+ "  Valeur : " + noeud.h );
//					// Reactualisation de l'affichage
//					//this.creationJPanel();
//				}
//				else  {
//					noeud = astar.aStar();
//					if (noeud.x == this.end_x && noeud.y ==end_y){
//						System.out.println("ARRIVE");
//					}
//					else {
//						my_laby[pos_y][pos_x]=4;
//						my_laby[noeud.y][noeud.x]=3;
//						System.out.println("Position : " + noeud.x +"/"+ noeud.y + "  Valeur : " + noeud.h);
//	
//						// Reactualisation de l'affichage
//						this.my_panel.revalidate();					}
//				}
//			}
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
			//my_lab.affiche();
			my_lab.setLocationRelativeTo (null);
			my_lab.pack ();
			my_lab.setVisible (true);
		
		}
		
		
	
	
}
