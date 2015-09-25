package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Solver;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class SolveListener implements ActionListener {
	private final JLabel statusLabel;
	private final JButton solveButton;
	private final SudokuGrid sudokuGrid;
	private final Solver solver;
	
	private SwingWorker<Void, Void> worker;
	
	public SolveListener(JLabel statusLabel, JButton solveButton, SudokuGrid sudokuGrid, Solver solver) {
		this.statusLabel = statusLabel;
		this.solveButton = solveButton;
		this.sudokuGrid = sudokuGrid;
		this.solver = solver;
		
		this.createWorker();
	}
	
	public final void createWorker() {
		this.worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				int found;
				
				do {
					found = solver.solve();
					sudokuGrid.updateCells();
			
					try {
						Thread.sleep(500);
					} catch (Exception e) {}
				} while (found > 0 && !solver.isSolved());
				
				if (solver.isSolved()) {
					statusLabel.setText("Solved!");
				} else {
					statusLabel.setText("Unable to solve the puzzle. :(");
				}
				
				return null;
			}
		};
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (this.solveButton.getText().equals("Solve!")) {
			this.solve();
		} else {
			this.cancel();
		}
		
	}
	
	private void solve() {
		this.statusLabel.setText("Solving...");
		this.solveButton.setText("Cancel");
		
		this.worker.execute();
	}
	
	private void cancel() {
		this.statusLabel.setText("Idling");
		this.solveButton.setText("Solve!");
		
		this.worker.cancel(true);
	}
}
