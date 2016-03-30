package Calibradores;
import java.io.File;
import java.util.ArrayList;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoAntiguo;


public class EvolucionanAntiguo extends EvolucionanSlider {
	UrbanizandoAntiguo CA;
	
	/**
	 * @param args
	 */
	public EvolucionanAntiguo() {
	
		
		
		//comparador = new ComparaIndividuos();
		vivos = new ArrayList<Individuo>();
				
		CA = new UrbanizandoAntiguo(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad2.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		elMejorDeEstaGeneracion = new int[CA.numeroDeCeldasX+1][CA.numeroDeCeldasY+1];
		nombresDeLosParametros = CA.nombresDeLosParametros;
		infoADN = CA.infoADN;
		
//		infoADN.setRango(0, 3, 17);
//		infoADN.setRango(1, 3, 17);
//		infoADN.setRango(2, 3, 17);
//		infoADN.setRango(3, 1, 333);
//		infoADN.setRango(4, 1, 333);
//		infoADN.setRango(5, 1, 333);
//		infoADN.setRango(6, 1, 160);
//		infoADN.setRango(7, 1, 160);
//		infoADN.setRango(8, 1, 444);
//		infoADN.setRango(9, 800, 1000);
		
		//6   2   1   222   298   270   38   42   134   314   336   542
//		int[] unAdn = {3, 2, 1, 222, 298, 270, 38, 42, 134, 314, 336, 542};
//		int[] otroAdn = {4, 5, 4, 222, 222, 222, 36, 30, 222, 333, 222, 444};
		
		//int[] unAdn = {3, 3, 3, 333, 333, 333, 1, 1, 444, 400};
		//int[] otroAdn = {3, 3, 3, 333, 333, 333, 1, 1, 444, 400};

		//vivos.add(0, new Individuo(unAdn));
		//vivos.add(1, new Individuo(otroAdn));
		
		vivos.add(0, new Individuo(infoADN));
		vivos.add(1, new Individuo(infoADN));
		vivos.add(2, new Individuo(infoADN));
		vivos.add(3, new Individuo(infoADN));
		vivos.add(4, new Individuo(infoADN));
		vivos.add(5, new Individuo(infoADN));
		vivos.add(6, new Individuo(infoADN));
		vivos.add(7, new Individuo(infoADN));
		
		acoplaSliders();
		
		chidos = new Ventana("Guardar Resultados", "Algoritmo Evolutivo UA");		
		chidos.setVisible(true);
		chidos.graf.setLocation(440, 715);
		chidos.setQueTantoLocation(460, 650);
		
		
//solo para correr los 3 calibradores al mismo tiempo
		ventana = new Ventana("Guardar Todo", "Todos");
		ventana.setVisible(true);

		
		pintaGrid1 = new PintaGrid(new File("Topilejo/topi1999.txt"), "Grrid Meta", 5); 
		pintaGrid2 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Mejor Aproximacion", 5); 
		pintaGrid3 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 4); 
		//pintaGrid4 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 4); 
		
		pintaGrid2.setLocation(460, 370);
		pintaGrid1.setLocation(460, 15);
		
	}
	public static void main(String[] args) {
		
		EvolucionanAntiguo gente = new EvolucionanAntiguo();
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
