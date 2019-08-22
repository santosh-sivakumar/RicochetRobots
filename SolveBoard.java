import java.util.*;
public class SolveBoard {
	
	// takes in a given board configuration, result board configuration
	// each board has a list of adjacent boards excluding the one that called it
	// implements DFS algorithm to find shortest path solution

	// Map<Board, Integer> visitStatus = new HashMap<Board,Integer>();
	// // 0 will correspond to white, 1 to grey, 2 to black
	// Map<Board, Board> childToParent = new HashMap<Board,Board>();
	int numMoves;


	SolveBoard () {

		// this.visitStatus = new HashMap<Board,Integer>();
		// this.childToParent = new HashMap<Board,Board>();
		this.numMoves = 0;
	}

	public String [] execute (Board start, int robotIndex, int robotSpace) {

		int maxDepth = 1;
		int numCurrMoves = 0;
		List<int []> moves = new ArrayList<int []>();
		moves.add(start.robots);
		Map<Integer,Integer> gameBoard = start.spaces;
		int [] startRobots = new int[4];
		for (int i = 0; i < 4; i++)
			startRobots[i] = start.robots[i];

		while (!withinDepth (gameBoard, startRobots, robotIndex, robotSpace, 
			maxDepth, numCurrMoves, moves)) {
			System.out.println(maxDepth);
			maxDepth += 1;
		}
		String [] movesToPlay = moveDirections (moves);
		return movesToPlay;
	}


	public boolean withinDepth (Map<Integer,Integer> gameBoard,
		int [] currRobots, int robotIndex, int robotSpace, int maxDepth, int numCurrMoves, 
		List<int []> moves) {

		if (isBoardSolved(currRobots,robotIndex, robotSpace)) {
			System.out.println("board solved");
			numMoves = numCurrMoves;
			return true;
		}

		if (maxDepth == 0) {
			return false;
		}

		ComputeAdjBoards comp = new ComputeAdjBoards(gameBoard);
		List<int []> adjBoards = comp.allAdjBoards(currRobots);
		for (int j = 0; j < adjBoards.size(); j++) {
			moves.add(adjBoards.get(j));
			if (withinDepth (gameBoard, adjBoards.get(j), robotIndex, robotSpace, 
				maxDepth - 1, numCurrMoves + 1, moves)) {
				return true;
			}
			else {
				moves.remove(moves.size() - 1);
			}
		}
		adjBoards.clear();
		return false;
	}

	private boolean isBoardSolved (int [] startRobots,  int robotIndex, int robotSpace) {


		if (startRobots[robotIndex] == robotSpace) {
			return true;
		}
		return false;
	}

	private void printBoard (Board curr) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				System.out.print(i);
				System.out.print(" ");
				System.out.print(j);
				System.out.print(" ");
				System.out.println(curr.spaces.get((i * 16) + j));
			}
		}
		System.out.println("-------");
	}

	private String returnRobotColor (int robotIndex) {

		String color = "";

		if (robotIndex == 0) {
			color = "red";
		}
		else if (robotIndex == 1) {
			color = "yellow";
		}
		else if (robotIndex == 2) {
			color = "green";
		}
		else {
			assert (robotIndex == 3);
			color = "blue";
		}
		return color;
	}

	private String[] moveDirections (List<int []> moves) {

		String [] movesToMake = new String[moves.size() - 1];

		for (int i = 1; i < moves.size(); i++) {
			String currMove = "";
			for (int j = 0; j < 4; j++) {
				int oldMove = moves.get(i - 1)[j];
				int newMove = moves.get(i)[j];
				if (newMove != oldMove) {
					currMove += "Move ";
					currMove += returnRobotColor(j);
					currMove += " robot";
					currMove += directionToMove (oldMove, newMove);
				}
			}
			movesToMake[i-1] = (currMove);
		}
		return movesToMake;
	}

	private String directionToMove (int oldMove, int newMove) {

		int oldX = oldMove % 16;
		int oldY = oldMove / 16;
		int newX = newMove % 16;
		int newY = newMove / 16;

		if (newY > oldY) {
			String direction = " down";
			return direction;
		}
		else if (newY < oldY) {
			String direction = " up";
			return direction;
		}
		else if (newX > oldX) {
			String direction = " right";
			return direction;			
		}
		else {
			assert (newX < oldX): "Not a viable move";
			String direction = " left";
			return direction;
		}
	}













}