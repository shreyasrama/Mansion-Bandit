package mansionBandit.gameWorld.matter;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import mansionBandit.gameWorld.areas.MansionArea;


/**
 * All Items come under this title
 * @author Liam De Grey
 *
 */
public interface GameMatter extends Serializable {

	/**
	 * returns the face that an item is on
	 */
	public Face getFace();

	/**
	 * sets the Face that the item is on/looking at
	 */
	public void setFace(Face f);

	/**
	 * returns the position of the item
	 */
	public Dimensions getDimensions();

	/**
	 * sets the Dimensions of the item
	 */
	public void setDimensions(Dimensions d);

	/**
	 * returns current room of the item
	 */
	public MansionArea getArea();

	/**
	 * sets the room of the item
	 */
	public void setArea(MansionArea r);

	/**
	 * returns the name of object, to be used for png files
	 */
	public String getName();

	/**
	 * returns the Image of the matter
	 */
	public BufferedImage getImage();

}
