package com.harjoitustyo.sudoku.solver.logic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Tile;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Branch
 */
public class boardTest {
	
	public Board board;
	
	public boardTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		this.board = new Board();
	}
	
	@After
	public void tearDown() {
	}

    // TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
	
	@Test
	public void boardIsInitialised() {
		Tile[][] tiles = this.board.getTiles();
		for (int x = 0; x < Board.BOARD_SIZE; x++) {
			for (int y = 0; y < Board.BOARD_SIZE; y++) {
				assertTrue(tiles[x][y] != null);
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
		
		assertTrue(count == (Board.BOARD_SIZE * Board.BOARD_SIZE));
	}
}
