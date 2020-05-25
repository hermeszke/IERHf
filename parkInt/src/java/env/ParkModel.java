package env;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.util.concurrent.ThreadLocalRandom;

public class ParkModel extends GridWorldModel {
    static Logger logger = Logger.getLogger(ParkController.class.getName());
    public enum CellType { EMPTY, PLASTICGARB, METALGARB, PAPERGARB, OBSTACLE }

    public static final int GSize = 25;
    private static int typeCount = 3;
    private static int vacuumCount = 3;
    private static int agentCount = typeCount + vacuumCount;
    private CellType[][] park;

    private int randomNumX;
    private int randomNumY;


	protected ParkModel() {
		super(GSize, GSize,agentCount);
    // First create an empty park
    park = new CellType[GSize][GSize];
    for(int i = 0; i < GSize; ++i){
      for(int j = 0; j < GSize; ++j){
        park[j][i] = CellType.EMPTY;
      }
    }

    // TODO: Add some obstacles

    //Add garbage
    for(int i = 0; i < 10; ++i){
      int ranX = ThreadLocalRandom.current().nextInt(0, GSize);
      int ranY = ThreadLocalRandom.current().nextInt(0, GSize);
      if(getCellType(ranX, ranY) == CellType.OBSTACLE){
        continue;
      }

      CellType garbType;
      int typeSelector = i % 3;
      if(typeSelector == 0){
        garbType = CellType.PLASTICGARB;
      }
      else if(typeSelector == 1){
        garbType = CellType.METALGARB;
      }
      else{
        garbType = CellType.PAPERGARB;
      }

      setCellType(ranX, ranY, garbType);
    }

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

  public int getWidth(){
    return GSize;
  }

  public int getHeight(){
    return GSize;
  }

  public CellType getCellType(int x, int y){
    return park[x][y];
  }

  public void setCellType(int x, int y, CellType ct){
    park[x][y] = ct;
  }
}
