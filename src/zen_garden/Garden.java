package zen_garden;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Garden {

	private Position entryGenes[];
	private static final boolean RIGHT = false, LEFT = true;
	private boolean decisionGenes[];
	
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
		map = new int[Evolution.map.length][];
		for(int i = 0; i < Evolution.map.length; i++)
			map[i] = Evolution.map[i].clone();
		map[0][0]=9;
		printMap();
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
