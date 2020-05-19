package env;
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;


public class ParkController extends Environment{

	 static Logger logger = Logger.getLogger(ParkController.class.getName());

	    ParkModel model; // the model of the grid

	    @Override
	    public void init(String[] args) {
	        model = new ParkModel();

	       // if (args.length == 1 && args[0].equals("gui")) {
	            ParkView view  = new ParkView(model);
	            model.setView(view);
	      //  }

	        updatePercepts();
	    }
	    
	    void updatePercepts() {
	    	clearPercepts("paperDump");
	    	clearPercepts("metalDumpt");
	    	clearPercepts("plasticDump");
	    	clearPercepts("vacuumAgent");

	        Location metalAg = model.getAgPos(0);
	        Location plastAg = model.getAgPos(1);
	        Location paperAg = model.getAgPos(2);
	        Location vacuumAgent = model.getAgPos(3);
	    }

}
