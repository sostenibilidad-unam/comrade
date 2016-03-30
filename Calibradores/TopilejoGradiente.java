package Calibradores;
import java.io.File;

import Metricas.MetricaMultiescala;
import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoFrenos;
import Modelo.UrbanizandoHibrido;


public class TopilejoGradiente extends Gradient {
	
	
	
	public UrbanizandoFrenos CA;

	public TopilejoGradiente() {
		
		CA = new UrbanizandoFrenos(72, 56, 11, 4);
	    CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setBosque(new File ("Topilejo/bosqueb.txt"));
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		infoADN = CA.infoADN;
		
		MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		
		//nuevoPunto=new PuntoDeBusqueda(infoADN);
		
		puntoDelRecuerdo=new PuntoDeBusqueda(infoADN);
		
		bajando = new Ventana("Guardar Todo", "Gradiente por Diferecias Finitas");
		bajando.setVisible(true);
		bajando.graf.setLocation(30, 715);
		bajando.setQueTantoLocation(50, 650);
		
	    pintaGrid1 = new PintaGrid(new File("Topilejo/topi1999.txt"), "Grrid Meta", 5); 
		pintaGrid2 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Modelo", 5); 
		pintaGrid3 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 5);
		//pintaGrid4 = new PintaGrid(new File("Topilejo/topi1995.txt"), "-", 5);
		
		//pintaGrid4.setLocation(50, 370);
		
	}
	
	
	public double mide(int[] parametros) {
		
		return CA.mide(parametros, 100);
	}
	
	@Override
	public int[][] unOutputPara(int[] parametros) {
		// TODO Auto-generated method stub
		return CA.unOutputPara(parametros);
	}
	
	public static void main(String[] args) {
		TopilejoGradiente caminante = new TopilejoGradiente();
		caminante.busca();
	}

}
