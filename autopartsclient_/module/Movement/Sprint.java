package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class Sprint extends Mod{
	
	public Sprint() {
		super("Sprint", "Toggles Sprint", Category.MOVEMENT);
	}
	
	@Override
	public void onTick() {
		mc.player.setSprinting(true);
		super.onTick();
	}
}
