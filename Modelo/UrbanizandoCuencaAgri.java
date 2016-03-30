package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Calibradores.CodigoGenetico;
import Metricas.Metrica;
import Metricas.MetricaDeAreaConClustersBinaria;
import Metricas.MultiescalaConBordeBinaria;

public class UrbanizandoCuencaAgri extends Automata {
	public CodigoGenetico infoADN;
	// estos son los parametros del automatas
	// 	para cambiar del estado 0 al 1
	int privacidad;//
    int gregariedad;
    int difusion1;
    int freno_pendiente;
	int freno_bosque;
	int freno_no_calle;
	int freno_agricultura;
	int freno_zona_federal;
    
	
	
	
	
//	public int numeroDeRenglones;
//	public int numeroDeColumnas;
	double cellsize;
	String xllcorner;
	String yllcorner;
	int NODATA_value;
	
		
    
	int lasIteracionesDeseadas=0; //lasIteracionesDeseadas son los a�os de prueba
								
	//double wagri = .8; // peso agricultura-bosque
	double wpend = .99; // peso pendiente
	
	public double distanciaMenor;
	
	public int x0;
	public int y0;
	
	//public MetricaDeAreaConClustersBinaria queTanto;
	public MultiescalaConBordeBinaria queTanto;
	double[][] dist_vias;
	int [][] paraEsteGuey;
	public double[][] pendiente;
	public double[][] dificultadcentros;
	
	public int[][] bosque;
	public int[][] agricultura;
	public int[][] zona_federal;
	
	public int iteraciones;
	public int[][] gridMeta;
	public int[][] gridInicial;
	public int[][] gridPromedio;
	private double dificultadPendiente;
	private double dificultadBosque;
	double necedad;
	private double dificultadPorFaltaDeCalle;
	private double dificultadPorAgricultura;
	private double dificultadPorZonaFederal;
	
	public String[] nombresDeLosParametros; 
	
	
	
	
	public UrbanizandoCuencaAgri(int numeroDeCeldasX, int numeroDeCeldasY, int numeroMaximoDeIteraciones, int estadosPosibles) {
		super(numeroDeCeldasX, numeroDeCeldasY, numeroMaximoDeIteraciones,
				estadosPosibles);
		//este es el constructor que hereda de Automata
		dist_vias = new double[numeroDeCeldasX][numeroDeCeldasY];
		pendiente = new double[numeroDeCeldasX][numeroDeCeldasY];
		paraEsteGuey = new int[numeroDeCeldasX][numeroDeCeldasY];
		
		iteracion=0;
		System.out.println("creando CA...");
		nombresDeLosParametros = new String[8];
		nombresDeLosParametros[0]="privacidad";
		nombresDeLosParametros[1]="gregariedad";
		nombresDeLosParametros[2]="difusion1";
		
		nombresDeLosParametros[3]="freno_no_calle";
		nombresDeLosParametros[4]="freno_pendiente";
		nombresDeLosParametros[5]="freno_bosque";
		nombresDeLosParametros[6]="freno_agricultura";
		nombresDeLosParametros[7]="freno_zona_federal";
		
		infoADN = new CodigoGenetico(8);
		infoADN.setRango(0, 2, 5);
		infoADN.setRango(1, 2, 300);
		infoADN.setRango(2, 5, 22);
		
		infoADN.setRango(3, 10, 100);
		infoADN.setRango(4, 10, 100);
		infoADN.setRango(5, 10, 100);
		infoADN.setRango(6, 10, 100);
		infoADN.setRango(7, 10, 100);
		
		
	}
   
	public void quieroCrecer(int timeSteps) {
		// TODO Auto-generated method stub
		for(int t=1;t<timeSteps;t++){
			iteracion++;
			System.out.println("intentando generar.........generacion "+ iteracion);
			generaElSiguiente();
			
			escribeGrid(iteracion);
		}
	}
	public void quieroCrecer(int timeSteps, String carpeta, int initialYear) {
		// TODO Auto-generated method stub
		//iteracion = 0;
		int year = 0;
		for(int t=1;t<=timeSteps;t++){
			year = iteracion + 1;
			System.out.println("urbanizando año "+ year);
			generaElSiguiente();
			iteracion++;
			//escribeGrid(iteracion, carpeta);
		}
		escribeGrid(iteracion, carpeta, initialYear);
	}
	public void quieroCrecer(int timeSteps, String carpeta, int initialYear, int testNumber) {
		// TODO Auto-generated method stub
		//iteracion = 0;
		int year = 0;
		for(int t=1;t<=timeSteps;t++){
			year = iteracion + 1;
			System.out.println("urbanizando año "+ year);
			generaElSiguiente();
			iteracion++;
			//escribeGrid(iteracion, carpeta);
		}
		escribeGrid(iteracion, carpeta, initialYear, testNumber);
	}
	public void quieroCrecerEscribiendo(int timeSteps, String carpeta, int initialYear) {
		// TODO Auto-generated method stub
		//iteracion = 0;
		int year = 0;
		for(int t=1;t<=timeSteps;t++){
			year = iteracion + 1;
			System.out.println("urbanizando año "+ year);
			generaElSiguiente();
			iteracion++;
			escribeGrid(iteracion, carpeta, initialYear);
		}
		escribeGrid(iteracion, carpeta, initialYear);
	}
	public int[][] dameLaIteracion(int i){
		int[][] laIteracion=new int[numeroDeCeldasX][numeroDeCeldasY];
		for (int k=0;k<numeroDeCeldasX;k++){
			for(int l=0;l<numeroDeCeldasY;l++){
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
	public void setDificultadCentros(){
		System.out.println("cargando dificultad por lejania a las centralidades...");
		AsciiGridReader gridDificultadCentros = new AsciiGridReader("escoge dificultad por lejania a las centralidades");
		dificultadcentros = gridDificultadCentros.getDoubleArray();
		gridDificultadCentros.close();
	}
	public void setDificultadCentros(File dificultadCentrosFile){
		System.out.println("cargando dificultad por lejania a las centralidades...");
		AsciiGridReader gridDificultadCentros = new AsciiGridReader(dificultadCentrosFile);
		dificultadcentros = gridDificultadCentros.getDoubleArray();
		System.out.println(dificultadcentros.length);
		gridDificultadCentros.close();
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
	
	public void setAgricultura(File agriculturaFile){
		System.out.println("cargando agricultura...");
		AsciiGridReader gridAgricultura = new AsciiGridReader(agriculturaFile);
		agricultura = gridAgricultura.getIntArray();
		gridAgricultura.close();
	}
	public void setFederal(File federalFile){
		System.out.println("cargando zonas federales...");
		AsciiGridReader gridFederal = new AsciiGridReader(federalFile);
		zona_federal = gridFederal.getIntArray();
		gridFederal.close();
	}
	
	
	public void setInitialGrid(File gridInicialFile){
		//los metodos setInitialGrid toman un grid (array de 2 dimanciones)
		//y acomodan sus valores en el plano inicial de un array de 3 dimenciones 
		System.out.println("cargando grid inicial...");
		AsciiGridReader gridInicialReader = new AsciiGridReader(gridInicialFile);
		gridInicial = gridInicialReader.getIntArray();
		for (int k=0;k<numeroDeCeldasX;k++){
			for(int l=0;l<numeroDeCeldasY;l++){
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
		gridInicial = gridInicialReader.getIntArray();
		for (int k=0;k<numeroDeCeldasX;k++){
			for(int l=0;l<numeroDeCeldasY;l++){
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
		for(int i=0; i<numeroDeCeldasX;i++){
			for(int j=0; j<numeroDeCeldasY; j++){
				elNuevoGrid[i][j]=valor[i][j][estaGeneracion];
			}
		}
		unGrid.escribeInt(elNuevoGrid, numeroDeCeldasX, numeroDeCeldasY, xllcorner, yllcorner, cellsize, NODATA_value);
		unGrid.close();
	}
	public void escribeGrid(int estaGeneracion, String carpeta, int initialYear) {
		int year = initialYear + estaGeneracion;
		File esteFile = new File(carpeta, "urbano"+year + ".txt");
		AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
		int[][] elNuevoGrid = new int[numeroDeCeldasX][numeroDeCeldasY];
		for(int i=0; i<numeroDeCeldasX;i++){
			for(int j=0; j<numeroDeCeldasY; j++){
				elNuevoGrid[i][j]=valor[i][j][estaGeneracion];
			}
		}
		unGrid.escribeInt(elNuevoGrid, numeroDeCeldasX, numeroDeCeldasY, xllcorner, yllcorner, cellsize, NODATA_value);
		unGrid.close();
	}
	public void escribeGrid(int estaGeneracion, String carpeta, int initialYear, int testNumber) {
		int year = initialYear + estaGeneracion;
		File esteFile = new File(carpeta, "urbano"+year + "_"+testNumber+".txt");
		AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
		int[][] elNuevoGrid = new int[numeroDeCeldasX][numeroDeCeldasY];
		for(int i=0; i<numeroDeCeldasX;i++){
			for(int j=0; j<numeroDeCeldasY; j++){
				elNuevoGrid[i][j]=valor[i][j][estaGeneracion];
			}
		}
		unGrid.escribeInt(elNuevoGrid, numeroDeCeldasX, numeroDeCeldasY, xllcorner, yllcorner, cellsize, NODATA_value);
		unGrid.close();
	}
	public void escribeGrid(File aquiMero) {
		AsciiGridWriter unGrid = new AsciiGridWriter(aquiMero);
		int[][] elNuevoGrid = new int[numeroDeCeldasX][numeroDeCeldasY];
		for(int i=0; i<numeroDeCeldasX;i++){
			for(int j=0; j<numeroDeCeldasY; j++){
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
    	
    	
    	for (int i = 0; i<numeroDeCeldasX; i++){
			for (int j = 0; j<numeroDeCeldasY; j++){
				reglasDeTranciscion(i,j);
			}
		}
	}
    
    public void reglasDeTranciscion(int i,int j){
    	int sumaCiv, sumaMore, sumaRadio3, sumaRadio10;
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
    		sumaRadio10 = laSumaDe1sRadioN(i, j, 10);
    		
    		
    		dificultadPendiente= 100.0 - 100.0 * Math.exp(-((double)freno_pendiente)*pendiente[i][j]*0.0046);
			dificultadBosque=((double)freno_bosque)*bosque[i][j];
			
			dificultadPorAgricultura=((double)freno_agricultura)*agricultura[i][j];
			dificultadPorZonaFederal=((double)freno_zona_federal)*zona_federal[i][j];
			
			
			dificultadPorFaltaDeCalle = 100.0 - (100.0 * Math.exp(-((double)freno_no_calle)*dist_vias[i][j]*0.000345));
			necedad = (100.0 * elMisterioHumano.nextDouble() ) - dificultadcentros[i][j];
			if (necedad < 0){
				necedad = 0;
			}
			//reglas de transicion para cambiar de 0 a 1  
    		if (eras == 0) {
    			if ( (necedad<dificultadPendiente) || (necedad < dificultadBosque) || (necedad <dificultadPorFaltaDeCalle) || (necedad <dificultadPorZonaFederal)|| (necedad <dificultadPorAgricultura) ){
    				valor[i][j][iteracion+1] = 0;
    			}else{
    				valor[i][j][iteracion+1] = 0;
    			
    				if( (sumaRadio10 >= gregariedad) && (sumaMore <= privacidad) ){
    			
    					valor[i][j][iteracion+1] = 1; // dispersion cercana a la carretera
    				}
	    		    
    				
    			}
    			if ( (necedad<dificultadPendiente) || (necedad < dificultadBosque) || (necedad <dificultadPorZonaFederal)|| (necedad <dificultadPorAgricultura) ){
    				valor[i][j][iteracion+1] = 0;
    			}else{
	    			if( (sumaRadio3 >= difusion1) ){
	    		    	valor[i][j][iteracion+1] = 1; // difusion cercana al pueblo
	    		    }
    			}

    		}
    		if (eras == 1) {
    			valor[i][j][iteracion+1] = 1; // si ya eres urbano asi te quedas
    		}
    		
    		
    		if (eras == NODATA_value) valor[i][j][iteracion+1] = NODATA_value;

    	
    	//generacion espontanea
    		
    	if ( (elMisterioHumano.nextInt(1000)<2) && (dificultadPorFaltaDeCalle<20) && (dificultadPendiente<85) && (dificultadBosque<85) && (dificultadPorZonaFederal<85) && (dificultadPorAgricultura<85) && (dificultadcentros[i][j]<15)){
    		if (valor[i][j][iteracion] == 0 ){
    			
    			for (int dx=-4; dx<=4; dx++)
    			    for (int dy=-4; dy<=4; dy++){
    			    	if (i+dx>=0 && i+dx<numeroDeCeldasX && j+dy>=0 && j+dy<numeroDeCeldasY && (((dx)*(dx))+((dy)*(dy))<12))
    			    		valor[i+dx][j+dy][iteracion+1] = 1;
    			    }
    			    	
    			
    		}
    	} 

    }
    

	public double mide(int[] esteADN){
    	return mide(esteADN[0], esteADN[1], esteADN[2], esteADN[3], 
    			esteADN[4], esteADN[5],esteADN[6], esteADN[7]);
    	//este metodo usa 12 parametros pero no debe ser dificil programarlo para que 
    	//el numero de parametros lo obtenga del codigoGenetico que aqui se llama infoADN
    	//por otro lado CodigoGenetico debe llamarse de otro modo para que al usar otros 
    	//metodos de calibracion se pueda usar la misma clase que en realidad tiene la 
    	//informacion de la cantidad de parametros y los limites aceptables para cada uno 
    }
	
    public double mide(int privacidad, int gregariedad, int difusion1,
    		int freno_no_calle, int freno_pendiente, 
    		int freno_bosque, int freno_agricultura, int freno_zona_federal){
    	//este metodo usa 12 parametros pero no debe ser dificil programarlo para que 
    	//el numero de parametros lo obtenga del codigoGenetico que aqui se llama infoADN
    	//por otro lado CodigoGenetico debe llamarse de otro modo para que al usar otros 
    	//metodos de calibracion se pueda usar la misma clase que en realidad tiene la 
    	//informacion de la cantidad de parametros y los limites aceptables para cada uno 
    	
    	this.privacidad=privacidad;
    	this.gregariedad=gregariedad;
    	this.difusion1=difusion1;
 
    	this.freno_no_calle=freno_no_calle;
    	this.freno_pendiente = freno_pendiente;
    	this.freno_bosque = freno_bosque;
    	this.freno_agricultura = freno_agricultura;
    	this.freno_zona_federal = freno_zona_federal;
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
    	return queTanto.esTantito(paraEsteGuey);
    	
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
	
	//public void setMetric(MetricaDeAreaConClustersBinaria metrica) {
	public void setMetric(MultiescalaConBordeBinaria metrica) {
		// TODO Auto-generated method stub
		queTanto = metrica;
	}

	public int[][] unOutputPara(int[] parametros) {
		this.privacidad=parametros[0];
    	this.gregariedad=parametros[1];
    	this.difusion1=parametros[2];
    	
   
    	
    	this.freno_no_calle=parametros[3];
    	this.freno_pendiente = parametros[4];
    	this.freno_bosque = parametros[5];
    	this.freno_agricultura = parametros[6];
    	this.freno_zona_federal = parametros[7];
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
	public static void main(String args[]){
		
		String[] nombresDeLosParametros;
		UrbanizandoCuencaAgri CA;
		BufferedReader bosquesFile;
		int ncols = 0;
		int nrows = 0;
		
		//new UrbanizaEsteCacho("/Users/fidel/25avos/x2y2", 10, "urb2000.txt", 2000);
		
		String carpeta = "/Users/fidel/OneDrive/workspaceJava/ExperimentosMEGADAPT/entero";
		
		try {
			bosquesFile = new BufferedReader(new FileReader(new File (carpeta, "bosques.txt")));
			String line = bosquesFile.readLine();
			String ncolsString = line.replace("ncols", "");
			ncolsString = ncolsString.replace(" ", "");
			ncols = Integer.parseInt(ncolsString);
			
			
			line = bosquesFile.readLine();
			String nrowsString = line.replace("nrows", "");
			nrowsString = nrowsString.replace(" ", "");
			nrows = Integer.parseInt(nrowsString);
			
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		CA = new UrbanizandoCuencaAgri(ncols, nrows, 22, 2);
		nombresDeLosParametros=CA.nombresDeLosParametros;
		
		
		//acoplaSliders();
		
		//comparador = new ComparaIndividuos();
		
		
		
		
		CA.setInitialGrid(new File (carpeta, "urb2000.txt"));
		
		CA.setDistVias(new File (carpeta, "dist_vias.txt"));
		//CA.setGoalGrid(new File (carpeta, "urban_2010.txt"));
		CA.setPendiente(new File (carpeta, "pendiente.txt"));
		CA.setBosque(new File (carpeta, "bosques2000.txt"));
		CA.setAgricultura(new File (carpeta, "agrop2000.txt"));
		CA.setFederal(new File (carpeta, "zona_federal.txt"));
		CA.setDificultadCentros(new File (carpeta, "dificultadcentros.txt"));
		
		
		
//		CA.privacidad = 2;//
//	    CA.gregariedad= 100;
//	    CA.difusion1= 100;
//	    CA.freno_pendiente= 50;
//	    CA.freno_bosque= 50;
//	    CA.freno_no_calle= 50;
//	    CA.freno_agricultura= 50;
//	    CA.freno_zona_federal= 50;
		
		//esto se debe sacar de los archivos de texto que genera el calibrador pues deben ser los valores para los parametros que mejor se acoplan 
		//4, 15, 18, 96, 83, 70, 83, 83
		//int [] parametros = {4, 15, 18, 96, 83, 70, 83, 83};
		//int [] parametros = {2, 3, 22, 100, 10, 97, 36, 29};
		//int [] parametros = {2, 3, 22, 100, 35, 65, 65, 76};
		//int [] parametros = {5, 6, 5, 10, 29, 59, 96, 54};
		
		int [] parametros = {4, 15, 18, 96, 83, 70, 83, 83};
		CA.privacidad = parametros[0];//
	    CA.gregariedad= parametros[1];
	    CA.difusion1= parametros[2];
	    CA.freno_pendiente= parametros[3];
	    CA.freno_bosque= parametros[4];
	    CA.freno_no_calle= parametros[5];
	    CA.freno_agricultura= parametros[6];
	    CA.freno_zona_federal= parametros[7];		
		
		int years=10;
		
		int initialYear = 2000;
		
		CA.setIteraciones(years);
		
		CA.quieroCrecer(years, carpeta, initialYear);
		
		
	}
	
}

