package Metricas;

import java.io.File;

import Modelo.UrbanizandoHibrido;


public class MetricaXMean extends Metrica {

	private int sumax=0;
	private int cuatasSumaste=0;
	private int sumaxComoEste=0;
	private int cuatasSumasteComoEste=0;
	private double xMeanComoEste;

	public MetricaXMean(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (comoEste[i][j]>0){
					sumaxComoEste+=i;
					cuatasSumasteComoEste++;
				}
			}
				
		}
		xMeanComoEste= (double)sumaxComoEste/(double)cuatasSumasteComoEste;
	}

	@Override
	public double esTantito(int[][] wanabe) {
		sumax=0;
		cuatasSumaste=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (wanabe[i][j]>0){
					sumax+=i;
					cuatasSumaste++;
				}
			}
				
		}
		double xMeanWanabe= (double)sumax/(double)cuatasSumaste;
		
		if (xMeanWanabe<xMeanComoEste){
			return 1.0- (xMeanWanabe/xMeanComoEste);
		}else{
			return 1.0-(xMeanComoEste/xMeanWanabe);
		}
		
	}
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		
		MetricaXMean metrica = new MetricaXMean(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		
		//metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		System.out.print(CA.mide(10, 2, 2, 100, 291, 331, 80, 100, 684));
		System.out.print(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
	}
}
