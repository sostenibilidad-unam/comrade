package Calibradores;

import java.util.Random;


public class Individuo{
	
	//CodigoGenetico infoADN;
	public int[] valoresADN;
	public int lugar;
	public double indice;
	
	public Individuo(CodigoGenetico infoADN) { //individuo en blanco
		valoresADN = new int[infoADN.size];
		//infoADN = new CodigoGenetico(size);
		Random azar= new Random();
		for (int parametro=0; parametro<infoADN.size;parametro++){
			valoresADN[parametro] =  azar.nextInt(infoADN.getGen(parametro).suIntervalo)+infoADN.getGen(parametro).min;
		}
		
	}
	public Individuo(int[] estosCromosomas) { //individuo con un adn determinado por el array de enteros que recibe el constructor
		//ADN = new int[7];
		valoresADN = estosCromosomas;
		//infoADN = new CodigoGenetico(valoresADN.length);
	}
//	public Individuo(int size, int tantito) {// individuo generado random + tantito 
//		
//			valoresADN = new int[size];
//			Random escupe = new Random();
//			CodigoGenetico genes = new CodigoGenetico(size);
//			Gen gen;
//			
//			
//			for (int i = 0; i < valoresADN.length; i++){
//				
//				gen = genes.getGen(i);
//				
//				valoresADN[i] = escupe.nextInt(gen.suIntervalo) + gen.min + tantito;
//			}
//	}
//	
	public void setIndice(double indice) {
		//los individuos tienen indice y son comparables
		this.indice=indice;
	}
	public int compareTo(Individuo o) {
		
		if (indice < o.indice)return -1;
		else if (indice == o.indice)return 0;
		else return 1;	
		
	}
	
	
}