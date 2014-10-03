package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mansionBandit.gameWorld.areas.Room;
import mansionBandit.gameWorld.matter.Couch;
import mansionBandit.gameWorld.matter.Decoration;
import mansionBandit.gameWorld.matter.Face;
import mansionBandit.gameWorld.matter.Dimensions;
import mansionBandit.gameWorld.matter.GameMatter;

public class GamePanel extends JPanel{
	int height = 600;
	int width = 800;

	//TODO get rid of demo
	public RoomView room;
	
	/**
	 * returns the object the user clicked on
	 * x and y should be given as relative to this panel, not the frame it is contained in
	 * 
	 * @param x position to check
	 * @param y position to check
	 * @return the GameMatter object, whatever that may be, or null if non found
	 */
	public GameMatter getObject(int x, int y){
		return room.findObjectByMouse(x, y);
	}

	public GamePanel(){
		//TODO fully integrate
		//currently this is a test integration of objects
		Room demoRoom = new Room("wall1", "ceiling1", "carpet1");
		//demo object to be placed on all sides
		demoRoom.addItem(makeDeco(Face.FLOOR));
		demoRoom.addItem(makeDeco(Face.EASTERN));
		demoRoom.addItem(makeDeco(Face.NORTHERN));
		demoRoom.addItem(makeDeco(Face.SOUTHERN));
		demoRoom.addItem(makeDeco(Face.CEILING));
		demoRoom.addItem(makeDeco(Face.WESTERN));
		//room = new RoomView(new DEMOROOM(), 0, 0, width, height, 0);
		room = new RoomView(demoRoom, 0, 0, width, height, 0);
	}

	//TODO remove
	private Decoration makeDeco(Face face){
		int size = 20;
		int x = (int) ((100 - size) * Math.random()) + (size / 2);
		int y = (int) ((100 - size) * Math.random()) + size;
		return new Decoration("testFace", face, new Dimensions(x, y, size));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		room.update();
		room.paintRoom(g);
	}
}
