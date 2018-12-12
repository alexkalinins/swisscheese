package engine.keyboard;

import java.awt.Color;

import javax.swing.JFrame;

public class KeyBoardTest extends JFrame{
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
