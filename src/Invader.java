import java.awt.Image;


public class Invader{

	int xDist = 10;
	int yDist = 10;
	int velx = 10;

	Image EnemyImage;

	public void setInvader(int x, int y, int Vx){
		xDist = x;
		yDist = y;
		velx = Vx;
	}
}

