package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Tile;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class SudokuGrid extends JPanel {
	private Board board;
	private List<SudokuCell> cells;
	
	public SudokuGrid(Board board) {
		this.board = board;
		this.cells = new ArrayList();
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		for (int row = 0; row < Board.BOARD_SIZE; row++) {
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
				constraints.gridx = column;
				constraints.gridy = row;
				
				Tile tile = this.board.getTileAt(column, row);
				SudokuCell sudokuCell = new SudokuCell(tile);
				Border border = null;
				
				if (row < Board.BOARD_SIZE - 1) {
					if (column < Board.BOARD_SIZE - 1) {
						border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
					} else {
						border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
					}
				} else {
					if (column < Board.BOARD_SIZE - 1) {
						border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
					} else {
						border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
					}
				}
				
				sudokuCell.setBorder(border);
				
				this.cells.add(sudokuCell);
				this.add(sudokuCell, constraints);
			}
		}
	}
	
	public void updateCells() {
		System.out.println("Updating cells");
		for (SudokuCell cell : this.cells) {
			cell.updateNumber();
		}
	}
}
