package com.harjoitustyo.sudoku.solver.logic;

import org.junit.Test;
import static org.junit.Assert.*;

// NOTE: Tests for the solver are pretty hard to do
public class solverTest {
	@Test
	public void solverDoesNotThinkUnsolvedPuzzleIsSolved() {
		String input =
			  "-458-371-\n"
			+ "81-----24\n"
			+ "7-9---5-8\n"
			+ "---9-7---\n"
			+ "----6----\n"
			+ "---4-2---\n"
			+ "6-4---3-5\n"
			+ "32-----87\n"
			+ "-573-826-\n";
		
		Board board = Parser.parseBoard(input);
		Solver solver = new Solver(board);
		
		assertFalse(solver.isSolved());
	}
	
	@Test
	public void solverKnowsSolvedPuzzleIsSolved() {
		String input =
			  "245893716\n"
			+ "813576924\n"
			+ "769214538\n"
			+ "536987142\n"
			+ "492165873\n"
			+ "178432659\n"
			+ "684721395\n"
			+ "321659487\n"
			+ "957348261\n";
		
		Board board = Parser.parseBoard(input);
		Solver solver = new Solver(board);
		
		assertTrue(solver.isSolved());
	}
	
	@Test(expected=IllegalStateException.class)
	public void solverSeesIncorrectlySolvedPuzzle() {
		String input =
			  "345893716\n"
			+ "813576924\n"
			+ "769214538\n"
			+ "536987142\n"
			+ "492165873\n"
			+ "178432659\n"
			+ "684721395\n"
			+ "321659487\n"
			+ "957348261\n";
		
		Board board = Parser.parseBoard(input);
		Solver solver = new Solver(board);
		solver.isSolved();
	}
}
