package zen_garden;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Garden {

	private Position entryGenes[];
	private static final int SAND = 0, STONE = -1;
	private static final boolean RIGHT = false, LEFT = true;
	private boolean decisionGenes[];
	int x,y,direction;
	
	int fitnessValue;
	private int map[][];
	
	/**
	 * Jedinec
	 */
	public Garden() {
		entryGenes = new Position[getEntriesCnt()];
		decisionGenes = new boolean[getDecisionsCnt()];
	}
	/**
	 * Nastavi jedinovi vsetky geny nahodne
	 */
	public void setRandom() {
		for(int i=0; i<getEntriesCnt(); i++) {
			entryGenes[i] = new Position();
			entryGenes[i].randomisation();
														entryGenes[i].printInfo();
		}	
		Random r = new Random();
		for(int i=0; i<getDecisionsCnt(); i++) {
			decisionGenes[i] = r.nextBoolean();
														System.out.println(decisionGenes[i]);
		}
														System.out.println();
	}
	/**
	 * Vrati pocet genov vstupu, (polovica obvodu)
	 */
	private int getEntriesCnt() {
		return Evolution.height + Evolution.width;
	}
	/**
	 * Vrati pocet genov rozhodnuti, (pocet kamenov)
	 */
	private int getDecisionsCnt() {
		return Evolution.stones[0].length;
	}
		
	public void fitness() {
		//skopirovanie mapy
		map = new int[Evolution.map.length][];
		for(int i = 0; i < Evolution.map.length; i++) {
			map[i] = Evolution.map[i].clone();
		}
		
		int line=0;
		//vyber genov vstupu
		for(int i=0; i<getEntriesCnt(); i++) {
			travel(entryGenes[0], ++line);
		}
		printMap();
	}
	/**
	 * Prejde hrablami raz cez zahradu
	 */
	private void travel(Position position, int mark) {
		x = position.x;
		y = position.y;
		direction = position.direction;
		
		x=1;y=9;direction=Position.UP;
		//ak moze vstupit
		if(map[x][y]==SAND) {
			map[x][y]=mark;
		} else return;
		

		boolean insideGarden=true;	
		//hrabanie
		while(insideGarden) {
			switch(direction) {
				case Position.UP: 
					if(!jump(x,y-1,mark)) {
						insideGarden = false;
					}
					break;
				case Position.DOWN:	
					if(!jump(x,y+1,mark)) {
						insideGarden = false;
					}
					break;
				case Position.RIGHT:
					if(!jump(x-1,y,mark)) {
						insideGarden = false;
					}
					break;	
				case Position.LEFT:
					if(!jump(x+1,y,mark)) {
						insideGarden = false;
					}
					break;
			}
		}
	}
	/**
	 * Otestuje platnost policka, posunie sa, zmeni smer alebo skonci 
	 */
	private boolean jump(int nextX, int nextY, int mark) {
		if(nextX>=0 && nextX<Evolution.width && nextY>=0 && nextY<Evolution.height){
			if(map[nextX][nextY]==SAND) {
				map[nextX][nextY] = mark;
				fitnessValue++;
				this.x=nextX;
				this.y=nextY;
				return true;
			}
			else return changeDirection();
		}
		return false;
	}
	/**
	 * Zmenenie smeru pohybu
	 */
	private boolean changeDirection() {
		System.out.println(x+"_"+y);
		
		switch(direction) {
			case Position.UP: 
	
				break;
			case Position.DOWN:	
	
				break;
			case Position.RIGHT:
	
				break;	
			case Position.LEFT:
	
				break;
		}
		
		return false;
	}
	/**
	 * Vypise mapu
	 */
	private void printMap() {
		for(int i=0; i<Evolution.height; i++) {
			for(int j=0; j<Evolution.width; j++) {
				System.out.printf("%2d ",map[j][i]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
