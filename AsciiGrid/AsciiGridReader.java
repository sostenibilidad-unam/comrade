package AsciiGrid;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;

public class AsciiGridReader {
	public int numeroDeRenglones=0;
	public int numeroDeColumnas=0;
	public String xllcorner="";
	public String yllcorner="";
	public double cellsize=0.0;
	public int NODATA_value=-9999;
	public int[][] loQueLeisteInteger;
	public double[][] loQueLeisteDouble;
	Frame aqui;
	BufferedReader peine;
		
	
			
	public AsciiGridReader(String titulo){
		
		//despliega un file chooser
		//para que el usuario escoja el .txt
		aqui= new Frame();
		JFileChooser cual = new JFileChooser("c:/");
		cual.setDialogTitle(titulo);
		
		//filtra para mostrar solo los txt
		TxtFilter filtroTxt=new TxtFilter();
		cual.setFileFilter(filtroTxt);
		
		File esteGrid=null;
		int retval = cual.showOpenDialog(aqui);
		if (retval == JFileChooser.APPROVE_OPTION) {
		  esteGrid=cual.getSelectedFile();
		}
		aqui.dispose();
		LeeTxt(esteGrid);
		
	}
	public AsciiGridReader(File esteGrid){
		LeeTxt(esteGrid);
	}
	
	private void LeeTxt(File esteGrid){
		
			//este metodo lee grids de ArcInfo exportados a ASCII 
			
				    	
			peine = null;
			cellsize=0.0;      
			NODATA_value=0; 
			
			try {
				peine = new BufferedReader( new FileReader(esteGrid) );
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
			try {
				peine.skip(14);
				numeroDeColumnas = Integer.parseInt(peine.readLine());
				peine.skip(14);
				numeroDeRenglones = Integer.parseInt(peine.readLine());
				peine.skip(14);
				xllcorner = peine.readLine();
				peine.skip(14);
				yllcorner = peine.readLine();
				peine.skip(14);
				cellsize = Double.parseDouble(peine.readLine());
				peine.skip(14);
				NODATA_value = Integer.parseInt(peine.readLine());
				
				
				//se les da tamo�o a los arrays
				loQueLeisteInteger=new int[numeroDeColumnas][numeroDeRenglones];
			    loQueLeisteDouble=new double[numeroDeColumnas][numeroDeRenglones];
			    
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			//este for recorre los renglones del archivo
			String esteToken = "";
			for (int renglon=0;renglon<numeroDeRenglones;renglon++){
				StringTokenizer oye=null;
				try {
					oye = new StringTokenizer( peine.readLine());
				} catch (IOException e) {
					System.out.println("aaahhh...");
					e.printStackTrace();
				}
				//este for recorre las columnas para cada renglon
				
				for (int columna=0;columna<numeroDeColumnas;columna++){
					if(oye.hasMoreTokens()){
						esteToken = oye.nextToken();
						//loQueLeisteInteger[columna][renglon]=Integer.parseInt(esteToken);
						loQueLeisteDouble[columna][renglon]=Double.parseDouble(esteToken);
					}
					
				}
			}
	}
	
	public int[][] getIntArray(){
		for (int columna=0;columna<numeroDeColumnas;columna++){
			for (int renglon=0;renglon<numeroDeRenglones;renglon++){
				loQueLeisteInteger[columna][renglon] = (int)loQueLeisteDouble[columna][renglon]; 
				//System.out.print(loQueLeisteInteger[columna][renglon] + " ");
			}
			//System.out.println();
		}
		   
		return loQueLeisteInteger;
	}
	public double[][] getDoubleArray(){
		return loQueLeisteDouble;
	}
	public void close() {
		// para cerrar los objetos de lectura
		try {
			peine.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		AsciiGridReader mijo = new AsciiGridReader("escoge grid");
		System.out.println("columnas = " + mijo.numeroDeColumnas + " renglones = " + mijo.numeroDeRenglones);
		double[][] ejemplo;
		ejemplo = mijo.getDoubleArray();
		System.out.println("la ultima celda " + ejemplo[mijo.numeroDeColumnas][mijo.numeroDeRenglones]);
		
	}
	
}
