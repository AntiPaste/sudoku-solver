package com.harjoitustyo.sudoku.solver.ui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

public class NumberListener implements ActionListener {
	private final SudokuGrid sudokuGrid;
	private final JLabel statusLabel;
	
	public NumberListener(SudokuGrid sudokuGrid, JLabel statusLabel) {
		this.sudokuGrid = sudokuGrid;
		this.statusLabel = statusLabel;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (this.statusLabel.getText().equals("Solving..."))
			return;
		
		int number = Integer.parseInt(((JButton) ae.getSource()).getText());
		SudokuCell cell = this.sudokuGrid.getSelected();
		
		if (cell == null) return;
		cell.setNumber(number);
	}
}
