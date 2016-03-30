package Calibradores;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


/**
 * A simple demo showing a dataset created using the {@link XYSeriesCollection} class.
 *
 */
public class Jfree implements ActionListener{

    /**
     * A demonstration application showing an XY series containing a null value.
     *
     * @param title  the frame title.
     */
	 Frame aqui;
	 Button boton;
	 XYSeries series;
	int contador;
	Random oye;
    public Jfree() {

        aqui = new Frame();
        aqui.setSize(150, 150);
        boton = new Button();
        
		boton.addActionListener(this);
		aqui.setLocation(600, 200);
		aqui.add(boton);
		aqui.setVisible(true);
		
        series = new XYSeries("Random Data");
        oye = new Random();
        for (int i=0;i<33;i++){
        	series.add(i, 20 + oye.nextInt(10));
        }
        contador = 33;
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "XY Series Demo",
            "X", 
            "Y", 
            xyDataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        ChartFrame chartFrame = new ChartFrame("aver", chart);
        chartFrame.setSize(new java.awt.Dimension(500, 270));
        chartFrame.setVisible(true);

    }

    
    public static void main(final String[] args) {

    	new Jfree();
        

    }


	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		series.add(contador, 20 + oye.nextInt(10));
		contador++;
	}

}
