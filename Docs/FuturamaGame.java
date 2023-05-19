//
//  FuturamaGame.java
//  Futurama
//
//  Created by Fraser on 9/06/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

public class FuturamaGame {
	
	public static final int LEFT_BOUNDARY = 50;
	public static final int RIGHT_BOUNDARY = 500;
	
	public static final int TITLE = 1;
	public static final int INTRO = 2;
	public static final int LEVEL = 6;
	public static final int MENU = 3;
	public static final int GAME = 4;
	public static final int END = 5;
	public static final int ASS = 7;	
	public static final int DEFEATED = 9;
	public static final int HIGHSCORES = 8;
	
	public static final int PENDING = 0;
	public static final int WON = 1;
	public static final int SHOT = 2;
	public static final int LANDED = 3;
	
	public static final int POINTS = 100;
	
	private int stage;
	private int ass;
	private int timeCounter;
	public int outcome;
	private int landedInvader;
	private int score;
	private int rank;
	private int level;
	private String highScores;
	
	public FuturamaGame () {
		timeCounter = 0;
		ass = 0;
		stage = TITLE;
		outcome = PENDING;
		landedInvader = 5;
		score = 0;
		rank = 0;
		level = 1;
		highScores = "";
	}
	
	public void increaseCounter () {
		timeCounter++;
	}
	
	public void resetCounter () {
		timeCounter = 0;
	}	

	public int isItTime () {
		return timeCounter;
	}
	
	public void increaseAss () {
		ass++;
	}
	
	public int getAss () {
		return ass;
	}	
	
	public void setOutcome ( int result) {
		outcome = result;
	}
	
	public int getOutcome () {
		return outcome;
	}	
	
	public void increaseScore(int points){
		score = score + points*POINTS;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setRank(int placing) {
		rank = placing;
	}
	
	public int getRank() {
		return rank;
	}
	
	public void setLandedInvader (int index) {
		landedInvader = index;
	}
	
	public int getLandedInvader () {
		return landedInvader;
	}	
	public void setStage (int newStage) {
		stage = newStage;
	}
	
	public int getStage () {
		return stage;
	}
	
	public void increaseLevel() {
		level++;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setHighScores(String scores) {
		highScores = scores;
	}
	
	public String getHighScores () {
		return highScores;
	}
}
