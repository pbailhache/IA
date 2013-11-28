import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Menu_Haut extends JMenuBar implements ChangeListener{
	private JMenu File, Help ;
	private JMenuItem quit,load, about, algorithme, reset ;
	private Popup popup ;
	private Box box ;
	private Timer timer ;
    private Boolean timer_on = false ;
    private JButton quit_b ;
    private Creation frame ;

	public Menu_Haut(final Creation frame){
		this.frame = frame ;
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
		algorithme = new JMenuItem("Drawing-Pen help");
		Help.add(algorithme);
		algorithme.addActionListener ( new ActionListener () {
	                @Override
			public void actionPerformed ( ActionEvent event ){
			    addPopUpHelp();
			}});
		Help.insertSeparator(1);
		about = new JMenuItem("about..");
		Help.add(about);
		about.addActionListener ( new ActionListener () {
	                @Override
			public void actionPerformed ( ActionEvent event ){
	                    try {
	                        addPopUpAbout();
	                    } catch (InterruptedException ex) {                     
	                    }
			}});
		this.add(Box.createHorizontalGlue());
		this.add(Help);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	// Ce popup n'étant pas intéréssant, nous ne le laissons ouvert que 3s
    public void addPopUpAbout() throws InterruptedException{
        if ( timer_on ){
            timer.stop();
        }
	JLabel description = new JLabel("Nom du logiciel");
	JLabel serie = new JLabel("N° de série : 0123456789");

	Box vbox = new Box(BoxLayout.Y_AXIS);
	vbox.add(Box.createRigidArea( new Dimension (20,20)));
	vbox.add(description);
       	vbox.add(Box.createRigidArea( new Dimension (20,20)));
	vbox.add(serie);
	vbox.add(Box.createRigidArea( new Dimension (20,20)));
	

	PopupFactory factory = PopupFactory.getSharedInstance();
	popup = factory.getPopup(this, vbox,300, 300);
	
	popup.show();

	ActionListener hider = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popup.hide();
            }
	};
        
	// Hide popup in 3 seconds
        timer = new Timer(3000,hider);
        timer.start();
        timer_on = true ;
    }

    // popup pour afficher à quoi sert, et comment se servir du logiciel
    public void addPopUpHelp() {

        if ( timer_on ){
            timer.stop();
        }       
	String text = "Ce programme nous permet de tester des algorithmes de recherche"
			+     " dans un labyrinthe, pour nous montrer le plus court chemin mais    "
			+     " egalement les diffèrentes cases explorées durant l'algorithme."
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
	popup = factory.getPopup(this, vbox,200, 200);
	
	popup.show();
        }
}
