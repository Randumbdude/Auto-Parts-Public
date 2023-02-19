package autopartsclient.module.Render;

import autopartsclient.module.Mod;

public class UnfocusedCPU extends Mod{
	public static boolean isToggled;
	
	public UnfocusedCPU() {
		super("UnfocusedCPU", "", Category.RENDER);
		this.showInArray = false;
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
