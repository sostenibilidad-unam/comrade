package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;

public class MultiescalaBribiesca extends Metrica {
	MetricaMultiescala multiescalaQueTanto;
	MetricaBribiesca bribiescaQueTanto;
	public MultiescalaBribiesca(int[][] comoEste, int numeroDeColumnas,
			int numeroDeRenglones) {
		super(comoEste, numeroDeColumnas, numeroDeRenglones);
		multiescalaQueTanto = new MetricaMultiescala(comoEste, numeroDeColumnas, numeroDeRenglones);
		bribiescaQueTanto = new MetricaBribiesca(comoEste, numeroDeColumnas, numeroDeRenglones);
	
	}

	@Override
	public double esTantito(int[][] wanabe) {
		//Math.sqrt(
		return  (multiescalaQueTanto.esTantito(wanabe)+bribiescaQueTanto.esTantito(wanabe)) / 2.0 ;
	}

//	public void  normalizateConEste(int[][] paraNormalizar){
//		multiescalaQueTanto.normalizateConEste(paraNormalizar);
//		bribiescaQueTanto.normalizateConEste(paraNormalizar);
//		System.out.print("factorNormalizante = " +factorNormalizante);
//	}
//	public void  normalizateConEste(File fileParaNormalizar){
//		AsciiGridReader reader = new AsciiGridReader(fileParaNormalizar);
//		int[][] arrayParaNormalizar = reader.getIntArray();
//		multiescalaQueTanto.normalizateConEste(arrayParaNormalizar);
//		bribiescaQueTanto.normalizateConEste(arrayParaNormalizar);
//		reader.close();
//	}
//	public double esTantitoNormalizado(int[][] wanabe){
//		return (multiescalaQueTanto.esTantitoNormalizado(wanabe)+bribiescaQueTanto.esTantitoNormalizado(wanabe))/2.0;
//	}
	
}
