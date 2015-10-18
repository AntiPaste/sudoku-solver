package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Solver;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class SolveWorker extends SwingWorker<Void, Void> {
	private final SudokuGrid sudokuGrid;
	private final JLabel statusLabel;
	private final JButton solveButton;
	private final Solver solver;
	
	private boolean isCancelled = false;
	
	public SolveWorker(SudokuGrid sudokuGrid, JLabel statusLabel, JButton solveButton, Solver solver) {
		this.sudokuGrid = sudokuGrid;
		this.statusLabel = statusLabel;
		this.solveButton = solveButton;
		this.solver = solver;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		int found = 0;
		boolean solved = false;
		
		System.out.println(this.solver.isSolved());
		System.out.println(this.solver.getBoard().getTileAt(8, 8).getNumber());
		System.out.println(this.isCancelled);
		
		do {
			try {
				found = this.solver.solve();
				solved = this.solver.isSolved();
				
				System.out.println(this.solver.getBoard());
				System.out.println("Found: " + found);
			} catch (IllegalStateException e) {
				System.out.println(e.getStackTrace());
				throw e;
			}
			
			this.sudokuGrid.updateCells();

			try {
				Thread.sleep(500);
			} catch (Exception e) {}
		} while (!this.isCancelled && !solved && found > 0);
		
		String text;
		if (this.isCancelled) {
			text = "Idling";
			
			this.solver.reset();
			this.sudokuGrid.setBoard(this.solver.getBoard());
		} else if (this.solver.isSolved()) {
			text = "Solved";
		} else {
			text = "Unable to solve the puzzle. :(";
		}
		
		this.sudokuGrid.updateCells();
		
		this.statusLabel.setText(text);
		this.solveButton.setText("Solve!");
		return null;
	}
	
	@Override
	protected void done() {
		try {
			get();
		} catch (InterruptedException e) {
			System.out.println("SolveWorker interrupted");
			System.out.println(e.getMessage());
		} catch (ExecutionException e) {
			if (e.getMessage().startsWith("java.lang.IllegalStateException")) {
				this.statusLabel.setText(e.getMessage().substring(33));
				this.solveButton.setText("Solve!");
			}
		} catch (CancellationException e) {
			this.isCancelled = true;
		}
	}
}
