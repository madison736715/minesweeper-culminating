import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.Timer;

public class GameWindow implements ActionListener{
    
    // Create a Tile class within the GameWindow class so that game tiles can be created more easily -- "extends JButton" allows Tile objects to be treated like JButtons
        private class Tile extends JButton { 
            // ATTRIBUTES
            private Border border = BorderFactory.createLineBorder(new Color(207, 114, 145), 2, false);
                // Row and col attributes allow for specific tiles to be identified        
                    private int row;
                    private int col;

            // CONSTRUCTOR
                // Create row and col parameters corresponding to the row and col attributes of each tile
                    public Tile(int row, int col) {
                        this.row = row;
                        this.col = col;
                        setBackground(new Color(250,187,208));
                        setBorder(border);
                    }
        }

// ATTRIBUTES

    // FRAME
            private JFrame gameFrame = new JFrame();
            private ImageIcon gameIcon = new ImageIcon("ICONS/flag.png");

    // TITLE PANEL (TITLE, NUM MINES, START BUTTON, AGAIN BUTTON, MENU BUTTON, TIME LABEL/TIMER)        
            private JLabel title = new JLabel();
            private JPanel titlePanel = new JPanel();
            private JButton startTimerButton = new JButton();
            private JButton againButton = new JButton();
            private JButton mainMenuButton = new JButton();
            private JLabel timerLabel = new JLabel();
            private Border timerBorder = BorderFactory.createLineBorder(Color.black, 2, false);
            private int elapsedTime;
            private int milliseconds;
            private int seconds;
            private int minutes;
        // %02d formats numbers to have 2 digits, left padding with 0's if less than 2 digits are provided
            private String milliString = String.format("%02d", milliseconds);
            private String secondString = String.format("%02d", seconds);
            private String minuteString = String.format("%02d", minutes);
        // Create a new timer with a delay of 1 millisecond
            private Timer timer = new Timer(1, new ActionListener() {
                public void actionPerformed (ActionEvent start) {
                    // Keep track of total time elapsed (milliseconds) (actually milliseconds/10)
                        elapsedTime+=1;
                    // Track milliseconds, (equal to total time elapsed) only displaying the final 2 digits
                        milliseconds = elapsedTime % 100;
                        milliString = String.format("%02d", milliseconds);
                    // Track seconds, (equal to total time elapsed/100) only displaying the final 2 digits
                        seconds = (elapsedTime/100) % 60;  
                        secondString = String.format("%02d", seconds);
                    // Track minutes, (equal to total time elapsed/6000) only displaying thr final 2 digits -- hopefully the time will never have to go over 99 minutes
                        minutes = (elapsedTime/6000) % 60;
                        minuteString = String.format("%02d", minutes);
                    // Constantly update the text of the timerLabel JLabel
                        timerLabel.setText(minuteString+":"+secondString+"."+milliString);
                }
            });

    // GAME PANEL
            private JPanel gamePanel = new JPanel();
            private ImageIcon bombPNG = new ImageIcon("ICONS/mine_bomb2.png");
        // Create a new ImageIcon with a resized flag for the tiles
            private ImageIcon flagTilePNG = new ImageIcon("ICONS/flag2.png");
            private int width = 600;
            private int height = 600;
            private int tileSize = 60;
            private int numCols = (width-tileSize)/tileSize;
            private int numRows = numCols;
            private int mineCount = 10;
        // Create a 2d array for tiles to be stored in
            private Tile[][] tileBoard = new Tile[numRows][numCols];
        // Create an ArrayList for the mine tiles to be stored in
            private ArrayList<Tile> mineList;
            private Random random = new Random();
        // Check if all tiles that are not mines have been clicked
            private int tilesClicked = 0; 
        // Disable tiles until "Start Time" button is pressed
            private boolean gameOver = true;

    // HIGH SCORE
        public static int[] scoreList = new int[] {999999999, 999999999, 999999999, 0};
        public static int gamesWon = 0;
        public static String timeHolder = "";
        //@SuppressWarnings("unused")
       // int[] scoreHolder;
            // scoreList[0] = 999999999;
            // scoreList[1] = 999999999;
            // scoreList[2] = 999999999;
            // scoreList[3] = 0;


// CONSTRUCTOR            
    GameWindow() {
        // CONSTRUCT GAME FRAME
                gameFrame.setTitle("Minesweeper");
                gameFrame.setIconImage(gameIcon.getImage());
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setSize(width,height); 
                gameFrame.setResizable(false);
                gameFrame.setLayout(null); 
                gameFrame.setLocationRelativeTo(null);
                
        // CONSTRUCT TITLE PANEL
                titlePanel.setLayout(null);
                titlePanel.setBounds(0,0,600,60);
            // Create title JLabel
                title.setText("Minesweeper: "+mineCount+" mines");
                title.setFont(new Font(null,Font.BOLD,30));
                title.setForeground(Color.black);
                title.setBounds(25,10,350,40);
            // Create "Start Time" JButton which implements an ActionListener
                startTimerButton.setBounds(450,20,100,25);
                startTimerButton.setText("Start Time");
                startTimerButton.setFont(new Font(null,Font.PLAIN,13));
                startTimerButton.setFocusable(false);
            // Listen for clicks of startTimerButton JButton
                startTimerButton.addActionListener(this);
            // Create "Play Again" JButton which implements an ActionListener
                againButton.setBounds(450,5,100,25);
                againButton.setText("Play Again");
                againButton.setFont(new Font(null,Font.PLAIN,13));
            // Hide againButton until game is over
                againButton.setVisible(false);
                againButton.setFocusable(false);
            // Listen for clicks of againButton JButton
                againButton.addActionListener(this);
            // Create "Main Menu" JButton which implements an ActionListener
                mainMenuButton.setBounds(450,33,100,25);    
                mainMenuButton.setText("Main Menu");
                mainMenuButton.setFont(new Font(null,Font.PLAIN,13));
            // Hide mainMenuButton until game is over
                mainMenuButton.setVisible(false);
                mainMenuButton.setFocusable(false);
            // Listen for clicks of mainMenuButton JButton
                mainMenuButton.addActionListener(this);
            // Initialize variables for timer
                elapsedTime = 0;
                milliseconds = 0;
                seconds = 0;
                minutes = 0;
            // Create a timerLabel to display the timer
                timerLabel.setBounds(390,10,175,40);
                timerLabel.setText(minuteString+":"+secondString+"."+milliString);
                timerLabel.setFont(new Font("Verdana",Font.PLAIN,30));
                timerLabel.setBorder(timerBorder);
            // Align text to the center of the timerLabel JLabel horizontally -- no need for vertical alignment since default is center
                timerLabel.setHorizontalAlignment(JLabel.CENTER);
            // Hide timerLabel until startTimerButton is pressed
                timerLabel.setVisible(false);
            // Add all features to the titlePanel
                titlePanel.add(title);
                titlePanel.add(startTimerButton);
                titlePanel.add(againButton);
                titlePanel.add(mainMenuButton);
                titlePanel.add(timerLabel);
            
        // CONSTRUCT GAME PANEL
            // Use GridLayout to format the tiles automatically
                gamePanel.setLayout(new GridLayout(numRows,numCols));    
                gamePanel.setBounds(0,60,584,500);
            // Use for loop to create tiles
                for (int r = 0; r < numRows; r++) {
                    for (int c = 0; c < numCols; c++) {
                        // Create new tile, using row and col paramaters from the for loop indices which will act as coordinates
                            Tile newTile = new Tile(r, c);
                        // Place tiles into the 2d array in order to have access to them based on their coordinates
                            tileBoard[r][c] = newTile;
                            newTile.setFocusable(false);
                            newTile.setFont(new Font(null,Font.PLAIN,45));
                        // Listen for clicks of game tiles -- use MouseListener instead of ActionListener so that left and right clicks can be differentiated
                            newTile.addMouseListener(new MouseAdapter() {
                                public void mousePressed(MouseEvent click) {
                                    if (gameOver) {
                                        // Disable all tiles if game is over (won or lost) -- "return" stops the mousePressed function right away
                                            return; 
                                    }
                                // Left click to select a tile
                                    else if (click.getButton() == MouseEvent.BUTTON1) {
                                        // If tile has not already been revealed (and does not have a flag on it) -- this check stops tiles from being clicked if they have already been selected or flagged
                                            if ((newTile.getText() == "") && (newTile.getIcon() == null) && (newTile.isContentAreaFilled())) {
                                                // If the tile is a mine
                                                    if (mineList.contains(newTile)) {
                                                        revealMines();
                                                    }
                                                // If the tile isn't a mine, check for mines surrounding that tile
                                                    else {
                                                        checkMine(newTile.row, newTile.col);
                                                    }
                                            }
                                    }
                               // Right click to set a flag on a tile
                                    else if (click.getButton() == MouseEvent.BUTTON3) {
                                        // If tile has not already been revealed (and does not have a flag on it)
                                            if ((newTile.getText() == "") && (newTile.getIcon() == null) && (newTile.isContentAreaFilled())) {
                                                newTile.setIcon(flagTilePNG);
                                            }
                                        // If tile has already been flagged, remove the flag -- tile becomes clickable again
                                            else if (newTile.getIcon() == flagTilePNG) {
                                                newTile.setIcon(null);
                                            }
                                    }
                                }
                           }); 
                        // Add each tile to the game panel       
                            gamePanel.add(newTile);
                    }
            // End of for loop
                } 
            // Use setMines function to assign mines to random tiles
                setMines();

        // HIGH SCORE

                
            

         
  
        // Add all features to the gameFrame JFrame and make it visible                  
                gameFrame.add(titlePanel);
                gameFrame.add(gamePanel);
                gameFrame.setVisible(true);
    }

// ACTION LISTENERS
@Override
    @SuppressWarnings("unused")
    public void actionPerformed (ActionEvent press) {
        // If "Start Time" button is pressed: hide the button, show the timer, and start the timer
            if (press.getSource()==this.startTimerButton){
                startTimerButton.setVisible(false);
                timerLabel.setVisible(true);
                start();
            }
        // If "Play Again" button is pressed: dispose of the current GameWindow and open a new one
            else if(press.getSource()==this.againButton){
                gameFrame.dispose();
                GameWindow gameAgain = new GameWindow();
            }
        // If "Main Menu" button is pressed: dispose of GameWindow and open a new MainWindow
          else if (press.getSource()==this.mainMenuButton){
              gameFrame.dispose();
              MainWindow menu = new MainWindow();
              menu.setScore(scoreList);
          }
    }

// TIMER FUNCTIONS
    void start() {
            timer.start();
        // Enable tiles
            gameOver = false;
    }

    void stop() {
              timer.stop();
        // Hide timerLabel, it will be displayed with the title
              timerLabel.setVisible(false);
    }

// GAME FUNCTIONS
    void setMines() {
        mineList = new ArrayList<Tile>();
        int mineLeft = mineCount;
        // While there are still mines that need to be set (based on the total given to mineCount)
            while (mineLeft > 0) {
                // Pick a random number from 0-numRows and 0-numCols for the row and col coordinates
                    int r = random.nextInt(numRows);
                    int c = random.nextInt(numCols);
                // Create an instance of a tile with those coordinates
                    Tile tile = tileBoard[r][c];
                // If a tile with those coordinates does not already have a mine
                    if (!mineList.contains(tile)) {
                        // Add it to the mineList to become a mine
                            mineList.add(tile);
                        // Decrease the amount of mines left to create
                            mineLeft -= 1;
                    }
                // Otherwise, continue looping until numMines unique mines are selected
            }
    }

    void revealMines() {
        // Go through the mineList ArrayList
            for (int i = 0; i < mineList.size(); i++) {
                // Create an instance of tile from mineList
                    Tile bomb = mineList.get(i);
                // Set icno to a bomb
                    bomb.setIcon(bombPNG);
                // Give the tile the effect of being disabled, while still maintaining its current appearance
                    bomb.setContentAreaFilled(false);
            }
        // End game and stop timer
            gameOver = true;
            stop();
        // Change the text of the title
            title.setText("Game Over!    "+timerLabel.getText());
        // Reveal the "Play Again" and "Main Menu" buttons
            againButton.setVisible(true);
            mainMenuButton.setVisible(true);  
    }

    void checkMine(int row, int col) {
        // If the coordinates are outside of the gamePanel range
            if (row<0 || row>=numRows || col<0 || col>=numCols) {
                return;
            }
        // Otherwise, create an instance of the tile
            Tile tile = tileBoard[row][col];
        // If the tile has already been selected
            if (! (tile.isContentAreaFilled())) {
                return;
            }
        // Give the tile the effect of being disabled, while maintaining its current appearance -- disabled buttons can't have colored text
            tile.setContentAreaFilled(false);
        // Increase the number of revealed tiles
            tilesClicked++;
           // tilesClicked = numRows*numCols - mineList.size();
        // Create a variable to keep track of how many mines are surrounding a given tile
            int minesFound = 0;
        // Check surrounding tiles
        // Top 3
            minesFound += countMine(row-1,col-1); // Top left
            minesFound += countMine(row-1,col); // Top
            minesFound += countMine(row-1,col+1); // Top right
        // Left and right
            minesFound += countMine(row,col-1); // Left
            minesFound += countMine(row,col+1); // Right
        // Bottom 3
            minesFound += countMine(row+1,col-1); // Bottom left
            minesFound += countMine(row+1,col); // Bottom 
            minesFound += countMine(row+1,col+1); // Bottom right
        // If there are 1 or more mines surrounding a given tile: remove flag icon (if any), and set its text to the number of mines
            if (minesFound > 0) {
                    tile.setIcon(null);
                    tile.setText(Integer.toString(minesFound));
                // Give each number a unique color
                    if (minesFound == 1) {
                        tile.setForeground(new Color(31,80,240)); // Blue
                   }
                   else if (minesFound == 2) {
                       tile.setForeground(new Color(240,31,62)); // Red
                   }
                   else if (minesFound == 3) {
                       tile.setForeground(new Color(31,240,55)); // Green
                    }
                    else if (minesFound == 4) {
                        tile.setForeground(new Color(240,101,31)); // Orange
                    }
                   else if (minesFound == 5) {
                       tile.setForeground(new Color(188,31,240)); // Purple
                    }
                    else if (minesFound == 6) {
                        tile.setForeground(new Color(240,191,31)); // Yellow
                   }
                    else if (minesFound == 7) {
                        tile.setForeground(new Color(31,240,177)); // Teal
                   }
                // The max amount of mines that can surround one tile is 8
                   else {
                       tile.setForeground(new Color(240,31,149)); // Pink
                   }
            }
        // If there are no mines surrounding the tile: remove flag icon (if any), and set its text to nothing, then check the surrounding tiles -- only blank tiles check their neighbors
            else {
                    tile.setIcon(null);
                    tile.setText("");
                // Recursively check surrounding tiles -- base case is if a tile has 1 or more mines surrounding it
                // Top 3
                    checkMine(row-1,col-1); // Top left
                    checkMine(row-1,col); // Top
                    checkMine(row-1,col+1); // Top right
                // Left and right
                    checkMine(row,col-1); // Left
                    checkMine(row,col+1); // Right
                // Bottom 3
                    checkMine(row+1,col-1); // Bottom left
                    checkMine(row+1,col); // Bottom 
                    checkMine(row+1,col+1); // Bottom right
            }
        // If all tiles other than mines have been uncovered
            if (tilesClicked == numRows*numCols - mineList.size()) {
                // End the game and stop the timer
                    gameOver = true; 
                    stop();
    
                // Add the elapsed time to the scoreList array
                    scoreList[3] = elapsedTime;
                    quickSort(scoreList,0,3);
                // Adjust the title bounds to be able to fit the new text
                    title.setBounds(25,10,400,40);
                    //title.setText("Mines cleared in "+timerLabel.getText()+"!");
                    //title.setText("Time: "+scoreList[0]+" "+scoreList[1]+" "+scoreList[2]+" "+scoreList[3]);
                    gamesWon++;
                    scoreList[3] = elapsedTime;
                    
                    title.setText("Games won: " + gamesWon + "Time: "+scoreList[0]+" "+scoreList[1]+" "+scoreList[2]+" "+scoreList[3]);
                    title.setFont(new Font(null,Font.PLAIN,13));
                // Reveal the "Play Again" and "Main Menu" buttons
                    againButton.setVisible(true);
                    mainMenuButton.setVisible(true);
                
            }  
      }

    int countMine(int row, int col) {
        // Provide an integer value for the number of surrounding mines found
            if (row<0 || row>=numRows || col<0 || col>=numCols) {
                return 0;
            }
            if (mineList.contains(tileBoard[row][col])) {
                return 1;
            }
            else {
                return 0;
            }
    }


   public static void swap(int[] arr, int i, int j){
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
  }


    public static void quickSort(int[] arr, int low, int high){
        if (low < high){
                int pi = partition(arr, low, high);
            // Separately sort elements before
            // partition and after partition
                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
        }
    }
  
   public static int partition(int[] arr, int low, int high){ 
        // pivot
            int pivot = arr[high]; 
      
        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
            int i = (low - 1); 
            for(int j = low; j < high ; j++){          
                // If current element is smaller 
                // than the pivot
                    if (arr[j] < pivot) {
                        // Increment index of 
                        // smaller element
                            i++; 
                            swap(arr, i, j);
                    }
            }
            swap(arr, i + 1, high);
            return (i + 1);
    }

    // public int[] getScore(){
    //     return scoreList;
    // }


// END OF GAME WINDOW CLASS
}


