package Metricas;

public class MultiescalaClusters extends Metrica {
	MetricaMultiescala multiescalaQueTanto;
	MetricaDeClusters2 clustersQueTanto;
	public MultiescalaClusters(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		multiescalaQueTanto = new MetricaMultiescala(comoEste, numeroDeColumnas, numeroDeRenglones);
		clustersQueTanto = new MetricaDeClusters2(comoEste, numeroDeColumnas, numeroDeRenglones);
	}

	@Override
	public double esTantito(int[][] wanabe) {
		double multiescala = multiescalaQueTanto.esTantito(wanabe);
		double clusters = clustersQueTanto.esTantito(wanabe);
		if (multiescala==0 || clusters==0){
			return (multiescala+clusters)/2.0;

			
		}else{
			return Math.sqrt(multiescala*clusters);
		}
		
	}

}
