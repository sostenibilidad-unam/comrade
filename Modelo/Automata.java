package Modelo;

import javax.swing.JFrame;


public class Automata {
		
	    public int numeroDeCeldasX;
	    public int numeroDeCeldasY;
	    public int[][][] valor; 
		public int iteracion;
		public int estadosPosibles;
		public int numeroMaximoDeIteraciones;
		public JFrame aqui;
		public Automata(int numeroDeCeldasX,int numeroDeCeldasY,int numeroMaximoDeIteraciones,int estadosPosibles){
			this.numeroDeCeldasX=numeroDeCeldasX;
			this.numeroDeCeldasY=numeroDeCeldasY;
			this.estadosPosibles=estadosPosibles;
			this.numeroMaximoDeIteraciones=numeroMaximoDeIteraciones;
			
			valor = new int[numeroDeCeldasX][numeroDeCeldasY][numeroMaximoDeIteraciones+1];
			iteracion = 0;
			
			
		}
			
		
		public void generaElSiguiente() {
			int estaSuma;
			int eras;
			
			for (int i = 0; i<numeroDeCeldasX; i++){
				for (int j = 1; j<=numeroDeCeldasY; j++){
					estaSuma=laSumaPesada(i,j);
					eras=valor[i][j][iteracion];
					if ((eras==0) && (estaSuma>20))valor[i][j][iteracion+1]=1;
					else if ((eras==1) && (estaSuma>60))valor[i][j][iteracion+1]=2;
					else if ((eras==2) && (estaSuma>600))valor[i][j][iteracion+1]=3;
					else if ((eras==3) && (estaSuma>6000))valor[i][j][iteracion+1]=4;
					else valor[i][j][iteracion+1]=valor[i][j][iteracion];
				}
			}
			
		}

		
		public int laSumaPesada(int i, int j) {
			// este metodo suma en la vewcindad de i,j 
			// 1 si el valor es 1, 10 si es 2, y 100 si es 3 
			int suma=0;
			for (int a = -1; a<=1; a++){
				for (int b = -1; b<=1; b++){
					if ( (i+a<=numeroDeCeldasX) && (j+b<=numeroDeCeldasY) ){
						if (valor[i+a][j+b][iteracion]>0){
							suma+=Math.pow(10, valor[i+a][j+b][iteracion] -1);
						}
						
					}
					
				}
			}
			return suma;
		}
		public double distanciaEnCeldas (int x1, int y1, int x2, int y2){
			//esta es la distancia en celdas osea que para obtener la distancia 
			//en alguna unidad de medidad mas universal como metros es necesario 
			//multiplicar por el tama�o de celda que se use en cada aplicacion 
			return Math.sqrt( ((x1-x2)*(x1-x2)) + ((y1-y2)*(y1-y2)) );
		}

		public boolean estaOcupadaLaCelda(int x, int y, int numeroDeIteracion) {
			// este metodo dice si una celda esta ocupada o no
			boolean siOno =false;
				if(valor[x][y][numeroDeIteracion]>0){
					siOno=true;
				}
			return siOno;
		}
		public boolean elGridContieneA(int x, int y){
			//este metodo responde si (x,y) esta en el grid
			boolean siOno =false;
			if( (x>0) && (x<=numeroDeCeldasX) && (y>0) && (y<=numeroDeCeldasY))siOno=true;
			return siOno;
		}
		
		public int laSuma(int i, int j) {
			// TODO Auto-generated method stub
			int suma=0;
			for (int a = -1; a<=1; a++){
				for (int b = -1; b<=1; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (i+a>-1) && (j+b>-1)){
						suma+=valor[i+a][j+b][iteracion];
					}
					
				}
			}
			return suma;
		}
		public int laSumaCiv(int i, int j) {
			// TODO Auto-generated method stub
			int suma=0;
			for (int a = -2; a<=2; a++){
				for (int b = -2; b<=2; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (i+a>-1) && (j+b>-1) && (Math.abs(a*b)<4) && (i+a>=0) && (j+b>=0) ){
						suma+=valor[i+a][j+b][iteracion];
					}
					
				}
			}
			return suma;
		}
		public int laSumaCiv1s(int i, int j) {
			// TODO Auto-generated method stub
			int suma=0;
			for (int a = -2; a<=2; a++){
				for (int b = -2; b<=2; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (i+a>-1) && (j+b>-1) && (Math.abs(a*b)<4) && (i+a>=0) && (j+b>=0) ){
						if (valor[i+a][j+b][iteracion]==1){
							suma++;
						}
						
					}
					
				}
			}
			return suma;
		}
		public int laSumaRadio3(int i, int j) {
			int suma=0;
			for (int a = -2; a<=2; a++){
				for (int b = -2; b<=2; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (i+a>-1) && (j+b>-1) && (i+a>=0) && (j+b>=0) ){
						suma+=valor[i+a][j+b][iteracion];
					}
					
				}
			}
			for (int a = -1; a<=1; a++){
				if ( (i+a<numeroDeCeldasX) && (j+3<numeroDeCeldasY) && (i+a>=0) ){
					
					suma+=valor[i+a][j+3][iteracion];
					
				}
				if ( (i+a<numeroDeCeldasX) && (j-3>=0) && (i+a>=0) ){
					
					suma+=valor[i+a][j-3][iteracion];	
					
				}
				
				
			}
			for (int b = -1; b<=1; b++){
				if ( (i+3<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (j+b>=0) ){
					suma+=valor[i+3][j+b][iteracion];
				}
				if ( (i-3>=0) && (j+b>=0) && (j+b<numeroDeCeldasY) ){
					suma+=valor[i-3][j+b][iteracion];
				}
				
			}
			return suma;
		}
		public int laSumaDe1sRadio3(int i, int j) {
			// aqui falta lo de loas 1s -----------------------------------------------
			
			int suma=0;
			for (int a = -2; a<=2; a++){
				for (int b = -2; b<=2; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (i+a>=0) && (j+b>=0) ){
						if (valor[i+a][j+b][iteracion]==1){
							suma++;
						}
						
					}
					
				}
			}
			for (int a = -1; a<=1; a++){
				if ( (i+a<numeroDeCeldasX) && (j+3<numeroDeCeldasY) && (i+a>=0) ){
					if (valor[i+a][j+3][iteracion]==1){
						suma++;
					}
					
					
				}
				if ( (i+a<numeroDeCeldasX) && (j-3>=0) && (i+a>=0) ){
					if (valor[i+a][j-3][iteracion]==1){
						suma++;
					}
					
					
				}
				
				
			}
			for (int b = -1; b<=1; b++){
				if ( (i+3<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (j+b>=0) ){
					if (valor[i+3][j+b][iteracion]==1){
						suma++;
					}
					
				}
				if ( (i-3>=0) && (j+b>=0) && (j+b<numeroDeCeldasY) ){
					if (valor[i-3][j+b][iteracion]==1){
						suma++;
					}
					
				}
				
			}
			return suma;
		}
		public int laSumaDe1sRadioN(int i, int j, int radio) {
			// aqui falta lo de loas 1s -----------------------------------------------
			
			int suma=0;
			for (int a = 0 - radio; a<=radio; a++){
				for (int b = 0 - radio; b<=radio; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) && (i+a>=0) && (j+b>=0) ){
						double distanciaEnCeldas = Math.sqrt(a*a + b*b);
						if (distanciaEnCeldas < radio){
							if (valor[i+a][j+b][iteracion]==1){
								suma++;
							}
							
						}
						
					}
					
				}
			}
			
			return suma;
		}
		public int cuantosVecinos (int i, int j) {
			// TODO Auto-generated method stub
			int suma=0;
			for (int a = -1; a<=1; a++){
				for (int b = -1; b<=1; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) ){
						if(valor[i+a][j+b][iteracion]>0)suma++;
					}
					
				}
			}
			return suma;
		}
		public int cualEsMayoria(int i, int j) {
			// TODO Auto-generated method stub
			int[] suma=new int[estadosPosibles];
			for (int a = -1; a<=1; a++){
				for (int b = -1; b<=1; b++){
					if ( (i+a<numeroDeCeldasX) && (j+b<numeroDeCeldasY) ){
						suma[valor[i+a][j+b][iteracion]]++;
					}
					
				}
			}
			int laMayorHastaAhora=0;
			int esta=0;
			int noHay=0;
			for (int r=1;r<estadosPosibles;r++){
				if (laMayorHastaAhora<suma[r]){
					esta = r;
					laMayorHastaAhora=suma[r];
				}
				if (suma[r]==0)noHay=r;
			}
			if (laMayorHastaAhora==1)esta=noHay;
			return esta;
		}
		
		public void vaciaValores(){
			
			for (int n = 1; n<=numeroMaximoDeIteraciones; n++){
				for (int i = 0; i<numeroDeCeldasX; i++){
					for (int j = 0; j<numeroDeCeldasY; j++){
						valor[i][j][n]=0;
					}
					
				}
			}
			
		}
	}
