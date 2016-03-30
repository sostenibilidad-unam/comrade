package Calibradores;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import java.util.Arrays;


public class ComoVenimos {
	
	
	public ComoVenimos (File carpeta, int entreCuantos, Path directoryPath){
		
		// TODO Auto-generated method stub
		
	    
		File calibrandoFile = null;
	    
		Path subcarpeta;
		int yArray = -1;
		int xArray = -1;
		
		double [][] arrayComoVenimos = new double[entreCuantos][entreCuantos];
		
		for(int cachoY=entreCuantos;cachoY>=1;cachoY--){
			yArray++;
			xArray = -1;
			for(int cachoX=1;cachoX<=entreCuantos;cachoX++){
				xArray++;
				subcarpeta = Paths.get(directoryPath.toString(),"x"+cachoX+"y"+cachoY);
				//System.out.println(subcarpeta.toString());
				
				calibrandoFile = new File(subcarpeta.toString(), "calibrando.txt");
				
				if (calibrandoFile.exists()) {
				
					String losMejores = elMejorHastaAhora(calibrandoFile);
					String [] parametros = losMejores.split(", ");
					   
					double ajuste = Double.parseDouble(parametros[parametros.length-1]);
					
					//System.out.println(ajuste);
					
					arrayComoVenimos[xArray][yArray] = ajuste;
					
					
					
					
				}else{
					System.out.println("aqui no hay nada");
					arrayComoVenimos[xArray][yArray] = 0;
				}
	        	
	        
	        }
	    }
		
		
	
		
		//ancho = 584607-421407
		//alto = 2297784-2105784
		//ancho del cacho = 32640 ------> en celdas 326
		//alto del cacho = 38400  ------> en celdas 384
		//cells 1632, 1920
		// esto debe hacerse dinamico y al vuelo para que ComoVenimos sea general
		
		
		
		
		double [][] geoArrayComoVenimos = new double [1632][1920];
		
		for(int i=0;i<entreCuantos;i++){
			for(int j=0;j<entreCuantos;j++){
		
				for(int laCeldaX=0;laCeldaX<326;laCeldaX++){
					for(int laCeldaY=0;laCeldaY<384;laCeldaY++){
						geoArrayComoVenimos[326*i+laCeldaX][384*j+laCeldaY] = arrayComoVenimos[i][j];
					}
				}
			}
		}
		
		File esteFile = new File(carpeta.toString(), "comovenimos.txt");
		AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
		unGrid.escribeDouble(geoArrayComoVenimos, 1632, 1920, "421407", "2105784", 100, -9999);
		unGrid.close();
		
		
		
		
		
	}
	
	public String elMejorHastaAhora( File file){
		
		
		BufferedReader peine = null;
		try {
			peine = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String laMejorLinea = null;
		String line;
		double mejorAjuste = 10;
	    try {
			while ((line = peine.readLine()) != null) {
				
			   String [] parametros = line.split(", ");
			   //aqui obtengo el indice
			   double ajuste = Double.parseDouble(parametros[parametros.length-1]); 
			    
			   
			   if (ajuste < mejorAjuste){
				   mejorAjuste = ajuste;
				   laMejorLinea = line;
			   }
			}
			peine.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return laMejorLinea;
	}

	public static void main(String[] args) {
		File directory;
		
		
		int entreCuantos = 0;
		int numeroDeCachos = 0;
		Path directoryPath = null;
		
		if(args.length == 0){
			directory = new File("/Users/fidel/25avos");
			directoryPath = Paths.get("/Users/fidel/25avos");
			String carpetismo = directoryPath.getFileName().toString();
			String cachosPorLadoString = carpetismo.replace("avos", "");
			numeroDeCachos = Integer.parseInt(cachosPorLadoString);
			entreCuantos =(int) (Math.sqrt(numeroDeCachos));
			
		}else{
			directory = new File(args[0]);
			directoryPath = Paths.get(args[0]);
			//args[0]. aqui se saca la raiz cuadrada del numero antes del "avos" y con eso se da valor a entreCuantos
			String carpetismo = directoryPath.getFileName().toString();
			String cachosPorLadoString = carpetismo.replace("avos", "");
			numeroDeCachos = Integer.parseInt(cachosPorLadoString);
			entreCuantos =(int) (Math.sqrt(numeroDeCachos));
			
		}
		
		
		new ComoVenimos(directory,entreCuantos,directoryPath);
		
		
		

	}

}
