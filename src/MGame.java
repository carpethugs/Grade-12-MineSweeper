import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

public abstract class MGame extends JFrame {
	private boolean _isSetup = false;
	private boolean _initialized = false;
	private ArrayList _ObjectList = new ArrayList();

	private boolean leftB,rightB;
	
	
	/**
	 * When implemented, this will allow the programmer to initialize the game
	 * before it begins running
	 * 
	 * Adding objects to the game and setting their initial positions should be
	 * done here.
	 * 
	 * @see GameObject
	 */
	public void initComponents() {
		getContentPane().setBackground(Color.lightGray);
		for (int i = 0; i < _ObjectList.size(); i++) {
				GameObject o = (GameObject)_ObjectList.get(i);
				o.repaint();
		}
	}
	
	/**
	 * Adds a game object to the screen
	 * 
	 * Any added objects will have their <code>act</code> method called every
	 * millisecond
	 * 
	 * @param o		the <code>GameObject</code> to add.
	 * @see	GameObject#act()
	 */
	public void add(GameObject o) {
		_ObjectList.add(o);
		getContentPane().add(o);
	}
	
	/**
	 * Removes a game object from the screen
	 * 
	 * @param o		the <code>GameObject</code> to remove
	 * @see	GameObject
	 */
	public void remove(GameObject o) {
		_ObjectList.remove(o);
		getContentPane().remove(o);
	}
	
	
	public void setBackground(Color c) {
		getContentPane().setBackground(c);
	}
	
	/**
	 * The default constructor for the game.
	 * 
	 * The default window size is 400x400
	 */
	public MGame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setSize(400, 400);
		setLocation(width/2,height/2);
		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuFileExit = new JMenuItem("Exit");
        menuBar.add(menuFile);
        menuFile.add(menuFileExit);
        setJMenuBar(menuBar);
        setTitle("MineSweeper");
               
        // Add window listener.
        addWindowListener (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
       menuFileExit.addActionListener( 
       		new ActionListener() {
       			public void actionPerformed(ActionEvent e) {
       				System.exit(0);
       			}
       		}
       	);

       addMouseListener(new MouseListener()
       {
    	   public void mouse(MouseEvent e)
    	   {
    		   
    	   }
       
       public void mousePressed(MouseEvent e)
       {
    	   int pressed=e.getButton();
    	   switch(pressed)
    	   {
    	   case MouseEvent.BUTTON1: leftB=true;break;
    	   case MouseEvent.BUTTON2: rightB=true;break;
    	   }
       }
       public void mouseReleased(MouseEvent e)
       {
    	   int released=e.getButton();
    	   switch(released)
    	   {
    	   case MouseEvent.BUTTON1: leftB=false;break;
    	   case MouseEvent.BUTTON2: rightB=false;break;
    	   }
       }

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
       });
		
    }


	public void Wins() {
		_WinDialog d = new _WinDialog(this, "Player 2 Wins!");
		d.setVisible(true);	
	}
	
	/**
	 * Gets the pixel width of the visible playing field
	 * 
	 * @return	a width in pixels
	 */
	public int getFieldWidth() {
		return getContentPane().getBounds().width;
	}
	
	/**
	 * Gets the pixel height of the visible playing field
	 * 
	 * @return a height in pixels
	 */
	public int getFieldHeight() {
		return getContentPane().getBounds().height;
	}
	
	class _WinDialog extends JDialog {
		JButton ok = new JButton("OK");
		_WinDialog(JFrame owner, String title) {
			super(owner, title);
			Rectangle r = owner.getBounds();
			setSize(200, 100);
			setLocation(r.x + r.width / 2 - 100, r.y + r.height / 2 - 50);
			getContentPane().add(ok);
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					_WinDialog.this.setVisible(false);
				}
			});
		}		
	}
}