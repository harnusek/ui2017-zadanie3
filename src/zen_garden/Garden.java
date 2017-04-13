package zen_garden;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Garden {

	private Position entryGenes[];
	private static final int SAND = 0, STONE = -1;
	private boolean decisionGenes[];
	int x,y,direction,decisionNumber;
	boolean isStuck;
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
	/**
	 * Ohodnoti jedinca	
	 */
	public void fitness() {
		//skopirovanie mapy
		map = new int[Evolution.map.length][];
		for(int i = 0; i < Evolution.map.length; i++) {
			map[i] = Evolution.map[i].clone();
		}
		
		int line=0;
		//vyber genov vstupu
		for(int i=0; i<getEntriesCnt(); i++) {
			travel(entryGenes[i], ++line);
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
		
		//x=1;y=9;direction=Position.UP;
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
				
		if(direction==Position.UP || direction==Position.DOWN) {	//vertical
			if(x>0 && x<Evolution.width-1 && map[x+1][y]==SAND && map[x-1][y]==SAND) {			//moze ist oboma smermi
				if(getDecision()) direction = Position.RIGHT;
				else direction = Position.LEFT;
				return true;
			}
			else if(x<Evolution.width-1 && map[x+1][y]==SAND) {							//vpravo
				direction = Position.RIGHT;	
				return true;
			}
			else if(x>0 && map[x-1][y]==SAND) {							//vlavo
				direction = Position.LEFT;	
				return true;
			}
			else {
				isStuck = true;
				return false;										//zasekol sa
			}
		}
		else if(y>0 && y<Evolution.width-1 && direction==Position.RIGHT || direction==Position.LEFT) {	//horizontal
			if(map[x][y+1]==SAND && map[x][y-1]==SAND) {			//moze ist oboma smermi
				if(getDecision()) direction = Position.UP;
				else direction = Position.DOWN;
				return true;
			}
			else if(y<Evolution.width-1 && map[x][y+1]==SAND) {							//hore
				direction = Position.UP;	
				return true;
			}
			else if(y>0 && map[x][y-1]==SAND) {							//dole
				direction = Position.LEFT;	
				return true;
			}
			else {
				isStuck = true;
				return false;										//zasekol sa
			}
		}
		
		return false;
	}
	/**
	 * Vrati rozhodnutie zabocenia z genu
	 */
	private boolean getDecision() {
		boolean decision = decisionGenes[decisionNumber];
		decisionNumber = (decisionNumber+1)%getDecisionsCnt();
		return decision;
	}
	/**
	 * Vypise mapu
	 */
	private void printMap() {
		for(int i=0; i<Evolution.height; i++) {
			for(int j=0; j<Evolution.width; j++) {
				if(map[j][i]==-1)System.out.printf("   ");
				else System.out.printf("%2d ",map[j][i]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
