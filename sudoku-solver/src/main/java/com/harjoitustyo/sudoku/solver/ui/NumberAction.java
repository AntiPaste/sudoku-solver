package com.harjoitustyo.sudoku.solver.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JLabel;

public class NumberAction extends AbstractAction {
	private final SudokuGrid sudokuGrid;
	private final JLabel statusLabel;
	
	public NumberAction(SudokuGrid sudokuGrid, JLabel statusLabel) {
		this.sudokuGrid = sudokuGrid;
		this.statusLabel = statusLabel;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (this.statusLabel.getText().equals("Solving..."))
			return;
		
		int number = Integer.parseInt(ae.getActionCommand());
		
		SudokuCell cell = this.sudokuGrid.getSelected();
		if (cell == null) return;
		
		cell.setNumber(number);
	}

}
