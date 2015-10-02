package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Solver;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;

public class SolveListener implements ActionListener {
	private final JLabel statusLabel;
	private final JButton solveButton;
	private final SudokuGrid sudokuGrid;
	private final Solver solver;
	
	private SolveWorker worker;
	
	public SolveListener(JLabel statusLabel, JButton solveButton, SudokuGrid sudokuGrid, Solver solver) {
		this.statusLabel = statusLabel;
		this.solveButton = solveButton;
		this.sudokuGrid = sudokuGrid;
		this.solver = solver;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (this.solveButton.getText().equals("Solve!")) {
			this.solve();
		} else if (this.solveButton.getText().equals("Cancel")) {
			this.cancel();
		} else {
			throw new IllegalStateException("Solve button has invalid text");
		}
	}
	
	private void solve() {
		this.statusLabel.setText("Solving...");
		this.solveButton.setText("Cancel");
		
		System.out.println(this.solver.getBoard().getTileAt(8, 8).getNumber());
		this.worker = new SolveWorker(this.sudokuGrid, this.statusLabel, this.solveButton, this.solver);
		this.worker.execute();
	}
	
	private void cancel() {
		this.statusLabel.setText("Idling");
		this.solveButton.setText("Solve!");
		
		this.worker.cancel(true);
	}
}
