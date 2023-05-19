//
//  FuturamaJPanel.java
//  Futurama
//
//  Created by Fraser on 7/06/06.
//  Copyright 2006 __MyCompanyName__. All rights reserved.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.applet.*;
import java.net.*;
import java.io.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;


public class FuturamaJPanel extends JPanel implements ActionListener, KeyListener, MouseListener {
	
	private Image title, background, warOver, congratsA, congratsAS, congratsASS, ship, landed, lela, fry1, fry2, fry3, highScores, bomb, bomb2, explosion, bonusExplosion;
	
	private Image imageA, imageB, bonusDesign;
	private Invader invaders[][];
	private BonusInvader bonusInvader;
	
	private FuturamaGame futurama;
	private Image shooterDesign;
	private Shooter shooter;
	private Shield shields[];
	private AudioClip gunFire, bells, laugh, boom, type, buzz, defeated, intro, blip, landing, orders, letsRock, dangerZone, whir, madMan, lastOne, decend, hit;
	
	private Timer time;

	public FuturamaJPanel() {
		
		title = loadImage("futurama_title.jpg");
		background = loadImage("Futurama.jpg");
		warOver = loadImage("War_over.jpg");
		congratsA = loadImage("CongratsA.jpg");
		congratsAS = loadImage("CongratsAS.jpg");
		congratsASS = loadImage("CongratsASS.jpg");
		lela = loadImage("Lela.jpg");
		ship = loadImage("Invader.jpg");
		landed = loadImage("End.jpg");		
		fry1 = loadImage("Fry1.jpg");
		fry2 = loadImage("Fry2.jpg");
		fry3 = loadImage("Fry.jpg");
		highScores = loadImage("Highscores.jpg");
		
		bonusDesign = loadImage("Invader4.gif");
		shooterDesign = loadImage("Shooter.gif");
		bomb = loadImage("Bomb.gif");
		bomb2 = loadImage("Bomb2.gif");
		explosion = loadImage("Explosion.gif");
		bonusExplosion = loadImage("Bonus Explosion.gif");
		
		gunFire = loadSound("Bullet.aif");
		bells = loadSound("Bells.aif");
		laugh = loadSound("Hehehe.aif");
		boom = loadSound("Boom.aif");
		type = loadSound("Type.aif");
		buzz = loadSound("buzz.aif");
		defeated = loadSound("Defeated.aif");
		intro = loadSound("Intro.aif");
		blip = loadSound("Invader.aif");
		letsRock = loadSound("Lets rock.aif");
		dangerZone = loadSound("Danger Zone.aif");
		landing = loadSound("Landing.aif");
		orders = loadSound("Orders.aif");
		whir = loadSound("Whir.aif");
		madMan = loadSound("Madman.aif");
		lastOne = loadSound("Last one.aif");
		decend = loadSound("Decend.aif");
		hit = loadSound("Hit.aif");
		
		initialiseInvaders();
		initialiseShields();
		shooter = new Shooter(shooterDesign);
		futurama = new FuturamaGame();
		bonusInvader = new BonusInvader(bonusDesign, bonusExplosion);

		addKeyListener(this);
		addMouseListener(this);
		time = new Timer(50, this);
		bells.play();
		
	}
	
	private void initialiseInvaders () {
		invaders = new Invader[Invader.INVADERS_ROWS][Invader.INVADERS_COLUMNS];
		for(int i = 0; i < Invader.INVADERS_ROWS; i++) {
			for(int j = 0; j < Invader.INVADERS_COLUMNS; j++) {
				if ( i == 0 ) {
					imageA = loadImage("Invader1a.gif");
					imageB = loadImage("Invader1b.gif");
				} else if ( i == 1 ) {
					imageA = loadImage("Invader2a.gif");
					imageB = loadImage("Invader2b.gif");
				} else if ( i == 2 ) {
					imageA = loadImage("Invader2b.gif");
					imageB = loadImage("Invader2a.gif");
				} else if ( i == 3 ) {
					imageA = loadImage("Invader3a.gif");
					imageB = loadImage("Invader3b.gif");
				} else if ( i == 4 ) {
					imageA = loadImage("Invader3b.gif");
					imageB = loadImage("Invader3a.gif");
				}
				invaders[i][j] = new Invader( (100 +j*35), (-400 + i*Invader.ROW_HEIGHT), imageA, imageB, explosion, bomb, bomb2, i );
			}
		}	
	}
	
	private void decendInvaders() {
		for (int j=0; j < Invader.INVADERS_COLUMNS; j++) {
			for (int i=0; i < Invader.INVADERS_ROWS; i++) {
				invaders[i][j].decend();
			}
		}
	}
	
	public void initialiseShields() {
		shields = new Shield[4];
		for (int i = 0; i < 4; i++) {
			shields[i] = new Shield(92 + 113*i, 296, madMan);
		}
	}
	
	public void actionPerformed (ActionEvent e) {
		
		if (futurama.getStage() == futurama.INTRO) {
			intro();
		
		}	else if (futurama.getStage() == futurama.LEVEL) {
			level();

 		} else if (futurama.getStage() == futurama.GAME) {
			if (futurama.isItTime() == 1) {
				dangerZone.play();
			}	
			if (invaders[0][0].getY() < (30 + 10*futurama.getLevel()) ) {
				decendInvaders();
				if (futurama.isItTime() == 0) {
					decend.play();
				}
			} else {
				game();
			}

		} else if (futurama.getStage() == futurama.ASS ) {
			ass();
			
		} else if (futurama.getStage() == futurama.DEFEATED ) {
			defeated();		
		}
		
		futurama.increaseCounter();			
		repaint();
	}
	
	public void intro () {
		if ( futurama.isItTime() == 260 ) {
			letsRock.play();
		} else if (futurama.isItTime() > 410 ) {
			futurama.setStage(futurama.LEVEL);
		}		
	}
	
	public void level () {	
		for (int k=0; k < shooter.getBulletNumber(); k++) {	
			shooter.moveBullet(k);
			repaint();
		}
	}
	
	public void game () {
		boolean endOfGame = false;
		shooter.moveShooter();
		for(int j = 0; j < Invader.INVADERS_COLUMNS; j++) {
			for(int i = 0; i < Invader.INVADERS_ROWS; i++) {
				
				//  If an invader has reached the bottom then the game ends
				if (invaders[i][j].isAlive() && invaders[i][j].getY() > 350) {
					futurama.setLandedInvader(i);
					futurama.setOutcome(futurama.LANDED);
					endOfGame = true;
				}
				if (invaders[i][j].isAlive()) {
					for ( int k = 0; k < 4; k++) {
						shields[k].intersects(invaders[i][j].getRect());
					}
				}
				if (invaders[0][0].getInvadersLeft() == 1 && invaders[i][j].isAlive() && invaders[i][j].getY() == 320) {
					landing.play();	
				}
			}		
			
			//  Changes direction of the invaders when they reach teh side of the screen
			if (futurama.isItTime() % invaders[0][0].getSpeed() == 0 ) {
				if ( j==0 && (invaders[0][invaders[0][0].getRightInvader()].getX() > FuturamaGame.RIGHT_BOUNDARY 
											|| invaders[0][invaders[0][0].getLeftInvader()].getX() < FuturamaGame.LEFT_BOUNDARY)) {
					invaders[0][invaders[0][0].getRightInvader()].changeDirection();
				}		
				invaders[4][j].move();	
				if (j==0 && invaders[4][j].isDropping() == true && futurama.isItTime() < 300) {
					orders.play();
				}
				
			} 
			if (futurama.isItTime() % invaders[0][0].getSpeed() == invaders[0][0].getStagger() ) {
				invaders[3][j].move();
					blip.play();
				} 
			
			if (futurama.isItTime() % invaders[0][0].getSpeed() == invaders[0][0].getStagger() * 2 ) {
				invaders[2][j].move();
			} 
			
			if (futurama.isItTime() % invaders[0][0].getSpeed() == invaders[0][0].getStagger() * 3 ) {
				invaders[1][j].move();
			} 
			
			if (futurama.isItTime() % invaders[0][0].getSpeed() == invaders[0][0].getStagger() * 4 ) {
				invaders[0][j].move();
				if ( j == Invader.INVADERS_COLUMNS-1 ) {
					invaders[0][0].stopDropping();
				}
			}
		}
		
		//  Tells invader to drop a bomb
		if (futurama.isItTime() % 40 == 15 ) {
			dropBomb(invaders);
		}
		
		//  Moves the bombs and checks if they hit the shooter or a shield
		for (int i=0; i < invaders[0][0].getBombNumber(); i++) {	
			invaders[0][5].moveBomb(i);
			if (shooter.isHit(invaders, invaders[0][0].getBombPos(i), i)) {
				endOfGame = true;
			}
			for ( int j = 0; j < 4; j++) {
				shields[j].isHit(invaders, invaders[0][0].getBombPos(i), i);
			}		
			repaint();
		}			
		
		//  Moves the bullets from the shooter and check if they hit an invader or a shield
		for (int k=0; k < shooter.getBulletNumber(); k++) {	
			shooter.moveBullet(k);
			repaint();
			
			for ( int j = 0; j < 4; j++) {
				shields[j].isHit(shooter, shooter.getBulletPos(k), k, futurama.isItTime());
			}		
			
			for (int j=0; j < Invader.INVADERS_COLUMNS; j++) {
				for (int i=0; i < Invader.INVADERS_ROWS; i++) {
	
					boolean invaderHit = invaders[i][j].isHit(shooter, shooter.getBulletPos(k), k);
					if (invaderHit) {
						hit.play();
						futurama.increaseScore(1);
						invaders[i][j].setTimeOfDeath(futurama.isItTime());
						invaders[0][0].setLeftInvader(outermostInvader(0, 0, 1));
						invaders[0][0].setRightInvader(outermostInvader(0, Invader.INVADERS_COLUMNS-1, -1));

						if (invaders[0][0].getInvadersLeft() == 1 ) {
							lastOne.play();
						} else if (invaders[0][0].getInvadersLeft() == 0 ) {
							newLevel();
						}
					}
				}	
			}	
			if (bonusInvader.isActive() ) {
				if ( bonusInvader.isHit(shooter, shooter.getBulletPos(k), k) ) {
					whir.stop();
					hit.play();
					bonusInvader.setTimeOfDeath(futurama.isItTime());
					futurama.increaseScore(5);
				}
			}
		}
		if ( futurama.isItTime() == 750 ) {
			bonusInvader.activate();
			whir.loop();
		}
		if ( bonusInvader.isActive() ) {
			bonusInvader.moveBonus();
			if (futurama.isItTime() == 750 + 200) {
				bonusInvader.timeToExit();
			}
			if ( !bonusInvader.isActive() ) {
				whir.stop();
			}
		}		
		if (endOfGame) {
			futurama.resetCounter();
			gameOver();
		}
	}	

	public void newLevel() {
		whir.stop();
		dangerZone.stop();	
		lastOne.stop();
		initialiseInvaders();
		bonusInvader.resetBonus();
		futurama.increaseLevel();
		futurama.setStage(futurama.LEVEL);
	}
	
	public void gameOver() {
		whir.stop();
		dangerZone.stop();
		lastOne.stop();
		boom.play();	
		setHighScores();
		futurama.setStage(futurama.ASS);
		futurama.resetCounter();	
	}



	public void setHighScores() {
		try{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse("http://idisk.mac.com/fraserscottmorrison/Public/Games/Futurama Invaders/Highscores.xml");
		
		doc.getDocumentElement ().normalize ();
        System.out.println ("Root element of the doc is " + doc.getDocumentElement().getNodeName());
        
        NodeList listOfScores = doc.getElementsByTagName("score");
        int totalPersons = listOfScores.getLength();
        System.out.println("Total no. of scores : " + totalPersons);

        for(int i=9; i>=0; i--){
            Node firstNode = listOfScores.item(i);
            
            if(firstNode.getNodeType() == Node.ELEMENT_NODE){
            	Element firstElement = (Element)firstNode;
            	firstElement.appendChild(doc.createElement("rule-syntax"));
                NodeList textFNList = firstElement.getChildNodes();
            	int highscore = Integer.parseInt(((Node)textFNList.item(0)).getNodeValue().trim());

            	if (highscore < futurama.getScore()) {
            		futurama.setRank(i);
            	} else break;
            }
        }
        

        System.out.println("rank: " + futurama.getRank());
        URL url = new URL("http://idisk.mac.com/fraserscottmorrison/Public/Games/Futurama Invaders/Highscores.xml");
        URLConnection      urlConn; 
        DataOutputStream   dos; 
        DataInputStream    dis;


        urlConn = url.openConnection(); 
        urlConn.setDoInput(true); 
        urlConn.setDoOutput(true); 
        urlConn.setUseCaches(false); 
        urlConn.setRequestProperty ("Content-Type", "application/x-www-form-urlencoded");

        dos = new DataOutputStream (urlConn.getOutputStream()); 
        String message = "NEW_ITEM=" + URLEncoder.encode("new text"); 
        dos.writeBytes(message); 
        dos.flush(); 
        dos.close();
		
		} catch (Exception e){
			System.out.println(e);
		}
	}

	
	public void setHighScoresOld() {
		BufferedReader bR;
		PrintWriter pW; 
		String scores = "", line;
		int previousScore;
		
		try{
			File myFile = new File( "/Library/Futurama Invaders" ); 
			if ( !myFile.exists() ) { 
				myFile.mkdir();
				pW = new PrintWriter( new FileWriter( "/Library/Futurama Invaders/Highscores.txt" )  );
				String initialScores = "ART 10000\nBEX 9000\nCAT 8000\nDAN 7000\nEVE 6000\nFRY 5000\nGED 4000\nHAL 3000\nIKE 2000\nJAN 1000";
				pW.print(initialScores);
				pW.close(); 
			}

			bR = new BufferedReader (new FileReader( "/Library/Futurama Invaders/Highscores.txt" ) ); 
			
			for (int i = 1; i <= 10; i++){
				line = bR.readLine();
				if (line != null) {
					previousScore = Integer.parseInt(line.substring(4));
					if (previousScore < futurama.getScore() && futurama.getRank() == 0) {
						scores += "ASS " + futurama.getScore() + "\n";
						futurama.setRank(i);
						i++;
					}
				}
				if (i <= 10) {
					scores += line + "\n";
				}
			} 
			pW = new PrintWriter( new FileWriter( "/Library/Futurama Invaders/Highscores.txt" )  ); 		
			pW.print(scores);
			futurama.setHighScores(scores);
			bR.close(); 
			pW.close(); 
		} catch( IOException e ) { 
			System.out.println(  e  ); 
		} 
	}
			
	public void ass() {	
		if (futurama.getAss() >= 3 && futurama.isItTime() == 1 ){
			laugh.play();
		} else if (futurama.getAss() >= 3 && futurama.isItTime() == 40 ){
			defeated.play();
			futurama.setStage(futurama.DEFEATED);
		} 
	}

	public void defeated() {
		if (futurama.isItTime() == 200) {
			futurama.setStage(futurama.HIGHSCORES);
		}	
	}

	//	
	//  Picks a random alive invader from a row to drop a bomb
	//	
	public void dropBomb(Invader invader[][]) {
		
		final int COLUMN_INDEX = (int)(Math.random()*Invader.INVADERS_COLUMNS);
		int colIndex = COLUMN_INDEX;
		int rowIndex = 0;
			
		while ( rowIndex < Invader.INVADERS_ROWS ) {
			if ( invader[rowIndex][colIndex].isAlive() ) {
				invaders[rowIndex][colIndex].fireBomb();
				rowIndex = Invader.INVADERS_ROWS;
			} else if ( colIndex == Invader.INVADERS_COLUMNS - 1) {
				colIndex = 0;
			} else if ( colIndex == COLUMN_INDEX - 1 ) {
				rowIndex ++;
			} else {
				colIndex ++;
			}
		}
	}		

	public int outermostInvader (int i, int j, int increment) {
		while (invaders[i][j].isAlive() == false) {
			i += 1;
			if (i == Invader.INVADERS_ROWS) {
				i=0;
				j += increment;
			}
			if ( j < 0 || j >= Invader.INVADERS_COLUMNS ) {
				return 0;
			}
		}	
		return j;
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		if (futurama.getStage() == futurama.TITLE) {
			title(g);
			
		} else if (futurama.getStage() == futurama.INTRO) {		
			intro(g);
			
		} else if (futurama.getStage() == futurama.LEVEL || futurama.getStage() == futurama.MENU || futurama.getStage() == futurama.GAME){
			game(g);
		
		} else if (futurama.getStage() == futurama.ASS){
			ass(g);

		} else if (futurama.getStage() == futurama.DEFEATED) {
			defeated(g);
			
		} else if (futurama.getStage() == futurama.HIGHSCORES) {
			highScores(g);
		}	
	}
	
	public void title (Graphics g) {
		g.drawImage(title,0,0,null);
		Font font1 = new Font("Helvetica", Font.PLAIN,  20);
		g.setFont(font1);
		g.setColor(Color.white);
		if (futurama.isItTime() == 0) {
			g.drawString("click mouse to play", 200,23);
		} else {
			g.drawString("press any key to play", 200,23);
		}
	}
		
	public void intro (Graphics g) {
		if (futurama.isItTime() < 60) {
			g.drawImage(lela,0,0,null);
		} else if ( futurama.isItTime() < 260 ){
			g.drawImage(ship,0,0,null);
		} else if ( futurama.isItTime() < 340 ){
			g.drawImage(fry1,0,0,null);
		} else if ( futurama.isItTime() < 370 ){
			g.drawImage(fry2,0,0,null);
		} else {
			g.drawImage(fry3,0,0,null);
		}			
	}
	
	public void game (Graphics g) {
		g.drawImage(background,0,0,null);
		shooter.drawShooter(g);
		
		Font font1 = new Font("Helvetica", Font.PLAIN,  20);
		g.setFont(font1);
		g.setColor(Color.white);
		String buffer = "            ";
		for (int i = 9; i < futurama.getScore(); i=(i+1)*10-1) {
			buffer = buffer.substring(2);
		}
		g.drawString(buffer+futurama.getScore(), 485,23);
	
		
		for(int i = 0; i < Invader.INVADERS_ROWS; i++) {
			for(int j = 0; j < Invader.INVADERS_COLUMNS; j++) {
				invaders[i][j].drawInvader(g, futurama.isItTime());
			}		
		}		
		for (int i=0; i < shooter.getBulletNumber(); i++) {
			shooter.drawBullet(g, i);
		}		
		for (int i=0; i < invaders[0][0].getBombNumber(); i++) {
			invaders[0][5].drawBomb(g, i, futurama.isItTime());
		}			
		for (int i=0; i < 4; i++) {
			shields[i].drawShield(g);
		}	
		bonusInvader.drawBonus(g, futurama.isItTime());
		
		if (futurama.getStage() == futurama.LEVEL) {
			g.setColor(Color.white);
			font1 = new Font("Helvetica", Font.PLAIN,  28);
			g.setFont(font1);
			g.drawString("Level " + futurama.getLevel(), 240,160);	
		}
	}

	public void ass (Graphics g) {
		if (futurama.isItTime() < 37 && futurama.getAss() == 0) {
			g.drawImage(warOver,0,0,null);
		} else if (futurama.getAss() == 0) {
			g.drawImage(congratsA,0,0,null);
			if (futurama.isItTime()/10%2 == 1) {
				g.setColor(Color.white);
				g.fillRect(219,330,28,7);
			}
		}  else if (futurama.getAss() == 1) {
			g.drawImage(congratsA,0,0,null);
			if (futurama.isItTime()/10%2 == 1) {
				g.setColor(Color.white);
				g.fillRect(274,330,28,7);
			}		
		} else if (futurama.getAss() == 2) {
			g.drawImage(congratsAS,0,0,null);
			if (futurama.isItTime()/10%2 == 1) {
				g.setColor(Color.white);
				g.fillRect(329 ,330,28,7);
			}		
		}	else if (futurama.getAss() == 3 && futurama.isItTime() < 40) {
			g.drawImage(congratsASS,0,0,null);
			
		}	else if (futurama.getAss() >= 3 && futurama.isItTime() >= 40) {
			g.drawImage(landed,0,0,null);
		}
			
		if (futurama.getAss() < 3 || (futurama.getAss() == 3 && futurama.isItTime() < 40)) {
			shooter.drawShooter(g);
			if (futurama.getOutcome() == futurama.LANDED ) {
				for (int j = 0; j < Invader.INVADERS_COLUMNS; j++) {
					invaders[futurama.getLandedInvader()][j].drawInvader(g, 10000000);
				}
			}
		}		
	}

	public void defeated (Graphics g) {
		g.drawImage(landed,0,0,null);
	}

	public void highScores (Graphics g) {
		g.drawImage(highScores,0,0,null);

		g.setColor(Color.white);
		Font font1 = new Font("Helvetica", Font.PLAIN,  20);
		g.setFont(font1);
		g.drawString("HIGH-", 489,120);
		g.drawString("SCORES", 475,140);
		
		font1 = new Font("Helvetica", Font.PLAIN,  18);
		g.setFont(font1);		
		
		String highScores = futurama.getHighScores();
		String name, score;
		
		for (int i = 1; i <= 10; i++) {
			g.setColor(Color.black);	
			if (futurama.getRank() == i) {
				g.setColor(Color.white);
			}
			name = highScores.substring(0,highScores.indexOf(' '));
			score = highScores.substring(highScores.indexOf(' ') +1 ,highScores.indexOf('\n'));
			if (score.length() == 4) {
				score = "  " + score;
			} else if (score.length() == 3) {
				score = "    " + score;
			}
		highScores = highScores.substring(highScores.indexOf('\n') +1);
			g.drawString(name, 185, 95 + i*18);
			g.drawString(score, 230, 95 + i*18);
		} 

	}	
		
	//
	//  This method loads an image using a MediaTracker to speed image display
	//
	private Image loadImage(String fileName) {
		URL url = getClass().getResource(fileName);
		Image i = Toolkit.getDefaultToolkit().createImage(url);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(i,0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {}
		return i;
	}
	
	private AudioClip loadSound (String fileName) {
		URL url = getClass().getResource(fileName);
		return Applet.newAudioClip(url);
	}
	
	public void keyPressed (KeyEvent e){
		
		if (futurama.getStage() == futurama.TITLE){
			futurama.setStage(futurama.INTRO);
			futurama.resetCounter();
			intro.play();
			
		} else if (futurama.getStage() == futurama.INTRO){
			intro.stop();
			futurama.setStage(futurama.LEVEL);
			
		} else if (futurama.getStage() == futurama.LEVEL){
				futurama.setStage(futurama.GAME);
				futurama.resetCounter();
				shooter.resetBullets();
			
		} else if (futurama.getStage() == futurama.MENU || futurama.getStage() == futurama.GAME){
			
			if (futurama.getStage() == futurama.MENU){
				futurama.setStage(futurama.GAME);
				futurama.resetCounter();
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				shooter.changeDirection(1);
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				shooter.changeDirection(2);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				shooter.changeDirection(0);
			}		
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) {
				shooter.fireBullet(gunFire);		
			}

		} else if (futurama.getStage() == futurama.ASS){
			if (futurama.isItTime() > 37 ) {
				futurama.increaseAss();
				if (futurama.getAss() <= 3) {
					type.play();
					if (futurama.getAss() == 3) {				
						futurama.resetCounter();
					}
				}	
			}


		} else if (futurama.getStage() == futurama.DEFEATED){
			defeated.stop();
			futurama.setStage(futurama.HIGHSCORES);
						
		} else if (futurama.getStage() == futurama.HIGHSCORES) {
			initialiseInvaders();
			initialiseShields();
			shooter = new Shooter(shooterDesign);
			futurama = new FuturamaGame();
			bonusInvader = new BonusInvader(bonusDesign, bonusExplosion);
			futurama.setStage(futurama.TITLE);
			bells.play();	
		}
	}
		
	public void keyReleased (KeyEvent e){}
	public void keyTyped (KeyEvent e){}
	public void mousePressed (MouseEvent e){
		requestFocus();
		time.start();
		if (futurama.getStage() == futurama.TITLE){
			futurama.setStage(futurama.INTRO);
			intro.play();
			futurama.resetCounter();
		}
	}
	public void mouseClicked (MouseEvent e){}
	public void mouseEntered (MouseEvent e){}
	public void mouseExited (MouseEvent e){}
	public void mouseReleased (MouseEvent e){}
}
