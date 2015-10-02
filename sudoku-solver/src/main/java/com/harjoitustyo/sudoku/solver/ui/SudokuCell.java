package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Coordinate;
import com.harjoitustyo.sudoku.solver.logic.Tile;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SudokuCell extends JPanel {
	private final Coordinate coordinate;
	
	private final Tile tile;
	private final JLabel number;
	private Color defaultBackground;
	
	public SudokuCell(Tile tile, Coordinate coordinate) {
		this.coordinate = coordinate;
		this.tile = tile;
		this.number = new JLabel(this.tile.toString());
		
		this.add(this.number);
	}
	
	public Coordinate getCoordinate() {
		return this.coordinate;
	}
	
	public int getNumber() {
		return (this.number.getText().equals("-") ? 0 : Integer.parseInt(this.number.getText()));
	}
	
	public void setNumber(int number) {
		this.tile.setNumber(number);
		this.updateNumber();
	}
	
	public void updateNumber() {
		this.number.setText(this.tile.toString());
	}
	
	public void applySelection() {
		this.defaultBackground = this.getBackground();
		this.setBackground(Color.CYAN);
	}
	
	public void clearSelection() {
		this.setBackground(this.defaultBackground);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
	}
}
