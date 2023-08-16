package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.NumberSetting;

public class Reach extends Mod{
	public static boolean isToggled;
	
	public static float reachlength = 3;
	
	public static NumberSetting Range = new NumberSetting("Range", 3, 10, 3, 0.1);
	
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
	    reachlength = Range.getValueFloat();
	}
}
