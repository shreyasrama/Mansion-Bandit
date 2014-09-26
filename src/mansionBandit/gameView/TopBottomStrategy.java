package mansionBandit.gameView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class TopBottomStrategy implements SurfaceStrategy {
	private boolean ceiling;
	private Surface surface;
	private BufferedImage surfaceTexture;
	private int surfaceX, surfaceY, surfaceWidth, surfaceHeight;
	
	public TopBottomStrategy(boolean top) {
		this.ceiling = top;
	}

	@Override
	public void paintSurface(Graphics g) {
		g.drawImage(surfaceTexture, surfaceX, surfaceY, surfaceWidth, surfaceHeight, null);

		//draw objects on the wall
		for (DrawnObject ob : surface.objects){
			BufferedImage obImage = null;
			BufferedImage shadow = null;
			try {
				obImage = ImageIO.read(this.getClass().getResource("/object/" + ob.getGameObject().getFace() + ".png"));
				shadow = ImageIO.read(this.getClass().getResource("/object/shadow.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO move into its own loop to prevent shadows overlapping other objects
			if (!ceiling){
				//draw shadow if object on floor
				g.drawImage(shadow, ob.getBoundX() -10, ob.getBoundY() + ob.getHeight() - 10, ob.getWidth() + 20, 20, null);
			}
			//draw object
			g.drawImage(obImage, ob.getBoundX(), ob.getBoundY(), ob.getWidth(), ob.getHeight(), null);
		}
	}

	@Override
	public Object click(int x, int y) {
		if (ceiling){
			return null;
		}
		//TODO search floor for items
		return null;
	}

	@Override
	public void setupSurface(Surface surface, DEMOWALL wall) {
		this.surface = surface;
		try {
			//set image for the view
			if (ceiling){
				surfaceTexture = ImageIO.read(this.getClass().getResource("/ceilings/" + surface.roomView.room.getCeiling() + ".png"));
			} else {
				surfaceTexture = ImageIO.read(this.getClass().getResource("/floors/" + surface.roomView.room.getFloor() + ".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set bounds for the surface
		surfaceX = surface.roomView.boundX;
		surfaceWidth = surface.roomView.width;
		surfaceHeight = surface.roomView.height/4;
		if (ceiling){
			surfaceY = surface.roomView.boundY;
		} else {
			surfaceY = surface.roomView.boundY + ((surface.roomView.height * 3) / 4);
		}

		//create object list for surface
		createGameObjects(wall);
	}
	
	/**
	 * wraps objects to be drawn into DrawnObjects
	 * with appropriate size and position distortion
	 * 
	 * @param wall
	 */
	private void createGameObjects(DEMOWALL wall){
		List<DrawnObject> obs = new ArrayList<DrawnObject>();
		
		//loop through objects on floor, and resize them
		for (DEMOOBJECT ob : wall.getObjects()){
			
			//determine x and y based on direction facing in room
			int x = getX(ob);
			int y = getY(ob);
			
			//get base height of object
			int size = (int) ((((double) ob.getSize()) / 100) * (surfaceHeight * 4));
			
			/* determine width and height based on distance away from viewer perspective
			 * this causes items that are further away to appear smaller
			 * (variable scale is the level of scaling to apply as a double between 0.5 and 1) */
			double scale = 0.5 + (0.5 * (((double) y) / 100));
			if (this.ceiling){
				//invert scale if this object is on the ceiling
				scale = 0.5 + (1 - scale);
			}
			//apply scale
			size = (int) (size * scale);
						
			//determine where the horizontal center of the image should be
			int objectCenterX = (int) (surfaceX + (x * ((double) surfaceWidth / 100)));
			//have to alter horizontal position to be closer to the center when further back
			int surfaceCenterX = surfaceX + (surfaceWidth / 2);
			int diff = Math.abs(surfaceCenterX - objectCenterX);
			//apply scaling to the diff
			diff = (int) (diff * scale);
			
			//apply the new x position, and account for having to draw from top left corner
			if (objectCenterX < surfaceCenterX){
				objectCenterX = surfaceCenterX - diff - (size / 2);
			} else if (surfaceCenterX < objectCenterX){
				objectCenterX = surfaceCenterX + diff - (size / 2);
			}
			
			//TODO change variable names so that we're not relying on scope?
			int top = (int) (surfaceY + (y * ((double) surfaceHeight / 100)));
			if (!this.ceiling){
				//objects y positions are anchored at the top of the object if being drawn on the ceiling, so no need to apply here
				top -= size;
			}
			
			//create the wrapped object and add to list
			DrawnObject dob = new DrawnObject(ob, objectCenterX, top, size, size);
			obs.add(dob);
		}
		surface.objects = obs;
	}
	
	private int getX(DEMOOBJECT ob){
		if (surface.roomView.room.getDirection() == DEMOROOM.E){
			if (ceiling){
				return 100 - ob.getY();
			}
			return ob.getY();
		}
		if (surface.roomView.room.getDirection() == DEMOROOM.S){
			return 100 - ob.getX();
		}
		if (surface.roomView.room.getDirection() == DEMOROOM.W){
			if (ceiling){
				return ob.getY();
			}
			return 100 - ob.getY();
		}
		//must be facing north
		return ob.getX();
	}
	
	private int getY(DEMOOBJECT ob){
		if (surface.roomView.room.getDirection() == DEMOROOM.E){
			if (ceiling){
				return ob.getX();
			}
			return 100 - ob.getX();
		}
		if (surface.roomView.room.getDirection() == DEMOROOM.S){
			return 100 - ob.getY();
		}
		if (surface.roomView.room.getDirection() == DEMOROOM.W){
			if (ceiling){
				return 100 - ob.getX();
			}
			return ob.getX();
		}
		//must be facing north
		return ob.getY();
	}
	
	/**
	 * orders objects according to those that should be drawn first
	 * 
	 * @param wall
	 */
	private void arrangeObjects(DEMOWALL wall){
		
	}

}