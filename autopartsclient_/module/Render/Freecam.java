package autopartsclient.module.Render;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.FreecamUtils.FreecamUtil;

public class Freecam extends Mod {

	public Logger logger = LogManager.getLogger(Freecam.class);

	public static boolean isToggled;

	public Freecam() {
		super("Freecam", "", Category.RENDER);
		this.setKey(GLFW.GLFW_KEY_C);
	}

	@Override
	public void onEnable() {
		isToggled = true;
		FreecamUtil.toggle();

	}

	@Override
	public void onDisable() {
		isToggled = false;
		FreecamUtil.disableNextTick();
		FreecamUtil.toggle();
		FreecamUtil.disableNextTick();
	}

}
