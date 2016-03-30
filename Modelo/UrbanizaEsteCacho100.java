package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;




public class UrbanizaEsteCacho100{
	
	public String[] nombresDeLosParametros;
	private UrbanizandoCuencaAgri CA;
	BufferedReader bosquesFile;
	int ncols = 0;
	int nrows = 0;
	String [] parametros;
	/**
	 * @param args
	 */
	public UrbanizaEsteCacho100(String carpeta, int years, String gridInicial, int initialYear) {
	//leer las celdas del raster de bosques
	
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
		
		BufferedReader peine = null;
		try {
			peine = new BufferedReader(new FileReader(new File (carpeta, "calibrando.txt")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String line;
		String [] parametros;
		
	    try {
			for (int testNumber=1;testNumber<100;testNumber++){
				line = peine.readLine();
				parametros = line.split(", ");
				
				CA = new UrbanizandoCuencaAgri(ncols, nrows, 22, 2);
				nombresDeLosParametros=CA.nombresDeLosParametros;
				
				
				//acoplaSliders();
				
				//comparador = new ComparaIndividuos();
				
				
				
				
				CA.setInitialGrid(new File (carpeta, gridInicial));
				
				CA.setDistVias(new File (carpeta, "dist_vias.txt"));
				//CA.setGoalGrid(new File (carpeta, "urban_2010.txt"));
				CA.setPendiente(new File (carpeta, "pendiente.txt"));
				CA.setBosque(new File (carpeta, "bosques2000.txt"));
				CA.setAgricultura(new File (carpeta, "agrop2000.txt"));
				CA.setFederal(new File (carpeta, "zona_federal.txt"));
				CA.setDificultadCentros(new File (carpeta, "dificultadcentros.txt"));
				
				
				//esto se debe sacar de los archivos de texto que genera el calibrador pues deben ser los valores para los parametros que mejor se acoplan 
				
				CA.privacidad = Integer.parseInt(parametros[0]);//
			    CA.gregariedad= Integer.parseInt(parametros[1]);
			    CA.difusion1= Integer.parseInt(parametros[2]);
			    CA.freno_pendiente= Integer.parseInt(parametros[3]);
			    CA.freno_bosque= Integer.parseInt(parametros[4]);
			    CA.freno_no_calle= Integer.parseInt(parametros[5]);
			    CA.freno_agricultura= Integer.parseInt(parametros[6]);
			    CA.freno_zona_federal= Integer.parseInt(parametros[7]);
				
//				CA.privacidad = 2;//
//			    CA.gregariedad= 100;
//			    CA.difusion1= 100;
//			    CA.freno_pendiente= 50;
//			    CA.freno_bosque= 50;
//			    CA.freno_no_calle= 50;
//			    CA.freno_agricultura= 50;
//			    CA.freno_zona_federal= 50;
				
				
				
				
				
				
				
				CA.setIteraciones(years);
				
				//CA.quieroCrecer(years, carpeta, initialYear);
				CA.quieroCrecer(years, carpeta, initialYear, testNumber);
			
			}
			
				
			   
			   
			   
			   
			
			peine.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
		
		

	}
	public UrbanizaEsteCacho100(String carpeta, int years, String gridInicial, int initialYear, int[] parametros) {
	//leer las celdas del raster de bosques
	//para poner parametros a mano
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
		
		
		
		
		CA.setInitialGrid(new File (carpeta, gridInicial));
		
		CA.setDistVias(new File (carpeta, "dist_vias.txt"));
		//CA.setGoalGrid(new File (carpeta, "urban_2010.txt"));
		CA.setPendiente(new File (carpeta, "pendiente.txt"));
		CA.setBosque(new File (carpeta, "bosques2000.txt"));
		CA.setAgricultura(new File (carpeta, "agrop2000.txt"));
		CA.setFederal(new File (carpeta, "zona_federal.txt"));
		CA.setDificultadCentros(new File (carpeta, "dificultadcentros.txt"));
		
		
		//esto se debe sacar de los archivos de texto que genera el calibrador pues deben ser los valores para los parametros que mejor se acoplan 
		
		CA.privacidad = parametros[0];//
	    CA.gregariedad= parametros[1];
	    CA.difusion1= parametros[2];
	    CA.freno_pendiente= parametros[3];
	    CA.freno_bosque= parametros[4];
	    CA.freno_no_calle= parametros[5];
	    CA.freno_agricultura= parametros[6];
	    CA.freno_zona_federal= parametros[7];
		
//		CA.privacidad = 2;//
//	    CA.gregariedad= 100;
//	    CA.difusion1= 100;
//	    CA.freno_pendiente= 50;
//	    CA.freno_bosque= 50;
//	    CA.freno_no_calle= 50;
//	    CA.freno_agricultura= 50;
//	    CA.freno_zona_federal= 50;
		
		
		
		
		
		
		
		CA.setIteraciones(years);
		
		
		CA.quieroCrecer(years, carpeta, initialYear);

	}
	//aqui hacer un metodo que sea como el de aqui arribita pero que reciba ademas un testNumber 
	//para pasarselo al metodo quieroCrecer que recibe un testNumber
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
			   
			   double ajuste = Double.parseDouble(parametros[parametros.length-1]); 
			   System.out.println(ajuste);
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
	public String tail( File file ) {
	    RandomAccessFile fileHandler = null;
	    try {
	        fileHandler = new RandomAccessFile( file, "r" );
	        long fileLength = fileHandler.length() - 1;
	        StringBuilder sb = new StringBuilder();

	        for(long filePointer = fileLength; filePointer != -1; filePointer--){
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	            if( readByte == 0xA ) {
	                if( filePointer == fileLength ) {
	                    continue;
	                }
	                break;

	            } else if( readByte == 0xD ) {
	                if( filePointer == fileLength - 1 ) {
	                    continue;
	                }
	                break;
	            }

	            sb.append( ( char ) readByte );
	        }

	        String lastLine = sb.reverse().toString();
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        if (fileHandler != null )
	            try {
	                fileHandler.close();
	            } catch (IOException e) {
	                /* ignore */
	            }
	    }
	}
//	  CA.privacidad = 2;//
//    CA.gregariedad= 100;
//    CA.difusion1= 100;
//    CA.freno_pendiente= 50;
//    CA.freno_bosque= 50;
//    CA.freno_no_calle= 50;
//    CA.freno_agricultura= 50;
//    CA.freno_zona_federal= 50;
	public static void main(String args[]){
			//new UrbanizaEsteCacho("/Users/fidel/25avos/x2y2", 10, "urb2000.txt", 2000);
			int [] estosParametros = {4, 15, 18, 96, 83, 70, 83, 83};
			new UrbanizaEsteCacho("/Users/fidel/25avos/x3y3", 20, "urb2010.txt", 2010, estosParametros);
	}
	
}