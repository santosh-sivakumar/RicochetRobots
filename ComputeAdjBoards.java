import java.util.*;

public class ComputeAdjBoards {
	// red is 1, yellow is 2, green is 3, blue is 4. North is 0, east is 1, south is 2, west is 3
	// robots array corresponds to what space robot lies in
	Map <Integer, Integer> currBoard = new HashMap<Integer,Integer>();

	ComputeAdjBoards (Map <Integer, Integer> currBoard) {
		this.currBoard = currBoard;
	}

	private boolean canMove (int [] robots, Integer currSpace, int direction) {

		int spaceToMove = moveSpace(currSpace, direction);

		for (int i = 0; i < 4; i++) {
			if (robots[i] == spaceToMove) {
				return false;
			}
		}

		if (currSpace == spaceToMove) {
			return false;
		}

		// if (currBoard.get(spaceToMove) % 10 != 0) {
		// 	return false;
		// }

		if (((currBoard.get(spaceToMove) % 1000000) >= 100000) && (direction == 2)) {
			return false;
		}
		if (((currBoard.get(spaceToMove) % 100000) >= 10000) && (direction == 3)) {
			return false;
		}
		if (((currBoard.get(spaceToMove) % 10000) >= 1000) && (direction == 0)) {
			return false;
		}
		if (((currBoard.get(spaceToMove) % 1000) >= 100) && (direction == 1)) {
			return false;
		}

		if (((currSpace % 1000000) >= 100000) && (direction == 0)){
			return false;
		}
		if (((currSpace % 100000) >= 10000) && (direction == 1)) {
			return false;
		}
		if (((currSpace % 10000) >= 1000) && (direction == 2)) {
			return false;
		}
		if (((currSpace % 1000) >= 100) && (direction == 3)) {
			return false;
		}

		return true;
	}

	private int moveSpace (int currSpace, int direction) {

		int y = (currSpace / 16);
		int x = (currSpace % 16);

		if (direction == 0 && y != 0) {
			y -= 1;
		}
		else if (direction == 1 && x != 15) {
			x += 1;
		}
		else if (direction == 2 && y != 15) {
			y += 1;
		}
		else if (direction == 3 && x != 0) {
			x -= 1;
		}

		int returnSpace = ((y * 16) + x);
		return returnSpace;
	}

	private int [] findOneBoard (int [] startRobots, int robotIndex, int direction) {
		
		int [] outputRobots = new int[4];

		for (int i = 0; i < 4; i++) {
			outputRobots[i] = startRobots[i];
		}

		int startSpace = outputRobots[robotIndex];
		int currSpace = outputRobots[robotIndex];

		while (canMove (outputRobots, currSpace, direction)) {
			currSpace = moveSpace (currSpace, direction);
			// System.out.print(robotIndex);
			// System.out.print(" ");
			// System.out.print(direction);
			// System.out.print(" ");
			// System.out.println(currSpace);
		}

		if (currSpace != startSpace) {
			// int newStart = (outputSpaces.get(startSpace) - robotIndex);
			// outputSpaces.put(startSpace, newStart);
			// int newFinish = (outputSpaces.get(currSpace) + robotIndex);
			// outputSpaces.put(currSpace, newFinish);
			outputRobots[robotIndex] = currSpace;
		}

		return outputRobots;
	}

	private boolean areBoardsDiff (int [] startRobots,  int [] finishRobots) {
		
		for (int i = 0; i < 4; i++) {
			if (startRobots[i] != finishRobots[i]) {
				return true;
			}
		}
		return false;
	}

	public List<int []> allAdjBoards (int [] startRobots) {

		List<int []> adjBoards = new ArrayList<int []>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int [] newRobots = findOneBoard (startRobots, i, j);
				if (areBoardsDiff(startRobots,newRobots)) {
					adjBoards.add(newRobots);
				}
			}
		}
		return adjBoards;
	}

}
