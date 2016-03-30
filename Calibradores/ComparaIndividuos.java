package Calibradores;
import java.util.Comparator;
/*
 * Created on 22-jul-2004
 */
public class ComparaIndividuos implements Comparator {
	public int compare(Object arg0, Object arg1) {
		Individuo i0 = (Individuo)arg0;
		Individuo i1 = (Individuo)arg1;
		if (i0.indice < i1.indice)return -1;
		else if (i0.indice == i1.indice)return 0;
		else return 1;	
	}

}
