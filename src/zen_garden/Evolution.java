package zen_garden;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Evolucny algoritmus
 * @author Ondrej 
 */
public class Evolution {
	public static int TOURNAMENT_SELECTION=0, PROPORTIONAL_SELECTION=1;
	public static int selectionMetod;
	public static int populationSize;
	public static int maxGenerations;
	public static int mutationPercentage;
	public static int width;
	public static int height;
	public static int[][] stones;
	public static int map[][];
	public Garden population[];
	
	/**
	 * Evolucny algoritmus
	 */
	public Evolution(int width, int height, int[][] stones) {
		this.width = width;
		this.height = height;
		this.stones = stones;
		
		createMap();//vytvori mapu zahradky
		
		generateFirstGeneration();//vytvori prvu generaciu nahodnych jedincov
		
		Garden solution = findSolution();
		if(solution != null) solution.printMap();
		else System.out.println("solution not found");
	}
	/**
	 * Prebieha evolucne hladanie riesenia
	 */
	private Garden findSolution() {
		for(int g=0; g<maxGenerations; g++) {
			//System.out.println("gen: "+g);
			//ohodnotenie jedincov
			int fitnessSum=0;
			for(Garden garden : population) {	
				fitnessSum+= garden.fitness();				
				if(garden.isFinal()) return garden;
			}
			//vytvorene novej populacie
			runReproduction(fitnessSum);
		}
		return null;
	}
	/**
	 * Vytvorenie novej populacie
	 */
	private void runReproduction(int fitnessSum) {
		Garden newPopulation[] = new Garden[populationSize];
		for(int i=0; i<populationSize; i++) {
			if(selectionMetod == TOURNAMENT_SELECTION) {
				newPopulation[i] = recombination(tournamentSelect(),tournamentSelect());
			}
			else if(selectionMetod == PROPORTIONAL_SELECTION) {
				newPopulation[i] = recombination(proportionalSelect(fitnessSum),proportionalSelect(fitnessSum));
			}
		}
		population = newPopulation;
	}
	/**
	 * Vrati skrizeneho a zmutovaneho potomka
	 */
	private Garden recombination(Garden male, Garden female) {
		Garden newGarden = new Garden();
		int index, count= newGarden.getEntriesCnt();
        ArrayList<Integer> r = new ArrayList<Integer>();
        
        for (int i=0; i<count; i++) r.add(new Integer(i));
        Collections.shuffle(r);		
		for(int i=0; i<count/2; i++) {
			index = r.get(i);
			newGarden.entryGenes[index] = male.getCopyEntryGene(index);
		}
		for(int i=count/2; i<newGarden.getEntriesCnt(); i++) {
			index = r.get(i);
			newGarden.entryGenes[index] = female.getCopyEntryGene(index);
		}
		r = new ArrayList<Integer>();
		count = newGarden.getDecisionsCnt();
        for (int i=0; i<count; i++) r.add(new Integer(i));
        Collections.shuffle(r);
		for(int i=0; i<count/2; i++) {
			index = r.get(i);
			newGarden.decisionGenes[index] = male.decisionGenes[index];
		}
		for(int i=count/2; i<newGarden.getDecisionsCnt(); i++) {
			index = r.get(i);
			newGarden.decisionGenes[index] = female.decisionGenes[index];
		}
		newGarden.mutation();
		return newGarden;
	}
	/**
	 * Selekcia - vyber nahodne 2 jedincov z populacie a vrat lepsieho z nich
	 */
	private Garden tournamentSelect() {
		Random r = new Random();
		int indexG1= r.nextInt(populationSize);
		int indexG2= r.nextInt(populationSize);
		if(population[indexG1].fitnessValue > population[indexG2].fitnessValue) {
			return population[indexG1];
		}
		else {
			return population[indexG2];
		}
	}
	/**
	 * Selekcia - pravdepodobnost vyberu jedinca sa urci ako podiel jeho sily voci celkovej sile populacie
	 */ 
	private Garden proportionalSelect(int fitnessSum) {
		Random r = new Random();
		int exceedance = r.nextInt(fitnessSum);
		int cumulation=0;

		for(int i=0;i<populationSize;i++){
			cumulation+= population[i].fitnessValue;
            if(cumulation > exceedance) {
            	return population[i];
            }
        }
		return population[0];
	}
	/**
	 * Vypise mapu
	 */
	private void printMap() {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(map[j][i]==-1)System.out.printf("  ");
				else System.out.printf("%2d ",map[j][i]);
			}
		System.out.println();
		}
	}
	/**
	 * Vytvori mapu zahradky
	 */
	private void createMap() {
		map = new int[width][height];
		
		for(int i=0; i<stones[0].length; i++) {
			map[stones[0][i]][stones[1][i]] = -1;
		}
	}
	/**
	 * Vytvori prvu generaciu nahodnych jedincov
	 */
	private void generateFirstGeneration() {
		population = new Garden[populationSize];
		for(int i=0; i<populationSize; i++) {
			population[i] = new Garden();
			population[i].setRandom();
		}
	}
}
