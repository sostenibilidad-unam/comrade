package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;
import Modelo.UrbanizandoHibrido;

public class MultiescalaConBorde extends Metrica {
	MetricaMultiescala multiescalaQueTanto;
	MetricaDeBorde bordeQueTanto;
	public MultiescalaConBorde(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		multiescalaQueTanto = new MetricaMultiescala(comoEste, numeroDeColumnas, numeroDeRenglones);
		bordeQueTanto = new MetricaDeBorde(comoEste, numeroDeColumnas, numeroDeRenglones);
	}

	@Override
	public double esTantito(int[][] wanabe) {
		double multiescala = multiescalaQueTanto.esTantito(wanabe);
		double borde = bordeQueTanto.esTantito(wanabe);
		if (multiescala==0 || borde==0){
			return (multiescala+borde)/2.0;

			
		}else{
			return Math.sqrt(multiescala*borde);
		}
		
	}
	
//	public void  normalizateConEste(int[][] paraNormalizar){
//		multiescalaQueTanto.normalizateConEste(paraNormalizar);
//		bordeQueTanto.normalizateConEste(paraNormalizar);
//		//System.out.print("factorNormalizante = " +factorNormalizante);
//	}
//	public void  normalizateConEste(File fileParaNormalizar){
//		AsciiGridReader reader = new AsciiGridReader(fileParaNormalizar);
//		int[][] arrayParaNormalizar = reader.getIntArray();
//		multiescalaQueTanto.normalizateConEste(arrayParaNormalizar);
//		bordeQueTanto.normalizateConEste(arrayParaNormalizar);
//		reader.close();
//	}
//	public double esTantitoNormalizado(int[][] wanabe){
//		return (multiescalaQueTanto.esTantitoNormalizado(wanabe)+bordeQueTanto.esTantitoNormalizado(wanabe))/2.0;
//	}
	public static void main(String[] args) {
		UrbanizandoHibrido CA= new UrbanizandoHibrido(72, 56, 11, 4);
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDificultad(new File ("Topilejo/dificultad.txt"));
		CA.setDistVias(new File ("Topilejo/dist_vias.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		//CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY ); 
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		System.out.print(CA.mide(3, 6, 5, 100, 295, 100, 80, 444, 319));
	}
}
