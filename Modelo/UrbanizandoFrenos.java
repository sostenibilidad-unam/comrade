package Modelo;

import java.io.File;
import java.util.Random;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Calibradores.CodigoGenetico;
import Metricas.Metrica;

public class UrbanizandoFrenos extends Automata {
	public CodigoGenetico infoADN;
	// estos son los parametros del automatas
	// 	para cambiar del estado 0 al 1
	int privacidad;//
    int gregariedad;
    int difusion1;
    int freno_pendiente;
	int freno_bosque;
	int freno_no_calle;
	
    //	 para cambiar del estado 1 al 10
	int difusion10;
		

	//	para cambiar del estado 10 al 100
	int difusion100; 
	
	
	
	
	
//	public int numeroDeRenglones;
//	public int numeroDeColumnas;
	double cellsize;
	String xllcorner;
	String yllcorner;
	int NODATA_value;
	
		
    
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
	public int[][] bosque;
	
	public int iteraciones;
	public int[][] gridMeta;
	public int[][] gridPromedio;
	private double dificultadPendiente;
	private double dificultadBosque;
	double necedad;
	private double dificultadPorFaltaDeCalle;
	public String[] nombresDeLosParametros; 
	
	
	
	
	public UrbanizandoFrenos(int numeroDeCeldasX, int numeroDeCeldasY, int numeroMaximoDeIteraciones, int estadosPosibles) {
		super(numeroDeCeldasX, numeroDeCeldasY, numeroMaximoDeIteraciones,
				estadosPosibles);
		//este es el constructor que hereda de Automata
		dist_vias = new double[numeroDeCeldasX+1][numeroDeCeldasY+1];
		pendiente = new double[numeroDeCeldasX+1][numeroDeCeldasY+1];
		paraEsteGuey = new int[numeroDeCeldasX+1][numeroDeCeldasY+1];
		
		iteracion=0;
		System.out.println("creando CA...");
		nombresDeLosParametros = new String[8];
		nombresDeLosParametros[0]="privacidad";
		nombresDeLosParametros[1]="gregariedad";
		nombresDeLosParametros[2]="difusion1";
		nombresDeLosParametros[3]="difusion10";
		nombresDeLosParametros[4]="difusion100";
		nombresDeLosParametros[5]="freno_no_calle";
		nombresDeLosParametros[6]="freno_pendiente";
		nombresDeLosParametros[7]="freno_bosque";
		
		infoADN = new CodigoGenetico(8);
		infoADN.setRango(0, 2, 5);
		infoADN.setRango(1, 2, 22);
		infoADN.setRango(2, 5, 22);
		infoADN.setRango(3, 20, 80);
		infoADN.setRango(4, 200, 800);
		infoADN.setRango(5, 10, 100);
		infoADN.setRango(6, 10, 100);
		infoADN.setRango(7, 10, 100);
		
		
		
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
	public void setBosque(File bosqueFile){
		AsciiGridReader bosqueReader = new AsciiGridReader(bosqueFile);
		bosque = bosqueReader.getIntArray();
		bosqueReader.close();
		
		
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
    	//if (elMisterioHumano.nextInt(100)>50){
    		eras=valor[i][j][iteracion];
    		
    		//*********************************aqui se puede modificar la forma de tomar en cuenta 
    		//*********************************los vecinos
    		//losVecinos=laSumaPesada(i, j);
    		sumaCiv=laSumaCiv1s(i, j);
    		sumaMore=laSuma(i, j);
    		sumaRadio3=laSumaDe1sRadio3(i, j);
    		
    		
    		
    		dificultadPendiente= 100.0 - 100.0 * Math.exp(-((double)freno_pendiente)*pendiente[i][j]*0.0046);
			dificultadBosque=((double)freno_bosque)*bosque[i][j];
			dificultadPorFaltaDeCalle = 100.0 - (100.0 * Math.exp(-((double)freno_no_calle)*dist_vias[i][j]*0.000345));
			necedad = 100.0 * elMisterioHumano.nextDouble();
			
			//reglas de transicion para cambiar de 0 a 1  
    		if (eras == 0) {
    			if ( (necedad<dificultadPendiente) || (necedad < dificultadBosque) || necedad <dificultadPorFaltaDeCalle){
    				valor[i][j][iteracion+1] = 0;
    			}else if( (sumaRadio3 >= gregariedad) && (sumaMore <= privacidad) ){
	    			valor[i][j][iteracion+1] = 1; // dispersion cercana a la carretera
	    		}else if( (sumaRadio3 >= difusion1) ){
    				valor[i][j][iteracion+1] = 1; // difusion cercana al pueblo
    		   	}else {
    				valor[i][j][iteracion+1] = 0;
    			}
//    			reglas de transicion para cambiar de 1 a 2  
    		}
    		if (eras == 1){
    			if ( (necedad<dificultadPendiente) || (necedad < dificultadBosque)){
    				valor[i][j][iteracion+1] = 1;
    			}else if (sumaMore >= difusion10){
    				 valor[i][j][iteracion+1] = 10;// difusion (engorda los 10s)
    			}else valor[i][j][iteracion+1] = 1;
        	}
    		//reglas de transicion para cambiar de 10 a 100
    		if (eras == 10){
    			if ( (necedad < dificultadPendiente) || (necedad < dificultadBosque)){
	    			valor[i][j][iteracion+1] = 10;
	    		}else{
	    			if (sumaMore >= difusion100){
	    				valor[i][j][iteracion+1] = 100; // engorda los 100s
	    			}else valor[i][j][iteracion+1] = 10;
	    		}
    		}
    		//si ya esta en el estado 100 ... permanece
    		if (eras == 100) valor[i][j][iteracion+1] = 100;
    		if (eras == NODATA_value) valor[i][j][iteracion+1] = NODATA_value;
    	//}else valor[i][j][iteracion+1] = valor[i][j][iteracion]; //si no cambia se queda igual
    	
    	//generacion espontanea
//    	if ( (elMisterioHumano.nextInt(10000)<2) && (dificultad[i][j]<0.4) ){
//    		if (valor[i][j][iteracion] == 0 ){
//    			valor[i][j][iteracion+1] = 1;
//    		}
//    	} 

    }
    

	public double mide(int[] esteADN){
    	return mide(esteADN[0], esteADN[1], esteADN[2], esteADN[3], 
    			esteADN[4], esteADN[5], esteADN[6], esteADN[7]);
    	//este metodo usa 12 parametros pero no debe ser dificil programarlo para que 
    	//el numero de parametros lo obtenga del codigoGenetico que aqui se llama infoADN
    	//por otro lado CodigoGenetico debe llamarse de otro modo para que al usar otros 
    	//metodos de calibracion se pueda usar la misma clase que en realidad tiene la 
    	//informacion de la cantidad de parametros y los limites aceptables para cada uno 
    }
	
    public double mide(int privacidad, int gregariedad, int difusion1,
    		int difusion10, int difusion100, int freno_no_calle, int freno_pendiente, 
    		int freno_bosque){
    	//este metodo usa 12 parametros pero no debe ser dificil programarlo para que 
    	//el numero de parametros lo obtenga del codigoGenetico que aqui se llama infoADN
    	//por otro lado CodigoGenetico debe llamarse de otro modo para que al usar otros 
    	//metodos de calibracion se pueda usar la misma clase que en realidad tiene la 
    	//informacion de la cantidad de parametros y los limites aceptables para cada uno 
    	
    	this.privacidad=privacidad;
    	this.gregariedad=gregariedad;
    	this.difusion1=difusion1;
    	   	
    	this.difusion10=difusion10;
    	this.difusion100=difusion100;
    	this.freno_no_calle=freno_no_calle;
    	this.freno_pendiente = freno_pendiente;
    	this.freno_bosque = freno_bosque;
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
    	for(int i=1;i<=veces;i++){
    		suma += mide(esteADN);
    	}
    	
    	double promedio = suma/(double)veces;
    	return promedio;
    }
	

	public void setIteraciones(int iteraciones) {
		this.iteraciones = iteraciones;
		
	}
	
	public void setMetric(Metrica metrica) {
		// TODO Auto-generated method stub
		queTanto = metrica;
	}

	public int[][] unOutputPara(int[] parametros) {
		this.privacidad=parametros[0];
    	this.gregariedad=parametros[1];
    	this.difusion1=parametros[2];
    	
    	this.difusion10=parametros[3];
    	this.difusion100=parametros[4];
    	
    	this.freno_no_calle=parametros[5];
    	this.freno_pendiente = parametros[6];
    	this.freno_bosque = parametros[7];
    	
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

