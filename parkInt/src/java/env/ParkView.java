package env;
import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class ParkView extends GridWorldView {

	ParkModel pmodel;
	int cntr = 0;
	
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
    	g.setColor(Color.green);
        switch (object) {
        case ParkModel.PLAST:
    		drawGarb(g, x, y, 64);
	        break;
        case ParkModel.PAPER:
        	drawGarb(g, x, y, 32);
	        break;
        case ParkModel.METAL:
        	drawGarb(g, x, y, 16);
	        break;}
    }
	
	public void drawGarb(Graphics g, int x, int y, int t) {
            switch (t) {
            case 64:
    	        g.setColor(Color.yellow);
    	        drawString(g, x, y, defaultFont,"Pl");
    	        break;
            case 32:
    	        g.setColor(Color.green);
    	        drawString(g, x, y, defaultFont,"Pa");
    	        break;
            case 16:
    	        g.setColor(Color.red);
    	        drawString(g, x, y, defaultFont,"M");
    	        break;
            }
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
