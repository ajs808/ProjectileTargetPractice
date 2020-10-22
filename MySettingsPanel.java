//MySettingsPanel.java
//settings panel, gives user option to change number of lives, difficulty, and
// import/export settings to their name

// Import JFrame and Components
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

// Events & Listeners for Components and JPPanels
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;    
import java.awt.event.ActionEvent;

// Layouts and Graphics
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Color;        
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Dimension;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

class MySettingsPanel extends JPanel implements ActionListener, ChangeListener
{
    private ProjectileCardPanel pcp; //instance of holder panel
    private CardLayout cl; //CardLayout that panel is held in
    private FlowLayout fl; // layout of panel
    private JLabel settingsLabel, livesLabel; //title label
    private Font font, font2; // font
    private JButton menuButton, exportButton, importButton; //button to go to menu
    private JSlider livesSlider; //slider to change number of lives
    private JRadioButton easy, medium, hard;
    private JLabel exportSettingsLabel, importSettingsLabel;
    private JTextField exportSettings,importSettings;
    private ButtonGroup difficultyGroup;
    
     private String entryName, entryScore, entry;
    private PrintWriter output; //file to write to
    private Scanner input; //scanner for reading in file
        
    public MySettingsPanel(ProjectileCardPanel pcpIn, CardLayout clIn)
    {
        setBackground(Color.PINK);
        
        pcp = pcpIn;
        cl = clIn;
        
        //instantiate and add components and listeners
        easy = new JRadioButton("Easy: No wind, gravity is the only force");
        medium = new JRadioButton("Medium: wind is present");
        hard = new JRadioButton("Hard: velocity is given in terms of energy");
        difficultyGroup = new ButtonGroup();
        menuButton = new JButton("Menu");
        exportButton = new JButton("Export");
        importButton = new JButton("Import");
        settingsLabel = new JLabel("Settings");
        livesLabel = new JLabel("Lives");
        font = new Font("Arial", Font.BOLD, 70);
        font2 = new Font("Arial", Font.BOLD, 40);
        fl = new FlowLayout(FlowLayout.CENTER, 5000, 10);
        livesSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 3);
        exportSettingsLabel = new JLabel("Save settings to your name");
        importSettingsLabel = new JLabel("Import settings");
        exportSettings = new JTextField("Name to export", 20);
        importSettings = new JTextField("Name to import", 20);
        
        output = null;
        input = null;
        
        difficultyGroup.add(easy);
        difficultyGroup.add(medium);
        difficultyGroup.add(hard);
        
        settingsLabel.setFont(font);
        livesLabel.setFont(font2);
        setLayout(fl);
        
        //settings for slider
        livesSlider.setMajorTickSpacing(1);
        livesSlider.setPaintTicks(true);
        livesSlider.setPaintLabels(true);
        livesSlider.setSnapToTicks(true);
        livesSlider.setPreferredSize(new Dimension(600, 100) );
        
        //set fonts
        livesSlider.setFont(font2);
        easy.setFont(font2);
        medium.setFont(font2);
        hard.setFont(font2);
        
        //add listeners
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        menuButton.addActionListener(this);
        livesSlider.addChangeListener(this);
        exportSettings.addActionListener(this);
        importSettings.addActionListener(this);
        exportButton.addActionListener(this);
        importButton.addActionListener(this);
        
        easy.setSelected(true);
        
        //add components to panel
        add(settingsLabel);
        add(livesLabel);
        add(livesSlider);
        add(easy);
        add(medium);
        add(hard);
        add(exportSettingsLabel);
        add(exportSettings);
        add(exportButton);
        add(importSettingsLabel);
        add(importSettings);
        add(importButton);
        add(menuButton);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
    
    //listener for button
    public void actionPerformed(ActionEvent evt)
    {
        //if menu button pressed
        if(evt.getSource() == menuButton)
        {
            cl.show(pcp, "HomeMenuPanel");
        }
        //if easy button pressed
        else if(evt.getSource() == easy)
        {
            //set difficulty to easy
            pcp.setVars(3, 1);
        }
        //if medium button pressed
        else if(evt.getSource()==  medium)
        {
            //set difficulty to medium
            pcp.setVars(3, 2);
        }
        //if hard button pressed
        else if(evt.getSource()  ==hard)
        {
            //set difficulty to hard
            pcp.setVars(3, 3);
        }
        //if export button pressed
        else if(evt.getSource() == exportButton)
        {
            writeToSettings();
        }
        //if import button pressed
        else if(evt.getSource() == importButton)
        {
            readToSettings();
        }
    }
    
    //ChangeListener for slider
    public void stateChanged(ChangeEvent evt)
    {
        //changes current number of lives, and chosen number of lives
        pcp.setVars(4, livesSlider.getValue() );
        pcp.setVars(0, livesSlider.getValue() );
        System.out.println(pcp.getVars(0) );
    }
    
    //saves a user's preferred settings by storing information in Settings.txt
    public void writeToSettings()
    {
        String fileName = new String("Settings.txt");
        File outFile = new File(fileName);
        String name = new String(exportSettings.getText().trim() );
        
        //try-catch to append to Settings.txt
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
        
        //appends users name, number of lives, and difficulty
        output.println(name);
        output.println(Integer.toString(pcp.getVars(0)));
        output.println(Integer.toString(pcp.getVars(3)));
        output.println("");
        
        output.close();
    }
    
    public void readToSettings()
    {
        String scoresFileName = new String("Settings.txt");
        int counter = 0;
         File problemFile = new File(scoresFileName);
         
         //try-catch to read from HighScores.txt
         try
         {
             input = new Scanner(problemFile);
         }
         catch (FileNotFoundException e)
         {
             System.err.printf("ERROR: File could not be opened");
            System.exit(1);
         }
         System.out.println("\n\n\n\n"+"\n\n\n\n");
         String currentLine = new String("");
        
         //scans through file
         while(input.hasNext())
         {
            currentLine = new String(input.nextLine() );
            
            //looks for entered name, and adjusts settings accordingly
            if(currentLine.equalsIgnoreCase(importSettings.getText() ) )
            {
                pcp.setVars(0, Integer.parseInt(input.nextLine()));
                pcp.setVars(3, Integer.parseInt(input.nextLine()));
                pcp.setVars(4, pcp.getVars(0) );
            }
         }
    }
}
