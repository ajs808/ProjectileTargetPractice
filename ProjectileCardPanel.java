//ProjectileCardPanel.java
//JPanel that holds all other panels in a card layout

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;

// Events & Listeners for Components and JPPanels
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

// Layouts and Graphics
import java.awt.GridLayout;
import java.awt.CardLayout;

class ProjectileCardPanel extends JPanel implements MouseListener
{
    // Declare CardLayout and JPanel objects
    private CardLayout cards;        //CardLayout for holder panel
    
    private MyMenuPanel menuPanel; //start panel
    private MyInstructionsPanel instructionsPanel;  //instructions panel
    private MyInstructionsPanel2 instructionsPanel2; //continuation of instructions
    private MyHighScoresPanel highScoresPanel; // high scores panel
    private MyGamePanel gamePanel;  //panel with actual gameplay
    private MySettingsPanel settingsPanel; //settings panel
    private MyCorrectPanel correctPanel;   //panel to be displayed for correct answer
    private MyIncorrectPanel incorrectPanel; // to be displayed for incorrect answer
    private MyConceptualPanel conceptualPanel; // panel to ask conceptual questions
    private MyEndGamePanel endGamePanel; //panel displayed at end of game

    //makeshift field variables
    private int numberLives;        //current number of lives(changes when answer is wrong)
    private int gameScore;            //current score
    //determines if conceptual question was right on first or second try
    private int firstTry;            
    private int difficulty;            //chosen difficulty settings
    private int numberLivesChosen;    //number of lives(does not change when answer is wrong)
    
    public ProjectileCardPanel()             // constructor
    {    
        // Initialize CardLayout and setLayout() to this panel
        //
        cards = new CardLayout();
        setLayout(cards);
        
        //initialize field vars
        numberLives = 3;
        numberLivesChosen =3;
        gameScore = 0;
        firstTry = 0;
        difficulty = 1;
        
        //create instances of all other panels
        menuPanel = new MyMenuPanel(this, cards);
        instructionsPanel = new MyInstructionsPanel(this, cards);
        instructionsPanel2 = new MyInstructionsPanel2(this, cards);
        highScoresPanel = new MyHighScoresPanel(this, cards);
        gamePanel = new MyGamePanel(this, cards);
        settingsPanel = new MySettingsPanel(this, cards);
        correctPanel = new MyCorrectPanel(this,cards);
        incorrectPanel = new MyIncorrectPanel(this,cards);
        conceptualPanel = new MyConceptualPanel(this, cards);
        endGamePanel = new MyEndGamePanel(this, cards);
        
        //add all panels to CardLayout
        add(menuPanel, "HomeMenuPanel");
        add(instructionsPanel, "InstructionsPanel");
        add(instructionsPanel2, "InstructionsPanel2");
        add(highScoresPanel, "HighScoresPanel");
        add(gamePanel, "GamePanel");
        add(settingsPanel, "SettingsPanel");
        add(correctPanel, "CorrectPanel");
        add(incorrectPanel, "IncorrectPanel");
        add(conceptualPanel, "ConceptualPanel");
        add(endGamePanel, "EndGamePanel");
        
        instructionsPanel.addMouseListener(this);
        
        cards.show(this, "HomeMenuPanel");

    }
    
    //to cycle through instructions panels by clicking
    public void mousePressed(MouseEvent evt)
    {
        // Changes instructions panel when panel is clicked
        if(evt.getSource() == instructionsPanel);    
            cards.show(this, "InstructionsPanel2");
    }
    public void mouseEntered(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    
    //getter-setter methods for field vars
    
    //getter method can be called by other classes to access field variables
    public int getVars(int chooseVar)
    {
        int returnNumber = -1;
        if(chooseVar == 0)
            returnNumber = this.numberLives;
        else if(chooseVar == 1)
            returnNumber =  this.gameScore;
        else if(chooseVar == 2)
            returnNumber = this.firstTry;
        else if(chooseVar == 3)
            returnNumber = this.difficulty;
        else if(chooseVar == 4)
            returnNumber = this.numberLivesChosen;
            
        return returnNumber;
    }
    
    //setter method can be called by other classes to change field variables
    public void setVars(int chooseVar, int num)
    {
        if(chooseVar == 0)
            this.numberLives = num;
        else if(chooseVar == 1)
            this.gameScore = num;
        else if(chooseVar == 2)
            this.firstTry = num;
        else if(chooseVar == 3)
            this.difficulty = num;
        else if(chooseVar == 4)
            this.numberLivesChosen = num;
    }

}
