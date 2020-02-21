package actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PageFileFilter extends FileFilter{

	@Override
	public boolean accept(File f) {
		return (f.isDirectory() || f.getName().toLowerCase().endsWith(".gpf"));
	}

	@Override
	public String getDescription() {
		return "Gerudok project files (*.gpf)";
	}
	
}
