package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class AntiKB extends Mod{
	public static boolean isToggled;

	public AntiKB() {
		super("AntiKB", "", Category.PLAYER);
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
