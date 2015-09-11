package com.harjoitustyo.sudoku.solver.logic;

public class Board {
	public static final int BOARD_SIZE = 9;
	
	private Tile[][] tiles;
	
	public Board() {
		this.tiles = new Tile[Board.BOARD_SIZE][Board.BOARD_SIZE];
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				this.tiles[x][y] = new Tile(x, y);
			}
		}
	}
	
	public Tile getTileAt(int x, int y) {
		return this.tiles[x][y];
	}
	
	public void setTileAt(Tile tile, int x, int y) {
		this.tiles[x][y] = tile;
	}
	
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Tile tile = this.getTileAt(x, y);
				output.append(tile.toString());
			}
			
			output.append("\n");
		}
		
		return output.toString();
	}
}
