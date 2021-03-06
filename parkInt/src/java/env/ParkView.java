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
        setBackground(Color.green);
        setVisible(true);
        repaint();
	}
	@Override
    public void draw(Graphics g, int x, int y, int object) {
        switch (object) {
        case ParkModel.PLAST:
    		drawGarb(g, x, y, 64);
	        break;
        case ParkModel.PAPER:
        	drawGarb(g, x, y, 32);
	        break;
        case ParkModel.METAL:
        	drawGarb(g, x, y, 16);
	        break;
		case ParkModel.OBSTACLE:
			drawObstacle(g, x, y);
		}
    }

	public void drawObstacle(Graphics g, int x, int y) {
			Color treeColor = new Color(83, 53, 10);
			g.setColor(treeColor);
			super.drawAgent(g, x, y, treeColor, -1);
		}

	public void drawGarb(Graphics g, int x, int y, int t) {
            switch (t) {
            case 64:
    	        g.setColor(Color.green);
    	        drawString(g, x, y, defaultFont,"Pl");
    	        break;
            case 32:
    	        g.setColor(Color.blue);
    	        drawString(g, x, y, defaultFont,"Pa");
    	        break;
            case 16:
    	        g.setColor(Color.yellow);
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
        	c = Color.blue;
            super.drawAgent(g, x, y, c, -1);
            g.setColor(Color.black);
            super.drawString(g, x, y, defaultFont, "Pa");
        	break;
		case 6: // Human
			c = new Color(240, 184, 160); // Some kind of skin color
			super.drawAgent(g, x, y, c, -1);
			break;
		case 7: // Human
			c = new Color(240, 184, 160); // Some kind of skin color
			super.drawAgent(g, x, y, c, -1);
			break;
	    default:
	    	c = Color.gray;
	        super.drawAgent(g, x, y, c, -1);
	    	break;
	    }
    }


}
