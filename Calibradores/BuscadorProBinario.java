package Calibradores;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Metricas.MultiescalaConBorde;
import Metricas.MultiescalaConBordeBinaria;
import Modelo.UrbanizandoFrenos;
import Modelo.UrbanizandoFrenosBinario;



public class BuscadorProBinario {
	EvolucionanFrenos gente;
	private CodigoGenetico infoADN;
	private Random azar;
	public ArrayList<Individuo>  DiezBuenos;
	
	private UrbanizandoFrenosBinario CA;
	
	public BuscadorProBinario() {
		
		
		corre1000yDameLosMejores10();
		
		for (int i=0; i<10;i++){
			new EvolucionanFrenosBinario().empiezen1000(DiezBuenos.get(i).valoresADN, i, "busqueda");
		}
		
	}
	
	private void corre1000yDameLosMejores10() {
		azar= new Random();
		infoADN = new CodigoGenetico(6);
		infoADN.setRango(0, 2, 5);
		infoADN.setRango(1, 2, 22);
		infoADN.setRango(2, 5, 22);
		infoADN.setRango(3, 0, 100);
		infoADN.setRango(4, 0, 100);
		infoADN.setRango(5, 0, 100);
		DiezBuenos = new ArrayList<Individuo>();
		
		
		CA = new UrbanizandoFrenosBinario(1632, 1920, 22, 2);
		
		
		CA.setInitialGrid(new File ("InputRasters/urb2000.txt"));
		
		CA.setDistVias(new File ("InputRasters/dist_vias.txt"));
		CA.setGoalGrid(new File ("InputRasters/urb2010.txt"));
		CA.setPendiente(new File ("InputRasters/pendiente.txt"));
		CA.setBosque(new File ("InputRasters/bosques.txt"));
		MultiescalaConBordeBinaria metrica = new MultiescalaConBordeBinaria(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//Metrica8metricas metrica = new Metrica8metricas(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaClusters metrica = new MultiescalaClusters(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		metrica.normalizateConEste(new File ("InputRasters/urb2000.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(10);
		Comparator<Individuo> comparador= new Comparator<Individuo>() {
			public int compare(final Individuo i0, final Individuo i1) {
				if (i0.indice < i1.indice)return -1;
				else if (i0.indice == i1.indice)return 0;
				else return 1;	
			}
			};
		for (int i=0; i<10;i++){
			Individuo este= new Individuo(infoADN);
			este.setIndice(CA.mide(este.valoresADN, 25));
			DiezBuenos.add(i, este);
		}	
//		Collections.sort(DiezBuenos, comparador);
//		
//		
//		
//		
//		for (int i=10; i<1000;i++){
//			Individuo este= new Individuo(infoADN);
//			este.indice = CA.mide(este.valoresADN, 25);
//			if (este.indice < DiezBuenos.get(9).indice){
//				DiezBuenos.set(9, este);
//				Collections.sort(DiezBuenos, comparador);
//			}
//		}
		
		for (int i=0; i<10;i++){
			for (int j=0; j<6;j++){
				System.out.print(DiezBuenos.get(i).valoresADN[j]+", ");
			}
			System.out.println(DiezBuenos.get(i).indice);
		}
		
		
		
		
	}

	public static void main(String[] args) {
		new BuscadorProBinario();

	}

}
