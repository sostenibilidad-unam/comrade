package Calibradores;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;

/*
 * Created on 31-jul-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author USER
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Ventana extends Frame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextArea yoDigo;
	boolean hayDisplay= false;
	Process guardando = null;
	public Grafica graf;
	Vector<Double> datos;
	public Frame queTanto;
	TextArea metrica;
	
	/**
	 * 
	 */

	public Ventana(String pal_Boton, String titulo) {
		super();
		graf = new Grafica(titulo + "_graf");
		datos = new Vector<Double>();
		
		setTitle(titulo);
		setSize(515,400);
		//setResizable(false);
		yoDigo = new TextArea("", 20, 40, TextArea.SCROLLBARS_VERTICAL_ONLY );
		yoDigo.setFont(new Font("u", Font.BOLD,13));
		Button salvar = new Button(pal_Boton);
		Button graficar = new Button("Ver Grafica");
		salvar.addActionListener(this);
		ActionListener ver = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	graf.pinta(datos);
               
            }
        };
        graficar.addActionListener(ver);
		
		setLayout(new BorderLayout());
		add(graficar, BorderLayout.NORTH);
		add(yoDigo, BorderLayout.CENTER);
		add(salvar, BorderLayout.SOUTH);
		setLocation(new Point(111,333));
		
		
		queTanto = new Frame();
		queTanto.setSize(368, 65);
		queTanto.setLayout(new BorderLayout());
		metrica = new TextArea();
		
		metrica.setFont(new Font("u", Font.BOLD,20));
		queTanto.add(metrica, BorderLayout.NORTH);
		queTanto.setVisible(true);
		
		
        
        // creo que la honda es chart.getXYPlot().setRenderer y 
        //al hacer el renderer decirle que se haga con el tooltipgrenerator ttg
        
		
		
	}
	public void setQueTantoLocation(int x, int y) {
		queTanto.setLocation(x, y);
		
		
	}
	public void escribe(String esteRenglon){
		yoDigo.append(esteRenglon);
		
	}
	public void escribe(Individuo esteGuey) {
		Double d = new Double(esteGuey.indice);
		datos.addElement(d);
		graf.agrega(esteGuey.indice);
		
		
		
		int[] parametro = esteGuey.valoresADN;
		String quien = "";
		
		for (int i = 0; i < parametro.length -1; i++){
			quien= "" + quien + parametro[i] + ", ";
		}
		quien= "" + quien +""+ parametro[parametro.length-1] + ", " + esteGuey.indice ;
		 
		escribe("" + quien + "\n");
		
		graf.addToolTip(quien);
		
		
		metrica.setText("    "+esteGuey.indice);
		
		
	}
	public void escribe(Individuo esteGuey, int iteraciones) {
		Double d = new Double(esteGuey.indice);
		datos.addElement(d);
		graf.agrega(esteGuey.indice);
		
		
		
		int[] parametro = esteGuey.valoresADN;
		String quien = "";
		
		for (int i = 0; i < parametro.length -1; i++){
			quien= "" + quien + parametro[i] + ", ";
		}
		quien= "" + quien +""+ parametro[parametro.length-1] + ") -> " + esteGuey.indice ;
		 
		escribe("" + quien + "\n");
		
		graf.addToolTip(quien);
		
		
		
		metrica.setText("#iteraciones="+ iteraciones +"           "+esteGuey.indice);
		
		
	}
	
	
    
    
   
    

	
	public void escribe(PuntoDeBusqueda esteGuey, int iteraciones) {
						
		int[] parametro = esteGuey.parametros;
		String quien="";
		
		for (int i = 0; i <= parametro.length -1; i++){
			quien= "" + quien + parametro[i] + ", ";
		}
		quien= "" + quien + "" + esteGuey.indice ;
		
		escribe("" + quien + "\n");
		
		graf.addToolTip(quien);
		
		metrica.setText("#iteraciones = "+ iteraciones +", M = "+esteGuey.indice);
		
		Double d = new Double(esteGuey.indice);
		datos.addElement(d);
		graf.agrega(esteGuey.indice);
	}
	public void escribe(PuntoDeBusqueda esteGuey) {
		
		int[] parametro = esteGuey.parametros;
		
		String quien="";
		
		for (int i = 0; i <= parametro.length -1; i++){
			quien= "" + quien + parametro[i] + ", ";
		}
		quien= "" + quien +"" + esteGuey.indice ;
		 
		escribe("" + quien + "\n");
		
		graf.addToolTip(quien);
		
		metrica.setText("    "+esteGuey.indice);
		
		Double d = new Double(esteGuey.indice);
		datos.addElement(d);
		graf.agrega(esteGuey.indice);
	}
	public void actionPerformed(ActionEvent e) {
		
		//verToggle();
		
		JFileChooser aqui = new JFileChooser();
		aqui.showSaveDialog(this);
		BufferedWriter pon = null;
		
		try {
			pon = new BufferedWriter (new FileWriter( aqui.getSelectedFile() ));
			pon.write( this.yoDigo.getText() );
			pon.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("NO PUDE GUARDAR");
			e1.printStackTrace();
		}
		
		
		
	}
	public void setQueTantoSetSize(int i, int j) {
		queTanto.setSize(i, j);
		
	}
	public void cierra(){
		graf.dispose();
		queTanto.dispose();
		dispose();
	}
	
	
//	public void verToggle(){
//		if (guardando == null){
//			try {
//				guardando = Runtime.getRuntime().exec("arc &r comoVamos");
//				BufferedReader siDime = new BufferedReader( new InputStreamReader( guardando.getInputStream() ) ) ;
//				String str = siDime.readLine();
//				System.out.println(str);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				//escribe( e.getMessage() );
//			}
//		}else{
//			
//			
//				guardando.destroy();	
//				try {
//					guardando.waitFor();
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				guardando.destroy();
//				guardando = null;
//				
//			
//		}
//	}
}
