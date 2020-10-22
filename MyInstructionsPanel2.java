//MyInstructionsPanel2.java
//Continuation of MyInstructionsPanel.java

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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

class MyInstructionsPanel2 extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JLabel instructionsLabel; //title label
    private Font font; //font
    private JButton menuButton, gameButton;// buttons to go to menu and game
    private JTextArea instructionsText; //textarea to hold instructions
    
    public MyInstructionsPanel2(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //instantiate and add components/listeners
        menuButton = new JButton("Menu");
        gameButton = new JButton("To Game");
        instructionsLabel = new JLabel("Instructions Cont.");
        instructionsText = new JTextArea("To solve for "
            +"y-displacement, you can use this equation:         "
            +"y=v*t*sin(theta)+(1/2)*a*t^2       (write this down!)\nwhere a=g=-9.8m/s^2\n\n"
            +"This equation can be rearranged to solve for the angle in this game\n\n"
            +"In the medium difficulty mode, acceleration a is no longer -9.8m/s^2, as you "
            +"must take the wind into account and find the net acceleration.\n\n" 
            +"In the hard difficulty mode, you are given maximum kinetic energy(KE) instead "
            +"of initial velocity.\n\n"
            +"To solve this, you can use the equation:       KE=(1/2)*m*v^2\n"
            +"where m is the mass of the projectile in kilograms(kg), and v is initial "
            +"velocity.\n\n\n"
            +"In this game, you are given time(t) and initial velocity(v), unless "
            +"you are playing on hard mode, in which case you are given kinetic energy(KE) "
            +"instead of initial velocity.\n\n"
            +"Since the projectile starts and ends at the same height, change in "
            +"height(y) is 0.\n\n"
            +"Using this information, you can solve for the correct angle to hit the "
            +"target.\n\n"
            +"The objective of this game is to score as many points as possible. You can "
            +"increase your score by decreasing the number of lives, or increasing the "
            +"difficulty in settings.\n\n\n\n"
            +"Good luck, and have fun!", 40, 100);
        font = new Font("Arial", Font.BOLD, 30);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        
        instructionsLabel.setFont(font);
        instructionsText.setEditable(false);
        setLayout(fl);
        
        menuButton.addActionListener(this);
        gameButton.addActionListener(this);
        
        add(instructionsLabel);
        add(instructionsText);
        add(menuButton);
        add(gameButton);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
    
    //listener for two buttons
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == menuButton)
            cl.show(pcp, "HomeMenuPanel");
        else if(evt.getSource() == gameButton)
            cl.show(pcp, "GamePanel");
        
    }
    
}
