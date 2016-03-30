package Calibradores;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Metricas.Metrica8metricas;
import Metricas.MetricaDeAreaConClustersBinaria;
import Metricas.MultiescalaConBorde;
import Metricas.MultiescalaConBordeBinaria;
import Metricas.MultiescalaConClustersBinaria;
import Modelo.UrbanizandoCuencaAgri;
import Modelo.UrbanizandoFrenos;
import Modelo.UrbanizandoCuenca;


public class EvolucionanCuencaAgri extends EvolucionanBinario {

	public String[] nombresDeLosParametros;
	private UrbanizandoCuencaAgri CA;
	int[] parametrosIniciales;
	/**
	 * @param args
	 */
	
	public EvolucionanCuencaAgri(String carpeta) {
		
		this.carpeta = carpeta;
		//leer las celdas del raster de bosques
		BufferedReader bosquesFile;
		int ncols = 0;
		int nrows = 0;
		try {
			bosquesFile = new BufferedReader(new FileReader(new File (carpeta, "bosques.txt")));
			String line = bosquesFile.readLine();
			String ncolsString = line.replace("ncols", "");
			ncolsString = ncolsString.replace(" ", "");
			ncols = Integer.parseInt(ncolsString);
			
			
			line = bosquesFile.readLine();
			String nrowsString = line.replace("nrows", "");
			nrowsString = nrowsString.replace(" ", "");
			nrows = Integer.parseInt(nrowsString);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("ncols"+ncols);
		
		System.out.println("nrows"+nrows);
		
		CA = new UrbanizandoCuencaAgri(ncols, nrows, 22, 2);
		nombresDeLosParametros=CA.nombresDeLosParametros;
		infoADN = CA.infoADN;
		
		
		vivos = new ArrayList<Individuo>();
		//int[] unAdn = {3, 3, 21, 64, 326, 98, 93, 13};
		//int[] otroAdn = {3, 3, 21, 64, 326, 98, 93, 13};
		
		
		// aqui puedo empezar a calibrar desde algunos adns que yo escoja····por ejemplo······int[] unAdn = {3, 3, 21, 64, 326, 98, 93, 13};····vivos.add(0, new Individuo(unAdn));
		vivos.add(0, new Individuo(infoADN));
		vivos.add(1, new Individuo(infoADN));
			
		vivos.add(2, new Individuo(infoADN));
		vivos.add(3, new Individuo(infoADN));
		vivos.add(4, new Individuo(infoADN));
		vivos.add(5, new Individuo(infoADN));
		vivos.add(6, new Individuo(infoADN));
		vivos.add(7, new Individuo(infoADN));
		
		//acoplaSliders();
		
		//comparador = new ComparaIndividuos();
		
		
		
		
		CA.setInitialGrid(new File (carpeta, "urb2000.txt"));
		
		CA.setDistVias(new File (carpeta, "dist_vias.txt"));
		CA.setGoalGrid(new File (carpeta, "urb2010.txt"));
		CA.setPendiente(new File (carpeta, "pendiente.txt"));
		CA.setBosque(new File (carpeta, "bosques2000.txt"));
		CA.setAgricultura(new File (carpeta, "agrop2000.txt"));
		CA.setFederal(new File (carpeta, "zona_federal.txt"));
		CA.setDificultadCentros(new File (carpeta, "dificultadcentros.txt"));
		
		//MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		MultiescalaConBordeBinaria metrica = new MultiescalaConBordeBinaria(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MetricaDeAreaConClustersBinaria metrica = new MetricaDeAreaConClustersBinaria(CA.gridInicial, CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		//Metrica8metricas metrica = new Metrica8metricas(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaClusters metrica = new MultiescalaClusters(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		//metrica.normalizateConEste(new File (carpeta, "urb2000.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(10);
		elMejorDeEstaGeneracion = new int[CA.numeroDeCeldasX+1][CA.numeroDeCeldasY+1];
		
		
		
		
		//int[] unAdn = {1, 1, 1, 333, 333, 333, 1, 444, 400, 1, 1};
		//int[] otroAdn = {10, 2, 2, 100, 291, 331, 80, 100, 684, 1, 1};

		
		//chidos = new Ventana("Guardar Resultados", "Algoritmo Evolutivo");		
		//chidos.setVisible(true);
		//chidos.graf.setLocation(440, 715);
		//chidos.setQueTantoLocation(460, 650);
		
		
//solo para correr los 3 calibradores al mismo tiempo
		//ventana = new Ventana("Guardar Todo", "Todos");
		//ventana.setVisible(true);

		
		//pintaGrid1 = new PintaGrid(new File("InputRasters/urb2010.txt"), "Grrid Meta", 1); 
		//pintaGrid2 = new PintaGrid(new File("InputRasters/urb2010.txt"), "Mejor Aproximacion", 1); 
		//pintaGrid3 = new PintaGrid(new File("InputRasters/urb2000.txt"), "Grid de Salida", 1); 
		//pintaGrid4 = new PintaGrid(new File("Topilejo/topi1995.txt"), "Grid de Salida", 4); 
		
		//pintaGrid2.setLocation(460, 370);
		//pintaGrid1.setLocation(460, 15);
		
	}
	
	public static void main(String[] args) {
		
		EvolucionanCuencaAgri gente = new EvolucionanCuencaAgri("/Users/fidel/OneDrive/workspaceJava/ExperimentosMEGADAPT/entero");
		//int[] unAdn = {3, 3, 21, 64, 326, 98, 93, 13};
		gente.empiezen();
		//3, 4, 20, 86, 100, 10, 10, 40, 0.08748903611458428
		
	}
	public double mide(int[] parametros) {
		
		return CA.mide(parametros, 1);
	}
	@Override
	public int[][] unOutputPara(int[] parametros) {
		
		return CA.unOutputPara(parametros);
	}
	
}
