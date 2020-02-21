package gerudok.model.workspace;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class DocumentSelection implements Transferable, ClipboardOwner{
	static public DataFlavor documentFlavor;
	private DataFlavor[] supportedFlavors = { documentFlavor };
	private Document document;
	
	public DocumentSelection(Document document) {
		
		this.document = document;
		
		try {
			documentFlavor = new DataFlavor(Class.forName("gerudok.model.workspace.Document"), "Dokument");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(documentFlavor))
			return (document);
		else
			throw new UnsupportedFlavorException(documentFlavor);
	}
	
	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}
	
	public boolean isDataFlavorSupported(DataFlavor flavor){
		return flavor.equals(documentFlavor);
	}
	
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
	
}
