package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class Step extends Mod{
	public Step() {
		super("Step", "", Category.MOVEMENT);
	}
	
	@Override
	public void onEnable() {
		ChatUtils.message("Step Enabled");
		mc.player.stepHeight = 2;
		super.onEnable();
	}
	@Override
	public void onDisable() {
		ChatUtils.message("Step Disabled");
		mc.player.stepHeight = .5f;
		super.onDisable();
	}
	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		super.onTick();
	}
}
