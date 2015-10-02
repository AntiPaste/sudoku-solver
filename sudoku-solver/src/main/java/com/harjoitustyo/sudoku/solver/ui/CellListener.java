package com.harjoitustyo.sudoku.solver.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CellListener implements MouseListener {
	private final SudokuGrid sudokuGrid;
	
	public CellListener(SudokuGrid sudokuGrid) {
		this.sudokuGrid = sudokuGrid;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		SudokuCell cell = (SudokuCell) e.getSource();
		SudokuCell selection = this.sudokuGrid.getSelected();
		
		if (selection != null && selection.equals(cell))
			cell = null;
		
		this.sudokuGrid.setSelected(cell);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
