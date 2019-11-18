/**Name: Daniel Mezhibovski
 * Date: 1/21/2018
 * Summary: Outlines variables, methods and behaviors of each button object
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

public class GameButton extends JButton
{
	private int bx,by;//Declares location variables
	private boolean isMine;//Declares mine boolean
	private boolean isFlagged=false;//Declares and sets isFlagged to false
	private boolean isClicked=false;//Declares and sets isClicked to false
	
	private int number;//Declares number int
	//Declares imageicons for the images
	private ImageIcon flag;
	private ImageIcon tile;
	private ImageIcon mine;
	private ImageIcon zero;
	private ImageIcon one;
	private ImageIcon two;
	private ImageIcon three;
	private ImageIcon four;
	private ImageIcon five;
	private ImageIcon six;
	private ImageIcon seven;
	private ImageIcon eight;
	private ImageIcon[] iconList={zero,one,two,three,four,five,six,seven,eight,flag,mine,tile};//Makes imageicon array with all previously decalred icons
	private BufferedImage[] buffList=MineSweeperGame.getPicList();//Declares and defines bufferedImage list from minesweepergame
	/**
	 * Constructor of GameButton that sets the location, size, defines if its a mine, adds mouse listener and sets the icon to a tile
	 * @param side size of sides
	 * @param x x location
	 * @param y y location
	 * @param Mine true if mine, false if not a mine
	 * @param num number value of tile
	 */
	public GameButton(int side,int x,int y,boolean Mine, int num)
	{
		super();
		number=num;//Defines number value
		bx=x/67;//Defines x location
		by=y/67;//Defiens y location
		isMine=Mine;//Defines if its a mine
		createIconList();//Runs createIconList
		setTile();//Sets the icon to a tile
		
		this.setSize(side,side);//Sets size
		this.setLocation(x, y);//Sets location
		this.setVisible(true);//Sets visibility
	    this.addMouseListener(mouseListener);//Adds mouse listener
	}
	/**
	 * Sets the button to the tile icon
	 */
	public void setTile()
	{
		this.setIcon(iconList[11]);//Sets the buttons icon to tile
	}
	/**
	 * Checks to see if button is a mine
	 * @return true if button is a mine and false if its not
	 */
	public boolean isMine()
	{
		return isMine;//Returns true if button is a mine and false if its not 
	}
	/**
	 * When button is right clicked, this method is run. It checks to see if the button is already flagged<br>
	 * and flags it or unflags it based on its previous state. <br>
	 * If button is flagged and is a mine, decreases numMinesLeft in MineSweeperGame and runs the wingame method to see if the player won 
	 */
	public void changeFlag()
	{
		if(this.isEnabled())//If button is enabled
		{
			if(isFlagged)//If flagged already
			{
				isFlagged=false;//Unflag
				if(isMine)//If its a mine
					MineSweeperGame.changeMinesLeft(false);//Run changeMinesLeft as a increment
				this.setIcon(iconList[11]);//Change icon to tile
			}
				
			else
			{
				isFlagged=true;//Flag the button
				this.setIcon(iconList[9]);//Set icon to flag
				if(isMine)//If the button is a mine
					MineSweeperGame.changeMinesLeft(true);MineSweeperGame.winGame();//Run changeMinesLeft as a decrement and run winGame to check it its a victory
			}
		}	
	}
	/**
	 * Returns true of the button is flagged and false if it is not
	 * @return true if flagged and false if not
	 */
	public boolean flagged()
	{
		return isFlagged;//Returns isFlagged 
	}
	/**
	 * Checks to see the button is flagged.<br>
	 * If flagged nothing happens<br>
	 * Otherwise, disables button, sets icon to the number value of the button,<br>
	 * sets the disabled icon to the number value too and if the number is zero it reruns the sweep method from MineSweeperGame
	 * @param x x location
	 * @param y y location
	 * @param first true if its the first button checkButton() is called on in a sweep, false if its not
	 */
	public void checkButton(int x, int y,boolean first)
	{
		if(!isFlagged)//If the button isnt flagged
		{
			this.setEnabled(false);//Turn off button
			this.setIcon(iconList[number]);//Change icon to the number
	        this.setDisabledIcon(iconList[number]);//Change disabled icon to the number
	        if(number == 0&& !first)//If the number is 0 and its the not the first tile clicked
	        {
	        	MineSweeperGame.sweep(x,y);//Run sweep again with the x,y coordinates of the checked button
	        }
	        isClicked=true;	//Changes isClicked to true
		}    
    }
	/**
	 * Returns true if the button was clicked and false if the button wasn't clicked
	 * @return true if the button was clicked and false if the button wasn't clicked
	 */
	public boolean getIsClicked()
	{
		return isClicked;//Returns isClicked
	}
	/**
	 * If the tile is a mine, disables it, changes the icon to a mine and changes the disabled icon to a mine
	 */
	public void openMine()
	{
		if(isMine)//If isMine is true
		{
			this.setEnabled(false);//Disables button
			this.setIcon(iconList[10]);//Sets icon to mine
			this.setDisabledIcon(iconList[10]);//Sets disabled icon to mine
		}	
	}
	/**
	 * Opens the tile. If its a mine, run openMine() and loseGame() from MineSweeperGame<br>
	 * If it isnt a mine, disables button, changes the icon to the number and the disabled icon to the number and runs the sweep method at the buttons coordinates
	 */
	public void openTile()
	{
		if(isMine)//If button is a mine
		{
			openMine();//Run openMine
			MineSweeperGame.loseGame();//Runs loseGame
		}
		else//If its not a mine 
		{
			this.setEnabled(false);//Disable button
	        this.setIcon(iconList[number]);//sets the icon and the disabled icon to the number
	        this.setDisabledIcon(iconList[number]);
			MineSweeperGame.sweep(bx,by);//Runs sweep at the coordinates
		}
	}	
	/**
	 * Creates the icon list with the bufferedImages from buffList[]
	 */
	public void createIconList()
	{
		for(int i =0;i<12;i++)
		{
			iconList[i]=new ImageIcon(buffList[i]);//converts each BufferedImage in buffList into an icon and apends it to iconList
		}
	}
    MouseListener mouseListener = new MouseAdapter() //Creates new mouseListener object
    {
        public void mouseClicked(MouseEvent mouseEvent) //If a mouse event occurs
        {
        	if(mouseEvent.getButton()!=MouseEvent.NOBUTTON)//If the mouse event is a button click
    		{ 
				if(SwingUtilities.isRightMouseButton(mouseEvent))//If the button click is the right mouse button
					changeFlag();	//Run change flag
				else//If the button click is not the right mouse button
				{
					if(!isFlagged)// and the button isn't flagged
						openTile();//Run openTile()
				}
					
    		}
        }
    };
}
    
