package Calibradores;

import java.io.File;
import java.io.IOException;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoHibrido;


public class CorremeUnHibrido extends CorremeUn{
	UrbanizandoHibrido CA;
	
	public CorremeUnHibrido() {
		
		CA = new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995a.txt"));
		//CA.setDistVias(new File ("Topilejo/distvia3.txt"));
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad2.txt"));
		//CA.setBosque(new File ("Topilejo/bosque1.txt"));
		CA.setIteraciones(4);
		nombresDeLosParametros=CA.nombresDeLosParametros;
		infoADN = CA.infoADN;
				
		preguntaCualArchivo();
		renglon=leeElRenglon();
		try {
			peine.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parametrosConIndice=renglon.split(", ");
		numeroDeParametros = parametrosConIndice.length-1;
		este = new Individuo(infoADN);
		este.setIndice( Double.valueOf(parametrosConIndice[numeroDeParametros]) );
		
		for (int i=0;i<numeroDeParametros;i++){
			este.valoresADN[i]=Integer.valueOf(parametrosConIndice[i]);
		}
	
		
//		infoADN = new CodigoGenetico(numeroDeParametros);
//		
//		infoADN = new CodigoGenetico(numeroDeParametros);
//		infoADN.setRango(0, 2, 5);
//		infoADN.setRango(1, 2, 22);
//		infoADN.setRango(2, 5, 22);
//		infoADN.setRango(3, 20, 333);
//		infoADN.setRango(4, 20, 333);
//		infoADN.setRango(5, 20, 333);
//		infoADN.setRango(6, 20, 80);
//		infoADN.setRango(7, 20, 300);
//		infoADN.setRango(8, 200, 800);
//		
		
//		3, 6, 12, 300, 199, 333, 80, 289, 300, 0.17482213569178318
//		
//		this.vecino_0=vecino_0;
//    	this.vecino_carretera_0=vecino_carretera_0;
//    	this.vecino_pueblo=vecino_pueblo;
//    	this.buffer_carretera_0=buffer_carretera_0;
//    	this.buffer_pueblo=buffer_pueblo;
//    	this.buffer_carr_pueb=buffer_carr_pueb;
//    	
//    	this.vecino_1=vecino_1;
//    	//this.vecino_carretera_1=vecino_carretera_1;
//    	this.buffer_carretera_1=buffer_carretera_1;
//    	
//    	this.vecino_2=vecino_2;
////    	this.vecino_carretera_2=vecino_carretera_2;
////    	this.buffer_carretera_2=buffer_carretera_2;
		
		
		
		
		
		acoplaSliders();
		
		monitor=new ScreenShotter();
		oye=new PintaGrid(new File ("Topilejo/topi1995.txt"), "??", 5);
		oye.setLocation(460, 380);
		
		gridmeta=new PintaGrid(new File ("Topilejo/topi1999.txt"), "Grid Meta", 5);
		gridmeta.setLocation(460, 70);
		
		chidos= new Ventana("Guardar Resultados", "Algoritmo Evolutivo");		
		//chidos.setVisible(true);
		chidos.graf.setLocation(825, 70);
		chidos.graf.setSize(450, 658);
		chidos.setQueTantoLocation(460, 663);
		
		
		
		
	    metricaQueTanto = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaBribiesca metrica = new MultiescalaBribiesca(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		metricaQueTanto.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		
		
	}

	
	public static void main(String[] args) {
		
		new CorremeUnHibrido();
	}
	

	@Override
	int[][] unOutputPara(int[] valoresADN) {
		// TODO Auto-generated method stub
		return CA.unOutputPara(valoresADN);
	}
}
