public class PlayGame {

	public String [] play (String topLeftName, String topRightName,String bottomLeftName
		,String bottomRightName,int redX,int redY,int yellowX,int yellowY ,int greenX
		,int greenY,int blueX,int blueY, String goalPiece) {


		TestBoards gameBoard = new TestBoards ();
		SolveBoard boardSolver = new SolveBoard ();
		
		gameBoard.initialize();
		
		try {
			Board start = gameBoard.createStartBoard(topLeftName,topRightName, 
		bottomLeftName, bottomRightName, redX,redY,yellowX, yellowY, 
		greenX, greenY, blueX, blueY);
			int[] endRobots = gameBoard.robotToSolveIndexValue(start.spaces, start.robots, goalPiece);

			int robotToSolveIndex = endRobots[0];

			int robotToSolveValue = endRobots[1];
			String [] movesToPlay = boardSolver.execute (start, robotToSolveIndex, robotToSolveValue);

			return movesToPlay;
		} catch (Exception e) {}


		String [] fail= new String [1];

		return fail;

	}
}