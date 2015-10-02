package com.harjoitustyo.sudoku.solver.ui;

import com.harjoitustyo.sudoku.solver.logic.Board;
import com.harjoitustyo.sudoku.solver.logic.Solver;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

public class UserInterface implements Runnable {
	private JFrame frame;
	
	private final Board board;
	private final Solver solver;
	
	public UserInterface(Board board, Solver solver) {
		this.board = board;
		this.solver = solver;
	}
	
	public void revalidate() {
		this.frame.revalidate();
	}
	
	@Override
	public void run() {
		this.frame = new JFrame("Sudoku Solver");
		this.frame.setPreferredSize(new Dimension(800, 600));
		
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.createComponents(this.frame, this.frame.getContentPane());
		
		this.frame.pack();
		this.frame.setVisible(true);
	}
	
	private void createComponents(JFrame frame, Container container) {
		GridBagConstraints constraints = new GridBagConstraints();
		//container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setLayout(new GridBagLayout());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel status = new JLabel("Idling");
		status.setHorizontalAlignment(JLabel.CENTER);
		
		SudokuGrid sudokuGrid = new SudokuGrid(this, this.board);
		
		JButton solve = new JButton("Solve!");
		SolveListener solveListener = new SolveListener(status, solve, sudokuGrid, this.solver);
		solve.addActionListener(solveListener);
		
		JButton exit = new JButton("Exit");
		ExitListener exitListener = new ExitListener();
		exit.addActionListener(exitListener);
		
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipady = 30;
		constraints.insets = new Insets(0, 0, 75, 0);
		container.add(solve, constraints);
		
		constraints.weightx = 0.5;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.ipady = 30;
		constraints.insets = new Insets(75, 0, 0, 0);
		container.add(exit, constraints);
		
		constraints.weightx = 0.5;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.ipady = 0;
		constraints.insets = new Insets(30, 0, 0, 0);
		container.add(status, constraints);
		
		constraints.weightx = 0.5;
		constraints.weighty = 1.0;
		constraints.gridx = 2;
		constraints.gridy = 2;
		constraints.ipady = 0;
		constraints.insets = new Insets(0, 0, 0, 0);
		container.add(sudokuGrid, constraints);
		
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 2;
		constraints.gridy = 3;
		constraints.ipady = 0;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		JPanel numberButtons = new JPanel();
		numberButtons.setLayout(new GridLayout(2, 10));
		for (int number = 0; number <= 9; number++) {
			JButton numberButton = new JButton(Integer.toString(number));
			NumberListener numberListener = new NumberListener(sudokuGrid, status);
			
			numberButton.addActionListener(numberListener);
			numberButtons.add(numberButton);
		}
		
		container.add(numberButtons, constraints);
		
		JComponent pane = (JComponent) container;
		InputMap inputs = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actions = pane.getActionMap();
		
		for (int number = 0; number <= 9; number++) {
			inputs.put(KeyStroke.getKeyStroke(Integer.toString(number)), "insertNumber");
		}
		
		actions.put("insertNumber", new NumberAction(sudokuGrid, status));
		
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		inputs.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		
		actions.put("up", new MoveAction(sudokuGrid, "up"));
		actions.put("down", new MoveAction(sudokuGrid, "down"));
		actions.put("left", new MoveAction(sudokuGrid, "left"));
		actions.put("right", new MoveAction(sudokuGrid, "right"));
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}
