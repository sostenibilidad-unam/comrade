package Calibradores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import Modelo.UrbanizandoHibrido;

public abstract class Gradient {
	public PuntoDeBusqueda puntoDelRecuerdo;
	//public CodigoGenetico codigo;
	public double[] parcial; 
	public int[] muevete; 
	public Ventana ventana, bajando;
	public PintaGrid pintaGrid1;
	public PintaGrid pintaGrid2;
	public PintaGrid pintaGrid3;
	public PintaGrid pintaGrid4;
	private int sinMovimiento;
	CodigoGenetico infoADN;
	int vecesQueSeUsoMide; 
	 
	public Gradient(){
		
	}
	public void busca8000(int[] aqui, int id, String filePrefix) {
		vecesQueSeUsoMide=0;
		int vecesAPromediar=50;
		double Ck=3.0;
		double ak=20.0;
		parcial = new double[infoADN.size];
		muevete = new int[infoADN.size];
		
		sinMovimiento = 0;
		
		puntoDelRecuerdo.parametros=aqui;
		
		puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
		
		bajando.escribe(puntoDelRecuerdo);
		pintaElPuntoDelRecuerdo();
		int[] puntoMenosDelta = null,puntoMasDelta = null;
		puntoMenosDelta = new int[infoADN.size];
		puntoMasDelta = new int[infoADN.size];
		PuntoDeBusqueda nuevoPunto=new PuntoDeBusqueda(infoADN.size);
		while (vecesQueSeUsoMide<8000){
			
			
			if (sinMovimiento>=3){
				nuevoPunto = exploraAlAzar();
			}else{
				//inizializando los puntos mas menos delta
				
				for (int i=0;i < infoADN.size;i++){
					
					int delta = infoADN.getGen(i).brinco;
					puntoMasDelta[i]=puntoDelRecuerdo.parametros[i] + delta;
					puntoMenosDelta[i]= puntoDelRecuerdo.parametros[i] - delta;
					puntoMasDelta=ponteEnRango(puntoMasDelta);
					puntoMenosDelta=ponteEnRango(puntoMenosDelta);
					
					parcial[i] =(mide(puntoMasDelta)- mide(puntoMenosDelta) )/(2.0 * delta);
					muevete[i] = (int)(Math.rint((ak * parcial[i] * (double)delta)));
					if (muevete[i]>2*delta)muevete[i]=2*delta;
					if (muevete[i]<-2*delta)muevete[i]=-2*delta;
				}
				//8000 de vecesQueSeUsoMide equivale a 1000 generaciones del algoritmo evolutivo
				//haciendo promedios de 25 corridas para el evolutivo y 100 para el gradiente
				vecesQueSeUsoMide+=(infoADN.size)*(2)*(4);
				System.out.print("parcial : ");
				for (int i=0;i < infoADN.size;i++)System.out.print(parcial[i]+", ");
					
				
				System.out.println();
				System.out.print("muevete : ");
				nuevoPunto=new PuntoDeBusqueda(infoADN.size);
				for (int i=0;i < infoADN.size;i++){
					nuevoPunto.parametros[i]=puntoDelRecuerdo.parametros[i] + muevete[i];
					System.out.print(muevete[i] + " , ");
					
				}
				System.out.println();
				nuevoPunto.ponteEnRango(infoADN);
			}	
			nuevoPunto.setIndice(mide(nuevoPunto.parametros));
			puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
			vecesQueSeUsoMide+=(2*4);
			System.out.println(nuevoPunto.indice+" < "+puntoDelRecuerdo.indice+ "?");
			
			if (nuevoPunto.indice<puntoDelRecuerdo.indice){
				int suma=0;
				for (int a=0;a<infoADN.size;a++){
					suma+=Math.abs(muevete[a]);
				}
				if (suma>0){
					sinMovimiento=0;
				}
				System.out.println("suma= "+suma);
				puntoDelRecuerdo.parametros=nuevoPunto.parametros;
				//puntoDelRecuerdo.indice=nuevoPunto.indice;
				
				pintaElPuntoDelRecuerdo();
				bajando.escribe(nuevoPunto, vecesQueSeUsoMide);
											
			}else{
				sinMovimiento++;
				System.out.println("no me movere!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				//nuevoPunto.parametros=puntoDelRecuerdo.parametros;
				//nuevoPunto.indice=puntoDelRecuerdo.indice;
				
				pintaElPuntoDelRecuerdo();
				bajando.escribe(puntoDelRecuerdo, vecesQueSeUsoMide);
			}
			
			
		}
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
		vecesQueSeUsoMide=0;
		int vecesAPromediar=50;
		double Ck=3.0;
		double ak=20.0;
		parcial = new double[infoADN.size];
		muevete = new int[infoADN.size];
		
		sinMovimiento = 0;
		
		
		
		puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
		
		bajando.escribe(puntoDelRecuerdo);
		pintaElPuntoDelRecuerdo();
		int[] puntoMenosDelta = null,puntoMasDelta = null;
		puntoMenosDelta = new int[infoADN.size];
		puntoMasDelta = new int[infoADN.size];
		PuntoDeBusqueda nuevoPunto=new PuntoDeBusqueda(infoADN.size);
		while (puntoDelRecuerdo.indice>0.005){
			
			
			if (sinMovimiento>=3){
				nuevoPunto = exploraAlAzar();
			}else{
				//inizializando los puntos mas menos delta
				
				for (int i=0;i < infoADN.size;i++){
					
					int delta = infoADN.getGen(i).brinco;
					puntoMasDelta[i]=puntoDelRecuerdo.parametros[i] + delta;
					puntoMenosDelta[i]= puntoDelRecuerdo.parametros[i] - delta;
					puntoMasDelta=ponteEnRango(puntoMasDelta);
					puntoMenosDelta=ponteEnRango(puntoMenosDelta);
					
					parcial[i] =(mide(puntoMasDelta)- mide(puntoMenosDelta) )/(2.0 * delta);
					muevete[i] = (int)(Math.rint((ak * parcial[i] * (double)delta)));
					if (muevete[i]>2*delta)muevete[i]=2*delta;
					if (muevete[i]<-2*delta)muevete[i]=-2*delta;
				}
				//8000 de vecesQueSeUsoMide equivale a 1000 generaciones del algoritmo evolutivo
				//haciendo promedios de 25 corridas para el evolutivo y 100 para el gradiente
				vecesQueSeUsoMide+=(infoADN.size)*(2)*(4);
				System.out.print("parcial : ");
				for (int i=0;i < infoADN.size;i++)System.out.print(parcial[i]+", ");
					
				
				System.out.println();
				System.out.print("muevete : ");
				nuevoPunto=new PuntoDeBusqueda(infoADN.size);
				for (int i=0;i < infoADN.size;i++){
					nuevoPunto.parametros[i]=puntoDelRecuerdo.parametros[i] + muevete[i];
					System.out.print(muevete[i] + " , ");
					
				}
				System.out.println();
				nuevoPunto.ponteEnRango(infoADN);
			}	
			nuevoPunto.setIndice(mide(nuevoPunto.parametros));
			puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
			vecesQueSeUsoMide+=(2*4);
			System.out.println(nuevoPunto.indice+" < "+puntoDelRecuerdo.indice+ "?");
			
			if (nuevoPunto.indice<puntoDelRecuerdo.indice){
				int suma=0;
				for (int a=0;a<infoADN.size;a++){
					suma+=Math.abs(muevete[a]);
				}
				//System.out.println("suma= "+suma);
				if (suma>0){
					sinMovimiento=0;
				}
				puntoDelRecuerdo.parametros=nuevoPunto.parametros;
				//puntoDelRecuerdo.indice=nuevoPunto.indice;
				
				pintaElPuntoDelRecuerdo();
				bajando.escribe(nuevoPunto, vecesQueSeUsoMide);
											
			}else{
				sinMovimiento++;
				System.out.println("no me movere!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				//nuevoPunto.parametros=puntoDelRecuerdo.parametros;
				//nuevoPunto.indice=puntoDelRecuerdo.indice;
				pintaElPuntoDelRecuerdo();
			}
			
			
		}
	}
	
		
	
	
	private void pintaElPuntoDelRecuerdo() {
		String quien = "" + puntoDelRecuerdo.parametros[0];
		for (int esteGen = 1; esteGen < puntoDelRecuerdo.parametros.length; esteGen++){
			quien = quien + ", " + puntoDelRecuerdo.parametros[esteGen];
		}
		pintaGrid2.fr.setTitle(quien);
		pintaGrid2.pinta(unOutputPara(puntoDelRecuerdo.parametros));
		
		
	}
//	private void pintaElOutputDelNuevoPunto() {
//		String quien = "" + nuevoPunto.parametros[0];
//		for (int esteGen = 1; esteGen < nuevoPunto.parametros.length; esteGen++){
//			quien = quien + ", " + nuevoPunto.parametros[esteGen];
//		}
//		pintaGrid2.fr.setTitle(quien);
//		pintaGrid2.pinta(unOutputPara(nuevoPunto.parametros));
//		
//		
//		
//		
//		
//	}
	private PuntoDeBusqueda exploraAlAzar() {
		//esta es una distribucion como de dos jorobas para que busque mas o lejos que cerca
		//pues este metodo se usa cuando se llega a un maximo local
		System.out.println("buscare al azar porque creo que estoy en optimo local" + sinMovimiento);
		Random oye = new Random();
		PuntoDeBusqueda unPunto=new PuntoDeBusqueda(infoADN.size);
		int brinca=0;
		for (int i=0;i < infoADN.size;i++){
			
			brinca = new Double(((double)infoADN.getGen(i).suIntervalo)/2.0).intValue() + new Double(infoADN.getGen(i).suIntervalo * oye.nextGaussian()).intValue();
			if (oye.nextBoolean()) brinca = -brinca;
			unPunto.parametros[i] = puntoDelRecuerdo.parametros[i] + brinca;
		}
		unPunto.ponteEnRango(infoADN);
		return unPunto;
	}
		
//	private void ponteEnRango() {
//		
//		for (int gen=0;gen < infoADN.size;gen++){
//			if(nuevoPunto.parametros[gen]>infoADN.getGen(gen).max)nuevoPunto.parametros[gen]=infoADN.getGen(gen).max;
//			if(nuevoPunto.parametros[gen]<infoADN.getGen(gen).min)nuevoPunto.parametros[gen]=infoADN.getGen(gen).min;
//			
//		}
//
//		//esto es solo para el cerca pero no tan cerca....osea que es una restriccion 
//		//especifica a un modelo de crecimiento urbano, este codigo no bebe ir aqui
//		if (nuevoPunto.parametros[0]>nuevoPunto.parametros[1]){
//			nuevoPunto.parametros[1]=nuevoPunto.parametros[0];
//		}     
//	}
	private int[] ponteEnRango(int[] punto) {
		
		for (int i=0;i < infoADN.size;i++){
			if(punto[i]>infoADN.getGen(i).max)punto[i]=infoADN.getGen(i).max;
			if(punto[i]<infoADN.getGen(i).min)punto[i]=infoADN.getGen(i).min;
			
		}
		return punto;
		
		
	}
	
	public abstract double mide(int[] parametros);
	public abstract int[][] unOutputPara(int[] parametros);
	
}
