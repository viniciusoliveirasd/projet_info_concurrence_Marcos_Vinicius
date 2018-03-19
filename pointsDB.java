import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;


public class pointsDB {
	
	private static final  File fileAll=new File("allPoints.txt"); 
	private static final  File fileBest=new File("bestMarks.txt"); 
	private String player;
	private double newPoints;
	private HashMap<String,Double> allPoints;
	private HashMap<String,Double> bestMarks;
	private static final int BEST_MARKS = 5;
	
	pointsDB(String player) throws IOException
	{
		this.player = player; 
		BufferedReader bfr = new BufferedReader(new FileReader(fileAll));
		String line;
		String name;
		Double points;
		
		allPoints =  new HashMap<String,Double>();
		bestMarks =  new HashMap<String,Double>();

	    while((line = bfr.readLine())!=null){
	        name = line;
	        line = bfr.readLine();
	        points = Double.parseDouble(line);
	        allPoints.put(name, points);
	    }
	    bfr.close();
	    
	    bfr = new BufferedReader(new FileReader(fileBest));
	    while((line = bfr.readLine())!=null){
	    	name = line;
	        line = bfr.readLine();
	        points = Double.parseDouble(line);
	        bestMarks.put(name, points);
	    }
	    bfr.close();
	    
	}
	
	public double getPoints()
	{
		return allPoints.get(player);
	}
	
	public void setNewPoints(double newPoints)
	{
		this.newPoints = newPoints;
	}
	
	public void update()
	{
		//update AllPoints
		if(allPoints.containsKey(player)){
			double currentPoints = allPoints.get(player);
			if(currentPoints < newPoints)
				allPoints.put(player, newPoints);
			else allPoints.put(player, newPoints);
		}
		else allPoints.put(player, newPoints);
		
		//update bestMarks 
		
		if(bestMarks.size() < BEST_MARKS)
		{
			bestMarks.put(player, newPoints);
			return;
		}
		
		String minKey;
		double minPoints = 100000000;
		Set<String> keys = bestMarks.keySet();
		
		for(String key : keys)
		{
			if(bestMarks.get(key)< minPoints)
			{
				minKey = key;
				minPoints = bestMarks.get(key);
			}	
		}
		
		if(minPoints<newPoints)
		{
			bestMarks.remove(minPoints);
			bestMarks.put(player, newPoints);
		}	
	}
	
	//retorna uma lista ordenada dos jogadores que fizeram mais pontos
	public playerBest[] getBest()
	{
		playerBest[] best = new playerBest[BEST_MARKS];
		
		Set<String> keys = bestMarks.keySet();
		int count = 0;
		
		for(String key : keys)
		{
			best[count++] = new playerBest(key,bestMarks.get(key));
		}
		
		//performs a bubble sort
		for(int i=0; i<BEST_MARKS ; i++)
			for(int j=0; j<BEST_MARKS-1 ; j++)
			{
				if(best[j].points<best[j+1].points)
				{
					playerBest aux = best[j];
					best[j] = best[j+1];
					best[j+1] = aux;
				}
			}
		
		return best;
	}
	
	public String getPlayer()
	{
		return player;
	}
	
	public void close() throws FileNotFoundException
	{	
		PrintWriter writer = new PrintWriter(fileAll);
		Set<String> keys = allPoints.keySet();
		
		for(String key : keys)
		{
			writer.println(key);
			writer.println(Double.toString(allPoints.get(key)));
		}
		writer.close();
		
		PrintWriter writerB = new PrintWriter(fileBest);
		keys = bestMarks.keySet();
		
		for(String key : keys)
		{
			writerB.println(key);
			writerB.println(Double.toString(bestMarks.get(key)));
		}
		writerB.close();	
	}
}




