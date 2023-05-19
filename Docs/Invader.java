//
//  Invader.java
//  Futurama
//
//  Created by Fraser on 8/06/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Invader {
	
	public static final int INVADERS_COLUMNS = 11;
	public static final int INVADERS_ROWS = 5;
	public static final int ROW_HEIGHT = 35;
	public static final int DROP_HEIGHT = 20;
	public static final int DISTANCE = 5;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	public static int leftInvader;
	public static int rightInvader;
	public static int direction;
	private static boolean dropDown;
	private static int invadersLeft;
	private static int speed;
	private static int stagger;
	
	private boolean isAlive;
	private int rowNumber;
	
	private Image designA;
	private Image designB;
	private Image explosion;
	private Image bombA;
	private Image bombB;
	
	private int shipLeft;
	private int shipRight;
	private int shipTop;
	private int shipBottom;
	private Rectangle outline;
	private int timeOfDeath;
	
	private static Point bombs[];
	private static int numberOfBombs;
	
	public  Invader (int newX, int newY, Image imageA, Image imageB, Image imageC, Image imageD,Image imageE, int row) {
		rowNumber = row;
		designA = imageA;
		designB = imageB;
		explosion = imageC;
		bombA = imageD;
		bombB = imageE;
		
		dropDown = false;
		isAlive = true;
		invadersLeft = INVADERS_COLUMNS * INVADERS_ROWS;
		
		timeOfDeath = 0;
		direction = RIGHT;
		leftInvader = 0;
		rightInvader= INVADERS_COLUMNS-1;
		speed = 22;
		stagger = 4;
		outline = new Rectangle(newX, newY, shipRight, shipBottom);

		shipLeft = 4;
		shipTop = 0;
		shipRight = 25;		
		shipBottom = 14;
		
		outline = new Rectangle(newX, newY, shipRight, shipBottom);
		
		if ( row == 0 ) {
			shipLeft = 4;
			shipTop = 3;
			outline.x += 3;
			outline.width -= 8;
			outline.height -= 2;
		}
		if ( row == 3 || row == 4 ) {
			shipLeft = 2;
			outline.height -= 3;
		}		
		
		bombs = new Point[10];
		numberOfBombs = 0;
		for (int i=0; i<numberOfBombs; i++) {
			bombs[i] = new Point(10,10);
		}		
	}
	
	public int getX () {
		return outline.x;
	}
	
	public int getY () {
		return outline.y;
	}
	
	public int getSpeed () {
		return speed;
	}
	
	public int getStagger () {
		return stagger;
	}	
	
	public Rectangle getRect () {
		return outline;
	}		
	
	public void drawInvader ( Graphics g, int time ) {
		if (isAlive) {
			g.drawImage(designA, outline.x - shipLeft, outline.y - shipTop, null);
		} else if ( timeOfDeath + 2 > time ) {
			g.drawImage(explosion, outline.x - shipLeft, outline.y - shipTop, null);
		}
	}
	
	public void changeDirection () {
		if (direction == RIGHT) {
			direction = LEFT;
		} else {
			direction = RIGHT;
		}
		dropDown = true;
	}		
	
	public boolean isDropping () {
		return dropDown;
	}
	
	public void stopDropping () {
		dropDown = false;
	}		
	
	public void decend() {
		outline.y += DISTANCE*3;
	}
	
	public void setTimeOfDeath(int time) {
		timeOfDeath = time;
	}
	
	public int getTimeOfDeath() {
		return timeOfDeath;
	}

	public void move () {
		if (direction == RIGHT) {
			outline.x += DISTANCE;
		} else {
			outline.x -= DISTANCE;
		}	
		if (dropDown) {
			outline.y += DROP_HEIGHT;
		}
		
		Image tempDesign = designA;
		designA = designB;
		designB = tempDesign;
	}	
	
	public boolean isHit (Shooter shooter, Rectangle bullet, int index) {
		if (isAlive) {
			if ( outline.intersects(bullet) ) {
				isAlive = false;
				invadersLeft -= 1;
				shooter.deleteBullet(index);
				if (invadersLeft % INVADERS_COLUMNS == 0) {
					speed -= 5;
					stagger -= 1;
				}
				if (invadersLeft == 1) {
					speed = 1;
				}
				return true;
			}
		}
		return false;
	}
	
	public int getInvadersLeft() {
		return invadersLeft;
	}		
	
	public boolean isAlive () {
		return isAlive;
	}
	
	 public void fireBomb () {
		 bombs[numberOfBombs] = new Point(outline.x + 8, outline.y+5);
		 numberOfBombs++;
	 }
	 
	 public void moveBomb (int position) {
		 if ( bombs[position].y < 500 ) {
			 bombs[position].translate(0,5);
		 } else {
			 deleteBomb(position);
		 }
	 }	
	 
	 public void deleteBomb(int position) {
		 for (int i=position; i < numberOfBombs-1; i++) {
			 bombs[i].move(bombs[i+1].x,bombs[i+1].y);
		 }
		 bombs[numberOfBombs-1].move(10,10);
		 numberOfBombs--;	
	 }
	 
	 public int getBombNumber () {
		 return numberOfBombs;
	 }
	 
	 public Point getBombPos (int index) {
		 return bombs[index];
	 }	
	 
	 public void drawBomb (Graphics g, int index, int time) {
		 if (time % 2 == 0) {
			 g.drawImage(bombA, bombs[index].x-5, bombs[index].y-5, null);
		 } else {
			 g.drawImage(bombB, bombs[index].x-5, bombs[index].y-3, null);			 
		 }
	 }
	
	public void setLeftInvader( int left ) {
		if (left > rightInvader) {
			left = rightInvader;
		}
		if (left > leftInvader) {
			leftInvader = left;
		}
	}

	public void setRightInvader( int right ) {
		if (right < leftInvader) {
			right = leftInvader;
		}
		if (right < rightInvader) {
			rightInvader = right;
		}
	}	
	
	public int getLeftInvader() {
		return leftInvader;
	}	
	
	public int getRightInvader() {
		return rightInvader;
	}		
	
	
}
