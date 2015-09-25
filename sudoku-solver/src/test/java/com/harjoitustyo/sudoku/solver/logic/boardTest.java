package com.harjoitustyo.sudoku.solver.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

public class boardTest {
	
	public Board board;
	
	@Before
	public void setUp() {
		this.board = new Board();
	}
	
	@Test
	public void boardIsInitialised() {
		Tile[][] tiles = this.board.getTiles();
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				assertNotNull(tiles[x][y]);
			}
		}
	}
	
	@Test
	public void boardIsPrintedCorrectly() {
		Tile[][] tiles = new Tile[Board.BOARD_SIZE][Board.BOARD_SIZE];
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				tiles[x][y] = new Tile(x, y, 3);
			}
		}
		
		this.board.setTiles(tiles);
		
		int count = 0;
		String output = this.board.toString();
		for (int i = 0; i < output.length(); i++) {
			if (output.charAt(i) == '3') {
				count++;
			}
		}
		
		assertEquals(count, (Board.BOARD_SIZE * Board.BOARD_SIZE));
	}
	
	@Test
	public void boardXYCoordinateToSquareConversionWorks() {
		List<Coordinate> testCases = new ArrayList();
		testCases.add(new Coordinate(0, 0));
		testCases.add(new Coordinate(5, 5));
		testCases.add(new Coordinate(8, 8));
		testCases.add(new Coordinate(4, 7));
		
		List<Coordinate> expectedResults = new ArrayList();
		expectedResults.add(new Coordinate(0, 0));
		expectedResults.add(new Coordinate(3, 3));
		expectedResults.add(new Coordinate(6, 6));
		expectedResults.add(new Coordinate(3, 6));
		
		Iterator<Coordinate> iteratorTest = testCases.iterator();
		Iterator<Coordinate> iteratorExpected = expectedResults.iterator();
		
		while (iteratorTest.hasNext() && iteratorExpected.hasNext()) {
			Coordinate testCase = iteratorTest.next();
			Coordinate expectedResult = iteratorExpected.next();
			
			Coordinate square = Board.coordinateToSquare(testCase.x, testCase.y);
			assertEquals(square.x, expectedResult.x);
			assertEquals(square.y, expectedResult.y);
		}
		
		// F*** you pit
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				Coordinate test = Board.coordinateToSquare(x, y);
				assertEquals(test.x, (x < 3 ? 0 : (x < 6 ? 3 : 6)));
				assertEquals(test.y, (y < 3 ? 0 : (y < 6 ? 3 : 6)));
			}
		}
	}
	
	@Test
	public void boardCoordinateToSquareConversionWorks() {
		List<Coordinate> testCases = new ArrayList();
		testCases.add(new Coordinate(0, 0));
		testCases.add(new Coordinate(5, 5));
		testCases.add(new Coordinate(8, 8));
		testCases.add(new Coordinate(4, 7));
		
		List<Coordinate> expectedResults = new ArrayList();
		expectedResults.add(new Coordinate(0, 0));
		expectedResults.add(new Coordinate(3, 3));
		expectedResults.add(new Coordinate(6, 6));
		expectedResults.add(new Coordinate(3, 6));
		
		Iterator<Coordinate> iteratorTest = testCases.iterator();
		Iterator<Coordinate> iteratorExpected = expectedResults.iterator();
		
		while (iteratorTest.hasNext() && iteratorExpected.hasNext()) {
			Coordinate testCase = iteratorTest.next();
			Coordinate expectedResult = iteratorExpected.next();
			
			Coordinate square = Board.coordinateToSquare(new Coordinate(testCase.x, testCase.y));
			assertEquals(square.x, expectedResult.x);
			assertEquals(square.y, expectedResult.y);
		}
	}
}
