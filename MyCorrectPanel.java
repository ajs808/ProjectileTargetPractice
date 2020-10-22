//MyCorrectPanel.java
//Panel to be shown when user's answer is correct

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
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;


class MyCorrectPanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JButton gameButton; // button to go back to game
    private JLabel highScoresLabel; //title label
    private Font font, scoreFont; //fonts used
    private int counter; //counter to determine to show concpetual or calc-based q's
    
    public MyCorrectPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        gameButton = new JButton("Next Question");
        highScoresLabel = new JLabel("Correct!");
        font = new Font("Arial", Font.BOLD, 70);
        scoreFont = new Font("Arial", Font.PLAIN, 100);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        
        counter = 1;
        
        highScoresLabel.setFont(font);
        setLayout(fl);
        
        gameButton.addActionListener(this);
        
        add(highScoresLabel);
        add(gameButton);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setFont(scoreFont);
        //if conceptual question, add 50 points
        if(counter%2 == 0)
        {
            System.out.println("\n\n\nGETVARS " + pcp.getVars(2));        
            if(pcp.getVars(2) == 0)
                g.drawString("+50 points", 600, 300);
        }
        //if calculation-based question, add 100 points
        else
        {
            g.drawString("+100 points", 600, 300);
        }
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        //if user has no more lives
        if(pcp.getVars(0) < 1)
        {
            cl.show(pcp, "EndGamePanel");
        }
        //game alternates between conceptual and calculation questions
        else if(counter%2==0)
        {
            cl.show(pcp, "GamePanel");
            pcp.setVars(2,0);
        }
        else
        {
            cl.show(pcp, "ConceptualPanel");
            pcp.setVars(1, pcp.getVars(1) + 100);
            pcp.setVars(2,0);
        }
            
        counter++;
    }
}
