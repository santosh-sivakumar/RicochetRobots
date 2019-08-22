import java.io.*;
import java.util.*;

public class TestBoards {

	Map <String, Integer> spaceAssignments = new HashMap<String,Integer>();

	TestBoards () {

		this.spaceAssignments = new HashMap<String,Integer>();
	}

	public void initialize () {

		spaceAssignments.put ("N", 100000);
		spaceAssignments.put ("E", 10000);
		spaceAssignments.put ("S", 1000);
		spaceAssignments.put ("W", 100);
		spaceAssignments.put ("X", 0);

		spaceAssignments.put ("RC", 1);
		spaceAssignments.put ("RH", 2);
		spaceAssignments.put ("RQ", 3);
		spaceAssignments.put ("RT", 5);

		spaceAssignments.put ("YC", 7);
		spaceAssignments.put ("YH", 11);
		spaceAssignments.put ("YQ", 13);
		spaceAssignments.put ("YT", 17);

		spaceAssignments.put ("GC", 19);
		spaceAssignments.put ("GH", 23);
		spaceAssignments.put ("GQ", 29);
		spaceAssignments.put ("GT", 31);

		spaceAssignments.put ("BC", 37);
		spaceAssignments.put ("BH", 41);
		spaceAssignments.put ("BQ", 43);
		spaceAssignments.put ("BT", 47);
	}

	public Board createStartBoard (String topLeftName, String topRightName, 
		 String bottomLeftName, String bottomRightName,int redX, int redY, int yellowX, int yellowY, 
		int greenX, int greenY, int blueX, int blueY) {

		Map<Integer,Integer> boardSpaces = createBoard (topLeftName, topRightName, 
			bottomLeftName, bottomRightName);
		int [] robotSpaces = placeRobots (redX, redY, yellowX, yellowY, greenX, greenY, blueX, blueY);

		Board output = new Board (boardSpaces, robotSpaces);
		return output;
	}
	
	private Map<Integer,Integer> createBoard (String topLeftName, String topRightName, 
		String bottomLeftName, String bottomRightName) {

		Map<Integer,Integer> boardSpaces = new HashMap <Integer,Integer>();
		
		createTopLeft (topLeftName, boardSpaces);		
		createTopRight (topRightName, boardSpaces);
		createBottomLeft (bottomLeftName, boardSpaces);
		createBottomRight (bottomRightName, boardSpaces);
		
  		return boardSpaces;
	}

	private int [] placeRobots (int redX, int redY, int yellowX, int yellowY, 
		int greenX, int greenY, int blueX, int blueY) {

		int [] robotSpaces = new int[4];
		robotSpaces[0] = ((redY * 16) + redX);
		robotSpaces[1] = ((yellowY * 16) + yellowX);
		robotSpaces[2] = ((greenY * 16) + greenX);
		robotSpaces[3] = ((blueY * 16) + blueX);

		return robotSpaces;
	}

	public int[] robotToSolveIndexValue (Map<Integer, Integer> boardSpaces,
		int [] startingRobots, String spaceOfInterest) {

		int [] robotInfo = new int[2];
		String robot = Character.toString(spaceOfInterest.charAt(0));
		int robotIndex = 0;

		if (robot.equals("R")) {
			robotIndex = 0;
		}
		if (robot.equals("Y")) {
			robotIndex = 1;
		}
		if (robot.equals("G")) {
			robotIndex = 2;
		}
		if (robot.equals("B")) {
			robotIndex = 3;
		}

		robotInfo[0] = robotIndex;

		int spaceOfInterestValue = spaceAssignments.get(spaceOfInterest);

		List<Integer> keyList = new ArrayList<Integer>(boardSpaces.keySet());

		for (int i = 0; i < keyList.size(); i++) {
			int spaceValue = boardSpaces.get(keyList.get(i));
			
			if ((spaceValue  % 100) == spaceOfInterestValue) {
				robotInfo[1] = keyList.get(i);
			}
		}

		return robotInfo;
	}

	private int spaceValue (String spaceCode) {

		int returnValue = 0;
		int length = spaceCode.length();
		if (length == 4) {
			length = 2;
		}

		for (int i = 0; i < length; i++) {

			String currChar = Character.toString(spaceCode.charAt(i));
			assert (spaceAssignments.containsValue(currChar)): "Incompatible string";
			int charValue = (spaceAssignments.get(currChar));

			returnValue += charValue;
		}

		if (spaceCode.length() == 4) {
			String goalPiece = Character.toString(spaceCode.charAt(2)) +
			Character.toString(spaceCode.charAt(3));
			returnValue += spaceAssignments.get(goalPiece);
		}	

		return returnValue;
	}

	private void createTopLeft (String topLeftName, Map<Integer, Integer> boardSpaces) {
		
		
		try {
			File input = new File (topLeftName);
			BufferedReader br = new BufferedReader(new FileReader(input)); 
	  		String line; 
	  		int lineCount = -1;

	  		while ((line = br.readLine()) != null) {
	  			lineCount += 1;
	  			assert (lineCount < 8): "Incompatible file";

	  			String [] spaces = line.split(",");

	  			for (int i = 0; i < spaces.length; i++) {
	  				
	  				assert (i < 8): "Incompatible for board";
	  				int currSpace = (i) + (lineCount * 16);
	  				int currSpaceValue = spaceValue(spaces[i]);
	  				boardSpaces.put(currSpace, currSpaceValue);
	  			}
	  		}
  		} catch (Exception e) {}
	}

	private void createTopRight (String topRightName, Map<Integer, Integer> boardSpaces) {
		
		
		try {
			File input = new File (topRightName);
			BufferedReader br = new BufferedReader(new FileReader(input)); 
	  		String line; 
	  		int lineCount = -1;

	  		while ((line = br.readLine()) != null) {

	  			lineCount += 1;
	  			assert (lineCount < 8): "Incompatible file";

	  			String [] spaces = line.split(",");

	  			for (int i = 0; i < spaces.length; i++) {
	  				
	  				assert (i < 8): "Incompatible for board";
	  				int currSpace = (15 - lineCount) + (i * 16);
	  				int currSpaceValue = spaceValue(rotateString(spaces[i], 1));
	  				
	  				boardSpaces.put(currSpace, currSpaceValue);

	  			}
	  		}
  		} catch (Exception e) {}
	}

	private void createBottomLeft (String bottomLeftName, Map<Integer, Integer> boardSpaces) {
		
		
		try {
			File input = new File (bottomLeftName);
			BufferedReader br = new BufferedReader(new FileReader(input)); 
	  		String line; 
	  		int lineCount = -1;

	  		while ((line = br.readLine()) != null) {

	  			lineCount += 1;
	  			assert (lineCount < 8): "Incompatible file";

	  			String [] spaces = line.split(",");

	  			for (int i = 0; i < spaces.length; i++) {
	  				
	  				assert (i < 8): "Incompatible for board";
	  				int currSpace = (lineCount) + (16 * (15 - i));
	  				int currSpaceValue = spaceValue(rotateString(spaces[i], 2));
	  				
	  				boardSpaces.put(currSpace, currSpaceValue);
	  			}
	  		}
  		} catch (Exception e) {}
	}

	private void createBottomRight (String bottomRightName, Map<Integer, Integer> boardSpaces) {
		
		try {
			File input = new File (bottomRightName);
			BufferedReader br = new BufferedReader(new FileReader(input)); 
	  		String line; 
	  		int lineCount = -1;

	  		while ((line = br.readLine()) != null) {

	  			lineCount += 1;
	  			assert (lineCount < 8): "Incompatible file";

	  			String [] spaces = line.split(",");

	  			for (int i = 0; i < spaces.length; i++) {
	  				
	  				assert (i < 8): "Incompatible for board";
	  				int currSpace = (16 * (15 - lineCount)) + (15 - i);
	  				int currSpaceValue = spaceValue(rotateString(spaces[i], 3));
	  				
	  				boardSpaces.put(currSpace, currSpaceValue);

	  			}
	  		}
  		} catch (Exception e) {}
	}

	private String rotateString (String input, int boardPiece) {

		String output = "";

		if (boardPiece == 1) {
			for (int i = 0; i < input.length(); i++) {
				String currChar = Character.toString(input.charAt(i));
				if (currChar.equals("N")) {
					output += "E";
				}
				else if (currChar.equals("E")) {
					output += "S";
				}
				else if (currChar.equals("S")) {
					output += "W";
				}
				else if (currChar.equals("W")) {
					output += "N";
				}
				else {
					output += currChar;
				}
			}
		}

		else if (boardPiece == 2) {
			for (int i = 0; i < input.length(); i++) {
				String currChar = Character.toString(input.charAt(i));
				if (currChar.equals("N")) {
					output += "W";
				}
				else if (currChar.equals("W")) {
					output += "S";
				}
				else if (currChar.equals("S")) {
					output += "E";
				}
				else if (currChar.equals("E")) {
					output += "N";
				}
				else {
					output += currChar;
				}
			}
		}
		else if (boardPiece == 3) {
			for (int i = 0; i < input.length(); i++) {
				String currChar = Character.toString(input.charAt(i));
				if (currChar.equals("N")) {
					output += "S";
				}
				else if (currChar.equals("W")) {
					output += "E";
				}
				else if (currChar.equals("S")) {
					output += "N";
				}
				else if (currChar.equals("E")) {
					output += "W";
				}
				else {
					output += currChar;
				}
			}
		}
		
		return output;
	}




















	}