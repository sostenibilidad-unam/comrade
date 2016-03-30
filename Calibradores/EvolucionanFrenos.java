package Calibradores;
import java.io.File;
import java.util.ArrayList;

import Metricas.Metrica8metricas;
import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoFrenos;


public class EvolucionanFrenos extends EvolucionanSlider {

	
	private UrbanizandoFrenos CA;
	/**
	 * @param args
	 */
	public EvolucionanFrenos() {
		
		CA = new UrbanizandoFrenos(72, 56, 11, 4);
		nombresDeLosParametros=CA.nombresDeLosParametros;
		infoADN = CA.infoADN;
		
		
		vivos = new ArrayList<Individuo>();
		//int[] unAdn = {3, 3, 21, 64, 326, 98, 93, 13};
		//int[] otroAdn = {3, 3, 21, 64, 326, 98, 93, 13};
		
		vivos.add(0, new Individuo(infoADN));
		vivos.add(1, new Individuo(infoADN));
			
		vivos.add(2, new Individuo(infoADN));
		vivos.add(3, new Individuo(infoADN));
		vivos.add(4, new Individuo(infoADN));
		vivos.add(5, new Individuo(infoADN));
		vivos.add(6, new Individuo(infoADN));
		vivos.add(7, new Individuo(infoADN));
		
		acoplaSliders();
		
		//comparador = new ComparaIndividuos();
		
				
		
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setBosque(new File ("Topilejo/bosqueb.txt"));
		//MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		Metrica8metricas metrica = new Metrica8metricas(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaClusters metrica = new MultiescalaClusters(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		elMejorDeEstaGeneracion = new int[CA.numeroDeCeldasX+1][CA.numeroDeCeldasY+1];
		
		
		
		
		//int[] unAdn = {1, 1, 1, 333, 333, 333, 1, 444, 400, 1, 1};
		//int[] otroAdn = {10, 2, 2, 100, 291, 331, 80, 100, 684, 1, 1};

		
		chidos = new Ventana("Guardar Resultados", "Algoritmo Evolutivo");		
		chidos.setVisible(true);
		chidos.graf.setLocation(440, 715);
		chidos.setQueTantoLocation(460, 650);
		
		
//solo para correr los 3 calibradores al mismo tiempo
		ventana = new Ventana("Guardar Todo", "Todos");
		ventana.setVisible(true);

		
		pintaGrid1 = new PintaGrid(new File("Topilejo/topi1999.txt"), "Grrid Meta", 5); 
		pintaGrid2 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Mejor Aproximacion", 5); 
		pintaGrid3 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 5); 
		//pintaGrid4 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 4); 
		
		pintaGrid2.setLocation(460, 370);
		pintaGrid1.setLocation(460, 15);
		
	}
	public static void main(String[] args) {
		
		EvolucionanFrenos gente = new EvolucionanFrenos();
		//int[] unAdn = {3, 3, 21, 64, 326, 98, 93, 13};
		gente.empiezen();
		
		
	}
	public double mide(int[] parametros) {
		
		return CA.mide(parametros, 25);
	}
	@Override
	public int[][] unOutputPara(int[] parametros) {
		
		return CA.unOutputPara(parametros);
	}
	
}
