package Calibradores;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import AsciiGrid.AsciiGridReader;


public class PintaGrid {
	int alto, ancho;
	int[] enArray;
	 public JFrame fr;
	 int size;
	 int aumento=1;
	 int numeroDeColumnas;
	 int numeroDeRenglones;
	 int[][] gridAumentado;
	private int[][] arrayGrid;
	private int[] arrayRGB;
	int red;
	int green;
	int blue;
	private BufferedImage b_image;
	private int[][] rgbRandom;
	 
	public PintaGrid(int ancho, int alto){
		this.alto = alto;
		this.ancho = ancho;
		size =  alto * ancho;
		enArray = new int[size];
		fr = new JFrame("visor de grids");
		
	}
	
	public PintaGrid(File gridFile, String titulo, int aumento){
		this.aumento = aumento;
		fr = new JFrame(titulo);
		AsciiGridReader gridReader = new AsciiGridReader(gridFile);
		numeroDeColumnas = gridReader.numeroDeColumnas;
		numeroDeRenglones = gridReader.numeroDeRenglones;
		arrayGrid = new int[numeroDeColumnas][numeroDeRenglones];
		ancho=numeroDeColumnas * aumento;
		alto=numeroDeRenglones * aumento;
		gridAumentado = new int[ancho+1][alto+1];
		
		
		size =  alto * ancho ;
		enArray = new int[size];
		
		arrayRGB = new int[size];
		//fr = new JFrame("visor de grids");
		
		arrayGrid = gridReader.getIntArray();
		
		pinta(arrayGrid);
		gridReader.close();
	}
	public void pintaConCalle(int[][] unOutput) {
		fr.setGlassPane(new MyGlass());
		fr.getGlassPane().setVisible(true);
		pinta(unOutput);
		
		
	}
	public void pintaRandom(int[][] grid){
		 // estos fors son para multiplicar el tamaño del grid por el aumento deseado para el
		// despliegue en la pantalla
		int maximo =0;
		
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				maximo=Math.max(grid[i][j],maximo); 
			}
				
		}
		rgbRandom = new int[maximo+1][4];
		Random oye = new Random();
		for (int o=1;o<=maximo;o++){
			for (int m=1;m<=3;m++){
				rgbRandom[o][m]=oye.nextInt(255);
			}
			
		}
		
		
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				for (int k=1;k<=aumento;k++){
					for (int l=1;l<=aumento;l++){
						gridAumentado[((i-1)*aumento) + k ][((j-1)*aumento) + l]=grid[i][j];
					}
				}
			}
		}
		
		int i=0;
		int alpha = 255; // non-transparent
		//File f = new File ("c:/elpaso/elbueno.jpg");
		//BufferedImage bi = new BufferedImage(ancho+1 , alto+1, BufferedImage.TYPE_INT_RGB);
		
		
		
		for (int k=1;k<=alto;k++){
			for(int l=1;l<=ancho;l++){
				int valor_l_k=gridAumentado[l][k];
				
				int rgb = (alpha << 24) |  (rgbRandom[valor_l_k][1] << 16) |  (rgbRandom[valor_l_k][2] << 8 ) | rgbRandom[valor_l_k][2];
				//bi.setRGB(l, k, rgb);
				arrayRGB[i++]=rgb;
			}
	
		}
	
		
		
//		Image fImage = jp.createImage ( new MemoryImageSource (ancho, alto, arrayRGB, 0, ancho));
		
		
//		try {
//			ImageIO.write (bi , "jpeg", f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		b_image = new BufferedImage (ancho, alto, BufferedImage.TYPE_INT_ARGB);
		b_image.setRGB (0, 0, ancho, alto, arrayRGB, 0, ancho);
		B_ImagePanel imp= new B_ImagePanel(b_image);
		fr.setSize(ancho+8,alto+30);
	   	
	   	
		//System.out.println("Intentando agregar ic");
	   	fr.getContentPane().removeAll();
	   	fr.getContentPane().add(imp);
	   	//imp.paint(fImage.getGraphics());
	   	fr.setVisible(true);
	}
	public void pinta(int[][] grid){
		
        // estos fors son para multiplicar el tamaño del grid por el aumento deseado para el
		// despliegue en la pantalla
		for (int i=1;i<=numeroDeColumnas;i++){
			for (int j=1;j<=numeroDeRenglones;j++){
				for (int k=1;k<=aumento;k++){
					for (int l=1;l<=aumento;l++){
						gridAumentado[((i-1)*aumento) + k ][((j-1)*aumento) + l]=grid[i][j];
					}
				}
			}
		}
		
		int i=0;
		int alpha = 255; // non-transparent
		//File f = new File ("c:/elpaso/elbueno.jpg");
		//BufferedImage bi = new BufferedImage(ancho+1 , alto+1, BufferedImage.TYPE_INT_RGB);
		
		for (int k=1;k<=alto;k++){
			for(int l=1;l<=ancho;l++){
				int valor_l_k=gridAumentado[l][k];
				//aqui se define el color en rgb para cada valor del grid
				if (valor_l_k==-9999){
					red=0;
					green=0;
					blue=0;
					
				}else if(valor_l_k==0){
//					red=190;
//					green=190;
//					blue=140;
					red=0;
					green=0;
					blue=0;
				}else if(valor_l_k==1){
					red=255;
					green=204;
					blue=0;
				}else if(valor_l_k==10){
					red=255;
					green=153;
					blue=51;
					
				}else if(valor_l_k==100){
					red=204;
					green=51;
					blue=0;
				}else{
					red=0;
					green=0;
					blue=0;
				}
				int rgb = (alpha << 24) |  (red << 16) |  (green << 8 ) | blue;
				//bi.setRGB(l, k, rgb);
				arrayRGB[i++]=rgb;
			}
	
		}
	
		
		
//		Image fImage = jp.createImage ( new MemoryImageSource (ancho, alto, arrayRGB, 0, ancho));
		
		
//		try {
//			ImageIO.write (bi , "jpeg", f);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		b_image = new BufferedImage (ancho, alto, BufferedImage.TYPE_INT_ARGB);
		b_image.setRGB (0, 0, ancho, alto, arrayRGB, 0, ancho);
		B_ImagePanel imp= new B_ImagePanel(b_image);
		fr.setSize(ancho+8,alto+30);
	   	
	   	
		//System.out.println("Intentando agregar ic");
	   	fr.getContentPane().removeAll();
	   	fr.getContentPane().add(imp);
	   	//imp.paint(fImage.getGraphics());
	   	fr.setVisible(true);
	}
	public void setLocation(int x, int y) {
		fr.setLocation(x, y);
		
		
	}
	public void pinta(File gridInicialFile){
		AsciiGridReader gridReader = new AsciiGridReader(gridInicialFile);
		pinta(gridReader.getIntArray() );
		gridReader.close();
		
	}
	public static void main(String[] args) {
		
		AsciiGridReader mijo = new AsciiGridReader("escoge grid");
		System.out.println("columnas = " + mijo.numeroDeColumnas + " renglones = " + mijo.numeroDeRenglones);
		int[][] ejemplo;
		ejemplo = mijo.getIntArray();
		System.out.println("la ultima celda " + ejemplo[mijo.numeroDeColumnas][mijo.numeroDeRenglones]);
		
		
		
		PintaGrid oyeVentana = new PintaGrid(new File("topilejo/dificultad.txt"), "dificultad",5);
		
		oyeVentana.pintaRandom(ejemplo);

	}
	 class B_ImagePanel extends JPanel {
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		BufferedImage bufImage; 

	   
	 public B_ImagePanel(BufferedImage image) {
		super();
		bufImage = image;
	}

	   /** Draw the image on the panel. **/
	   public void paintComponent (Graphics g) {
	     super.paintComponent (g);
	     if (bufImage != null)
	         g.drawImage (bufImage, 0, 0, this );
	   } // makeImage

	 } // class GrayBufImagePanel
	public void dispose() {
		fr.dispose();
		
	}

	
}

