//Allie Ramirez
//Time Invested: about 20 hours
//references:
//[1]http://stackoverflow.com/questions/8938235/java-sort-an-array. Modified to use on objects. Used with help of CS120 tutor.
//[2] "gunshot.wav" was originally named "gun_44mag_11.wav" and was from http://www.wavsource.com/snds_2013-12-01_4763536717386394/sfx/gun_44mag_11.wav
//[3] "gameplay.wav" was originally named "DvdThemeMusic.eerie.creepy.sugarPlumDarkMix.mp3" and is open source from:https://archive.org/details/EerieCreepyAndScaryMusicForYourScoresDvds
//[4]  "squish.wav" was originally named "203040__jjhouse4__dropping-body-in-mud.wav" and was obtained from: http://www.freesound.org/people/jjhouse4/sounds/203040/
//[5] "background.png" was obtained from: http://www.freefever.com/stock/best--los-angeles-d-landscape-post-apocalyptic-night-picture-image.jpg
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class finalProject extends Applet implements MouseMotionListener, MouseListener, KeyListener, Runnable {
	//declare images that will be used in the game
	//zombies
	zombie [] zombies;
	int zombX;
	int zombY;
	//create the player's gun
	gun playerGun;
	//get gunshot
	private AudioClip gunshot;
	private AudioClip gameMusic;
	private AudioClip drop;
	//background
	Image background;
	//title screen
	Image car;
	Image title;
	//crosshairs
	Image crosshairs;
	//tells whether or not you are at title screen.
	private boolean titleScreen;

	int score;

	//integers that make the gun image appear where the mouse is
	private int mouseX; //mouse X coordinate
	private int mouseY;//mouse Y coordinate
	private int gunX; //centers gun on mouse x
	private int gunY; //centers gun on mouse y


	public void init(){
		int score = 0;

		//sets size of applet
		setSize(1300, 600);

		//makes it so title shows up upon startup
		titleScreen = true;

		//initialize zombies
		zombies = new zombie[10];
		for(int i = 0; i < 10; i++){
			zombX = (int)(Math.random() * 1200);
			zombY = (int) (Math.random()* 400);
			zombies[i] = new zombie (this, zombX, zombY);
			}

		//initialize background image
		background = getImage(getCodeBase(), "background.png");
		//initalize gun image
		playerGun = new gun (300, 300, this);
		//gun = getImage(getCodeBase(), "gun.jpg");
		car = getImage(getCodeBase(), "car1.png");

		//initialize gunshot sound
		gunshot = getAudioClip(getCodeBase(), "gunshot.wav");//[2];
		//initialize game music
		gameMusic = getAudioClip(getCodeBase(), "gameplay.wav");//[3]
		//initialize zombie drop sound
		drop = getAudioClip(getCodeBase(), "squish.wav");//[4]

		//init crosshairs
		crosshairs = getImage(getCodeBase(), "crosshairs.png");
		//initialize title sceen image
		title = getImage(getCodeBase(), "titleScreen.png");

		//add mouse motion listener/key listener
		addMouseMotionListener(this);
		addKeyListener(this);
		addMouseListener(this);

		//start a new thread so it will buffer
		Thread t = new Thread(this);
		t.start(); //starts thread
		}
	//the buffer
	public void update (Graphics g){
		paint(g);
		}
	//paint method
	public void paint(Graphics h){
		//create buffer
		Image buffer = createImage(getWidth(), getHeight());

		//draw everything to buffer
		Graphics g = buffer.getGraphics();
			if (titleScreen == true){
				g.drawImage(title, 0, 0, this);
				}
			else{
				//background paint
				g.drawImage(background, 0, 0, this);

			//draw zombies?
			zombieSort();//zorts zombies so closer ones are painted on top
			for(int z = 0; z < zombies.length; z++){
				zombies[z].paintZombie(g);
			//keep score
				g.setColor(Color.red);
				g.setFont(new Font("Chiller", Font.BOLD, 80));
				g.drawString("Score:" + score, 80, 80);
				}
			//paint car
			g.drawImage(car, 600, 250, this);

			//paint gun at mouse X location but keep anchored to bottom of screen
			playerGun.paintGun(mouseX, 300, g);

			//paint crosshairs on mouse location
			g.drawImage(crosshairs, (mouseX-40), (mouseY-31), this);
			}

		//draw thing on buffer to screen
		h.drawImage(buffer, 0, 0, this);
		}

	//MouseListener Things
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		int shootX = e.getX();
		int shootY = e.getY();
		if(!playerGun.getIsShooting()){
		for(int z = 0; z < zombies.length; z++){
			if(zombies[z].headShot(shootX, shootY)< 50){//checks zombies to see if they are in range
			drop.play();
			zombies[z].shot(); //calls shot method, which sets gotShot to true in Zombies class
			score++;
			break;
				}
			}
		playerGun.shootGun();
		gunshot.play();
		}
	}
	public void mouseReleased(MouseEvent e){}


	//KeyListeners
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_S){
			titleScreen = false;
			gameMusic.loop();
			}
		if(e.getKeyCode() == KeyEvent.VK_Q){
			titleScreen = true;
			}
		repaint();
		}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	//MouseMotionListeners
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		//use mouse position to determine where gun is painted
		mouseX = e.getX();
		mouseY = e.getY();

		repaint();
		}

	//run
	public void run(){
		while (true){
			try{Thread.sleep(80);} catch(Exception e){}
			for(int i = 0; i < zombies.length; i++){
				zombies[i].shuffle();
				}
			repaint();
			}
		}
//checks each zombie's Y position and paints the closest zombie on top
	public void zombieSort() {//[1]
	    boolean swapped = true;
	    int j = 0;
	    zombie tmp;
	    while (swapped) {
	        swapped = false;
	        j++;
	        for (int i = 0; i < zombies.length - j; i++) {
	            if (zombies[i].getZombieY() > zombies[i + 1].getZombieY()) {
	                tmp = zombies[i];
	                zombies[i] = zombies[i + 1];
	                zombies[i + 1] = tmp;
	                swapped = true;
	            }
	        }
	    }
	}
}