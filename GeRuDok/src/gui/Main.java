package gui;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main
{
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException
	{
		MainWindow mainWindow = MainWindow.getInstance();
		/// Main
		final String DEFAULT_FONT_FAMILY = "SansSerif";
		final int DEFAULT_FONT_SIZE = 18;
		UIManager.put("TextPane.font", 
				new Font(DEFAULT_FONT_FAMILY, Font.PLAIN, DEFAULT_FONT_SIZE));
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
	}
}
