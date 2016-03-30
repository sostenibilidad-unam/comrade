package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;




public class correParalelo {
	private static class UrbanizaCacho implements Runnable {
    	String carpeta ="";
    	int urbanizingYears = 0;
    	
    	public UrbanizaCacho(String dir, int uYears){
    		carpeta = dir;
    		urbanizingYears = uYears;
    	}
    
    	public void run() {
    		new UrbanizaEsteCacho(carpeta, urbanizingYears, "urb2010.txt", 2010);
        
    	}
    }

	public static void main(String[] args) throws InterruptedException {
		File directory;
		
		int sangriaIzquierda;
		int sangriaDerecha;
		int sagriaArriba = 0;
		int sangriaAbajo = 0;
		int celdasX = 0;
		int celdasY = 0;
		int enCualXvoy;
		int enCualYvoy;
		//ncols         1632
		//nrows         1920
		int[][] arrayCompleto = new int[1632][1925];
		int[][] urbano;
		int entreCuantos = 0;
		int numeroDeCachos = 0;
		Path directoryPath = null;
		int urbanizingYears;
		
		if(args.length == 0){
			directory = new File("/Users/fidel/25avos");
			directoryPath = Paths.get("/Users/fidel/25avos");
			String carpetismo = directoryPath.getFileName().toString();
			String cachosPorLadoString = carpetismo.replace("avos", "");
			numeroDeCachos = Integer.parseInt(cachosPorLadoString);
			entreCuantos =(int) (Math.sqrt(numeroDeCachos));
			
			urbanizingYears = 20;
		}else{
			directory = new File(args[0]);
			directoryPath = Paths.get(args[0]);
			//args[0]. aqui se saca la raiz cuadrada del numero antes del "avos" y con eso se da valor a entreCuantos
			String carpetismo = directoryPath.getFileName().toString();
			String cachosPorLadoString = carpetismo.replace("avos", "");
			numeroDeCachos = Integer.parseInt(cachosPorLadoString);
			entreCuantos =(int) (Math.sqrt(numeroDeCachos));
			
			urbanizingYears = Integer.parseInt(args[1]);
			
		}
		

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    ExecutorService threadPool = Executors.newFixedThreadPool(numeroDeCachos);
	    
	    
	    for (File file : fList) {
	        if (file.isDirectory() && file.getName().startsWith("x")) {
	        	File hayUrbano = new File (file.getAbsolutePath(), "urb2010_ntn");
	        	
	        	
	        	if (hayUrbano.exists()) {
	        		
	        		for(int thisYear=1;thisYear<=urbanizingYears;thisYear++){//cuando quiero escribir en ascii grids todos los años
	        			System.out.println("no hay nadie");	
		        		FileChannel inChannel = null;
						try {
							inChannel = new FileInputStream(new File(file.getAbsolutePath(), "urb2010.txt")).getChannel();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	    FileChannel outChannel = null;
						try {
							//aqui cambier el nombre para que le sume los años
							int year = 2010 + thisYear;
							
							outChannel = new FileOutputStream(new File(file.getAbsolutePath(), "urbano"+year + ".txt")).getChannel();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	    try {
		        	        inChannel.transferTo(0, inChannel.size(), outChannel);
		        	    } catch (IOException e) {
		        	        try {
								throw e;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		        	    } finally {
		        	        if (inChannel != null)
								try {
									inChannel.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        	        if (outChannel != null)
								try {
									outChannel.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		        	    }
		        	    
	        	    }// aqui termina el for de cuando quiero escribir en ascii grids todos los años
	        		
	        	} else {
	        		threadPool.submit(new Thread(new UrbanizaCacho(file.getAbsolutePath(), urbanizingYears)));
	        		
	        		
	        		
	        		
	        		
		        	System.out.println("Nuevo Thread Creado en: "+ file.getAbsolutePath());
	        		
		        }
		        	
	        } 
	    }
	    
	    threadPool.shutdown();
		
	    threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	    
	    System.out.println("ya");
	    
	    Path subcarpeta;
	    for(int thisYear=1;thisYear<=urbanizingYears;thisYear++){//cuando quiero escribir en ascii grids todos los años
	    	
	   
			enCualYvoy = 0;
			for(int cachoY=entreCuantos;cachoY>=1;cachoY--){
				enCualXvoy = 0;
				for(int cachoX=1;cachoX<=entreCuantos;cachoX++){
				
					subcarpeta = Paths.get(directoryPath.toString(),"x"+cachoX+"y"+cachoY);
					System.out.println(subcarpeta.toString());
	
		        	
	
		        
	        	
	        		// aqui ya estoy en una carpeta que contiene los resultados de urbanizacion para un cacho especifico
		        	BufferedReader sangriasFile = null;
					try {
						sangriasFile = new BufferedReader(new FileReader(new File (subcarpeta.toString(), "sangrias.txt")));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			String line = null;
					try {
						line = sangriasFile.readLine();
					
		    			String sangriaIzquierdaString = line.replace("sangriaIzquierda ", "");
		    			
		    			sangriaIzquierda = (int) ( Double.parseDouble(sangriaIzquierdaString) / 100.0) ;
	    		
						line = sangriasFile.readLine();
					
		    			String sangriaDerechaString = line.replace("sangriaDerecha ", "");
		    			sangriaDerecha = (int) ( Double.parseDouble(sangriaDerechaString) / 100.0) ;
	    		
						line = sangriasFile.readLine();
					
		    			String sagriaArribaString = line.replace("sagriaArriba ", "");
		    			sagriaArriba = (int) ( Double.parseDouble(sagriaArribaString) / 100.0) ;
	    			
	    			
						line = sangriasFile.readLine();
					
		    			String sangriaAbajoString = line.replace("sangriaAbajo ", "");
		    			sangriaAbajo = (int) ( Double.parseDouble(sangriaAbajoString) / 100.0) ;
		    			
		    			System.out.println("sangriaIzquierda "+sangriaIzquierda); 
		    			System.out.println("sangriaDerecha "+sangriaDerecha); 
		    			System.out.println("sagriaArriba "+sagriaArriba); 
		    			System.out.println("sangriaAbajo "+sangriaAbajo); 
		    			
		    			
		    			int year = 2010 + thisYear;
		    			
		    			File urbanoFile = new File(subcarpeta.toString(), "urbano"+year + ".txt");
		    			//File urbanoFile = new File(subcarpeta.toString(), "bosques.txt");
		    			AsciiGridReader urbanoReader = new AsciiGridReader(urbanoFile);
		    			urbano = urbanoReader.getIntArray();
		    			urbanoReader.close();
		    			//z.length and z[0].length
		    			celdasX = urbano.length;
		    			celdasY = urbano[0].length;
		    			System.out.println("celdasX "+ celdasX);
		    			System.out.println("celdasY "+ celdasY);
		    			int xTotal = -1;
		    			int yTotal = -1;
		    			for(int x=sangriaIzquierda;x<celdasX-(sangriaDerecha);x++){
		    				xTotal++;
		    				yTotal = -1;
		    				for(int y=sagriaArriba;y<celdasY-(sangriaAbajo);y++){
		    					yTotal++;
		    					//System.out.println(x+" "+y);
		    					arrayCompleto[enCualXvoy+xTotal][enCualYvoy+yTotal] = urbano[x][y];
		    				}
		    			}
		    			
		    			enCualXvoy = enCualXvoy + celdasX - (sangriaDerecha + sangriaIzquierda );
		    			
	    			} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        
			        	
		        } //for cachoX
				enCualYvoy = enCualYvoy + celdasY - (sagriaArriba + sangriaAbajo );
				
		    }//for cachoY
		    //t[1632+20][1920+20]
			
			int year = 2010 + thisYear;
			File esteFile = new File(directoryPath.toString(), "urbano"+year + ".txt");
			AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
			unGrid.escribeInt(arrayCompleto, 1632, 1920, "421407", "2105784", 100, -9999);
			unGrid.close();
		
	    }// aqui termina el for de cuando quiero escribir en ascii grids todos los años
	}

}



