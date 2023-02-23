package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class AntiLev extends Mod{
	public static boolean isToggled;

	public AntiLev() {
		super("AntiLev", "", Category.PLAYER);
	}
	
	@Override
	public void onEnable() {
		isToggled = true;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		isToggled = false;
		super.onDisable();
	}
}
