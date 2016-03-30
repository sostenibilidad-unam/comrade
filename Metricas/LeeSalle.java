package Metricas;

public class LeeSalle extends Metrica{

	public LeeSalle(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double esTantito(int[][] wanabe) {
		int interseccion=0;
		int union=0;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				if ((comoEste[i][j]>0) && (wanabe[i][j]>0)) {
					interseccion++;
				}
				if ((comoEste[i][j]>0) || (wanabe[i][j]>0)) {
					union++;
				}
			}
		}
		
				
		return interseccion/union;
	}
	

}
