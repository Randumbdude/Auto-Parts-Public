package autopartsclient.module.Movement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autopartsclient.module.Mod;

public class AntiRubberBand extends Mod {
	public Logger logger = LogManager.getLogger(AntiRubberBand.class);

	public static boolean isToggled;

	public AntiRubberBand() {
		super("AntiBand", "", Category.MOVEMENT);
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
