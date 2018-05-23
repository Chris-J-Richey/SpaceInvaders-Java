
//Programmer Chris Richey
//date: 11/2
//Calculates: SpaceInvader

import java.applet.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.text.*;

public class SpaceInvaderAdvancedE extends JApplet implements KeyListener, ActionListener{
	JPanel pnlMain = new JPanel();
	Image shipImage;
	Image bulletImage;
	int xEnemyInt = 10;
	int yEnemyInt = 10;
	int xCEnemyInt = 10;
	int xShipInt = 50;
	int yShipInt = 250;
	int xBulletInt = 70;
	int yBulletInt = 250;
	int count = 0;
	int dead = 0;
	boolean OnorOff = true;
	boolean EnemyChange = true;
	Timer myTimer = new Timer(250,this);
	ArrayList<Invader> Invaders = new ArrayList<Invader>();
	
	public void init(){
		myTimer.start();
		shipImage = getImage(getDocumentBase(),"ship.png");
		bulletImage = getImage(getDocumentBase(),"bullet.png");
		for (int i = 0; i < 5; i++){
			Invaders.add(new Invader());
		}
		
		setContentPane(pnlMain);
		this.getContentPane().setBackground (Color.BLACK);
		resize(400,300);
		
		addKeyListener(this);
		setFocusable(true);
	}
	public void actionPerformed(ActionEvent evt) {
		//count tells if enemy is dead
		//0 = alive
		//>0 = dead
		//once shot count goes to 1 then goes up every time the actionPreformed is run
		//at five they enemy goes black
		requestFocus();

		//enemy stuff
		//sets pics
		for (int i = 0; i < Invaders.size(); i++){
			if (count == 0){
				Invaders.get(i).EnemyImage = getImage(getDocumentBase(),"Emeny1 (1).png");
			}
		}
		//movement
		if(Invaders.size() > 0){
			if(Invaders.get(0).xDist > getWidth() - (Invaders.size() * 25) - 10){
				Invaders.get(0).velx = -10;
				Invaders.get(0).yDist += 10;
			}
			if(Invaders.get(0).xDist < 10){
				Invaders.get(0).velx = 10;
				Invaders.get(0).yDist += 10;
			}
			if (count == 0){
				Invaders.get(0).xDist += Invaders.get(0).velx;
			}
			Invaders.get(0).setInvader(Invaders.get(0).xDist, Invaders.get(0).yDist, Invaders.get(0).velx);
			for(int x = 1; x < Invaders.size(); x++){
				Invaders.get(x).setInvader((Invaders.get(x-1).xDist + 25), (Invaders.get(x-1).yDist), (Invaders.get(x-1).velx));
			}
		}
		repaint();
		
		//shot bullet and collision code
		if (!OnorOff){
			yBulletInt -= 10;
		}
		if (yBulletInt <= 0){
			OnorOff = true;
			xBulletInt = xShipInt + 20;
			yBulletInt = yShipInt;
		}
		if (count >= 1){
			count += 1;
			if (count == 5){
				for (int i = dead; i < Invaders.size() - 1; i++){
					Invaders.get(i).EnemyImage = Invaders.get(i + 1).EnemyImage;
					Invaders.get(i).velx = Invaders.get(i + 1).velx;
					Invaders.get(i).xDist = Invaders.get(i + 1).xDist;
					Invaders.get(i).yDist = Invaders.get(i + 1).yDist;
					dead = Invaders.size() - 1;
				}
				Invaders.remove(Invaders.get(dead));
				count = 0;
			}
		}
		if (count == 0){
			for (int i = 0; i < Invaders.size(); i++){
				Rectangle rect1 = new Rectangle(xBulletInt, yBulletInt, 50, 50);
				Rectangle rect2 = new Rectangle(Invaders.get(i).xDist, Invaders.get(i).yDist, 20, 20);
				if(rect1.intersects(rect2)){
					Invaders.get(i).EnemyImage = getImage(getDocumentBase(),"Blow.png");
					dead = i;
					xBulletInt = xShipInt + 20;
					yBulletInt = yShipInt;
					OnorOff = true;
					count = 1;
				}
			}
		}
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == 37){
			xShipInt -= 10;
			if (OnorOff){
				xBulletInt = xShipInt + 20;
				yBulletInt = yShipInt;
			}
		}
		else if (key == 39){
			xShipInt += 10;
			if (OnorOff){
				xBulletInt = xShipInt + 20;
				yBulletInt = yShipInt;
			}
		}
		else if (key == 32){
			OnorOff = false;
		}
		repaint();
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
	public void paint(Graphics g){
		super.paint(g);
		for (int i = 0; i < Invaders.size(); i++) {
			g.drawImage(Invaders.get(i).EnemyImage, Invaders.get(i).xDist, Invaders.get(i).yDist, 20, 20, this);
		}
		g.drawImage(shipImage,xShipInt, yShipInt,this);
		g.drawImage(bulletImage,xBulletInt, yBulletInt,this);
	}
	public void Update(Graphics gr) {
		paint(gr);
	}
}
