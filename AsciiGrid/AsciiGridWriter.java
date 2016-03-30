package AsciiGrid;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;




public class AsciiGridWriter {
	Frame aqui;
	boolean esEntero;
	FileOutputStream out;
	PrintStream pluma;
	
	public AsciiGridWriter() {
		File esteFile = escogeArchivo();
		alistaLaPluma(esteFile);
	}
	
	public AsciiGridWriter(File esteFile){
		alistaLaPluma(esteFile);
	}	
	public void escribeInt(int[][] esteGrid, int numeroDeCeldasX, int numeroDeCeldasY,
			String xllcorner,  String yllcorner, double cellsize, int NODATA_value){
		try{
			pluma.println("ncols         "+numeroDeCeldasX);
        	pluma.println("nrows         "+numeroDeCeldasY);
        	pluma.println("xllcorner     "+xllcorner);
        	pluma.println("yllcorner     "+yllcorner);
        	pluma.println("cellsize      "+cellsize);
        	pluma.println("NODATA_value  "+NODATA_value);
        	
        	for (int y=0; y<numeroDeCeldasY;y++){
        		for (int x=0; x<numeroDeCeldasX;x++){	
        			pluma.print(esteGrid[x][y]+" ");
        		}
        		pluma.println();			  
	 	    } 
					
		  	pluma.close();
		  		  	
         }catch (Exception e){System.err.println ("Error writing to file");}
	}	
	public void escribeDouble(double[][] esteGrid, int numeroDeCeldasX, int numeroDeCeldasY,
			String xllcorner,  String yllcorner, double cellsize, int NODATA_value){
		try{
			pluma.println("ncols         "+numeroDeCeldasX);
        	pluma.println("nrows         "+numeroDeCeldasY);
        	pluma.println("xllcorner     "+xllcorner);
        	pluma.println("yllcorner     "+yllcorner);
        	pluma.println("cellsize      "+cellsize);
        	pluma.println("NODATA_value  "+NODATA_value);
        	
        	for (int y=0; y<numeroDeCeldasY;y++){
        		for (int x=0; x<numeroDeCeldasX;x++){	
        			pluma.print(esteGrid[x][y]+" ");
        		}
        		pluma.println();			  
	 	    } 
					
		  	pluma.close();
		  		  	
         }catch (Exception e){System.err.println ("Error writing to file");}
		
	}	
	private File escogeArchivo(){
			//	despliega un file chooser
			//para que el usuario escoja el .txt
			aqui= new Frame();
			JFileChooser cual = new JFileChooser("c:/workspace");
			cual.setDialogTitle("Salvar el grid como...");
			
			//filtra para mostrar solo los txt
			TxtFilter filtroTxt=new TxtFilter();
			cual.setFileFilter(filtroTxt);
			
			File esteFile=null;
			int retval = cual.showSaveDialog(aqui);
			if (retval == JFileChooser.APPROVE_OPTION) {
				if (cual.getSelectedFile().getName().endsWith(".txt")){
					esteFile = cual.getSelectedFile();
				}else{
					esteFile=new File(cual.getSelectedFile().getAbsolutePath()+".txt");
				}
				
			}
			aqui.dispose();
			return esteFile;
	}
	private void alistaLaPluma(File esteFile) {
		out = null;
		try {
			out = new FileOutputStream(esteFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	pluma = new PrintStream( out );
		
	}
	
	
	public static void main(String[] args) {
		AsciiGridReader mijo = new AsciiGridReader("escoge");
		System.out.println("columnas = " + mijo.numeroDeColumnas + " renglones = " + mijo.numeroDeRenglones);
		int[][] ejemplo;
		ejemplo = mijo.getIntArray();
		System.out.println("la ultima celda " + ejemplo[mijo.numeroDeColumnas][mijo.numeroDeRenglones]);
		
		AsciiGridWriter ejemploTxt = new AsciiGridWriter();
		ejemploTxt.escribeInt(ejemplo, mijo.numeroDeColumnas, mijo.numeroDeRenglones, mijo.xllcorner, mijo.yllcorner, mijo.cellsize, mijo.NODATA_value);

	}

	public void close() {
		// TODO Auto-generated method stub
		
		pluma.close();
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
