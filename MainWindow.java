import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class MainWindow implements ActionListener{ // ActionListener to listen for button presses
// ATTRIBUTES  

    // FRAME
        // JFrame = a GUI window to add components to
            private JFrame defaultFrame = new JFrame();
        // ImageIcon creates an ImageIcon from a file
            ImageIcon flagPNG = new ImageIcon("ICONS/flag.png"); 

    // TITLE PANEL (TITLE, FLAG ICONS, MINIGAME ICON)
        // JPanel = a GUI container that can store multiple components
            JPanel titlePanel = new JPanel();
        // Border creates a border taking in color, thickness, and boolean rounded (doesnt round very well)
            Border titleBorder = BorderFactory.createLineBorder(new Color(207,114,145),6,false); 
        //JLabel = a GUI display area for a string of text, an image, or both
            JLabel title = new JLabel(); 
            JLabel secondFlag = new JLabel();
            JLabel miniGameImg = new JLabel();
            ImageIcon miniGamePNG = new ImageIcon("ICONS/minesweeper_mini.png");

    // BUTTON PANEL (PLAY BUTTON, HOW TO PLAY BUTTON)
            JPanel buttonPanel = new JPanel();
        // JButton = a GUI button that can perform an action when clicked on
            JButton playButton = new JButton();
            JButton howToButton = new JButton();
            Border buttonBorder = BorderFactory.createLineBorder(new Color(207,114,145),4,false);
            ImageIcon bombPNG = new ImageIcon("ICONS/mine_bomb2.png");

    // HIGH SCORE PANEL (TOP 3 SCORES: 4-LETTER NAME, TIME)
            JPanel scorePanel = new JPanel();
            JLabel highScores = new JLabel();
            JLabel score1Label = new JLabel();
            JLabel score2Label = new JLabel();
            JLabel score3Label = new JLabel();
            public static int[] scoreHolder = new int[4];

// CONSTRUCTOR
    MainWindow() {
        // CONSTRUCT MAIN FRAME
            // Set title of frame
                defaultFrame.setTitle("Minesweeper"); 
            // Application is disposed instead of hidden when "X" is clicked
                defaultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Prevent frame from being resized
                defaultFrame.setResizable(false); 
            // Set width and height of frame
                defaultFrame.setSize(600,600); 
            // Allows compnents to be placed freely on the frame, using setBounds instead of a layout manager
                defaultFrame.setLayout(null); 
            // Change background color of frame's content pane to a custom color -- content pane is a layer over the frame's container, objects are placed in the content pane of the frame
                defaultFrame.getContentPane().setBackground(new Color(173,111,132)); 
            // Change icon of frame (top left corner icon) -- this requires "getImage" while creating an icon for the content pane does not
                defaultFrame.setIconImage(flagPNG.getImage()); 
            // Set where the frame is placed on computer screen when it is opened -- "null" opens window in the middle of the screen
                defaultFrame.setLocationRelativeTo(null); 

        // CONSTRUCT TITLE PANEL
            // Change background color of panel -- "getContentPane" is not needed for panels
                titlePanel.setBackground(new Color(250,187,208));
                titlePanel.setLayout(null);
                titlePanel.setBounds(18,30,550,330);
            // Set the panels border -- to make it stand out, but also blend better with the frame's background
                titlePanel.setBorder(titleBorder);
            // Set text of the title JLabel
                title.setText("Minesweeper");
            // Set an icon to the title JLabel
                title.setIcon(flagPNG);
            // vv These are the default positions vv
            // Align the title's text to the left, center or right of title's ImageIcon
                // title.setHorizontalTextPosition(JLabel.TRAILING); 
            // Align the title's text to the bottom, center or top of title's ImageIcon                
                // title.setVerticalTextPosition(JLabel.CENTER); 
            // Set font color of text (default is a dark grey color)
                title.setForeground(Color.black); 
            // Set font of text -- create new font to change the style and size of the font
                title.setFont(new Font(null,Font.BOLD,50)); 
            // Set the gap between the title's text and ImageIcon
                title.setIconTextGap(30); 
                title.setBounds(25,5,500,75);
            // Create a second ImageIcon to be placed on the other side of the title JLabel using the secondFlag JLabel
                secondFlag.setIcon(flagPNG);
                secondFlag.setBounds(460,5,100,75);
            // Create an ImageIcon showing an example of a minesweeper game to be placed under the title JLabel
                miniGameImg.setIcon(miniGamePNG);
                miniGameImg.setBounds(185,100,185,185);
                miniGameImg.setBorder(titleBorder);
            // Add all features to the titlePanel JPanel
                titlePanel.add(title);
                titlePanel.add(secondFlag);
                titlePanel.add(miniGameImg);
            // vv It is visible automatically vv
                // titlePanel.setVisible(true);

        // CONSTRUCT BUTTON PANEL
                buttonPanel.setBackground(new Color(173,111,132));
                buttonPanel.setLayout(null);
                buttonPanel.setBounds(50,375,250,170);
            // Create a "PLAY" button which implements an ActionListener
                playButton.setBounds(25,0,200,70);
                playButton.setBackground(new Color(250,187,208));
                playButton.setBorder(buttonBorder);
                playButton.setFont(new Font(null,Font.BOLD,30));
                playButton.setText("PLAY");
                playButton.setIcon(bombPNG);
                playButton.setForeground(Color.black);
            // Remove focus box from around the text of the playButton JButton
                playButton.setFocusable(false);
            // Listen for clicks of the playButton JButton
                playButton.addActionListener(this); 
            // Create "How To Play" button which implements an ActionListener
                howToButton.setBounds(25,100,200,70);
                howToButton.setBackground(new Color(250,187,208));
                howToButton.setBorder(buttonBorder);
            // Use "|" to combine text styles BOLD and ITALIC
                howToButton.setFont(new Font(null,Font.BOLD|Font.ITALIC,25));
                howToButton.setText("How To Play");
                howToButton.setForeground(Color.black);
                howToButton.setFocusable(false);
            // Listen for clicks of the howToButton JButton
                howToButton.addActionListener(this);
            // Add all features to the buttonPanel JPanel
                buttonPanel.add(playButton);
                buttonPanel.add(howToButton);
            // vv It is automatically visible vv
                // buttonPanel.setVisible(true);
            
        // CONSTRUCT HIGH SCORE PANEL
        scorePanel.setBackground(Color.green);
                scorePanel.setLayout(null);
                scorePanel.setBounds(300,375,250,170);
        highScores = new JLabel();
        highScores.setText("High Scores");
        highScores.setForeground(Color.black); 
        highScores.setFont(new Font(null,Font.BOLD,25)); 
        highScores.setBounds(10,5,240,30);
        highScores.setHorizontalTextPosition(JLabel.CENTER);
        score1Label.setText("1 - "+scoreHolder[0]);
        score1Label.setForeground(Color.black); 
        score1Label.setFont(new Font(null,Font.PLAIN,20)); 
        score1Label.setBounds(5,45,240,30);
        score1Label.setHorizontalTextPosition(JLabel.CENTER);
        
        score2Label.setText("2 - "+scoreHolder[1]);
        score2Label.setForeground(Color.black); 
        score2Label.setFont(new Font(null,Font.PLAIN,20)); 
        score2Label.setBounds(5,85,240,30);
        score2Label.setHorizontalTextPosition(JLabel.CENTER);        
        
        score3Label.setText("3 - "+scoreHolder[2]);
        score3Label.setForeground(Color.black); 
        score3Label.setFont(new Font(null,Font.PLAIN,20)); 
        score3Label.setBounds(5,125,240,30);
        score3Label.setHorizontalTextPosition(JLabel.CENTER);

        scorePanel.add(highScores);
        scorePanel.add(score1Label);
        scorePanel.add(score2Label);
        scorePanel.add(score3Label);




        // Add all features to the defaultFrame JFrame and make it visible
                defaultFrame.add(titlePanel);
                defaultFrame.add(buttonPanel);
                defaultFrame.add(scorePanel);
            // Make frame visible (not automatically visible) -- might be that the frame attributes are made visible with the frame unless stated otherwise
                defaultFrame.setVisible(true); 
    }

// ACTION LISTENERS
    // Removes warnings from unused objects -- just makes the code look nicer, removes underlines on unused objects
        @SuppressWarnings("unused")
        public void actionPerformed (ActionEvent press){
            // Exit out of the main window when playButton or howToButton is clicked
                defaultFrame.dispose();
            // Open a gameWindow if the playButton is clicked
                if(press.getSource()==playButton){
                    GameWindow game = new GameWindow();
                    //game.getScore();
                }
            // Open a howToWindow if the howToButton is clicked
                // else if(press.getSource()==howToButton){
                    // HowToWindow howTo = new HowToWindow();
                // }
        }

        public void setScore(int[] times){
            scoreHolder = times;
        }

    

// END OF MAIN WINDOW CLASS
}








