package Calibradores;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoAntiguo;
import Modelo.UrbanizandoFrenos;

public class BuscadorProAntiguo {
	EvolucionanAntiguo gente;
	
	private CodigoGenetico infoADN;
	public ArrayList<Individuo>  DiezBuenos;
	
	private UrbanizandoAntiguo CA;
	
	public BuscadorProAntiguo() {
		
		
		corre1000yDameLosMejores10();
		
		for (int i=0; i<10;i++){
			new EvolucionanAntiguo().empiezen1000(DiezBuenos.get(i).valoresADN, i, "busquedaAntiguo");
		}
		
	}
	
	private void corre1000yDameLosMejores10() {
		
		
		DiezBuenos = new ArrayList<Individuo>();
		
		
		CA = new UrbanizandoAntiguo(72, 56, 11, 4);
		
		
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad2.txt"));
		infoADN=CA.infoADN;
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
		new BuscadorProAntiguo();

	}

}