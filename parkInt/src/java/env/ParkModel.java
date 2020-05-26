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
    public enum AgentType { PLASTIC_BIN, METAL_BIN, PAPER_BIN, VACUUM, NONE }

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

    // TODO: Add some more obstacles - maybe even dinamically
    // For now, put a tree in (more or less) the middle of the park
    setCellType(10, 10, CellType.OBSTACLE);
    setCellType(10, 11, CellType.OBSTACLE);
    setCellType(11, 10, CellType.OBSTACLE);
    setCellType(11, 11, CellType.OBSTACLE);

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

    // Set the locations of the recycle bins.
    // They can only be in the first column.
    for(int i = 0; i < 3; ++i){
      int ranY = ThreadLocalRandom.current().nextInt(0, GSize);
      Location loc = new Location(0, ranY);
      while(!isCellEmpty(loc)){
        ranY = ThreadLocalRandom.current().nextInt(0, GSize);
        loc = new Location(0, ranY);
      }

      setAgPos(i, loc);
    }

    // Set the locations of the agents
    // They can start anywhere on an empty cell
    for(int i = 3; i < agentCount; ++i){
      setAgPos(i, getRandomEmptyLocation());
    }
	}

  public Location getRandomEmptyLocation(){
    int ranx = ThreadLocalRandom.current().nextInt(0, 25);
    int rany = ThreadLocalRandom.current().nextInt(0, 25);
    Location l = new Location(ranx, rany);

    while(!isCellEmpty(l.x, l.y)){
      ranx = ThreadLocalRandom.current().nextInt(0, 25);
      rany = ThreadLocalRandom.current().nextInt(0, 25);
      l = new Location(ranx, rany);
    }

    return l;
  }

  public boolean isCellEmpty(Location l){
    return isCellEmpty(l.x, l.y);
  }

  public boolean isCellEmpty(int x, int y){
    if(getCellType(x, y) != CellType.EMPTY){
      return false;
    }

    try{
      for(int i = 0; i < agentCount; ++i){
        Location currentLoc = getAgPos(i);
        if(currentLoc.x == x && currentLoc.y == y){
          return false;
        }
      }
    }
    catch(Exception ex){}

    return true;
  }

  public void logLocations(){
    for(int i = 0; i < agentCount; ++i){
      Location l = getAgPos(i);
    }
  }

  public AgentType getAgentType(int id){
    switch(id){
      case 0:
        return AgentType.METAL_BIN;
      case 1:
        return AgentType.PAPER_BIN;
      case 2:
        return AgentType.PLASTIC_BIN;
      case 3:
      case 4:
      case 5:
        return AgentType.VACUUM;
      default:
        return AgentType.NONE;
    }
  }

  public List<Integer> getAgentIDs(){
    List<Integer> intList = new ArrayList<>();

    for(int i = 0; i < agentCount; ++i){
      intList.add(i);
    }

    return intList;
  }

  public int getWidth(){
    return GSize;
  }

  public int getHeight(){
    return GSize;
  }

  public CellType getCellType(Location l){
    return getCellType(l.x, l.y);
  }

  public CellType getCellType(int x, int y){
    return park[x][y];
  }

  public void setCellType(int x, int y, CellType ct){
    park[x][y] = ct;
  }
}
