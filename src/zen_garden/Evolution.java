package zen_garden;

public class Evolution {
	public static int selectionMetod;
	public static int populationSize;
	public static int maxGenerations;
	public static int width;
	public static int height;
	public static int[][] stones;
	static int map[][];
	Garden population[];
	
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
		
		printMap();
		//output();
	}
	/**
	 * 
	 */
	private Garden findSolution() {
		for(int i=0; i<maxGenerations; i++) {
			evaluation();
			selection();
			recombination();
			mutation();
		}
		return null;
	}
	private void evaluation() {
		for(int i=0; i<populationSize; i++) {
			population[i].fitness();
		}
		
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
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	private void selection() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Vypise mapu
	 */
	private void printMap() {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				System.out.printf("%2d ",map[j][i]);
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
	/**
	 * Vystup
	 */
	private void output() {
		System.out.println("dimensions: [" +width + ", " + height+ "]");
		System.out.println("stones:");
		for(int i=0; i<stones[0].length; i++) {
			System.out.println("\t[" + stones[0][i] + ", " + stones[1][i] + "]");
		}
		System.out.println("--------------------------");
	}
	
}
