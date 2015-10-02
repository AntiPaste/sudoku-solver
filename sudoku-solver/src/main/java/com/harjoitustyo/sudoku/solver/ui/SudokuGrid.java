package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Coordinate;
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
	private final UserInterface ui;
	private Board board;
	private List<SudokuCell> cells;
	
	private SudokuCell selected;
	
	public SudokuGrid(UserInterface ui, Board board) {
		this.ui = ui;
		
		this.setLayout(new GridBagLayout());
		this.setBoard(board);
	}
	
	public final void generateFromBoard() {
		GridBagConstraints constraints = new GridBagConstraints();
		this.cells = new ArrayList();
		this.removeAll();
		this.revalidate();
		
		for (int row = 0; row < Board.BOARD_SIZE; row++) {
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
				constraints.gridx = column;
				constraints.gridy = row;
				
				Border border;
				Tile tile = this.board.getTileAt(column, row);
				
				CellListener cellListener = new CellListener(this);
				SudokuCell sudokuCell = new SudokuCell(tile, new Coordinate(column, row));
				sudokuCell.addMouseListener(cellListener);
				
				if (row < Board.BOARD_SIZE - 1) {
					if (column < Board.BOARD_SIZE - 1) {
						// top and left border
						border = new MatteBorder((row != 0 && row % 3 == 0 ? 3 : 1), (column != 0 && column % 3 == 0 ? 3 : 1), 0, 0, Color.GRAY);
					} else {
						border = new MatteBorder((row != 0 && row % 3 == 0 ? 3 : 1), 1, 0, 1, Color.GRAY);
					}
				} else {
					if (column < Board.BOARD_SIZE - 1) {
						border = new MatteBorder(1, (column != 0 && column % 3 == 0 ? 3 : 1), 1, 0, Color.GRAY);
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
	
	public final void setBoard(Board board) {
		this.board = board;
		this.generateFromBoard();
		this.revalidate();
		
		for (SudokuCell cell : this.cells) {
			cell.revalidate();
		}
		
		this.ui.revalidate();
	}
	
	public SudokuCell getSelected() {
		return this.selected;
	}
	
	public void setSelected(SudokuCell cell) {
		if (this.selected != null)
			this.selected.clearSelection();
		
		this.selected = cell;
		
		if (this.selected != null)
			this.selected.applySelection();
	}
	
	public List<SudokuCell> getCells() {
		return this.cells;
	}
	
	public void updateCells() {
		for (SudokuCell cell : this.cells) {
			cell.updateNumber();
		}
	}
}
