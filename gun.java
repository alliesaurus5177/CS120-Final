//Allie Ramirez
//Time Invested: about 20 hours
//references:

import java.applet.*;
import java.awt.*;

public class gun extends Applet {
	Image [] gun; //names the image, makes array of images to be used
	Applet gameApplet; //references the main applet being used
	private int xPos;//xposition of image
	private int yPos; //yposition of image
	int index;
	private boolean isShooting;

	public gun(int x, int y, Applet a){
		index = 0;
		isShooting = false;
		xPos = x;
		yPos = y;
		gameApplet = a;
		gun = new Image [6];
		gun [0] = gameApplet.getImage(gameApplet.getCodeBase(), "gun/gun.png");
		gun [1] = gameApplet.getImage(gameApplet.getCodeBase(), "gun/gun1.png");
		gun [2] = gameApplet.getImage(gameApplet.getCodeBase(), "gun/gun2.png");
		gun [3] = gameApplet.getImage(gameApplet.getCodeBase(), "gun/gun3.png");
		gun [4] = gameApplet.getImage(gameApplet.getCodeBase(), "gun/gun2.png");
		gun [5] = gameApplet.getImage(gameApplet.getCodeBase(), "gun/gun1.png");

		}
	public void paintGun (int x, int y, Graphics g){
		xPos = x;
		yPos = y;
		if (isShooting){
			index = (index + 1)%6;
			if(index == 0 ){
				isShooting = false;
				}
			}
		g.drawImage(gun[index], (xPos - 110), yPos, gameApplet);
	}

	public void shootGun(){
		isShooting = true;
		}
	public boolean getIsShooting(){
		return isShooting;
		}
	}
