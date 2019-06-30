package edu.wm.cs.cs301.amazebylukedyer.gui;

import edu.wm.cs.cs301.amazebylukedyer.generation.CardinalDirection;
import edu.wm.cs.cs301.amazebylukedyer.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebylukedyer.gui.Constants.UserInput;

public class BasicRobot implements Robot {

	private int odometer;
	public Controller controller;
	protected CardinalDirection facingThisDirection;
	private boolean stopped;
	protected Direction relativeDirection;
	

	/**
	 * Constructor to set up a Robot object
	 * Takes a controller as input, initializes odometer to 0, sets robot and driver, sets direction to east, and makes hasStopped 		 * false.
	 *
	 * @author Luke Dyer
	 * @params controller, creates controller variable to set robot
	 */
	public BasicRobot(Controller controller) {
		this.controller = controller;
		this.odometer = 0;
		this.stopped = false;
		this.facingThisDirection = CardinalDirection.East;	
		
		this.setMaze(controller);

	}

	@Override
	public void rotate(Turn turn) {
		
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		
		if (stopped == true) {
			return;
		}
		if (turn == Turn.RIGHT) {
			//System.out.println("In the rotate method, before pressing keyDown, direction is: " + this.facingThisDirection);
			controller.keyDown(UserInput.Right, 0);
			this.facingThisDirection = this.facingThisDirection.rotateClockwise();

			//System.out.println("In the rotate method, after pressing keyDown, direction is: " + this.facingThisDirection);
			
		}
		else if (turn == Turn.LEFT) {
			//System.out.println("In the rotate method, before pressing keyDown, direction is: " + this.facingThisDirection);

			controller.keyDown(UserInput.Left, 0);
			this.facingThisDirection = this.facingThisDirection.rotateClockwise().rotateClockwise().rotateClockwise();

			//System.out.println("In the rotate method, after pressing keyDown, direction is: " + this.facingThisDirection);
		}
		else if (turn == Turn.AROUND) {
			//System.out.println("before the turn around, x is: "  + xPosition + "and y is: " + yPosition);
			controller.keyDown(UserInput.Right, 0);
			controller.keyDown(UserInput.Right, 0);
			this.facingThisDirection = this.facingThisDirection.rotateClockwise().rotateClockwise();
			//System.out.println("after the turn around, x is: "  + xPosition + "and y is: " + yPosition);
		}
	}

	@Override
	public void move(int distance, boolean manual) {
		
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		
		if (stopped == true) {
			return;
		}
		
		for (int d = 0; d < distance; d++) {
			int currentPosition[] = this.controller.getCurrentPosition();
			
			int xPosition = currentPosition[0];
			int yPosition = currentPosition[1];

			if (manual == true) {
				this.stopped = true;
			}
			if (hasWall(xPosition, yPosition, facingThisDirection) == false) {
				controller.keyDown(UserInput.Up, 0);
			}
	
			//work on this at home, at work fill in the more trivial methods
			
		}
		
	}

	@Override
	public int[] getCurrentPosition() throws Exception {

		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		if (mazeConfig.isValidPosition(xPosition, yPosition) == false) {
			throw new Exception();
		}
		else {
			return currentPosition;
		}
		
	}

	@Override
	public void setMaze(Controller controller) {
		this.controller = controller;
	}

	@Override
	public boolean isAtExit() {
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		
		if (mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.East) == false) {
			if (mazeConfig.isValidPosition(xPosition+1, yPosition) == false) {
				//System.out.println("Apparently does not have a wall to the east");
				return true;
			}
		}
		if (mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.West) == false) {
			if (mazeConfig.isValidPosition(xPosition-1, yPosition) == false) {
				//System.out.println("Apparently does not have a wall to the west");
				return true;
			}
		}
		if (mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.North) == false) {
			if (mazeConfig.isValidPosition(xPosition, yPosition-1) == false) {
				//System.out.println("Apparently does not have a wall to the north");
				return true;
			}
		}
		if (mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.South) == false) {
			if (mazeConfig.isValidPosition(xPosition, yPosition+1) == false) {
				//System.out.println("Apparently does not have a wall to the south");
				return true;
			}
		}
		
		
		return false;
			
		
	}

	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {
		
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		
		CardinalDirection cd = directionTranslator(direction);
		
		
		while (mazeConfig.isValidPosition(xPosition, yPosition) == true) {
			if (mazeConfig.hasWall(xPosition, yPosition, cd)) {
				return false;
			}
			if (cd == CardinalDirection.North) {
				yPosition--;
			}
			if (cd == CardinalDirection.South) {
				yPosition++;
			}
			if (cd == CardinalDirection.East) {
				xPosition--;
			}
			if (cd == CardinalDirection.West) {
				xPosition++;
			}
		}
		
		return true;
	}

	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		
		if (mazeConfig.getMazecells().isInRoom(xPosition, yPosition) == true) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasRoomSensor() {
		return true;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return this.facingThisDirection;
	}

	@Override
	public float getBatteryLevel() {
		//default
		return 0;
	}

	@Override
	public void setBatteryLevel(float level) {
		//default
		
	}

	@Override
	public int getOdometerReading() {
		return this.odometer;
	}

	@Override
	public void resetOdometer() {
		this.odometer = 0;
	}

	@Override
	public float getEnergyForFullRotation() {
		//default
		return 0;
	}

	@Override
	public float getEnergyForStepForward() {
		//default
		return 0;
	}

	@Override
	public boolean hasStopped() {
		return this.stopped;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		
		int footsteps = 0;
		CardinalDirection cd = directionTranslator(direction);
		
		while (mazeConfig.hasWall(xPosition, yPosition, cd) == false) {
			if (mazeConfig.isValidPosition(xPosition, yPosition) == false) {
				return Integer.MAX_VALUE;
			}
			if (cd == CardinalDirection.North) {
				yPosition++;
				//maybe yPosition--
			}
			if (cd == CardinalDirection.South) {
				yPosition--;
				//maybe yPosition++
			}
			if (cd == CardinalDirection.East) {
				xPosition++;
			}
			if (cd == CardinalDirection.West) {
				xPosition--;
			}
			footsteps++;
		}
		
		return footsteps;
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		return true;
	}
	
	/**
	 * Takes a direction as input and gives the corresponding CardinalDirection for distanceToObstacle and
	 * canSeeExit
	 * @author Luke Dyer
	 * @param direction
	 * @return CardinalDirection based on given direction (Left, Right, Forwards, or Backwards)
	 */
	public CardinalDirection directionTranslator(Direction direction) {
		CardinalDirection cd = this.getCurrentDirection();
		if (direction == Direction.FORWARD) {
			cd = cd;
		}
		
		if (direction == Direction.RIGHT) {
			cd = cd.rotateClockwise();
		}
		if (direction == Direction.BACKWARD) {
			cd = cd.rotateClockwise().rotateClockwise();
		}
		if (direction == Direction.LEFT) {
			cd = cd.rotateClockwise().rotateClockwise().rotateClockwise();
		}
		return cd;
	}
	
	/**
	 * Takes a CardinalDirection as input and returns a boolean using the hasWall method of mazeConfig
	 * that tells if there is a wall in that CardinalDirection at that position.
	 * @author Luke Dyer
	 * @param cd
	 * @return boolean that is true if there is a wall, and false if there is not a wall
	 */
	public boolean hasWallInThisDirection(CardinalDirection cd) {
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		
		
		return this.hasWall(xPosition, yPosition, cd);
	}
	
	
	public boolean hasWall(int x, int y, CardinalDirection cd) {
		
		if (cd == CardinalDirection.South || cd == CardinalDirection.North) {
			cd = cd.oppositeDirection();
		}
		
		return this.controller.getMazeConfiguration().hasWall(x, y, cd);
	}
	 
	/**
	 * Has the robot take one step forwards toward the exit
	 * @author Luke Dyer
	 * @return boolean, true if it makes it, false if it does not
	 */
	public boolean stepTowardsExit() {
		//get a cd based on isValidPosition, which means exit is in that direction, then have robot rotate till
		//facing that direction, then walk 1
		
		MazeConfiguration mazeConfig = this.controller.getMazeConfiguration();
		int currentPosition[] = this.controller.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		CardinalDirection cd = null;
		

		
		if (!mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.North) && mazeConfig.isValidPosition(xPosition, yPosition-1) == false) {
			cd = CardinalDirection.North;
		}
		
		if (!mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.South) && mazeConfig.isValidPosition(xPosition, yPosition+1) == false) {
			cd = CardinalDirection.South;
		}
		
		if (!mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.East) && mazeConfig.isValidPosition(xPosition+1, yPosition) == false) {
			cd = CardinalDirection.East;
		}
		
		if (!mazeConfig.hasWall(xPosition, yPosition, CardinalDirection.West) && mazeConfig.isValidPosition(xPosition-1, yPosition) == false) {
			cd = CardinalDirection.West;
		}

		if (cd == null) {
//			System.out.println("Should have chosen North: " + !mazeConfig.isValidPosition(xPosition, yPosition-1));
//			System.out.println("Should have chosen South: " + !mazeConfig.isValidPosition(xPosition, yPosition+1));
//			System.out.println("Should have chosen East: " + !mazeConfig.isValidPosition(xPosition+1, yPosition));
//			System.out.println("Should have chosen West: " + !mazeConfig.isValidPosition(xPosition-1, yPosition));
			//since it seems to be choosing two different directions, maybe we should use either the hasWallInThisDireciton
			//in conjunction with that check to see if it is not valid and there is no wall there, so choose that
			//maybe also try canSeeExit in a certain direction so we know that if a position is invalid and we can see
			//the exit from that direction, we know that is the true exit and has to be the chosen cd
			//System.out.println("For some reason, we did not choose a cardinaldirection");
			return false;
		}
		
		
		if (cd == CardinalDirection.North || cd == CardinalDirection.South) {
			while (getCurrentDirection().oppositeDirection() != cd) {
				rotate(Turn.RIGHT);
			}
		}
		else {
			while (getCurrentDirection() != cd) {
				//System.out.println("in stepTowardsExit, our current direction is: " + getCurrentDirection());
				//System.out.println("in stepTowardsExit, the CardinalDirection chosen was: " + cd);
				rotate(Turn.RIGHT);
			}
		}
		
		move(1, false);
		
		return true;
	}
	
	/**
	 * Gets the MazeConfiguration for the WallFollower class
	 * @author Luke Dyer
	 * @return MazeConfiguration
	 */
	public MazeConfiguration getWallfollowerMazeConfiguration() {
		return this.controller.getMazeConfiguration();
	}
	
	public void stuckInCorner() {
		this.controller.switchFromPlayingToWinning(0);
	}
	

	
	
}
