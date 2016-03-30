package Calibradores;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoFrenos;



public class BuscadorPro {
	EvolucionanFrenos gente;
	private CodigoGenetico infoADN;
	private Random azar;
	public ArrayList<Individuo>  DiezBuenos;
	
	private UrbanizandoFrenos CA;
	
	public BuscadorPro() {
		
		
		corre1000yDameLosMejores10();
		
		for (int i=0; i<10;i++){
			new EvolucionanFrenos().empiezen1000(DiezBuenos.get(i).valoresADN, i, "busqueda");
		}
		
	}
	
	private void corre1000yDameLosMejores10() {
		azar= new Random();
		infoADN = new CodigoGenetico(8);
		infoADN.setRango(0, 2, 5);
		infoADN.setRango(1, 2, 22);
		infoADN.setRango(2, 5, 22);
		infoADN.setRango(3, 20, 80);
		infoADN.setRango(4, 200, 800);
		infoADN.setRango(5, 0, 100);
		infoADN.setRango(6, 0, 100);
		infoADN.setRango(7, 0, 100);
		DiezBuenos = new ArrayList<Individuo>();
		
		
		CA = new UrbanizandoFrenos(72, 56, 11, 4);
		
		
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setBosque(new File ("Topilejo/bosqueb.txt"));
		MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//Metrica8metricas metrica = new Metrica8metricas(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaClusters metrica = new MultiescalaClusters(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
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
			for (int j=0; j<8;j++){
				System.out.print(DiezBuenos.get(i).valoresADN[j]+", ");
			}
			System.out.println(DiezBuenos.get(i).indice);
		}
		
		
		
		
	}

	public static void main(String[] args) {
		new BuscadorPro();

	}

}
