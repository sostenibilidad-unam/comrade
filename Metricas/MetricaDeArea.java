package Metricas;


public class MetricaDeArea extends Metrica {

	
	private int sumaComoEste;
	private int sumaWanabe;

	public MetricaDeArea(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		sumaComoEste=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (comoEste[i][j]>0){
					sumaComoEste++;
				}
			}
				
		}
	}

	@Override
	public double esTantito(int[][] wanabe) {
		sumaWanabe=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (wanabe[i][j]>0){
					sumaWanabe++;
				}
			}
				
		}
		
		if (sumaComoEste<sumaWanabe){
			return 1.0- ((double)sumaComoEste/(double)sumaWanabe);
		}else{
			return 1.0- ((double)sumaWanabe/(double)sumaComoEste);
		}
		
	}

}
