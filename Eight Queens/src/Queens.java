/*Scott Schreiber
 *ITCS 3153 Assignment 1 - Eight Queens
 * 
 * 
 * 
 * 
 * 
 * */

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Queens {

	public static int[][] board = new int[8][8];
	public static Random rand = new Random();
	public static int betterStateCount = 0;
	public static int stateChangeCount = 0;
	public static int restartCount = -1; // starts at -1 to offset initial state setup
	public static int currentH;
	public static boolean restartRequired = false;

	public static void main(String[] args) {

		int[][] temp;

		// sets initial state
		restart();
		currentH = calcH(board);
		printBoard();
		/*
		temp = getNewConfig();
		
		for(int a = 0; a < 8; a++)
			for(int b = 0; b < 8; b++)
				board[a][b] = temp[a][b];
		
		currentH = calcH(board);
		printBoard();*/

		while (currentH > 0) {

			currentH = calcH(board);
			printBoard();
			board = getNewConfig().clone();
			if(restartRequired) 
				restart();
			

		}
		System.out.print("State changes: " + stateChangeCount + "\nRestarts: " + restartCount);

	}

	public static int[][] getNewConfig() {

		int[][] finalConfig = new int[8][8];
		int[][] currentConfig = new int[8][8];
		
		for(int a = 0; a < 8; a++)
			for(int b = 0; b < 8; b++)
				currentConfig[a][b] = board[a][b];
		
		//printBoard(currentConfig);
		
		int lowestH = currentH;
		int currentH = 8;

		for (int j = 0; j < board.length; j++) {

			for (int i = 0; i < board.length; i++) {

				// clears column of queens
				for (int r = 0; r < board.length; r++)
					currentConfig[r][j] = 0;
				
				//printBoard(currentConfig);

				currentConfig[i][j] = 1;
				currentH = calcH(currentConfig);
				//printBoard(currentConfig);
				if (currentH < lowestH) {
					
					for(int a = 0; a < 8; a++)
						for(int b = 0; b < 8; b++)
							finalConfig[a][b] = currentConfig[a][b];
					
					
					//finalConfig = currentConfig.clone();
					betterStateCount++;
				}

			}
			//System.out.println("Next col");

			for(int ii = 0; ii < board.length; ii++)
				for(int jj = 0; jj < board.length; jj++)
					currentConfig[ii][jj] = board[ii][jj];
			
			//printBoard(currentConfig);
			//printBoard(board);
			

		}

		if (lowestH > 0) {

			System.out.println("Neighbors found with lower h: " + betterStateCount);

			if (betterStateCount == 0) {
				restartRequired = true;
				System.out.println("RESTART");
			}

			else {
				stateChangeCount++;
				System.out.println("Setting new current state");
			}
			betterStateCount = 0;
		}
		else {
			System.out.println("Solution found!");
		}
		return finalConfig;
	}

	public static int calcH(int[][] config) {

		int conflicts = 0;
		boolean conflictFound = false;

		// checks rows
		for (int i = 0; i < config.length; i++) {
			for (int j = 0; j < config[i].length; j++) {
				if (config[i][j] == 1) {

					// checks to the left
					for (int left = 0; left < j; left++) {
						if (config[i][left] == 1) {
							conflictFound = true;
						}
					}
					// checks to the right
					for (int right = j + 1; right < config.length - j; right++) {
						if (config[i][right] == 1) {
							conflictFound = true;
						}
					}
					// checks diagonals

					for (int diagUpLeft = 1; i - diagUpLeft >= 0 && j - diagUpLeft >= 0; diagUpLeft++) {
						if (config[i - diagUpLeft][j - diagUpLeft] == 1) {
							conflictFound = true;
						}
					}
					for (int diagUpRight = 1; i - diagUpRight >= 0 && j + diagUpRight <= 7; diagUpRight++) {
						if (config[i - diagUpRight][j + diagUpRight] == 1) {
							conflictFound = true;
						}
					}
					for (int diagDownRight = 1; i + diagDownRight <= 7 && j + diagDownRight <= 7; diagDownRight++) {
						if (config[i + diagDownRight][j + diagDownRight] == 1) {
							conflictFound = true;
						}
					}
					for (int diagDownLeft = 1; i + diagDownLeft <= 7 && j - diagDownLeft >= 0; diagDownLeft++) {
						if (config[i + diagDownLeft][j - diagDownLeft] == 1) {
							conflictFound = true;
						}
					}

					if (conflictFound) {
						conflicts++;
						conflictFound = false;
					}

				}

			}
		}

		return conflicts;
	}

	public static void restart() {

		// clear board
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = 0;

		// randomly place queens in columns
		for (int k = 0; k < board.length; k++) {

			board[rand.nextInt(8)][k] = 1;
		}
		restartCount++;
		restartRequired = false;
	}

	public static void printBoard() {

		int r = 0;

		System.out.print("Current h: " + currentH + "\nCurrent State:\n");

		// ---------------Column numbers formatting----------------//
		System.out.print("  ");
		for (int x = 0; x < 8; x++)
			System.out.print(x + " ");
		System.out.print("\n  ");
		for (int x = 0; x < 8; x++)
			System.out.print("- ");
		System.out.print("\n");

		// ---------------Board printing----------------//
		for (int i = 0; i < board.length; i++) {
			System.out.print(r + "|");

			for (int j = 0; j < board[i].length; j++) {

				System.out.print(board[i][j] + " ");
			}
			r++;
			System.out.print("\n");
		}
		

	}
	public static void printBoard(int[][] b) {

		int r = 0;

		System.out.println("Test board");

		// ---------------Column numbers formatting----------------//
		System.out.print("  ");
		for (int x = 0; x < 8; x++)
			System.out.print(x + " ");
		System.out.print("\n  ");
		for (int x = 0; x < 8; x++)
			System.out.print("- ");
		System.out.print("\n");

		// ---------------Board printing----------------//
		for (int i = 0; i < b.length; i++) {
			System.out.print(r + "|");

			for (int j = 0; j < b[i].length; j++) {

				System.out.print(b[i][j] + " ");
			}
			r++;
			System.out.print("\n");
		}
		

	}

}
