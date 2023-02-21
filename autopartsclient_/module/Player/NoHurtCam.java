package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class NoHurtCam extends Mod{
	public static boolean isToggled;
	
	public NoHurtCam() {
		super("NoHurtCam", "", Category.PLAYER);
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
