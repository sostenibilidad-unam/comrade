package AsciiGrid;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class TxtFilter extends FileFilter{
	
	public boolean accept(File f) {
		boolean siono =false;
		if (f.isDirectory()) siono= true;
		if (f.getName().endsWith(".txt")) siono=true;
		return siono;
	}
	
	public String getDescription() {
		
		return "documentos de texto";
	}
	
}