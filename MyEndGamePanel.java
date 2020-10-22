//MyEndGamePanel.java
//Panel to be shown when user loses all lives and game is over
//allows user to see their final score with bonuses, and enter their name

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

// Events & Listeners for Components and JPPanels
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;

// Layouts and Graphics
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;

// File reading 
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

class MyEndGamePanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JButton homeButton, submitButton; //button to go to main menu
    private JLabel highScoresLabel; //title label
    private JTextField nameField, scoreField; //fields for name and score
    private Font font; //font
    //strings for different parts of each high score entry
    private String entryName, entryScore, entry;
    private PrintWriter output; //file to write to
    
    public MyEndGamePanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //instantiate and add components
        homeButton = new JButton("Menu");
        submitButton = new JButton("Submit");
        highScoresLabel = new JLabel("Game Over");
        nameField = new JTextField("Enter Name", 30);
        scoreField = new JTextField("Enter Score", 30);
        font = new Font("Arial", Font.BOLD, 70);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        output = null;
        
        //set font and layout
        highScoresLabel.setFont(font);
        setLayout(fl);
        
        //add listeners
        homeButton.addActionListener(this);
        submitButton.addActionListener(this);
        
        //add components
        add(highScoresLabel);
        add(nameField);
        add(submitButton);
        add(homeButton);
    }
    
    public void paintComponent(Graphics g)
    {
        //paint scores to screen after game is over
        super.paintComponent(g);
        g.drawString("Your score: " +
            Integer.toString(pcp.getVars(1)), 600, 250);
        g.drawString("Bonus lives score: " +  
            Integer.toString(10*(10-pcp.getVars(4) ) ), 600, 270);
        g.drawString("Difficulty Multiplier: " +
            Integer.toString(pcp.getVars(3) ) + "x", 600, 290);
        
        //change score
        int scoreHolder = pcp.getVars(1);
        //scorebonus for number of lives
        scoreHolder += (10*(10-pcp.getVars(4) ) );
        //score bonus for difficulty
        scoreHolder *= pcp.getVars(3);
        pcp.setVars(1, scoreHolder);
        
        g.drawString("Final score: " +
            Integer.toString(scoreHolder), 600, 330 );
        
    }
    
    //listener for home button
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == homeButton)
            cl.show(pcp, "HomeMenuPanel");
        else if(evt.getSource() == submitButton)
        {
            System.out.println("submitted");
            entryName = nameField.getText();
            entryScore = Integer.toString(pcp.getVars(1) );
            System.out.println(entryName);
            System.out.println(entryScore);
            cl.show(pcp, "HomeMenuPanel");
            writeScores();
        }
    }
    
    //writes scores to HighScores.txt using input from JTextFields
    public void writeScores()
    {
        String fileName = new String("HighScores.txt");
        File outFile = new File(fileName);
        
        //try-catch to append to HighScores.txt
        try
        {
            output = new PrintWriter(new FileWriter(outFile, true) );
        }
        catch (IOException e)
        {
            System.err.printf("ERROR: Cannot find/open file %s.\n\n\n",
                fileName);
            System.exit(1);
        }
        //adds player's name - score
        entry = new String(entryName + " - " + entryScore);
        output.println(entry + "\n");
        
        System.out.println("High Scores written\n\n\n");
        output.close();
    }
}
