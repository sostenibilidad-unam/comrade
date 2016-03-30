package Calibradores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public abstract class Tanteador{

	
	public PintaGrid pintaGrid1;
	public PintaGrid pintaGrid2;
	public PintaGrid pintaGrid3;
	
	public PuntoDeBusqueda puntoDeTanteo, puntoDelRecuerdo, vuelo, deltaRandom;
	public CodigoGenetico infoADN;
	public ScreenShotter monitor;
	public int contador;
	public Ventana ventana, bajando;
	int iteraciones;
	public boolean[] esMovible;
	public int vecesQueSeUsoMide;
	 
	public Tanteador(){
		
	}
	public void busca2000(int[] aqui, int id, String filePrefix) {
		//2000 veces mide aqui equivale a 1000 generaciones del evolutivo
		//porque aca se promedian 100 y en el evolutivo 25
		puntoDelRecuerdo.parametros = aqui;
		
		vecesQueSeUsoMide=0;
		puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
		
		while (vecesQueSeUsoMide<2000){
			iteraciones++;
			despliegaElRecuerdo();
			deltaRandom = generaVectorRandom();
			puntoDeTanteo = sumaVectores ();
			ponteEnRango();	
			
			vecesQueSeUsoMide+=2;
			puntoDeTanteo.setIndice(mide(puntoDeTanteo.parametros));
			puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));

			
			if (puntoDeTanteo.indice < puntoDelRecuerdo.indice){
				quedateConElTanteo();
				vuelo = calculaVueloPositivo();
			}else{
				puntoDeTanteo = restaVectores ();
				ponteEnRango();
				vecesQueSeUsoMide++;
				puntoDeTanteo.setIndice(mide(puntoDeTanteo.parametros));
				if (puntoDeTanteo.indice < puntoDelRecuerdo.indice){
					quedateConElTanteo();
					vuelo = calculaVueloNegativo();
				}else{
					reduceVuelo();
					//solo para correr los 3 calibradores al mismo tiempo
					//ventana.escribe(puntoDeTanteo);
				}
			}
		}
		System.out.print("EXITO! en : " + vecesQueSeUsoMide + " mediciones");
		
		BufferedWriter pon = null;
		
		try {
			pon = new BufferedWriter (new FileWriter( new File("" +filePrefix +""+ id + ".txt") ));
			pon.write( bajando.yoDigo.getText() );
			pon.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("NO PUDE GUARDAR");
			e1.printStackTrace();
		}
		close();
		
		
		
	}
	public void close(){
		bajando.cierra();
		pintaGrid1.dispose();
		pintaGrid2.dispose();
		pintaGrid3.dispose();
	}
	public void busca() {
		monitor = new ScreenShotter();
		contador=0;
		puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
		iteraciones=0;
		
		while (puntoDelRecuerdo.indice>.00001){
			iteraciones++;
			despliegaElRecuerdo();
			deltaRandom = generaVectorRandom();
			puntoDeTanteo = sumaVectores ();
			ponteEnRango();	
			
			puntoDeTanteo.setIndice(mide(puntoDeTanteo.parametros));
			puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));

			
			if (puntoDeTanteo.indice < puntoDelRecuerdo.indice){
				quedateConElTanteo();
				vuelo = calculaVueloPositivo();
			}else{
				puntoDeTanteo = restaVectores ();
				ponteEnRango();
				puntoDeTanteo.setIndice(mide(puntoDeTanteo.parametros));
				if (puntoDeTanteo.indice < puntoDelRecuerdo.indice){
					quedateConElTanteo();
					vuelo = calculaVueloNegativo();
				}else{
					reduceVuelo();
					//solo para correr los 3 calibradores al mismo tiempo
					//ventana.escribe(puntoDeTanteo);
				}
			}
		}
		System.out.print("EXITO! en : " + iteraciones + " iteraciones");
		
	}
	public void quedateConElTanteo() {
		
		puntoDelRecuerdo = puntoDeTanteo;
		
		
		
		
	}
	public void despliegaElRecuerdo(){
		int[] uno = puntoDelRecuerdo.parametros;
		String quien = "" + uno[0];
		for (int esteGen = 1; esteGen < uno.length; esteGen++){
			quien = quien + ", " + uno[esteGen];
		}
		pintaGrid2.pinta(unOutputPara(uno));
		pintaGrid2.fr.setTitle(quien);
		bajando.escribe(puntoDelRecuerdo, vecesQueSeUsoMide);
		
	}
	public void reduceVuelo() {
		for (int gen=0;gen < infoADN.size;gen++){
			vuelo.parametros[gen] =  new Double( (0.95)* vuelo.parametros[gen]  ).intValue();
		}
		
	}
	protected PuntoDeBusqueda restaVectores() {
		PuntoDeBusqueda resta = new PuntoDeBusqueda(infoADN.size);
		for (int gen=0;gen < infoADN.size;gen++){
			resta.parametros[gen] = puntoDelRecuerdo.parametros[gen] + vuelo.parametros[gen] - deltaRandom.parametros[gen];
		}
		return resta;
		
	}
	protected PuntoDeBusqueda calculaVueloPositivo() {
		PuntoDeBusqueda v = new PuntoDeBusqueda(infoADN.size);
		for (int gen=0;gen < infoADN.size;gen++){
			v.parametros[gen] =  new Double( ((0.2)* vuelo.parametros[gen]) + ((0.4) * deltaRandom.parametros[gen]) ).intValue();
		}
		return v;
	}
	protected PuntoDeBusqueda calculaVueloNegativo() {
		PuntoDeBusqueda v = new PuntoDeBusqueda(infoADN.size);
		for (int gen=0;gen < infoADN.size;gen++){
			v.parametros[gen] =  new Double( ((0.2)* vuelo.parametros[gen]) - ((0.4) * deltaRandom.parametros[gen]) ).intValue();
		}
		return v;
	}
	protected void ponteEnRango() {
		
		for (int gen=0;gen < infoADN.size;gen++){
			if(puntoDeTanteo.parametros[gen]>infoADN.getGen(gen).max)puntoDeTanteo.parametros[gen]=infoADN.getGen(gen).max;
			if(puntoDeTanteo.parametros[gen]<infoADN.getGen(gen).min)puntoDeTanteo.parametros[gen]=infoADN.getGen(gen).min;
			
		}
		//esto es solo para el cerca pero no tan cerca....osea que es una restriccion 
		//especifica a un modelo de crecimiento urbano, este codigo no bebe ir aqui
		if (puntoDeTanteo.parametros[0]>puntoDeTanteo.parametros[1]){
			puntoDeTanteo.parametros[1]=puntoDeTanteo.parametros[0];
		}       
		
	}
	
	
	public PuntoDeBusqueda sumaVectores(){
		PuntoDeBusqueda suma = new PuntoDeBusqueda(infoADN.size);
		for (int i=0;i < infoADN.size;i++){
			suma.parametros[i] = puntoDelRecuerdo.parametros[i] +deltaRandom.parametros[i] + vuelo.parametros[i];
		}
		return suma;
	}
	protected PuntoDeBusqueda generaVectorRandom() {
		PuntoDeBusqueda deltaRandom = new PuntoDeBusqueda(infoADN.size);
		Random oye = new Random();
		for (int i=0;i < infoADN.size;i++){
			deltaRandom.parametros[i] = new Double(infoADN.getGen(i).suIntervalo * oye.nextGaussian()).intValue();
		}
		return deltaRandom;
	}
	
	public abstract double mide(int[] parametros);
	
	public abstract int[][] unOutputPara(int[] parametros);
	

}
