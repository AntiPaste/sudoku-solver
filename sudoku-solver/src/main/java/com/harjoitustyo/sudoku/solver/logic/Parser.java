package com.harjoitustyo.sudoku.solver.logic;

/**
 * This class parses inputs and outputs sensible formats,
 * such as classes used by the program.
 * 
 * @author Kasper Koho
 */
public class Parser {

	/**
	 * Method returns a class representation from the string
	 * representation of a Sudoku board.
	 * 
	 * @param input string representation of a Sudoku board
	 * @return class representation of the Sudoku board
	 */
	public static final Board parseBoard(String input) {
		String[] rows = input.split("\n");
		
		if (rows.length != Board.BOARD_SIZE) {
			throw new IllegalArgumentException(String.format("Puzzle contains too many rows for a %dx%d board.", Board.BOARD_SIZE, Board.BOARD_SIZE));
		}
		
		Board board = new Board();
		Tile[][] tiles = new Tile[Board.BOARD_SIZE][Board.BOARD_SIZE];
		
		for (int y = 0; y < rows.length; y++) {
			String row = rows[y];
			
			if (row.length() > Board.BOARD_SIZE) {
				throw new IllegalArgumentException(String.format("Puzzle contains too many columns for a %dx%d board.", Board.BOARD_SIZE, Board.BOARD_SIZE));
			}
			
			for (int x = 0; x < row.length(); x++) {
				char character = row.charAt(x);
				
				if (character != '-' && !Character.isDigit(character)) {
					throw new IllegalArgumentException(String.format("Illegal character %c found in puzzle.", character));
				}
				
				Tile tile = (character == '-' ? new Tile(x, y) : new Tile(x, y, Character.getNumericValue(character)));
				tiles[x][y] = tile;
			}
		}
		
		board.setTiles(tiles);
		return board;
	}
}
