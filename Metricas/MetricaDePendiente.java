package Metricas;

import java.io.File;

import Calibradores.PintaGrid;
import Modelo.UrbanizandoHibrido;


public class MetricaDePendiente extends Metrica {
	private double sumaPendienteComoEste=0;
	private int lasQueSumasteComoEste=0;
	double[][] pendiente;
	private int lasQueSumasteWanabe=0;
	private double sumaPendienteWanabe=0;
	private double pendienteComoEste;
	private double pendienteWanabe;

	//esta metrica recibe en el constructor un grid con la pendiente ademas de lo normal que 
	//reciben todas las metricas
	public MetricaDePendiente(int[][] comoEste, double[][] pendiente, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		
		this.pendiente=new double[numeroDeColumnas+1][numeroDeRenglones+1]; 
		this.pendiente=pendiente;
		sumaPendienteComoEste=0.0;
		lasQueSumasteComoEste=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (comoEste[i][j]>0){
					sumaPendienteComoEste+=pendiente[i][j];
					lasQueSumasteComoEste++;
				}
			}
				
		}
		pendienteComoEste = sumaPendienteComoEste / (double)lasQueSumasteComoEste;
		System.out.println("pendienteComoEste = "+pendienteComoEste);
	}

	@Override
	public double esTantito(int[][] wanabe) {
		sumaPendienteWanabe=0.0;
		lasQueSumasteWanabe=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (wanabe[i][j]>0){
					sumaPendienteWanabe+=pendiente[i][j];
					lasQueSumasteWanabe++;
				}
			}
				
		}
		pendienteWanabe = sumaPendienteWanabe / (double)lasQueSumasteWanabe;
		System.out.println("pendienteWanabe = "+pendienteWanabe);
		
		if (pendienteComoEste<pendienteWanabe){
			return 1.0-(pendienteComoEste/pendienteWanabe);
		}else{
			return 1.0-(pendienteWanabe/pendienteComoEste);
		}
		
	}
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		
		MetricaDePendiente metrica = new MetricaDePendiente(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		
		//metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		//System.out.print(CA.mide(1, 2, 2, 100, 295, 100, 80, 444, 319));
		System.out.print(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
	}
}
