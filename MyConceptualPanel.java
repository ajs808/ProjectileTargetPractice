//MyConceptualPanel.java
//Panel that gives user a conceptual question and checks if it is correct
//reads in problems from InfoBank.txt

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

// Events & Listeners for Components and JPPanels
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;

// Layouts and Graphics
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Image;
import java.awt.FlowLayout;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class MyConceptualPanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private JLabel gameLabel; //title label
    private Font font, font2; //font used
    private JButton submitButton; //button to submit answer
    private JLabel titleLabel; //JLabel for panel title
    private int problemNumberRand; //random problem chooser
    //strings to hold current problem, answer, and explanation for problem
    private String currentProblem, currentAnswer, currentExplanation;
    // booleans to determine whether user tried to press button, whether answer is wron
    private boolean buttonTried, answerWrong;
    private JTextField answerField; //takes in user's answer
    private Scanner fileInput; //Scanner to read in from InfoBank.txt
    
    public MyConceptualPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //initialize vars
        problemNumberRand = 1;
        currentProblem = new String("");
        currentAnswer = new String("");
        currentExplanation  = new String("");
        buttonTried = false;
        answerWrong = false;
        
        getProblem(); // get problem, answer, explanation from text file to be displayed
        
        //instantiate objects
        font = new Font("Arial", Font.BOLD, 70);
        font2 = new Font("Arial", Font.BOLD, 20);
        titleLabel = new JLabel("Conceptual Question");
        answerField = new JTextField(30);
        submitButton = new JButton("Submit");
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1000,100);
        fileInput = null;
        
        //set fonts and layouts
        titleLabel.setFont(font);
        submitButton.setFont(font2);
        setLayout(fl);
        
        //add action listeners
        answerField.addActionListener(this);
        submitButton.addActionListener(this);
        
        //add components to panel
        add(titleLabel);
        add(answerField);
        add(submitButton);
    }
    
    
    //listeners for buttons, menus, textfields, and timer
    public void actionPerformed(ActionEvent evt)
    {
        //if answer is entered
        if(evt.getSource() == answerField ||
            evt.getSource() == submitButton)
        {
            //if correct
            if(answerField.getText().equalsIgnoreCase(currentAnswer) )
            {
                answerWrong = false;
                buttonTried = false;
                //get new problem
                getProblem();
                repaint();
                //add to score
                if(pcp.getVars(2) == 0)
                    pcp.setVars(1, pcp.getVars(1)+50);
                //show correct panel
                cl.show(pcp, "CorrectPanel");
            }
            // if wrong
            else
            {
                answerWrong = true;
                //lose a life
                pcp.setVars(0,pcp.getVars(0)-1);
                pcp.setVars(2, 1);
                repaint();
            }
        }
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(150,200,1100,50);
        g.setColor(Color.BLACK);
        g.drawString(currentProblem, 160,215);
        //show correct answer and explanation if answer is wrong
        if(answerWrong)
        {
            g.drawString(currentExplanation, 160, 360);
            g.drawString("Enter the correct answer to continue", 160, 380);
        }
    }
    
    //method used to read in a random problem, answer, and explanation from InfoBank.txt
    public void getProblem()
    {
        String bankFileName = new String("InfoBank.txt");
        
        //try-catch to open InfoBank.txt
         File problemFile = new File(bankFileName);
         try
         {
             fileInput = new Scanner(problemFile);
         }
         catch (FileNotFoundException e)
         {
             System.err.printf("ERROR: File could not be opened");
            System.exit(1);
         }
        
         //choose random problem number
         problemNumberRand = (int)(Math.random()*15+1 );
         System.out.println("\n\n\n\n"+"\n\n\n\n");
         String currentLine = new String("1");
        
        //scan through InfoBank.txt to get corresponding question, answer, explanation
         while(fileInput.hasNext() )
         {
            currentLine = new String(fileInput.nextLine() );
            if(currentLine.equals(Integer.toString(problemNumberRand) ) )
            {
                //set problem, answer, and explanation
                System.out.println("test success");
                currentProblem = fileInput.nextLine();
                fileInput.nextLine();
                currentAnswer = fileInput.nextLine();
                fileInput.nextLine();
                currentExplanation = fileInput.nextLine();
                
                System.out.println(currentProblem);
                System.out.println(currentAnswer);
                System.out.println(currentExplanation);
            }
                
         }
        
         fileInput.nextLine();
     }
    
}
