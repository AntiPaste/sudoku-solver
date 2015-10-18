package com.harjoitustyo.sudoku.solver.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a single Sudoku board tile, it's state, and methods
 * used to inspect or manipulate the state.
 *
 * @author Kasper Koho
 */
public class Tile {
	public static final int EMPTY = 0;
	public static final List<Integer> ALL_NUMBERS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

	private int x;
	private int y;

	private int number;
	private boolean isGuess;

	private Set<Integer> possibilities;

	public Tile(int x, int y) {
		this(x, y, Tile.EMPTY);
	}

	public Tile(int x, int y, int number) {
		this.x = x;
		this.y = y;
		this.number = number;

		this.possibilities = new HashSet(Tile.ALL_NUMBERS);
		this.removePossibilities(this.number);
	}

	public Set<Integer> addPossibilities(int... numbers) {
		for (int number : numbers) {
			this.possibilities.add(number);
		}

		return this.possibilities;
	}

	public final Set<Integer> removePossibilities(int... numbers) {
		for (int number : numbers) {
			this.possibilities.remove(number);
		}

		return this.possibilities;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Set<Integer> getPossibilities() {
		return this.possibilities;
	}

	public void setPossibilities(Set<Integer> possibilities) {
		this.possibilities = possibilities;
	}
	
	public boolean isGuess() {
		return this.isGuess;
	}

	public void setIsGuess(boolean isGuess) {
		this.isGuess = isGuess;
	}

	@Override
	public String toString() {
		return (this.number == Tile.EMPTY ? "-" : String.valueOf(this.number));
	}
}
