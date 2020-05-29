package env;

import java.util.List;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import jason.environment.grid.GridWorldModel;
import jason.environment.grid.Location;
import java.util.concurrent.ThreadLocalRandom;

public class ParkModel extends GridWorldModel {
    public static final int GSize = 25;
    public static final int METAL = 16;
    public static final int PLAST = 64;
    public static final int PAPER = 32;
	public static final int OBSTACLE = 256;

    private static int agentCount = 8;
    private static int maxGarbage = 10;
    private static int typeCount = 3;


    public static final int MErr = 2; // max error in pick garb
    int nerr; // number of tries of pick garb

	ArrayList<Location> loc = new ArrayList<Location>();

	ArrayList<Location> garbageLoc = new ArrayList<Location>();
	ArrayList<Integer> garbType = new ArrayList<Integer>();
	ArrayList<String> agentNameWNum = new ArrayList<String>();
	ArrayList<Boolean> agentDir = new ArrayList<Boolean>();
	ArrayList<Boolean> agentIsReturning = new ArrayList<Boolean>();
	ArrayList<Integer> agentCarries = new ArrayList<Integer>();

    private int randomNumX;
    private int randomNumY;

    public int cntr;

    private static final SecureRandom random = new SecureRandom();

	protected ParkModel() {
		super(GSize, GSize,agentCount);
			cntr=typeCount;
			int vcCnt = 1;
			Location temp;

			//Dumps
			temp = new Location(0,0);
		    setAgPos(0,temp);
		    loc.add(temp);

			temp = new Location(0,12);
		    setAgPos(1,temp);
		    loc.add(temp);

			temp = new Location(0,24);
		    setAgPos(2,temp);
		    loc.add(temp);

		    //Cleaners

		    temp = new Location(0,0);
		    setAgPos(3,temp);
		    loc.add(temp);
	    	String s = "vacuumAgent";
	    	agentNameWNum.add(s.concat(Integer.toString(vcCnt)));
	    	agentDir.add(false);
	    	agentIsReturning.add(false);
	    	agentCarries.add(0);
	    	vcCnt++;

		    temp = new Location(8,0);
		    setAgPos(4,temp);
		    loc.add(temp);
	    	s = "vacuumAgent";
	    	agentNameWNum.add(s.concat(Integer.toString(vcCnt)));
	    	agentDir.add(false);
	    	agentIsReturning.add(false);
	    	agentCarries.add(0);
	    	vcCnt++;

		    temp = new Location(16,0);
		    setAgPos(5,temp);
		    loc.add(temp);
	    	s = "vacuumAgent";
	    	agentNameWNum.add(s.concat(Integer.toString(vcCnt)));
	    	agentDir.add(false);
	    	agentIsReturning.add(false);
	    	agentCarries.add(0);
	    	vcCnt++;

	    	//Garbage

	    	garbageLoc.add(new Location(5,8));
	    	garbType.add(ParkModel.PLAST);
	    	add(ParkModel.PLAST,new Location(5,8));

	    	garbageLoc.add(new Location(9,3));
	    	garbType.add(ParkModel.METAL);
	    	add(ParkModel.METAL,new Location(9,3));

	    	garbageLoc.add(new Location(16,4));
	    	garbType.add(ParkModel.PAPER);
	    	add(ParkModel.PAPER,new Location(16,4));

				// Set some obstacles
				// TODO: Add more
				add(ParkModel.OBSTACLE, new Location(5, 6));

		    int t =0;
		    while(garbageLoc.size()!=5)
		    {
		    	randomNumX  = ThreadLocalRandom.current().nextInt(1, 24 + 1);
			    randomNumY = ThreadLocalRandom.current().nextInt(1, 24 + 1);
			    temp = new Location(randomNumX,randomNumY);
			    if(!loc.contains(temp) && !garbageLoc.contains(temp))
			    {
			    	t = ThreadLocalRandom.current().nextInt(1,4);
			    	garbageLoc.add(temp);
			    	if(t == 1) {

				    	garbType.add(ParkModel.PLAST);
				    	add(ParkModel.PLAST,temp);
			    	}
			    	else if(t == 2)
			    	{
				    	garbType.add(ParkModel.PAPER);
				    	add(ParkModel.PAPER,temp);
			    	}
			    	else if(t == 3) {
				    	garbType.add(ParkModel.METAL);
				    	add(ParkModel.METAL,temp);
			    	}
			    }
			    
		    }
		    
		  //Human
		    
			temp = new Location(3, 4);
			s = "human";
	    	agentNameWNum.add(s.concat(Integer.toString(1)));
			setAgPos(6, temp);
			loc.add(temp);
			
			temp = new Location(4, 7);
			s = "human";
	    	agentNameWNum.add(s.concat(Integer.toString(2)));
			setAgPos(7, temp);
			loc.add(temp);
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
	
	public void reInstate() {
		Location tmp0 = getAgPos(0);
		Location tmp1 = getAgPos(1);
		Location tmp2 = getAgPos(2);
		Location tmp3 = getAgPos(3);
		Location tmp4 = getAgPos(4);
		Location tmp5 = getAgPos(5);
		Location tmp6 = getAgPos(6);
		Location tmp7 = getAgPos(7);

		setAgPos(0, tmp0);
		setAgPos(1, tmp1);
		setAgPos(2, tmp2);
		setAgPos(3, tmp3);
		setAgPos(4, tmp4);
		setAgPos(5, tmp5);
		setAgPos(6, tmp6);
		setAgPos(7, tmp7);
		
		view.repaint();
	}

	void moveTowards(int x, int y, int ind) throws Exception {
        Location r1 = getAgPos(ind);
        if (r1.x < x)
            r1.x++;
        else if (r1.x > x)
            r1.x--;
        if (r1.y < y)
            r1.y++;
        else if (r1.y > y)
            r1.y--;
        setAgPos(ind, r1);
    }

	void movePerimeter(int ind) {
		switch(ind) {
		case 3: moveBetween(0,GSize/3-1, ind);break;
		case 4: moveBetween(GSize/3,(2*GSize/3)-1, ind);break;
		case 5: moveBetween(2*GSize/3,GSize-1, ind);break;
		}
	}

	void moveBetween(int xmin, int xmax, int ind) {
		Location r1 = getAgPos(ind);
		boolean flip = agentDir.get(ind-3);
		boolean returning = agentIsReturning.get(ind-3);
		if(r1.x == xmin && r1.y ==0) {
			agentDir.set(ind-3, false);
			agentIsReturning.set(ind-3,false);
		}
		if((r1.x == xmax && r1.y == GSize-1 && !returning) || (returning)) {
			agentIsReturning.set(ind-3,true);
			if(r1.x == xmin && r1.y == 0) {
				agentIsReturning.set(ind-3,false);
				agentDir.set(ind-3, false);
			}
			else if(r1.x > xmin) r1.x--;
			else if(r1.y > 0 ) r1.y--;
		}
		else if(!flip && r1.x < xmax) r1.x++;
		else if(flip && r1.x > xmin) r1.x--;
		else if(flip && r1.x == xmin && r1.y<GSize-1) {agentDir.set(ind-3, false); r1.y++;}
		else {
			agentDir.set(ind-3, true);
			r1.y++;
		}
		setAgPos(ind, r1);
	}
	void pickGarb(int ind) {
		Location l = getAgPos(ind);
        if (hasObject(METAL, l)) {
                remove(METAL, l);
    	    	agentCarries.set(ind-typeCount,METAL);
        }
        else if(hasObject(PAPER, l))
        {
                remove(PAPER, l);
    	    	agentCarries.set(ind-typeCount,PAPER);
        }
        else if( hasObject(PLAST, l)) {
                remove(PLAST, l);
    	    	agentCarries.set(ind-typeCount,PLAST);
        }
    }

	public int getByLoc(Location tmp) {
    	for(int i =0;i< garbageLoc.size();i++) {
    		if(tmp.x == garbageLoc.get(i).x && tmp.y == garbageLoc.get(i).y) {
    			return i;
    		}
    	}
    	return -1;
    }


	public int getAgentByName(String name) {
		for(int i = 0; i< agentNameWNum.size();i++) {
			if(agentNameWNum.get(i).compareTo(name) == 0) {
				return i+typeCount;
			}
		}
		return -1;
	}


	void dropGarbage(int ind) {
		Location l = getAgPos(ind);
		int check = agentCarries.get(ind-typeCount);
		if(loc.contains(l)) {
			if(check == METAL) {
				if(loc.get(0) == l) {
					add(METAL,l);
	    	    	agentCarries.set(ind-typeCount,0);

				}
			}
			else if(check == METAL) {
				if(loc.get(2) == l) {
					add(PAPER,l);
	    	    	agentCarries.set(ind-typeCount,0);
				}
			}
			else if(check == METAL) {
				if(loc.get(1) == l) {
					add(PLAST,l);
	    	    	agentCarries.set(ind-typeCount,0);
				}
			}
		}
	}

	public int isThisGarbage(Location l) {
		if(hasObject(METAL, l)) {
			return METAL;
		}
		else if (hasObject(PLAST, l)) {
			return PLAST;
		}
		else if(hasObject(PAPER, l)) {
			return PAPER;
		}
		return -1;
	}
}
