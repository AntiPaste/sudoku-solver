package com.harjoitustyo.sudoku.solver.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Tile {
	public static final int EMPTY = 0;
	
	private int x;
	private int y;
	
	private int number;
	
	private Set<Integer> possibilities;
	
	public Tile(int x, int y) {
		this(x, y, Tile.EMPTY);
	}
	
	public Tile(int x, int y, int number) {
		this.x = x;
		this.y = y;
		this.number = number;
		
		this.possibilities = new HashSet(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		this.removePossibilities(this.number);
	}
	
	public Set<Integer> addPossibilities(int... numbers) {
		for (int number : numbers)
			this.possibilities.add(number);
		
		return this.possibilities;
	}
	
	public final Set<Integer> removePossibilities(int... numbers) {
		for (int number : numbers)
			this.possibilities.remove(number);
		
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
	
	@Override
	public String toString() {
		return (this.number == Tile.EMPTY ? "-" : String.valueOf(this.number));
	}
}
