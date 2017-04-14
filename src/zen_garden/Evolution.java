package zen_garden;
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
			for(Garden garden : population) {	
				garden.fitness();				//ohodnotenie jedincov
				if(garden.isFinal()) return garden;
			}
		recombination();
		mutation();
		}
		return null;
	}

	/**
	 * 
	 */
	private void mutation() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	private void recombination() {
		if(selectionMetod == TOURNAMENT_SELECTION) {
			
		}
		else if(selectionMetod == PROPORTIONAL_SELECTION) {
			
		}
	}
	/**
	 * Vypise mapu
	 */
	private void printMap() {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(map[j][i]==-1)System.out.printf(" K");
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
