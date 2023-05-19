//
//  Shooter.java
//  Futurama
//
//  Created by Fraser on 9/06/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Shooter {
	
	public static final int STATIONARY = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int SPEED = 5;
	
	public static final int MAX_BULLETS = 3;

	private int direction;
	private int shooterLeft;
	private int shooterRight;
	private int shooterTop;
	private int shooterBottom;
	
	private Image shooterDesign;
	private Rectangle bullets[];
	private int numberOfBullets;
	private Rectangle outline;
	
	public Shooter (Image shooterImage) {
		shooterLeft = 1;
		shooterTop = 8;
		shooterRight = 31;
		shooterBottom = 14;
		direction = STATIONARY;
		shooterDesign = shooterImage;
		outline = new Rectangle(260, 370, shooterRight, shooterBottom);
		
		bullets = new Rectangle[MAX_BULLETS];
		numberOfBullets = 0;
		for (int i=0; i<numberOfBullets; i++) {
			bullets[i] = new Rectangle(10,10,2,8);
		}
	}
	
	public void drawShooter ( Graphics g ) {
		g.drawImage(shooterDesign, outline.x-shooterLeft, outline.y-shooterTop, null);
	}	
	
	public void changeDirection ( int change ) {
		direction = change;
	}	
	
	public void moveShooter () {
		if (direction == LEFT) {
			if ( outline.x > FuturamaGame.LEFT_BOUNDARY ) {
				outline.x -= SPEED;				
			}
		} else if (direction == RIGHT) {
			if ( outline.x < FuturamaGame.RIGHT_BOUNDARY ) {
				outline.x += SPEED;				
			}
		}
	}	
	
	public boolean isHit (Invader invaders[][], Point bomb, int index) {
		if ( outline.contains(bomb) ) {
			invaders[0][0].deleteBomb(index);
			return true;
		}
		return false;
	}
	
	
	public void fireBullet (AudioClip gunFire) {
		if (numberOfBullets < MAX_BULLETS) {
			bullets[numberOfBullets] = new Rectangle(outline.x + 15, outline.y -7, 2, 8);
			numberOfBullets++;
			gunFire.play();
		}
	}
	
	public void moveBullet (int position) {
		
		if ( bullets[position].y > 0 ) {
			bullets[position].translate(0,-5*2);
		} else {
			deleteBullet(position);
		}
	}	
	
	public void deleteBullet(int position) {
		for (int i=position; i < numberOfBullets-1; i++) {
				bullets[i].move(bullets[i+1].x,bullets[i+1].y);
		}
	bullets[numberOfBullets-1].move(10,10);
	numberOfBullets--;	
	}
	
	public int getBulletNumber () {
		return numberOfBullets;
	}
	
	public Rectangle getBulletPos (int index) {
		return bullets[index];
	}	
	
	public void resetBullets () {
		numberOfBullets = 0;
	}
	
	public void drawBullet (Graphics g, int index) {
		g.setColor(Color.WHITE);
		g.fillRect(bullets[index].x,	bullets[index].y, bullets[index].width, bullets[index].height);
	}		
}