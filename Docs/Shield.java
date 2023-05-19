//
//  Shield.java
//  Futurama
//
//  Created by Fraser on 28/06/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.applet.*;
import java.net.*;

public class Shield {
	
	public static final int BLOCKS_ROWS = 7;
	public static final int BLOCKS_COLUMNS = 20;
	
	private boolean[][] isActive;
	private Rectangle[][] blocks;
	private Point lastKilled;
	private int lastCount;
	private int hitsInRow;
	private AudioClip madMan;
	
	public Shield (int x, int y, AudioClip mad) {

		madMan = mad;
		lastKilled = new Point(0,0);
		lastCount = 0;
		hitsInRow = 0;
		blocks = new Rectangle[BLOCKS_ROWS][BLOCKS_COLUMNS];
		isActive = new boolean[BLOCKS_ROWS][BLOCKS_COLUMNS];
		for(int i = 0; i < BLOCKS_ROWS; i++) {
			for(int j = 0; j < BLOCKS_COLUMNS; j++) {
				blocks[i][j] = new Rectangle(x + j*3, y + i*8,3,8);
				isActive[i][j] = true;
			}
		}
		
		isActive[0][0] = false;
		isActive[1][0] = false;
		isActive[0][1] = false;
		isActive[1][1] = false;
		isActive[2][0] = false;
		isActive[0][2] = false;
		isActive[BLOCKS_ROWS-1][5] = false;
		isActive[BLOCKS_ROWS-1][6] = false;
		isActive[BLOCKS_ROWS-2][6] = false;
		isActive[BLOCKS_ROWS-2][7] = false;
		isActive[BLOCKS_ROWS-1][7] = false;
		isActive[BLOCKS_ROWS-2][8] = false;
		isActive[BLOCKS_ROWS-1][8] = false;
		isActive[BLOCKS_ROWS-2][9] = false;
		isActive[BLOCKS_ROWS-1][9] = false;

		isActive[0][19] = false;
		isActive[1][19] = false;
		isActive[0][18] = false;
		isActive[1][18] = false;
		isActive[2][19] = false;
		isActive[0][17] = false;
		isActive[BLOCKS_ROWS-1][14] = false;
		isActive[BLOCKS_ROWS-1][13] = false;
		isActive[BLOCKS_ROWS-2][13] = false;
		isActive[BLOCKS_ROWS-2][12] = false;
		isActive[BLOCKS_ROWS-1][12] = false;
		isActive[BLOCKS_ROWS-2][11] = false;
		isActive[BLOCKS_ROWS-1][11] = false;
		isActive[BLOCKS_ROWS-2][10] = false;
		isActive[BLOCKS_ROWS-1][10] = false;
	}
	
	public void isHit (Shooter shooter, Rectangle bullet, int index, int counter) {

		for(int i = BLOCKS_ROWS-1; i >= 0 ; i--) {
			for(int j = BLOCKS_COLUMNS-1; j >= 0; j--) {
				if (isActive[i][j]) {
					if ( blocks[i][j].intersects(bullet) ) {
						isActive[i][j] = false;
						if (j > 0) {
							if ( blocks[i][j-1].intersects(bullet) ) {
								isActive[i][j-1] = false;
							}	
						}
						shooter.deleteBullet(index);
						if (j == lastKilled.y &&  counter - lastCount < 10) {
							hitsInRow++;
							if (hitsInRow == 3) {
								madMan.play();
							}
						} else {
							hitsInRow = 1;
						}
						lastKilled = new Point(i,j);
						lastCount = counter;
					}
				}
			}
		}
	}
	
	public void isHit (Invader invaders[][], Point bomb, int index) {
		for(int i = 0; i < BLOCKS_ROWS; i++) {
			for(int j = 0; j < BLOCKS_COLUMNS; j++) {
				if (isActive[i][j]) {
					if ( blocks[i][j].contains(bomb) ) {
						invaders[0][0].deleteBomb(index);
						isActive[i][j] = false;
					}
				}
			}
		}
	}	
	
	public void drawShield ( Graphics g) {
		g.setColor(Color.green);
		for(int i = 0; i < BLOCKS_ROWS; i++) {
			for(int j = 0; j < BLOCKS_COLUMNS; j++) {
				if (isActive[i][j]) {
					g.fillRect(blocks[i][j].x, blocks[i][j].y, blocks[i][j].width, blocks[i][j].height);				
				}
			}
		}
	}
	
	public void intersects (Rectangle rect) {
		for(int i = 0; i < BLOCKS_ROWS; i++) {
			for(int j = 0; j < BLOCKS_COLUMNS; j++) {	
				if (rect.y + rect.height*2 > blocks[i][j].y) {
					isActive[i][j] = false;
				}
			}
		}
	}

}
