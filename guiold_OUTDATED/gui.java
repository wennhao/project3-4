import javax.swing.*;
import java.awt.*;

class gui {
    public static void main(String[] args) {
	
	    //Initialiseer een nieuwe JFrame en configureer de basics,
	    //dus size en de ON_CLOSE operation.
        JFrame frame = new JFrame("Workshop frame");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	
	    //Aanmaak van drie JPanels
        JPanel flowLayoutPanel = new JPanel();
        JPanel boxLayoutPanel = new JPanel();
        JPanel gridLayoutPanel = new JPanel();
	
	    //Zet voor iedere panel een aparte layout manager;
	    //FlowLayout voor links naar rechts, BoxLayout voor x-as of y-as, GridLayout voor raster.
	    //Let op dat sommige layout managers dus parameters mee moeten krijgen in hun constructors.
        flowLayoutPanel.setLayout(new FlowLayout());
        boxLayoutPanel.setLayout(new BoxLayout(boxLayoutPanel, BoxLayout.Y_AXIS));
        gridLayoutPanel.setLayout(new GridLayout(3, 2));
	
	    //Drie arrays van knoppen, 1 voor iedere panel.
        JButton[] buttons1 = {
        new JButton("Button1"),
        new JButton("Button2"),
        new JButton("Button3"),
        new JButton("Button4"),
        new JButton("Button5"),
        new JButton("Button6")
    };
        JButton[] buttons2 = {
        new JButton("Button1"),
        new JButton("Button2"),
        new JButton("Button3"),
        new JButton("Button4"),
        new JButton("Button5"),
        new JButton("Button6")
    };
        JButton[] buttons3 = {
        new JButton("Button1"),
        new JButton("Button2"),
        new JButton("Button3"),
        new JButton("Button4"),
        new JButton("Button5"),
        new JButton("Button6")
    };
	
	//Knoppen toevoegen aan de panels.
    for (JButton b : buttons1){
        flowLayoutPanel.add(b);
    }
    for (JButton b : buttons2){
        boxLayoutPanel.add(b);
    }
    for (JButton b : buttons3){
        gridLayoutPanel.add(b);
    }
	
	    //Maak een main panel aan om alle andere panels in onder te brengen, inclusief layout manager.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	
	    //Voeg de layout panels toe aan de main panel. Let op, volgorde maakt uit.
        mainPanel.add(flowLayoutPanel);
        mainPanel.add(boxLayoutPanel);
        mainPanel.add(gridLayoutPanel);
	
	    //Voeg mainpanel toe aan de JFrame en maak de JFrame visible.
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}