package Calibradores;

import java.util.Random;

public abstract class EvolucionanAnealing extends Evolucionan {

	public EvolucionanAnealing() {
		
	}
	public void muta(int individuoOrigen, int individuoAMutar){
		Random escupe= new Random();
		double coolingEfect = 1.0 - (.0009*(double)vueltas);
		for (int i=0; i<infoADN.size; i++){
			vivos.get(individuoAMutar).valoresADN[i] = vivos.get(individuoOrigen).valoresADN[i] + new Double(infoADN.getGen(i).suIntervalo * coolingEfect * escupe.nextGaussian()).intValue();
			
			//para no salir del rango
			if (vivos.get(individuoAMutar).valoresADN[i]>infoADN.getGen(i).max){
				vivos.get(individuoAMutar).valoresADN[i]=infoADN.getGen(i).max;
			}
			else if (vivos.get(individuoAMutar).valoresADN[i]<infoADN.getGen(i).min){
				vivos.get(individuoAMutar).valoresADN[i]=infoADN.getGen(i).min;
			}
			
		}
	}
	
}
