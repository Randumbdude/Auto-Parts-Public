package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import autopartsclient.module.Mod.Category;
import autopartsclient.util.Player.PlayerUtils;

public class Speed extends Mod{
	public Speed() {
		super("Speed", "", Category.MOVEMENT);
	}
	
	@Override
	public void onTick() {
		PlayerUtils.setSpeed(getKey(), getKey(), getKey(), getKey(), getKey());
		super.onTick();
	}
}
