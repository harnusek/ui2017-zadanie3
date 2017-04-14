package zen_garden;

import java.io.*;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
public class ScenarioControl {

	private Scanner input = new Scanner(new File("data/input.txt"));
	private FileInputStream configuration = new FileInputStream(new File("data/config.txt"));
	int width, height;
	int stones[][];
	
	/**
	 * Nacita konfiguraciu a scenare zo suboru a spusti ich
	 */
	public ScenarioControl() throws IOException {
		readConfig();

		while(input.hasNextInt()) {	//spustenie scenarov
			readScenario();
			new Evolution(width, height, stones);
		}
	}
	/**
	 * Nacita konfiguraciu zo suboru
	 */
	private void readConfig() throws FileNotFoundException, IOException {
		Properties config = new Properties();
		config.load(configuration);

		Evolution.selectionMetod = Integer.parseInt(config.getProperty("selectionMetod"));
		Evolution.populationSize = Integer.parseInt(config.getProperty("populationSize"));
		Evolution.maxGenerations = Integer.parseInt(config.getProperty("maxGenerations"));
		Evolution.mutationPercentage = Integer.parseInt(config.getProperty("mutationPercentage"));
	}
	/**
	 * Pravdepodobnostna  f
	 */
	private void probability() {
		int sum=0, count=100000;
		int p=10;
		Random r=new Random();
		
		for(int i=0; i<count; i++) {
			int rand = r.nextInt(10);
			if(p>rand) sum++;
		}
		System.out.println(sum);
		
	}
	/**
	 * Nacita jeden scenar zo suboru
	 */
	private void readScenario() throws IOException {
		width = input.nextInt();
		height = input.nextInt(); 
		int x,y;
		int stonesCnt = input.nextInt(); 
		stones = new int[2][stonesCnt];
		
		for(int i=0; i<stonesCnt; i++) {
			x = input.nextInt(); 
			if(x<0 || x>=width) 
				throw new IOException();
			y = input.nextInt(); 
			if(y<0 || y>=height) 
				throw new IOException();
			stones[0][i] = x; 
			stones[1][i] = y; 
		}
	}
	/**
	 * Hlavna metoda
	 */
	public static void main(String args[]) throws IOException {
		new ScenarioControl();
	}

}