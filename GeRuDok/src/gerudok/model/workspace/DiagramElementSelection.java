package gerudok.model.workspace;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import elements.DiagramDevice;
import elements.DiagramElement;

public class DiagramElementSelection implements Transferable, ClipboardOwner, Serializable
{
	static public DataFlavor elementFlavor;
	transient private DataFlavor[] supportedFlavors = { elementFlavor };
	transient public ArrayList<DiagramDevice> diagramElements = new ArrayList<DiagramDevice>();

	public DiagramElementSelection(ArrayList<DiagramDevice> arrayList) 
	{
		diagramElements = arrayList;
		
		try 
		{
			elementFlavor = new DataFlavor(Class.forName("java.util.ArrayList"), "Elements");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException 
	{
		if (flavor.equals(elementFlavor))
			return (diagramElements);
		else
			throw new UnsupportedFlavorException(elementFlavor);
	}

	public DataFlavor[] getTransferDataFlavors() 
	{
		return supportedFlavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return flavor.equals(elementFlavor);
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) 
	{
	}
}
