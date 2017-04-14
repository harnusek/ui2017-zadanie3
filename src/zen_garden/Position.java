package zen_garden;

import java.util.Random;

public class Position {

	public int x, y;
	public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	public int direction;

	/**
	 * Inicializuje gen nahodne
	 */
	public void randomisation() {
		Random r = new Random();
		int first = Evolution.width;
		int second = first + Evolution.height-1;
		int third = second + Evolution.width-1;
		int fourth = third + Evolution.height-1;
		int g = r.nextInt(2*(Evolution.height + Evolution.width)-4);	
		
		if(g < first) {
			x = g;
			y = 0;
			direction = DOWN;
		}
		else if(g < second) {
			x = Evolution.width-1;
			y = (1+g-first);
			direction = LEFT;
		}
		else if(g < third) {
			x = (g-second);
			y = Evolution.height-1;
			direction = UP;
		}
		else if(g < fourth) {
			x = 0;
			y = (1+g-third);
			direction = RIGHT;
		}
	}
	/**
	 * Vypise info
	 */
	public void printInfo() {
		System.out.printf("[%2d, %2d]\n",x,y);
	}
}
