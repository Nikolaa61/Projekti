package gerudok.model.workspace;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

public class KlipbordDeljenja implements ClipboardOwner{
	private static KlipbordDeljenja instance;
	
	private Clipboard clipboard = new Clipboard("Klipbord");
	
	public static KlipbordDeljenja getInstance() {
		if (instance == null) {
            instance = new KlipbordDeljenja();
		}
		return instance;		
	}
	
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) 
	{
	}

	public  Clipboard getClipboard() {
		return clipboard;
	}
}
