//MyInstructionsPanel.java
//Panel that offers user basic physics knowledge as well as instructions on how to 
// play the game

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;


// Events & Listeners for Components and JPPanels
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

// Layouts and Graphics
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;

class MyInstructionsPanel extends JPanel implements ActionListener, MouseListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JLabel instructionsLabel; //title label
    private Font font; // font
    private JButton menuButton, gameButton;// buttons to go to menu and game
    private JTextArea instructionsText; //textarea to hold instructions
    
    public MyInstructionsPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //instantiate and add components
        menuButton = new JButton("Menu");
        gameButton = new JButton("To Game");
        instructionsLabel = new JLabel("Instructions (click to continue)");
        instructionsText = new JTextArea("This "
            +"game is designed for people who have completed a class in Algebra 2/Trig "
            +"with a high grade, or any higher class of math. \nThis game can be played with "
            +"little or no physics knowledge, as all equations are stated.\n\n"
            +"These problems can be solved with algebra and basic trigonometry.\n\n"
            +"Projectile motion is defined as the "
            +"form of motion that an object experiences when thrown near "
            +"the earth's surface, under ONLY the force of gravity, ignoring air "
            +"resistance.\n\n" 
            +"An example of this is throwing a ball, or shooting a cannnonball.\n\n"
            +"Displacement(d, x, or y) is a measure of distance, and is a vector quantity, meaning it "
            +"has both magnitude and direction (e.g. 20m [up]). It is measured in meters(m)\n\n"
            +"Velocity(v) is the rate at which an object moves, and is equal to displacement" 
            +"divided by time (v=d/t). Velocity is a vector quantity, meaning "
            +"it has both magnitude and direction (e.g. 52 m/s [north]).\n\n"
            +"Speed(s) is also the rate at which an object moves, and is equal to distance "
            +"divided by time. Speed is a scalar quantity, meaning it only has "
            +"magnitude (e.g. 52 m/s). \n\n"
            +"Both speed and velocity are measured in meters per second (m/s).\n\n"
            +"Acceleration(a) is the rate at which velocity changes, and is equal to change "
            +"in velocity divided by time(deltaV/t). Acceleration is also a vector quantity "
            +"(e.g. 10 m/s^2 [left]).\n\n"
            +"Acceleration is measured in meters per second per second (m/s^2).\n\n"
            +"The force of gravity accelerates objects at a rate of 9.8 meters per second "
            +"squared; Acceleration g = -9.8m/s^2"
            +"(m/s^2).\n\n"
            +"In projectile motion, horizontal and vertical motion are completely separate. "
            +"Horizontal velocity remains constant, but vertical velocity changes at a "
            +"constant rate (acceleration due to gravity).\n\n"
            +"The initial vertical velocity is equal to V*sin(theta), theta being the angle "
            +"of launch. The initial horizontal velocity is equal to V*cos(theta).\n\n"
            +"Time(t) is measured in seconds (s).\n\n"
            +"To solve for x-displacement, you can use the equation d=rt, or for projectile "
            +"motion, x=v*cos(theta)*t\n\n", 40, 100);
        font = new Font("Arial", Font.BOLD, 30);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        
        instructionsLabel.setFont(font);
        instructionsText.setEditable(false);
        setLayout(fl);
        
        //add listeners
        menuButton.addActionListener(this);
        gameButton.addActionListener(this);
        
        instructionsText.addMouseListener(this);
        
        add(instructionsLabel);
        add(instructionsText);
        add(menuButton);
        add(gameButton);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
    
    //listeners for two buttons
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == menuButton)
            cl.show(pcp, "HomeMenuPanel");
        else if(evt.getSource() == gameButton)
            cl.show(pcp, "GamePanel");
        
    }
    
    public void mousePressed(MouseEvent evt)
    {
        // Select the next panel on the stack of panels
        if(evt.getSource() == instructionsText);    
            cl.show(pcp, "InstructionsPanel2");
    }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    
}
