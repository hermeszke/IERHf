package env;
import jason.environment.grid.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;
import env.ParkModel.CellType;

public class ParkView extends JPanel {

	private int parkWidth, parkHeight, cellLength;
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
		cellLength = 25;
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

	//TODO: Agent drawing

}
