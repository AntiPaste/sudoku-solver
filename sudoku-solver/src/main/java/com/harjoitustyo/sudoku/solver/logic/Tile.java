package com.harjoitustyo.sudoku.solver.logic;

public class Tile {
	private int x;
	private int y;
	
	private int number;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile(int x, int y, int number) {
		this(x, y);
		this.number = number;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.number);
	}
}
