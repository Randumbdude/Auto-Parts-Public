package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class NoClickCooldown extends Mod{
	public static boolean isToggled;

	public NoClickCooldown() {
		super("NoClickCool", "", Category.PLAYER);
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
