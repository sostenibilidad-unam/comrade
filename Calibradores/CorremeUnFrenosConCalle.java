package Calibradores;

import java.io.File;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;

public class CorremeUnFrenosConCalle extends CorremeUnFrenos {
	
	
	public CorremeUnFrenosConCalle() {
		super();
		CA.setDistVias(new File ("Topilejo/distvia3.txt"));
	}
	
	
	public static void main(String[] args) {
		
		new CorremeUnFrenosConCalle();
	}
	public void correElModeloyDespliega(){
		
		
		unOutput=unOutputPara(este.valoresADN);
		este.setIndice(metricaQueTanto.esTantitoNormalizado(unOutput));
		chidos.escribe(este);
		
		String quien = "" + este.valoresADN[0];
		for (int esteGen = 1; esteGen < este.valoresADN.length; esteGen++){
			quien = quien + ", " + este.valoresADN[esteGen];
		}
					
		oye.pintaConCalle(unOutput);
		oye.fr.setTitle(quien);
		
		
		AsciiGridReader gridInicialReader = new AsciiGridReader(new File("topilejo/topi1995.txt"));
		//int[][] gridInicial = gridInicialReader.getIntArray();
		
		gridInicialReader.close();
		AsciiGridWriter escribe = new AsciiGridWriter(new File("hipotetico8.txt"));
		escribe.escribeInt(unOutput, gridInicialReader.numeroDeColumnas, gridInicialReader.numeroDeRenglones, gridInicialReader.xllcorner, gridInicialReader.yllcorner, gridInicialReader.cellsize, gridInicialReader.NODATA_value);
	}

}
