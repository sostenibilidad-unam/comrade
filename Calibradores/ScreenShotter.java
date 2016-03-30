package Calibradores;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import java.util.Timer;

import javax.imageio.*;
public class ScreenShotter {
	Robot robot;
	Rectangle rectangle;
	public ScreenShotter(){
		try
		{
			//Get the screen size
		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			rectangle = new Rectangle(0, 0, screenSize.width, screenSize.height);

		    robot = new Robot();
		    
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void captureNow(String fileName){
		try
		{
			BufferedImage image = robot.createScreenCapture(rectangle);
	
			File file;
		     		    	
		    //Save the screenshot as a jpg       or gif?
			file = new File(fileName);
			ImageIO.write(image, "jpg", file);
			
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	
	public static void main(String[] args) {
		new ScreenShotter().captureNow("eresjpg.jpg");
	}
}
