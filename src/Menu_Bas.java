import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;


public class Menu_Bas implements MouseListener{
	
	// Creation des lagorithmes
	private MeilleurDAbord meilleur = null ;
	private CoutUniforme uniforme = null ;
	private AStar star = null ;
	
	private JComboBox<String> combobox;
	private JButton button_next, button_fin, button_quit ;
	private Box hbox1 ;
	private My_Laby my_laby ;
	private Noeud oldDep, newDep;
	private LinkedList<Noeud> listeDeplacementToDo = new LinkedList<Noeud>();
	private LinkedList<Noeud> listeDep = new LinkedList<Noeud>();


	public Menu_Bas( My_Laby laby){
		// Creation de la boite contenant les diffèrents éléments
        hbox1=Box.createHorizontalBox();
        // Creation de la comboBox
        combobox = new JComboBox<String>();
        combobox.setPreferredSize(new Dimension(50, 20));
        combobox.addItem("Meilleur d'Abord");
        combobox.addItem("Cout Uniforme");
        combobox.addItem("AStar");
        hbox1.add(combobox);
        // Glue
        hbox1.add(Box.createRigidArea(new Dimension(15,0)));
        
        // Creation des Boutons
        button_next = new JButton("Next");
        button_fin = new JButton("Cycle");
        button_quit = new JButton("Quitter");
        hbox1.add(button_next);
        hbox1.add(Box.createRigidArea(new Dimension(5,0)));
        hbox1.add(button_fin);
        hbox1.add(Box.createRigidArea(new Dimension(5,0)));
        hbox1.add(button_quit);
        button_quit.addActionListener ( new ActionListener () {
            public void actionPerformed ( ActionEvent event ){
		    System.exit(0);
		}});
        hbox1.add(Box.createRigidArea(new Dimension(5,0)));
        
	}
	
	public Box getBox()
    {
        return hbox1;
    }
	
	public My_Laby getMyLaby(){
		return this.my_laby ;
	}
	
	// affiche deplacement
	public void affichDeplacement() 
	{
		// TODO Auto-generated method stub
		if(!listeDeplacementToDo.isEmpty())
		{
			Noeud currentDep = listeDeplacementToDo.removeFirst();
			my_laby.my_laby[oldDep.y][oldDep.x]=4;
			my_laby.my_laby[currentDep.y][currentDep.x]=3;
			this.my_laby.affichage(my_laby.my_laby);
			oldDep = currentDep;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		Noeud last_pos,courant,initial;
		initial = new Noeud(this.my_laby.getPosX(), this.my_laby.getPosY(),0,null);
		LinkedList<Noeud> listeDeplacementToDo = new LinkedList<Noeud>();
		// TODO Auto-generated method stub
		// ****************************************************
		if( this.combobox.getSelectedItem()=="Meilleur d'Abord"){
			if ( this.uniforme==null){
				// J'initialise la classe CoutUniforme
				uniforme = new CoutUniforme(this.my_laby.getLaby(),this.my_laby.getX(),this.my_laby.getY(), this.my_laby.getPosX(), this.my_laby.getPosY(), this.my_laby.getEndX(), this.my_laby.getEndY(), initial);
				// On lance l'algo avec notre position courante et on recupere où on doit aller
				listeDep = uniforme.algo(initial);				
				oldDep = listeDep.getFirst() ;
			}
			else {
				listeDep = uniforme.algo(oldDep);
				oldDep = listeDep.getFirst() ;
				newDep = listeDep.getLast() ;
				if (newDep.x == this.my_laby.getEndX() && newDep.y == this.my_laby.getEndY()){
					LinkedList<Noeud> liste_parcours_final = new LinkedList<Noeud>();
					liste_parcours_final = uniforme.retourPere(newDep, liste_parcours_final);
					listeDeplacementToDo.addAll(listeDep);
					for(int k =0 ; k < liste_parcours_final.size() ; k++){
						my_laby.my_laby[liste_parcours_final.get(k).y][liste_parcours_final.get(k).x]=5;
					}
				}
				else{
					listeDeplacementToDo.addAll(listeDep);
				}
				
			}
		}
		else if (this.combobox.getSelectedItem()=="Cout Uniforme"){
			
		}
		else {
			
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



}
