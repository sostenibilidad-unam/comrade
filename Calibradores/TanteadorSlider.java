package Calibradores;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class TanteadorSlider{

	
	public PintaGrid pintaGrid1;
	public PintaGrid pintaGrid2;
	public PintaGrid pintaGrid3;
	
	public PuntoDeBusqueda puntoDeTanteo, puntoDelRecuerdo, vuelo, deltaRandom;
	public CodigoGenetico infoADN;
	public ScreenShotter monitor;
	public int contador;
	public Ventana ventana, bajando;
	int iteraciones;
	public boolean[] esMovible;

	private JSlider[] parametros;
	private TextField[] valorDelParametro;
	private JCheckBox[] fijame;
	private JFrame aqui;
	private BufferedReader peine;
	public String[] nombresDeLosParametros;
	public Panel[] panelesText;
	private boolean siguele;

	public TanteadorSlider(){
		contador=0;
		iteraciones=0;
	}
	public void acoplaSliders(){
		parametros= new JSlider[infoADN.size];
		valorDelParametro=new TextField[infoADN.size];
		esMovible = new boolean[infoADN.size];
		fijame = new JCheckBox[infoADN.size];
		panelesText=new Panel[infoADN.size];
		
		for (int i = 0; i<infoADN.size; i++){
			
			
			parametros[i] = new JSlider(JSlider.VERTICAL,
	                infoADN.getGen(i).min , infoADN.getGen(i).max, infoADN.getGen(i).min);	
			parametros[i].addChangeListener(new sliderCatcher(i));
			parametros[i].setToolTipText(nombresDeLosParametros[i]);
			
			valorDelParametro[i]=new TextField(""+ infoADN.getGen(i).min);
			valorDelParametro[i].addActionListener(new textFieldCatcher(i));
			panelesText[i]=  new Panel();
			panelesText[i].add(valorDelParametro[i]);
			
		
			fijame[i] = new JCheckBox("fijar");
			fijame[i].addItemListener(new CheckBoxListener(i));
		
		}
		
		
		
		aqui = new JFrame("Parametros");
		aqui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		aqui.setLayout(new GridLayout(3,infoADN.size));
		
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(panelesText[i]);
		}
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(parametros[i]);
		}
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(fijame[i]);
		}
				
			
		
		aqui.setVisible(true);
		aqui.setSize(600,200);
		aqui.setLocation(350,750);
	}

	public String dameOtroRenglon() {
		String esteRenglon = null;
		try {
			esteRenglon = peine.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return esteRenglon;
	}
	public void preguntaCualArchivo() {
		
		JFileChooser cual = new JFileChooser("C:/");
		
		
		int retval = cual.showOpenDialog(aqui);
		if (retval == JFileChooser.APPROVE_OPTION) {
			try {
				peine = new BufferedReader( new FileReader(cual.getSelectedFile()) );
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public void busca() {
		//monitor = new ScreenShotter();
		contador++;
		puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));
		for (int i=0; i<infoADN.size;i++){
			vuelo.parametros[i]=0;
		}
		
		siguele=true;
		while ((puntoDelRecuerdo.indice>.00001) && (siguele) ){
			iteraciones++;
			despliegaElRecuerdo();
			
			deltaRandom = generaVectorRandom();
			puntoDeTanteo = sumaVectores ();
			ponteEnRango();	
			
			puntoDeTanteo.setIndice(mide(puntoDeTanteo.parametros));
			puntoDelRecuerdo.setIndice(mide(puntoDelRecuerdo.parametros));

			
			if (puntoDeTanteo.indice < puntoDelRecuerdo.indice){
				quedateConElTanteo();
				vuelo = calculaVueloPositivo();
			}else{
				puntoDeTanteo = restaVectores ();
				ponteEnRango();
				puntoDeTanteo.setIndice(mide(puntoDeTanteo.parametros));
				if (puntoDeTanteo.indice < puntoDelRecuerdo.indice){
					quedateConElTanteo();
					vuelo = calculaVueloNegativo();
				}else{
					reduceVuelo();
					//solo para correr los 3 calibradores al mismo tiempo
					//ventana.escribe(puntoDeTanteo);
				}
			}
		}
		System.out.println("esto fue el busca numero " + contador);
		//System.out.print("EXITO! en : " + iteraciones + " iteraciones");
		
	}
	public void quedateConElTanteo() {
		
		puntoDelRecuerdo = puntoDeTanteo;
		
		
		
		
	}
	public void despliegaElRecuerdo(){
		int[] uno = puntoDelRecuerdo.parametros;
		String quien = "" + uno[0];
		for (int esteGen = 1; esteGen < uno.length; esteGen++){
			quien = quien + ", " + uno[esteGen];
		}
		pintaGrid2.pinta(unOutputPara(uno));
		pintaGrid2.fr.setTitle(quien);
		bajando.escribe(puntoDelRecuerdo, iteraciones);
		for (int i=0; i<infoADN.size; i++){
			parametros[i].setValue(puntoDelRecuerdo.parametros[i]);
			valorDelParametro[i].setText(""+ puntoDelRecuerdo.parametros[i]);
		}
	}
	
	public void reduceVuelo() {
		for (int gen=0;gen < infoADN.size;gen++){
			vuelo.parametros[gen] =  new Double( (0.95)* vuelo.parametros[gen]  ).intValue();
		}
		
	}
	protected PuntoDeBusqueda restaVectores() {
		PuntoDeBusqueda resta = new PuntoDeBusqueda(infoADN.size);
		for (int gen=0;gen < infoADN.size;gen++){
			resta.parametros[gen] = puntoDelRecuerdo.parametros[gen] + vuelo.parametros[gen] - deltaRandom.parametros[gen];
		}
		return resta;
		
	}
	protected PuntoDeBusqueda calculaVueloPositivo() {
		PuntoDeBusqueda v = new PuntoDeBusqueda(infoADN.size);
		for (int gen=0;gen < infoADN.size;gen++){
			v.parametros[gen] =  new Double( ((0.2)* vuelo.parametros[gen]) + ((0.4) * deltaRandom.parametros[gen]) ).intValue();
		}
		return v;
	}
	protected PuntoDeBusqueda calculaVueloNegativo() {
		PuntoDeBusqueda v = new PuntoDeBusqueda(infoADN.size);
		for (int gen=0;gen < infoADN.size;gen++){
			v.parametros[gen] =  new Double( ((0.2)* vuelo.parametros[gen]) - ((0.4) * deltaRandom.parametros[gen]) ).intValue();
		}
		return v;
	}
	protected void ponteEnRango() {
		
		for (int gen=0;gen < infoADN.size;gen++){
			if(puntoDeTanteo.parametros[gen]>infoADN.getGen(gen).max)puntoDeTanteo.parametros[gen]=infoADN.getGen(gen).max;
			if(puntoDeTanteo.parametros[gen]<infoADN.getGen(gen).min)puntoDeTanteo.parametros[gen]=infoADN.getGen(gen).min;
			
		}
		
	}
	
	
	public PuntoDeBusqueda sumaVectores(){
		PuntoDeBusqueda suma = new PuntoDeBusqueda(infoADN.size);
		for (int i=0;i < infoADN.size;i++){
			suma.parametros[i] = puntoDelRecuerdo.parametros[i] +deltaRandom.parametros[i] + vuelo.parametros[i];
		}
		return suma;
	}
	protected PuntoDeBusqueda generaVectorRandom() {
		PuntoDeBusqueda deltaRandom = new PuntoDeBusqueda(infoADN.size);
		Random oye = new Random();
		for (int i=0;i < infoADN.size;i++){
			deltaRandom.parametros[i] = new Double(infoADN.getGen(i).suIntervalo * oye.nextGaussian()).intValue();
		}
		return deltaRandom;
	}
	
	public abstract double mide(int[] parametros);
	
	public abstract int[][] unOutputPara(int[] parametros);
	
	
	
	
	class CheckBoxListener implements ItemListener {
		int parametro;
        public CheckBoxListener(int parametro){
        	this.parametro=parametro;
        }
        @Override
		public void itemStateChanged(ItemEvent e) {
        	esMovible[parametro]=false;
        	int valor = parametros[parametro].getValue();
        	puntoDelRecuerdo.parametros[parametro]=valor;
        	puntoDeTanteo.parametros[parametro]=valor;
	        	
        	if (e.getStateChange() == ItemEvent.DESELECTED){
        		esMovible[parametro]=true;
        	}
		}
        	
    }
	
	class sliderCatcher implements ChangeListener{
		int parametro;
		public sliderCatcher(int parametro){
			this.parametro=parametro;
		}
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		        int valor = (int)source.getValue();
		        siguele=false;
		        puntoDelRecuerdo.parametros[parametro]=valor;
		        busca();
//		        puntoDelRecuerdo.parametros[parametro]=valor;
//	        	puntoDeTanteo.parametros[parametro]=valor;
//		       	        
//		        valorDelParametro[parametro].setText(""+valor);
		        
		    }
			
		}
		
	}
	class textFieldCatcher implements ActionListener{
		int parametro;
		public textFieldCatcher(int parametro){
			this.parametro=parametro;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				int valor = Integer.valueOf( ((TextField)e.getSource()).getText() );
				puntoDelRecuerdo.parametros[parametro]=valor;
	        	puntoDeTanteo.parametros[parametro]=valor;
				parametros[parametro].setValue(valor);	  
				
		    
		}
		
	}

}
