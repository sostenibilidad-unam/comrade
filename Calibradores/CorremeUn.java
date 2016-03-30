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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import AsciiGrid.AsciiGridReader;
import AsciiGrid.AsciiGridWriter;
import Metricas.MultiescalaConBorde;

public abstract class CorremeUn implements ActionListener{
	
	BufferedReader peine;
	String renglon;
	PintaGrid oye;
	ScreenShotter monitor;
	PintaGrid gridmeta;
	String[] parametrosConIndice;
	Ventana chidos;
	Individuo este;
	MultiescalaConBorde metricaQueTanto;
	public CodigoGenetico infoADN;
	TextField[] valorDelParametro;
	JFrame aqui;
	JSlider[] parametros;
	int numeroDeParametros;
	public Panel[] panelesText;
	public String[] nombresDeLosParametros;
	double sumaDeMedidas;
	double vecesQueMediste;
	int[][] unOutput;
	
	public CorremeUn() {
		// TODO Auto-generated constructor stub
			
		
	}
	protected String leeElRenglon() {
		String esteRenglon = null;
		try {
			esteRenglon = peine.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			peine.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return esteRenglon;
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
		JButton toAsciiGrid = new JButton("AsciiGrid");
		toAsciiGrid.addActionListener(new GuardaGridsListener());
		aqui = new JFrame("Parametros");
		aqui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		aqui.setLayout(new GridLayout(2,infoADN.size));
		
		
		JButton dameOtro = new JButton();
		dameOtro.addActionListener(this);
		
		aqui.add(dameOtro);	
		
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(panelesText[i]);
		}
		aqui.add(toAsciiGrid);
		for (int i = 0; i<infoADN.size; i++){
			aqui.add(parametros[i]);
		}
		
		dameOtro.setSize(40, 12);	
			
		
		aqui.setVisible(true);
		aqui.setSize(600,200);
		aqui.setLocation(350,750);
	}
	public void actionPerformed(ActionEvent e) {
		correElModeloyDespliega();
	}
	public void correElModeloyDespliega(){
		
		unOutput=unOutputPara(este.valoresADN);
		double medida=metricaQueTanto.esTantitoNormalizado(unOutput);
		sumaDeMedidas+=medida;
		vecesQueMediste+=1.0;
		
		este.setIndice(medida);
		//este.setIndice(sumaDeMedidas/vecesQueMediste);
		chidos.escribe(este);
		
		String quien = "" + este.valoresADN[0];
		for (int esteGen = 1; esteGen < este.valoresADN.length; esteGen++){
			quien = quien + ", " + este.valoresADN[esteGen];
		}
					
		oye.pinta(unOutput);
		//oye.fr.setTitle(quien);
	}
	
	abstract int[][] unOutputPara(int[] valoresADN);
	
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
	public static void main(String[] args) {
		
		new CorremeUnFrenos();
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
	class LectorDeArchivoListener implements ActionListener{

		

		@Override
		public void actionPerformed(ActionEvent e) {
			preguntaCualArchivo();
			
			renglon=leeElRenglon();
			parametrosConIndice=renglon.split(", ");
			
			este = new Individuo(infoADN);
			este.setIndice( Double.valueOf(parametrosConIndice[parametrosConIndice.length-1]) );
			
			for (int i=0;i<parametrosConIndice.length-1;i++){
				este.valoresADN[i]=Integer.valueOf(parametrosConIndice[i]);
				parametros[i].setValue(este.valoresADN[i]);
				valorDelParametro[i].setText(""+este.valoresADN[i]);
			}
			sumaDeMedidas=0;
			vecesQueMediste=0;
			
			correElModeloyDespliega();
		}
		
	}
	class GuardaGridsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			AsciiGridReader lector = new AsciiGridReader(new File("Topilejo/topi1995.txt"));
			AsciiGridWriter escritor= new AsciiGridWriter();
			escritor.escribeInt(unOutput, lector.numeroDeColumnas, lector.numeroDeRenglones, lector.xllcorner, lector.yllcorner, lector.cellsize, lector.NODATA_value);
			
			

		}

	}
}