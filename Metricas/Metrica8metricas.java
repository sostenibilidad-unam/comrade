package Metricas;

import java.io.File;

import Modelo.UrbanizandoHibrido;

public class Metrica8metricas extends Metrica {

	
	MetricaCompare compareQueTanto;
	MetricaDeArea areasQueTanto;
	MetricaDeBorde bordesQueTanto;
	MetricaDeClusters clusterQueTanto;
	MetricaDePendiente pendienteQueTanto;
	MetricaXMean xmeanQueTanto;
	MetricaYMean yminQueTanto;
	MetricaFmatch fmatchQueTanto;
	
	public Metrica8metricas(int[][] comoEste, double[][] pendiente, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		compareQueTanto= new MetricaCompare(comoEste, numeroDeColumnas, numeroDeRenglones);
		areasQueTanto=new MetricaDeArea(comoEste, numeroDeColumnas, numeroDeRenglones);
		bordesQueTanto=new MetricaDeBorde(comoEste, numeroDeColumnas, numeroDeRenglones);
		clusterQueTanto=new MetricaDeClusters(comoEste, numeroDeColumnas, numeroDeRenglones);
		pendienteQueTanto=new MetricaDePendiente(comoEste, pendiente, numeroDeColumnas, numeroDeRenglones);
		xmeanQueTanto=new MetricaXMean(comoEste, numeroDeColumnas, numeroDeRenglones);
		yminQueTanto=new MetricaYMean(comoEste, numeroDeColumnas, numeroDeRenglones);
		fmatchQueTanto=new MetricaFmatch(comoEste, numeroDeColumnas, numeroDeRenglones);
		
	
	}

	@Override
	public double esTantito(int[][] wanabe) {
		double compare = compareQueTanto.esTantito(wanabe);
		double areas = areasQueTanto.esTantito(wanabe);
		double bordes = bordesQueTanto.esTantito(wanabe);
		double cluster = clusterQueTanto.esTantito(wanabe);
		double pendiente = pendienteQueTanto.esTantito(wanabe);
		double xmean = xmeanQueTanto.esTantito(wanabe);
		double ymin = yminQueTanto.esTantito(wanabe);
		double fmatch = fmatchQueTanto.esTantito(wanabe);
		System.out.println("" + compare +", "+areas+", "+ bordes +", "+ cluster +", "+ pendiente +", "+ xmean +", "+ymin +", "+fmatch);
		//return compare*areas*bordes*cluster*pendiente*xmean*ymin*fmatch;
		return Math.pow(compare*areas*bordes*cluster*pendiente*xmean*ymin*fmatch, 1.0/8.0);
		//return (compare+areas+bordes+cluster+pendiente+xmean+ymin+fmatch)/8.0;
		
	}
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		Metrica8metricas metrica = new Metrica8metricas(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		System.out.print(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
	}

}


