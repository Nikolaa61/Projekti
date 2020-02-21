package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.UndoManager;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit.*;
import javax.swing.text.StyledEditorKit.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import elements.SlotType;
import elements.TextType;


import java.util.List;
import java.util.*;


public class TextWindow extends JFrame
{
	private JTextPane editorPane = new JTextPane();
	private JButton saveButton = new JButton("Save");
	private JScrollPane editorScrollPane = new JScrollPane(editorPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private UndoManager undoMgr = new UndoManager();
	private JComboBox<String> fontSizeComboBox__;
	private JComboBox<String> textAlignComboBox__;
	private JComboBox<String> fontFamilyComboBox__;
	private String pictureButtonName__;
	private File file__;
	private BufferedImage image = null; 
	enum BulletActionType {INSERT, REMOVE};
	enum NumbersActionType {INSERT, REMOVE};
	enum UndoActionType {UNDO, REDO};
	private static final String MAIN_TITLE = "My Editor - ";
	private static final int DEFAULT_FONT_SIZE = 18;
	private static final List<String> FONT_LIST = Arrays.asList(new String [] {"Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "Tahoma", "Times New Roman", "Verdana"});
	private static final String [] FONT_SIZES  = {"Font Size", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30"};
	private static final String [] TEXT_ALIGNMENTS = {"Text Align", "Left", "Center", "Right", "Justified"};
	private static final char BULLET_CHAR = '\u2022';
	private static final String BULLET_STR = new String(new char [] {BULLET_CHAR});
	private static final String BULLET_STR_WITH_SPACE = BULLET_STR + " ";
	private static final int BULLET_LENGTH = BULLET_STR_WITH_SPACE.length();
	private static final String NUMBERS_ATTR = "NUMBERS";
	private static final String ELEM = AbstractDocument.ElementNameAttribute;
	private static final String COMP = StyleConstants.ComponentElementName;
	
	// This flag checks true if the caret position within a bulleted para
		// is at the first text position after the bullet (bullet char + space).
		// Also see EditorCaretListener and BulletParaKeyListener.
		private boolean startPosPlusBullet__;

		// This flag checks true if the caret position within a numbered para
		// is at the first text position after the number (number + dot + space).
		// Alse see EditorCaretListener and NumbersParaKeyListener.		
		private boolean startPosPlusNum__;
	
	public TextWindow() {
		
		
		
		setLayout(new BorderLayout());
		editorPane.setDocument(getNewDocument());
		editorPane.addKeyListener(new BulletParaKeyListener());
		editorPane.addKeyListener(new NumbersParaKeyListener());
		editorPane.addCaretListener(new EditorCaretListener());
		
		add(editorScrollPane, BorderLayout.CENTER);
		
//		add(saveButton, BorderLayout.SOUTH);
		EditButtonActionListener editButtonActionListener = new EditButtonActionListener();
		
		JButton cutButton = new JButton(new CutAction());
		cutButton.setHideActionText(true);
		cutButton.setText("Cut");
		cutButton.addActionListener(editButtonActionListener);
		JButton copyButton = new JButton(new CopyAction());
		copyButton.setHideActionText(true);
		copyButton.setText("Copy");
		copyButton.addActionListener(editButtonActionListener);
		JButton pasteButton = new JButton(new PasteAction());
		pasteButton.setHideActionText(true);
		pasteButton.setText("Paste");
		pasteButton.addActionListener(editButtonActionListener);
		
		JButton boldButton = new JButton(new BoldAction());
		boldButton.setHideActionText(true);
		boldButton.setText("Bold");
		boldButton.addActionListener(editButtonActionListener);
		JButton italicButton = new JButton(new ItalicAction());
		italicButton.setHideActionText(true);
		italicButton.setText("Italic");
		italicButton.addActionListener(editButtonActionListener);
		JButton underlineButton = new JButton(new UnderlineAction());
		underlineButton.setHideActionText(true);
		underlineButton.setText("Underline");
		underlineButton.addActionListener(editButtonActionListener);
		
		textAlignComboBox__ = new JComboBox<String>(TEXT_ALIGNMENTS);
		textAlignComboBox__.setEditable(false);
		textAlignComboBox__.addItemListener(new TextAlignItemListener());
		
		fontSizeComboBox__ = new JComboBox<String>(FONT_SIZES);
		fontSizeComboBox__.setEditable(false);
		fontSizeComboBox__.addItemListener(new FontSizeItemListener());

		Vector<String> editorFonts = getEditorFonts();
		editorFonts.add(0, "Font Family");
		fontFamilyComboBox__ = new JComboBox<String>(editorFonts);
		fontFamilyComboBox__.setEditable(false);
		fontFamilyComboBox__.addItemListener(new FontFamilyItemListener());
		
		JButton colorButton = new JButton("Set Color");
		colorButton.addActionListener(new ColorActionListener());

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new UndoActionListener("UNDO"));
		JButton redoButton = new JButton("Redo");
		redoButton.addActionListener(new UndoActionListener("REDO"));
		
		JButton insertPictureButton = new JButton("Picture Insert");
		insertPictureButton.addActionListener(new PictureInsertActionListener());
		JButton deletePictureButton = new JButton("Picture Delete");
		deletePictureButton.addActionListener(new PictureDeleteActionListener());
		
		JButton bulletInsertButton = new JButton("Bullets Insert");
		bulletInsertButton.addActionListener(
				new BulletActionListener(BulletActionType.INSERT));
		JButton bulletRemoveButton = new JButton("Bullets Remove");
		bulletRemoveButton.addActionListener(
				new BulletActionListener(BulletActionType.REMOVE));
		
		JButton numbersInsertButton = new JButton("Numbers Insert");
		numbersInsertButton.addActionListener(
			new NumbersActionListener(NumbersActionType.INSERT));
		JButton numbersRemoveButton = new JButton("Numbers Remove");
		numbersRemoveButton.addActionListener(
			new NumbersActionListener(NumbersActionType.REMOVE));
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel1.add(cutButton);
		panel1.add(copyButton);
		panel1.add(pasteButton);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(boldButton);
		panel1.add(italicButton);
		panel1.add(underlineButton);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(colorButton);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(textAlignComboBox__);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(fontSizeComboBox__);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		panel1.add(fontFamilyComboBox__);
		panel1.add(new JSeparator(SwingConstants.VERTICAL));
		System.out.println("proba");
		
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel2.add(insertPictureButton);
		panel2.add(deletePictureButton);
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(bulletInsertButton);
		panel2.add(bulletRemoveButton);
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(numbersInsertButton);
		panel2.add(numbersRemoveButton);
		panel2.add(new JSeparator(SwingConstants.VERTICAL));
		panel2.add(undoButton);
		panel2.add(redoButton);
		
		JPanel toolBarPanel = new JPanel();
		toolBarPanel.setLayout(new BoxLayout(toolBarPanel, BoxLayout.PAGE_AXIS));
		toolBarPanel.add(panel1);
		toolBarPanel.add(panel2);
		
		add(toolBarPanel, BorderLayout.NORTH);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem newItem	= new JMenuItem("New");
		newItem.setMnemonic(KeyEvent.VK_N);
		newItem.addActionListener(new NewFileListener());
		JMenuItem openItem	= new JMenuItem("Open...");
		openItem.setMnemonic(KeyEvent.VK_O);
		openItem.addActionListener(new OpenFileListener());
		JMenuItem saveItem	= new JMenuItem("Save (...)");
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.addActionListener(new SaveFileListener());
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});

		fileMenu.add(newItem);
		fileMenu.addSeparator();
		fileMenu.add(openItem);
		fileMenu.add(saveItem);

		fileMenu.addSeparator();
		fileMenu.add(exitItem);		
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		editorPane.requestFocusInWindow();
		
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
			}
		});
		
		setTitle("Tekstualni Editor");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenD = tk.getScreenSize();
		
		setSize(900, 500);
		
		setResizable(false);
		setLocationRelativeTo(null);	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void setText(String string)
	{
		editorPane.setText(string);
	}
	
	private StyledDocument getNewDocument() {
		
		StyledDocument doc = new DefaultStyledDocument();
		doc.addUndoableEditListener(new UndoEditListener());
		return doc;
	}
	
	private StyledDocument getEditorDocument() {
		
		StyledDocument doc = (DefaultStyledDocument) editorPane.getDocument();
		return doc;
	}
	
	private Vector<String> getEditorFonts() {
		
		String [] availableFonts =
			GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Vector<String> returnList = new Vector<>();
	
		for (String font : availableFonts) {
	
			if (FONT_LIST.contains(font)) {

				returnList.add(font);
			}
		}
	
		return returnList;
	}
	
	private class EditButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			editorPane.requestFocusInWindow();
		}
	}
	
	private class ColorActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
		
			Color newColor = JColorChooser.showDialog(new JFrame(), "Izaberi boju", Color.BLACK);
			if (newColor == null) {
			
				editorPane.requestFocusInWindow();
				return;
			}
			
			SimpleAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setForeground(attr, newColor);
			editorPane.setCharacterAttributes(attr, false);
			editorPane.requestFocusInWindow();
		}
	}
	
	private class TextAlignItemListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {

			if ((e.getStateChange() != ItemEvent.SELECTED) ||
				(textAlignComboBox__.getSelectedIndex() == 0)) {
			
				return;
			}
			
			String alignmentStr = (String) e.getItem();			
			int newAlignment = textAlignComboBox__.getSelectedIndex() - 1;
			// New alignment is set based on these values defined in StyleConstants:
			// ALIGN_LEFT 0, ALIGN_CENTER 1, ALIGN_RIGHT 2, ALIGN_JUSTIFIED 3
			textAlignComboBox__.setAction(new AlignmentAction(alignmentStr, newAlignment));	
			textAlignComboBox__.setSelectedIndex(0); // initialize to (default) select
			editorPane.requestFocusInWindow();
		}
	} // TextAlignItemListener
	
	private class FontSizeItemListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {

			if ((e.getStateChange() != ItemEvent.SELECTED) ||
				(fontSizeComboBox__.getSelectedIndex() == 0)) {

				return;
			}
			
			String fontSizeStr = (String) e.getItem();			
			int newFontSize = 0;
			
			try {
				newFontSize = Integer.parseInt(fontSizeStr);
			}
			catch (NumberFormatException ex) {

				return;
			}

			fontSizeComboBox__.setAction(new FontSizeAction(fontSizeStr, newFontSize));	
			fontSizeComboBox__.setSelectedIndex(0); // initialize to (default) select
			editorPane.requestFocusInWindow();
		}
	} // FontSizeItemListener
	
	private class FontFamilyItemListener implements ItemListener {

		public void itemStateChanged(ItemEvent e) {

			if ((e.getStateChange() != ItemEvent.SELECTED) ||
				(fontFamilyComboBox__.getSelectedIndex() == 0)) {
			
				return;
			}
			
			String fontFamily = (String) e.getItem();
			fontFamilyComboBox__.setAction(new FontFamilyAction(fontFamily, fontFamily));	
			fontFamilyComboBox__.setSelectedIndex(0); // initialize to (default) select
			editorPane.requestFocusInWindow();
		}
	} // FontFamilyItemListener
	
	
	
	private class UndoEditListener implements UndoableEditListener {

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {

			undoMgr.addEdit(e.getEdit()); // remember the edit
		}
	}
	
	private class UndoActionListener implements ActionListener {
		
		private String type;
	
		public UndoActionListener(String type) {
		
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			switch (type) {
			
				case "UNDO":
					if (! undoMgr.canUndo()) {
				
						editorPane.requestFocusInWindow();
						return; // no edits to undo
					}

					undoMgr.undo();
					break;
					
				case "REDO":
					if (! undoMgr.canRedo()) {
				
						editorPane.requestFocusInWindow();
						return; // no edits to redo
					}

					undoMgr.redo();
			}

			editorPane.requestFocusInWindow();
		}
	} // UndoActionListener
	
	/*
	 * Action listener class for bullet insert and remove button actions.
	 */
	private class BulletActionListener implements ActionListener {

		private BulletActionType bulletActionType;
		
		public BulletActionListener(BulletActionType actionType) {
		
			bulletActionType = actionType;
		}

		/*
		 * Common routine for insert and remove bullet actions. This routine
		 * loops thru the selected text and inserts or removes a bullet.
		 * - For insert action: inserts a bullet at the beginning of each para
		 * of selected text. The paras already bulleted or numbered are ignored.
		 * - For remove bullet action: removes the bullet in case a para is 
		 * bulleted for the selected text.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
		
			String selectedText = editorPane.getSelectedText();
			
			if ((selectedText == null) || (selectedText.trim().isEmpty())) {

				editorPane.requestFocusInWindow();
				return;
			}
			
			StyledDocument doc = getEditorDocument();			
			Element paraEle = doc.getParagraphElement(editorPane.getSelectionStart());
			int paraEleStart = paraEle.getStartOffset();
			int paraEleEnd = 0;
			
			BULLETS_PARA_LOOP:
			do {
				paraEle = doc.getParagraphElement(paraEleStart);
				paraEleEnd = paraEle.getEndOffset();
				
				if ((paraEleEnd - paraEleStart) <= 1) { // empty line/para
				
					paraEleStart = paraEleEnd;
					paraEle = doc.getParagraphElement(paraEleStart);
					continue BULLETS_PARA_LOOP;
				}

				switch (bulletActionType) {
				
					case INSERT:
						if ((! isBulletedPara(paraEleStart)) &&
								(! isNumberedPara(paraEleStart))) {
				
							insertBullet(paraEleStart, paraEleStart);
						}
						
						break; // switch
				
					case REMOVE:
						if (isBulletedPara(paraEleStart)) {
				
							removeBullet(paraEleStart, BULLET_LENGTH);
						}
				}

				// Get the updated para element details after bulleting
				paraEle = doc.getParagraphElement(paraEleStart);
				paraEleEnd = paraEle.getEndOffset();

				paraEleStart = paraEleEnd;

			} while (paraEleEnd <= editorPane.getSelectionEnd());
			// BULLETS_PARA_LOOP
			
			editorPane.requestFocusInWindow();
		}
	}

	private boolean isBulletedPara(int paraEleStart) {
						
		if (getParaFirstCharacter(paraEleStart) == BULLET_CHAR) {
			
			return true;
		}
			
		return false;
	}
	
	private char getParaFirstCharacter(int paraEleStart) {
		
		String firstChar = "";
			
		try {
			firstChar = editorPane.getText(paraEleStart, 1);
		}
		catch (BadLocationException ex) {
			
			throw new RuntimeException(ex);
		}
			
		return firstChar.charAt(0);
	}
	
	private boolean isNumberedPara(int paraEleStart) {

		AttributeSet attrSet = getParaStartAttributes(paraEleStart);		
		Integer paraNum = (Integer) attrSet.getAttribute(NUMBERS_ATTR);

		if ((paraNum == null) || (! isFirstCharNumber(paraEleStart))) {

			return false;
		}

		return true;
	}
		
	private boolean isFirstCharNumber(int paraEleStart) {
			
		if (Character.isDigit(getParaFirstCharacter(paraEleStart))) {
			
			return true;
		}
		
		return false;
	}

	/*
	 * The insert bullet routine; inserts the bullet in the editor document. This
	 * routine is used from the insert action (ActionListener) as well as bullet
	 * para key press actions (keyPressed or keyReleased methods of KeyListener).
	 *
	 * The parameter insertPos is the position at which the bullet is to be
	 * inserted. The parameter attributesPos is the position from which the bullet
	 * is to get its attributes (like color, font, size, etc.). The two parameter
	 * values are derived differently for bullet insert and bullet para Enter
	 * key press actions. 
	 *
	 * Bullet insert action: the insertPos and attributesPos is the same,
	 * the paraEleStart.
	 * Enter key press: the insertPos is the current caret position of keyReleased(),
	 * and the attributesPos is the previous paraEleStart position from
	 * keyPressed() method.
	 */
	private void insertBullet(int insertPos, int attributesPos) {
								
		try {
			getEditorDocument().insertString(insertPos,
												BULLET_STR_WITH_SPACE,
												getParaStartAttributes(attributesPos));
		}
		catch(BadLocationException ex) {
				
			throw new RuntimeException(ex);
		}
	}

	private AttributeSet getParaStartAttributes(int pos) {
	
		StyledDocument doc = (DefaultStyledDocument) editorPane.getDocument();
		Element	charEle = doc.getCharacterElement(pos);
		return charEle.getAttributes();
	}
	
	/*
	 * The remove bullet routine; removes the bullet in the editor document. This
	 * routine is used from the delete action (ActionListener) as well as bullet
	 * para key press actions (keyPressed or keyRemoved methods of KeyListener).
	 * The keys include the Enter, Backspace, Delete keys.
	 *
	 * The parameter removePos is the start position and the length is the length
	 * of text to be removed. Length of characters removed is: BULLET_LENGTH
	 * or +1 (includes carriage return folowing the BULLET_LENGTH). The two
	 * parameter values are derived differently for bullet remove and bullet
	 * para key press actions. 
	 *
	 * Bullet remove action: removePos is paraEleStart and the BULLET_LENGTH.
	 * Delete key press: removePos is current caret pos of keyPressed() and
	 * the BULLET_LENGTH.
	 * Backspace key press: removePos is paraEleStart of keyPressed() and
	 * the length is BULLET_LENGTH.
	 * Enter key press: removePos is previous paraEleStart of keyPressed() and
	 * the length is BULLET_LENGTH + 1 (+1 includes CR).
	 */
	private void removeBullet(int removePos, int length) {

		try {
			getEditorDocument().remove(removePos, length);
		}
		catch(BadLocationException ex) {
				
			throw new RuntimeException(ex);
		}
	}
	
	/*
	 * Key listener class for key press and release actions within a bulleted
	 * para. The keys include Enter, Backspace, Delete and Left. The Enter press
	 * is implemented with both the keyPressed and keyReleased methods. The Delete,
	 * Backspace and Left key press is implemented within the keyPressed.
	 */
	public class BulletParaKeyListener implements KeyListener {
	
		// These two variables are derived in the keyPressed and are used in
		// keyReleased method.
		private String prevParaText_;
		private int prevParaEleStart_;
		
		// Identifies if a key is pressed in a bulleted para. 
		// This is required to distinguish from the numbered para.
		private boolean bulletedPara_; 


		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			bulletedPara_ = false;
			int pos = editorPane.getCaretPosition();
			
			if (! isBulletedParaForPos(pos)) {
			
				return;
			}
			
			Element paraEle = getEditorDocument().getParagraphElement(pos);
			int paraEleStart = paraEle.getStartOffset();
			
			switch (e.getKeyCode()) {
			
				case KeyEvent.VK_LEFT: // same as that of VK_KP_LEFT
				case KeyEvent.VK_KP_LEFT: int newPos = pos - (BULLET_LENGTH + 1);
										doLeftArrowKeyRoutine(newPos, startPosPlusBullet__);
										break;			
				case KeyEvent.VK_DELETE: doDeleteKeyRoutine(paraEle, pos);
										break;
				case KeyEvent.VK_BACK_SPACE: doBackspaceKeyRoutine(paraEle);
										break;
				case KeyEvent.VK_ENTER: getPrevParaDetails(pos);
			}

		} // keyPressed()
		
		private boolean isBulletedParaForPos(int caretPos) {

			Element paraEle = getEditorDocument().getParagraphElement(caretPos);
		
			if (isBulletedPara(paraEle.getStartOffset())) {
			
				return true;
			}
			
			return false;
		}
		
		// This method is used with Enter key press routine.
		// Two instance variable values are derived here and are used
		// in the keyReleased() method: prevParaEleStart_ and prevParaText_
		private void getPrevParaDetails(int pos) {
		
			pos =  pos - 1;
			
			if (isBulletedParaForPos(pos)) {
			
				bulletedPara_ = true;
				Element paraEle = getEditorDocument().getParagraphElement(pos);
				prevParaEleStart_ = paraEle.getStartOffset();
				prevParaText_ =
						getPrevParaText(prevParaEleStart_, paraEle.getEndOffset());
			}
		}
		
		// Delete key press routine within bulleted para.
		private void doDeleteKeyRoutine(Element paraEle, int pos) {

			int paraEleEnd = paraEle.getEndOffset();
			
			if (paraEleEnd > getEditorDocument().getLength()) {

				return; // no next para, end of document text
			}
				
			if (pos == (paraEleEnd - 1)) { // last char of para; -1 is for CR
				
				if (isBulletedParaForPos(paraEleEnd + 1)) {

					// following para is bulleted, remove
					removeBullet(pos, BULLET_LENGTH);
				}
				// else, not a bulleted para
				// delete happens normally (one char)
			}
		}
		
		// Backspace key press routine within a bulleted para.
		// Also, see EditorCaretListener.
		private void doBackspaceKeyRoutine(Element paraEle) {
			
			// In case the position of cursor at the backspace is just 
			// before the bullet (that is BULLET_LENGTH).
			if (startPosPlusBullet__) {

				removeBullet(paraEle.getStartOffset(), BULLET_LENGTH);
				startPosPlusBullet__ = false;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		
			if (! bulletedPara_) {

				return;
			}
				
			switch (e.getKeyCode()) {
		
				case KeyEvent.VK_ENTER: doEnterKeyRoutine();
										break;
			}
		}

		// Enter key press routine within a bulleted para.
		// Also, see keyPressed().
		private void doEnterKeyRoutine() {

			String prevParaText = prevParaText_;
			int prevParaEleStart = prevParaEleStart_;

			// Check if prev para with bullet has text					
			if (prevParaText.length() < 4) {
				
				// Para has bullet and no text, remove bullet+CR from para
				removeBullet(prevParaEleStart, (BULLET_LENGTH + 1));
				editorPane.setCaretPosition(prevParaEleStart);
				return;
			}
			// Prev para with bullet and text
			
			// Insert bullet for next para (current position), and
			// prev para attributes are used for this bullet.	
			insertBullet(editorPane.getCaretPosition(), prevParaEleStart); 
		}
		
	} // BulletParaKeyListener

	private String getPrevParaText(int prevParaEleStart, int prevParaEleEnd) {
			
		String prevParaText = "";
			
		try {
			prevParaText = getEditorDocument().getText(prevParaEleStart, 
											(prevParaEleEnd -  prevParaEleStart));
		}
		catch(BadLocationException ex) {
				
			throw new RuntimeException(ex);
		}
			
		return prevParaText;
	}
	
	/*
	 * Left arrow key press routine within a bulleted and numbered paras.
	 * Moves the cursor when caret is at position startPosPlusBullet__ or at
	 * startPosPlusNum__ for bullets or numbers respectively.
	 * Also see EditorCaretListener.
	 *
	 * The parameter startTextPos indicates if startPosPlusBullet__ or
	 * startPosPlusNum__. pos is the present caret postion.
	 */
	private void doLeftArrowKeyRoutine(int pos, boolean startTextPos) {
	
		if (! startTextPos) {
		
			return;
		}
		
		// Check if this is start of document
		Element paraEle =
				getEditorDocument().getParagraphElement(editorPane.getCaretPosition());
		final int newPos = (paraEle.getStartOffset() == 0) ? 0 : pos;
		
		// Position the caret in an EDT, otherwise the caret is
		// positioned at one less position than intended.
		SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
			
				editorPane.setCaretPosition(newPos);
			}
		});
	}

	/*
	 * This listener is used with the bulleted and numbered para actions.
	 * The bulleted item's bullet is made of bullet + space. The cursor (caret)
	 * is not allowed to position at the bullet para's first position and at 
	 * the space after the bullet. This listener controls the cursor position
	 * in such cases; the cursor jumps/moves to the after bullet+space position
	 * (indicated by startPosPlusBullet__ boolean instance variable).
	 *
	 * Also, the backspace and left-arrow key usage requires startPosPlusBullet__
	 * to perform the routines (see doLeftArrowKeyRoutine() and BulletParaKeyListener).
	 *
	 * This is also similar for numbered paras (see startPosPlusNum__ and
	 * NumbersParaKeyListener).
	 */
	private class EditorCaretListener implements CaretListener {
	
		@Override
		public void caretUpdate(CaretEvent e) {

			startPosPlusBullet__ = false;
			startPosPlusNum__ = false;
			Element paraEle = 
				getEditorDocument().getParagraphElement(editorPane.getCaretPosition());
			int paraEleStart = paraEle.getStartOffset();
	
			if (isBulletedPara(paraEleStart)) {

				if (e.getDot() == (paraEleStart + BULLET_LENGTH)) {

					startPosPlusBullet__ = true;
				}
				else if (e.getDot() < (paraEleStart + BULLET_LENGTH)) {
			
					editorPane.setCaretPosition(paraEleStart + BULLET_LENGTH);
				}
				else {
					// continue
				}
			}
			else if (isNumberedPara(paraEleStart)) {
			
				int numLen = getNumberLength(paraEleStart);

				if (e.getDot() < (paraEleStart + numLen)) {
			
					editorPane.setCaretPosition(paraEleStart + numLen);
				}
				else if (e.getDot() == (paraEleStart + numLen)) {

					startPosPlusNum__ = true;
				}
				else {
					// continue
				}
			}
			else {
				// not a bulleted or numbered para
			}
		}
	}
	
	/*
	 * Returns the numbered para's number length. This length includes
	 * the number + dot + space. For example, the text "12. A Numbered para..."
	 * has the number length of 4.
	 */
	private int getNumberLength(int paraEleStart) {
	
		Integer num = getParaNumber(paraEleStart);
		int len = num.toString().length() + 2; // 2 = dot + space after number
		return len;
	}
	
	private Integer getParaNumber(int paraEleStart) {
		
		AttributeSet attrSet = getParaStartAttributes(paraEleStart);		
		Integer paraNum = (Integer) attrSet.getAttribute(NUMBERS_ATTR);
		return paraNum;
	}

	/*
	 * Action listener class for number insert and remove button actions.
	 */
	private class NumbersActionListener implements ActionListener {

		private NumbersActionType numbersActionType;
		private int n;
	
		public NumbersActionListener(NumbersActionType actionType) {
		
			numbersActionType = actionType;
		}

		/*
		 * Common routine for insert and remove numbers actions. This routine
		 * loops thru the selected text and inserts or removes a number.
		 * - For insert action: inserts a number at the beginning of each para
		 * of selected text. The paras already bulleted or numbered are ignored.
		 *  Note that the numbering always starts from 1.
		 * - For remove action: removes the number in case a para is numbered
		 * for the selected text.
		 */		
		@Override
		public void actionPerformed(ActionEvent e) {
		
			StyledDocument doc = getEditorDocument();
			String selectedText = editorPane.getSelectedText();
			
			if ((selectedText == null) || (selectedText.trim().isEmpty())) {

				editorPane.requestFocusInWindow();
				return;
			}
			
			Element paraEle = doc.getParagraphElement(editorPane.getSelectionStart());
			int paraEleStart = paraEle.getStartOffset();
			int paraEleEnd = 0;
			boolean firstPara = true;
			
			NUMBERS_PARA_LOOP:
			do {
				paraEle = doc.getParagraphElement(paraEleStart);
				paraEleEnd = paraEle.getEndOffset();
				
				if ((paraEleEnd - paraEleStart) <= 1) { // empty line
				
					if (firstPara) {
					
						firstPara = false;
						n = 0;
					}

					paraEleStart = paraEleEnd;
					paraEle = doc.getParagraphElement(paraEleStart);
					continue NUMBERS_PARA_LOOP;
				}

				switch (numbersActionType) {
				
					case INSERT:
					
						if (isBulletedPara(paraEleStart)) {
						
							break; // switch
						}
					
						if (firstPara) {
					
							firstPara = false;
							n = 0;
						}
						
						if (isNumberedPara(paraEleStart)) {
				
							// remove any existing number
							removeNumber(paraEleStart, getNumberLength(paraEleStart));
						}
					
						if (! isNumberedPara(paraEleStart)) {
				
							Integer nextN = new Integer(++n);
							insertNumber(paraEleStart, paraEleStart, nextN);
						}
						
						break; // switch
				
					case REMOVE:
					
						if (isNumberedPara(paraEleStart)) {
				
							removeNumber(paraEleStart, getNumberLength(paraEleStart));
						}
				}

				// Get the updated para element details after numbering
				paraEle = doc.getParagraphElement(paraEleStart);
				paraEleEnd = paraEle.getEndOffset();

				paraEleStart = paraEleEnd;

			} while (paraEleEnd <= editorPane.getSelectionEnd());
			// NUMBERS_PARA_LOOP

			editorPane.requestFocusInWindow();
		}
	}
	
	/*
	 * The insert number routine; inserts the number in the editor document. This
	 * routine is used from the insert action (ActionListener) as well as number
	 * para key press actions (keyPressed or keyReleased methods of KeyListener).
	 *
	 * The parameter insertPos is the position at which the number is to be
	 * inserted. The parameter attributesPos is the position from which the number
	 * is to get its attributes (like color, font, size, etc.). The two parameter
	 * values are derived differently for the insert and the number para key press
	 * actions. The patameter num is the number being inserted.
	 *
	 * Number insert action: the insertPos and attributesPos is the same,
	 * the paraEleStart.
	 * Enter key press: the insertPos is the current caret position of keyReleased(),
	 * and the attributesPos is the previous paraEleStart position from
	 * keyPressed() method.
	 */
	private void insertNumber(int insertPos, int attributesPos, Integer num) {

		try {
			getEditorDocument().insertString(insertPos,
								getNumberString(num),
								getNumbersAttributes(attributesPos, num));
		}
		catch(BadLocationException ex) {

			throw new RuntimeException(ex);
		}
	}

	private String getNumberString(Integer nextNumber) {
		
		return new String(nextNumber.toString() + "." + " ");
	}
		
	private AttributeSet getNumbersAttributes(int paraEleStart, Integer number) {
		
		AttributeSet attrs1 = getParaStartAttributes(paraEleStart);
		SimpleAttributeSet attrs2 = new SimpleAttributeSet(attrs1);
		attrs2.addAttribute(NUMBERS_ATTR, number);
		return attrs2;
	}	

	/*
	 * The remove number routine; removes the number in the editor document. This
	 * routine is used from the delete action (ActionListener) as well as the number
	 * para key press actions (keyPressed or keyRemoved methods of KeyListener).
	 * The keys include the Enter, Backspace, Delete keys.
	 *
	 * The parameter removePos is the start position and the length is the length
	 * of text to be removed. Length of characters removed is derived from the
	 * method getNumberLength() or +1 (includes carriage return folowing the
	 * number length). The two parameter values are derived differently for
	 * number remove action and number para key press actions. 
	 *
	 * Number remove action: removePos is paraEleStart and the length from
	 * the method getNumberLength().
	 * Delete key press: removePos is current caret pos of keyPressed() and
	 * the length from the method getNumberLength().
	 * Backspace key press: removePos is paraEleStart of keyPressed() and
	 * the length from the method getNumberLength().
	 * Enter key press: removePos is previous paraEleStart of keyPressed() and
	 * the length from the method getNumberLength() + 1 (+1 includes CR).
	 */
	private void removeNumber(int removePos, int length) {
				
		try {
			getEditorDocument().remove(removePos, length);
		}
		catch(BadLocationException ex) {
				
			throw new RuntimeException(ex);
		}
	}
	
	/*
	 * Key listener class for key press and release actions within a numbered
	 * para. The keys include Enter, Backspace, Delete and Left. The Enter press
	 * is implemented with both the keyPressed and keyReleased methods. The Delete,
	 * Backspace and Left key press is implemented within the keyPressed.
	 *
	 * This also includes key press actions (backspace, enter and delete) for
	 * the text selected within the numbered paras.
	 */
	public class NumbersParaKeyListener implements KeyListener {
	
		// These two variables are derived in the keyPressed and are used in
		// keyReleased method.
		private String prevParaText_;
		private int prevParaEleStart_;
		
		// Identifies if a key is pressed in a numbered para.
		// This is required to distinguish from the bulleted para.
		private boolean numberedPara_; 


		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		
			String selectedText = editorPane.getSelectedText();
			
			if ((selectedText == null) || (selectedText.trim().isEmpty())) {

				// continue, processing key press without any selected text
			}
			else {
				// text is selected within numbered para and a key is pressed
				doReplaceSelectionRoutine();
				return;
			}
			
			numberedPara_ = false;
			int pos = editorPane.getCaretPosition();
			
			if (! isNumberedParaForPos(pos)) {
			
				return;
			}
			
			Element paraEle = getEditorDocument().getParagraphElement(pos);
			int paraEleStart = paraEle.getStartOffset();
			
			switch (e.getKeyCode()) {
			
				case KeyEvent.VK_LEFT: // same as that of VK_KP_LEFT
				case KeyEvent.VK_KP_LEFT: int newPos = pos - 
													(getNumberLength(paraEleStart) + 1);
										doLeftArrowKeyRoutine(newPos, startPosPlusNum__);
										break;			
				case KeyEvent.VK_DELETE: doDeleteKeyRoutine(paraEle, pos);
										break;
				case KeyEvent.VK_BACK_SPACE: doBackspaceKeyRoutine(paraEle);
										break;
				case KeyEvent.VK_ENTER: getPrevParaDetails(pos);
										break;
			}

		} // keyPressed()

		private boolean isNumberedParaForPos(int caretPos) {

			Element paraEle = getEditorDocument().getParagraphElement(caretPos);
		
			if (isNumberedPara(paraEle.getStartOffset())) {
			
				return true;
			}
			
			return false;
		}
		
		/*
		 * Routine for processing selected text with numbered paras
		 * after pressing Enter, Backspace or Delete keys, and the
		 * paste insert replacing the selected text.
		 */
		private void doReplaceSelectionRoutine() {
			
			// Get selection start and end para details.
			// Check if there are numbered paras at top and bottom
			// of the selection. Re-number if needed i.e., when selection
			// is replaced in the middle of numbered paras or at the top
			// items of the numbered paras.
			
			StyledDocument doc = getEditorDocument();			
			Element topParaEle = doc.getParagraphElement(editorPane.getSelectionStart());
			Element bottomParaEle = doc.getParagraphElement(editorPane.getSelectionEnd());

			int bottomParaEleStart = bottomParaEle.getStartOffset();			
			int bottomParaEleEnd = bottomParaEle.getEndOffset();
			
			// No numbered text at bottom, no processing required -or-
			// no next para after selection end (end of document text).
			if ((! isNumberedPara(bottomParaEleStart)) ||
					(bottomParaEleEnd > doc.getLength())) {

				return;
			}
			
			// Check if para following the selection end is numbered or not.
			Element paraEle = doc.getParagraphElement(bottomParaEleEnd + 1);
			int paraEleStart = paraEle.getStartOffset();
			
			if (! isNumberedPara(paraEleStart)) {
			
				return;
			}
			
			// Process re-numbering

			Integer numTop = getParaNumber(topParaEle.getStartOffset());
			
			if (numTop != null) {
				
				// There are numbered items above the removed para, and
				// there are numbered items following the removed para;
				// bottom numbers start from numTop + 1.
				doNewNumbers(paraEleStart, numTop);
			}
			else { 
				// numTop == null
				// There are no numbered items above the removed para, and
				// there are numbered items following the removed para;
				// bottom numbers start from 1.
				doNewNumbers(paraEleStart, 0);
			}

		} // doReplaceSelectionRoutine()
		
		/*
		 * Common routine to arrive at new numbers and replace the previous
		 * ones after the following actions within numbered para list:
		 * - Enter, Delete, Backspace key press.
		 * - Delete, Backspace and paste-insert selected text.
		 */
		private void doNewNumbers(int nextParaEleStart, Integer newNum) {
	
			StyledDocument doc = getEditorDocument();
			Element nextParaEle = doc.getParagraphElement(nextParaEleStart);
			boolean nextParaIsNumbered = true;

			NUMBERED_PARA_LOOP:
			while (nextParaIsNumbered) {

				Integer oldNum = getParaNumber(nextParaEleStart);
				newNum++;				
				replaceNumbers(nextParaEleStart, oldNum, newNum);

				nextParaIsNumbered = false;
				
				// Get following para details after number is replaced for a para

				int nextParaEleEnd = nextParaEle.getEndOffset();
				int nextParaPos = nextParaEleEnd + 1;
				
				if (nextParaPos > doc.getLength()) {

					break NUMBERED_PARA_LOOP; // no next para, end of document text
				}
				
				nextParaEle = doc.getParagraphElement(nextParaPos);
				nextParaEleStart = nextParaEle.getStartOffset();
				nextParaIsNumbered = isNumberedPara(nextParaEleStart);			
			}
			// NUMBERED_PARA_LOOP
			
		} // doNewNumbers()

		private void replaceNumbers(int nextParaEleStart, Integer prevNum,
									Integer newNum) {
				
			try {
				((DefaultStyledDocument) getEditorDocument()).replace(
													nextParaEleStart,
													getNumberString(prevNum).length(), 
													getNumberString(newNum), 
								getNumbersAttributes(nextParaEleStart, newNum));
			}
			catch(BadLocationException ex) {
				
				throw new RuntimeException(ex);
			}
		}	
	
		// Delete key press routine within a numbered para.
		private void doDeleteKeyRoutine(Element paraEle, int pos) {

			int paraEleEnd = paraEle.getEndOffset();
			
			if (paraEleEnd > getEditorDocument().getLength()) {

				return; // no next para, end of document text
			}
				
			if (pos == (paraEleEnd - 1)) { // last char of para; -1 is for CR
			
				Element nextParaEle =
						getEditorDocument().getParagraphElement(paraEleEnd + 1);
				int nextParaEleStart = nextParaEle.getStartOffset();
				
				if (isNumberedPara(nextParaEleStart)) {

					removeNumber(pos, getNumberLength(nextParaEleStart));
					doReNumberingForDeleteKey(paraEleEnd + 1);
				}
				// else, not a numbered para
				// delete happens normally (one char)
			}
		}

		private void doReNumberingForDeleteKey(int delParaPos) {
	
			// Get para element details where delete key is pressed
			StyledDocument doc = getEditorDocument();
			Element paraEle = doc.getParagraphElement(delParaPos);
			int paraEleStart = paraEle.getStartOffset();
			int paraEleEnd = paraEle.getEndOffset();

			// Get bottom para element details
			Element bottomParaEle = doc.getParagraphElement(paraEleEnd + 1);
			int bottomParaEleStart = bottomParaEle .getStartOffset();

			// In case bottom para is not numbered or end of document,
			// no re-numbering is required.
			if ((paraEleEnd > doc.getLength()) || 
					(! isNumberedPara(bottomParaEleStart))) {
			
				return;
			}
			
			Integer n = getParaNumber(paraEleStart);
			doNewNumbers(bottomParaEleStart, n);	
		}

		// Backspace key press routine within a numbered para.
		// Also, see EditorCaretListener.
		private void doBackspaceKeyRoutine(Element paraEle) {
			
			// In case the position of cursor at the backspace is just after
			// the number: remove the number and re-number the following ones.
			if (startPosPlusNum__) {

				int startOffset = paraEle.getStartOffset();
				removeNumber(startOffset, getNumberLength(startOffset));
				doReNumberingForBackspaceKey(paraEle, startOffset);				
				startPosPlusNum__ = false;
			}
		}
		
		private void doReNumberingForBackspaceKey(Element paraEle, int paraEleStart) {
			
			// Get bottom para element and check if numbered.
			StyledDocument doc = getEditorDocument();		
			Element bottomParaEle = doc.getParagraphElement(paraEle.getEndOffset() + 1);
			int bottomParaEleStart = bottomParaEle.getStartOffset();
		
			if (! isNumberedPara(bottomParaEleStart)) {
		
				return; // there are no numbers following this para, and
						// no re-numbering required.
			}
		
			// Get top para element and number
			
			Integer numTop = null;
			
			if (paraEleStart == 0) {
			
				// beginning of document, no top para exists
				// before the document start; numTop = null
			}
			else {	
				Element topParaEle = doc.getParagraphElement(paraEleStart - 1);
				numTop = getParaNumber(topParaEle.getStartOffset());
			}
		
			if (numTop == null) {
		
				// There are no numbered items above the removed para, and
				// there are numbered items following the removed para;
				// bottom numbers start from 1.
				doNewNumbers(bottomParaEleStart, 0);
			}
			else { 
				// numTop != null
				// There are numbered items above the removed para, and
				// there are numbered items following the removed para;
				// bottom numbers start from numTop + 1.
				doNewNumbers(bottomParaEleStart, numTop);
			}
		}
		
		// This method is used with Enter key press routine.
		// Two instance variable values are derived here and are used
		// in the keyReleased() method: prevParaEleStart_ and prevParaText_
		private void getPrevParaDetails(int pos) {
		
			pos =  pos - 1;
			
			if (isNumberedParaForPos(pos)) {
			
				numberedPara_ = true;
				Element paraEle = getEditorDocument().getParagraphElement(pos);
				prevParaEleStart_ = paraEle.getStartOffset();
				prevParaText_ =
						getPrevParaText(prevParaEleStart_, paraEle.getEndOffset());
			}
		}
		
//		private class PictureInsertActionListener implements ActionListener {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				File pictureFile = choosePictureFile();
//				
//				if (pictureFile == null) {
//				
//					editorPane.requestFocusInWindow();
//					return;
//				}
//				
//				ImageIcon icon = new ImageIcon(pictureFile.toString());			
//				JButton picButton = new JButton(icon);
//				picButton.setBorder(new LineBorder(Color.WHITE));
//				picButton.setMargin(new Insets(0,0,0,0));
//				picButton.setAlignmentY(.9f);
//				picButton.setAlignmentX(.9f);
//				picButton.addFocusListener(new PictureFocusListener());
//				picButton.setName("PICTURE_ID_" + new Random().nextInt());
//				editorPane.insertComponent(picButton);
//				editorPane.requestFocusInWindow();
//			}
//			
//			private File choosePictureFile() {
//			
//				JFileChooser chooser = new JFileChooser();
//				FileNameExtensionFilter filter = new FileNameExtensionFilter(
//									"PNG, JPG & GIF Images", "png", "jpg", "gif");
//				chooser.setFileFilter(filter);
//				
//				if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
//				
//					return chooser.getSelectedFile();
//				}
//				else {
//					return null;
//				}
//			}
//		} // PictureInsertActionListener

		private class PictureFocusListener implements FocusListener {

			@Override
			public void focusGained(FocusEvent e) {

				JButton button = (JButton) e.getComponent();
				button.setBorder(new LineBorder(Color.GRAY));
				pictureButtonName__ = button.getName();
			}
			
			@Override
			public void focusLost(FocusEvent e) {

				((JButton) e.getComponent()).setBorder(new LineBorder(Color.WHITE));
			}
		}

		private class PictureDeleteActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				StyledDocument doc = getEditorDocument();
				ElementIterator iterator = new ElementIterator(doc);
				Element element;
				
				while ((element = iterator.next()) != null) {
				
					AttributeSet attrs = element.getAttributes();
				
					if (attrs.containsAttribute(ELEM, COMP)) {

						JButton button = (JButton) StyleConstants.getComponent(attrs);
					
						if (button.getName().equals(pictureButtonName__)) {

							try {
								doc.remove(element.getStartOffset(), 1); // length = 1
							}
							catch (BadLocationException ex_) {
				
								throw new RuntimeException(ex_);
							}
						}
					}
				}
		
				editorPane.requestFocusInWindow();
				pictureButtonName__ = null;
			}
		} // PictureDeleteActionListener
		
		@Override
		public void keyReleased(KeyEvent e) {
		
			if (! numberedPara_) {

				return;
			}
		
			switch (e.getKeyCode()) {
			
				case KeyEvent.VK_ENTER: doEnterKeyRoutine();
										break;
			}
		}
		
		// Enter key press routine within a numbered para.
		// Also, see keyPressed().
		private void doEnterKeyRoutine() {
			
			String prevParaText = prevParaText_;
			int prevParaEleStart = prevParaEleStart_;					
			int len = getNumberLength(prevParaEleStart) + 1; // +1 for CR

			// Check if prev para with numbers has text					
			if (prevParaText.length() == len) {
				
				// Para has numbers and no text, remove number from para
				removeNumber(prevParaEleStart, len);		
				editorPane.setCaretPosition(prevParaEleStart);
				return;
			}
			// Prev para with number and text,			
			// insert number for new para (current position)
			Integer num = getParaNumber(prevParaEleStart);
			num++;
			insertNumber(editorPane.getCaretPosition(), prevParaEleStart, num);
			
			// After insert, check for numbered paras following the newly
			// inserted numberd para; and re-number those paras.
			
			// Get newly inserted number para details
			StyledDocument doc = getEditorDocument();	
			Element newParaEle = doc.getParagraphElement(editorPane.getCaretPosition());
			int newParaEleEnd = newParaEle.getEndOffset();

			if (newParaEleEnd > doc.getLength()) {

				return; // no next para, end of document text
			}

			// Get next para (following the newly inserted one) and
			// re-number para only if already numered.
			Element nextParaEle = doc.getParagraphElement(newParaEleEnd + 1);
			int nextParaEleStart = nextParaEle.getStartOffset();
			
			if (isNumberedPara(nextParaEleStart)) {

				doNewNumbers(nextParaEleStart, num);
			}

		} // doEnterKeyRoutine()
		
	} // NumbersParaKeyListener
	
	private class NewFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			initEditorAttributes();
			editorPane.setDocument(getNewDocument());
			file__= null;
			setFrameTitleWithExtn("New file");
		}
		
		private void initEditorAttributes() {
		
			AttributeSet attrs1 = editorPane.getCharacterAttributes();
			SimpleAttributeSet attrs2 = new SimpleAttributeSet(attrs1);
			attrs2.removeAttributes(attrs1);
			editorPane.setCharacterAttributes(attrs2, true);
		}
	}
	
	private class OpenFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			file__ = chooseFile();
			
			if (file__ == null) {
			
				return;
			}
			
			readFile(file__);
			setFrameTitleWithExtn(file__.getName());
		}
		
		private File chooseFile() {
		
			JFileChooser chooser = new JFileChooser();
			
			if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
			
				return chooser.getSelectedFile();
			}
			else {
				return null;
			}
		}
		
		private void readFile(File file) {
	
			StyledDocument doc = null;
	
			try (InputStream fis = new FileInputStream(file);
					ObjectInputStream ois = new ObjectInputStream(fis)) {
			
				doc = (DefaultStyledDocument) ois.readObject();
			}
			catch (FileNotFoundException ex) {

				JOptionPane.showMessageDialog(new JFrame(), "Input file was not found!");
				return;
			}
			catch (ClassNotFoundException | IOException ex) {

				throw new RuntimeException(ex);
			}
			
			editorPane.setDocument(doc);
			doc.addUndoableEditListener(new UndoEditListener());
			applyFocusListenerToPictures(doc);
		}
		
		private void applyFocusListenerToPictures(StyledDocument doc) {

			ElementIterator iterator = new ElementIterator(doc);
			Element element;
			
			while ((element = iterator.next()) != null) {
			
				AttributeSet attrs = element.getAttributes();
			
				if (attrs.containsAttribute(ELEM, COMP)) {

					JButton picButton = (JButton) StyleConstants.getComponent(attrs);
					picButton.addFocusListener(new PictureFocusListener());
				}
			}
		}
	}
	private void setFrameTitleWithExtn(String titleExtn) {

		this.setTitle(MAIN_TITLE + titleExtn);
	}
	private class SaveFileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			if (file__ == null) {
		
				file__ = chooseFile();
			
				if (file__ == null) {
			
					return;
				}
			}
			
			DefaultStyledDocument doc = (DefaultStyledDocument) getEditorDocument();
			
			try (OutputStream fos = new FileOutputStream(file__);
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				
				oos.writeObject(doc);
			}
			catch (IOException ex) {

				throw new RuntimeException(ex);
			}
			
			setFrameTitleWithExtn(file__.getName());
		}

		private File chooseFile() {
		
			JFileChooser chooser = new JFileChooser();
			
			if (chooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {

				return chooser.getSelectedFile();
			}
			else {
				return null;
			}
		}
	}
	private class PictureFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

			JButton button = (JButton) e.getComponent();
			button.setBorder(new LineBorder(Color.GRAY));
			pictureButtonName__ = button.getName();
		}
		
		@Override
		public void focusLost(FocusEvent e) {

			((JButton) e.getComponent()).setBorder(new LineBorder(Color.WHITE));
		}
	}
	private class PictureInsertActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			File pictureFile = choosePictureFile();
			
			if (pictureFile == null) {
			
				editorPane.requestFocusInWindow();
				return;
			}
			image = new BufferedImage(963, 640, 
                    BufferedImage.TYPE_INT_ARGB);
			try {
				image = ImageIO.read(pictureFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			ImageIcon icon = new ImageIcon(image);			
			JLabel picLabel = new JLabel(icon);
			picLabel.setBorder(new LineBorder(Color.WHITE));
			picLabel.setAlignmentY(.9f);
			picLabel.setAlignmentX(.9f);
			picLabel.addFocusListener(new PictureFocusListener());
			picLabel.setName("PICTURE_ID_" + new Random().nextInt());
			getContentPane().add(picLabel, BorderLayout.CENTER);
			editorPane.insertComponent(picLabel);
			editorPane.requestFocusInWindow();
			AffineTransform at = AffineTransform.getScaleInstance(200, 200);
			Graphics2D g = image.createGraphics();
			g.drawRenderedImage(image, at);
		}
		
		private File choosePictureFile() {
		
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"PNG, JPG & GIF Images", "png", "jpg", "gif");
			chooser.setFileFilter(filter);
			
			if (chooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
			
				return chooser.getSelectedFile();
			}
			else {
				return null;
			}
		}
	} // PictureInsertActionListener


	private class PictureDeleteActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			StyledDocument doc = getEditorDocument();
			ElementIterator iterator = new ElementIterator(doc);
			Element element;
			
			while ((element = iterator.next()) != null) {
			
				AttributeSet attrs = element.getAttributes();
			
				if (attrs.containsAttribute(ELEM, COMP)) {

					JButton button = (JButton) StyleConstants.getComponent(attrs);
				
					if (button.getName().equals(pictureButtonName__)) {

						try {
							doc.remove(element.getStartOffset(), 1); // length = 1
						}
						catch (BadLocationException ex_) {
			
							throw new RuntimeException(ex_);
						}
					}
				}
			}
	
			editorPane.requestFocusInWindow();
			pictureButtonName__ = null;
		}
	} // PictureDeleteActionListener
	public BufferedImage getScaledInstance(BufferedImage img,
	            int targetWidth,
	            int targetHeight,
	            Object hint,
	            boolean higherQuality){
		int type = (img.getTransparency() == Transparency.OPAQUE) ?
		BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = (BufferedImage)img;
		int w, h;
	if (higherQuality) {
		// Use multi-step technique: start with original size, then
		// scale down in multiple passes with drawImage()
		// until the target size is reached
		w = img.getWidth();
		h = img.getHeight();
	} else {
		// Use one-step technique: scale directly from original
		// size to target size with a single drawImage() call
		w = targetWidth;
		h = targetHeight;
	}
	
	do {
		if (higherQuality && w > targetWidth) {
		w /= 2;
		if (w < targetWidth) {
			w = targetWidth;
		}
	}
	
	if (higherQuality && h > targetHeight) {
		h /= 2;
		if (h < targetHeight) {
			h = targetHeight;
		}
	}
	
	BufferedImage tmp = new BufferedImage(w, h, type);
	Graphics2D g2 = tmp.createGraphics();
	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
	//        g2.setRenderingHint(RenderingHints.KEY_DITHERING, hint);
	g2.drawImage(ret, 0, 0, w, h, null);
	g2.dispose();
	
	ret = tmp;
	} while (w != targetWidth || h != targetHeight);
	
	return ret;
}
}
