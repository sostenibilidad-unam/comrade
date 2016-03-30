package Calibradores;
import java.io.File;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoFrenos;


public class CorremeUnFrenos extends CorremeUn{
	UrbanizandoFrenos CA;
	private PintaGrid pinta1;
	private PintaGrid pinta2;
	private PintaGrid pinta3;
	
	private PintaGrid pinta0;
	
	
	public CorremeUnFrenos() {
		// TODO Auto-generated constructor stub
		CA = new UrbanizandoFrenos(815, 923, 11, 4);
		CA.setInitialGrid(new File ("asciGrids/2000real.txt"));
		CA.setDistVias(new File ("asciGrids/dist_vias.txt"));
		//CA.setDistVias(new File ("Topilejo/distvia3.txt"));
		CA.setGoalGrid(new File ("asciGrids/realinput.txt"));
		CA.setPendiente(new File ("asciGrids/pendiente.txt"));
		CA.setBosque(new File ("asciGrids/bosque.txt"));
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
//		pinta0=new PintaGrid(new File ("Topilejo/topi1995.txt"), "1995", 3);
//		pinta1=new PintaGrid(new File ("Topilejo/topi1995.txt"), "1996", 3);
//		pinta2=new PintaGrid(new File ("Topilejo/topi1995.txt"), "1997", 3);
//		pinta3=new PintaGrid(new File ("Topilejo/topi1995.txt"), "1998", 3);
//		
		
		
		acoplaSliders();
		
		monitor=new ScreenShotter();
		oye=new PintaGrid(new File ("Topilejo/topi1995.txt"), "1999", 3);
		oye.setLocation(660, 380);
		
		gridmeta=new PintaGrid(new File ("Topilejo/topi1999.txt"), "Grid Meta", 3);
		gridmeta.setLocation(660, 70);
		
		chidos= new Ventana("Guardar Resultados", "Algoritmo Evolutivo");		
		chidos.setVisible(true);
		chidos.graf.setLocation(1025, 70);
		chidos.graf.setSize(250, 658);
		chidos.setQueTantoLocation(660, 663);
		aqui.setSize(613, 200);
		aqui.setLocation(660, 726);
		
		
	    metricaQueTanto = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaBribiesca metrica = new MultiescalaBribiesca(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		metricaQueTanto.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		new CorremeUnFrenos();
	}
	
	
	@Override
	int[][] unOutputPara(int[] valoresADN) {
		// TODO Auto-generated method stub
		int[][] unO=null;
		unO=CA.unOutputPara(valoresADN);
		pinta1.pinta(CA.dameLaIteracion(1));
		pinta2.pinta(CA.dameLaIteracion(2));
		pinta3.pinta(CA.dameLaIteracion(3));
		
		return unO;
	}
	
	
}




