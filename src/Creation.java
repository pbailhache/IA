import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;


public class Creation extends JFrame implements MouseListener{

	// Variable algo
	private CoutUniforme uniforme = null ;
	private MeilleurDAbord meilleur = null ;
	private AStar star = null ;
	
	// Variable
	private File file = new File("C:\\Users\\Ptit\\workspace\\Laby2\\laby.txt");
	private My_Laby my_panel ;
	private JButton button_next, button_fin, button_quit ;
	private JComboBox<String> menu_algo ;
	private Box box_bas, box_haut ;
	private int x,y,pos_x,pos_y,end_x,end_y;
	
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
		my_panel = new My_Laby(x,y,pos_x,pos_y,end_x,end_y,my_laby,this);

		
		// ******** Ajout dans la frame
		this.add(haut,BorderLayout.NORTH);
		this.add(box_bas,BorderLayout.SOUTH);
		this.add(this.my_panel,BorderLayout.CENTER);
	}
	
	
	
	// ************************* ReadFile *******************************
	
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
		
		// *******************************************************************************
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Object source = arg0.getSource();
			Noeud noeudArr ;
			Noeud initial = new Noeud(pos_x,pos_y,0,null);
			LinkedList<Noeud> listeDep = new LinkedList();
			if ( source == button_next && this.pos_x != this.end_x && this.pos_y != this.end_y){
				// TODO Auto-generated method stub
				
				// ****************************************************
				if( this.menu_algo.getSelectedItem()=="Cout Uniforme"){	
					if ( this.uniforme==null){
						// J'initialise la classe CoutUniforme
						uniforme = new CoutUniforme(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
						listeDep = uniforme.algo(initial);	
						oldDep = listeDep.getFirst();
						noeudArr = listeDep.getLast();
						this.pos_x = noeudArr.x;
						this.pos_y = noeudArr.y;
						listeDeplacementToDo.addAll(listeDep);
					}
					else {
						
						// COut uniforme deja initialiser, il suffit de lancer l'algo
						listeDep = uniforme.algo(oldDep);
						noeudArr = listeDep.getLast();
						initial = listeDep.getFirst();
						
						// C'est la fin
						if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
							listeDeplacementToDo.addAll(listeDep);
							LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
							liste_parcours_final = uniforme.retourPere(noeud, liste_parcours_final);
							listeArrive.addAll(liste_parcours_final);
						}
						else{
							listeDeplacementToDo.addAll(listeDep);
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
						}	
					}					
				}
				else if (this.menu_algo.getSelectedItem()=="Meilleur d'Abord"){
					if ( this.meilleur==null){
						meilleur = new MeilleurDAbord(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
						listeDep = meilleur.algo(initial);	
						oldDep = listeDep.getFirst();
						noeudArr = listeDep.getLast();
						this.pos_x = noeudArr.x;
						this.pos_y = noeudArr.y;
						listeDeplacementToDo.addAll(listeDep);
					}
					else {
						// COut uniforme deja initialiser, il suffit de lancer l'algo
						listeDep = meilleur.algo(oldDep);
						noeudArr = listeDep.get(1);
						initial = listeDep.getFirst();
						
	
						// C'est la fin
						if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
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
				else if (this.menu_algo.getSelectedItem()=="AStar"){
					if ( this.star==null){
						// J'initialise la classe CoutUniforme
						star = new AStar(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
						listeDep = star.algo(initial);	
						oldDep = listeDep.getFirst();
						noeudArr = listeDep.getLast();
						this.pos_x = noeudArr.x;
						this.pos_y = noeudArr.y;
						listeDeplacementToDo.addAll(listeDep);
					}
					else {
						// COut uniforme deja initialiser, il suffit de lancer l'algo
						listeDep = star.algo(oldDep);
						noeudArr = listeDep.getLast();
						initial = listeDep.getFirst();
						
	
						// C'est la fin
						if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
							listeDeplacementToDo.addAll(listeDep);
							LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
							liste_parcours_final = star.retourPere(noeudArr, liste_parcours_final);
							listeArrive.addAll(liste_parcours_final);
						}
						else{
							listeDeplacementToDo.addAll(listeDep);
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
						}	
					}		
					
				}
			}
			else if ( source == button_fin  && this.pos_x != this.end_x && this.pos_y != this.end_y){
				boolean isFini = false ;
				while ( !isFini ){
					if( this.menu_algo.getSelectedItem()=="Cout Uniforme"){	
						if ( this.uniforme==null){
							// J'initialise la classe CoutUniforme
							uniforme = new CoutUniforme(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
							listeDep = uniforme.algo(initial);	
							oldDep = listeDep.getFirst();
							noeudArr = listeDep.getLast();
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
							listeDeplacementToDo.addAll(listeDep);
						}
						else {
							
							// COut uniforme deja initialiser, il suffit de lancer l'algo
							listeDep = uniforme.algo(oldDep);
							oldDep = listeDep.getLast();
							noeudArr = listeDep.getLast();
							initial = listeDep.getFirst();							
							// C'est la fin
							if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
								listeDeplacementToDo.addAll(listeDep);
								LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
								liste_parcours_final = uniforme.retourPere(noeudArr, liste_parcours_final);
								listeArrive.addAll(liste_parcours_final);
								isFini = true ;	
							}
							else{
								listeDeplacementToDo.addAll(listeDep);
							}	
						}					
					}
					else if (this.menu_algo.getSelectedItem()=="Meilleur d'Abord"){
						if ( this.meilleur==null){
							meilleur = new MeilleurDAbord(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
							listeDep = meilleur.algo(initial);	
							oldDep = listeDep.getFirst();
							noeudArr = listeDep.getLast();
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
							listeDeplacementToDo.addAll(listeDep);
						}
						else {
							// Cout uniforme deja initialiser, il suffit de lancer l'algo
							listeDep = meilleur.algo(oldDep);
							oldDep = listeDep.get(1);
							noeudArr = listeDep.get(1);
							initial = listeDep.getFirst();
							
		
							// C'est la fin
							if (noeudArr.x == this.end_x && noeudArr.y ==end_y){
								listeDeplacementToDo.addAll(listeDep);
								LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
								liste_parcours_final = meilleur.retourPere(noeudArr, liste_parcours_final);
								listeArrive.addAll(liste_parcours_final);
								isFini = true ;
								
							}
							else{
								listeDeplacementToDo.addAll(listeDep);
							}	
						}		
					}
					else if (this.menu_algo.getSelectedItem()=="AStar"){
						if ( this.star==null){
							// J'initialise la classe CoutUniforme
							star = new AStar(this.my_laby,this.x,this.y, this.pos_x, this.pos_y, this.end_x, this.end_y ,initial);
							listeDep = star.algo(initial);	
							oldDep = listeDep.getFirst();
							noeudArr = listeDep.getLast();
							this.pos_x = noeudArr.x;
							this.pos_y = noeudArr.y;
							listeDeplacementToDo.addAll(listeDep);
						}
						else {
							// COut uniforme deja initialiser, il suffit de lancer l'algo
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
		
		public void reset(){
			this.ReadFile(file);
			System.out.println("RESET Check");
			this.my_panel = null ;
			this.my_panel = new My_Laby(x,y,pos_x,pos_y,end_x,end_y,my_laby,this);
			this.my_panel.setLaby(this.my_laby);
			this.remove(my_panel);
			this.add(this.my_panel,BorderLayout.CENTER);
			this.uniforme = null ;
			this.meilleur = null ;
			this.star = null ;
			//this.my_panel.affichage(this.my_panel.getLaby());
			this.listeDeplacementToDo = new LinkedList<Noeud>();
			this.listeArrive = new LinkedList<Noeud>();
		}
		
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
			    System.out.println(fichier);
			    sortie.close();
			    this.file = fichier ;
			    this.ReadFile(fichier);
			    this.listeDeplacementToDo = new LinkedList<Noeud>();
			    this.remove(my_panel);
			    this.uniforme = null ;
				this.meilleur = null ;
				this.star = null ;
			    this.my_panel = new My_Laby(x,y,pos_x,pos_y,end_x,end_y,my_laby,this);
				this.add(this.my_panel,BorderLayout.CENTER);
			}
	  	}
		
	// ************************** MAIN *************************************
		
		public static void main ( String argv[]){	
			Creation my_lab = new Creation();
			//my_lab.affiche();
			my_lab.setLocationRelativeTo (null);
			my_lab.pack ();
			my_lab.setVisible (true);
			
			while (true)
			{
				my_lab.affichDeplacement();
				my_lab.my_panel.affichage(my_lab.my_panel.getLaby());
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}

	private void affichDeplacement() 
	{
		// TODO Auto-generated method stub
		if(!listeDeplacementToDo.isEmpty())
		{
			Noeud currentDep = listeDeplacementToDo.removeFirst();
			if (oldDep.x != end_x || oldDep.y != end_y)
				my_laby[oldDep.y][oldDep.x]=4;
			my_laby[currentDep.y][currentDep.x]=3;
			//this.my_panel.affichage(my_laby);
			oldDep = currentDep;
		}
		else if (!listeArrive.isEmpty()){
			Noeud currentDep = listeArrive.removeFirst();
			my_laby[currentDep.y][currentDep.x]=5;
			//this.my_panel.affichage(my_laby);
		}
	}
		
		
	
	
}

	