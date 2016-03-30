package Calibradores;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import AsciiGrid.AsciiGridReader;
import Metricas.MultiescalaConBorde;
import Modelo.UrbanizandoHibrido;	
import Modelo.UrbanizandoFrenos;


public class CronologiaDeUnaBusqueda {
	public CodigoGenetico infoADN;
	UrbanizandoFrenos CA;
	PintaGrid oye;
	ScreenShotter monitor;
	private PintaGrid gridmeta;
	BufferedReader peine;
	String renglon;
	String[] parametrosConIndice;
	Ventana chidos;
	Individuo este;
	MultiescalaConBorde metricaQueTanto;
	TextField[] valorDelParametro;
	JFrame aqui;
	JSlider[] parametros;
	int numeroDeParametros;
	public Panel[] panelesText;
	public String[] nombresDeLosParametros;
	
	
	int[][] unOutput;
	
	public CronologiaDeUnaBusqueda() {
		CA = new UrbanizandoFrenos(72, 56, 11, 4);
		infoADN = CA.infoADN;
		nombresDeLosParametros = CA.nombresDeLosParametros;
			
		CA.setInitialGrid(new File ("Topilejo/topi1995.txt"));
		CA.setDistVias(new File ("Topilejo/distancia1.txt"));
		CA.setGoalGrid(new File ("Topilejo/topi1999.txt"));
		CA.setPendiente(new File ("Topilejo/pendiente.txt"));
		CA.setBosque(new File ("Topilejo/bosqueb.txt"));
		MultiescalaConBorde metrica = new MultiescalaConBorde(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//Metrica8metricas metrica = new Metrica8metricas(CA.gridMeta, CA.pendiente, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		//MultiescalaClusters metrica = new MultiescalaClusters(CA.gridMeta, CA.numeroDeCeldasX, CA.numeroDeCeldasY );
		
		metrica.normalizateConEste(new File ("Topilejo/topi1995.txt"));
		CA.setMetric(metrica);
		CA.setIteraciones(4);
		
		monitor=new ScreenShotter();
		oye=new PintaGrid(new File ("Topilejo/topi1995.txt"), "??", 5);
		oye.setLocation(460, 590);
		
		gridmeta=new PintaGrid(new File ("Topilejo/topi1999.txt"), "Grid Meta", 5);
		gridmeta.setLocation(827, 590);
		
		PintaGrid gridInicial = new PintaGrid(new File ("Topilejo/topi1995.txt"), "Grid Salida", 5);
		gridInicial.setLocation(460, 70);
		
		Ventana chidos = new Ventana("Guardar Resultados", "Algoritmo Evolutivo");
		chidos.setSize(368, 830);
		chidos.setLocation(93, 70);
		chidos.setVisible(true);
		chidos.graf.setLocation(827, 70);
		chidos.graf.setSize(368, 521);
		chidos.setQueTantoLocation(460, 525);
		
		
		//preguntaCualArchivo();
		try {
			peine = new BufferedReader( new FileReader(new File("C:/unaBusquedaBien2.txt")) );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		renglon=dameOtroRenglon();
		String[] parametrosConIndice;
		int contador=0;
		int cuentaHasta5=0;
		parametrosConIndice=renglon.split(", ");
		
		este = new Individuo(infoADN);
		este.setIndice( Double.valueOf(parametrosConIndice[parametrosConIndice.length-1]) );
		
		for (int i=0;i<parametrosConIndice.length-1;i++){
			este.valoresADN[i]=Integer.valueOf(parametrosConIndice[i]);
		}
		chidos.escribe(este);
		
		
		acoplaSliders();
		
		aqui.setSize(368,170);
		aqui.setLocation(460,380);
		
		while (renglon!=null){
			
			cuentaHasta5++;
			
			
			String quien = "" + este.valoresADN[0];
			for (int esteGen = 1; esteGen < este.valoresADN.length; esteGen++){
				quien = quien + ", " + este.valoresADN[esteGen];
			}
						
			oye.pinta(CA.unOutputPara(este.valoresADN));
			oye.fr.setTitle(quien);
			
			renglon=dameOtroRenglon();
			String ceros="";
			if (cuentaHasta5==5){
				contador++;
				cuentaHasta5=0;
				if ((contador<100)&&(contador>=10)){
					ceros="0";
				}
				if (contador<10){
					ceros="00";
				}
				monitor.captureNow("monitor"+ ceros +""+ contador+ ".jpg");
			}
			if (renglon!=null){
				parametrosConIndice=renglon.split(", ");
				
				este = new Individuo(infoADN);
				este.setIndice( Double.valueOf(parametrosConIndice[parametrosConIndice.length-1]) );
				
				for (int i=0;i<parametrosConIndice.length-1;i++){
					este.valoresADN[i]=Integer.valueOf(parametrosConIndice[i]);
				}
				ajustaSliders();
				chidos.escribe(este);
			}
			
			
		}
		oye.pinta(new File("frenos4.txt"));
		contador++;
		monitor.captureNow("monitor"+ contador+ ".jpg");
		contador++;
		monitor.captureNow("monitor"+ contador+ ".jpg");
		contador++;
		monitor.captureNow("monitor"+ contador+ ".jpg");
		contador++;
		monitor.captureNow("monitor"+ contador+ ".jpg");
		contador++;
		monitor.captureNow("monitor"+ contador+ ".jpg");
		contador++;
		monitor.captureNow("monitor"+ contador+ ".jpg");
	}
	private void ajustaSliders() {
		for (int i = 0; i<infoADN.size; i++){
			parametros[i].setValue(este.valoresADN[i]);	
			valorDelParametro[i].setText(""+ este.valoresADN[i]);
		}
		
	}
	private String dameOtroRenglon() {
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
		JFrame aqui;
		aqui = new JFrame("selecciona in archivo");
		aqui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aqui.setSize(200,100);
		aqui.setLocation(400,400);
		//aqui.setVisible(true);
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
	public void acoplaSliders(){
		parametros= new JSlider[infoADN.size];
		valorDelParametro=new TextField[infoADN.size];
		panelesText=new Panel[infoADN.size];
				
		for (int i = 0; i<infoADN.size; i++){
			parametros[i] = new JSlider(JSlider.VERTICAL,
	                infoADN.getGen(i).min , infoADN.getGen(i).max, este.valoresADN[i]);	
			parametros[i].addChangeListener(new sliderCatcher(i));
			parametros[i].setToolTipText(nombresDeLosParametros[i]);
			
			valorDelParametro[i]=new TextField(""+ este.valoresADN[i]);
			//valorDelParametro[i].setSize(40, 20);
			//valorDelParametro[i].setAlignment(Label.CENTER);
			valorDelParametro[i].addActionListener(new textFieldCatcher(i));
			panelesText[i]=  new Panel();
			panelesText[i].add(valorDelParametro[i]);
		
		}
//		JButton fromFile = new JButton("from File");
//		fromFile.addActionListener(new LectorDeArchivoListener());
//		
//		JButton toAsciiGrid = new JButton("AsciiGrid");
//		toAsciiGrid.addActionListener(new GuardaGridsListener());
		aqui = new JFrame("Parametros");
		aqui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		aqui.setLayout(new GridLayout(2,infoADN.size));
		
		
//		JButton dameOtro = new JButton();
//		dameOtro.addActionListener(this);
		
//		aqui.add(dameOtro);	
		
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(panelesText[i]);
		}
		//aqui.add(toAsciiGrid);
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(parametros[i]);
		}
		
		//dameOtro.setSize(40, 12);	
			
		
		aqui.setVisible(true);
		aqui.setSize(400,170);
		aqui.setLocation(480,400);
	}
	public static void main(String[] args) {
		
		new CronologiaDeUnaBusqueda();
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
				este.valoresADN[parametro]=valor;
				parametros[parametro].setValue(valor);	        
		    
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
		        este.valoresADN[parametro]=valor;
		        valorDelParametro[parametro].setText(""+valor);
		        
		    }
			
		}
		
	}
}
