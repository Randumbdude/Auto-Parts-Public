package autopartsclient.module.Misc;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class Freecam extends Mod {
	public Freecam() {
		super("Freecam", "", Category.MISC);
	}
	
	@Override
	public void onEnable() {
		ChatUtils.message("Freecam Enabled");
		super.onEnable();
	}
	@Override
	public void onDisable() {
		ChatUtils.message("Freecam Disabled");
		super.onDisable();
	}
	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		super.onTick();
	}
}
