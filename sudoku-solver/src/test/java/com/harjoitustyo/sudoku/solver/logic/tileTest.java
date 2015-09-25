package com.harjoitustyo.sudoku.solver.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

public class tileTest {
	@Test
	public void tilePrintsCorrectly() {
		Tile tile = new Tile(0, 0, 3);
		assertEquals(tile.toString(), "3");
	}
	
	@Test
	public void tileCanAddOnePossibility() {
		Tile tile = new Tile(0, 0, 3);
		tile.setPossibilities(new HashSet());
		
		Set<Integer> observed = tile.addPossibilities(6);
		Set<Integer> expected = new HashSet(Arrays.asList(6));
		
		assertEquals(expected, observed);
	}
	
	@Test
	public void tileCanAddManyPossibilities() {
		Tile tile = new Tile(0, 0, 3);
		tile.setPossibilities(new HashSet());
		
		Set<Integer> observed = tile.addPossibilities(6, 7, 8, 9);
		Set<Integer> expected = new HashSet(Arrays.asList(6, 7, 8, 9));
		
		assertEquals(expected, observed);
	}
	
	@Test
	public void tileCanRemoveOnePossibility() {
		Tile tile = new Tile(0, 0, 3);
		tile.setPossibilities(new HashSet(Arrays.asList(1, 2, 3)));
		
		Set<Integer> observed = tile.removePossibilities(1);
		Set<Integer> expected = new HashSet(Arrays.asList(2, 3));
		
		assertEquals(expected, observed);
	}
	
	@Test
	public void tileCanRemoveManyPossibilities() {
		Tile tile = new Tile(0, 0, 3);
		tile.setPossibilities(new HashSet(Arrays.asList(1, 2, 3)));
		
		Set<Integer> observed = tile.removePossibilities(2, 3);
		Set<Integer> expected = new HashSet(Arrays.asList(1));
		
		assertEquals(expected, observed);
	}
}
