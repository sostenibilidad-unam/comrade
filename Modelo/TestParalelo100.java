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




public class TestParalelo100 {
	private static class UrbanizaCacho implements Runnable {
		String carpeta ="";
    	int urbanizingYears = 0;
    	
    	public UrbanizaCacho(String dir, int uYears){
    		carpeta = dir;
    		urbanizingYears = uYears;
    	}
    
    	public void run() {
    		new UrbanizaEsteCacho100(carpeta, urbanizingYears, "urb2000.txt", 2000);
        
    	}
    }

	public static void main(String[] args) throws InterruptedException {
		File directory;
		
		int numeroDeCachos = 0;
		Path directoryPath = null;
		int urbanizingYears;
		
		if(args.length == 0){
			directory = new File("/Users/fidel/25avos");
			directoryPath = Paths.get("/Users/fidel/25avos");
			String carpetismo = directoryPath.getFileName().toString();
			String cachosPorLadoString = carpetismo.replace("avos", "");
			numeroDeCachos = Integer.parseInt(cachosPorLadoString);
			urbanizingYears = 10;
		}else{
			directory = new File(args[0]);
			directoryPath = Paths.get(args[0]);
			//args[0]. aqui se saca la raiz cuadrada del numero antes del "avos" y con eso se da valor a entreCuantos
			String carpetismo = directoryPath.getFileName().toString();
			String cachosPorLadoString = carpetismo.replace("avos", "");
			numeroDeCachos = Integer.parseInt(cachosPorLadoString);
			urbanizingYears = Integer.parseInt(args[1]);
			
		}
		

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    ExecutorService threadPool = Executors.newFixedThreadPool(numeroDeCachos);
	    
	    
	    for (File file : fList) {
	        if (file.isDirectory() && file.getName().startsWith("x")) {
	        	File hayUrbano = new File (file.getAbsolutePath(), "urb2000_ntn");
	        	
	        
	        	if (hayUrbano.exists()) {
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
						int year = 2000 + urbanizingYears;
						
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
	        		
	        	} else {
	        		threadPool.submit(new Thread(new UrbanizaCacho(file.getAbsolutePath(), urbanizingYears)));
	        		
	        		
	        		
	        		
	        		
		        	System.out.println("Nuevo Thread Creado en: "+ file.getAbsolutePath());
	        		
		        }
		        	
	        } 
	    }
	    
	    threadPool.shutdown();
		
	    threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	    
	    System.out.println("ya");
	    
	    
	  
	}

	}



