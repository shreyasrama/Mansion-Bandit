package mansionBandit.gameWorld.matter;

import mansionBandit.gameWorld.areas.MansionArea;

/**
 * Guard is an enemy of the bandit and can decrease the
 * bandits health bar
 * @author Liam De Grey
 *
 */
public class Guard extends Character {
	private MansionArea area;

	public Guard(String name, Face face, MansionArea area) {
		super(name, null, null, face, null);
		this.area = area;
	}

	public void wakeUp(final Guard guard){
		Thread clockTick = new Thread(){
			private Bandit prey = null;

			@Override
			public void run(){

				for(GameMatter itm: area.getItems()){
					if(itm instanceof Bandit){
						prey = (Bandit) itm;
					}
				}
				try {
					this.sleep(2500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (prey.getArea().equals(guard.area) && prey.getArea().getItems().contains(guard)){
					prey.kill();
				}
			}
		};
		clockTick.start();
	}

	@Override
	public void kill(){
		area.addItem(new FurnitureStatic("Guards Tombstone", "A nice momento to leave the houseowners", "tomb", Face.FLOOR, new Dimensions(45, 45, 30)));
		area.addItem(new Sellable("Guards Wallet", "Woah that guard was loaded!", "wallet", Face.FLOOR, new Dimensions(55, 55, 20), 500));
		area.getItems().remove(this);
	}

	@Override
	public String getImage(){
		return "guard";
	}

	@Override
	public String getDescription(){
		return "Oh no! It's a guard! Stab him quick!";
	}

	@Override
	public Dimensions getDimensions(){
		return new Dimensions(50, 50, 70);
	}


}
