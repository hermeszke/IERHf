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
    
    private static int agentCount = 7;
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
			temp = new Location(0,0);
		    setAgPos(0,temp);
		    loc.add(temp);

			temp = new Location(0,12);
		    setAgPos(1,temp);
		    loc.add(temp);

			temp = new Location(0,24);
		    setAgPos(2,temp);
		    loc.add(temp);
		    

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
		    
	    	garbageLoc.add(new Location(3,3));
	    	garbType.add(ParkModel.METAL);
	    	add(ParkModel.METAL,new Location(3,3));
	    	
		    int t =0;
		    while(garbageLoc.size()!=maxGarbage/3)
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
		   /* System.out.println(loc);
		    System.out.println(agentNameWNum);
		    System.out.println(garbageLoc);
		    System.out.println(garbType);
		    for(int i = 0; i< getNbOfAgs();i++) {
		    	System.out.println(getAgPos(i));
		    }*/
		    
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
		setAgPos(0, tmp0);
		setAgPos(1, tmp1);
		setAgPos(2, tmp2);
		setAgPos(3, tmp3);
		setAgPos(4, tmp4);
		setAgPos(5, tmp5);
		view.repaint();
	}
	
	void moveTowards(int x, int y, int ind) throws Exception {
        Location r1 = getAgPos(ind);
        System.out.println(r1 + "+ " + ind);
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
		System.out.println("Index" + ind);
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
            // sometimes the "picking" action doesn't work
            // but never more than MErr times
            if (random.nextBoolean() || nerr == MErr) {
                remove(METAL, l);
                nerr = 0;
    	    	agentCarries.set(ind-3,METAL);
            } else {
                nerr++;
            }
        }
        else if(hasObject(PAPER, l))
        {
        	if (random.nextBoolean() || nerr == MErr) {
                remove(PAPER, l);
                nerr = 0;
    	    	agentCarries.set(ind-3,PAPER);
            } else {
                nerr++;
            }
        }
        else if( hasObject(PLAST, l)) {
        	if (random.nextBoolean() || nerr == MErr) {
                remove(PLAST, l);
                nerr = 0;
    	    	agentCarries.set(ind-3,PLAST);
            } else {
                nerr++;
            }
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

/*	void removeGarbage(int ind) {
		Location l = getAgPos(ind);
		System.out.println("Loc:" + l + "+"  + garbageLoc);
		for( int i = 0; i< garbageLoc.size();i++) {
			if(garbageLoc.get(i).x == l.x && garbageLoc.get(i).y == l.y) {
				switch(garbType.get(i)) {
				case 16: carryingMet = true; carryingPaper = false; carryingPlast = false;
						garbageLoc.remove(i);
						garbType.remove(i);
						remove(ParkModel.METAL,garbageLoc.get(i));
						break;
				case 32:carryingMet = false; carryingPaper = true; carryingPlast = false;
						garbageLoc.remove(i);
						garbType.remove(i);
						remove(ParkModel.PAPER,garbageLoc.get(i));
						break;
				case 64:carryingMet = false; carryingPaper = false; carryingPlast = true;
						garbageLoc.remove(i);
						garbType.remove(i);
						remove(ParkModel.PLAST,garbageLoc.get(i));
						break;
				}
			}
		}
	}
*/
	void dropGarbage(int ind) {
		Location l = getAgPos(ind);
		int check = agentCarries.get(ind-3);
		if(loc.contains(l)) {
			if(check == METAL) {
				if(loc.get(0) == l) {
					add(METAL,l);
	    	    	agentCarries.set(ind-3,0);
	    	    	
				}
			}
			else if(check == METAL) {
				if(loc.get(2) == l) {
					add(PAPER,l);
	    	    	agentCarries.set(ind-3,0);
				}
			}
			else if(check == METAL) {
				if(loc.get(1) == l) {
					add(PLAST,l);
	    	    	agentCarries.set(ind-3,0);
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

