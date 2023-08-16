package autopartsclient.module.Movement;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class Phase extends Mod {
    public static boolean isToggled;

    public static ModeSetting Mode = new ModeSetting("Mode", "Normal", "Blink", "Normal");

    public Phase() {
	super("Phase", "", Category.MOVEMENT);
	this.setKey(GLFW.GLFW_KEY_Z);
	addSetting(Mode);
    }

    @Override
    public void onEnable() {
	mc.player.noClip = true;
	isToggled = true;
	super.onEnable();
    }

    @Override
    public void onDisable() {
	mc.player.noClip = false;
	isToggled = false;
	super.onDisable();
    }

    @Override
    public void onTick() {
	if (Mode.getMode() == "Blink") {
	    mc.player.setBoundingBox(new Box(new Vec3d(0, 0, 0), new Vec3d(0, 0, 0)));
	    //mc.player.setYaw(mc.player.getYaw() + 10f);
	    //mc.player.setYaw(mc.player.getYaw() - 10f);
	}
	else if (Mode.getMode() == "Normal") {
	    //e
	}
    }
}
