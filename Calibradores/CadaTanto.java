package Calibradores;

import java.util.Timer;
import java.util.TimerTask;

public class CadaTanto extends TimerTask {
	ScreenShotter monitor;
	int contador;
	
	public CadaTanto(){
		super();
		monitor=new ScreenShotter();
		contador=0;
	}
	public void run() {
		contador++;
		monitor.captureNow("monitor"+ contador+ ".gif");

	}
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new CadaTanto(), 1000, 1000);
		
	}
}
