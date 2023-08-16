package autopartsclient.module.Player;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.NumberSetting;

public class Timer extends Mod {
    public static boolean isToggled;

    public static double TimerSpeed = 2;

    public static NumberSetting SpeedSetting = new NumberSetting("Speed", 0.1, 10, 1, 0.1);

    public Timer() {
	super("Timer", "", Category.PLAYER);
	addSetting(SpeedSetting);
	setKey(GLFW.GLFW_KEY_V);
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

    @Override
    public void onTick() {
	TimerSpeed = SpeedSetting.getValue();
    }
}
