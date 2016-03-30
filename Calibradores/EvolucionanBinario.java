package Calibradores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import Metricas.MetricaMultiescala;
import Metricas.MultiescalaConBordeBinaria;

/*
 * Created on 22-jul-2004
 *	
 cuantas			   = {0-.7}    .12 
 * vecino_0			   = {1-24}	   12
 * vecino_pueblo_0     = {1-24}    12 *NUEVO*
 * vecino_carretera_0  = {1-24}	   11        /* el minimo numero de vecino necesario para que: si estas en el bufer de la carretera, puedas cambiar al siguiente estado
 * buffer_carretera_0  = {10-300}  150	     /* el tama�o de bufer de la carretera en metros
 * buffer_pueblo       = {10-444}      *NUEVO*
 * buffer_carr_pueb    = {10-555}      *NUEVO*   
 * 		primer intento....SoloUnos						
 *
 *  los reales (0-1)los convertire en enteros (0-100)
 */ 

//2, 3, 7, 100, 68, 100, 0.021307405771494946

public abstract class EvolucionanBinario{
	public ArrayList<Individuo>  vivos;
	//public ComparaIndividuos comparador;
	public CodigoGenetico infoADN;
	//public Ventana ventana;
	//public Ventana chidos;
	public int radio;
	//SceenShotter monitor;
	
	double[] estosDatos;
	double indiceDelBueno, indiceUnBueno;
	public MultiescalaConBordeBinaria quetanto;
	
	//public PintaGrid pintaGrid1;
	//public PintaGrid pintaGrid2;
	//public PintaGrid pintaGrid3;
	//public PintaGrid pintaGrid4;
	public int [][] elMejorDeEstaGeneracion ;
	public int vueltas;
	//ScreenShotter monitor;
	
	
	public int contador = 0;
	//public boolean[] esMovible;
	public String carpeta = "";
	
	BufferedWriter writer;
	
	
	public EvolucionanBinario() {
		
		
	}
	
	
	public void empiezen() {
		
		
		
		
		//esMovible = new boolean[infoADN.size];
		vueltas=0;
		indiceDelBueno = 30.0;
		//monitor = new ScreenShotter();
		vivos.get(0).indice = 0.3;
		//chidos.escribe(vivos.get(0));
		//for (int i = 0; i<infoADN.size; i++){
		//	esMovible[i]=true;
		//}
		
		
		while (vivos.get(0).indice > 0.000000001){
			vueltas++;
			
			//aparea(); // esto debe generar hijo1 e hijo2 como dos combinaciones diferentes de los papas
			
			aparea();
			
			muta();
			
			quienSobrevive();
			
			
			//despliegaElMejor();
			
			
			
		}
		//despliegaElMejor();
		
		//ventana.escribe("en " + vueltas + " vueltas");
	}
	public void escribe(Individuo esteGuey) {
		Double d = new Double(esteGuey.indice);
		
		
		
		
		int[] parametro = esteGuey.valoresADN;
		String quien = "";
		
		for (int i = 0; i < parametro.length -1; i++){
			quien= "" + quien + parametro[i] + ", ";
		}
		quien= "" + quien +""+ parametro[parametro.length-1] + ", " + esteGuey.indice ;
		
		try {
			Path path = Paths.get(carpeta, "calibrando.txt");
			writer = new BufferedWriter(new FileWriter(path.toString(), true));
			
			writer.write("" + quien);
			writer.newLine();
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		System.out.println("" + quien + "\n");
		
		
		
		
	}
	

	public void despliegaElMejor() {
		//chidos.escribe(vivos.get(0));
		int este[] = vivos.get(0).valoresADN;
		String quien = "" + este[0];
		for (int esteGen = 1; esteGen < este.length; esteGen++){
			quien = quien + ", " + este[esteGen];
		}
		
		//pintaGrid2.pinta(unOutputPara(este));
		//pintaGrid2.fr.setTitle(quien);
	}

	public void quienSobrevive() {
		ponIndices();
		//Comparable.sort( vivos );
		//Collections.sort(vivos, comparador); // ordena los 8
		Collections.sort(vivos,
				new Comparator<Individuo>() {
				public int compare(final Individuo i0, final Individuo i1) {
					if (i0.indice < i1.indice)return -1;
					else if (i0.indice == i1.indice)return 0;
					else return 1;	
				}
				});
			
		 
		//Corre.huella(quetanto, quien);
		
	}
	

	public void ponIndices(){
		//este metodo evalua los individuos de la generacion asignandoles un
		//indice de adecuacion mediante el metodo CA.mide
		for (int i = 0; i < vivos.size(); i++){
			//el metodo mide de la clase Urbanizando toma los parametros,
			//corre las iteraciones deseadas y compara la ultima 
			//iteracion con el gridMeta
			vivos.get(i).setIndice(mide(vivos.get(i).valoresADN));
			//ventana.escribe(vivos.get(i));
			
			escribe(vivos.get(i));
		}
		
	} 
	
	public void muta() {
		// muta los primeros 4 individuos y los guarda en los 
		// segundos 4 en la ListaDeIndividuos
		for (int i = 0; i < 4; i++){
			 muta(i, i + 4);
		}
		
		//esto es solo para el cerca pero no tan cerca....osea que es una restriccion 
		//especifica a un modelo de crecimiento urbano, este codigo no bebe ir aqui
		for (int i = 0; i < 8; i++){
			if (vivos.get(i).valoresADN[1]<=vivos.get(i).valoresADN[0]){
				vivos.get(i).valoresADN[1]=vivos.get(i).valoresADN[0]+1;
			}       
		}
		
	}
	public void muta(int individuoOrigen, int individuoAMutar){
		Random escupe= new Random();
		for (int i=0; i<infoADN.size; i++){
			vivos.get(individuoAMutar).valoresADN[i] = vivos.get(individuoOrigen).valoresADN[i] + new Double(infoADN.getGen(i).suIntervalo * escupe.nextGaussian()).intValue();
			
			//para no salir del rango
			if (vivos.get(individuoAMutar).valoresADN[i]>infoADN.getGen(i).max){
				vivos.get(individuoAMutar).valoresADN[i]=infoADN.getGen(i).max;
			}
			else if (vivos.get(individuoAMutar).valoresADN[i]<infoADN.getGen(i).min){
				vivos.get(individuoAMutar).valoresADN[i]=infoADN.getGen(i).min;
			}
				
			
			
		}
		
		
	}

	public void crossover(){
		Random escupe = new Random();
		int hastaAqui = escupe.nextInt(infoADN.size);
		for (int i = 0; i < hastaAqui; i++){
			vivos.get(2).valoresADN[i] = vivos.get(0).valoresADN[i];
			vivos.get(3).valoresADN[i] = vivos.get(1).valoresADN[i];
			//vivos.setADN(2, i, vivos.get(0).valoresADN[i]);
			//vivos.setADN(3, i, vivos.get(1).valoresADN[i]);
		}
		for (int i = hastaAqui; i < infoADN.size; i++){
			vivos.get(2).valoresADN[i] = vivos.get(1).valoresADN[i];
			vivos.get(3).valoresADN[i] = vivos.get(0).valoresADN[i];
			//vivos.setADN(2, i, vivos.get(1).valoresADN[i]);
			//vivos.setADN(3, i, vivos.get(0).valoresADN[i]);
		}
		
	}
	private boolean sonIguales(){
		boolean siono=true;
		for (int i=0;i<infoADN.size;i++){
			if ( vivos.get(0).valoresADN[i] != vivos.get(1).valoresADN[i] ){
				siono = false;
			}
		}
		
		return siono;
	}
	public void aparea() {
		// Este metodo genera dos hijos seleccionando al azar cada gen (de cualquiera de los papas)
		// y haciendo cross over... osea que si el hijo1.gen[i] es de un papa => hijo2.gen[i] es del otro papa
		Random escupe = new Random();
		if ( sonIguales() ){
			System.out.println("AAAAAAAAA!!!!....MUTARE SI DIOS QUIERE ");
			muta(0, 2);
			muta(1, 3);
		}else{
			
		
		for (int i = 0; i < infoADN.size; i++){		
			
			switch (escupe.nextInt(5)){
				case 0 : {
					vivos.get(2).valoresADN[i] = vivos.get(0).valoresADN[i];
					vivos.get(3).valoresADN[i] = (int)((vivos.get(0).valoresADN[i] + vivos.get(1).valoresADN[i]) / 2);
					break;
				}
				case 1 :
				case 2 :	
				case 3 : {
					vivos.get(2).valoresADN[i] = vivos.get(0).valoresADN[i];
					vivos.get(3).valoresADN[i] = vivos.get(1).valoresADN[i];
					break;
				}
				case 4 :
				case 5 :{
					vivos.get(2).valoresADN[i] = vivos.get(1).valoresADN[i];
					vivos.get(3).valoresADN[i] = vivos.get(0).valoresADN[i];
					break;
				}
				
			}
			
		}
		
		}
		
	}
public void empiezen1000(int[] aqui, int id, String filePrefix) {
		
		
		vivos.get(0).valoresADN= aqui;
		vivos.get(1).valoresADN= aqui;
		
		indiceDelBueno = 30.0;
		
		vivos.get(0).indice = 0.3;
		
		
		for (int i = 0; i<1000; i++){
			
			aparea();
			
			muta();
			
			quienSobrevive();
			
			//despliegaElMejor();
			
			
		}
		
		BufferedWriter pon = null;
		
		try {
			pon = new BufferedWriter (new FileWriter( new File("" +filePrefix +""+ id + ".txt") ));
			pon.write( "chidos.yoDigo.getText()" );
			pon.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("NO PUDE GUARDAR");
			e1.printStackTrace();
		}
		//close();
		
		
		
	}
//	public void close(){
//		ventana.cierra();
//		chidos.cierra();
//		pintaGrid1.dispose();
//		pintaGrid2.dispose();
//		pintaGrid3.dispose();
//	}
	public abstract double mide(int[] parametros);
	
	public abstract int[][] unOutputPara(int[] parametros);
}
     