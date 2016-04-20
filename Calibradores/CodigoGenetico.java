package Calibradores;


/*
 * Created on 22-jul-2004
 *	
 *		
 * cuantas			   = {0-.7}    .12 
 * vecino_0			   = {1-24}	   12
 * vecino_pueblo_0     = {1-24}    12 *NUEVO*
 * vecino_carretera_0  = {1-24}	   11        /* el minimo numero de vecino necesario para que: si estas en el bufer de la carretera, puedas cambiar al siguiente estado
 * buffer_carretera_0  = {10-300}  150	     /* el tamaño de bufer de la carretera en metros
 * buffer_pueblo       = {10-444}      *NUEVO*
 * buffer_carr_pueb    = {10-555}      *NUEVO*   
 * 		primer intento....SoloUnos						
 * 
 *  vecino_1            = {1-240}   62
 * vecino_carretera_1  = {1-240}   36
 * buffer_carretera_1  = {10-300}  50
 * vecino_10           = {10-2400} 666
 * vecino_carretera_10 = {10-300}  200
 * buffer_carretera_10 = {10-300}  30
 * wagri               = {0-1}     .8		/* peso agricultura-bosque
 * wpend               = {0-1}     .99 	    /* peso pendiente
 * iteraciones      = {?-?}     6??? 
 *
 * 
 *  los reales (0-1)los convertire en enteros (0-100)
 */
public class CodigoGenetico{	
//cuantas, vecino_0, vecino_carretera_0, vecino_pueblo, buffer_carretera_0, buffer_pueblo, buffer_carr_pueb	
	
	Gen[] gen;
	public int size;
	public CodigoGenetico(int size) {
		this.size = size;
		gen = new Gen[size];
				
	}
	/**
	 * 
	 */
	public Gen getGen(int j) {
		// TODO Auto-generated method stub
		return (Gen)gen[j];
	}
	public void setRango(int numeroDeGen, int valorMinimo, int valorMaximo){
		gen[numeroDeGen] = new Gen(valorMinimo, valorMaximo);
	}
	
}
class Gen {
	public int suIntervalo;
	public int min, max, brinco;
	public Gen(int min, int max) {
		
		try {
			
			this.min = min;
			this.max = max;
			
			suIntervalo = max - min;
			
			brinco = Math.round(suIntervalo/20);
			if (brinco == 0) brinco = 1;
			
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
}
