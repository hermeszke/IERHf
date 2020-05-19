package env;
import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ParkView extends GridWorldView {

	ParkModel pmodel;
	public ParkView(ParkModel model) {
		super(model, "Park Cleaner", 700);
		// TODO Auto-generated constructor stub
		pmodel = model;
        defaultFont = new Font("Arial", Font.BOLD, 16); // change default font
        setVisible(true);
        repaint();
	}
	
	@Override
    public void draw(Graphics g, int x, int y, int object) {
        super.drawAgent(g, x, y, Color.GREEN, 0);
        super.drawAgent(g, x, y, Color.GREEN, 1);
        super.drawAgent(g, x, y, Color.blue, 2);
        super.drawAgent(g, x, y, Color.blue,3);
        
        repaint();
    }

    @Override
    public void drawAgent(Graphics g, int x, int y, Color c, int id) {
        switch (id) {
        case 0:
       	 	c = Color.yellow;
            super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "M");
            break;
        case 1:
       	 	c = Color.green;
            super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "Pl");
            break;
        case 2:
        	c = Color.red;
            super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "Pa");
        	break;
	    default:
	    	c = Color.black;
	        super.drawAgent(g, x, y, c, -1);
	        g.setColor(Color.white);
	        super.drawString(g, x, y, defaultFont, "VC");
	    	break;
	    }
    }

}
