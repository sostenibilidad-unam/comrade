package Calibradores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Evolucionan10Inicios {
	BufferedReader peine;
	public Evolucionan10Inicios(){
		
		String[] parametrosConIndice;
		preguntaCualArchivo();
		String renglon=dameOtroRenglon();
		parametrosConIndice=renglon.split(", ");
		int[] puntoDeInicio = new int[parametrosConIndice.length];
		
		for (int i=0; i<10;i++){
			
			parametrosConIndice=renglon.split(", ");
			for (int j=0;j<parametrosConIndice.length-1;j++){
				puntoDeInicio[j]=Integer.valueOf(parametrosConIndice[j]);
			}
			
			
			new EvolucionanFrenos().empiezen1000(puntoDeInicio, i, "evo8metricas");
		
			renglon=dameOtroRenglon();
	
		}
	
	}
	
	
	public void preguntaCualArchivo() {
		JFrame aqui;
		aqui = new JFrame("selecciona in archivo");
		aqui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aqui.setSize(200,100);
		aqui.setLocation(400,400);
		//aqui.setVisible(true);
		JFileChooser cual = new JFileChooser("C:/");
		
		
		int retval = cual.showOpenDialog(aqui);
		if (retval == JFileChooser.APPROVE_OPTION) {
			try {
				peine = new BufferedReader( new FileReader(cual.getSelectedFile()) );
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	private String dameOtroRenglon() {
	String esteRenglon = null;
	try {
		esteRenglon = peine.readLine();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return esteRenglon;
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Evolucionan10Inicios();
	}

}
