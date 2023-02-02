package autopartsclient.module.Movement;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.ChatUtils;

public class Flight extends Mod{
	public NumberSetting speed = new NumberSetting("Speed", 0, 10, 1, 0.1);
	public BooleanSetting testBool = new BooleanSetting("TestBool", true);
	public ModeSetting testMode = new ModeSetting("TestMode", "test", "Test 2 ", "test 3", "yuh");
	
	public Flight() {
		super("Flight", "Fly Hack", Category.MOVEMENT);
		this.setKey(GLFW.GLFW_KEY_G);
		addSetting();
	}
	
	@Override
	public void onEnable() {
		ChatUtils.message("Flight Enabled");
	}
	
	@Override
	public void onTick() {		
		mc.player.getAbilities().flying = true;
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		ChatUtils.message("Flight Disabled");
		mc.player.getAbilities().flying = false;
		super.onDisable();
	}
}
