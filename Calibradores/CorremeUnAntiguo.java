package Calibradores;

import java.io.File;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoAntiguo;

public class CorremeUnAntiguo extends CorremeUn {

	UrbanizandoAntiguo CA;
	
	
	
	public CorremeUnAntiguo() {
		CA = new UrbanizandoAntiguo(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995a.txt"));
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		//CA.setDistVias(new File ("Topilejo/distvia3.txt"));
		CA.setDificultad(new File("Topilejo/dificultad2.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setIteraciones(4);
		nombresDeLosParametros=CA.nombresDeLosParametros;
		infoADN = CA.infoADN;
		
		preguntaCualArchivo();
		renglon=leeElRenglon();
		parametrosConIndice=renglon.split(", ");
		numeroDeParametros = parametrosConIndice.length-1;
				
		este = new Individuo(infoADN);
		este.setIndice( Double.valueOf(parametrosConIndice[numeroDeParametros]) );
		
		for (int i=0;i<numeroDeParametros;i++){
			este.valoresADN[i]=Integer.valueOf(parametrosConIndice[i]);
		}
	
		acoplaSliders();
		
		monitor=new ScreenShotter();
		oye=new PintaGrid(new File ("Topilejo/topi1995.txt"), "??", 5);
		oye.setLocation(20, 380);
		
		gridmeta=new PintaGrid(new File ("Topilejo/topi1999.txt"), "Grid Meta", 5);
		gridmeta.setLocation(20, 70);
		
		chidos= new Ventana("Guardar Resultados", "Algoritmo Evolutivo");		
		chidos.setVisible(true);
		chidos.graf.setLocation(385, 70);
		chidos.graf.setSize(250, 658);
		chidos.setQueTantoLocation(20, 663);
		aqui.setSize(616, 200);
		aqui.setLocation(20, 726);
		
		metricaQueTanto = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaBribiesca metrica = new MultiescalaBribiesca(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		metricaQueTanto.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		
		
		
		
	}
	
	public static void main(String[] args) {
		
		new CorremeUnAntiguo();
	}
	@Override
	int[][] unOutputPara(int[] valoresADN) {
		// TODO Auto-generated method stub
		return CA.unOutputPara(valoresADN);
	}
	

}
