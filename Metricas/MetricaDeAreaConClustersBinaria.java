package Metricas;

import java.io.File;

import AsciiGrid.AsciiGridReader;
import Modelo.UrbanizandoHibrido;

public class MetricaDeAreaConClustersBinaria{
	MetricaDeNuevaArea areaQueTanto;
	MetricaDeClusters clustersQueTanto;
	public MetricaDeAreaConClustersBinaria(int[][] urbanoInicial, int[][] urbanoFinal, int numeroDeColumnas, int numeroDeRenglones) {
		
		areaQueTanto = new MetricaDeNuevaArea(urbanoInicial, urbanoFinal, numeroDeColumnas, numeroDeRenglones);
		clustersQueTanto = new MetricaDeClusters(urbanoFinal, numeroDeColumnas, numeroDeRenglones);
	}

	
	public double esTantito(int[][] wanabe) {
		double areaNueva = areaQueTanto.esTantito(wanabe);
		double clusters = clustersQueTanto.esTantito(wanabe);
		//if (areaNueva==0 || clusters==0){
			
			return 0.7*areaNueva+0.3*clusters;

			
		//}else{
		//	return Math.sqrt(areaNueva*clusters);
		//}
		
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
		
	}
}
