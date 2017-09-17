/*Scott Schreiber
 *ITCS 3153 Assignment 1 - Eight Queens
 * 
 * 
 * 
 * 
 * 
 * */



import java.util.Random;
public class Queens {

	public static int[][] board = new int[8][8];
	public static Random rand = new Random();
	public static int betterStateCount = 0;
	public static int stateChangeCount = 0;
	public static int restartCount = -1; //starts at -1 to offset initial state setup
	public static int currentH;
	
	public static void main(String[] args) {
		
		//sets initial state
		//restart();
		board[6][0] = 1;
		board[4+1][1] = 1;
		board[2][2] = 1;
		board[0][3] = 1;
		board[5][4] = 1;
		board[7][5] = 1;
		board[1][6] = 1;
		board[3][7] = 1;
		currentH = calcH();
		printBoard();
		
		
		/*
		while(currentH > 0) {
			
			currentH = calcCurrentH();
			
			restart();
			printBoard();
			
		}
		System.out.print("State changes: " + stateChangeCount + "\nRestarts: " + restartCount);
		 */
	}
	
	
	public static int calcH() {
		
		int conflicts = 0;
		boolean conflictFound = false;
		
		//checks rows
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j] == 1) {
					
					//checks to the left
					for(int left = 0; left < j; left++) {
						if(board[i][left] == 1) {
							conflictFound = true;
						}
					}
					//checks to the right
					for(int right = j+1; right < board.length-j; right++) {
						if(board[i][right] == 1) {
							conflictFound = true;
						}
					}
					//checks diagonals
					
					for(int diagUpLeft = 1; i-diagUpLeft >= 0 && j-diagUpLeft >= 0; diagUpLeft++) {
						if(board[i-diagUpLeft][j-diagUpLeft] == 1) {
							conflictFound = true;
						}
					}
					for(int diagUpRight = 1; i-diagUpRight >= 0 && j+diagUpRight <= 7; diagUpRight++) {
						if(board[i-diagUpRight][j+diagUpRight] == 1) {
							conflictFound = true;
						}
					}
					for(int diagDownRight = 1; i+diagDownRight <= 7 && j+diagDownRight <= 7; diagDownRight++) {
						if(board[i+diagDownRight][j+diagDownRight] == 1) {
							conflictFound = true;
						}
					}
					for(int diagDownLeft = 1; i+diagDownLeft <= 7 && j-diagDownLeft >= 0 ; diagDownLeft++) {
						if(board[i+diagDownLeft][j-diagDownLeft] == 1) {
							conflictFound = true;
						}
					}
					
					if(conflictFound) {
						conflicts++;
						conflictFound = false;
					}
					
				}
					
			}
		}
		
		//checks diagonals
		
		
		
		
		
		return conflicts;
	}
	
	
	
	public static void restart() {

		//clear board
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[i].length; j++)
				board[i][j] = 0;
	
		//randomly place queens in columns
		for(int i = 0; i < board.length; i++) {
			
			board[rand.nextInt(8)][i] = 1;
		}	
			restartCount++;
	}
	
	
	public static void printBoard() {
		
		int r = 0;
		
		System.out.print("Current h: " + currentH + "\nCurrent State:\n");
		
		
		//---------------Column numbers formatting----------------//
		System.out.print("  ");
		for(int x = 0; x < 8; x++)
			System.out.print(x + " ");
		System.out.print("\n  ");
		for(int x = 0; x < 8; x++)
			System.out.print("- ");
		System.out.print("\n");
		
		//---------------Board printing----------------//
		for(int i = 0; i < board.length; i++) {
			System.out.print(r + "|");
			
			for(int j = 0; j < board[i].length; j++) {

				System.out.print(board[i][j] + " ");
			}
			r++;
			System.out.print("\n");
		}
		System.out.println("Neighbors found with lower h: " + betterStateCount);
		
	}

}
