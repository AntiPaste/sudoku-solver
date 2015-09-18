package com.harjoitustyo.sudoku.solver.logic;

import java.util.HashSet;
import java.util.Set;

public class Solver {
	private final Board board;
	
	public Solver(Board board) {
		this.board = board;
		this.refreshPossibilities();
	}
	
	public void solve() {
		int found = 0;
		
		do {
			found += this.checkSinglePossibilities();
		} while (!this.isSolved() && found > 0);
	}
	
	// Should this be in Solver? Maybe move to Board?
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
	
	// Should this be in Solver? Maybe move to Board?
	public final void refreshPossibilities() {
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
}
