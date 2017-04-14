package zen_garden;

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
		else System.out.println("null");
	}
	/**
	 * Prebieha evolucne hladanie riesenia
	 */
	private Garden findSolution() {
		for(int g=0; g<maxGenerations; g++) {
			//ohodnotenie jedincov
			int fitnessSum=0;
			for(Garden garden : population) {	
				fitnessSum+= garden.fitness();				
				if(garden.isFinal()) return garden;
			}
			
			//vytvorene novej populacie
			runReproduction(fitnessSum);
			
			//mutacia
			runMutation();
		}
		return null;
	}
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
		
	}
	/**
	 * Vrati skrizeneho potomka
	 */
	private Garden recombination(Garden tournamentSelect, Garden tournamentSelect2) {
		tournamentSelect.printMap();
		return null;
	}
	/**
	 * Selekcia - vyber nahodne 2 jedincov z populacie a vrat lepsieho z nich
	 */
	private Garden tournamentSelect() {
		Random r = new Random();
		int indexG1= r.nextInt(populationSize);
		int indexG2= r.nextInt(populationSize);
		//System.out.println(population[indexG1].fitnessValue + " " + population[indexG2].fitnessValue);
		if(population[indexG1].fitnessValue > population[indexG2].fitnessValue) {
			return population[indexG1];
		}
		else {
			return population[indexG2];
		}
	}
	/**
	 * selekcia
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
	 * Mutacia genov
	 */
	private void runMutation() {	
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
