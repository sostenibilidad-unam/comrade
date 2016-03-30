package Calibradores;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class MyGlass extends JComponent {

	public MyGlass() {
		// TODO Auto-generated constructor stub
		setVisible(true);
	}
	public void paint(Graphics g){
		BufferedImage img = null;
		try {
			img = ImageIO.read( new File("callesita4.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(img, 0, 0, this);
	}
}
