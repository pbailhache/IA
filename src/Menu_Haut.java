import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//********************************************************************************************
// 											Menu Haut
// ********************************************************************************************
/*
 * Cette classe permet d'avoir la barre avec les options en haut de la fenetre
 * Permettant ainsi facilement le chargement, le reset ou encore tout simplement
 * d'acceder au menu Help pour savoir le fonctionnement du programme.
 * De plus chacunes de ces fonctions a un raccourci clavier.
 */
public class Menu_Haut extends JMenuBar implements ChangeListener{
	private JMenu File, Help ;
	private JMenuItem quit,load, algorithme, reset ;
	private Popup popup ;
	private Timer timer ;
    private Boolean timer_on = false ;
    private JButton quit_b ;
    
    //********************************************************************************************
	//									Constructeur
	//********************************************************************************************
    public Menu_Haut(final Creation frame){
		// Menu File
		File = new JMenu("File");
		File.setMnemonic(KeyEvent.VK_F);
	    // Menu File - Bouton Load
		load = new JMenuItem ("Load");
		load.setAccelerator(KeyStroke.getKeyStroke
                (KeyEvent.VK_F,InputEvent.CTRL_MASK));
		load.addActionListener ( new ActionListener () {
            @Override
            	public void actionPerformed ( ActionEvent event ){
            	frame.load();
            }});
		File.add(load);
		// Menu File - Reset
		reset = new JMenuItem ("Reset");
		reset.setAccelerator(KeyStroke.getKeyStroke
                (KeyEvent.VK_R,InputEvent.CTRL_MASK));
		reset.addActionListener ( new ActionListener () {
            @Override
            	public void actionPerformed ( ActionEvent event ){
            	frame.reset();
            }});
		File.add(reset);
		// Menu File - Bouton Quit   
		quit = new JMenuItem ("Quit");
		quit.setAccelerator(KeyStroke.getKeyStroke
	                (KeyEvent.VK_Q,InputEvent.CTRL_MASK));
		quit.addActionListener ( new ActionListener () {
	            public void actionPerformed ( ActionEvent event ){
			    System.exit(0);
			}});
		File.add(quit);
		
		this.add(File);
		
		// Menu Help
		Help = new JMenu("Help");
		Help.setMnemonic(KeyEvent.VK_H);
		algorithme = new JMenuItem("Command help");
		Help.add(algorithme);
		algorithme.addActionListener ( new ActionListener () {
	                @Override
			public void actionPerformed ( ActionEvent event ){
			    addPopUpHelp();
			}});
		this.add(Box.createHorizontalGlue());
		this.add(Help);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	//********************************************************************************************
	//									PopUpHelp
	//********************************************************************************************

    // popup pour afficher à quoi sert, et comment se servir du logiciel
    public void addPopUpHelp() { 
		String text = "Ce programme nous permet de tester des algorithmes de recherche"
				+     "\n dans un labyrinthe, pour nous montrer le plus court chemin mais    "
				+     "\n egalement les diffèrentes cases explorées durant l'algorithme."
				+ "\n\n Utilisation \n"
				+ " \n Bouton Next  pour voir un déplacement "
				+ "\n Bouton  Cycle pour voir directement le résultat"
				+ "\n Bouton Quit pour quitter l'application"
				+ "\n\n Menu \n"
				+ "\n Load : Pour charger un labyrinthe"
				+ "\n Reset : Pour remettre le labyrinthe initial"
				+ "\n Quit : Pour quitter l'application"
				+ "\n\n Algorithme\n"
				+ "\n Cout Uniforme : Execute l'algorithme de recherche par Cout Uniforme"
				+ "\n Meilleur d'Abord : Execute l'algorithme du Meilleur d'Abord"
				+ "\n AStar : Execute l'algorithme A*";
		JTextArea resume = new JTextArea(text,5,5);
	
		Box vbox = new Box(BoxLayout.Y_AXIS);
		vbox.add(Box.createHorizontalGlue());
		quit_b = new JButton("Quit");
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
