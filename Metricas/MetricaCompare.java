package Metricas;


public class MetricaCompare extends Metrica {

	private int sumaComoEste;
	private int sumaWanabe;

	public MetricaCompare(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		sumaComoEste=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				sumaComoEste+=comoEste[i][j];
			}
				
		}
	}

	@Override
	public double esTantito(int[][] wanabe) {
		sumaWanabe=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
					sumaWanabe+=wanabe[i][j];
			}
				
		}
		
		if (sumaComoEste>sumaWanabe){
			return 1.0 - ((double)sumaWanabe/(double)sumaComoEste);
		}else{
			return 1.0 - ((double)sumaComoEste/(double)sumaWanabe);
		}
		
	}

}
