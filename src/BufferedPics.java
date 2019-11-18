/**Name: Daniel Mezhibovski
 * Date: 1/21/2018
 * Summary: Imports all images needed as BufferedImages and adds them to one list
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BufferedPics 
{
	//Declares BufferedImage objects for all images
	private BufferedImage bFlag;
	private BufferedImage bMine;
	private BufferedImage bTile;
	private BufferedImage bZero;
	private BufferedImage bOne;
	private BufferedImage bTwo;
	private BufferedImage bThree;
	private BufferedImage bFour;
	private BufferedImage bFive;
	private BufferedImage bSix;
	private BufferedImage bSeven;
	private BufferedImage bEight;
	private BufferedImage[] list;//Declares array of bufferedImages 
	/**
	 * BufferedPics constructor runs the importPics() method
	 */
	public BufferedPics()//Constructor
	{
		importPics();//Runs importPics
	}
	/**
	 * Returns the BufferedImage array
	 * @return list variable
	 */
	public BufferedImage[] getBufferedPics()
	{
		return list;//Returns list
	}
	/**
	 * Imports all images through a try/catch and adds them all to the already declared list array
	 */
	public void importPics()
	{
		try{//Imports all the images as BufferedImages
			bFlag= ImageIO.read(getClass().getResourceAsStream("flag.png"));
			bMine= ImageIO.read(getClass().getResourceAsStream("mine.png"));
			bTile= ImageIO.read(getClass().getResourceAsStream("tile.png"));
			bZero= ImageIO.read(getClass().getResourceAsStream("0.png"));
			bOne= ImageIO.read(getClass().getResourceAsStream("1.png"));
			bTwo= ImageIO.read(getClass().getResourceAsStream("2.png"));
			bThree= ImageIO.read(getClass().getResourceAsStream("3.png"));
			bFour= ImageIO.read(getClass().getResourceAsStream("4.png"));
			bFive= ImageIO.read(getClass().getResourceAsStream("5.png"));
			bSix= ImageIO.read(getClass().getResourceAsStream("6.png"));
			bSeven= ImageIO.read(getClass().getResourceAsStream("7.png"));
			bEight= ImageIO.read(getClass().getResourceAsStream("8.png"));
			
		}catch(IOException e)//If file could not be found, prints "Could not find file"
		{
			System.out.println("Could Not Find File");
		}
		list = new BufferedImage[]{bZero,bOne,bTwo,bThree,bFour,bFive,bSix,bSeven,bEight,bFlag,bMine,bTile};//Adds all images to list
	}
	
}
