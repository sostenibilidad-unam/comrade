package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Calibradores.PintaGrid;
import Modelo.UrbanizandoHibrido;

public class MetricaDeClusters2 extends Metrica {

	int numeroDeColumnas;
	int numeroDeRenglones;
	public int[][] numerandoClustersWanabe;
	int contadorDeClustersWanabe;
	public int[][] numerandoClustersComoEste;
	int contadorDeClustersComoEste;
	public MetricaDeClusters2(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		this.numeroDeColumnas=numeroDeColumnas;
		this.numeroDeRenglones=numeroDeRenglones;
		numerandoClustersWanabe=new int[numeroDeColumnas][numeroDeRenglones];
		numerandoClustersComoEste=new int[numeroDeColumnas][numeroDeRenglones];
		contadorDeClustersComoEste = 0;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				numerandoClustersComoEste[i][j]=0;
			}
				
		}
		
		
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				if ((numerandoClustersComoEste[i][j]==0) && (comoEste[i][j]>0)){
					contadorDeClustersComoEste++;
					buscaVecinosComoEste(i, j);
				}
			
			}
				
		}
	
		
	
	}

	@Override
	public double esTantito(int[][] wanabe) {
		this.wanabe=wanabe;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				numerandoClustersWanabe[i][j]=0;
			}
				
		}
		
		contadorDeClustersWanabe = 0;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				if ((numerandoClustersWanabe[i][j]==0) && (wanabe[i][j]>0)){
					contadorDeClustersWanabe++;
					buscaVecinosWanabe(i,j);
				}
			
			}
				
		}
		
		
		//System.out.println("contadorDeClustersComoEste = "+ contadorDeClustersComoEste + "contadorDeClustersWanabe = " + contadorDeClustersWanabe);
		if (contadorDeClustersComoEste>contadorDeClustersWanabe){
			return 1.0- ((double)contadorDeClustersWanabe/(double)contadorDeClustersComoEste);
		}else{
			return 1.0-((double)contadorDeClustersComoEste/(double)contadorDeClustersWanabe);
		}
		
	}

	void buscaVecinosWanabe(int i, int j) {
		boolean arriba = false, abajo = false, izquierda = false, derecha = false;
		boolean arribaIzquierda = false, arribaDerecha = false, abajoIzquierda = false, abajoDerecha = false;
		if (i<numeroDeColumnas-1){
			if (numerandoClustersWanabe[i+1][j]==0){
				if (wanabe[i+1][j]>0){
					derecha=true;
					numerandoClustersWanabe[i+1][j]=contadorDeClustersWanabe;
				}
			}
		}	
		
		if (i>1){
			if (numerandoClustersWanabe[i-1][j]==0){
				if (wanabe[i-1][j]>0) {
					izquierda=true;
					numerandoClustersWanabe[i-1][j]=contadorDeClustersWanabe;
				}
			}	
		}
		if (j<numeroDeRenglones-1){
			if (numerandoClustersWanabe[i][j+1]==0){
				if	(wanabe[i][j+1]>0 ){
					abajo=true;
					numerandoClustersWanabe[i][j+1]=contadorDeClustersWanabe;
				}
			}
			if (i<numeroDeColumnas-1){
				if (numerandoClustersWanabe[i+1][j+1]==0){
					if (wanabe[i+1][j+1]>0){
						abajoDerecha=true;
						numerandoClustersWanabe[i+1][j+1]=contadorDeClustersWanabe;
					}
					
				}	
			}
			if (i>1){
				if (numerandoClustersWanabe[i-1][j+1]==0){
					if (wanabe[i-1][j]>0) {
						abajoIzquierda=true;
						numerandoClustersWanabe[i-1][j+1]=contadorDeClustersWanabe;
					}
				}
			}
		}		
		
		if (j>1){
			if (numerandoClustersWanabe[i][j-1]==0){
				if (wanabe[i][j-1]>0){
					arriba=true;
					numerandoClustersWanabe[i][j-1]=contadorDeClustersWanabe;
				}
			}
			if (i<numeroDeColumnas-1){
				if (numerandoClustersWanabe[i+1][j-1]==0){
					if (wanabe[i+1][j-1]>0){
						arribaDerecha=true;
						numerandoClustersWanabe[i+1][j-1]=contadorDeClustersWanabe;
					}
					
				}
			}
			if (i>1){
				if (numerandoClustersWanabe[i-1][j-1]==0){
					if (wanabe[i-1][j]>0) {
						arribaIzquierda=true;
						numerandoClustersWanabe[i-1][j-1]=contadorDeClustersWanabe;
					}	
				}
			}
		}
		if (derecha) buscaVecinosWanabe(i+1, j);
		if (izquierda) buscaVecinosWanabe(i-1, j);
		if (abajo) buscaVecinosWanabe(i, j+1);
		if (arriba) buscaVecinosWanabe(i, j-1);
		if (abajoDerecha) buscaVecinosWanabe(i+1, j+1);
		if (abajoIzquierda) buscaVecinosWanabe(i-1, j+1);
		if (arribaDerecha) buscaVecinosWanabe(i+1, j-1);
		if (arribaIzquierda) buscaVecinosWanabe(i-1, j-1);
		
	}
	void buscaVecinosComoEste(int i, int j) {
		boolean arriba = false, abajo = false, izquierda = false, derecha = false;
		boolean arribaIzquierda = false, arribaDerecha = false, abajoIzquierda = false, abajoDerecha = false;
		if (i<numeroDeColumnas-1){
			if (numerandoClustersComoEste[i+1][j]==0){
				if (wanabe[i+1][j]>0){
					derecha=true;
					numerandoClustersComoEste[i+1][j]=contadorDeClustersComoEste;
				}
			}
		}	
		
		if (i>1){
			if (numerandoClustersComoEste[i-1][j]==0){
				if (wanabe[i-1][j]>0) {
					izquierda=true;
					numerandoClustersComoEste[i-1][j]=contadorDeClustersComoEste;
				}
			}	
		}
		if (j<numeroDeRenglones-1){
			if (numerandoClustersComoEste[i][j+1]==0){
				if	(comoEste[i][j+1]>0 ){
					abajo=true;
					numerandoClustersComoEste[i][j+1]=contadorDeClustersComoEste;
				}
			}
			if (i<numeroDeColumnas-1){
				if (numerandoClustersComoEste[i+1][j+1]==0){
					if (comoEste[i+1][j+1]>0){
						abajoDerecha=true;
						numerandoClustersComoEste[i+1][j+1]=contadorDeClustersComoEste;
					}
					
				}	
			}
			if (i>1){
				if (numerandoClustersComoEste[i-1][j+1]==0){
					if (comoEste[i-1][j]>0) {
						abajoIzquierda=true;
						numerandoClustersComoEste[i-1][j+1]=contadorDeClustersComoEste;
					}
				}
			}
		}		
		
		if (j>1){
			if (numerandoClustersComoEste[i][j-1]==0){
				if (comoEste[i][j-1]>0){
					arriba=true;
					numerandoClustersComoEste[i][j-1]=contadorDeClustersComoEste;
				}
			}
			if (i<numeroDeColumnas-1){
				if (numerandoClustersComoEste[i+1][j-1]==0){
					if (comoEste[i+1][j-1]>0){
						arribaDerecha=true;
						numerandoClustersComoEste[i+1][j-1]=contadorDeClustersComoEste;
					}
					
				}
			}
			if (i>1){
				if (numerandoClustersComoEste[i-1][j-1]==0){
					if (comoEste[i-1][j]>0) {
						arribaIzquierda=true;
						numerandoClustersComoEste[i-1][j-1]=contadorDeClustersComoEste;
					}	
				}
			}
		}
		if (derecha) buscaVecinosComoEste(i+1, j);
		if (izquierda) buscaVecinosComoEste(i-1, j);
		if (abajo) buscaVecinosComoEste(i, j+1);
		if (arriba) buscaVecinosComoEste(i, j-1);
		if (abajoDerecha) buscaVecinosComoEste(i+1, j+1);
		if (abajoIzquierda) buscaVecinosComoEste(i-1, j+1);
		if (arribaDerecha) buscaVecinosComoEste(i+1, j-1);
		if (arribaIzquierda) buscaVecinosComoEste(i-1, j-1);
		
	}
	void buscaVecinosComoEste2(int i, int j) {
		boolean arriba = false, abajo = false, izquierda = false, derecha = false;
		if (i<numeroDeColumnas-1)
		if (numerandoClustersComoEste[i+1][j]==0)
		if (comoEste[i+1][j]>0){
			derecha=true;
			numerandoClustersComoEste[i+1][j]=contadorDeClustersComoEste;
		}
		if (i>1)
		if (numerandoClustersComoEste[i-1][j]==0)
		if (comoEste[i-1][j]>0) {
			izquierda=true;
			numerandoClustersComoEste[i-1][j]=contadorDeClustersComoEste;
		}
		if (j<numeroDeRenglones-1)
		if (numerandoClustersComoEste[i][j+1]==0)
		if	(comoEste[i][j+1]>0 ){
			abajo=true;
			numerandoClustersComoEste[i][j+1]=contadorDeClustersComoEste;
		}
		if (j>1)
		if (numerandoClustersComoEste[i][j-1]==0)
		if (comoEste[i][j-1]>0){
			arriba=true;
			numerandoClustersComoEste[i][j-1]=contadorDeClustersComoEste;
		}
		if (derecha) buscaVecinosComoEste(i+1, j);
		if (izquierda) buscaVecinosComoEste(i-1, j);
		if (abajo) buscaVecinosComoEste(i, j+1);
		if (arriba) buscaVecinosComoEste(i, j-1);
	}
	
	
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		//CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		MetricaDeClusters2 metrica = new MetricaDeClusters2(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		//metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		//System.out.print(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
		int[] estos = {3, 6, 5, 100, 295, 100, 80, 444, 319};
		System.out.print(CA.mide(estos));
		int[][] unOutput = CA.unOutputPara(estos);
		PintaGrid pinta = new PintaGrid(new File("Topilejo/topi1999.txt"),"",5);
		pinta.pinta(unOutput);
		metrica.esTantito(unOutput);
		AsciiGridReader gridReader95 = new AsciiGridReader(new File("Topilejo/topi1995.txt"));
		
		
		
		int numeroDeColumnas = gridReader95.numeroDeColumnas;
		int numeroDeRenglones = gridReader95.numeroDeRenglones;
		String xllcorner = gridReader95.xllcorner; 
		String yllcorner = gridReader95.yllcorner; 
		double cellsize = gridReader95.cellsize;
		int NODATA_value = gridReader95.NODATA_value;
		File esteFile = new File("clusters2.txt");
		AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
		
		unGrid.escribeInt(metrica.numerandoClustersWanabe, numeroDeColumnas, numeroDeRenglones, xllcorner, yllcorner, cellsize, NODATA_value);
		unGrid.close();
		
	}
	
//	public static void main(String[] args) {
//		  
//		
//			AsciiGridReader gridReader95 = new AsciiGridReader(new File("Topilejo/topi1995.txt"));
//			int[][] topilejo95 = gridReader95.getIntArray();
//			int numeroDeColumnas = gridReader95.numeroDeColumnas;
//			int numeroDeRenglones = gridReader95.numeroDeRenglones;
//			String xllcorner = gridReader95.xllcorner; 
//			String yllcorner = gridReader95.yllcorner; 
//			double cellsize = gridReader95.cellsize;
//			int NODATA_value = gridReader95.NODATA_value;
//			
//			gridReader95.close();
//			
////			AsciiGridReader gridReader99 = new AsciiGridReader(new File("Topilejo/topi1999.txt"));
////			int[][] topilejo99 = gridReader99.getIntArray();
////			gridReader99.close();
//			
//			MetricaDeClusters OyeClustersQueTanto= new MetricaDeClusters(topilejo95,numeroDeColumnas,numeroDeRenglones);
//			File esteFile = new File("clusters.txt");
//			AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
//			
//			unGrid.escribeInt(OyeClustersQueTanto.numerandoClustersComoEste, numeroDeColumnas, numeroDeRenglones, xllcorner, yllcorner, cellsize, NODATA_value);
//			unGrid.close();
//			
//		
//		
//	}
}

