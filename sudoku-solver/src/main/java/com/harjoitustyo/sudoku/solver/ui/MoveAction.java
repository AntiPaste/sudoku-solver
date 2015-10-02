package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Coordinate;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;

public class MoveAction extends AbstractAction {
	private final SudokuGrid sudokuGrid;
	private final String direction;
	
	public MoveAction(SudokuGrid sudokuGrid, String direction) {
		this.sudokuGrid = sudokuGrid;
		this.direction = direction;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		SudokuCell selected = this.sudokuGrid.getSelected();
		if (selected == null) return;
		
		int dx, dy;
		switch (this.direction) {
			case "up":
				dx = 0;
				dy = -1;
				break;
			case "down":
				dx = 0;
				dy = 1;
				break;
			case "left":
				dx = -1;
				dy = 0;
				break;
			case "right":
				dx = 1;
				dy = 0;
				break;
			default:
				dx = dy = 0;
		}
		
		List<SudokuCell> cells = this.sudokuGrid.getCells();
		Coordinate selectedCoordinate = selected.getCoordinate();
		
		for (SudokuCell cell : cells) {
			Coordinate coordinate = cell.getCoordinate();
			if (coordinate.x == selectedCoordinate.x + dx && coordinate.y == selectedCoordinate.y + dy) {
				this.sudokuGrid.setSelected(cell);
				break;
			}
		}
	}
}
