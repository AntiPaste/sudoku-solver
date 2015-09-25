package com.harjoitustyo.sudoku.solver.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class contains methods used to solve a given Sudoku puzzle.
 * 
 * @author Kasper Koho
 */
public class Solver {
	private final Board board;
	
	public Solver(Board board) {
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
		
		return found;
	}
	
	/**
	 * Checks the state of the current puzzle to see if it is solved.
	 * 
	 * @note Should this be moved into Board?
	 * 
	 * @return boolean indicating the status of the puzzle (solved, not solved)
	 */
	public boolean isSolved() {
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				if (this.board.getTileAt(x, y).getNumber() == Tile.EMPTY) return false;
			}
		}
		
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				Coordinate square = Board.coordinateToSquare(x, y);
				Set<Integer> combination = new HashSet();
				
				// Check tiles that are in the same horizontal or vertical line,
				// or in the same 3x3 square as our tile, in a single loop.
				// Again, hard-coded for 9x9 boards
				for (int i = 0; i < 9; i++) {
					combination.add(this.board.getTileAt(i, y).getNumber());
					combination.add(this.board.getTileAt(x, i).getNumber());
					combination.add(this.board.getTileAt(square.x + (i % 3), square.y + (i / 3)).getNumber());
				}
				
				if (combination.size() != 9)
					throw new IllegalStateException("Puzzle is solved incorrectly.");
			}
		}
		
		return true;
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
				
				this.board.setTileAt(tile, x, y);
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
				if (possibilities.isEmpty()) throw new IllegalArgumentException(String.format("Found a tile which cannot be solved @ %d:%d", x, y));
				
				int number = possibilities.iterator().next(); // Possibilities cannot be empty
				
				tile.setNumber(number);
				this.board.setTileAt(tile, x, y);
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
}
