package Metricas;

import java.io.File;

import Modelo.UrbanizandoHibrido;


public class MetricaYMean extends Metrica {

	private int sumay=0;
	private int cuatasSumaste=0;
	private int sumayComoEste=0;
	private int cuatasSumasteComoEste=0;
	private double yMeanComoEste;

	public MetricaYMean(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (comoEste[i][j]>0){
					sumayComoEste+=j;
					cuatasSumasteComoEste++;
				}
			}
				
		}
		yMeanComoEste= (double)sumayComoEste/(double)cuatasSumasteComoEste;
	}

	@Override
	public double esTantito(int[][] wanabe) {
		sumay=0;
		cuatasSumaste=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (wanabe[i][j]>0){
					sumay+=j;
					cuatasSumaste++;
				}
			}
				
		}
		double yMeanWanabe= (double)sumay/(double)cuatasSumaste;
		System.out.println("este =" + yMeanComoEste);
		System.out.println("wanabe =" + yMeanWanabe);
		if (yMeanWanabe<yMeanComoEste){
			return 1.0-(yMeanWanabe/yMeanComoEste);
		}else{
			return 1.0-(yMeanComoEste/yMeanWanabe);
		}
		
	}
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		
		MetricaYMean metrica = new MetricaYMean(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		
		//metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		System.out.println(CA.mide(10, 2, 2, 100, 291, 331, 80, 100, 684));
		System.out.println(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
	}
}