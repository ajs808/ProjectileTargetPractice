//MyGamePanel.java
//Main game panel
//Calculation-based problems that shows an animation of the projectile
//problems are randomly generated, difficulty setting is taken into account

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// Events & Listeners for Components and JPPanels
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;

// Layouts and Graphics
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Image;

// File reading and writing
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;

class MyGamePanel extends JPanel implements ActionListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private JLabel gameLabel; //title label
    private Font font, scoreFont, defaultFont; //font used
    private JMenuItem quitItem, instructionsItem; //options in menu
    private JMenu menu; //menu to go to instructions or home
    private JMenuBar menuBar; //menu bar to hold menu
    private JButton submitButton; //button to submit answer
    private JLabel angleLabel; //labels for JTextFields
    // integer to determine what stage the animation is in, x position of projectile, y
    // position of projectile, x velocity, y velocity, initial velocity to be set by game,
    // increment essentially functions as time(goes up every time timer performs action
    private int correct,x,y,xVel, yVel, initVel, initXVel, increment;
    // randomly chosen problem, initial angle in degrees, number of lives, current score,
    // chosen difficulty, accel. due to wind(in medium difficulty), mass of projectile
    // (for hard difficulty), and kinetic energy of ball (also hard difficulty)
    private int problemNumberRand, initAngleDegs, numberLives, 
        currentScore, difficulty, windAccel, mass, energy;
    // initial angle inputted by user, correct angle set by game, correctDistance, time
    private double initAngleRads, correctAngle, chosenDistance, time;  
    //booleans to check what stage the animation/level is in
    private boolean animationStart, animationRunning, answerIsCorrect;
    private Timer projectileTimer; //timer for drawing projectile
    //input boxes for entering initial velocity(only for testing purposes(, and
    // box for entering angle
    private JTextField initVelField, initAngleField;
    private Image image; // image of target
    private String pictName; //name of image file to be loaded
    private Scanner fileInput; //Scanner for InfoBank.txt
    
    private ProjectileCardPanel pcpGetterSetter;
    
    public MyGamePanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(new Color(116, 201, 247));
        
        pcp = pcpIn;
        cl = clIn;
        
        //getProblem(); // get problem, answer, explanation from text file to be displayed
        
        //instantiate objects
        quitItem = new JMenuItem("Quit Game");
        instructionsItem = new JMenuItem("Instructions");
        submitButton = new JButton("Launch!");
        menu = new JMenu("Menu");
        menuBar = new JMenuBar();
        gameLabel = new JLabel("Playing Game");
        //initVelField = new JTextField(20);
        initAngleField = new JTextField(20);
        angleLabel = new JLabel("Set Initial Angle:");
        font = new Font("Arial", Font.PLAIN, 30);
        scoreFont = new Font("Arial", Font.BOLD, 70);
        defaultFont = new Font("Arial", Font.PLAIN, 12);
        fileInput = null;
        image = null;
        pictName = new String("Target.jpg");
        
        //initialize vars
        projectileTimer = new Timer(25, this);
        correct = 0;
        increment = -10;
        x=100;
        y=500;
        xVel = 10;
        yVel = -30;
        initVel = 42;
        problemNumberRand = 1;
        numberLives = -1;
        initAngleRads = (Math.PI/3);
        correctAngle = (Math.PI/12);
        animationStart = false;
        animationRunning = false;
        answerIsCorrect = false;
        currentScore = 0;
        difficulty = 1;
        windAccel = 0;
        initXVel = 0;
        time = 0.0;
        mass = 0;
        energy =0;
        
        // Get the Image from a file
        loadImage();
        gameLabel.setFont(font);
        
        setLayout(null);
        
        //add all listeners, add menu components, add all components to panel, set size
        // and location of components
        quitItem.addActionListener(this);
        instructionsItem.addActionListener(this);
        submitButton.addActionListener(this);
        initAngleField.addActionListener(this);
        
        //add menu parts
        menu.add(instructionsItem);
        menu.add(quitItem);
        menuBar.add(menu);
 
         //add all components
        add(menuBar);
        add(gameLabel);
        add(submitButton);
        add(angleLabel);
        add(initAngleField);
        
        //choose size and position of components
        menuBar.setBounds(20,20,50,20);
        submitButton.setBounds(400,600,100,70);
        gameLabel.setBounds(1100,20,600,50);
        angleLabel.setBounds(50,625,200,20);
        initAngleField.setBounds(250,625,100,20);
        
        writeProblem();
    }
    
    // used to draw cannon, target, and projectile
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        pcpGetterSetter = new ProjectileCardPanel();
        
        //paint ground, sky, cannon, target, information box
        g.setFont(defaultFont);
        g.setColor(Color.BLACK);
        g.fillRect(45,495,105,30);
        g.drawImage(image,75+(int)(2.586*chosenDistance),500,50,25,this);
        g.drawLine(0, 525, 1500, 525);
        g.setColor(Color.GREEN);
        g.fillRect(0,525,1500,600);
        g.setColor(Color.WHITE);
        g.fillRect(500,50,350,150);
        g.setColor(Color.BLACK);
        g.drawString("Solve for the angle of the cannon to hit the target",
            200, 200);
        g.drawString("Time: " + time, 500, 75);
        g.drawString("Set the angle between 0 and 90(round down to nearest integer)", 500,90);
        
        //get number of lives, current score, and current difficulty
        numberLives = pcp.getVars(0);
        currentScore = pcp.getVars(1);
        difficulty = pcp.getVars(3);
        
        System.out.println("LIVES" + numberLives);
        
        System.out.println(pcp.getVars(0) );
        
        g.drawString("Lives left: " + Integer.toString(numberLives),
            500, 160);
        g.setFont(scoreFont);
        g.drawString("Score: " + Integer.toString(currentScore), 700, 670);
        
        g.setFont(defaultFont);
        //for easy difficulty
        if(difficulty == 1)
        {    
            //normal information
            g.drawString("Initial Velocity: " + initVel, 500,60);
            g.drawString("Difficulty: easy", 500, 190);
            windAccel = 0;
        }
        //for medium difficulty
        else if(difficulty == 2)
        {
            g.drawString("Difficulty: medium", 500, 190);
            //wind is added for medium difficulty
            if(windAccel>0)
                g.drawString("A magic wind blows the ball upward at " +windAccel +"m/s^2",500, 105);
            else if(windAccel<0)
                g.drawString("A magic wind blows the ball downward at " +Math.abs(windAccel)
                 +"m/s^2",500, 105);
            else if(windAccel == 0)
                g.drawString("There is no wind", 500, 105);
        }
        //for hard difficulty
        else if(difficulty == 3)
        {
            windAccel = 0;
            g.drawString("Difficulty: hard", 500, 190);
            //mass and kinetic energy are given instead of velocity
            if(mass == 0)
            {
                g.drawString("First round, next problem will use energy", 500, 105);
                g.drawString("Initial Velocity: " + initVel, 500,60);
            }
            else
            {
                g.drawString("Mass of projectile: " + mass + "kg", 500, 105);
                g.drawString("Maximum kinetic energy: " + energy + "J", 500, 120);
            }
        }
        System.out.println("\n\n\n\n\n\n\n"+correctAngle);
        
        if(animationStart && correct == 1)
        {
            if( (y <525) && animationRunning)
            {
                //draws circle in path of projectile
                increment+=1;
                x+=(int)(xVel/2);
                y+=yVel;
                //yVel+=2;
                yVel+=(int)((9.8-windAccel)/4.9);
                
            }
            else
            {
                animationRunning = false;
                animationStart = false;
                projectileTimer.stop();
                //determines what panel to show after animation
                if(answerIsCorrect)
                {
                    cl.show(pcp, "CorrectPanel");
                    writeProblem();
                }
                else if(!answerIsCorrect)
                {
                    if(numberLives>1)
                    {
                        cl.show(pcp, "IncorrectPanel");
                        writeProblem();
                    }
                    else
                        cl.show(pcp, "EndGamePanel");
                    pcp.setVars(0,pcp.getVars(0)-1);
                }
            }
            
            //cover information box
            g.setColor(new Color(116, 201, 247));
            g.fillRect(500,50,350,150);
            g.setColor(Color.WHITE);
            g.fillOval(x,y,10,10);
        
        }
    }
    
    //listeners for buttons, menus, textfields, and timer
    public void actionPerformed(ActionEvent evt)
    {

        if(evt.getSource() == instructionsItem)
        {
            cl.show(pcp, "InstructionsPanel");
        }
        else if(evt.getSource() == quitItem)
            cl.show(pcp, "HomeMenuPanel");
        
        else if(evt.getSource() == submitButton &&
            (Integer.parseInt(initAngleField.getText())>=0 &&
             Integer.parseInt(initAngleField.getText())<=90))
        {
            correct = 1;
            increment = -11;
            x=100;
            y=500;
            
            //entered angle
            double entered = 0;
            entered = Double.parseDouble(initAngleField.getText() );
            //determines if answer is correct or not
            
            initAngleDegs = (int)entered;
            initAngleRads = (double)(Math.toRadians(entered));

            System.out.println(initAngleRads);
            System.out.println(initAngleDegs);
            
            //checks if answer is within +1 or -1 of correct answer OR 
            // +1 or -1 of 90-correct answer, as complementary angles yield the
            // same result
            if(initAngleDegs == correctAngle || initAngleDegs == (90-correctAngle)
                || initAngleDegs == correctAngle+1
                || initAngleDegs == correctAngle-1
                || initAngleDegs == (89-correctAngle)
                || initAngleDegs == (91-correctAngle) )
            {
                answerIsCorrect = true;
            }
            
            else
                answerIsCorrect = false;
            
            //determines initial velocities of the ball based on entered angle
            xVel = (int)(initVel*(Math.cos(initAngleRads) ) );
            yVel = (int)((-1)*initVel*(Math.sin(initAngleRads) ) );

            animationRunning = true;
            projectileTimer.start();
            repaint();
            
            
        }
        
        //start animation
        else if(evt.getSource() == projectileTimer)
        {
            animationStart = true;
            animationRunning = true;
            repaint();
            System.out.println("test line");
        }
    }
    
    //reads in image file of target
    public void loadImage()
    {
        File pictFile = new File(pictName);
        //try-catch to open image of target
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
    
    //randomly generates a problem based on chosen difficulty
    public void writeProblem()
    {
        //if difficulty is easy
        if(pcp.getVars(3) == 1)
        {
            //generates random velocity and angle
            initVel = (int)(Math.random()*40+30);
            correctAngle = Math.random()*(Math.PI/3)+(Math.PI/8);
        
            //uses range equation to generate distance based on vel and angel
            chosenDistance = (Math.pow(initVel, 2)*Math.sin(2*correctAngle))/(9.8);
            //finds time
            time = (chosenDistance/(initVel*Math.cos(correctAngle) ));
            time = Math.round(time*100.0)/100.0;
            
            correctAngle = (int)(Math.toDegrees(correctAngle) );
            
            System.out.println(initVel);
            System.out.println("Correct Angle: " + correctAngle );
            System.out.println(chosenDistance);
        }
        
        //if difficulty is medium
        else if(pcp.getVars(3) == 2)
        {
            //generates random velocity, angle, and wind
            initVel = (int)(Math.random()*20+20);
            correctAngle = Math.random()*(Math.PI/3)+(Math.PI/6);
            windAccel = (int)(Math.random()*7 - 7);
            
            //uses range equation to find range
            chosenDistance = (Math.pow(initVel, 2)*Math.sin(2*correctAngle))/9.8;
            
            chosenDistance = (Math.pow(initVel, 2)*Math.sin(2*correctAngle))/(9.8-windAccel);
            //finds time
            time = (chosenDistance/(initVel*Math.cos(correctAngle) ));
            time = Math.round(time*100.0)/100.0;
            
            correctAngle = (int)(Math.toDegrees(correctAngle) );
            
            System.out.println(initVel);
            System.out.println("Correct Angle: " + correctAngle );
            System.out.println(chosenDistance);
        }
        
        //if difficulty is hard
        else if(pcp.getVars(3) == 3)
        {
            //randomly generate velocity, angle, and mass
            initVel = (int)(Math.random()*40+30);
            correctAngle = Math.random()*(Math.PI/3)+(Math.PI/8);
            mass = (int)(Math.random()*20+1);
            
            //uses range equation to get distance, solves for time
            chosenDistance = (Math.pow(initVel, 2)*Math.sin(2*correctAngle))/(9.8);
            time = (chosenDistance/(initVel*Math.cos(correctAngle) ));
            time = Math.round(time*100.0)/100.0;
            
            //calculates energy
            energy = (int)(0.5*mass*(Math.pow(initVel,2) ) );
            
            correctAngle = (int)(Math.toDegrees(correctAngle) );
            System.out.println(initVel);
            System.out.println("Correct Angle: " + correctAngle );
            System.out.println(chosenDistance);
        }
    }
}
