package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Tile;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SudokuCell extends JPanel {
	private Tile tile;
	private JLabel number;
	
	public SudokuCell(Tile tile) {
		this.tile = tile;
		this.number = new JLabel(this.tile.toString());
		
		this.add(this.number);
	}
	
	public void updateNumber() {
		System.out.println("Updating");
		this.number.setText(this.tile.toString());
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
	}
}
