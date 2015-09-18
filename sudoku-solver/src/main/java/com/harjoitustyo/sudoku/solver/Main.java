package com.harjoitustyo.sudoku.solver;

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Parser;
import com.harjoitustyo.sudoku.solver.logic.Solver;

public class Main {
	public static final String SUDOKU =
			  "-458-371-\n"
			+ "81-----24\n"
			+ "7-9---5-8\n"
			+ "---9-7---\n"
			+ "----6----\n"
			+ "---4-2---\n"
			+ "6-4---3-5\n"
			+ "32-----87\n"
			+ "-573-826-\n";
	
	public static void main(String[] args) {
		Board board = Parser.parseBoard(Main.SUDOKU);
		Solver solver = new Solver(board);
		
		System.out.println(board);
		System.out.println("Solving...");
		
		solver.solve();
		
		if (solver.isSolved())
			System.out.println("Hurray, we solved the puzzle!");
		else
			throw new NullPointerException("Does not compute.");
	}
}
