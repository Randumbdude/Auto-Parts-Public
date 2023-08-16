package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class Step extends Mod{
	public Step() {
		super("Step", "", Category.MOVEMENT);
	}
	
	@Override
	public void onDisable() {
		mc.player.setStepHeight(.5f);
		super.onDisable();
	}
	@Override
	public void onTick() {
		// TODO Auto-generated method stub
	    mc.player.setStepHeight(2);
		super.onTick();
	}
}
