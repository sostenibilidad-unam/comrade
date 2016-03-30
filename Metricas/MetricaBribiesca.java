package Metricas;

public class MetricaBribiesca extends Metrica {

	
		int areaComoEste, perimetroComeEste;
		double compactnesComoEste;
		public MetricaBribiesca(int[][] comoEste1, int numeroDeColumnas,
				int numeroDeRenglones) {
			super(comoEste1, numeroDeColumnas, numeroDeRenglones);
			
			
			areaComoEste=0;
			perimetroComeEste=0;
			
			
			for (int i=1;i<=numeroDeColumnas;i++){
				for (int j=1;j<=numeroDeRenglones;j++){
					if (comoEste[i][j]>0){
							areaComoEste++;
					
							if (comoEste[i+1][j]==0){
								perimetroComeEste++;
							}
							if (comoEste[i-1][j]==0){
								perimetroComeEste++;
							}
							if (comoEste[i][j+1]==0){
								perimetroComeEste++;
							}
							if (comoEste[i][j-1]==0){
								perimetroComeEste++;
							}
												
					}
					
				}
			}
			double n = (double)areaComoEste;
			double P = (double)perimetroComeEste;
			compactnesComoEste= ( n - (P / 4.0) ) / ( n - Math.sqrt(n) );
			
		}
		
		@Override
		public double esTantito(int[][] wanabe1) {
			int areaWanabe, perimetroWanabe;
			double compactnesWanabe;
			
			//estos dos fors son para darle una orilla de ceros al grid wanabe,
			//esto se hace para poder calcular en las celdas de la orilla 
			//de la misma forma que en las celdas interiores
			for (int i=0;i<=numeroDeColumnas+1;i++){
				for (int j=0;j<=numeroDeRenglones+1;j++){
					wanabe[i][j]=0;
				}
			}
			
			for (int i=1;i<=numeroDeColumnas;i++){
				for (int j=1;j<=numeroDeRenglones;j++){
					wanabe[i][j]=wanabe1[i][j];
				}
			}
			
			
			areaWanabe=0;
			perimetroWanabe=0;
			
			
			for (int i=1;i<=numeroDeColumnas;i++){
				for (int j=1;j<=numeroDeRenglones;j++){
					if (wanabe[i][j]>0){
							areaWanabe++;
					
							if (wanabe[i+1][j]==0){
								perimetroWanabe++;
							}
							if (wanabe[i-1][j]==0){
								perimetroWanabe++;
							}
							if (wanabe[i][j+1]==0){
								perimetroWanabe++;
							}
							if (wanabe[i][j-1]==0){
								perimetroWanabe++;
							}
												
					}
					
				}
			}
			double n = (double)areaWanabe;
			double P = (double)perimetroWanabe;
			compactnesWanabe= ( n - (P/4) ) / ( n - Math.sqrt(n) );
			
			if (compactnesWanabe>compactnesComoEste){
				
				return 1.0 - compactnesComoEste/compactnesWanabe;
				
			}else {
				
				return 1.0 - compactnesWanabe/compactnesComoEste;
				
			}
			
		}
		
}
	
	
	
	
	
	


