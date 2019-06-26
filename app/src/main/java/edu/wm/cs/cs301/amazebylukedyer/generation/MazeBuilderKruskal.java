package edu.wm.cs.cs301.amazebylukedyer.generation;

import java.util.ArrayList;



public class MazeBuilderKruskal extends MazeBuilder implements Runnable {

	
	//will need a maze board to iterate through as we need to assign the maze squares to a set for the 
	//algorithm to work
	protected static int[][] mazeBoard;
	
	protected static ArrayList<Wall> candidates; 
	
	public int count = 0;
	
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
		
	}
	
	public MazeBuilderKruskal(boolean determinate) {
		super(determinate);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	
	@Override
	protected void generatePathways() {
		
		//make a new maze board
		mazeBoard = new int[width][height];
		int num = 1;
		
		//use a nested for loop to go through the mazeBoard and assign a value to each square, very similarly to
		//what I did in SimplePuzzleState
		//This will be what determines what is in what set like in the linked blog article and how to distinguish
		//adding new spaces to an existing set when an edge is chosen
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				mazeBoard[i][j] = num;
				num++;
			}
		}
		
		//Similarly to how Prim's algorithm, we should use the candidate wall approach with a list of walls
		//to tear down
		
		candidates = new ArrayList<Wall>();
		makeWallList(candidates);
		
		//Similarly to Prim'salgorithm, we want to keep going through until the list of candidate walls is empty
		while (candidates.isEmpty() == false) {
			
			//We take a random wall
			Wall currentWall = extractWallFromCandidateSetRandomly(candidates);
			
			//now we want the coordiantes of the wall. This will be important for comparing the current
			//and neighbor walls since that is what I'm thinking of doing to decide how to update the board
			//We can just use the getter methods from the wall class to help with this
			int thisx = currentWall.getX();
			int thisy = currentWall.getY();
			int neighborx = currentWall.getNeighborX();
			int neighbory = currentWall.getNeighborY();
			
			//Now we want to do some if statements to make sure that the neighbor and currents that we are comparing
			//arent border walls
			if (neighborx >= 0 && neighbory >= 0) {
				
				//we also need to check if the walls are out of bounds
				if (neighborx < width && neighbory < height) {
					
					//the last check we need is one to make sure the values of the positions are not the same.
					//If they were the same value, that would mean they are in the same set and in that case we skip
					//But if they are different sets, we want to use another helper method to take down the wall and 
					//update the values of that set that was breached to the values of the invading set
					if (mazeBoard[neighborx][neighbory] != mazeBoard[thisx][thisy]) {
						
						//will need to send in the wall to take down, and the coordinates for the current and neighbor 
						//in question so we can change the values of those guys' sets
						updateBreachedSet(currentWall, thisx, thisy, neighborx, neighbory);
						
					}
				}
			}
		}
		
		

		
	}
	
	//want to use this method to take down the wall in question and then take all the values of the current set that
	//is invading the other set and make it so that the breached set's values match those of the invading set
	private void updateBreachedSet(Wall wall, int invadeX, int invadeY, int breachX, int breachY) {
		count++;
		//first, take down the wall between the sets
		cells.deleteWall(wall);
		
		//important side note! Originally I did not have these variables and decided to just do a direct comparison 
		//of mazeBoard[breachX][breachY] with mazeBoard[i][j] and for some reason it wasn't working. I decided to 
		//make the exact same comparison using variables containing the values in question rather than the direct values
		//and for some reason that worked after I went through a lot of debugging
		int invader = mazeBoard[invadeX][invadeY];
		int breached = mazeBoard[breachX][breachY];
		
		//then loop through the board and change any space with the value of the breached set to be that of the value of
		//invading set to show that it has conquered it and added it to his set
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (mazeBoard[i][j] == breached) {
					mazeBoard[i][j] = invader;
				}
			}
		}
		
		
	}

	
	//This is the same as Prim's
	private Wall extractWallFromCandidateSetRandomly(final ArrayList<Wall> candidates) {
		return candidates.remove(random.nextIntWithinInterval(0, candidates.size()-1)); 
	}
	
	
	//We need a different wall List method from Prim's because of the differences between the two algorithms
	//For Prim's, we can just focus on the walls towards new cells. But for Kruskal's we have to focus on all
	//the walls over height width of maze rather than at a specific x, y position. For that reason, I want to implement
	//pretty much the same thing but loop through width and height and then call the same methods
	private void makeWallList(ArrayList<Wall> walls) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j< height; j++) {
				for (CardinalDirection cd : CardinalDirection.values()) {
					Wall wall = new Wall(i, j, cd);
					if (cells.canGo(wall)) {// 
						walls.add(wall);
					}
				}
			}
		}
	}
	
}
