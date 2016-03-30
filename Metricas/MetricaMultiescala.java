package Metricas;


public class MetricaMultiescala extends Metrica{
	// esta clase es una manera de medir la diferencia en la forma de dos grids
	// como una combinacion lineal de el promedio de diferencias en diferentes 
	// escalas espaciales
	
	
	//grids auxiliares
	public int [][][] comoEste2;
	public int [][][] comoEste4;
	public int [][][] comoEste8;
	public int [][][] wanabe2;
	public int [][][] wanabe4;
	public int [][][] wanabe8;
		
	private int estado;
		
	public int[] suma8=new int[4];
	public int[] suma4=new int[4];
	public int[] suma2=new int[4];
	
	public double[] promedio8=new double[4];
	public double[] promedio4=new double[4];
	public double[] promedio2=new double[4];
	//private double numeroDeCeldasAlCuadrado;
	private double numeroDeCeldas;
	public MetricaMultiescala(int[][] comoEste, int numeroDeColumnas, int numeroDeRenglones){
		
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		this.numeroDeColumnas=numeroDeColumnas;
		this.numeroDeRenglones=numeroDeRenglones;
		this.comoEste = comoEste;
		
		//numeroDeCeldasAlCuadrado = numeroDeColumnas * numeroDeRenglones * numeroDeColumnas * numeroDeRenglones;
		numeroDeCeldas = numeroDeColumnas * numeroDeRenglones;
				
		wanabe2=new int[3][3][4];
		wanabe4=new int[5][5][4];
		wanabe8=new int[9][9][4];
		
		comoEste2=new int[3][3][4];
		comoEste4=new int[5][5][4];
		comoEste8=new int[9][9][4];
		
		
		// se divide el grid comoEste en 4, 16, y 64 subdiciciones, se cuenta
		// el numero de celdas diferentes de cero en cada subdivicion
		// y se guarda en comoEste2, comoEste4, y comoEste8 respectivamente
		int valorEnElGrid;
		vaciaComoEste();
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				valorEnElGrid=comoEste[i][j];
				if (valorEnElGrid==1){
					estado=1;
				}else if (valorEnElGrid==10){
					estado=2;
				}else if (valorEnElGrid == 100){
					estado=3;
				}else{
					estado=0;
				}
				if (estado>0){
					comoEste2[dondeCaeI(i,2)][dondeCaeJ(j,2)][estado]++;
					comoEste4[dondeCaeI(i,4)][dondeCaeJ(j,4)][estado]++;
					comoEste8[dondeCaeI(i,8)][dondeCaeJ(j,8)][estado]++;	
				}
				
			}
		}
		for (int i=1;i<=8;i++){
			for (int j=1;j<=8;j++){
				System.out.print(" " +  comoEste8[i][j][1] );
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
	    
		for(int h=1;h<=3;h++){
			combinacionLineal = combinacionLineal + promedio2[h] + promedio4[h] + promedio8[h];
			
		}
		combinacionLineal= combinacionLineal/9.0;
	    // falta refinar este calculo....................................
		//return Math.exp(0.0 - combinacionLineal);
		//return Math.pow(1.0 - combinacionLineal, 40);
		//return Math.pow(combinacionLineal,1.0/10.0) - 0.5;
		return combinacionLineal;
	}
	private void promediaYNormaliza() {
		for (int estado=1;estado<=3;estado++){
			promedio2[estado] = ((double) Math.pow(suma2[estado],.5)) / numeroDeCeldas;
			promedio4[estado] = ((double) Math.pow(suma4[estado],.5)) / numeroDeCeldas;
			promedio8[estado] = ((double) Math.pow(suma8[estado],.5)) / numeroDeCeldas;
		}
		
			
		
	}
	private void sumaLasDiferenciasAlCuadrado() {
		// suma2, suma4 y suma8 son unos grids para guardar el calculo de:
		// la sumatoria de (comoEste#-wanabe#)^2 
		// donde comoEste# y wanabe# son la cuenta de celdas de cada estado
		// en cada subdivicuion del dominio original en 4, 16 y 64 partes
		// para mas informacion sobre esta manera de medir la forma 
		// se puede consultar la tesis para la obtencion de grado de fisico
		// de Fidel Serrano Candela disponible en www...
		
		for (int estado=1;estado<=3;estado++){
			for (int i=1;i<=8;i++){
				for (int j=1;j<=8;j++){
					suma8[estado]+=Math.pow(comoEste8[i][j][estado]-wanabe8[i][j][estado],2.0);
					//System.out.print(" " +  suma8[estado] );
				}
				//System.out.println();		
			}
			//System.out.println();	
			for (int i=1;i<=4;i++){
				for (int j=1;j<=4;j++){
					suma4[estado]+=Math.pow(comoEste4[i][j][estado]-wanabe4[i][j][estado],2.0);
					//System.out.print(" " +  suma4[estado]);
				}
				//System.out.println();		
			}
			//System.out.println();	
			for (int i=1;i<=2;i++){
				for (int j=1;j<=2;j++){
					suma2[estado]+=Math.pow(comoEste2[i][j][estado]-wanabe2[i][j][estado],2.0);
					//System.out.print(" " +  suma2[estado]);
				}
				//System.out.println();	
			}
			//System.out.println();
		}
			
		
		
	}
	public void hazLaPiramideWanabe() {
		// se divide el grid wanabe en 4, 16, y 64 subdiviciones, se cuenta
		// el numero de celdas diferentes de cero en cada subdivicion
		// y se guarda en wanabe2, wanabe4, y wanabe8 respectivamente
		// para cada estado
		vaciaWanabe();
		int valorEnElGrid;
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				valorEnElGrid=wanabe[i][j];
				if (valorEnElGrid==1){
					estado=1;
				}else if (valorEnElGrid==10){
					estado=2;
				}else if (valorEnElGrid == 100){
					estado=3;
				}else{
					estado=0;
				}
				if (estado>0){
					wanabe2[dondeCaeI(i,2)][dondeCaeJ(j,2)][estado]++;
					wanabe4[dondeCaeI(i,4)][dondeCaeJ(j,4)][estado]++;
					wanabe8[dondeCaeI(i,8)][dondeCaeJ(j,8)][estado]++;
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
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				valorEnElGrid=wanabe[i][j];
				if (valorEnElGrid==1){
					estado=1;
				}else if (valorEnElGrid==10){
					estado=2;
				}else if (valorEnElGrid == 100){
					estado=3;
				}else{
					estado=0;
				}
				if (estado>0){
					wanabe2[dondeCaeI(i,2)][dondeCaeJ(j,2)][estado]++;
					wanabe4[dondeCaeI(i,4)][dondeCaeJ(j,4)][estado]++;
					wanabe8[dondeCaeI(i,8)][dondeCaeJ(j,8)][estado]++;
				}
				
			}
		}
		
	}
	private void vaciaWanabe(){
		for (int e=1;e<=3;e++){
			suma2[e]= 0;
			suma4[e]= 0;
			suma8[e]= 0;
			for (int i=1;i<=2;i++){
				for (int j=1;j<=2;j++){
						wanabe2[i][j][e]=0;
				}
			}
			for (int i=1;i<=4;i++){
				for (int j=1;j<=4;j++){
						wanabe4[i][j][e]=0;
				}
			}
			for (int i=1;i<=8;i++){
				for (int j=1;j<=8;j++){
						wanabe8[i][j][e]=0;
				}
			}
		}
	}
	private void vaciaComoEste(){
		for (int e=1;e<=3;e++){
			
			for (int i=1;i<=2;i++){
				for (int j=1;j<=2;j++){
						comoEste2[i][j][e]=0;
				}
			}
			for (int i=1;i<=4;i++){
				for (int j=1;j<=4;j++){
					comoEste4[i][j][e]=0;
				}
			}
			for (int i=1;i<=8;i++){
				for (int j=1;j<=8;j++){
					comoEste8[i][j][e]=0;
				}
			}
		}
	}
	
}
