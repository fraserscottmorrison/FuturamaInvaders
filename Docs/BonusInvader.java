//
//  BonusInvader.java
//  Futurama
//
//  Created by Fraser on 4/07/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BonusInvader {
	
	public static final int STATIONARY = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int SPEED = 5;
	
	private int bonusLeft;
	private int bonusRight;
	private int bonusTop;
	private int bonusBottom;	
	
	private int timeOfDeath;
	private int direction;
	private Rectangle outline;
	private Image bonusDesign;
	private Image bonusExplosion;
	private boolean isActive;
	private boolean exiting;
	
	public BonusInvader (Image bonusImage, Image imageC) {
		
		bonusLeft = 5;
		bonusRight = 30;
		bonusTop = 2;
		bonusBottom = 15;	
		
		timeOfDeath = 0;
		outline = new Rectangle(-60, 30, bonusRight, bonusBottom);
		bonusDesign = bonusImage;
		bonusExplosion = imageC;
		isActive = false;
		exiting = false;	
		direction = STATIONARY;
	}
	
	public void activate () {
		isActive = true;
		direction = RIGHT;
	}
	
	public boolean isActive () {
		return isActive;
	}
	
	public void drawBonus ( Graphics g, int time ) {
		if (isActive) {
			g.drawImage(bonusDesign,outline.x-bonusLeft,outline.y-bonusTop,null);
		} else if ( timeOfDeath + 2 > time ) {
			g.drawImage(bonusExplosion, outline.x-bonusLeft,outline.y-bonusTop,null);
		} else if ( timeOfDeath + 2 <= time && timeOfDeath + 15 > time ) {
			Font font1 = new Font("Courier", Font.PLAIN,  18);
			g.setFont(font1);
			g.setColor(Color.white);
			g.drawString("500", outline.x-bonusLeft+6,outline.y-bonusTop+13);
		}
}
	
	public void moveBonus () {
		if (direction == LEFT) {
			outline.x -= SPEED;
			if ( outline.x < FuturamaGame.LEFT_BOUNDARY || (int)(Math.random()*50) == 0 ) {
				changeDirection(RIGHT);
			}		

		} else if (direction == RIGHT) {
			outline.x += SPEED;				
			if ( outline.x > FuturamaGame.LEFT_BOUNDARY ) {
				if ( (outline.x > FuturamaGame.RIGHT_BOUNDARY || (int)(Math.random()*50) == 0) && !exiting) {
					changeDirection(LEFT);
				}
			}
		}
		
		if (exiting && outline.x > FuturamaGame.RIGHT_BOUNDARY + 80) {
			resetBonus();
		}
	}		
	
	public void changeDirection (int newDirection) {
		direction = newDirection;
	}
	
	public void timeToExit () {
		exiting = true;
	}
	
	public void setTimeOfDeath(int time) {
		timeOfDeath = time;
	}
	
	public int getTimeOfDeath() {
		return timeOfDeath;
	}	
	
	public void resetBonus () {
		isActive = false;
		outline = new Rectangle(-60, 30, bonusRight, bonusBottom);
		exiting = false;	
		direction = STATIONARY;
	}
	
	public boolean isHit (Shooter shooter, Rectangle bullet, int index) {
		if ( outline.intersects(bullet) ) {
			shooter.deleteBullet(index);
			isActive = false;
			return true;
		}
		return false;
	}

}
