package zen_garden;

import java.io.*;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
public class ScenarioControl {

	private Scanner in = null;
	private FileWriter out = null;
	int width, height;
	int stones[][];
	
	/**
	 * Nacita konfiguraciu a scenare zo suboru a spusti ich
	 */
	public ScenarioControl() throws IOException {
		
		readConfig();
		//System.out.println("Conciguraton: " +Evolution.selectionMetod + ", " + Evolution.populationSize + ", " + Evolution.maxGenerations);
		
		in = new Scanner(new File("data/input.txt"));
		out = new FileWriter("data/output.txt");
		
		while(in.hasNextInt()) {	//spustenie scenarov
			readScenario();
			new Evolution(width, height, stones);
			//probability();
		}
	}
	/**
	 * Nacita konfiguraciu zo suboru
	 */
	private void readConfig() throws FileNotFoundException, IOException {
		Properties config = new Properties();
		config.load(new FileInputStream(new File("data/config.txt")));

		Evolution.selectionMetod = Integer.parseInt(config.getProperty("selectionMetod"));
		Evolution.populationSize = Integer.parseInt(config.getProperty("populationSize"));
		Evolution.maxGenerations = Integer.parseInt(config.getProperty("maxGenerations"));
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
		width = in.nextInt();
		height = in.nextInt(); 
		int x,y;
		int stonesCnt = in.nextInt(); 
		stones = new int[2][stonesCnt];
		
		for(int i=0; i<stonesCnt; i++) {
			x = in.nextInt(); 
			if(x<0 || x>=width) 
				throw new IOException();
			y = in.nextInt(); 
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