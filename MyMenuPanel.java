//MyMenuPanel.java
//Main menu for game
//Panel that is first shown when player runs the program

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

// Events & Listeners for Components and JPPanels
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;

// Layouts and Graphics
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class MyMenuPanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    //buttons to go to instructions panel, high scores panel, game panel, and settings
    private JButton instructionsButton, highScoresButton, startButton,
        settingsButton;
    private JLabel menuLabel, subLabel; //title label
    private Font font, subFont; //font
    private int counter;
    private String pictName;
    private Image image;
    
    public MyMenuPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        counter = 1;
        
        //instantiate and add all components/listeners
        instructionsButton = new JButton("Instructions");
        highScoresButton = new JButton("High Scores");
        startButton = new JButton("New Game");
        settingsButton = new JButton("Settings");
        menuLabel = new JLabel("Projectile Target Practice");
        subLabel = new JLabel("This game is designed to be played " +
            "with a pencil, paper, and calculator!");
        font = new Font("Arial", Font.BOLD, 70);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        
        image = null;
        pictName = new String("Trajectory.png");
        
        menuLabel.setFont(font);
        setLayout(fl);
        
        font = new Font("Arial", Font.BOLD, 40);
        subFont = new Font("Arial", Font.PLAIN, 20);
        instructionsButton.setFont(font);
        highScoresButton.setFont(font);
        startButton.setFont(font);
        settingsButton.setFont(font);
        
        instructionsButton.addActionListener(this);
        highScoresButton.addActionListener(this);
        startButton.addActionListener(this);
        settingsButton.addActionListener(this);
    
        add(menuLabel);
        add(subLabel);
        add(startButton);
        add(instructionsButton);
        add(highScoresButton);
        add(settingsButton);
        
        loadImage();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //int testNumber = pcp.getVars(0);
        g.setColor(Color.BLACK);
        g.drawImage(image,300,400,800,300,this);
    }
    
    // listeners for each button
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == instructionsButton)
            cl.show(pcp, "InstructionsPanel");
        else if(evt.getSource() == highScoresButton)
            cl.show(pcp, "HighScoresPanel");
        else if(evt.getSource() == startButton)
        {
            cl.show(pcp, "GamePanel");
            //set score to 0
            pcp.setVars(1,0);
            //set current number of lives to # of lives chosen in settings
            pcp.setVars(0, pcp.getVars(4));
        }
        else if(evt.getSource() == settingsButton)
            cl.show(pcp, "SettingsPanel");
    }
    
    //loads trajectory image
    public void loadImage()
    {
        File pictFile = new File(pictName);
        try
        {
            image = ImageIO.read(pictFile);
        }
        catch(IOException e)
        {
            System.err.println("\n\n" + pictName + " can't be found.\n\n");
            
            e.printStackTrace();
        }    
    }
}
