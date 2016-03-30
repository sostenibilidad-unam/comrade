package Metricas;

import java.io.File;

import Modelo.UrbanizandoHibrido;

public class MetricaFmatch extends Metrica {

	private int CuentaIguales;

	public MetricaFmatch(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		
		
	}

	@Override
	public double esTantito(int[][] wanabe) {
		CuentaIguales=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (wanabe[i][j]==comoEste[i][j]){
					CuentaIguales++;
				}
			}
				
		}
		
		return 1.0- (double)CuentaIguales/((double)(numeroDeColumnas*numeroDeRenglones));
		
	}
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		
		MetricaFmatch metrica = new MetricaFmatch(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		
		//metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		System.out.print(CA.mide(10, 2, 2, 100, 291, 331, 80, 100, 684));
		System.out.print(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
	}
}
