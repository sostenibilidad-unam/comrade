package Calibradores;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CustomXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;


public class Grafica {
	XYSeries series, ceros;
	int contador;
	JFreeChart chart;
	XYSeriesCollection xyDataset;
	ChartFrame chartFrame;
	String titulo;
	ArrayList<String> toolTips; 
	public Grafica(String title){
		titulo = title;
		series = new XYSeries("Datos");
		
		toolTips= new ArrayList<String>();
		CustomXYToolTipGenerator ttg = new CustomXYToolTipGenerator();
        ttg.addToolTipSeries(toolTips);
		xyDataset = new XYSeriesCollection(series);
		
		
		
        chart = ChartFactory.createXYLineChart(
            "Distancia con lo esperado",
            "X", 
            "Y", 
            xyDataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        
        
         XYPlot plot = chart.getXYPlot();
        //
         XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
         //renderer.setShapesVisible(true);
         //renderer.setShapesFilled(true);
         
         
         renderer.setSeriesStroke(0, new BasicStroke(3.0f));
               
        //XYItemRenderer trenderer = plot.getRenderer();
        renderer.setToolTipGenerator(ttg);
        
        ValueAxis ejeY = plot.getDomainAxis();
        ejeY.setRange(new Range(0, 1000));
        ValueAxis ejeX = plot.getRangeAxis();
        ejeX.setRange(new Range(0, 1.0));
        
        chartFrame = new ChartFrame(title, chart);
        
        chartFrame.setSize(new java.awt.Dimension(400, 270));
        chartFrame.setVisible(true);
        
        contador=0;
	}
	public void agrega(double este){
		contador++;
		series.add(contador, este);
	}
	public void pinta(Vector datos) {
		series.clear();
		xyDataset = new XYSeriesCollection(series);
		
        chart = ChartFactory.createXYLineChart(
            "Distancia con lo esperado",
            "X", 
            "Y", 
            xyDataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        chartFrame = new ChartFrame(titulo, chart);
        chartFrame.setSize(new java.awt.Dimension(400, 270));
        chartFrame.setVisible(true);
		for (int i=0; i<datos.size(); i++){
			series.add(i,((Double)datos.get(i)).doubleValue());
		}
		contador = datos.size();
		
	}
	public void setLocation(int x, int y) {
		chartFrame.setLocation(x, y);
		
		
	}
	public void setSize(int i, int j) {
		chartFrame.setSize(i, j);
		
	}
	public void addToolTip(String quien) {
		toolTips.add(quien);
		
	}
	public void dispose(){
		chartFrame.dispose();
	}
}
