package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class Sprint extends Mod{
	
	public Sprint() {
		super("Sprint", "Toggles Sprint", Category.MOVEMENT);
	}
	@Override
	public void onEnable() {
		ChatUtils.message("Sprint Enabled");
	}
	
	@Override
	public void onTick() {
		mc.player.setSprinting(true);
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		ChatUtils.message("Sprint Disabled");
		super.onDisable();
	}
}
