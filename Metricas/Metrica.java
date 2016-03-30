package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;


public abstract class Metrica {
// esta clase es una manera de medir la diferencia en la forma de dos grids
	
	//comoEste es el grid patron 
	//wanabe es el grid que se quiere apoximar a comoEste 
	// sele pasa el comoEste en el constructor porque comoEste no cambia
	//pero wanabe es un grid nuevo por cada corrida del modelo asi que se 
	//le pasa en el metodo esTantito
	public int [][] comoEste;
    public int [][] wanabe;
		
	public int numeroDeColumnas;
	public int numeroDeRenglones;
	public double factorNormalizante;
	
	public Metrica(int[][] comoEste, int numeroDeColumnas, int numeroDeRenglones){
		this.numeroDeColumnas=numeroDeColumnas;
		this.numeroDeRenglones=numeroDeRenglones;
				
		wanabe=new int[numeroDeColumnas+2][numeroDeRenglones+2];
		this.comoEste=new int[numeroDeColumnas+2][numeroDeRenglones+2];
		//aqui se define el tama�o de wanabe y comoEste usando lo recibido, 
		//osea que para usar una metrica es necesario decirle al constructor,
		//el tama�o de los grids a comparar
		
		
		//estos dos fors son para darle una orilla de ceros al grid comoeste,
		//esto se hace para poder calcular en las celdas de la orilla 
		//de la misma forma que en las celdas interiores
		for (int i=0;i<=numeroDeColumnas+1;i++){
			for (int j=0;j<=numeroDeRenglones+1;j++){
				this.comoEste[i][j]=0;
			}
		}
		
		for (int i=0;i<numeroDeColumnas;i++){
			for (int j=0;j<numeroDeRenglones;j++){
				this.comoEste[i+1][j+1]=comoEste[i][j];
			}
		}
		factorNormalizante=1.0;
	}
	
	public void  normalizateConEste(int[][] paraNormalizar){
		factorNormalizante = esTantito(paraNormalizar);
		System.out.println("factorNormalizante = " +factorNormalizante);
	}
	public void  normalizateConEste(File fileParaNormalizar){
		AsciiGridReader reader = new AsciiGridReader(fileParaNormalizar);
		int[][] arrayParaNormalizar = reader.getIntArray();
		normalizateConEste(arrayParaNormalizar);
		reader.close();
	}
	
	public abstract double esTantito(int[][] wanabe);
		// este es el metodo publico con el que las Metricas miden la distancia entre 
		// el grid que se le dio en el constructor y el wanabe que se recibe como
		// argumento en este metodo
		
		//this.wanabe=wanabe;
	public double esTantitoNormalizado(int[][] wanabe){
		return esTantito(wanabe)/factorNormalizante;
	}
}
