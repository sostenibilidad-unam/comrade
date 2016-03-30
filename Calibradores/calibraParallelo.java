package Calibradores;

import java.io.File;


public class calibraParallelo {
	


	
    private static class CalibraCacho implements Runnable {
    	String carpeta ="";
    	
    	public CalibraCacho(String dir){
    		carpeta = dir;
    	}
    
    	public void run() {
    		EvolucionanCuencaAgri gente = new EvolucionanCuencaAgri(carpeta);
			gente.empiezen();
        
    	}
    }

public static void main(String args[])
    throws InterruptedException {
	File directory;
	if(args.length == 0){
		directory = new File("/Users/fidel/121avos");
	}else{
		directory = new File(args[0]);
	}
	

    // get all the files from a directory
    File[] fList = directory.listFiles();
    for (File file : fList) {
        if (file.isDirectory()) {
        	File hayUrbano = new File (file.getAbsolutePath(), "urb2010_ntn");
        	
        
        	if (hayUrbano.exists()) {
        		System.out.println("no hay nadie");	
        	} else {
        		new Thread(new CalibraCacho(file.getAbsolutePath())).start();
	        	System.out.println("Nuevo Thread Creado en: "+ file.getAbsolutePath());
        		
	        }
	        	
        } 
    }
    
    
	
	
  
}

}
