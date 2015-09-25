package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Solver;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class UserInterface implements Runnable {
	private JFrame frame;
	
	private Board board;
	private Solver solver;
	
	public UserInterface(Board board, Solver solver) {
		this.board = board;
		this.solver = solver;
	}
	
	@Override
	public void run() {
		this.frame = new JFrame("Sudoku Solver");
		this.frame.setPreferredSize(new Dimension(800, 600));
		
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		this.createComponents(this.frame.getContentPane());
		
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private void createComponents(Container container) {
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		JLabel status = new JLabel("Idling");
		JButton solve = new JButton("Solve!");
		SudokuGrid sudokuGrid = new SudokuGrid(this.board);
		
		SolveListener solveListener = new SolveListener(status, solve, sudokuGrid, this.solver);
		solve.addActionListener(solveListener);
		
		container.add(sudokuGrid);
		container.add(status);
		container.add(solve);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
