
// Last Update 130421 2309
// Use AWT's Layout Manager
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
// Use AWT's Event handlers
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
// Use Swing's Containers and Components
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;





/**
 * The Mine Sweeper Game. Left-click to reveal a cell. Right-click to
 * plant/remove a flag for marking a suspected mine. You win if all the cells
 * not containing mines are revealed. You lose if you reveal a cell containing a
 * mine.
 */
@SuppressWarnings("serial")
public class MineSweeper extends JFrame {
	
	// Variables
	private static int difficulty = 1;
	private static boolean cheat = false;
	private int MouseLeftClickCount = 0;
	private Random randomBucket = new Random(); // Create random object to generate random location for mines
	// Array to store location of all mines
	private int minesCordsX[];
	private int minesCordsY[];
	// Class level cords for selected square
	private int rowSelected = -1;
	private int colSelected = -1;
	private boolean gameOver = false; // Checking if game is over
	private Icon bombIcon = new ImageIcon("C:\\bomb6.PNG");
	private Icon flagIcon = new ImageIcon("C:\\flag2.PNG");
	private Icon unRevealedTileIcon = new ImageIcon("C:\\tileEasyUn.PNG");
	private Icon flagCrossIcon = new ImageIcon("C:\\flagwcross.PNG");
	private Icon newGameIcon = new ImageIcon("C:\\newgamebutton.PNG");
	private Icon settingsIcon = new ImageIcon("C:\\settingsbutton.PNG");
	private Icon highScoreButtonIcon = new ImageIcon("C:\\highscorebutton.PNG");
	private Icon quitbuttonIcon = new ImageIcon("C:\\quitbutton.PNG");
	private Icon clockIcon = new ImageIcon("C:\\clock.PNG");
	private Icon bombpanelIcon = new ImageIcon("C:\\bomb2.PNG");
	private Icon resetIcon = new ImageIcon("C:\\reset.PNG");
	private Icon easyTileUnIcon = new ImageIcon("C:\\tileEasyUn.PNG");
	private Icon difficultyIcon = new ImageIcon("C:\\difficulty.PNG");
	private Icon cheatsIcon = new ImageIcon("C:\\cheats.PNG");
	private Icon easyIcon = new ImageIcon("C:\\easy.PNG");
	private Icon mediumIcon = new ImageIcon("C:\\medium.PNG");
	private Icon hardIcon = new ImageIcon("C:\\hard.PNG");
	private Icon yesIcon = new ImageIcon("C:\\yes.PNG");
	private Icon noIcon = new ImageIcon("C:\\no.PNG");
	private Icon backIcon = new ImageIcon("C:\\back.PNG");
	private Icon mainMenuIcon = new ImageIcon("C:\\mainmenu.PNG");
	private Icon gameIcon = new ImageIcon("C:\\game.PNG");
	
	private Icon oneIcon = new ImageIcon("C:\\1.PNG");
	private Icon twoIcon = new ImageIcon("C:\\2.PNG");
	private Icon threeIcon = new ImageIcon("C:\\3.PNG");
	private Icon fourIcon = new ImageIcon("C:\\4.PNG");
	private Icon fiveIcon = new ImageIcon("C:\\5.PNG");
	private Icon sixIcon = new ImageIcon("C:\\6.PNG");
	private Icon sevenIcon = new ImageIcon("C:\\7.PNG");
	private Icon eightIcon = new ImageIcon("C:\\8.PNG");
	private Color revealedBackground = new Color(242,219,183);
	private ButtonListener Lis = new ButtonListener();
	private JTextField tfNumBomb;//, tfScore;
	private String inStr = "";
	private JButton btnBack = new JButton("Back");
	private int totalWins = 0;
	
	Font font1 = new Font("Consolas",Font.LAYOUT_LEFT_TO_RIGHT, 30);
	Font numFont = new Font("Consolas" ,Font.BOLD, 30);
	Font highScoreFont = new Font("Consolas", Font.BOLD, 25);
	
	//Timer Source -> https://stackoverflow.com/questions/16815724/create-a-count-up-timer-in-java-seconds-and-milliseconds-only
	private final ClockListener clock = new ClockListener();
    private final Timer timer = new Timer(1, clock);
    private final JTextField tfClock = new JTextField(4);
    
    
    public static final Dimension easyDim = new Dimension(640,720);
    public static final Dimension startPageButtons = new Dimension(240,50);
    
    Color plainBlue = new Color(86,124,220);
	

	// Name-constants for the game properties
	public static int ROWS = 10; // number of cells
	public static int COLS = 10;
	public static final int START = 1;

	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60; // Cell width and height, in pixels
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Game board width/height
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	public static final Color BGCOLOR_NOT_REVEALED = Color.DARK_GRAY;
	public static final Color FGCOLOR_NOT_REVEALED = Color.RED; // flag
	public static final Color BGCOLOR_REVEALED = new Color(110,34,126);
	public static final Color FGCOLOR_REVEALED = Color.LIGHT_GRAY; // number of mines
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

	// Buttons for user interaction
	JButton btnCells[][] = new JButton[ROWS][COLS];

	// Number of mines in this game. Can vary to control the difficulty level.
	private int numMines;
	private int numMinesRemaining = 0;
	// Location of mines. True if mine is present on this cell.
	boolean mines[][] = new boolean[ROWS][COLS];

	// User can right-click to plant/remove a flag to mark a suspicious cell
	boolean flags[][] = new boolean[ROWS][COLS];

	// Revealed array
	boolean revealed[][] = new boolean[ROWS][COLS];

	// Constructor to set up all the UI and game components
	public MineSweeper(int start) {
		new StartPage();
	}

	public MineSweeper() {
		// Add Menu Bar
		JMenuBar menuBar; // the menu-bar
		JMenu menu; // each menu in the menu-bar
		JMenuItem menuItem; // an item in a menu

		// Constructors
		menuBar = new JMenuBar();

		// First Menu
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_CONTROL); // alt short-cut key
		menuBar.add(menu); // the menu-bar adds this menu
		menuItem = new JMenuItem("Main Menu", KeyEvent.VK_M);
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new StartPage();
				dispose();
			}
		});
		menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MineSweeper();
				dispose();
			}
		});

		// Second Menu
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H); // short-cut key
		menuBar.add(menu); // the menu bar adds this menu

		menuItem = new JMenuItem("Rules", KeyEvent.VK_R);
		menu.add(menuItem); // the menu adds this item
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new HelpPage();
				//dispose();
			}
		});
		setJMenuBar(menuBar);

		gameOver = false;
		timer.setInitialDelay(0); //Initialised timer
		// Allocate a common instance of listener as the MouseEvent listener
		// for all the JButtons
		CellMouseListener listener = new CellMouseListener();
		Container cp = this.getContentPane(); // JFrame's content-pane
		cp.setLayout(new BorderLayout(ROWS, COLS));
		// cp.setLayout(new BorderLayout());
		// add pnlStatusBar to cp
		//pnlStatusBar.setLayout(new FlowLayout());
		JLabel lblNumBomb = new JLabel();
		//pnlStatusBar.add(lblNumBomb); // Add Label for num of bombs remaining
		tfNumBomb = new JTextField("" + numMinesRemaining, 2);
		tfNumBomb.setFont(font1);
		tfNumBomb.setBorder(null);
		tfNumBomb.setForeground(new Color(209,79,79));
		tfNumBomb.setBackground(new Color(43,92,73));
		//label.setFont(new Font("Verdana", Font.PLAIN, 18));
		tfNumBomb.setEditable(false);
		tfNumBomb.addMouseListener(listener);
		//pnlStatusBar.add(tfNumBomb); // Add TextField for num of bombs remaining
		JLabel lblScore = new JLabel();
		lblScore.setIcon(clockIcon);
		//pnlStatusBar.add(lblScore); // Add Label for Score
		/* tfScore = new JTextField("" + MouseLeftClickCount, 5);
		tfScore.setFont(font1);
		tfScore.setBorder(null);
		tfScore.setEditable(false);
		tfScore.addMouseListener(listener); */
		//pnlStatusBar.add(tfScore); // Add TextField for score
		//cp.add(pnlStatusBar, BorderLayout.SOUTH);
		lblNumBomb.setIcon(bombpanelIcon);
		// add pnlReset to cp
		Panel pnlReset = new Panel();
		pnlReset.setLayout(new FlowLayout());
		JButton btnReset = new JButton();
		btnReset.setBorder(null);
		btnReset.setIcon(resetIcon);
		btnReset.setPreferredSize(new Dimension(60,60));
		tfClock.setFont(font1);
		tfClock.setText(0 + "");
		tfClock.setForeground(new Color(209,79,79));
		tfClock.setBackground(new Color(43,92,73));
		tfClock.setBorder(null);
		btnReset.addActionListener(Lis);
		pnlReset.add(lblNumBomb);
		pnlReset.add(tfNumBomb);
		pnlReset.add(btnReset);
		pnlReset.add(lblScore);
		pnlReset.add(tfClock);
		pnlReset.setBackground(new Color(43,92,73));
		//pnlReset.setPreferredSize(new Dimension(320,200));
		
		
		cp.add(pnlReset, BorderLayout.NORTH);
		// add pnlGame to cp
		Panel pnlGame = new Panel();
		pnlGame.setLayout(new GridLayout(ROWS, COLS, 2, 2));
		
		cp.add(pnlGame, BorderLayout.CENTER);

		//EASY Construct 10x10 JButtons and add to the content-pane 

		// Set the size of the content-pane and pack all the components
		// under this container.
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				btnCells[row][col] = new JButton(); // Allocate each JButton of the array
				btnCells[row][col].setSize(new Dimension(32,32));
				pnlGame.add(btnCells[row][col]); // add to content-pane in GridLayout
		

				// Add MouseEvent listener to process the left/right mouse-click
				btnCells[row][col].addMouseListener(listener); // For all rows and cols
			}
		}
		cp.setPreferredSize(easyDim);
		
		pack();
		cp.setBackground(new Color(58,143,140));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // handle window-close button
		setTitle("Mine Sweeper");
		setVisible(true); // show it
		
		// Centralised
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		cp.setForeground(Color.red);
		// Initialize for a new game
		initGame();
	}

	// Initialize and re-initialize a new game method
	private void initGame() {
		// Reset cells, mines, and flags
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				// Set all cells to un-revealed
				btnCells[row][col].setEnabled(true); // enable button
				btnCells[row][col].setIcon(unRevealedTileIcon);
				btnCells[row][col].setBorderPainted(false);
				btnCells[row][col].setBackground(BGCOLOR_NOT_REVEALED);
				btnCells[row][col].setFont(FONT_NUMBERS);
				btnCells[row][col].setOpaque(true);
				btnCells[row][col].setText(""); // display blank
				mines[row][col] = false; // clear all the mines
				flags[row][col] = false; // clear all the flags
				revealed[row][col] = false; // clear all revealed cells
			}
		}

		// For debugging and revealing all locations of the bombs
		System.out.println("Bombs made at: ");
		for (int count = 0; count <= numMines - 1; count++) {
			System.out.printf("[%d][%d]\n", minesCordsX[count], minesCordsY[count]);
		}
	}

	// VERY START OF THE GAME
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MineSweeper(START);
			}
		});
	}

	// Define the ButtonListener Inner Class for Reset Button and New Game Button
	private class ButtonListener extends WindowAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new MineSweeper();
		}
	}

	// Define the Listener Inner Class
	private class CellMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			// Determine the (row, col) of the JButton that triggered the event

			// Get the source object that fired the Event
			JButton source = (JButton) e.getSource();
			// Scan all rows and columns, and match with the source object
			boolean found = false;
			for (int row = 0; row < ROWS && !found; ++row) {
				for (int col = 0; col < COLS && !found; ++col) {
					if (source == btnCells[row][col]) {
						rowSelected = row;
						colSelected = col;
						found = true; // break both inner/outer loops
					}
				}
			}

			// Left-click to reveal a cell; Right-click to plant/remove the flag.
			if (e.getButton() == MouseEvent.BUTTON1) { // Left-button clicked
				if (MouseLeftClickCount == 0 && !(flags[rowSelected][colSelected]) && !gameOver) {
					generateMines();
					timer.start(); //Start the timer
					revealCell(rowSelected, colSelected);
					numMinesRemaining = numMines;
					tfNumBomb.setText("" + numMinesRemaining); // Update num of mines remaining
					if (btnCells[rowSelected][colSelected].getText().isBlank()) {
						revealSurrounding(rowSelected, colSelected);
					}
					// prevents you from left clicking when there is a flag
				} else if (MouseLeftClickCount >= 1 && !(flags[rowSelected][colSelected]) && !gameOver) {
					// If you hit a mine, game over
					// Otherwise, reveal the cell and display the number of surrounding mine
					if (mines[rowSelected][colSelected]) {
						gameOver = true;
						timer.stop(); //stop timer when you explode
						for (int count = 0; count <= numMines - 1; count++) {
							if (!(flags[minesCordsX[count]][minesCordsY[count]])) { // Will not reveal bombs in squares
																					// that have flags
								btnCells[minesCordsX[count]][minesCordsY[count]].setIcon(bombIcon);
								btnCells[minesCordsX[count]][minesCordsY[count]].setBackground(revealedBackground);
							}
						}
						// Makes a cross when there is flag planted but there isnt a bomb there
						for (int countRow = 0; countRow <= ROWS - 1; countRow++) {
							for (int countCol = 0; countCol <= COLS - 1; countCol++) {
								if (flags[countRow][countCol] == true && mines[countRow][countCol] == false) {
									btnCells[countRow][countCol].setIcon(flagCrossIcon);
								}
							}
						}
						// put a cross on the flags which dont have bombs
						btnCells[rowSelected][colSelected].setBackground(Color.red);
						JOptionPane.showMessageDialog(null, "Game Over");
						System.out.println("Game Over");
						//PlaySound.PlaySoundClose();
						new PlaySound2();
						//inStr = JOptionPane.showInputDialog(null, "Enter your Name", "Mine Sweeper", JOptionPane.PLAIN_MESSAGE);
						// score = Integer.parseInt(tfScore.getText());
					} else {
						revealCell(rowSelected, colSelected);
						if (btnCells[rowSelected][colSelected].getText().isBlank()) {
							revealSurrounding(rowSelected, colSelected);
						}
					}
				}

			} else if (e.getButton() == MouseEvent.BUTTON3) { // right-button clicked
				// If the location is not flagged, plant a flag
				if (flags[rowSelected][colSelected] == false && !gameOver) {
					btnCells[rowSelected][colSelected].setIcon(flagIcon);
					flags[rowSelected][colSelected] = true;
					revealed[rowSelected][colSelected] = true;
					numMinesRemaining--;
					tfNumBomb.setText("" + numMinesRemaining); // Update num of mines remaining
				} else if (!gameOver) {
					// Otherwise, remove the flag
					btnCells[rowSelected][colSelected].setIcon(unRevealedTileIcon);
					revealed[rowSelected][colSelected] = false;
					flags[rowSelected][colSelected] = false;
					numMinesRemaining++;
					tfNumBomb.setText("" + numMinesRemaining); // Update num of mines remaining
				}
			}

			// Check if the player has won, after revealing this cell
			if (MouseLeftClickCount == ROWS * COLS - numMines) {
				timer.stop();
				if (cheat == false && !gameOver) {
					//new PlaySound3();
					JOptionPane.showMessageDialog(null, "You Win!");
					inStr = JOptionPane.showInputDialog(null, "Enter your Name", "Mine Sweeper", JOptionPane.PLAIN_MESSAGE);
					totalWins++;
					gameOver = true;
					//new PlaySound3();
					
				}
				else if (cheat == true && !gameOver) {
					JOptionPane.showMessageDialog(null, "Cheat cheat cheat! Minus all your marks!");
					cheat = false;
				}

				
			}
		}

		// revealCell Method
		public void revealCell(int thisRow, int thisCol) {
			revealed[thisRow][thisCol] = true;
			btnCells[thisRow][thisCol].setEnabled(false);
			btnCells[thisRow][thisCol].setBackground(revealedBackground);
			btnCells[thisRow][thisCol].setIcon(null);
			btnCells[thisRow][thisCol].removeMouseListener(this);
			if (!gameOver) {MouseLeftClickCount++;}
			System.out.println(MouseLeftClickCount);
			int numMines = getSurroundingMines(thisRow, thisCol);
			if (numMines == 0) {
				btnCells[thisRow][thisCol].setText("");
			} else {
				if (numMines == 1) {
					btnCells[thisRow][thisCol].setIcon(oneIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(oneIcon);
				}
				else if (numMines == 2) {
					btnCells[thisRow][thisCol].setIcon(twoIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(twoIcon);
				}
				else if (numMines == 3) {
					btnCells[thisRow][thisCol].setIcon(threeIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(threeIcon);
				}
				else if (numMines == 4) {
					btnCells[thisRow][thisCol].setIcon(fourIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(fourIcon);
				}
				else if (numMines == 5) {
					btnCells[thisRow][thisCol].setIcon(fiveIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(fiveIcon);
				}
				else if (numMines == 6) {
					btnCells[thisRow][thisCol].setIcon(sixIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(sixIcon);
				}
				else if (numMines == 7) {
					btnCells[thisRow][thisCol].setIcon(sevenIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(sevenIcon);
				}
				else if (numMines == 8) {
					btnCells[thisRow][thisCol].setIcon(eightIcon);
					btnCells[thisRow][thisCol].setDisabledIcon(eightIcon);
				}
				//btnCells[thisRow][thisCol].setText("" + numMines);
				//btnCells[thisRow][thisCol].setFont(numFont);
			}
		}

		// Recursive Method
		public void revealSurrounding(int thisRow, int thisCol) {
			for (int row = thisRow - 1; row <= thisRow + 1; row++) {
				for (int col = thisCol - 1; col <= thisCol + 1; col++) {
					if (row >= 0 && row < ROWS && col >= 0 && col < COLS && !revealed[row][col] && !mines[row][col]) {
						if (!gameOver) MouseLeftClickCount++;
						System.out.println(MouseLeftClickCount);
						// tfScore.setText(MouseLeftClickCount * numMines + ""); // update score
						revealed[row][col] = true;
						btnCells[row][col].setEnabled(false);
						// btnCells[row][col].setBackground(BGCOLOR_REVEALED);
						btnCells[row][col].setIcon(null);
						btnCells[row][col].setBackground(revealedBackground);
						btnCells[row][col].removeMouseListener(this);
						// Get surrounding mines
						int numMines = getSurroundingMines(row, col);
						if (numMines == 0) {
							btnCells[row][col].setText("");
							revealSurrounding(row, col);
						} else {
							if (numMines == 1) {
								btnCells[row][col].setIcon(oneIcon);
								btnCells[row][col].setDisabledIcon(oneIcon);
							}
							else if (numMines == 2) {
								btnCells[row][col].setIcon(twoIcon);
								btnCells[row][col].setDisabledIcon(twoIcon);
							}
							else if (numMines == 3) {
								btnCells[row][col].setIcon(threeIcon);
								btnCells[row][col].setDisabledIcon(threeIcon);
							}
							else if (numMines == 4) {
								btnCells[row][col].setIcon(fourIcon);
								btnCells[row][col].setDisabledIcon(fourIcon);
							}
							else if (numMines == 5) {
								btnCells[row][col].setIcon(fiveIcon);
								btnCells[row][col].setDisabledIcon(fiveIcon);
							}
							else if (numMines == 6) {
								btnCells[row][col].setIcon(sixIcon);
								btnCells[row][col].setDisabledIcon(sixIcon);
							}
							else if (numMines == 7) {
								btnCells[row][col].setIcon(sevenIcon);
								btnCells[row][col].setDisabledIcon(sevenIcon);
							}
							else if (numMines == 8) {
								btnCells[row][col].setIcon(eightIcon);
								btnCells[row][col].setDisabledIcon(eightIcon);
							}
							//btnCells[row][col].setText("" + numMines);
							//btnCells[row][col].setFont(numFont);
						}
					}
				}
			}
		}

		// Get mines Method
		public int getSurroundingMines(int rowSelected, int colSelected) {
			// Set hint of the revealed cell
			int hint = 0;
			// Check if the cell[0][0]
			if (rowSelected == 0 && colSelected == 0) {
				for (int rowCheck = 0; rowCheck <= 1; rowCheck++) {
					for (int colCheck = 0; colCheck <= 1; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the cell[0][COLS - 1] selected
			} else if (rowSelected == 0 && colSelected == COLS - 1) {
				for (int rowCheck = 0; rowCheck <= 1; rowCheck++) {
					for (int colCheck = -1; colCheck <= 0; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the cell[ROWS - 1][0] selected
			} else if (rowSelected == ROWS - 1 && colSelected == 0) {
				for (int rowCheck = -1; rowCheck <= 0; rowCheck++) {
					for (int colCheck = 0; colCheck <= 1; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the cell[ROWS - 1][COLS - 1] selected
			} else if (rowSelected == ROWS - 1 && colSelected == COLS - 1) {
				for (int rowCheck = -1; rowCheck <= 0; rowCheck++) {
					for (int colCheck = -1; colCheck <= 0; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the other first row elements are selected
			} else if (rowSelected == 0) {
				for (int rowCheck = 0; rowCheck <= 1; rowCheck++) {
					for (int colCheck = -1; colCheck <= 1; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the other last row elements are selected
			} else if (rowSelected == ROWS - 1) {
				for (int rowCheck = -1; rowCheck <= 0; rowCheck++) {
					for (int colCheck = -1; colCheck <= 1; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the first column is selected
			} else if (colSelected == 0) {
				for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
					for (int colCheck = 0; colCheck <= 1; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// Check if the last column is selected
			} else if (colSelected == COLS - 1) {
				for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
					for (int colCheck = -1; colCheck <= 0; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
				// For all other cells
			} else {
				for (int rowCheck = -1; rowCheck <= 1; rowCheck++) {
					for (int colCheck = -1; colCheck <= 1; colCheck++) {
						if (mines[rowSelected + rowCheck][colSelected + colCheck]) {
							hint++;
						}
					}
				}
			}
			return hint;
		}

		// generate mines
		public void generateMines() {
			if (cheat == true) {
				numMines = 99;
				JOptionPane.showMessageDialog(null, "CODING ALSO WANT TO CHEAT!!!\n-Prof Chua 2021");
				dispose();
				new StartPage();
			} else if (difficulty == 1 && cheat == false) {
				numMines = 10;
			} else if (difficulty == 2 && cheat == false) {
				numMines = 20;
			} else if (difficulty == 3 && cheat == false) {
				numMines = 30;
			}
			int minRandomCord = 0;
			int maxRandomCord = ROWS - 1;
			minesCordsX = new int[numMines];
			minesCordsY = new int[numMines];
			for (int count = 1; count <= numMines;) {
				int randomX = randomBucket.nextInt(maxRandomCord - minRandomCord + 1) + minRandomCord; // Generates a
																										// random number
																										// for X-cord
				int randomY = randomBucket.nextInt(maxRandomCord - minRandomCord + 1) + minRandomCord; // Generates a
																										// random number
																										// for Y-cord
				if (mines[randomX][randomY] == false && (randomX != rowSelected || randomY != colSelected)) {
					if (!(randomX == rowSelected && randomY == colSelected)) { // Prevents a bomb from being generated
																				// at the first click
						mines[randomX][randomY] = true;
						minesCordsX[count - 1] = randomX;
						minesCordsY[count - 1] = randomY;
						count++; // Only increase count if there is no bomb at the random location
					}
				}
			}
			// Debugging mines
			System.out.println("Bombs made at: ");
			for (int count = 0; count <= numMines - 1; count++) {
				System.out.printf("[%d][%d] ", minesCordsX[count], minesCordsY[count]);
			}
			System.out.println();
		}
	}
	
	private class ClockListener implements ActionListener
	{
	    private int minutes;
	    private int seconds;
	    private int milliseconds;

	    @Override
	    public void actionPerformed(ActionEvent e) 
	    {
	        SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");

	        if (milliseconds == 100) 
	        {
	            milliseconds = 000;
	            seconds++;
	        }
	        if (seconds == 60) {
	            seconds = 00;
	            minutes++;
	        }

	        tfClock.setText(String.valueOf("" + seconds));
	        milliseconds++;
	    }
	}


	private class SettingsPage extends JFrame {
		// Variables
		private JButton btnDifficulty, btnCheat;
		private Listener Lis = new Listener();

		public SettingsPage() {
			try {
				setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\settings.PNG")))));
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Add Menu Bar
			JMenuBar menuBar; // the menu-bar
			JMenu menu; // each menu in the menu-bar
			JMenuItem menuItem; // an item in a menu

			// Constructors
			menuBar = new JMenuBar();

			// First Menu
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_CONTROL); // alt short-cut key
			menuItem = new JMenuItem("Main Menu", KeyEvent.VK_M);
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new StartPage();
					dispose();
				}
			});
			menuBar.add(menu); // the menu-bar adds this menu
			menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new MineSweeper();
					dispose();
				}
			});

			// Second Menu
			menu = new JMenu("Help");
			menu.setMnemonic(KeyEvent.VK_H); // short-cut key
			menuBar.add(menu); // the menu bar adds this menu

			menuItem = new JMenuItem("Rules", KeyEvent.VK_R);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new HelpPage();
					//dispose();
				}
			});
			
			
			setJMenuBar(menuBar);
			// Build container
			Container cp = this.getContentPane();
			JPanel Panel0 = new JPanel();
			JPanel Panel1 = new JPanel();
			JPanel Panel2 = new JPanel();
			Panel0.setLayout(new BoxLayout(Panel0, BoxLayout.PAGE_AXIS));
			Panel1.setLayout(new FlowLayout());
			Panel2.setLayout(new FlowLayout());
			
			Dimension difficultyButtonDim = new Dimension(200,60);
			
			Panel0.setBackground(new Color(86,124,220));
			Panel1.setBackground(new Color(86,124,220));
			Panel2.setBackground(new Color(86,124,220));
			
			if (difficulty == 1 && cheat == false) {
				// Difficulty Label
				JLabel lblDiff = new JLabel();
				lblDiff.setPreferredSize(difficultyButtonDim);
				lblDiff.setIcon(difficultyIcon);
				Panel1.add(lblDiff);
				// Difficulty TextField EASY

				btnDifficulty = new JButton();
				btnDifficulty.addActionListener(Lis);
				btnDifficulty.setPreferredSize(difficultyButtonDim);
				btnDifficulty.setIcon(easyIcon);
				btnDifficulty.setBorder(null);
				Panel1.add(btnDifficulty);

				// Cheats Label
				JLabel lblCheat = new JLabel();
				lblCheat.setPreferredSize(difficultyButtonDim);
				lblCheat.setIcon(cheatsIcon);
				Panel2.add(lblCheat);
				// Cheats TextField NO
				btnCheat = new JButton();
				btnCheat.setIcon(noIcon);
				btnCheat.setBorder(null);
				btnCheat.addActionListener(Lis);
				btnCheat.setPreferredSize(difficultyButtonDim);
				Panel2.add(btnCheat);
				
			} else if (difficulty == 2 && cheat == false) {
				// Difficulty Label
				JLabel lblDiff = new JLabel();
				lblDiff.setPreferredSize(difficultyButtonDim);
				lblDiff.setIcon(difficultyIcon);
				Panel1.add(lblDiff);
				
				// Difficulty TextField MEDIUM
				btnDifficulty = new JButton();
				btnDifficulty.addActionListener(Lis);
				btnDifficulty.setPreferredSize(difficultyButtonDim);
				btnDifficulty.setIcon(mediumIcon);
				btnDifficulty.setBorder(null);
				Panel1.add(btnDifficulty);

				// Cheats Label
				JLabel lblCheat = new JLabel();
				lblCheat.setPreferredSize(difficultyButtonDim);
				lblCheat.setIcon(cheatsIcon);
				Panel2.add(lblCheat);
				// Cheats TextField NO
				btnCheat = new JButton();
				btnCheat.setIcon(noIcon);
				btnCheat.setBorder(null);
				btnCheat.addActionListener(Lis);
				Panel2.add(btnCheat);
			} else if (difficulty == 3 && cheat == false) {
				// Difficulty Label
				JLabel lblDiff = new JLabel();
				lblDiff.setPreferredSize(difficultyButtonDim);
				lblDiff.setIcon(difficultyIcon);
				Panel1.add(lblDiff);
				// Difficulty TextField
				btnDifficulty = new JButton();
				btnDifficulty.addActionListener(Lis);
				btnDifficulty.setPreferredSize(difficultyButtonDim);
				btnDifficulty.setIcon(hardIcon);
				btnDifficulty.setBorder(null);
				Panel1.add(btnDifficulty);

				// Cheats Label
				JLabel lblCheat = new JLabel();
				lblCheat.setPreferredSize(difficultyButtonDim);
				lblCheat.setIcon(cheatsIcon);
				Panel2.add(lblCheat);
				// Cheats TextField
				btnCheat = new JButton();
				btnCheat.setIcon(noIcon);
				btnCheat.setBorder(null);
				btnCheat.addActionListener(Lis);
				Panel2.add(btnCheat);
			} else if (difficulty == 1 && cheat == true) {
				// Difficulty Label
				JLabel lblDiff = new JLabel();
				lblDiff.setPreferredSize(difficultyButtonDim);
				lblDiff.setIcon(difficultyIcon);
				Panel1.add(lblDiff);
				// Difficulty TextField EASY
				btnDifficulty = new JButton();
				btnDifficulty.addActionListener(Lis);
				btnDifficulty.setPreferredSize(difficultyButtonDim);
				btnDifficulty.setIcon(easyIcon);
				btnDifficulty.setBorder(null);
				Panel1.add(btnDifficulty);

				// Cheats Label
				JLabel lblCheat = new JLabel();
				lblCheat.setPreferredSize(difficultyButtonDim);
				lblCheat.setIcon(cheatsIcon);
				Panel2.add(lblCheat);
				// Cheats TextField YES
				btnCheat = new JButton();
				btnCheat.setIcon(yesIcon);
				btnCheat.setBorder(null);
				btnCheat.addActionListener(Lis);
				Panel2.add(btnCheat);
			} else if (difficulty == 2 && cheat == true) {
				// Difficulty Label
				JLabel lblDiff = new JLabel();
				lblDiff.setPreferredSize(difficultyButtonDim);
				lblDiff.setIcon(difficultyIcon);
				Panel1.add(lblDiff);
				// Difficulty TextField
				btnDifficulty = new JButton();
				btnDifficulty.setPreferredSize(difficultyButtonDim);
				btnDifficulty.setIcon(mediumIcon);
				btnDifficulty.setBorder(null);
				btnDifficulty.addActionListener(Lis);
				Panel1.add(btnDifficulty);

				// Cheats Label
				JLabel lblCheat = new JLabel();
				lblCheat.setPreferredSize(difficultyButtonDim);
				lblCheat.setIcon(cheatsIcon);
				Panel2.add(lblCheat);
				// Cheats TextField YES
				btnCheat = new JButton();
				btnCheat.setIcon(yesIcon);
				btnCheat.setBorder(null);
				btnCheat.addActionListener(Lis);
				Panel2.add(btnCheat);
			} else if (difficulty == 3 && cheat == true) {
				// Difficulty Label
				JLabel lblDiff = new JLabel();
				lblDiff.setPreferredSize(difficultyButtonDim);
				lblDiff.setIcon(difficultyIcon);
				Panel1.add(lblDiff);
				// Difficulty TextField HARD
				btnDifficulty = new JButton();
				btnDifficulty.setPreferredSize(difficultyButtonDim);
				btnDifficulty.addActionListener(Lis);
				btnDifficulty.setIcon(hardIcon);
				btnDifficulty.setBorder(null);
				Panel1.add(btnDifficulty);

				// Cheats Label
				JLabel lblCheat = new JLabel();
				lblCheat.setPreferredSize(difficultyButtonDim);
				lblCheat.setIcon(cheatsIcon);
				Panel2.add(lblCheat);
				// Cheats TextField YES
				btnCheat = new JButton();
				btnCheat.setIcon(yesIcon);
				btnCheat.setBorder(null);
				btnCheat.addActionListener(Lis);
				Panel2.add(btnCheat);
			}

			// Back Button
			JPanel pnlBack = new JPanel();
			pnlBack.setLayout(new FlowLayout());
			pnlBack.setBackground(plainBlue);
			btnBack = new JButton();
			btnBack.setIcon(backIcon);
			btnBack.setBorder(null);
			btnBack.addActionListener(Lis);
			btnBack.setPreferredSize(difficultyButtonDim);
			pnlBack.add(btnBack);

			// Add Panel into Frame
			cp.setLayout(new GridBagLayout());
			Panel0.add(Panel1);
			Panel0.add(Panel2);
			Panel0.add(pnlBack);
			cp.add(Panel0);

			// Frame setup
			setTitle("Settings");
			setVisible(true);
			setBackground(Color.DARK_GRAY);
			cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
			pack();
			
			

			// Centralised
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		}

		// Inner TextField Listener Class
		private class Listener extends WindowAdapter implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton)e.getSource(); 
	               // Get a reference of the source that has fired the event. 
	               // getSource() returns a java.lang.Object. Downcast back to Button. 
				if (source == btnBack) {
					//PlaySound.PlaySoundClose();
					new StartPage();
					dispose();
				}
			
				else if (source == btnCheat && btnCheat.getIcon() == noIcon) {
					JOptionPane.showMessageDialog(null, "ARE YOU SURE YOU WANNA CHEAT\nProf Chua is watching");
					cheat = true;
					btnCheat.setIcon(yesIcon);
				}
				else if (source == btnCheat && btnCheat.getIcon() == yesIcon) {
					btnCheat.setIcon(noIcon);
					cheat = false;
				}
	
			/*
			 * if (e.getActionCommand().equalsIgnoreCase("Back")) {
					new StartPage();
					dispose();
				} 
				else if (btnCheat.getIcon() == noIcon) {
					JOptionPane.showMessageDialog(null, "ARE YOU SURE YOU WANNA CHEAT\nProf Chua is watching");
					cheat = true;
					btnCheat.setIcon(yesIcon);
				}
				else if(btnCheat.getIcon() == yesIcon) {
					btnCheat.setIcon(noIcon);
					cheat = false;
				}
				else if (e.getActionCommand().equalsIgnoreCase("No")) {
					JOptionPane.showMessageDialog(null, "ARE YOU SURE YOU WANNA CHEAT\nProf Chua is watching");
					cheat = true;
					//btnCheat.setText("YES");
					btnCheat.setIcon(yesIcon);
				} 
				 else if (e.getActionCommand().equalsIgnoreCase("YES")) {
					btnCheat.setIcon(noIcon);
					cheat = false;
				}*/

				else if (source == btnDifficulty && btnDifficulty.getIcon() == easyIcon) {
					System.out.println("Changing from easy to medium");
					btnDifficulty.setIcon(mediumIcon);
					difficulty = 2;
				}
				else if (source == btnDifficulty && btnDifficulty.getIcon() == mediumIcon) {
					System.out.println("Changing from medium to hard");
					btnDifficulty.setIcon(hardIcon);
					difficulty = 3;
				}
				else if (source == btnDifficulty && btnDifficulty.getIcon() == hardIcon) {
					System.out.println("Changing from hard to easy");
					btnDifficulty.setIcon(easyIcon);
					difficulty = 1;
				}
				
			}
		}
	}

	private class StartPage extends JFrame {
		// Variables
		private JButton btnNewGame, btnSettings, btnHighScore, btnQuit;
		private ButtonListener lis = new ButtonListener();
		private JPanel pnlNew = new JPanel();
		private JPanel pnlSet = new JPanel();
		private JPanel pnlHigh = new JPanel();
		private JPanel pnlQuit = new JPanel();

		// Constructors
		public StartPage() {
			//new PlaySound();
			// Add Menu Bar
			JMenuBar menuBar; // the menu-bar
			JMenu menu; // each menu in the menu-bar
			JMenuItem menuItem; // an item in a menu

			// Constructors
			menuBar = new JMenuBar();

			// First Menu
			try {
				setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\finalmain.PNG")))));
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_CONTROL); // alt short-cut key
			menuBar.add(menu); // the menu-bar adds this menu
			menuItem = new JMenuItem("Main Menu", KeyEvent.VK_M);
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new StartPage();
					dispose();
				}
			});
			menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new MineSweeper();
					dispose();
				}
			});

			// Second Menu
			menu = new JMenu("Help");
			menu.setMnemonic(KeyEvent.VK_H); // short-cut key
			menuBar.add(menu); // the menu bar adds this menu

			menuItem = new JMenuItem("Rules", KeyEvent.VK_R);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new HelpPage();
					//dispose();
				}
			});
			setJMenuBar(menuBar);

			Container cp = this.getContentPane();
			// set all smaller panels
			pnlNew.setLayout(new FlowLayout());
			pnlSet.setLayout(new FlowLayout());
			pnlHigh.setLayout(new FlowLayout());
			pnlQuit.setLayout(new FlowLayout());

			// Main Panel
			JPanel btnPanel = new JPanel();
			btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.PAGE_AXIS));
			
			

			// New Game Button
			btnNewGame = new JButton("New Game");
			btnNewGame.addActionListener(lis);
			btnNewGame.setPreferredSize(startPageButtons);
			btnNewGame.setIcon(newGameIcon);
			pnlNew.add(btnNewGame);
			btnPanel.add(pnlNew);
			// Settings Button
			btnSettings = new JButton("Settings");
			btnSettings.addActionListener(lis);
			btnSettings.setPreferredSize(startPageButtons);
			btnSettings.setIcon(settingsIcon);
			pnlSet.add(btnSettings);
			btnPanel.add(pnlSet);
			// High score Button
			btnHighScore = new JButton("High Score");
			btnHighScore.addActionListener(lis);
			btnHighScore.setPreferredSize(startPageButtons);
			btnHighScore.setIcon(highScoreButtonIcon);
			pnlHigh.add(btnHighScore);
			btnPanel.add(pnlHigh);
			// Quit Button
			btnQuit = new JButton("Quit");
			btnQuit.addActionListener(lis);
			btnQuit.setPreferredSize(startPageButtons);
			btnQuit.setIcon(quitbuttonIcon);
			pnlQuit.add(btnQuit);
			btnPanel.add(pnlQuit);

			cp.setLayout(new GridBagLayout());
			cp.add(btnPanel);
			// Source ->
			// https://stackoverflow.com/questions/41403166/java-gui-how-to-center-a-single-button

			// Frame setup
			setTitle("Mine Sweeper");
			setVisible(true);
			cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
			setBackground(plainBlue);
			pack();

			// Centralised
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		}

		// Define the ButtonListener Inner Class for Reset Button and New Game Button
		private class ButtonListener extends WindowAdapter implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equalsIgnoreCase("new game")) {
					dispose();
					new MineSweeper();
				} else if (e.getActionCommand().equalsIgnoreCase("settings")) {
					new SettingsPage();
					dispose();
				} else if (e.getActionCommand().equalsIgnoreCase("high score")) {
					new HighScorePage();
					dispose();
				} else if (e.getActionCommand().equalsIgnoreCase("quit")) {
					//PlaySound.PlaySoundClose();
					dispose();
					
				}
			}
		}
	}

	private class HelpPage extends JFrame {
		// Variables
		private JLabel lblHelp;

		// Constructor
		public HelpPage() {
			//PlaySound.PlaySoundClose();
			try {
				setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\help.PNG")))));
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Add Menu Bar
			JMenuBar menuBar; // the menu-bar
			JMenu menu; // each menu in the menu-bar
			JMenuItem menuItem; // an item in a menu

			// Constructors
			menuBar = new JMenuBar();

			// First Menu
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_CONTROL); // alt short-cut key
			menuBar.add(menu); // the menu-bar adds this menu
			menuItem = new JMenuItem("Main Menu", KeyEvent.VK_M);
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new StartPage();
					dispose();
				}
			});
			menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new MineSweeper();
					dispose();
				}
			});

			// Second Menu
			menu = new JMenu("Help");
			menu.setMnemonic(KeyEvent.VK_H); // short-cut key
			menuBar.add(menu); // the menu bar adds this menu

			menuItem = new JMenuItem("Rules", KeyEvent.VK_R);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new HelpPage();
					//dispose();
				}
			});
			setJMenuBar(menuBar);

			// Build container
			Container cp = getContentPane();
			cp.setLayout(new FlowLayout());
			// Build Label for help
			lblHelp = new JLabel("The numbers on the board represent how many bombs are adjacent to a square.");
			cp.add(lblHelp);
			lblHelp = new JLabel(
					"For example, if a square has a \"3\" on it, then there are 3 bombs next to that square.");
			cp.add(lblHelp);
			lblHelp = new JLabel("The bombs could be above, below, right left, or diagonal to the square.");
			cp.add(lblHelp);
			lblHelp = new JLabel("Avoid all the bombs and expose all the empty spaces to win Minesweeper.");
			cp.add(lblHelp);
			lblHelp = new JLabel("Tip: Use the numbers to determine where you know a bomb is.");
			cp.add(lblHelp);
			lblHelp = new JLabel(
					"Tip: You can right click a square with the mouse to place a flag where you think a bomb is.");
			cp.add(lblHelp);
			lblHelp = new JLabel("This allows you to avoid that spot.");
			cp.add(lblHelp);
			lblHelp = new JLabel("score the most points and have fun!");
			cp.add(lblHelp); // Source -> https://www.ducksters.com/games/minesweeper.php
			JPanel btnPanel = new JPanel();
			btnPanel.setLayout(new FlowLayout());
			
			//BACK TO GAME BUTTON
			JButton btnBackToGame = new JButton();
			btnBackToGame.setIcon(gameIcon);
			btnBackToGame.setBorder(null);
			btnBackToGame.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			//BACK TO MAIN MENU BUTTON
			btnBackToGame.setPreferredSize(new Dimension(200,60));
			btnPanel.add(btnBackToGame);
			JButton btnBack = new JButton();
			btnBack.setIcon(mainMenuIcon);
			btnBack.setBorder(null);
			btnBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//PlaySound.PlaySoundClose();
					new StartPage();
					dispose();
				}

			});
			btnBack.setPreferredSize(new Dimension(200, 60));
			btnPanel.add(btnBack);
			btnPanel.setBackground(plainBlue);
			cp.add(btnPanel);
			// Frame setup
			setTitle("Help");
			setVisible(true);
			cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
			setBackground(Color.DARK_GRAY);
			pack();

			// Centralised
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		}
	}

	private class HighScorePage extends JFrame {

		public HighScorePage() {
			
		
			try {
				setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\highscore.PNG")))));
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Add Menu Bar
			JMenuBar menuBar; // the menu-bar
			JMenu menu; // each menu in the menu-bar
			JMenuItem menuItem; // an item in a menu

			// Constructors
			menuBar = new JMenuBar();

			// First Menu
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_CONTROL); // alt short-cut key
			menuBar.add(menu); // the menu-bar adds this menu
			menuItem = new JMenuItem("Main Menu", KeyEvent.VK_M);
			menu.add(menuItem);
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new StartPage();
					dispose();
				}
			});
			menuItem = new JMenuItem("New Game", KeyEvent.VK_N);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new MineSweeper();
					dispose();
				}
			});

			// Second Menu
			menu = new JMenu("Help");
			menu.setMnemonic(KeyEvent.VK_H); // short-cut key
			menuBar.add(menu); // the menu bar adds this menu

			menuItem = new JMenuItem("Rules", KeyEvent.VK_R);
			menu.add(menuItem); // the menu adds this item
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new HelpPage();
					dispose();
				}
			});
			setJMenuBar(menuBar);

			Container cp = getContentPane();
			cp.setLayout(new GridBagLayout());
			JPanel pnlBox = new JPanel();
			pnlBox.setLayout(new BoxLayout(pnlBox, BoxLayout.PAGE_AXIS));
			JPanel pnlLabel = new JPanel();
			pnlLabel.setPreferredSize(new Dimension(500,300));
			pnlLabel.setLayout(new FlowLayout());
			pnlLabel.setBackground(plainBlue);
			JPanel pnlButton = new JPanel();
			pnlButton.setLayout(new FlowLayout());
			JLabel lbl1 = new JLabel("");

			lbl1.setPreferredSize(new Dimension(450, 80));
			lbl1.setFont(highScoreFont);
			if (totalWins >= 1) {
				lbl1.setText(inStr + ": " + tfClock.getText() + " seconds" );
				try {
					Formatter highScoreWriter = new Formatter(new File("highscore.txt"));
					highScoreWriter.format("%s: %s%n" ,inStr, tfClock.getText());
					highScoreWriter.format("Marcel: 25%n");
					highScoreWriter.format("LiYao: 34%n");
					highScoreWriter.format("Marcel: 54%n");
					highScoreWriter.format("Marcel: 65%n");
					highScoreWriter.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else {
				try {
					Scanner scoreReader = new Scanner(new File("highscore.txt"));
					String display = scoreReader.nextLine();
					lbl1.setText(display);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			
			pnlLabel.add(lbl1);
			pnlBox.add(pnlLabel);
			
			//BACK BUTTON
			JButton btnBack = new JButton();
			btnBack.setPreferredSize(new Dimension(200,60));
			btnBack.setBorder(null);
			btnBack.setIcon(backIcon);
			btnBack.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//PlaySound.PlaySoundClose();
					dispose();
					new StartPage();
				}

			});
			pnlButton.add(btnBack);
			pnlButton.setBackground(plainBlue);
			pnlBox.add(pnlButton);
			pnlBox.setBackground(plainBlue);
			cp.add(pnlBox);
			// Frame setup
			setTitle("High Score");
			setVisible(true);
			cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
			setBackground(Color.DARK_GRAY);
			pack();


			// Centralised
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		}
	}
}