package autopartsclient.module.Misc;

import autopartsclient.module.Mod;

public class AntiKick extends Mod{
	public static boolean isToggled;
	
	public AntiKick() {
		super("AntiKick", "", Category.MISC);
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
}
