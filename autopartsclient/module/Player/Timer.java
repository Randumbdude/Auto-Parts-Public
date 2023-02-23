package autopartsclient.module.Player;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;

public class Timer extends Mod{
	public static boolean isToggled;
	
	public static double TimerSpeed = 2;
	
	public static ModeSetting SpeedSetting = new ModeSetting("Speed", "1", "1.3", "1.5", "2", "3", "1");
	
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
		if(SpeedSetting.getMode() == "1") {
			TimerSpeed = 1;
		}
		else if(SpeedSetting.getMode() == "1.3") {
			TimerSpeed = 1.3;
		}
		else if(SpeedSetting.getMode() == "1.5") {
			TimerSpeed = 1.5;
		}
		else if(SpeedSetting.getMode() == "2") {
			TimerSpeed = 2;
		}
		else if(SpeedSetting.getMode() == "3") {
			TimerSpeed = 3;
		}
		else {
			TimerSpeed = 1;
		}
	}
}
