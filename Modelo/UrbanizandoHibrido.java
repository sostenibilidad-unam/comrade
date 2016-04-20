package Modelo;

import java.io.File;
import java.util.Random;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Calibradores.CodigoGenetico;
import Metricas.Metrica;
import Metricas.MetricaMultiescala;


public class UrbanizandoHibrido extends Automata{
	// estos son los parametros del automatas
	// 	para cambiar del estado 0 al 1
	int vecino_0=40;//
    int vecino_carretera_0=30;
    int vecino_pueblo=70;
	int buffer_carretera_0=400;
	int buffer_pueblo=400;
	int buffer_carr_pueb=400;
    
	//	 para cambiar del estado 1 al 2
	int vecino_1 = 80;
	int vecino_carretera_1 = 666;
	int buffer_carretera_1 = 50;

	//	para cambiar del estado 2 al 3
	int vecino_2 = 6666; 
	int vecino_carretera_2 = 5555;
	int buffer_carretera_2 = 100;
	
//	public int numeroDeRenglones;
//	public int numeroDeColumnas;
	double cellsize;
	String xllcorner;
	String yllcorner;
	int NODATA_value;
	
	
	double[][] dificultad;
    
    
	int lasIteracionesDeseadas=0; //lasIteracionesDeseadas son los años de prueba
								
	//double wagri = .8; // peso agricultura-bosque
	double wpend = .99; // peso pendiente
	
	public double distanciaMenor;
	
	public int x0;
	public int y0;
	
	public Metrica queTanto;
	double[][] dist_vias;
	int [][] paraEsteGuey;
	public double[][] pendiente;
	public int iteraciones;
	public int[][] gridMeta;
	public int[][] gridPromedio;
	public String[] nombresDeLosParametros;
	public CodigoGenetico infoADN; 
	
	public UrbanizandoHibrido(int numeroDeCeldasX, int numeroDeCeldasY, int numeroMaximoDeIteraciones, int estadosPosibles) {
		super(numeroDeCeldasX, numeroDeCeldasY, numeroMaximoDeIteraciones,
				estadosPosibles);
		//este es el constructor que hereda de Automata
		dist_vias = new double[numeroDeCeldasX+1][numeroDeCeldasY+1];
		pendiente = new double[numeroDeCeldasX+1][numeroDeCeldasY+1];
		paraEsteGuey = new int[numeroDeCeldasX+1][numeroDeCeldasY+1];
		
		
		nombresDeLosParametros = new String[9];
		nombresDeLosParametros[0]="privacidad";
		nombresDeLosParametros[1]="gregariedad";
		nombresDeLosParametros[2]="difusion1";
		nombresDeLosParametros[3]="bufer1";
		nombresDeLosParametros[4]="bufer2";
		nombresDeLosParametros[5]="bufer3";
		nombresDeLosParametros[6]="difusion10";
		nombresDeLosParametros[7]="bufer4";
		nombresDeLosParametros[8]="difusion100";
		
		infoADN = new CodigoGenetico(9);
		infoADN.setRango(0, 2, 5);
		infoADN.setRango(1, 2, 22);
		infoADN.setRango(2, 5, 22);
		infoADN.setRango(3, 20, 333);
		infoADN.setRango(4, 20, 333);
		infoADN.setRango(5, 20, 333);
		infoADN.setRango(6, 20, 80);
		infoADN.setRango(7, 20, 300);
		infoADN.setRango(8, 200, 800);
		
		
		iteracion=0;
		System.out.println("creando CA...");
		
	}
   
	public void quieroCrecer(int timeSteps) {
		// TODO Auto-generated method stub
		for(int t=1;t<timeSteps;t++){
			System.out.println("intentando generar.........generacion "+ iteracion);
			generaElSiguiente();
			iteracion++;
			escribeGrid(iteracion);
		}
	}
	public int[][] dameLaIteracion(int i){
		int[][] laIteracion=new int[numeroDeCeldasX+1][numeroDeCeldasY+1];
		for (int k=1;k<=numeroDeCeldasX;k++){
			for(int l=1;l<=numeroDeCeldasY;l++){
				laIteracion[k][l]=valor[k][l][i];
			}
		}
		return laIteracion;
		
	}
	public void setDistVias(){
		System.out.println("cargando dist_vias...");
		AsciiGridReader gridDistVias = new AsciiGridReader("escoge dist_vias");
		dist_vias = gridDistVias.getDoubleArray();
		gridDistVias.close();
	}
	public void setDistVias(File distViasFile){
		System.out.println("cargando dist_vias...");
		AsciiGridReader gridDistVias = new AsciiGridReader(distViasFile);
		dist_vias = gridDistVias.getDoubleArray();
		gridDistVias.close();
	}
	
	public void setPendiente(){
		AsciiGridReader pendienteReader = new AsciiGridReader("escoge pendiente");
		pendiente = pendienteReader.getDoubleArray();
		pendienteReader.close();
	}
	public void setPendiente(File pendienteFile){
		AsciiGridReader pendienteReader = new AsciiGridReader(pendienteFile);
		pendiente = pendienteReader.getDoubleArray();
		pendienteReader.close();
	}
	public void setInitialGrid(File gridInicialFile){
		//los metodos setInitialGrid toman un grid (array de 2 dimanciones)
		//y acomodan sus valores en el plano inicial de un array de 3 dimenciones 
		System.out.println("cargando grid inicial...");
		AsciiGridReader gridInicialReader = new AsciiGridReader(gridInicialFile);
		int[][] gridInicial = gridInicialReader.getIntArray();
		for (int k=1;k<=numeroDeCeldasX;k++){
			for(int l=1;l<=numeroDeCeldasY;l++){
				valor[k][l][0]=gridInicial[k][l];
			}
		}
		
		xllcorner = gridInicialReader.xllcorner; 
		yllcorner = gridInicialReader.yllcorner; 
		cellsize = gridInicialReader.cellsize;
		NODATA_value = gridInicialReader.NODATA_value;
		gridInicialReader.close();
	}
	public void setInitialGrid(String titulo){
		System.out.println("cargando grid inicial...");
		//el constructor de AsciiGridReader que resive un string abre un 
		//FileChooser para dejar que el usuario escoja su file .txt
		AsciiGridReader gridInicialReader = new AsciiGridReader(titulo);
		int[][] gridInicial = gridInicialReader.getIntArray();
		for (int k=1;k<=numeroDeCeldasX;k++){
			for(int l=1;l<=numeroDeCeldasY;l++){
				valor[k][l][0]=gridInicial[k][l];
			}
		}
		xllcorner = gridInicialReader.xllcorner; 
		yllcorner = gridInicialReader.yllcorner; 
		cellsize = gridInicialReader.cellsize;
		NODATA_value = gridInicialReader.NODATA_value;
		gridInicialReader.close();
	}
	public void setGoalGrid(File gridMetaFile){
		System.out.println("cargando grid meta...");
		AsciiGridReader gridMetaReader = new AsciiGridReader(gridMetaFile);
		gridMeta = gridMetaReader.getIntArray();
		
		//queTanto = new MetricaMultiescala(gridMeta, gridMetaReader.numeroDeColumnas, gridMetaReader.numeroDeRenglones);
		gridMetaReader.close();
	}
	public void setGoalGrid(String titulo){
		System.out.println("cargando grid meta...");
		AsciiGridReader gridMetaReader = new AsciiGridReader(titulo);
		gridMeta = gridMetaReader.getIntArray();
		
		//queTanto = new MetricaMultiescala(gridMeta, gridMetaReader.numeroDeColumnas, gridMetaReader.numeroDeRenglones);
		//este renlon ahora se hace en setMetric()
		
		gridMetaReader.close();
	}
	public void escribeGrid(int estaGeneracion) {
		File esteFile = new File("grid"+estaGeneracion);
		AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
		int[][] elNuevoGrid = null;
		for(int i=1; i<=numeroDeCeldasX;i++){
			for(int j=1; j<=numeroDeCeldasY; j++){
				elNuevoGrid[i][j]=valor[i][j][estaGeneracion];
			}
		}
		unGrid.escribeInt(elNuevoGrid, numeroDeCeldasX, numeroDeCeldasY, xllcorner, yllcorner, cellsize, NODATA_value);
		unGrid.close();
	}
	public void escribeGrid(File aquiMero) {
		AsciiGridWriter unGrid = new AsciiGridWriter(aquiMero);
		int[][] elNuevoGrid = new int[numeroDeCeldasX+1][numeroDeCeldasY+1];
		for(int i=1; i<=numeroDeCeldasX;i++){
			for(int j=1; j<=numeroDeCeldasY; j++){
				elNuevoGrid[i][j]=valor[i][j][iteracion];
			}
		}
		unGrid.escribeInt(elNuevoGrid, numeroDeCeldasX, numeroDeCeldasY, xllcorner, yllcorner, cellsize, NODATA_value);
		unGrid.close();
	}
	
	public double distancia (int x1, int y1, int x2, int y2){
		//se multiplica por cellsize para tener la distancia en metros
		return (cellsize * distanciaEnCeldas(x1, y1, x2, y2));
	}
	private void revisaElAnilloCuadrado(int radio,int numeroDeIteracion){
		//este for recorre las celdas en las lineas horizontales a -radio y +radio
		for (int laXVariando=x0-radio;laXVariando<=x0+radio;laXVariando++){
			checaEsta(laXVariando,y0-radio,numeroDeIteracion);
			checaEsta(laXVariando,y0+radio,numeroDeIteracion);
		}
        //este for recorre las celdas en las lineas verticales a -radio y +radio
		for (int laYVariando=y0-radio+1;laYVariando<=y0+radio-1;laYVariando++){
			checaEsta(x0-radio,laYVariando,numeroDeIteracion);
			checaEsta(x0+radio,laYVariando,numeroDeIteracion);
		}
	}
	
	private void checaEsta(int x, int y, int numeroDeIteracion) {
		//este metodo checa si (x,y) esta en el grid, y es una celda ocupada 
		if (elGridContieneA(x,y)){
			if(estaOcupadaLaCelda(x,y,numeroDeIteracion)){
				double estaDistancia= distancia(x,y,x0,y0);
				comparaConLaDistaciaMenor(estaDistancia);
				
			}
		}
		
	}
	
	
	
	public double distanciaAlaMancha(int x0, int y0, int numeroDeIteracion){
		//este metodo calcula la distancia de una celda dada a un grid
		this.x0=x0;
		this.y0=y0;
		if (estaOcupadaLaCelda(x0, y0, numeroDeIteracion)){
			distanciaMenor=0.0;
		}else{
			distanciaMenor=8888;
			int radio=0;
			while ( (distanciaMenor==8888)) {
				radio++;
				revisaElAnilloCuadrado(radio, numeroDeIteracion);
			}
			for (int esteRadio=radio+1;esteRadio<=Math.ceil(distanciaMenor);esteRadio++){
				revisaElAnilloCuadrado(esteRadio, numeroDeIteracion);
			}
		}
		return distanciaMenor;
	}
	
	
	private void comparaConLaDistaciaMenor(double distanciaAEstaCelda){
		//este metodo compara la distancia que recive como parametro con la 
		//variable distanciaMenor y guarda la distancia menor en esa misma variable 
		if (distanciaAEstaCelda<distanciaMenor)distanciaMenor=distanciaAEstaCelda;
	}
	
	    
    //este metodo sobre escibe el de Automata
    public void generaElSiguiente() {
    	for (int i = 1; i<=numeroDeCeldasX; i++){
			for (int j = 1; j<=numeroDeCeldasY; j++){
				reglasDeTranciscion(i,j);
			}
		}
	}
    
    public void reglasDeTranciscion(int i,int j){
    	int sumaCiv, sumaMore, sumaRadio3;
		int eras;
  	
    	Random elMisterioHumano= new Random();
    	if (elMisterioHumano.nextInt(100)>50){
    		eras=valor[i][j][iteracion];
    		
    		//*********************************aqui se puede modificar la forma de tomar en cuenta 
    		//*********************************los vecinos
    		//losVecinos=laSumaPesada(i, j);
    		sumaCiv=laSumaCiv1s(i, j);
    		sumaMore=laSuma(i, j);
    		sumaRadio3=laSumaDe1sRadio3(i, j);
    		
    		//reglas de transicion para cambiar de 0 a 1  
    		
    		if (eras == 0) {
    			if ( dificultad[i][j]>=1 ){
    				valor[i][j][iteracion+1] = 0;
    			}else if( (sumaRadio3 >= vecino_carretera_0) && (dist_vias[i][j] <= buffer_carretera_0) && (sumaCiv <= vecino_0) ){
	    						valor[i][j][iteracion+1] = 1; // dispersion cercana a la carretera
	    	    }else if( (sumaCiv >= vecino_pueblo) && (elMisterioHumano.nextInt(100)>50) && (dist_vias[i][j] <= buffer_carr_pueb)){
    			//}else if( (sumaCiv >= vecino_pueblo) && (dist_vias[i][j] <= buffer_carr_pueb)){
    		    					valor[i][j][iteracion+1] = 1; // difusion cercana al pueblo
		    	}else valor[i][j][iteracion+1] = 0;
//    			reglas de transicion para cambiar de 1 a 2  
    		}
    		if (eras == 1){
    			if ( dificultad[i][j]>=1 ){
    				valor[i][j][iteracion+1] = 1;
    			}else if ( (sumaMore >= vecino_1) && (dist_vias[i][j] <= buffer_carretera_1) ){
    				 valor[i][j][iteracion+1] = 10;// difusion (engorda los 10�s)
    			}else valor[i][j][iteracion+1] = 1;
        	}
    		//reglas de transicion para cambiar de 10 a 100
    		if (eras == 10){
    			if (elMisterioHumano.nextInt(100)>50){
	    			if ( dificultad[i][j]>=1 ){
	    				valor[i][j][iteracion+1] = 10;
	    			}else if (sumaMore >= vecino_2){
	    				valor[i][j][iteracion+1] = 100; // engorda los 100�s
	    			}else valor[i][j][iteracion+1] = 10;
    			}else valor[i][j][iteracion+1] = 10;
    		}
    		//si ya esta en el estado 100 ... permanece
    		if (eras == 100) valor[i][j][iteracion+1] = 100;
    		if (eras == NODATA_value) valor[i][j][iteracion+1] = NODATA_value;
    	}else valor[i][j][iteracion+1] = valor[i][j][iteracion]; //si no cambia se queda igual
    	
    	//generacion espontanea
//    	if ( (elMisterioHumano.nextInt(10000)<2) && (dificultad[i][j]<0.4) ){
//    		if (valor[i][j][iteracion] == 0 ){
//    			valor[i][j][iteracion+1] = 1;
//    		}
//    	} 

    }
    

	public double mide(int[] esteADN){
    	return mide(esteADN[0], esteADN[1], esteADN[2], esteADN[3], 
    			esteADN[4], esteADN[5], esteADN[6], esteADN[7], esteADN[8]);
    	//este metodo usa 12 parametros pero no debe ser dificil programarlo para que 
    	//el numero de parametros lo obtenga del codigoGenetico que aqui se llama infoADN
    	//por otro lado CodigoGenetico debe llamarse de otro modo para que al usar otros 
    	//metodos de calibracion se pueda usar la misma clase que en realidad tiene la 
    	//informacion de la cantidad de parametros y los limites aceptables para cada uno 
    }
	
    public double mide(int vecino_0, int vecino_carretera_0, int vecino_pueblo,
    		int buffer_carretera_0, int buffer_pueblo, int buffer_carr_pueb,
    		int vecino_1, int buffer_carretera_1,
    		int vecino_2){
    	//este metodo usa 12 parametros pero no debe ser dificil programarlo para que 
    	//el numero de parametros lo obtenga del codigoGenetico que aqui se llama infoADN
    	//por otro lado CodigoGenetico debe llamarse de otro modo para que al usar otros 
    	//metodos de calibracion se pueda usar la misma clase que en realidad tiene la 
    	//informacion de la cantidad de parametros y los limites aceptables para cada uno 
    	
    	this.vecino_0=vecino_0;
    	this.vecino_carretera_0=vecino_carretera_0;
    	this.vecino_pueblo=vecino_pueblo;
    	this.buffer_carretera_0=buffer_carretera_0;
    	this.buffer_pueblo=buffer_pueblo;
    	this.buffer_carr_pueb=buffer_carr_pueb;
    	
    	this.vecino_1=vecino_1;
    	//this.vecino_carretera_1=vecino_carretera_1;
    	this.buffer_carretera_1=buffer_carretera_1;
    	
    	this.vecino_2=vecino_2;
//    	this.vecino_carretera_2=vecino_carretera_2;
//    	this.buffer_carretera_2=buffer_carretera_2;
    	iteracion=0;
    	vaciaValores();
    	for(int t=0;t<iteraciones;t++){
			generaElSiguiente();
			iteracion++;
			//System.out.println("vamos en la iteracion " + iteracion);
		}
    	//iteracion--;
    	//System.out.println("intentare sacar la el grid de la iteracion " + iteracion);
    	//System.out.println("con " + numeroDeCeldasX + " columnas y " + numeroDeCeldasY + " renglones");
    	
    	paraEsteGuey = dameLaIteracion(iteracion);
		
		// el medidor queTanto se pregunta cual es la distancia
    	// entre el grid patron y paraEsteGuey
    	return queTanto.esTantitoNormalizado(paraEsteGuey);
    	
    }
    public double mide(int[] esteADN, int veces){
    	double suma = 0.0;
    	int[][] gridSuma= new int[numeroDeCeldasX+1][numeroDeCeldasY+1];
    	gridPromedio= new int[numeroDeCeldasX+1][numeroDeCeldasY+1];
    	for(int i=1;i<=veces;i++){
    		suma += mide(esteADN);
    		
//    		for (int x = 1; x<=numeroDeCeldasX; x++){
//    			for (int y = 1; y<=numeroDeCeldasY; y++){
//    				gridSuma[x][y]+=valor[x][y][iteraciones];
//    			}
//    		}
    	}
    	
//    	for (int x = 1; x<=numeroDeCeldasX; x++){
//			for (int y = 1; y<=numeroDeCeldasY; y++){
//				gridPromedio[x][y]=(int)Math.pow(10,Math.round(Math.log10(  (gridSuma[x][y])/veces ) ) );
//			}
//				
//		}
    	
    	double promedio = suma/(double)veces;
    	return promedio;
    }
	

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
		
	}
	public void setDificultad(File gridDificultadFile){
		System.out.println("cargando dificultad...");
		AsciiGridReader gridDificultadReader = new AsciiGridReader(gridDificultadFile);
		dificultad = new double[numeroDeCeldasX+1][numeroDeCeldasY+1];
		dificultad = gridDificultadReader.getDoubleArray();
		
		gridDificultadReader.close();
	}
	public void setDificultad(String titulo){
		System.out.println("cargando dificultad...");
		//el constructor de AsciiGridReader que resive un string abre un 
		//FileChooser para dejar que el usuario escoja su file .txt
		AsciiGridReader gridDificultadReader = new AsciiGridReader(titulo);
		dificultad = new double[numeroDeCeldasX+1][numeroDeCeldasY+1];
		dificultad = gridDificultadReader.getDoubleArray();
		gridDificultadReader.close();
	}

	public void setMetric(Metrica metrica) {
		// TODO Auto-generated method stub
		queTanto = metrica;
	}

	public int[][] unOutputPara(int[] parametros) {
		this.vecino_0=parametros[0];
    	this.vecino_carretera_0=parametros[1];
    	this.vecino_pueblo=parametros[2];
    	this.buffer_carretera_0=parametros[3];
    	this.buffer_pueblo=parametros[4];
    	this.buffer_carr_pueb=parametros[5];
    	
    	this.vecino_1=parametros[6];
    	this.buffer_carretera_1=parametros[7];
    	this.vecino_2=parametros[8];
    	
		iteracion=0;
    	vaciaValores();
		for(int t=0;t<iteraciones;t++){
			generaElSiguiente();
			iteracion++;
			//System.out.println("vamos en la iteracion " + iteracion);
		}
    	//iteracion--;
    	//System.out.println("intentare sacar la el grid de la iteracion " + iteracion);
    	//System.out.println("con " + numeroDeCeldasX + " columnas y " + numeroDeCeldasY + " renglones");
    	    	
		return dameLaIteracion(iteracion);
		
	}
	
}
