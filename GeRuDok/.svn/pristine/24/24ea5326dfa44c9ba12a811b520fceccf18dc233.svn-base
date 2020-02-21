package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutInfo extends JDialog
{
	private ImageIcon img1;
	private JLabel igorOpis;
	private JLabel igorSl;
	
	private ImageIcon img2;
	private JLabel nikolaOpis;
	private JLabel nikolaSl;
	
	public AboutInfo()
	{	
		setTitle("About");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenD = tk.getScreenSize();
		int sHeight = screenD.height;
		int sWidth = screenD.width;
		setSize(sWidth / 3, sHeight / 2);
		
		img1 = new ImageIcon(getClass().getResource("Slika.png"));
		
		Image image1 = img1.getImage();
		Image newImg1 = image1.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		img1 = new ImageIcon(newImg1);
		
		igorSl = new JLabel(img1);
		igorOpis = new JLabel();
		igorOpis.setText("<html>Igor Gvozdenov<br>igvozdenov8719rn@raf.rs</html>");
		igorOpis.setForeground(Color.white);
		
		img2 = new ImageIcon(getClass().getResource("temp.jpg"));
		
		Image image2 = img2.getImage();
		Image newImg2 = image2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		img2 = new ImageIcon(newImg2);
		
		nikolaSl = new JLabel(img2);
		nikolaOpis = new JLabel("<html>Nikola Boskovic<br>nboskovic11019rn@raf.rs</html>");
		nikolaOpis.setForeground(Color.white);

		
		setLayout(new GridLayout(2, 2, 15, 15));
		
		add(igorSl);
		add(igorOpis);
		add(nikolaSl);
		add(nikolaOpis);
		
		getContentPane().setBackground(Color.DARK_GRAY);
		setResizable(false);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
