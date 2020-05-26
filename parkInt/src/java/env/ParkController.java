package env;
import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;
import javax.swing.JFrame;


public class ParkController extends Environment{

	 static Logger logger = Logger.getLogger(ParkController.class.getName());

	    ParkModel model; // the model of the grid
			ParkView view;

	    @Override
	    public void init(String[] args) {
	        model = new ParkModel();

	       // if (args.length == 1 && args[0].equals("gui")) {
	            ParkView view  = new ParkView(model);
	           // model.setView(view);
	      //  }


				JFrame f = new JFrame("Park cleaners");
				f.setBounds(30, 30, 25 * 30 + 15, 25 * 30 + 38);
				f.getContentPane().add(view);
    		f.setVisible(true);

	        updatePercepts();
	    }

			@Override
    public boolean executeAction(String ag, Structure action){
			logger.info(ag + " is doing: " + action);

			return true;
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
