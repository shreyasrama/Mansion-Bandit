package mansionBandit.gameView;

public class DEMOOBJECTbackup {
	static String face = "testFace";
	private int x, y;
	private int size;
	
	/* assuming that each wall is a 100x100 grid, and that each object will be a
	 * size based on that grid. the value 20 means that the object will be 20x20
	 * in terms of the the walls 100x100
	 * 
	 */
	public DEMOOBJECTbackup(){
		size = 20;
		x = (int) ((100 - size) * Math.random()) + (size / 2);
		y = (int) ((100 - size) * Math.random()) + size;
		//x = 20;
		//y = 20;
	}
	
	public DEMOOBJECTbackup(int xx, int yy){
		size = 25;
		x = xx;
		y = yy;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public String getFace(){
		return face;
	}
}
