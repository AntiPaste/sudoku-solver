package com.harjoitustyo.sudoku.solver.logic;

/**
 * This class contains the basic state and methods used to
 * inspect or manipulate a Sudoku board.
 * 
 * @author Kasper Koho
 */
public class Board {

	/**
	 * BOARD_SIZE defines the width and height of the board
	 */
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
	
	/**
	 * Method returns the top left coordinates of the 3x3 square that
	 * input coordinate (x, y) falls in
	 * 
	 * @note Hard-coded for 9x9 puzzle boards
	 * 
	 * @param x any x-coordinate within the 9x9 puzzle board
	 * @param y any y-coordinate within the 9x9 puzzle board
	 * @return top left coordinates of the 3x3 square that coordinate (x, y) falls in
	 */
		public static final Coordinate coordinateToSquare(int x, int y) {
		return new Coordinate(
				(x < 3 ? 0 : (x < 6 ? 3 : 6)),
				(y < 3 ? 0 : (y < 6 ? 3 : 6))
		);
	}
	
	/**
	 * Method returns the top left coordinates of the 3x3 square that
	 * input coordinate (coordinate.x, coordinate.y) falls in
	 * 
	 * @note Hard-coded for 9x9 puzzle boards
	 * 
	 * @param coordinate any (x, y) coordinate within the 9x9 puzzle board
	 * @return top left coordinates of the 3x3 square that coordinate (x, y) falls in
	 */
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
