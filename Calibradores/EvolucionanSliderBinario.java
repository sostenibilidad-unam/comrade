package Calibradores;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public abstract class EvolucionanSliderBinario extends EvolucionanBinario {
	private JSlider[] parametros;
	private TextField[] valorDelParametro;
	private JCheckBox[] fijame;
	protected JFrame aqui;
	
	public String[] nombresDeLosParametros;
	public Panel[] panelesText;

	public void acoplaSliders(){
		parametros= new JSlider[infoADN.size];
		valorDelParametro=new TextField[infoADN.size];
		//esMovible = new boolean[infoADN.size];
		fijame = new JCheckBox[infoADN.size];
		panelesText=new Panel[infoADN.size];
		
		for (int i = 0; i<infoADN.size; i++){
			
			
			parametros[i] = new JSlider(JSlider.VERTICAL,
	                infoADN.getGen(i).min , infoADN.getGen(i).max, vivos.get(0).valoresADN[i]);	
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

	
	
	
	public void despliegaElMejor() {
//		chidos.escribe(vivos.get(0));
		int este[] = vivos.get(0).valoresADN;
		String quien = "" + este[0];
		for (int esteGen = 1; esteGen < este.length; esteGen++){
			quien = quien + ", " + este[esteGen];
		}
//		pintaGrid2.pinta(unOutputPara(este));
//		pintaGrid2.fr.setTitle(quien);
		for (int i=0; i<infoADN.size; i++){
			parametros[i].setValue(vivos.get(0).valoresADN[i]);
			valorDelParametro[i].setText(""+ vivos.get(0).valoresADN[i]);
		}
    	
			
	}
	public void close(){
		aqui.dispose();
//		ventana.cierra();
//		chidos.cierra();
//		pintaGrid1.dispose();
//		pintaGrid2.dispose();
//		pintaGrid3.dispose();
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
        	//esMovible[parametro]=false;
        	int valor = parametros[parametro].getValue();
        	for (int j=0; j<6; j++){
	        	vivos.get(j).valoresADN[parametro]=valor;
	        }		
//        	if (e.getStateChange() == ItemEvent.DESELECTED){
//        		esMovible[parametro]=true;
//        	}
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
		        for (int j=0; j<6; j++){
		        	vivos.get(j).valoresADN[parametro]=valor;
		        }
		        	
		       	        
		        valorDelParametro[parametro].setText(""+valor);
		        
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
				for (int j=0; j<6; j++){
					vivos.get(j).valoresADN[parametro]=valor;
					parametros[parametro].setValue(valor);	    
				}
				    
		    
		}
		
	}
}
