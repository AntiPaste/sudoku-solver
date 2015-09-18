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
	
	// Calculate the top left coordinate of the 3x3 square we are in
	// Hard-coded for 9x9 boards
	public static final Coordinate coordinateToSquare(int x, int y) {
		return new Coordinate(
				(x < 3 ? 0 : (x < 6 ? 3 : 6)),
				(y < 3 ? 0 : (y < 6 ? 3 : 6))
		);
	}
	
	public static final Coordinate coordinateToSquare(Coordinate coordinate) {
		return Board.coordinateToSquare(coordinate.x, coordinate.y);
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
		for (int y = 0; y < Board.BOARD_SIZE; y++) {
			for (int x = 0; x < Board.BOARD_SIZE; x++) {
				Tile tile = this.getTileAt(x, y);
				output.append(tile.toString());
				output.append(" ");
			}
			
			output.append("\n");
		}
		
		return output.toString();
	}
}
