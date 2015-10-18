package com.harjoitustyo.sudoku.solver;

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Parser;
import com.harjoitustyo.sudoku.solver.logic.Solver;
import com.harjoitustyo.sudoku.solver.ui.UserInterface;
import javax.swing.SwingUtilities;

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
	
	public static final String TEST =
			  "------123\n"
			+ "-------5-\n"
			+ "------7-9\n"
			+ "---------\n"
			+ "------8--\n"
			+ "---------\n"
			+ "---------\n"
			+ "---------\n"
			+ "--------8\n";
	
	private static final String EXPERT =
			"67-25--34\n" +
			"-3---1--2\n" +
			"--2-4---7\n" +
			"----2--9-\n" +
			"1-569--4-\n" +
			"------7--\n" +
			"---53--2-\n" +
			"3--4-----\n" +
			"-----9-6-";
	
	private static final String ARTO_INKALA =
			"8--------\n" +
			"--36-----\n" +
			"-7--9-2--\n" +
			"-5---7---\n" +
			"----457--\n" +
			"---1---3-\n" +
			"--1----68\n" +
			"--85---1-\n" +
			"-9----4--";
	
	public static void main(String[] args) {
		Board board = Parser.parseBoard(Main.ARTO_INKALA);
		Solver solver = new Solver(board);
		
		UserInterface ui = new UserInterface(board, solver);
		SwingUtilities.invokeLater(ui);
	}
}
