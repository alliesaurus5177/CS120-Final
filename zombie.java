//Allie Ramirez
//Time Invested: about 20 hours
//references:

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class zombie {
	Applet gameApplet;
	Image [] zombie;
	int xPos;
	int yPos;
	int xVel;
	int puddleX;
	int puddleY;
	int index;
	int counter;
	boolean onScreen;
	boolean gotShot;


	public zombie(Applet a, int x, int y){

		onScreen = true;
		gotShot = false;
		xPos = x;
		yPos = y;
		puddleX = -1000;
		puddleY = -1000;
		xVel = (int)(Math.random()* 15) + 1;
		index = 0;
		counter = 0;
		gameApplet = a;
		zombie = new Image[10];
		//first spot in array is for normal walking
		//TODO: make this a loop and populate it
		zombie[0] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/walk.png");
		zombie[1] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/walk1.png");
		zombie[2] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/walk2.png");
		//set up images for zombie being shot
		zombie [3]= gameApplet.getImage(gameApplet.getCodeBase(), "zombie/headshot2.png");
		zombie [4] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/headshot2.png");
		zombie [5] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/headshot.png");
		zombie [6] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/collapse.png");
		zombie [7] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/collapse1.png");
		zombie [8] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/collapse2.png");
		zombie [9] = gameApplet.getImage(gameApplet.getCodeBase(), "zombie/collapse3.png");
	}

	public void paintZombie(Graphics g){
		if (onScreen && !gotShot){
				g.drawImage(zombie[index], xPos, yPos, gameApplet);
			}
		if(onScreen && gotShot){
			g.drawImage(zombie[index], xPos, yPos, gameApplet);
			}
		g.drawImage(zombie[9], puddleX, puddleY, gameApplet);
		}
	public void shuffle(){
		if(!gotShot){
			if(xPos > -150){//makes them travel across screen
				xPos = (xPos - xVel);
				}
			else{//once they wander off screen, return them to start
				xPos = 1300;
				}
			//this makes headshot not happen. Cool.
			index=(index+1)%3;

			}
		if(gotShot){
		while(xVel > 0){
			xVel--;
			xPos = (xPos + xVel);
			}
		if (index < 9){
			index++;
			}
		else{
			if(counter < 15){
				index = 0;
				counter++;
				gotShot = false;
				puddleX = xPos;
				puddleY = yPos;
				xVel = (int)(Math.random()* (15 + counter)) ;
				xPos = 1300;
				yPos = (int)(Math.random() * 400);
				}
			else{
				onScreen = false;
				}
			}
		}

	}

//checks to see distance from mouse location to zombie head
	public double headShot(int xClick, int yClick) {
		int dx = xClick - (xPos + 60);
		int dy = yClick - (yPos + 60);
		return Math.sqrt(dx*dx + dy*dy);
	}
	public void shot(){
		gotShot = true;
		}

	public int getZombieY(){
		return yPos;
		}

}
