package Metricas;


public class MetricaDeBorde extends Metrica {
	int bordeComoEste;
	public MetricaDeBorde(int[][] comoEste1, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste1, numeroDeColumnas, numeroDeRenglones);
		
		
		bordeComoEste=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if (comoEste[i][j]>0){
					if ((comoEste[i+1][j]==0) || (comoEste[i-1][j]==0) || (comoEste[i][j+1]==0)|| (comoEste[i][j-1]==0)){
						bordeComoEste++;
					}
				}
				
				
			}
		}
		
	}

	@Override
	public double esTantito(int[][] wanabe1) {
		
		//estos dos fors son para darle una orilla de ceros al grid wanabe,
		//esto se hace para poder calcular en las celdas de la orilla 
		//de la misma forma que en las celdas interiores
		for (int i=0;i<=numeroDeColumnas+1;i++){
			for (int j=0;j<=numeroDeRenglones+1;j++){
				wanabe[i][j]=0;
			}
		}
		
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				wanabe[i+1][j+1]=wanabe1[i][j];
			}
		}
		
		
		int bordeWanabe=0;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				if (wanabe[i][j]>0){
					if ((wanabe[i+1][j]==0) || (wanabe[i-1][j]==0) || (wanabe[i][j+1]==0)|| (wanabe[i][j-1]==0)){
						bordeWanabe++;
					}
				}
				
				
			}
		}
		
		if (bordeWanabe>bordeComoEste){
			
			return 1.0 - (double)bordeComoEste/(double)bordeWanabe;
			
		}else {
			
			return 1.0 - (double)bordeWanabe/(double)bordeComoEste;
			
		}
		
	}
	
}
