package zen_garden;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Garden {

	public Position entryGenes[];
	private static final int SAND = 0, STONE = -1;
	public boolean decisionGenes[];
	private int x,y,direction,decisionNumber,mark;
	private boolean isStuck;
	public int fitnessValue;
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
		}	
		Random r = new Random();
		for(int i=0; i<getDecisionsCnt(); i++) {
			decisionGenes[i] = r.nextBoolean();
		}
	}
	/**
	 * Vrati pocet genov vstupu, (polovica obvodu)
	 */
	public int getEntriesCnt() {
		return Evolution.height + Evolution.width;
	}
	/**
	 * Vrati pocet genov rozhodnuti, (pocet kamenov)
	 */
	public int getDecisionsCnt() {
		return Evolution.stones[0].length;
	}
	/**
	 * Ohodnoti jedinca	
	 * @return 
	 */
	public int fitness() {
		//skopirovanie mapy
		map = new int[Evolution.map.length][];
		for(int i = 0; i < Evolution.map.length; i++) {
			map[i] = Evolution.map[i].clone();
		}
		//znacka na mape
		mark=1;
		//vyber genov vstupu
		for(int i=0; i<getEntriesCnt(); i++) {
			travel(entryGenes[i]);
		}
		//this.printMap();
		return fitnessValue;
	}
	/**
	 * Znaci finalny stav
	 */
	public boolean isFinal() {
		if(!isStuck) {
			if(fitnessValue == (Evolution.height*Evolution.width)-getDecisionsCnt() ) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Prejde hrablami raz cez zahradu
	 */
	private void travel(Position position) {
		x = position.x;
		y = position.y;
		direction = position.direction;

		//ak moze vstupit
		if(map[x][y]==SAND) {
			map[x][y]=mark;
			++fitnessValue;
		} else return;

		boolean insideGarden=true;	
		//hrabanie
		while(insideGarden) {
			switch(direction) {
				case Position.UP: 
					if(!jump(x,y-1)) {
						insideGarden = false;
					}
					break;
				case Position.DOWN:	
					if(!jump(x,y+1)) {
						insideGarden = false;
					}
					break;
				case Position.RIGHT:
					if(!jump(x+1,y)) {
						insideGarden = false;
					}
					break;	
				case Position.LEFT:
					if(!jump(x-1,y)) {
						insideGarden = false;
					}
					break;
			}
		}
	}
	/**
	 * Otestuje platnost policka, posunie sa, zmeni smer alebo skonci 
	 */
	private boolean jump(int nextX, int nextY) {
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
		mark++;
		return false;//koniec ciary
	}
	/**
	 * Zmenenie smeru pohybu
	 */
	private boolean changeDirection() {
		boolean wantHorisontal = (direction==Position.UP || direction==Position.DOWN);
		boolean wantVertical = (direction==Position.RIGHT || direction==Position.LEFT);
		
		if(wantHorisontal) {	
			boolean canRight = (x<Evolution.width-1 && map[x+1][y]==SAND) || (x==Evolution.width-1);	
			boolean canLeft = ((x>0 && map[x-1][y]==SAND) || (x==0));				
			
			if(canRight && canLeft) {			
				if(getDecision()) direction = Position.RIGHT;
				else direction = Position.LEFT;
				return true;
			}
			else if(canRight) {							
				direction = Position.RIGHT;	
				return true;
			}
			else if(canLeft) {						
				direction = Position.LEFT;	
				return true;
			}
			else {
				isStuck = true;
				return false;										
			}
		}
		else if(wantVertical) {		
			boolean canDown = (y<Evolution.height-1 && map[x][y+1]==SAND) || (y==Evolution.height-1);	
			boolean canUp = ((y>0 && map[x][y-1]==SAND) || (y==0));	
			
			if(canDown && canUp) {			
				if(getDecision()) direction = Position.DOWN;
				else direction = Position.UP;
				return true;
			}
			else if(canDown) {							
				direction = Position.DOWN;	
				return true;
			}
			else if(canUp) {						
				direction = Position.UP;	
				return true;
			}
			else {
				isStuck = true;
				return false;										
			}
		}

		
		return false;
	}
	/**
	 * Vrati rozhodnutie zabocenia z genu
	 */
	private boolean getDecision() {
		if(decisionNumber==0) {
			return new Random().nextBoolean();
		}
		boolean decision = decisionGenes[decisionNumber];
		decisionNumber = (decisionNumber+1)%getDecisionsCnt();
		return decision;
	}
	/**
	 * Vypise mapu
	 */
	void printMap() {
		for(int i=0; i<Evolution.height; i++) {
			for(int j=0; j<Evolution.width; j++) {
				if(map[j][i]==STONE)System.out.printf("   ");
				else System.out.printf("%2d ",map[j][i]);
			}
			System.out.println();
		}
		System.out.println("-"+fitnessValue+"-\n");
	}
	/**
	 * Vrati duplikovany gen
	 */
	public Position getCopyEntryGene(int i) {
		Position copy = new Position();	
		copy.x = entryGenes[i].x;
		copy.y = entryGenes[i].y;
		copy.direction = entryGenes[i].direction;
		return copy;
	}
	/**
	 * Zmutuje jedinca
	 */
	public void mutation() {
		Random r = new Random();
		for(int i=0; i<getEntriesCnt(); i++) {
			if(Evolution.mutationPercentage>r.nextInt(100)) {
				entryGenes[i].randomisation();
			}
		}	
	}
	
}

