import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

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
		runAStar();

	}

	public static void runAStar() {

		Node comparator = new Node(0, 0, 0);
		Node top, bottom, left, right;
		ArrayList<Node> closedList = new ArrayList<>();
		ArrayList<Node> path = new ArrayList<>();
		Node currentNode;
		PriorityQueue<Node> openList = new PriorityQueue<>(10, comparator);
		boolean goalfound = false;

		// calculate F,G,H, parent is null of start node
		startNode.setParent(null);
		startNode.setG(calcG(startNode));
		startNode.setH(calcH(startNode));
		startNode.setF();

		// add start node to open list
		openList.add(startNode);

		// loop while goal is not found or openlist is not empty

		while (!goalfound && !openList.isEmpty()) {
			// pop off node with lowest F from open list
			currentNode = openList.poll();
			// check if it's the goal node, if yes generate path
			if (currentNode.equals(goalNode)) {
				goalfound = true;
				// generate path
				while (currentNode.getParent() != null) {
					path.add(currentNode);
					currentNode = currentNode.getParent();
				}

				System.out.println("Printing path list");
				for (int i = path.size() - 1; i >= 0; i--) {
					System.out.println(path.get(i));
				}

			} else {// if not generate neighbors.

				// -------------Top--------------//

				if (currentNode.getRow() - 1 > 0) {
					top = grid[currentNode.getRow() - 1][currentNode.getCol()];
					if (top.getType() != 1 && !closedList.contains(top)) {
						top.setG(calcG(top));
						top.setH(calcH(top));
						top.setF();
						top.setParent(currentNode);
						openList.add(top);
					}
				}
				// -------------Bottom--------------//
				if (currentNode.getRow() + 1 < 14) {
					bottom = grid[currentNode.getRow() + 1][currentNode.getCol()];
					if (bottom.getType() != 1 && !closedList.contains(bottom)) {
						bottom.setG(calcG(bottom));
						bottom.setH(calcH(bottom));
						bottom.setF();
						bottom.setParent(currentNode);
						openList.add(bottom);
					}
				}
				// -------------Left--------------//
				if (currentNode.getCol() - 1 > 0) {
					left = grid[currentNode.getRow()][currentNode.getCol() - 1];
					if (left.getType() != 1 && !closedList.contains(left)) {
						left.setG(calcG(left));
						left.setH(calcH(left));
						left.setF();
						left.setParent(currentNode);
						openList.add(left);
					}
				}
				// -------------Right--------------//
				if (currentNode.getCol() + 1 < 14) {
					right = grid[currentNode.getRow()][currentNode.getCol() + 1];
					if (right.getType() != 1 && !closedList.contains(right)) {
						right.setG(calcG(right));
						right.setH(calcH(right));
						right.setF();
						right.setParent(currentNode);
						openList.add(right);
					}
				}

				// add current node to closed list
				closedList.add(currentNode);
			}

		}

		/*
		 * Node test = new Node(0, 0, 0); test.setF(10); Node test2 = new Node(0, 0, 0);
		 * test2.setF(20); Node test3 = new Node(0, 0, 0); test3.setF(5);
		 * 
		 * openList.add(test); System.out.println(openList.peek().getF());
		 * openList.add(test2); System.out.println(openList.peek().getF());
		 * openList.add(test3); System.out.println(openList.peek().getF());
		 */
	}

	public static int calcH(Node n) {

		return Math.abs(n.getCol() - goalNode.getCol()) + Math.abs(n.getRow() - goalNode.getRow());
	}

	public static int calcG(Node n) {

		return Math.abs(n.getCol() - startNode.getCol()) + Math.abs(n.getRow() - startNode.getRow());
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

		String[] temp = start.split(",");
		String[] temp2 = goal.split(",");

		startNode = grid[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])];
		goalNode = grid[Integer.parseInt(temp2[0])][Integer.parseInt(temp2[1])];
	}

}
