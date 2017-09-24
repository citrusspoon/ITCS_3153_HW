import java.util.Random;
import java.util.Scanner;

public class Main {

	public static Node[][] grid = new Node[15][15];
	public static Random rand = new Random();
	public static Node startNode;
	public static Node goalNode;

	public static void main(String[] args) {

		generateGrid();
		printGrid();
		promptInput();
		printGrid();
		
		
		

	}

	public static void generateGrid() {

		// sets non traversable nodes (10%)

		for (int x = 0; x < (int) ((grid.length * grid.length) * 0.1); x++) {
			int r = rand.nextInt(14);
			int c = rand.nextInt(14);

			while (grid[r][c] != null) {
				r = rand.nextInt(14);
				c = rand.nextInt(14);
			}
			grid[r][c] = new Node(r, c, 1);
		}

		// sets remaining spaces to traversable
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == null)
					grid[i][j] = new Node(i, j, 0);
			}
		}
	}

	public static void printGrid() {

		int r = 0;

		// ---------------Column numbers formatting----------------//
		System.out.print("   ");
		for (int x = 0; x < 15; x++) {
			if (x < 10)
				System.out.print(x + "  ");
			else
				System.out.print(x + " ");

		}
		System.out.print("\n   ");
		for (int x = 0; x < 15; x++)
			System.out.print("-- ");
		System.out.print("\n");

		// ---------------Board printing----------------//
		for (int i = 0; i < grid.length; i++) {
			if (r < 10)
				System.out.print(" ");

			System.out.print(r + "|");

			for (int j = 0; j < grid[i].length; j++) {

				System.out.print(grid[i][j].getType() + "  ");
			}
			r++;
			System.out.print("\n");
		}

	}
	
	public static void promptInput() {
		
		Scanner input = new Scanner(System.in);
		String start, goal;
		
		System.out.println("Enter start node in the format x,y: ");
		start = input.nextLine();
		System.out.println("Enter goal node in the format x,y: ");
		goal = input.nextLine();
		
		startNode = grid[Integer.parseInt(start.substring(0, 1))][Integer.parseInt(start.substring(2))];
		goalNode = grid[Integer.parseInt(goal.substring(0, 1))][Integer.parseInt(goal.substring(2))];		
	}
}
