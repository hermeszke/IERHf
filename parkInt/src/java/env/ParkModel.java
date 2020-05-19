package env;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.util.concurrent.ThreadLocalRandom;

public class ParkModel extends GridWorldModel {


    public static final int GSize = 25;
    private static int typeCount = 3;
    private static int vacuumCount = 3;
    private static int agentCount = typeCount + vacuumCount;


    private int randomNumX;
    private int randomNumY;

	
	protected ParkModel() {
		super(GSize, GSize,agentCount);
			int cntr=0;
			ArrayList<Location> loc = new ArrayList<Location>();
			Location temp;
		    while(loc.size()!=agentCount)
		    {	
			    if(cntr < typeCount) {
				    randomNumX  = 0;
				    randomNumY = ThreadLocalRandom.current().nextInt(0, 24 + 1);
				    temp = new Location(randomNumX,randomNumY);
				    if(!loc.contains(temp))
				    {
				    	setAgPos(cntr, temp);
				    	loc.add(temp);
				    	cntr++;
				    }
			    }
			    else {
				    randomNumX  = ThreadLocalRandom.current().nextInt(0, 24 + 1);
				    randomNumY = ThreadLocalRandom.current().nextInt(0, 24 + 1);
				    temp = new Location(randomNumX,randomNumY);
				    if(!loc.contains(temp))
				    {
				    	setAgPos(cntr, temp);
				    	loc.add(temp);
				    	cntr++;
				    }
			    	
			    }
		  }
	}
}

