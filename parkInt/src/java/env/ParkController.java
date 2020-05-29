package env;
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.Random;


public class ParkController extends Environment{

		static Logger logger = Logger.getLogger(ParkController.class.getName());

		 private final ReentrantLock lock = new ReentrantLock();

			public final String mr = new String("mine");
	    public final String dr = new String("drop");
			public final String generateDest = new String("generateDest");
			public final String mightLitter = new String("mightLitter");
			public final String walk = new String("walk");
	    public final Term nc = Literal.parseLiteral("move_to(next_cell)");
	    public final Term cm = Literal.parseLiteral("consume(metal)");
	    public final Term cpl = Literal.parseLiteral("consume(plastic)");
	    public final Term cpp = Literal.parseLiteral("consume(paper)");
	    public  Literal vc1gb;
			public final static int HUMAN_ID = 6;




	    ParkModel model; // the model of the grid
			private Location humanDestination = new Location(10, 10);
			private Random rand = new Random();

	    @Override
	    public void init(String[] args) {
	        model = new ParkModel();
	        ParkView view  = new ParkView(model);
	        model.setView(view);

	        updatePercepts();
	    }

	    void updatePercepts() {
	    	clearPercepts("paperDump");
	    	clearPercepts("metalDumpt");
	    	clearPercepts("plasticDump");
	    	clearPercepts("vacuumAgent1");
	    	clearPercepts("vacuumAgent2");
	    	clearPercepts("vacuumAgent3");
				clearPercepts("human");

	        Location metalAg = model.getAgPos(0);
	        Location plastAg = model.getAgPos(1);
	        Location paperAg = model.getAgPos(2);
	        Location vacuumAgent1 = model.getAgPos(3);
	        Location vacuumAgent2 = model.getAgPos(4);
	        Location vacuumAgent3 = model.getAgPos(5);

	        Literal pos3 = Literal.parseLiteral("my_pos(" + vacuumAgent1.x + "," + vacuumAgent1.y + ")");
	        Literal pos4 = Literal.parseLiteral("my_pos(" + vacuumAgent2.x + "," + vacuumAgent2.y + ")");
	        Literal pos5 = Literal.parseLiteral("my_pos(" + vacuumAgent3.x + "," + vacuumAgent3.y + ")");


	        addPercept("vacuumAgent1",pos3);
	        addPercept("vacuumAgent2",pos4);
	        addPercept("vacuumAgent3",pos5);
	        int tmp = model.isThisGarbage(vacuumAgent1);
	        if(tmp != -1) {
                vc1gb = Literal.parseLiteral("found("+tmp+")");
                addPercept("vacuumAgent1",vc1gb);
            }
	        tmp = model.isThisGarbage(vacuumAgent2);
	        if(tmp != -1) {
                vc1gb = Literal.parseLiteral("found("+tmp+")");
                addPercept("vacuumAgent2",vc1gb);
            }
	        tmp = model.isThisGarbage(vacuumAgent3);
	        if(tmp != -1) {
                vc1gb = Literal.parseLiteral("found("+tmp+")");
                addPercept("vacuumAgent3",vc1gb);
            }

	        if(model.isThisGarbage(metalAg) == model.METAL) {
                vc1gb = Literal.parseLiteral("trash("+model.METAL+")");
                addPercept("metalDump",vc1gb);
	        }
	        if(model.isThisGarbage(plastAg) == model.PLAST) {
                vc1gb = Literal.parseLiteral("trash("+model.PLAST+")");
                addPercept("plasticDump",vc1gb);
	        }
	        if(model.isThisGarbage(paperAg) == model.PAPER) {
                vc1gb = Literal.parseLiteral("trash("+model.PAPER+")");
                addPercept("paperDump",vc1gb);
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
	    	 else if(action.getFunctor().equals(cm)) {
	    		 model.remove(model.METAL, model.getAgPos(model.getAgentByName(ag)));
	    	 }
	    	 else if(action.getFunctor().equals(cpp)) {
	    		 model.remove(model.PAPER, model.getAgPos(model.getAgentByName(ag)));
	    	 }
	    	 else if(action.getFunctor().equals(cpl)) {
	    		 model.remove(model.PLAST, model.getAgPos(model.getAgentByName(ag)));
	    	 }
				 else if(action.getFunctor().equals(walk)){
					 // Humans is just walking towars their destination
					 model.moveTowards(humanDestination.x, humanDestination.y, HUMAN_ID);
				 }
				 else if(action.getFunctor().equals(mightLitter)){
					 // Humans might drop their garbage
					 int r = rand.nextInt(10);
					 if(r < 1){
						 logger.info("Hahaha GARBAGE goes puff");
						 int chooser = rand.nextInt(3);
						 int type;
						 switch(chooser){
							 case 0:
							 	type = ParkModel.METAL;
								break;
							case 1:
								type = ParkModel.PLAST;
								break;
							default:
								type = ParkModel.PAPER;
								break;
						 }

						 Location humanLoc = model.getAgPos(HUMAN_ID);
						 model.add(type, humanLoc);
					 }
				 }
				 else if(action.getFunctor().equals(generateDest)){
					 // Might change the place they want to go
					 int r = rand.nextInt(10);
					 if(r < 3){
						 int x = rand.nextInt(model.GSize);
						 int y = rand.nextInt(model.GSize);

						 logger.info(String.format("I decided that I want to go to %d : %d", x, y));

						 humanDestination = new Location(x, y);
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
