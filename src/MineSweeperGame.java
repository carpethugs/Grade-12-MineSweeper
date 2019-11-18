/**Name: Daniel Mezhibovski
 * Date: 1/21/2018
 * Summary: Outlines variables, methods and behaviors of the game and creates the JFrame
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JMenu;
import javax.swing.JOptionPane;

public class MineSweeperGame extends MGame
{
	private static MineSweeperGame m = new MineSweeperGame();//Creates new game
	private static final int WIDTH=610;//Defines width of frame
	private static final int HEIGHT=650;//Defines height of frame
	private static final int TILES=9;//Defines how many tiles per side
	public static int buttonSize=(WIDTH-10)/TILES+1;
	private static int lim=TILES-1;//Defines limit for methods that check surrounding mines
	private static int numMines=12;//Defines number of mines in the game
	private static int numMinesLeft=numMines;//Defines the number of mines left in the game 
	private static GameButton[][] bGrid=new GameButton[TILES][TILES];//Creates new GameButton 2d array with size TILExTILE that will hold buttons
	private static boolean[][] mineLocation= new boolean[TILES][TILES];//Creates new boolean 2d array with size TILExTILE that will hold mine locations
	private static int[][] numbers= new int[TILES][TILES];//Creates new int 2d array with size TILExTILE that will hold the number values of tiles
	private static BufferedPics pics=new BufferedPics();//Creates new BufferedPics object
	private static BufferedImage[] picList=pics.getBufferedPics();//Creates new BufferedImage array that holds all images in BufferedPics
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//Creates dimension object that holds screensize
	private static int sWidth = (int) screenSize.getWidth();//Creates int that is screenwidth
	private static int sHeight = (int) screenSize.getHeight();//Creates in that is screenheight
//	private static JMenu menuInst = new JMenu("Instructions");
	/**
	 * Main method which starts the game and sets up the JFrame
	 * @param args
	 */
	public static void main(String[] args) 
	{
		startPane();
		setUp();//Runs setup
		m.setSize(WIDTH, HEIGHT);//Sets size of window
		m.setVisible(true);//Makes Visible
		m.setLocation(sWidth/2-WIDTH/2,sHeight/2-HEIGHT/2);//Sets location of window in middle of screen
		m.setResizable(false);//Turns off resizability
		m.initComponents();//Initializes components from mGame
//		m.add(menuInst);
	}
	/**
	 * Method that sets up the components of the minesweeper game, like the mine locations, the buttons and the grid numbers
	 */
	public static void setUp()
	{
		addMines();//Adds mines
		gridNums();//Adds gridnums
//		printArray(mineLocation);
//		printArray(numbers);
		addButtons();//Adds Buttons
		m.repaint();//Repaints window
	}
	/**
	 * addButtons() generates and adds a new button for every x,y coordinate
	 */
	public static void addButtons()
	{
		for(int y=0;y<TILES;y++)
		{
			for(int x =0;x<TILES;x++)
			{
				bGrid[x][y]=new GameButton(buttonSize,x*buttonSize,y*buttonSize,mineLocation[x][y],numbers[x][y]);//Creates new button in x,y location of bGrid that passes size location, if its a mine and number
				m.add(bGrid[x][y]);//Adds button to pane
//				System.out.println("x = "+x+" y= "+y+" is "+ bGrid[x][y].isMine());
			}
			
		}
	}
	/**
	 * Deletes all buttons from grid when reseting game
	 */
	public static void deleteGrid()
	{
		for(int y=0;y<TILES;y++)
		{
			for(int x =0;x<TILES;x++)
			{
				m.remove(bGrid[x][y]);//Removes button from x,y location in bGrid
			}
			
		}
	}
	/**
	 * Generates mines in random places of the grid 
	 */
	public static void addMines()
	{
		int count=0;//Creates counter
		while(count<numMines)//Loops till counter is >= to number of mines
		{
			int x = (int)(Math.random()*TILES);//Picks random x coordinate
			int y = (int)(Math.random()*TILES);//Picks random y coordinate
			if(mineLocation[x][y]==false)//If that location isnt already a mine
			{
				mineLocation[x][y]=true;//Make it a mine
				count++;//Increment counter by one
			}
		}
		
	}
	/**
	 * Empties the entire grid of mines 
	 */
	public static void deleteMines()
	{
		for(int y=0;y<TILES;y++)
		{
			for(int x=0;x<TILES;x++)
			{
				mineLocation[x][y]=false;//Sets all coordinates in mineLocation to false(no mines)
			}
		}	
	}
	/**
	 * Checks every grid tile to see how many mines surrounds it and then adds that number to the corresponding place in numbers[][]
	 */
	public static void gridNums()
	{
		for(int y=0;y<TILES;y++)
		{
			for(int x=0;x<TILES;x++)
			{
				if(mineLocation[x][y]==false)//If coordinate isnt mine
					numbers[x][y]=getNumMines(x,y);//Gets int value for number of surrounding mines and puts it at x,y of numbers[][]
				else
					numbers[x][y]=9;//Uses 9 to represent mines
			}
		}
	}
	/**
	 * Checks an individual tile for how many mines surround it
	 * @param x x location
	 * @param y y location
	 * @return The number of mines surrounding tile
	 */
	public static int getNumMines(int x,int y)//Counts surrounding mines
	{
		int n=TILES;
		if(y==0)
		{
			if(x==0)//Where x and y are 0
				return isMine(1,1)+isMine(0,1)+isMine(1,0);
			else if(x==n-1)//Where y is 0 and x is the last tile
				return isMine(n-1,1)+isMine(n-2,1)+isMine(n-2,0);
			else//Where y is 0 and x is in between 0 and the last
				return isMine(x-1,0)+isMine(x+1,0)+isMine(x-1,1)+isMine(x,1)+isMine(x+1,1);
		}
		else if(y==n-1)
		{
			if(x==0)//Where y is last and x is 0
				return isMine(0,n-2)+isMine(1,n-2)+isMine(1,n-1);
			else if(x==n-1)//Where y and x are last
				return isMine(n-1,n-2)+isMine(n-2,n-2)+isMine(n-2,n-1);
			else//Where y is last and x is in between 0 and last
				return isMine(x,y-1)+isMine(x+1,y-1)+isMine(x-1,y-1)+isMine(x-1,y)+isMine(x+1,y);
		}
		else if(x==0)//When x is 0 and y is in between 0 and last
		{
			return isMine(0,y-1)+isMine(0,y+1)+isMine(1,y-1)+isMine(1,y)+isMine(1,y+1);
		}
		else if(x==n-1)//When x is last and y is in between 0 and last
		{
			return isMine(n-1,y-1)+isMine(n-1,y+1)+isMine(n-2,y-1)+isMine(n-2,y)+isMine(n-2,y+1);
		}
		else//Everywhere inbetween
		{
			return isMine(x-1,y-1)+isMine(x-1,y)+isMine(x-1,y+1)+isMine(x,y-1)+isMine(x+1,y-1)+isMine(x+1,y)+isMine(x+1,y+1)+isMine(x,y+1);
		}			
	}
	/**
	 * Recursive method that opens up empty cells touching it, then reruns for the opened empty cells
	 * @param x x location
	 * @param y y location
	 */
	public static void sweep(int x,int y)
	{
                if(!bGrid[x][y].getIsClicked()&&numbers[x][y]==0&&!bGrid[x][y].flagged())//If button is not clicked and the number value is zero and the button isnt flagged
                {
                	bGrid[x][y].checkButton(x, y,true);//Checks the cell at x,y and true for first 
                	if(x>0&&y>0&&x<lim&&y<lim)//If x and y are greater than 0 and less than the limit
                	{
                		bGrid[x][y-1].checkButton(x,y-1,false);
                		bGrid[x][y+1].checkButton(x,y+1,false);
                		bGrid[x-1][y].checkButton(x-1,y,false);
                		bGrid[x+1][y].checkButton(x+1,y,false);
                		bGrid[x-1][y-1].checkButton(x-1,y-1,false);
                		bGrid[x+1][y+1].checkButton(x+1,y+1,false);
                		bGrid[x-1][y+1].checkButton(x-1,y+1,false);
                		bGrid[x+1][y-1].checkButton(x+1,y-1,false);
                	}
                	else if(y>0&&x<lim&&y<lim)//If y is greater than 0 and x and y are less than the limit
                	{
                		bGrid[x][y-1].checkButton(x,y-1,false);
                		bGrid[x][y+1].checkButton(x,y+1,false);
                		bGrid[x+1][y].checkButton(x+1,y,false);
                		bGrid[x+1][y+1].checkButton(x+1,y+1,false);
                		bGrid[x+1][y-1].checkButton(x+1,y-1,false);
                	}
                	else if(x>0&&x<lim&&y<lim)//If x is greater than zero and x and y are less that the limit
                	{
                		bGrid[x][y+1].checkButton(x,y+1,false);
                		bGrid[x-1][y].checkButton(x-1,y,false);
                		bGrid[x+1][y].checkButton(x+1,y,false);
                		bGrid[x+1][y+1].checkButton(x+1,y+1,false);
                		bGrid[x-1][y+1].checkButton(x-1,y+1,false);
                	}
                	else if(x>0&&y>0&&x<lim)//If x and y are greater than zero and x is less than the limit
                	{
                		bGrid[x][y-1].checkButton(x,y-1,false);
                		bGrid[x-1][y].checkButton(x-1,y,false);
                		bGrid[x+1][y].checkButton(x+1,y,false);
                		bGrid[x-1][y-1].checkButton(x-1,y-1,false);
                		bGrid[x+1][y-1].checkButton(x+1,y-1,false);
                	}
                	else if(x>0&&y>0&&y<lim)//If x and y are greater than zero and y is less than the limit
                	{
                		bGrid[x][y-1].checkButton(x,y-1,false);
                		bGrid[x][y+1].checkButton(x,y+1,false);
                		bGrid[x-1][y].checkButton(x-1,y,false);
                		bGrid[x-1][y-1].checkButton(x-1,y-1,false);
                		bGrid[x-1][y+1].checkButton(x-1,y+1,false);
                	}   
                }
                else if(!bGrid[x][y].getIsClicked())//If the value isnt 0
                	bGrid[x][y].checkButton(x, y,true);
	}
	/**
	 * Checks a particular tile if it is a mine
	 * @param x x location
	 * @param y y location
	 * @return 1 for mine and 0 for no mine
	 */
	public static int isMine(int x, int y)
	{
		if(mineLocation[x][y]==true)//Checks to see if given location on grid is a mine
			return 1;//Return 1 if mine
		return 0;//Return 0 if not mine
	}
	/**
	 * Either increases or decreases variable numMinesLeft
	 * @param down true for decrement, false for increment 
	 */
	public static void changeMinesLeft(boolean down)
	{
		if(down)//If decreasing 
			numMinesLeft--;//Decrement numMinesLeft by one
		else
			numMinesLeft++;//Otherwise increment by one
//		System.out.println(numMinesLeft);
	}
	/**
	 * Resets numMinesLeft back to original number
	 */
	public static void resetMinesLeft()
	{
		numMinesLeft=numMines;//Resets numMunes left back to numMines
	}
	/**
	 * Returns BufferedImage[] picList
	 * @return picList
	 */
	public static BufferedImage[] getPicList()
	{
		return picList;//Returns the BufferedImage piclist
	}
	/**
	 * Opens up all the mine locations and opens optionPane for loss
	 */
	public static void loseGame()
	{
		for(int x=0;x<TILES;x++)
		{
			for(int y=0;y<TILES;y++)
			{
				bGrid[x][y].openMine();//If x,y coordinate is a mine, runs GameButton's openMine method
			}
		}
		optionPane("YOU LOSE");//Runs optionpane with loss parameter
	}
	/**
	 * Opens optionPane for win if number of mines left is 0
	 */
	public static void winGame()
	{
		if(numMinesLeft==0)//If the number of mines left is 0 then run optionpane with win parameter
		{
			optionPane("YOU WIN");
		}
	}
	/**
	 * Opens optionPane with two buttons.
	 * If clicked yes, runs deleteGrid() and deleteMines() then reruns setUp() to restart game.
	 * If clicked no, closes game.
	 * @param s String saying win or loss
	 */
	public static void optionPane(String s)
	{
		int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", s,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		//Creates option pane with two buttons and stores user action in an int
		if (response == JOptionPane.NO_OPTION)//If the response is no the end program 
			System.exit(0); 
		else if (response == JOptionPane.YES_OPTION)//If user response is yes then run deletegrid,deletemines,resetminesleft and setup to restart game 
	    {
	    	deleteGrid();
	    	deleteMines();
	    	resetMinesLeft();
	    	setUp(); 	
	    }
	    else if (response == JOptionPane.CLOSED_OPTION) //If closes the window ends the game
	    	System.exit(0);
			    
	}
	/**
	 * Opens optionPane with intructions how to play the game
	 * @param s String saying win or loss
	 */
	public static void startPane()
	{
		Object[] options = {"OK"};
	    int n = JOptionPane.showOptionDialog(null,"Left Click to Open Tile\nRight Click to Flag Tile\nGoal: Flag All Mines Without Dying ","Instructions",JOptionPane.PLAIN_MESSAGE,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
	}
	/**
	 * Prints a boolean[][] in console
	 * @param a boolean[][]
	 */
	public static void printArray(boolean[][] a)
	{
		for(int y=0;y<a.length;y++)
		{
			for(int x=0;x<a[y].length;x++)
			{
				System.out.print(a[x][y]+" ");//Prints x,y coordinate with a space
			}
			System.out.println();//New line
		}
		System.out.println();
		
	}
	/**
	 * Prints int[][] in console
	 * @param a int[][]
	 */
	public static void printArray(int[][] a)
	{
		for(int y=0;y<a.length;y++)
		{
			for(int x=0;x<a[y].length;x++)
			{
				System.out.print(a[x][y]+" ");//Prints x,y coordinate with a space
			}
			System.out.println();//New line
		}
		
	}
}