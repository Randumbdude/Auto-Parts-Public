package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class NoSlow extends Mod{
	public static boolean isToggled;

	public NoSlow() {
		super("NoSlow", "", Category.PLAYER);
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
