import java.util.Random;
public class Queens {

	public static int[][] board = new int[8][8];
	public static Random rand = new Random();
	public static int conflicts = 0;
	public static int betterStateCount = 0;
	public static boolean goalReached = false;
	public static int stateChangeCount = 0;
	public static int restartCount = 0;
	
	public static void main(String[] args) {
		
		
		while(!goalReached) {
			
			restart();
			printBoard();
			
		}
		System.out.print("State changes: " + stateChangeCount + "\nRestarts: " + restartCount);

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
		
		System.out.print("Current h: " + conflicts + "\nCurrent State:\n");
		
		
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
