import javax.swing.*;

/**
 * Creates the frame
 * @author Crystal Chun	ID #012680952
 *
 */
public class Frame extends JFrame
{
	/**The panel to be placed on the frame*/
	private Panel panel;
	
	/**
	 * The frame constructor, constructs the window
	 */
	public Frame()
	{
		setBounds(50, 40, 1300, 800);
		panel = new Panel();
		getContentPane().add(panel);
	}
	
	public static void main(String[] args) 
	{
		//Creates the frame, allows it to be closed, and sets it to visible 
		Frame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
