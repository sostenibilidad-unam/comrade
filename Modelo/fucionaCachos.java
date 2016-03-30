package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;



public class fucionaCachos {


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
			int[][] arrayCompleto = new int[1632][1920];
			int[][] urbano;
			int entreCuantos = 0;
			Path directoryPath = null;
			
			
			if(args.length == 0){
				directory = new File("/Users/fidel/25avos");
				directoryPath = Paths.get("/Users/fidel/25avos");
				String carpetismo = directoryPath.getFileName().toString();
				String cachosPorLado = carpetismo.replace("avos", "");
				entreCuantos =(int) (Math.sqrt(Double.parseDouble(cachosPorLado)));
				
			}else{
				directory = new File(args[0]);
				directoryPath = Paths.get(args[0]);
				//args[0]. aqui se saca la raiz cuadrada del numero antes del "avos" y con eso se da valor a entreCuantos
				String carpetismo = directoryPath.getFileName().toString();
				String cachosPorLado = carpetismo.replace("avos", "");
				entreCuantos =(int) (Math.sqrt(Double.parseDouble(cachosPorLado)));
				
			}
			
			//directoryPath.getParent();
			Path subcarpeta;
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
		    			
		    			
		    			File urbanoFile = new File(subcarpeta.toString(), "urbano2030.txt");
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
			File esteFile = new File(directoryPath.toString(), "urbano2030.txt");
			AsciiGridWriter unGrid = new AsciiGridWriter(esteFile);
			unGrid.escribeInt(arrayCompleto, 1632, 1920, "421407", "2105784", 100, -9999);
			unGrid.close();
			
			
	}

}
