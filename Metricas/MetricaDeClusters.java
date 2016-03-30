package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Calibradores.PintaGrid;
import Modelo.UrbanizandoHibrido;


public class MetricaDeClusters{
	int numeroDeColumnas;
	int numeroDeRenglones;
	int clustersComoEste;
	int clustersWanabe;
	boolean[][] visitedComoEste; 
	boolean[][] visitedWanabe; 
	int[][] comoEste;
	int[][] wanabe;
	public double factorNormalizante;
	public MetricaDeClusters(int[][] comoEste, int numeroDeColumnas,int numeroDeRenglones) {
		this.comoEste = comoEste;
		this.numeroDeColumnas=numeroDeColumnas;
		this.numeroDeRenglones=numeroDeRenglones;
		visitedComoEste = new boolean[numeroDeColumnas][numeroDeRenglones];
		clustersComoEste = 0;
		for (int i=0;i<numeroDeColumnas;i++)
			for (int j=0;j<numeroDeRenglones;j++)
				if (comoEste[i][j]>0 && !visitedComoEste[i][j]){
					DFSComoEste(i,j);
					clustersComoEste++;
				}
	
	}

	public void DFSComoEste(int x, int y) {
		// TODO Auto-generated method stub
		visitedComoEste[x][y] = true;
		  // iterate over neighbours
		for (int dx=-1; dx<=1; dx++)
		    for (int dy=-1; dy<=1; dy++)
		    	if (x+dx>=0 && x+dx<numeroDeColumnas && y+dy>=0 && y+dy<numeroDeRenglones && !visitedComoEste[x+dx][y+dy])
		    		if (comoEste[x+dx][y+dy]>0) DFSComoEste(x+dx, y+dy);
	}
	private void DFSWanabe(int x, int y) {
		// TODO Auto-generated method stub
		visitedWanabe[x][y] = true;
		  // iterate over neighbours
		for (int dx=-1; dx<=1; dx++)
		    for (int dy=-1; dy<=1; dy++)
		    	if (x+dx>=0 && x+dx<numeroDeColumnas && y+dy>=0 && y+dy<numeroDeRenglones && !visitedWanabe[x+dx][y+dy] )
		    		if (wanabe[x+dx][y+dy]>0) DFSWanabe(x+dx, y+dy);
	}

	public double esTantito(int[][] wanabe) {
		this.wanabe = wanabe;
		visitedWanabe = new boolean[numeroDeColumnas][numeroDeRenglones];
		clustersWanabe = 0;
		for (int i=0;i<numeroDeColumnas;i++)
			for (int j=0;j<numeroDeRenglones;j++)
				if (wanabe[i][j]>0 && !visitedWanabe[i][j]){
					DFSWanabe(i,j);
					clustersWanabe++;
				}

		
		
		
		System.out.println("clustersComoEste = "+ clustersComoEste + "clustersWanabe = " + clustersWanabe);
		if (clustersComoEste>clustersWanabe){
			return 1.0- ((double)clustersWanabe/(double)clustersComoEste);
		}else{
			return 1.0-((double)clustersComoEste/(double)clustersWanabe);
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
		MetricaDeClusters queTanto = new MetricaDeClusters(a,344,400);
		System.out.println(queTanto.esTantito(b));
		
	}
}	

