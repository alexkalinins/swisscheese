package SwissCheese.engine.keyboard;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * Test class for testing the functionality of the Keyboard listener.
 * 
 * @author Alex Kalinins
 */
public class KeyBoardTest extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4551755352000162403L;

	KeyBoardTest(){
		Keyboard keyboard = new Keyboard();
		addKeyListener(keyboard);
		setSize(400,400);
		setBackground(Color.BLACK);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new KeyBoardTest();
		
	}
}
