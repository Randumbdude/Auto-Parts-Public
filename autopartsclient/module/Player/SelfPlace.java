package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class SelfPlace extends Mod{
	public boolean isToggled;
	
	public SelfPlace() {
		super("SelfPlace", "", Category.PLAYER);
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
