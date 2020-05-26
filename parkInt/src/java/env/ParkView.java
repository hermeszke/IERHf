package env;
import jason.environment.grid.*;

import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import env.ParkModel.CellType;
import env.ParkModel.AgentType;

import java.util.logging.Logger;

public class ParkView extends JPanel {
	static Logger logger = Logger.getLogger(ParkController.class.getName());

	private class AgentDrawInfo{
		int x, y;
		Color agentColor;
		String agentLabel;
	}

	private int parkWidth, parkHeight, cellLength, agentDiameter;
	ParkModel model;
	Color grassColor, obstacleColor, plasticGarbColor, metalGarbColor, paperGarbColor;
	Color vacuumAgColor, metalDumpColor, paperDumpColor, plasticDumpColor;

	public ParkView(ParkModel _model) {
		grassColor = new Color(11, 102, 35);
		obstacleColor = new Color(101, 67, 33);
		plasticGarbColor = new Color(219, 219, 72);
		metalGarbColor = new Color(105, 105, 105);
		paperGarbColor = new Color(0, 0, 150);
		vacuumAgColor = Color.black;
		metalDumpColor = Color.gray;
		paperDumpColor = Color.blue;
		plasticDumpColor = Color.yellow;
		model = _model;
		parkWidth = model.getWidth();
		parkHeight = model.getHeight();
		cellLength = 30;
		agentDiameter = 30;
	}

	protected void paintComponent(Graphics g) {
		CellType cellToDraw;
		for(int y = 0; y < parkHeight; ++y){
			for(int x = 0; x < parkWidth; ++x){
				cellToDraw = model.getCellType(x, y);
				g.setColor(getCellColor(cellToDraw));
				g.fillRect(x * cellLength, y * cellLength, cellLength, cellLength);
			}
		}

		List<Integer> agentIDs = model.getAgentIDs();
		for(int i : agentIDs){
			drawAgent(g, i);
		}
	}

	private Color getCellColor(CellType ct){
		switch(ct){
			case EMPTY:
				return grassColor;
			case PLASTICGARB:
				return plasticGarbColor;
			case METALGARB:
				return metalGarbColor;
			case PAPERGARB:
				return paperGarbColor;
			case OBSTACLE:
				return obstacleColor;
			default:
				return Color.cyan;
		}
	}

	private AgentDrawInfo getAgentDrawInfo(int id){
		AgentType at = model.getAgentType(id);
		AgentDrawInfo adi = new AgentDrawInfo();
		Location loc = model.getAgPos(id);

		adi.x = loc.x * cellLength;
		adi.y = loc.y * cellLength;

		Color agentColor;
		String label;
		switch(at){
			case PLASTIC_BIN:
				agentColor = Color.yellow;
				label = "PL";
				break;
			case METAL_BIN:
				agentColor = Color.gray;
				label = "ME";
				break;
			case PAPER_BIN:
				agentColor = Color.blue;
				label = "PA";
				break;
			case VACUUM:
				agentColor = Color.black;
				label = "VA";
				break;
			default:
				agentColor = Color.cyan;
				label = "";
				break;
		}

		adi.agentColor = agentColor;
		adi.agentLabel = label;

		return adi;
	}

	private void drawAgent(Graphics g, int id){
		AgentDrawInfo adi = getAgentDrawInfo(id);
		g.setColor(adi.agentColor);
		g.fillOval(adi.x, adi.y, agentDiameter, agentDiameter);
		g.setColor(Color.white);
		g.drawString(adi.agentLabel, adi.x +cellLength / 4 + 1, adi.y + cellLength / 2 + 5);
	}

}
