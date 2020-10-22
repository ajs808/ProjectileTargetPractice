//GameProject.java
//Arul Saxena
//Period 3
//Java Final Game Project
//Projectile Target Practice

// Import JFrame
import javax.swing.JFrame;

public class GameProject  //JFrame class
{
    private JFrame cardFrame; //JFrame
    //instantiation of holder panel with CardLayout
    private ProjectileCardPanel holderPanel; 

    public GameProject()
    {
    }
    
    public static void main(String[] args) //calls run()
    {
        GameProject gp = new GameProject();
        gp.run();
    }
    
    public void run()
    {
        //calls method to make JFrame and set it visible
        makeGameFrame();
    }
    
    public void makeGameFrame()
    {
        // Create a JFrame with BorderLayout
        
        cardFrame = new JFrame("Projectile Target Practice");    // Create the JFrame
        cardFrame.setDefaultCloseOperation(cardFrame.EXIT_ON_CLOSE);
        cardFrame.setSize(1350, 800);
        cardFrame.setLocation(0, 0);
        
        // Create panel
        holderPanel = new ProjectileCardPanel();
        
        // Add panel to the frame
        cardFrame.getContentPane().add(holderPanel);
        
        // Make the JFrame visible
        cardFrame.setVisible(true);
    }    
}    
