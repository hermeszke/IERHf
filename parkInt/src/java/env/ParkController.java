package env;
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;


public class ParkController extends Environment{

		static Logger logger = Logger.getLogger(ParkController.class.getName());

		 private final ReentrantLock lock = new ReentrantLock();
		 
	    public final String mr = new String("mine");
	    public final String dr = new String("drop");
	    public final Term nc = Literal.parseLiteral("move_to(next_cell)");
	    public  Literal vc1gb;
	    

	    ParkModel model; // the model of the grid

	    @Override
	    public void init(String[] args) {
	        model = new ParkModel();
	        ParkView view  = new ParkView(model);
	        model.setView(view);

	        updatePercepts();
	    }
	    
	    void updatePercepts() {
	    	/*clearPercepts("paperDump");
	    	clearPercepts("metalDumpt");
	    	clearPercepts("plasticDump");*/
	    	clearPercepts("vacuumAgent1");
	    	clearPercepts("vacuumAgent2");
	    	clearPercepts("vacuumAgent3");

	       /* Location metalAg = model.getAgPos(0);
	        Location plastAg = model.getAgPos(1);
	        Location paperAg = model.getAgPos(2);*/
	        Location vacuumAgent1 = model.getAgPos(3);
	        Location vacuumAgent2 = model.getAgPos(4);
	        Location vacuumAgent3 = model.getAgPos(5);
	        
	        Literal pos3 = Literal.parseLiteral("my_pos(" + vacuumAgent1.x + "," + vacuumAgent1.y + ")");
	        Literal pos4 = Literal.parseLiteral("my_pos(" + vacuumAgent2.x + "," + vacuumAgent2.y + ")");
	        Literal pos5 = Literal.parseLiteral("my_pos(" + vacuumAgent3.x + "," + vacuumAgent3.y + ")");
	        
	        
	        addPercept("vacuumAgent1",pos3);
	        addPercept("vacuumAgent2",pos4);
	        addPercept("vacuumAgent3",pos5);
	        System.out.println(" GarbageList" + model.garbageLoc);
	        int tmp1 = model.isThisGarbage(vacuumAgent1);
	        int tmp2 = model.isThisGarbage(vacuumAgent2);
	        int tmp3 = model.isThisGarbage(vacuumAgent3);
	        if(tmp1 != -1) {
                vc1gb = Literal.parseLiteral("found("+tmp1+")");
                addPercept("vacuumAgent1",vc1gb);
            }
	        if(tmp1 != -1) {
                vc1gb = Literal.parseLiteral("found("+tmp2+")");
                addPercept("vacuumAgent2",vc1gb);
            }
	        if(tmp1 != -1) {
                vc1gb = Literal.parseLiteral("found("+tmp3+")");
                addPercept("vacuumAgent3",vc1gb);
            }
	    }
	    
	    @Override
	    public boolean executeAction(String ag, Structure action) {
	    	lock.lock();  // block until condition holds
	        try {
	    	 System.out.println("["+ag+"] doing: "+action);
	    	 if(action.equals(nc)) {
	             if(ag.equals("vacuumAgent1")) {
	            	 model.movePerimeter(model.getAgentByName(ag));
	             } else if(ag.equals("vacuumAgent2")) {
	            	 model.movePerimeter(model.getAgentByName(ag));
	             } else if(ag.equals("vacuumAgent3")) {
	            	 model.movePerimeter(model.getAgentByName(ag));
	             }
	    	 }
	    	 else if(action.getFunctor().equals(mr)) {
	             if(ag.equals("vacuumAgent1")) {
	            	// model.removeGarbage(model.getAgentByName(ag));
	            	 model.pickGarb(model.getAgentByName(ag));
	             }
	             else if(ag.equals("vacuumAgent2")) {
	            	// model.removeGarbage(model.getAgentByName(ag));
	            	 model.pickGarb(model.getAgentByName(ag));
	            	 	
	             } else if(ag.equals("vacuumAgent3")) {
	            	// model.removeGarbage(model.getAgentByName(ag));
	            	 model.pickGarb(model.getAgentByName(ag));
	             }
	    	}
	    	 else if(action.getFunctor().equals(dr)) {
	    		 if(ag.equals("vacuumAgent1")) {
	            	 model.dropGarbage(model.getAgentByName(ag));
	             }
	             else if(ag.equals("vacuumAgent2")) {
	            	 model.dropGarbage(model.getAgentByName(ag));
	             } 
	             else if(ag.equals("vacuumAgent3")) {
	            	 model.dropGarbage(model.getAgentByName(ag));
	             }
	    	 }
	    	 else if(action.getFunctor().equals("move_towards")) {
	             int x = (new Integer(action.getTerm(0).toString())).intValue();
	             int y = (new Integer(action.getTerm(1).toString())).intValue();
	             
	             if(ag.equals("vacuumAgent1")) {
	            	 model.moveTowards(x, y, model.getAgentByName(ag));
	             }
	             else if(ag.equals("vacuumAgent2")) {
	            	 model.moveTowards(x, y, model.getAgentByName(ag));
	             }
	             else if(ag.equals("vacuumAgent3")) {
	            	 model.moveTowards(x, y, model.getAgentByName(ag));
	             }
	             
	    	 }
	    	 model.reInstate();
	    	 updatePercepts();
	    	 Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
			       lock.unlock();}
	    	// repaint();

	        informAgsEnvironmentChanged();
	    	 return true;
	    }
	    

}
