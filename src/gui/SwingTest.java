package gui;

import javax.swing.JFrame;

public class SwingTest
{
	private JFrame frame;

	public SwingTest()
	{
		frame = new JFrame("IS Task Manager");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingTest swt = new SwingTest();		
	}
}