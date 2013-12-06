import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;


public class Creation extends JFrame implements MouseListener{

	// ********************************************************************************************
	// 											Variable
	// ********************************************************************************************
	
	// Variable algo
	private CoutUniforme uniforme = null ;
	private MeilleurDAbord meilleur = null ;
	private AStar star = null ;
	
	// Variable
	private File file = new File("C:\\Users\\Ptit\\workspace\\Laby2\\laby.txt");
	private My_Laby my_panel ;
	private Timer timer ;
	private Popup popup ;
    private Boolean timer_on = false ;
	private JButton button_next, button_fin, button_quit ;
	private JComboBox<String> menu_algo ;
	private Box box_bas ;
	private int x,y,pos_x,pos_y,end_x,end_y,nombreCout=-1;
	
	/* my_laby ca contenir un tableau remplit de integer allant de 0 a 5 :
	//	 * - 0 : couloir inexploré
	//	 * - 1 : Mur
	//	 * - 2 : Sortie
	//	 * - 3 : Position du personnage
	//	 * - 4 : Lieux deja exploré
	//	 * - 5 : Chemin optimal vers la sortie
	//	*/
	private int[][] my_laby ;
	public Noeud noeud ;	
	private Noeud oldDep;
	private LinkedList<Noeud> listeDeplacementToDo = new LinkedList<Noeud>();
	private LinkedList<Noeud> listeArrive = new LinkedList<Noeud>();
	
	// ********************************************************************************************
	// 											Constructeur
	// ********************************************************************************************
	public Creation (){
		super("Projet Labyrinthe");
		this.ReadFile(file);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE) ;
		this.setPreferredSize(new Dimension (600,600));
		
		// ****** Initialisation
		button_next = new JButton("Next");
		button_next.addMouseListener(this);
		button_fin = new JButton("Fin de Cycle");
		button_fin.addMouseListener(this);
		button_quit = new JButton("Quit");
		button_quit.addActionListener ( new ActionListener () {
            public void actionPerformed ( ActionEvent event ){
		    System.exit(0);
		}});
		menu_algo = new JComboBox<String>() ;
		box_bas = Box.createHorizontalBox();
		Menu_Haut haut = new Menu_Haut(this);
		// ********* Option
		menu_algo.setPreferredSize(new Dimension(50, 20));
		menu_algo.addItem("Cout Uniforme");
		menu_algo.addItem("Meilleur d'Abord");
		menu_algo.addItem("AStar");

		box_bas.add(Box.createRigidArea(new Dimension(5,5)));
		box_bas.add(menu_algo);
		box_bas.add(Box.createHorizontalGlue());
		
		box_bas.add(button_fin);
		box_bas.add(Box.createHorizontalGlue());
		box_bas.add(button_next);
		box_bas.add(Box.createHorizontalGlue());
		box_bas.add(button_quit);
		box_bas.add(Box.createHorizontalGlue());
		
		//creationJPanel();
		my_panel = new My_Laby(x,y,my_laby,this);

		
		// ******** Ajout dans la frame
		this.add(haut,BorderLayout.NORTH);
		this.add(box_bas,BorderLayout.SOUTH);
		this.add(this.my_panel,BorderLayout.CENTER);
	}
	
	
	
	// ********************************************************************************************
	// 											ReadFile
	// ********************************************************************************************
	
	public void ReadFile(File fichier){
		String ligne="";
		int i=0,j=0 ;
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			
			// *** Debut *********
			
			ligne=br.readLine();
			this.pos_x = Integer.parseInt(ligne)-1;

			ligne=br.readLine();
			this.pos_y = Integer.parseInt(ligne)-1;

			ligne=br.readLine();
			this.x = Integer.parseInt(ligne);

			ligne=br.readLine();
			this.y = Integer.parseInt(ligne);
			
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
			try {
				this.addPopUpError();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			
	}
		
		
	// ********************************************************************************************
	// 											mouseClicked
	// ********************************************************************************************
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Object source = arg0.getSource();
			Noeud noeudArr, tmp ;
			Noeud initial = new Noeud(pos_x,pos_y,0,null);
			LinkedList<Noeud> listeDep = new LinkedList<Noeud>();
			// On regarde quel bouton est selectionner, et si on est deja arrivé à la fin
			// ****************************************************
			//  			Bouton Next
			if ( source == button_next && this.pos_x != this.end_x && this.pos_y != this.end_y){	
				nombreCout++;
				// ****************************************************
				//   			Cout Uniforme
				if( this.menu_algo.getSelectedItem()=="Cout Uniforme" && this.meilleur==null && this.star==null){	
					// Si l'algorithme cout uniforme n'est pas encore créer 
					if ( this.uniforme==null){
						// J'initialise la classe CoutUniforme
						uniforme = new CoutUniforme(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
						listeDep = uniforme.algo(initial);	
						oldDep = listeDep.getFirst();
						noeudArr = listeDep.getLast();
						this.pos_x = noeudArr.x;
						this.pos_y = noeudArr.y;
						listeDeplacementToDo.addAll(listeDep);
						nombreCout++;
						listeDep = uniforme.algo(oldDep);
						noeudArr = listeDep.getLast();
						initial = listeDep.getFirst();
						listeDeplacementToDo.addAll(listeDep);
						
					}
					// Cout uniforme deja initialiser, il suffit de lancer l'algo
					else {					
						listeDep = uniforme.algo(oldDep);
						noeudArr = listeDep.getLast();
						initial = listeDep.getFirst();
										
						// C'est la fin
						if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
							LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
							liste_parcours_final = uniforme.retourPere(noeudArr, liste_parcours_final);
							listeArrive.addAll(liste_parcours_final);
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
						}
						else{
							listeDeplacementToDo.addAll(listeDep);							
						}	
					}					
				}
				// ****************************************************
				//   			Meilleur d'abord
				else if (this.menu_algo.getSelectedItem()=="Meilleur d'Abord"  && this.uniforme==null && this.star==null){
					// Si meilleur n'est pas créé
					if ( this.meilleur==null){
						meilleur = new MeilleurDAbord(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
						listeDep = meilleur.algo(initial);	
						oldDep = listeDep.getFirst();
						noeudArr = listeDep.getLast();
						this.pos_x = noeudArr.x;
						this.pos_y = noeudArr.y;
						listeDeplacementToDo.addAll(listeDep);
						nombreCout++;
						listeDep = meilleur.algo(oldDep);
						noeudArr = listeDep.get(1);
						initial = listeDep.getFirst();
						listeDeplacementToDo.addAll(listeDep);
					}
					// Meilleur D'abord deja initialiser, il suffit de lancer l'algo
					else {
						listeDep = meilleur.algo(oldDep);
						noeudArr = listeDep.get(1);
						initial = listeDep.getFirst();
						
							
						// C'est la fin
						if ((noeudArr.x == this.end_x && noeudArr.y ==end_y) || (initial.x == this.end_x && initial.y ==end_y)){
							LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
							liste_parcours_final = meilleur.retourPere(noeudArr, liste_parcours_final);
							listeArrive.addAll(liste_parcours_final);
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
							
						}
						else{
							listeDeplacementToDo.addAll(listeDep);
						}	
					}		
				}
				// ****************************************************
				//   				ASTAR
				else if (this.menu_algo.getSelectedItem()=="AStar" && this.meilleur==null && this.uniforme==null){
					if ( this.star==null){
						// J'initialise la classe AStar
						star = new AStar(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
						listeDep = star.algo(initial);	
						oldDep = listeDep.getFirst();
						noeudArr = listeDep.getLast();
						this.pos_x = noeudArr.x;
						this.pos_y = noeudArr.y;
						listeDeplacementToDo.addAll(listeDep);
						nombreCout++;
						listeDep = star.algo(oldDep);
						noeudArr = listeDep.getLast();
						initial = listeDep.getFirst();
						listeDeplacementToDo.addAll(listeDep);

					}
					else {
						// Meilleur D'abord deja initialiser, il suffit de lancer l'algo
						listeDep = star.algo(oldDep);
						noeudArr = listeDep.getLast();
						initial = listeDep.getFirst();
	
						// C'est la fin
						if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
							listeDeplacementToDo.addAll(listeDep);
							LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
							liste_parcours_final = star.retourPere(noeudArr, liste_parcours_final);
							listeArrive.addAll(liste_parcours_final);
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
							
						}
						else{
							listeDeplacementToDo.addAll(listeDep);							
						}	
					}		
					
				}
				else {
					try {
						this.addPopUpAlgo();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// ****************************************************
			//   				Bouton Cycle
			else if ( source == button_fin  && this.pos_x != this.end_x && this.pos_y != this.end_y){
				boolean isFini = false;
				// La même chose que précedement mais on le lance dans une boucle
				while ( !isFini ){
					nombreCout++;
						// ****************************************************
						// 					Cout Uniforme
						if( this.menu_algo.getSelectedItem()=="Cout Uniforme"){	
							if ( this.uniforme==null){
								// J'initialise la classe CoutUniforme
								uniforme = new CoutUniforme(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
								listeDep = uniforme.algo(initial);	
								oldDep = listeDep.getFirst();
								noeudArr = listeDep.getLast();
								listeDeplacementToDo.addAll(listeDep);

							}
							else {
								System.out.println("Uniforme    " + nombreCout);
								// Cout uniforme deja initialiser, il suffit de lancer l'algo
								listeDep = uniforme.algo(oldDep);
								oldDep = listeDep.getLast();
								noeudArr = listeDep.getLast();
								// C'est la fin
								if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
									listeDeplacementToDo.addAll(listeDep);
									LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
									liste_parcours_final = uniforme.retourPere(noeudArr, liste_parcours_final);
									listeArrive.addAll(liste_parcours_final);
									try {
										this.addPopUpAlgoFini();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									isFini = true ;	
								}
								else{
									listeDeplacementToDo.addAll(listeDep);
								}	
							}					
						}
						// ****************************************************
						// 					Meilleur d'Abord
						else if (this.menu_algo.getSelectedItem()=="Meilleur d'Abord"){
							// Initialisation du meilleur d'abord
							if ( this.meilleur==null){
								meilleur = new MeilleurDAbord(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
								listeDep = meilleur.algo(initial);	
								oldDep = listeDep.getFirst();
								noeudArr = listeDep.getLast();
								listeDeplacementToDo.addAll(listeDep);
							}
							else {
								System.out.println("Meilleur    " + nombreCout);
								// Meilleur d'Abord deja initialiser, il suffit de lancer l'algo
								listeDep = meilleur.algo(oldDep);
								oldDep = listeDep.get(1);
								noeudArr = listeDep.get(1);
								initial = listeDep.getFirst();	
								tmp = listeDep.getLast() ;
								// C'est la fin
								if ((noeudArr.x == this.end_x && noeudArr.y ==end_y) || (tmp.x == this.end_x && tmp.y ==end_y)){
									listeDeplacementToDo.addAll(listeDep);
									LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
									liste_parcours_final = meilleur.retourPere(noeudArr, liste_parcours_final);
									listeArrive.addAll(liste_parcours_final);
									try {
										this.addPopUpAlgoFini();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									isFini = true ;
									
								}
								else{
									listeDeplacementToDo.addAll(listeDep);
								}	
							}		
						}
						// ****************************************************
						//						AStar
						else if (this.menu_algo.getSelectedItem()=="AStar"){
							if ( this.star==null){
								// J'initialise la classe Astar	
								star = new AStar(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
								listeDep = star.algo(initial);	
								oldDep = listeDep.getFirst();
								noeudArr = listeDep.getLast();
								
								listeDeplacementToDo.addAll(listeDep);
							}
							else {
								System.out.println("A*    " + nombreCout);
								// AStar deja initialiser, il suffit de lancer l'algo
								listeDep = star.algo(oldDep);
								noeudArr = listeDep.getLast();
								initial = listeDep.getFirst();
								oldDep = listeDep.getLast();
							
							
			
								// C'est la fin
								if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
									listeDeplacementToDo.addAll(listeDep);
									LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
									liste_parcours_final = star.retourPere(noeudArr, liste_parcours_final);
									listeArrive.addAll(liste_parcours_final);
									try {
										this.addPopUpAlgoFini();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									isFini = true ;
								}
								else{
									listeDeplacementToDo.addAll(listeDep);
								}	
							}		
							
						
				
					}				
				}
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
		
		// ********************************************************************************************
		// 											Reset
		// ********************************************************************************************
		// Remet dans l'état initial le labyrinthe
		public void reset(){
			this.ReadFile(file);
			this.my_panel = null ;
			this.my_panel = new My_Laby(x,y,my_laby,this);
			this.remove(my_panel);
			this.add(this.my_panel,BorderLayout.CENTER);
			this.uniforme = null ;
			this.meilleur = null ;
			this.nombreCout = -1 ;
			this.star = null ;
			this.listeDeplacementToDo = new LinkedList<Noeud>();
			this.listeArrive = new LinkedList<Noeud>();
		}
		
		// ********************************************************************************************
		// 											Load
		// ********************************************************************************************
		// Permet de charger un labyrinthe
		public void load(){
			JFileChooser dialogue = new JFileChooser(new File("."));
			PrintWriter sortie = null;
			File fichier;
			
			if (dialogue.showOpenDialog(null)== 
			    JFileChooser.APPROVE_OPTION) {
			    fichier = dialogue.getSelectedFile();
			    try {
					sortie = new PrintWriter(new FileWriter(fichier.getPath(), true));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    sortie.close();
			    this.file = fichier ;			    
			    this.listeDeplacementToDo = new LinkedList<Noeud>();
			    this.listeArrive = new LinkedList<Noeud>();
			    this.remove(my_panel);
			    this.uniforme = null ;
				this.meilleur = null ;
				this.nombreCout = -1 ;
				this.star = null ;
				this.ReadFile(fichier);
			    this.my_panel = new My_Laby(x,y,my_laby,this);
				this.add(this.my_panel,BorderLayout.CENTER);
			}
	  	}
		
		// ********************************************************************************************
		// 											Main
		// ********************************************************************************************
		
		public static void main ( String argv[]){	
			Creation my_lab = new Creation();
			my_lab.setLocation(500, 100);
			my_lab.pack ();
			my_lab.setVisible (true);
			// Permet l'actualisation de l'affichage en temps réél
			while (true)
			{
				my_lab.affichDeplacement();
				my_lab.my_panel.affichage();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
		
		// ********************************************************************************************
		// 											Affiche Deplacement
		// ********************************************************************************************
		// Permet d'afficher les diffèrents déplacements
		public void affichDeplacement() 
		{
			if(!listeDeplacementToDo.isEmpty())
			{
				Noeud currentDep = listeDeplacementToDo.removeFirst();
				if (oldDep.x != end_x || oldDep.y != end_y)
					my_laby[oldDep.y][oldDep.x]=4;
				my_laby[currentDep.y][currentDep.x]=3;
				oldDep = currentDep;
			}
			else if (!listeArrive.isEmpty()){
				Noeud currentDep = listeArrive.removeFirst();
				my_laby[currentDep.y][currentDep.x]=5;
				if ( listeArrive.size()==1){
					try {
						this.addPopUpAlgoFini();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		// ********************************************************************************************
		// 											PopUp
		// ********************************************************************************************
		// PopUp pour indiquer que le fichier à charger n'est pas bon
	   public void addPopUpError() throws InterruptedException{
	        if ( timer_on ){
	            timer.stop();
	        }
	        String text = "Probleme lors du chargement. \nLe fichier ne correspond pas aux normes."
			+ "\nVoici comment doit être formater le fichier :"
			+ "\n - position en X"
			+ "\n - position en Y"
			+ "\n - Largeur du labyrinthe"
			+ "\n - Longueur du labyrinthe"
			+ "\n - Le labyrinthe par ligne et colonne";
			JTextArea resume = new JTextArea(text,5,5);
	
			PopupFactory factory = PopupFactory.getSharedInstance();
			popup = factory.getPopup(this, resume,700, 200);
			popup.show();
			ActionListener hider = new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            popup.hide();
			        }
			};      
			// Hide popup in 3 seconds
			    timer = new Timer(5000,hider);
			    timer.start();
			    timer_on = true ;
		}
	   
	   public void addPopUpAlgo() throws InterruptedException{
	        if ( timer_on ){
	            timer.stop();
	        }
	        String text = "Il y a déjà un algorithme en cours.";
			JTextArea resume = new JTextArea(text,5,5);
	
			PopupFactory factory = PopupFactory.getSharedInstance();
			popup = factory.getPopup(this, resume,700, 200);
			popup.show();
			ActionListener hider = new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            popup.hide();
			        }
			};      
			// Hide popup in 3 seconds
			    timer = new Timer(5000,hider);
			    timer.start();
			    timer_on = true ;
		}
	   
	   public void addPopUpAlgoFini() throws InterruptedException{

	        String text = "Arrivé ! \n Nombre de cout éxécuter : " + this.nombreCout ;
			JTextArea resume = new JTextArea(text,5,5);
	
			Box vbox = new Box(BoxLayout.Y_AXIS);
			vbox.add(Box.createHorizontalGlue());
			JButton quit_b = new JButton("Quit");
			quit_b.addActionListener ( new ActionListener () {
		                @Override
				public void actionPerformed ( ActionEvent event ){
				    popup.hide();
				}});
			vbox.add(resume);
			vbox.add(Box.createRigidArea( new Dimension (20,20)));
			vbox.add(quit_b);
 
			PopupFactory factory = PopupFactory.getSharedInstance();
			popup = factory.getPopup(this, vbox,700, 200);
			popup.show();

		}
	
}

	