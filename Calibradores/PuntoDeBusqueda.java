package Calibradores;

import java.util.Random;


public class PuntoDeBusqueda implements Comparable{
	
	
	public int[] parametros;
	public int lugar;
	public double indice;
	
	public PuntoDeBusqueda(int size) { //individuo en blanco
		parametros = new int[size];
		
		for (int i=0;i<size;i++){
			parametros[i]=0;
		}
		
	}
	
	
	public PuntoDeBusqueda(CodigoGenetico infoADN) { 
		parametros = new int[infoADN.size];
		//infoADN = new CodigoGenetico(size);
		Random azar= new Random();
		for (int i=0; i<infoADN.size;i++){
			parametros[i] =  azar.nextInt(infoADN.getGen(i).suIntervalo)+infoADN.getGen(i).min;
		}
		
	}
	
	public PuntoDeBusqueda(int[] estosCromosomas) { //individuo con un adn determinado por el array de enteros que recibe el constructor
		
		parametros = estosCromosomas;
		
	}
	
	public void ponteEnRango(CodigoGenetico infoADN) { 
		for (int gen=0;gen < infoADN.size;gen++){
			if(parametros[gen]>infoADN.getGen(gen).max)parametros[gen]=infoADN.getGen(gen).max;
			if(parametros[gen]<infoADN.getGen(gen).min)parametros[gen]=infoADN.getGen(gen).min;
			
		}

		//esto es solo para el cerca pero no tan cerca....osea que es una restriccion 
		//especifica a un modelo de crecimiento urbano, este codigo no bebe ir aqui
		if (parametros[1]<=parametros[0]){
			parametros[1]=parametros[0]+1;
		}     
	}
	public void setIndice(double indice) {
		
		this.indice=indice;
	}
	public int compareTo(PuntoDeBusqueda o) {
		
		if (indice < o.indice)return -1;
		else if (indice == o.indice)return 0;
		else return 1;	
		
	}
	public int compareTo(Object arg0) {
		
		return 0;
	}
	
}