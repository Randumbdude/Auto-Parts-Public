package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;

public class Reach extends Mod{
	public static boolean isToggled;
	
	public static float reachlength = 3;
	
	public static ModeSetting Range = new ModeSetting("Range", "4", "5", "6", "4");
	
	public Reach() {
		super("Reach", "", Category.PLAYER);
		addSetting(Range);
	}
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		isToggled = true;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		isToggled = false;
		super.onDisable();
	}
	
	@Override
	public void onTick() {
		if(Range.getMode() == "4") {
			reachlength = 4;
		}
		else if(Range.getMode() == "5") {
			reachlength = 5;
		}
		else if(Range.getMode() == "6") {
			reachlength = 6;
		}
		else {
			reachlength = 4;
		}
	}
}
