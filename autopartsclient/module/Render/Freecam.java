package autopartsclient.module.Render;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autopartsclient.Client;
import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.FreecamUtils.FreecamUtil;

public class Freecam extends Mod {

	public Logger logger = LogManager.getLogger(Freecam.class);

	public static boolean isToggled;

	public Freecam() {
		super("Freecam", "", Category.RENDER);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("Freecam Enabled");
		isToggled = true;
		FreecamUtil.toggle();

	}

	@Override
	public void onDisable() {
		ChatUtils.message("Freecam Disabled");
		isToggled = false;
		FreecamUtil.disableNextTick();
		FreecamUtil.toggle();
		FreecamUtil.disableNextTick();
	}

}
