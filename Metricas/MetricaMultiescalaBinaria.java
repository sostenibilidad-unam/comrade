package Metricas;


public class MetricaMultiescalaBinaria extends Metrica{
	// esta clase es una manera de medir la diferencia en la forma de dos grids
	// como una combinacion lineal de el promedio de diferencias en diferentes 
	// escalas espaciales
	
	
	//grids auxiliares
	public int [][] comoEste2;
	public int [][] comoEste4;
	public int [][] comoEste8;
	public int [][] wanabe2;
	public int [][] wanabe4;
	public int [][] wanabe8;
		
	private int estado;
		
	public int suma8;
	public int suma4;
	public int suma2;
	
	public double promedio8;
	public double promedio4;
	public double promedio2;
	//private double numeroDeCeldasAlCuadrado;
	private double numeroDeCeldas;
	public MetricaMultiescalaBinaria(int[][] comoEste, int numeroDeColumnas, int numeroDeRenglones){
		
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		this.numeroDeColumnas=numeroDeColumnas;
		this.numeroDeRenglones=numeroDeRenglones;
		this.comoEste = comoEste;
		
		//numeroDeCeldasAlCuadrado = numeroDeColumnas * numeroDeRenglones * numeroDeColumnas * numeroDeRenglones;
		numeroDeCeldas = numeroDeColumnas * numeroDeRenglones;
				
		wanabe2=new int[3][3];
		wanabe4=new int[5][5];
		wanabe8=new int[9][9];
		
		comoEste2=new int[3][3];
		comoEste4=new int[5][5];
		comoEste8=new int[9][9];
		
		
		// se divide el grid comoEste en 4, 16, y 64 subdiciciones, se cuenta
		// el numero de celdas diferentes de cero en cada subdivicion
		// y se guarda en comoEste2, comoEste4, y comoEste8 respectivamente
		int valorEnElGrid;
		vaciaComoEste();
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				valorEnElGrid=comoEste[i][j];
				if (valorEnElGrid==1){
				
					comoEste2[dondeCaeI(i,2)][dondeCaeJ(j,2)]++;
					comoEste4[dondeCaeI(i,4)][dondeCaeJ(j,4)]++;
					comoEste8[dondeCaeI(i,8)][dondeCaeJ(j,8)]++;	
				}
				
			}
		}
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				System.out.print(" " +  comoEste8[i][j] );
			}
			System.out.println();
		}
		
		
		
	}
	public int dondeCaeI(int i,int diviciones){
		
		return (int) Math.ceil( (i*(double)diviciones)/numeroDeColumnas  );
		
	}
    public int dondeCaeJ(int j,int diviciones){
		
		return (int) Math.ceil( (j*(double)diviciones)/numeroDeRenglones  );
		
	}
	public double esTantito(int[][] wanabe) {
		// este es el metodo publico con el que el Medidor mide la distancia entre 
		// el grid que se le dio en el constructor y el wanabe que se recibe como
		// argumento en este metodo
		
		//this.wanabe=wanabe;
		hazLaPiramideWanabe(wanabe);
//		for (int i=1;i<=8;i++){
//			for (int j=1;j<=8;j++){
//				System.out.print(" " +  wanabe8[i][j][1] );
//			}
//			System.out.println();
//		}
	    sumaLasDiferenciasAlCuadrado();
	    promediaYNormaliza();
	    double combinacionLineal = 0;
	    
		
		combinacionLineal = combinacionLineal + promedio2 + promedio4 + promedio8;
			
		
		combinacionLineal= combinacionLineal/3.0;
	    // falta refinar este calculo....................................
		//return Math.exp(0.0 - combinacionLineal);
		//return Math.pow(1.0 - combinacionLineal, 40);
		//return Math.pow(combinacionLineal,1.0/10.0) - 0.5;
		return combinacionLineal;
	}
	private void promediaYNormaliza() {
		
		promedio2 = ((double) Math.pow(suma2,.5)) / numeroDeCeldas;
		promedio4 = ((double) Math.pow(suma4,.5)) / numeroDeCeldas;
		promedio8 = ((double) Math.pow(suma8,.5)) / numeroDeCeldas;		
		
	}
	private void sumaLasDiferenciasAlCuadrado() {
		// suma2, suma4 y suma8 son unos grids para guardar el calculo de:
		// la sumatoria de (comoEste#-wanabe#)^2 
		// donde comoEste# y wanabe# son la cuenta de celdas de cada estado
		// en cada subdivicuion del dominio original en 4, 16 y 64 partes
		// para mas informacion sobre esta manera de medir la forma 
		// se puede consultar la tesis para la obtencion de grado de fisico
		// de Fidel Serrano Candela disponible en www...
		
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				suma8+=Math.pow(comoEste8[i][j]-wanabe8[i][j],2.0);
				//System.out.print(" " +  suma8[estado] );
			}
			//System.out.println();		
		}
		//System.out.println();	
		for (int i=1;i<=4;i++){
			for (int j=1;j<=4;j++){
				suma4+=Math.pow(comoEste4[i][j]-wanabe4[i][j],2.0);
				//System.out.print(" " +  suma4[estado]);
			}
			//System.out.println();		
		}
		//System.out.println();	
		for (int i=1;i<=2;i++){
			for (int j=1;j<=2;j++){
				suma2+=Math.pow(comoEste2[i][j]-wanabe2[i][j],2.0);
				//System.out.print(" " +  suma2[estado]);
			}
			//System.out.println();	
		}
		//System.out.println();
		
			
		
		
	}
	public void hazLaPiramideWanabe() {
		// se divide el grid wanabe en 4, 16, y 64 subdiviciones, se cuenta
		// el numero de celdas diferentes de cero en cada subdivicion
		// y se guarda en wanabe2, wanabe4, y wanabe8 respectivamente
		// para cada estado
		vaciaWanabe();
		int valorEnElGrid;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				valorEnElGrid=wanabe[i][j];
				if (valorEnElGrid==1){
				
					wanabe2[dondeCaeI(i,2)][dondeCaeJ(j,2)]++;
					wanabe4[dondeCaeI(i,4)][dondeCaeJ(j,4)]++;
					wanabe8[dondeCaeI(i,8)][dondeCaeJ(j,8)]++;
				}
			}
		}
		
	}
	public void hazLaPiramideWanabe(int[][] wanabe) {
		// se divide el grid wanabe en 4, 16, y 64 subdiviciones, se cuenta
		// el numero de celdas diferentes de cero en cada subdivicion
		// y se guarda en wanabe2, wanabe4, y wanabe8 respectivamente
		// para cada estado 
		vaciaWanabe();
		int valorEnElGrid;
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				valorEnElGrid=wanabe[i][j];
				if (valorEnElGrid==1){
					
					wanabe2[dondeCaeI(i,2)][dondeCaeJ(j,2)]++;
					wanabe4[dondeCaeI(i,4)][dondeCaeJ(j,4)]++;
					wanabe8[dondeCaeI(i,8)][dondeCaeJ(j,8)]++;
				}
				
			}
		}
		
	}
	private void vaciaWanabe(){
		
		suma2= 0;
		suma4= 0;
		suma8= 0;
		for (int i=1;i<=2;i++){
			for (int j=1;j<=2;j++){
					wanabe2[i][j]=0;
			}
		}
		for (int i=1;i<=4;i++){
			for (int j=1;j<=4;j++){
					wanabe4[i][j]=0;
			}
		}
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
					wanabe8[i][j]=0;
			}
		}
	}
	
	private void vaciaComoEste(){
		
			
		for (int i=1;i<=2;i++){
			for (int j=1;j<=2;j++){
					comoEste2[i][j]=0;
			}
		}
		for (int i=1;i<=4;i++){
			for (int j=1;j<=4;j++){
				comoEste4[i][j]=0;
			}
		}
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				comoEste8[i][j]=0;
			}
		}
		
	}
	
}
