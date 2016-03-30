package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Calibradores.PintaGrid;
import Modelo.UrbanizandoHibrido;


public class MetricaDeNuevaArea{
	int numeroDeColumnas;
	int numeroDeRenglones;
	int clustersComoEste;
	int clustersWanabe;
	boolean[][] visitedComoEste; 
	boolean[][] visitedWanabe; 
	int[][] urbanoFinal;
	int[][] urbanoInicial;
	int[][] wanabe;
	int numeroDeCeldasInicial = 0;
	int numeroDeCeldasFinal = 0;
	int numeroDeCeldasNuevas = 0;
	public double factorNormalizante;
	public MetricaDeNuevaArea(int[][] urbanoInicial, int[][] urbanoFinal, int numeroDeColumnas,int numeroDeRenglones) {
		this.urbanoInicial = urbanoInicial;
		this.urbanoFinal = urbanoFinal;
		this.numeroDeColumnas=numeroDeColumnas;
		this.numeroDeRenglones=numeroDeRenglones;
		
		numeroDeCeldasInicial = 0;
		numeroDeCeldasFinal = 0;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				numeroDeCeldasInicial+=urbanoInicial[i][j];
				numeroDeCeldasFinal+=urbanoFinal[i][j];
			}
					
		}		
		numeroDeCeldasNuevas = numeroDeCeldasFinal - numeroDeCeldasInicial;
	
	}
	

	public double esTantito(int[][] wanabe) {
		this.wanabe = wanabe;
		
		
		int numeroDeCeldasFinalWanabe = 0;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				
				numeroDeCeldasFinalWanabe+=wanabe[i][j];
			}
					
		}	
		int numeroDeCeldasNuevasWanabe = numeroDeCeldasFinalWanabe - numeroDeCeldasInicial;
		
		
		System.out.println("numeroDeCeldasNuevas = "+ numeroDeCeldasNuevas + "numeroDeCeldasNuevasWanabe = " + numeroDeCeldasNuevasWanabe);
		if (numeroDeCeldasNuevas>numeroDeCeldasNuevasWanabe){
			return 1.0- ((double)numeroDeCeldasNuevasWanabe/(double)numeroDeCeldasNuevas);
		}else{
			return 1.0-((double)numeroDeCeldasNuevas/(double)numeroDeCeldasNuevasWanabe);
		}
		
	}
	public void  normalizateConEste(int[][] paraNormalizar){
		factorNormalizante = esTantito(paraNormalizar);
		System.out.println("factorNormalizante = " +factorNormalizante);
	}
	public void  normalizateConEste(File fileParaNormalizar){
		AsciiGridReader reader = new AsciiGridReader(fileParaNormalizar);
		int[][] arrayParaNormalizar = reader.getIntArray();
		normalizateConEste(arrayParaNormalizar);
		reader.close();
	}
	

	public double esTantitoNormalizado(int[][] wanabe){
		return esTantito(wanabe)/factorNormalizante;
	}
	
	
	public static void main(String[] args) {
//		int [][] a = {	{1,0,0,0,0},
//						{1,1,0,0,1},
//						{0,0,0,0,1},
//						{1,1,0,0,1},
//						{1,1,0,0,1}};
//		int [][] b = {	{1,0,0,0,0},
//						{1,1,0,0,1},
//						{0,0,0,0,0},
//						{1,1,0,0,1},
//						{1,1,0,0,1}};
//		
//		MetricaDeClusters queTanto = new MetricaDeClusters(a,5,5);
//		System.out.println(queTanto.esTantito(b));
		AsciiGridReader aReader = new AsciiGridReader(new File("/Users/fidel/25avos/x2y2", "urb2000.txt"));
		int[][] a = aReader.getIntArray();
		aReader.close();
		AsciiGridReader bReader = new AsciiGridReader(new File("/Users/fidel/25avos/x2y2", "urb2010.txt"));
		int[][] b = bReader.getIntArray();
		bReader.close();
		AsciiGridReader cReader = new AsciiGridReader(new File("/Users/fidel/25avos/x2y2", "urbano2010.txt"));
		int[][] c = cReader.getIntArray();
		cReader.close();
		MetricaDeNuevaArea queTanto = new MetricaDeNuevaArea(a,b,344,400);
		System.out.println(queTanto.esTantito(c));
		
		
	}
}	

