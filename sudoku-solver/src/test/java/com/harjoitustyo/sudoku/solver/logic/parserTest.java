package com.harjoitustyo.sudoku.solver.logic;

import org.junit.Test;
import static org.junit.Assert.*;

public class parserTest {
	@Test(expected=IllegalArgumentException.class)
	public void parserChecksTooFewRows() {
		StringBuilder input = new StringBuilder();
		String row = new String(new char[Board.BOARD_SIZE]).replace("\0", "-");
		
		for (int i = 0; i < Board.BOARD_SIZE - 1; i++) {
			input.append(row);
			input.append("\n");
		}
		
		Parser.parseBoard(input.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parserChecksTooManyRows() {
		StringBuilder input = new StringBuilder();
		String row = new String(new char[Board.BOARD_SIZE]).replace("\0", "-");
		
		for (int i = 0; i < Board.BOARD_SIZE + 1; i++) {
			input.append(row);
			input.append("\n");
		}
		
		Parser.parseBoard(input.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parserChecksTooFewColumns() {
		StringBuilder input = new StringBuilder();
		String row = new String(new char[Board.BOARD_SIZE]).replace("\0", "-");
		
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			input.append(row);
			
			if (i % Board.BOARD_SIZE == (Board.BOARD_SIZE / 3))
				input.delete(0, input.length() - 1);
			
			input.append("\n");
		}
		
		Parser.parseBoard(input.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parserChecksTooManyColumns() {
		StringBuilder input = new StringBuilder();
		String row = new String(new char[Board.BOARD_SIZE]).replace("\0", "-");
		
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			input.append(row);
			
			if (i % Board.BOARD_SIZE == (Board.BOARD_SIZE / 3))
				input.append(row.charAt(0));
			
			input.append("\n");
		}
		
		Parser.parseBoard(input.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parserChecksInvalidCharacters() {
		StringBuilder input = new StringBuilder();
		String row = new String(new char[Board.BOARD_SIZE]).replace("\0", "-");
		
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			input.append(row);
			
			if (i % Board.BOARD_SIZE == (Board.BOARD_SIZE / 3))
				input.replace(3, 4, "!");
			
			input.append("\n");
		}
		
		Parser.parseBoard(input.toString());
	}
	
	@Test
	public void parserDoesItsJob() {
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
		for (int i = 0; i < (Board.BOARD_SIZE * Board.BOARD_SIZE); i++) {
			int position = i + (i / 9);
			int x = (i % Board.BOARD_SIZE);
			int y = (i / Board.BOARD_SIZE);
			
			char character = input.charAt(position);
			
			if (character == '-')
				assertTrue(board.getTileAt(x, y).getNumber() == Tile.EMPTY);
			else
				assertTrue(board.getTileAt(x, y).getNumber() == Character.getNumericValue(character));
		}
	}
}
