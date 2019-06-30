package edu.wm.cs.cs301.amazebylukedyer.gui;

import edu.wm.cs.cs301.amazebylukedyer.generation.Distance;
import edu.wm.cs.cs301.amazebylukedyer.generation.MazeConfiguration;
import edu.wm.cs.cs301.amazebylukedyer.gui.Robot.Direction;
import edu.wm.cs.cs301.amazebylukedyer.gui.Robot.Turn;

public class WallFollower implements RobotDriver{
	
	private Robot brobot;
	private int width;
	private int height;
	private Distance distance;
	private Direction direction;
	private Controller controller;
	//hopefully this works
	
	public WallFollower() {
		//nothing?
	}
	@Override
	public void setRobot(Robot r) {
		this.brobot = r;
		
	}

	@Override
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		
	}

	@Override
	public void setDistance(Distance distance) {
		this.distance = distance;
		
	}

	@Override
	public boolean drive2Exit() throws Exception {
		
		MazeConfiguration mazeConfig = brobot.getWallfollowerMazeConfiguration();
		int currentPosition[] = brobot.getCurrentPosition();
		int xPosition = currentPosition[0];
		int yPosition = currentPosition[1];
		int prevX = 0;
		int prevY = 0;
		int counter = 0;
		while (brobot.isAtExit() == false) {
			
//			boolean rightWall = mazeConfig.hasWall(xPosition, yPosition, brobot.getCurrentDirection().rotateClockwise());
//			boolean frontWall = mazeConfig.hasWall(xPosition, yPosition, brobot.getCurrentDirection());
//			boolean leftWall = mazeConfig.hasWall(xPosition, yPosition, brobot.getCurrentDirection().rotateClockwise().rotateClockwise().rotateClockwise());
					
			boolean frontWall = brobot.hasWallInThisDirection(brobot.getCurrentDirection());
			boolean leftWall = brobot.hasWallInThisDirection(brobot.getCurrentDirection().rotateClockwise().rotateClockwise().rotateClockwise());
			
			//System.out.println("There is a front wall: " + frontWall);
			//System.out.println("There is a left wall: " + leftWall);
			//there is no wall in front and no wall to the left
			if (frontWall == false && leftWall == false) {
					
				//System.out.println("No wall in front and no wall to the left, turn left");
				brobot.rotate(Turn.LEFT);
			}
			
			//there is a wall in front and a wall to the left
			else if (frontWall == true && leftWall == true) {
				
					//System.out.println("Wall in front and wall to the left, turn right");
					brobot.rotate(Turn.RIGHT);
			}
			
			//there is a wall in front and no wall to the left
			else if (frontWall == true && leftWall == false) {
				
					//System.out.println("Wall in front and no wall to the left, turn left");
					brobot.rotate(Turn.LEFT);
			}
			
			//there is a wall in front and a wall to the left and a wall to the right

			
			if (!brobot.hasWallInThisDirection(brobot.getCurrentDirection())) {
				
				//System.out.println("No wall in this direction, move");
				//System.out.println("Current direction is: " + brobot.getCurrentDirection());
				prevX = xPosition;
				prevY = yPosition;
				brobot.move(1, false);
				
				//System.out.println("brobot has moved");
				if (prevX == brobot.getCurrentPosition()[0] && prevY == brobot.getCurrentPosition()[1]) {
					System.out.println("we did not really move");
					counter ++;
					if (counter == 10000) {
						System.out.println("Your robot got stuck in a corner! How Unfortunate! I swear this only happens every once in a while! Try Again!");
						brobot.stuckInCorner();
						break;
					}
					
				}
			}
			
			if (brobot.hasStopped()) {
				//System.out.println("Brobot stopped for some reason");
				return false;
			}
			//System.out.println("Is Brobot at the exit?: " + brobot.isAtExit());
		}
		//System.out.println("Brobot's direction at termination is: " + brobot.getCurrentDirection());
		//System.out.println("while loop has terminated");
		
		
		return brobot.stepTowardsExit();
	}

	@Override
	public float getEnergyConsumption() {
		//default
		return 0;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return brobot.getOdometerReading();
	}

}
