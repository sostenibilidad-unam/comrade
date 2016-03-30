package Metricas;


public class Metrica7metricas extends Metrica {
	MetricaCompare compareQueTanto;
	MetricaDeArea areasQueTanto;
	MetricaDeBorde bordesQueTanto;
	MetricaDeClusters clusterQueTanto;
	MetricaDePendiente pendienteQueTanto;
	MetricaXMean xmeanQueTanto;
	MetricaYMean yminQueTanto;
	
	public Metrica7metricas(int[][] comoEste, double[][] pendiente, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		compareQueTanto= new MetricaCompare(comoEste, numeroDeColumnas, numeroDeRenglones);
		areasQueTanto=new MetricaDeArea(comoEste, numeroDeColumnas, numeroDeRenglones);
		bordesQueTanto=new MetricaDeBorde(comoEste, numeroDeColumnas, numeroDeRenglones);
		clusterQueTanto=new MetricaDeClusters(comoEste, numeroDeColumnas, numeroDeRenglones);
		pendienteQueTanto=new MetricaDePendiente(comoEste, pendiente, numeroDeColumnas, numeroDeRenglones);
		xmeanQueTanto=new MetricaXMean(comoEste, numeroDeColumnas, numeroDeRenglones);
		yminQueTanto=new MetricaYMean(comoEste, numeroDeColumnas, numeroDeRenglones);
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
		
		return compare*areas*bordes*cluster*pendiente*xmean*ymin;
		
	}

}
