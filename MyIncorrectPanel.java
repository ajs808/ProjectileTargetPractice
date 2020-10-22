//MyIncorrectPanel.java
//Panel to be displayed when user gets answer incorrect

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


class MyIncorrectPanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JButton gameButton; //button to return to game
    private JLabel incorrectPanelLabel; //title label
    private JTextArea explanationArea; //text area for explanation of how to do problem
    private Font font; // font
    
    public MyIncorrectPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //instantiate and add components
        gameButton = new JButton("Back to Game");
        incorrectPanelLabel = new JLabel("Incorrect!");
        explanationArea = new JTextArea(" Explanation: \n" 
            +"You are given time(t) and initial velocity(v), unless "
            +"you are playing on hard mode, in which case you are given kinetic energy(KE) "
            +"instead of initial velocity.\n\n"
            +"Since the projectile starts and ends at the same height, change in "
            +"height(y) is 0.\n\n"
            +"Using this information, you can solve for the correct angle to hit the "
            +"target.\n\n"
            +"To solve this, this equation:     y=v*t*sin(theta) + (1/2)*a*t^2\n\n"
            +"can be rearranged to:     theta = arccos( (-a*t)/(2*v) )\n\n"
            +"If you are playing on medium difficulty, remember that acceleration "
            +"is NOT equal to gravity anymore, you must add or subtract acceleration "
            +"from the wind.\n\n"
            +"If you are playing on hard difficulty, make sure to solve for initial "
            +"velocity using the kinetic energy equation:     KE = (1/2)*m*v^2\n\n\n\n"
            +"Try another problem!", 20, 100);
        font = new Font("Arial", Font.BOLD,70);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        
        incorrectPanelLabel.setFont(font);
        explanationArea.setEditable(false);
        setLayout(fl);
        
        gameButton.addActionListener(this);
        
        add(incorrectPanelLabel);
        add(gameButton);
        add(explanationArea);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
    
    //listener for game button
    public void actionPerformed(ActionEvent evt)
    {
        cl.show(pcp, "GamePanel");
    }
}
