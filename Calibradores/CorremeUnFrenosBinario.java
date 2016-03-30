package Calibradores;
import java.io.File;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoFrenosBinario;


public class CorremeUnFrenosBinario extends CorremeUn{
	UrbanizandoFrenosBinario CA;
	private PintaGrid pinta1;
	private PintaGrid pinta2;
	private PintaGrid pinta3;
	
	private PintaGrid pinta0;
	
	
	public CorremeUnFrenosBinario(){
		// TODO Auto-generated constructor stub
		CA = new UrbanizandoFrenosBinario(1632, 1920, 22, 2);
		CA.setInitialGrid(new File ("InputRasters/urban_2010.txt"));
		CA.setDistVias(new File ("InputRasters/dist_vias.txt"));
		//CA.setDistVias(new File ("Topilejo/distvia3.txt"));
		CA.setGoalGrid(new File ("InputRasters/urban_2010.txt"));
		CA.setPendiente(new File ("InputRasters/pendiente.txt"));
		CA.setBosque(new File ("InputRasters/bosques.txt"));
		CA.setIteraciones(20);
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
		pinta1=new PintaGrid(new File ("InputRasters/urban_2010.txt"), "1", 1);
		pinta2=new PintaGrid(new File ("InputRasters/urban_2010.txt"), "2", 1);
		pinta3=new PintaGrid(new File ("InputRasters/urban_2010.txt"), "3", 1);
//		
		
		
		acoplaSliders();
		
		
		oye=new PintaGrid(new File ("InputRasters/urban_2010.txt"), "2010", 1);
		oye.setLocation(660, 380);
		
		
		gridmeta=new PintaGrid(new File ("InputRasters/urban_2010.txt"), "Grid Meta", 1);
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
		metricaQueTanto.normalizateConEste(new File ("InputRasters/urban_2010.txt"));
		
	
		
	    
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		new CorremeUnFrenosBinario();
	}
	
	
	@Override
	int[][] unOutputPara(int[] valoresADN) {
		// TODO Auto-generated method stub
		int[][] unO=null;
		unO=CA.unOutputPara(valoresADN);
		pinta1.pinta(CA.dameLaIteracion(1));
		pinta2.pinta(CA.dameLaIteracion(2));
		pinta3.pinta(CA.dameLaIteracion(19));
		
		return unO;
	}
	
	
}




