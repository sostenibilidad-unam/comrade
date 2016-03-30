package Calibradores;
import java.io.File;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoFrenos;


public class TanteadorTopilejo extends Tanteador {
	UrbanizandoFrenos CA;
	 
	public TanteadorTopilejo() {
//		int[] unConjuntoDeParametros = {1, 1, 1, 333, 333, 333, 1, 1, 444, 400, 400, 555};
//		puntoDeTanteo = new PuntoDeBusqueda(unConjuntoDeParametros);
//		puntoDelRecuerdo = new PuntoDeBusqueda(unConjuntoDeParametros);
		
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
		
		puntoDeTanteo = new PuntoDeBusqueda(infoADN.size);
		puntoDelRecuerdo = new PuntoDeBusqueda(infoADN.size);
		vuelo = new PuntoDeBusqueda(infoADN.size);
		
		
		bajando = new Ventana("Guardar Acercamiento", "Caminante Siego Estocastico");		
		bajando.setVisible(true);
		bajando.graf.setLocation(850, 715);
		bajando.setQueTantoLocation(865, 650);
		
//solo para correr los 3 calibradores al mismo tiempo		
//		ventana = new Ventana("Guardar Todo", "Todos");
//		ventana.setVisible(true);

		
	    pintaGrid1 = new PintaGrid(new File("Topilejo/topi1999.txt"), "Grrid Meta", 4); 
		pintaGrid2 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Mejor Aproximacion", 5); 
		pintaGrid3 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 4);
		
		pintaGrid2.setLocation(865, 370);
	}
	
	public double mide(int[] parametros) {
		
		return CA.mide(parametros, 100);
	}
	
	
	public static void main(String[] args) {
		
		
		TanteadorTopilejo topo = new TanteadorTopilejo();
		topo.busca();
		
	}

	

	@Override
	public int[][] unOutputPara(int[] parametros) {
		// TODO Auto-generated method stub
		return CA.unOutputPara(parametros);
	}

}
