import java.util.*;

public class Board {

	Map <Integer, Integer> spaces = new HashMap<Integer, Integer>();
	int [] robots = new int[4];
	List<Board> adjList = new ArrayList<Board>();

	Board (Map <Integer, Integer> spaces, int [] robots ) {
		this.spaces = spaces;
		this.robots = robots;
		this.adjList = new ArrayList<Board>();
	}
}