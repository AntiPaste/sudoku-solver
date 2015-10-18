package com.harjoitustyo.sudoku.solver.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains methods used to solve a given Sudoku puzzle.
 * 
 * @author Kasper Koho
 */
public class Solver {
	private Board board;
	private final Board original;
	
	/**
	 *
	 */
	public static final int FOUND_CONTRADICTION = 0;

	/**
	 *
	 */
	public static final int NO_CONTRADICTION = 1;

	/**
	 *
	 */
	public static final int SOLVED_CONTRADICTION = 2;
	
	public Solver(Board board) {
		this.board = board;
		this.original = this.board.clone();
		
		this.refreshPossibilities();
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * Resets the board to it's original state.
	 */
	public void reset() {
		Board board = this.original.clone();
		this.board = board;
		
		this.refreshPossibilities();
	}
	
	/**
	 * Performs one step of solving the Sudoku puzzle using different methods.
	 * 
	 * @return how many tiles were solved on this iteration
	 */
	public int solve() {
		int found = 0;
		
		found += this.checkSinglePossibilities();
		found += this.checkSquareSinglePossibilities();
		if (found > 0) return found;
		
		found += this.checkContradictions();
		return found;
	}
	
	/**
	 * Helper method for counting frequencies.
	 * 
	 * @param map map to alter
	 * @param key key to increment
	 */
	public void incrementKey(Map<Integer, Integer> map, Integer key) {
		if (!map.containsKey(key))
			map.put(key, 0);
		
		map.put(key, map.get(key) + 1);
	}
	
	/**
	 * Checks the state of the current puzzle to see if it is solved.
	 * 
	 * @note Should this be moved into Board?
	 * 
	 * @return boolean indicating the status of the puzzle (solved, not solved)
	 */
	public boolean isSolved() {
		boolean solved = true;
		
		for (int x = 0, y = 0; x < Board.BOARD_SIZE && y < Board.BOARD_SIZE; x++, y++) {
			Map<Integer, Integer> rows = new HashMap();
			Map<Integer, Integer> columns = new HashMap();
			
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
				int number = this.board.getTileAt(column, y).getNumber();
				if (number == Tile.EMPTY) solved = false;
				
				this.incrementKey(rows, number);
				
			}
			
			for (int row = 0; row < Board.BOARD_SIZE; row++) {
				int number = this.board.getTileAt(x, row).getNumber();
				if (number == Tile.EMPTY) solved = false;
				
				this.incrementKey(columns, number);
			}
			
			for (int number = 1; number <= 9; number++) {
				Integer frequency = rows.get(number);
				if (frequency != null && frequency > 1) {
					throw new IllegalStateException("Puzzle solved incorrectly.");
				}
				
				frequency = columns.get(number);
				if (frequency != null && frequency > 1) {
					throw new IllegalStateException("Puzzle solved incorrectly.");
				}
			}
		}
		
		for (int x = 0; x < Board.BOARD_SIZE; x += 3) {
			for (int y = 0; y < Board.BOARD_SIZE; y += 3) {
				Map<Integer, Integer> square = new HashMap();
				
				for (int xSquare = x; xSquare < (x + 3); xSquare++) {
					for (int ySquare = y; ySquare < (y + 3); ySquare++) {
						this.incrementKey(square, this.board.getTileAt(xSquare, ySquare).getNumber());
					}
				}
				
				for (int number = 1; number <= 9; number++) {
					Integer frequency = square.get(number);
					if (frequency != null && frequency > 1) {
						throw new IllegalStateException("Puzzle solved incorrectly.");
					}
				}
			}
		}
		
		return solved;
	}
	
	/**
	 * Refreshes the possibilities of each tile using different methods.
	 * 
	 * @note Should this be moved to Board?
	 */
	public final void refreshPossibilities() {
		this.removeBasicPossibilities();
	}
	
	/**
	 * Removes possibilities from each tile using only the basic rules of Sudoku,
	 * that is, there can be only one of each number on any given vertical or
	 * horizontal lines, or within a 3x3 square.
	 */
	public void removeBasicPossibilities() {
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				Tile tile = this.board.getTileAt(x, y);
				Coordinate squareCoordinate = Board.coordinateToSquare(x, y);
				
				// Check tiles that are in the same horizontal or vertical line,
				// or in the same 3x3 square as our tile, in a single loop.
				// Again, hard-coded for 9x9 boards
				for (int i = 0; i < 9; i++) {
					Tile horizontal = this.board.getTileAt(i, y);
					Tile vertical = this.board.getTileAt(x, i);
					Tile square = this.board.getTileAt(squareCoordinate.x + (i % 3), squareCoordinate.y + (i / 3));
					
					tile.removePossibilities(horizontal.getNumber(), vertical.getNumber(), square.getNumber());
				}
			}
		}
	}
	
	/**
	 * Checks each tile to see if there is only one possible number that fits
	 * into that tile.
	 * 
	 * @return how many tiles were solved with this iteration
	 */
	public int checkSinglePossibilities() {
		int found = 0;
		
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				Tile tile = this.board.getTileAt(x, y);
				if (tile.getNumber() != Tile.EMPTY) continue;
				
				Set<Integer> possibilities = tile.getPossibilities();
				if (possibilities.size() > 1) continue;
				if (possibilities.isEmpty()) throw new IllegalStateException(String.format("Found a tile which cannot be solved @ %d:%d", x + 1, y + 1));
				
				int number = possibilities.iterator().next(); // Possibilities cannot be empty
				
				tile.setNumber(number);
				this.refreshPossibilities();
				
				found++;
				
				System.out.println(String.format("Square @ %d:%d = %d, single possibility", x, y, number));
				System.out.println(this.board);
			}
		}
		
		return found;
	}
	
	/**
	 * Solves tiles by checking if a possible number occurs in a 3x3
	 * square only once.
	 * 
	 * @return how many tiles were solved with this iteration
	 */
	public int checkSquareSinglePossibilities() {
		int found = 0;
		
		for (int xSquare = 0; xSquare < Board.BOARD_SIZE; xSquare += 3) {
			for (int ySquare = 0; ySquare < Board.BOARD_SIZE; ySquare += 3) {
				List<Integer> frequencies = new ArrayList();
				
				for (int x = xSquare; x < xSquare + 3; x++) {
					for (int y = ySquare; y < ySquare + 3; y++) {
						Tile tile = this.board.getTileAt(x, y);
						if (tile.getNumber() != Tile.EMPTY) continue;
						
						frequencies.addAll(tile.getPossibilities());
					}
				}
				
				for (Integer number : Tile.ALL_NUMBERS) {
					if (Collections.frequency(frequencies, number) != 1) continue;
					
					SQUARE_LOOP:
					for (int x = xSquare; x < xSquare + 3; x++) {
						for (int y = ySquare; y < ySquare + 3; y++) {
							Tile tile = this.board.getTileAt(x, y);
							if (tile.getNumber() != Tile.EMPTY) continue;
							if (!tile.getPossibilities().contains(number)) continue;
							
							tile.setNumber(number);
							this.board.setTileAt(tile, x, y);
							this.refreshPossibilities();
							
							found++;
							System.out.println(String.format("Square @ %d:%d = %d, single square possibility", x, y, number));
							System.out.println(this.board);
							
							break SQUARE_LOOP;
						}
					}
				}
			}
		}
		
		return found;
	}
    
	/**
	 * Checks for contradictions that allow us to deduce the number of a tile.
	 * @return how many tiles were solved in this iteration
	 */
	public int checkContradictions() {
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				Tile tile = this.board.getTileAt(x, y);
				if (tile.getNumber() != Tile.EMPTY) continue;
				
				Set<Integer> possibilities = tile.getPossibilities();
				if (possibilities.size() != 2) continue;
				
				Iterator<Integer> iterator = possibilities.iterator();
				Board clone = this.board.clone();
				
				Integer firstGuess = iterator.next();
				Integer secondGuess = iterator.next();
                
				int contradiction = this.makeAGuess(clone, x, y, firstGuess);
				if (contradiction == Solver.FOUND_CONTRADICTION) {
					Tile solved = this.board.getTileAt(x, y);
					
					solved.setNumber(secondGuess);
					this.board.setTileAt(solved, x, y);
					this.refreshPossibilities();
					
					return 1;
				} else if (contradiction == Solver.SOLVED_CONTRADICTION) {
					Tile solved = this.board.getTileAt(x, y);
					
					solved.setNumber(firstGuess);
					this.board.setTileAt(solved, x, y);
					this.refreshPossibilities();
					
					return 1;
				}
				
				clone = this.board.clone();
				contradiction = this.makeAGuess(clone, x, y, secondGuess);
				if (contradiction == Solver.FOUND_CONTRADICTION) {
					Tile solved = this.board.getTileAt(x, y);
					
					solved.setNumber(firstGuess);
					this.board.setTileAt(solved, x, y);
					this.refreshPossibilities();
					
					return 1;
				} else if (contradiction == Solver.SOLVED_CONTRADICTION) {
					Tile solved = this.board.getTileAt(x, y);
					
					solved.setNumber(secondGuess);
					this.board.setTileAt(solved, x, y);
					this.refreshPossibilities();
					
					return 1;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * Guesses that the tile at coordinates (x, y) has a certain number.
	 * If exceptions happen, we have found a contradiction.
	 * 
	 * @param board Sudoku board
	 * @param x x-coordinate of the tile to guess
	 * @param y y-coordinate of the tile to guess
	 * @param number number that we should guess
	 * @return whether we found a contradiction or not
	 */
	public int makeAGuess(Board board, int x, int y, int number) {		
		Tile tile = board.getTileAt(x, y);
		tile.setIsGuess(true);
		tile.setNumber(number);
		board.setTileAt(tile, x, y);

		Solver solver = new Solver(board);

		int found;
		
		do {
			try {
				found = solver.solve();
			} catch (IllegalStateException e) {
				return Solver.FOUND_CONTRADICTION;
			}
		} while (found > 0);
		
		try {
			if (solver.isSolved()) {
				return Solver.SOLVED_CONTRADICTION;
			}
		} catch (IllegalStateException e) {
			return Solver.FOUND_CONTRADICTION;
		}
		
		return Solver.NO_CONTRADICTION;
	}
}
