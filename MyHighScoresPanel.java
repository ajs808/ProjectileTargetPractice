//MyHighScoresPanel.java
//Panel to display list of top scorers and their scores

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import javax.swing.JLabel;


// Events & Listeners for Components and JPPanels
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

// Layouts and Graphics
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;


class MyHighScoresPanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JButton homeButton, refreshButton; //button to go to main menu
    private JLabel highScoresLabel; //title label
    private Font font, scoreFont; //font
    private Scanner fileInput; //scanner to read in file
    private String scoreLine;  //string to keep track of what line is being read
    private int counter, fileLength; //counter to determine current line number
    private String[] highScoresList, highScoresNames; //arrays to hold high scorers
    private int[] highScoresValues; //array to hold score values
    
    public MyHighScoresPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //instantiate and add components
        homeButton = new JButton("Menu");
        refreshButton = new JButton("Refresh Scores");
        highScoresLabel = new JLabel("High Scores");
        font = new Font("Arial", Font.BOLD, 70);
        scoreFont = new Font("Arial", Font.BOLD, 30);
        fl = new FlowLayout(FlowLayout.CENTER, 1000, 10);
        fileInput = null;
        scoreLine = new String("");
        counter = 0;
        highScoresList = new String[20];
        highScoresNames = new String[20];
        highScoresValues = new int[20];
        for(int i = 0; i<20; i++)
        {
             highScoresList[i] = new String("");
             highScoresNames[i] = new String("");
             highScoresValues[i] = 0;
        }
       
        //set font and layout
        highScoresLabel.setFont(font);
        setLayout(fl);
        
        //add listeners
        homeButton.addActionListener(this);
        refreshButton.addActionListener(this);
        
        //add components
        add(highScoresLabel);
        add(homeButton);
        add(refreshButton);
        
        highScoresList = new String[20];
        getScores();
        sortScores();
        
    }
    
    public void paintComponent(Graphics g)
    {
        
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(400,170,600,500);
        g.setColor(Color.BLACK);
        g.setFont(scoreFont);
        
        //paint high scores to screen
        int h = 0;
        for(int i = highScoresNames.length-1; i > highScoresNames.length-6 ; i--)
        {
            g.drawString(highScoresNames[i], 410, 200 + 50*h);
            h++;   
        }
    }
    
    //listener for home button
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == homeButton)
            cl.show(pcp, "HomeMenuPanel");
        else if(evt.getSource() == refreshButton)
        {
            //reads in from HighScores.txt and repaints
            System.out.println("refresh");
            highScoresList = new String[20];
            getScores();
            sortScores();
            repaint();
        }
    }
    
    //reads in high scores from HighScores.txt
    public void getScores()
    {
        String scoresFileName = new String("HighScores.txt");
        counter = 0;
         File problemFile = new File(scoresFileName);
         
         //try-catch to read from HighScores.txt
         try
         {
             fileInput = new Scanner(problemFile);
         }
         catch (FileNotFoundException e)
         {
             System.err.printf("ERROR: File could not be opened");
            System.exit(1);
         }
         System.out.println("\n\n\n\n"+"\n\n\n\n");
         String currentLine = new String("");
        
         //reads in from file and stores scores in array
         while(fileInput.hasNext() && counter < highScoresList.length)
         {
            scoreLine = fileInput.nextLine();
            if(scoreLine.length() != 0 )
            {
                System.out.println(scoreLine);
                   highScoresList[counter] = scoreLine;
                counter++;
            }

         }
         System.out.println("LENGTH IS: " + counter);
         
         repaint();
         fileInput.nextLine();
     }
     
     //sorts scores in the array
     public void sortScores()
     {
        String currentName = new String("");
        String currentValueString = new String("");
        int currentValue = 0;
        highScoresNames = new String[counter];
        highScoresValues = new int[counter];
        
        //clears array
        for(int i = 0; i<counter; i++)
        {
             highScoresNames[i] = new String("");
             highScoresValues[i] = 0;
        }
        
        //sets array to match another array
        for(int a = 0; a < counter; a++)
        {
            System.out.println(highScoresList[a] + "\n\n");
            currentName = new String(highScoresList[a] );
            currentName = new String(currentName.trim() );
            
            currentValueString = new String(highScoresList[a].substring(highScoresList[a].indexOf('-')+1) );
            currentValueString = new String(currentValueString.trim() );
            
            currentValue = Integer.parseInt(currentValueString);
            
            highScoresNames[a] = new String(currentName);
            highScoresValues[a] = currentValue;
        }
        
        //sorts array based on score
        for (int i = 0 ; i < highScoresValues.length-1; i++)
           {
              for (int j = 0; j < highScoresValues.length-i-1; j++)
             {
                 if (highScoresValues[j] > highScoresValues[j+1])
                 {
                      int dummy = highScoresValues[j+1];
                      String dummyString = new String(highScoresNames[j+1]);
                      highScoresValues[j+1] = highScoresValues[j];
                      highScoresNames[j+1] = highScoresNames[j];
                      
                      highScoresValues[j] = dummy;
                      highScoresNames[j] = new String(dummyString);
                   }
               }
           }
           
           for(int q = 0; q < highScoresNames.length; q++)
           {
            System.out.println("SORTED: " + highScoresNames[q]);
           }
     }
}
