package commands;

import java.util.ArrayList;

import gui.MainWindow;



public class CommandManager {
		//lista koja predstavlja stek na kome se nalaze konkretne izvršene komande
		private ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();
		//pokazivač steka, sadrži redni broj komande za undo / redo operaciju
		private int currentCommand = 0;
		
		/*
		 * Dodaje novu komandu na stek i poziva izvršavanje komande
		 */
		public void addCommand(AbstractCommand command){
			while(currentCommand < commands.size())
				commands.remove(currentCommand);
			commands.add(command);
			doCommand();
		}
		
		/*
		 * Metoda koja poziva izvršavanje konkretne komande 
		 */
		public void doCommand(){
			if(currentCommand < commands.size()){
				commands.get(currentCommand++).doCommand();
				MainWindow.getInstance().getActionManager().getUndoAction().setEnabled(true);
			}
			if(currentCommand==commands.size()){
				MainWindow.getInstance().getActionManager().getRedoAction().setEnabled(false);
			}
		}

		/*
		 * Metoda koja poziva redo konkretne komande
		 */
		public void undoCommand(){
			if(currentCommand > 0){
				MainWindow.getInstance().getActionManager().getRedoAction().setEnabled(true);
				commands.get(--currentCommand).undoCommand();
			}
			if(currentCommand==0){
				MainWindow.getInstance().getActionManager().getUndoAction().setEnabled(false);
			}
		}

}
