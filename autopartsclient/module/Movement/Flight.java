package autopartsclient.module.Movement;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Flight extends Mod{
	public NumberSetting speed = new NumberSetting("Speed", 1, 10, 1, 0.5);
	public BooleanSetting testBool = new BooleanSetting("TestBool", true);
	public ModeSetting testMode = new ModeSetting("Mode", "Normal", "Bypass", "Normal");
	
	double MAX_SPEED = 1;
	double FALL_SPEED = -0.035;
	double acceleration = 1;

	MinecraftClient client = MinecraftClient.getInstance();
	
	public Flight() {
		super("Flight", "Fly Hack", Category.MOVEMENT);
		this.setKey(GLFW.GLFW_KEY_G);
		addSetting(testMode);
	}
	
	@Override
	public void onEnable() {
		ChatUtils.message("Flight Enabled");
	}
	
	@Override
	public void onTick() {		
		if(testMode.getMode() == "Normal") {
		mc.player.getAbilities().flying = true;
		}
		else if (testMode.getMode() == "Bypass") {
			boolean jumpPressed = client.options.jumpKey.isPressed();
			boolean forwardPressed = client.options.forwardKey.isPressed();
			boolean leftPressed = client.options.leftKey.isPressed();
			boolean rightPressed = client.options.rightKey.isPressed();
			boolean backPressed = client.options.backKey.isPressed();

			Entity entity = client.player;
			if (client.player.hasVehicle()) {
				entity = client.player.getVehicle();
			}

			Vec3d velocity = entity.getVelocity();
			Vec3d newVelocity = new Vec3d(velocity.x, -FALL_SPEED, velocity.z);

			if (jumpPressed) {
				if (forwardPressed) {
					newVelocity = client.player.getRotationVector().multiply(MAX_SPEED);
				}
				if (leftPressed && !client.player.hasVehicle()) {
					
					newVelocity = client.player.getRotationVector().multiply(MAX_SPEED).rotateY(3.1415927F / 2);
					
					newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
				}
				if (rightPressed && !client.player.hasVehicle()) {
					
					newVelocity = client.player.getRotationVector().multiply(MAX_SPEED).rotateY(-3.1415927F / 2);
					
					newVelocity = new Vec3d(newVelocity.x, 0, newVelocity.z);
				}
				if (backPressed) {
					newVelocity = client.player.getRotationVector().negate().multiply(MAX_SPEED);
				}

				newVelocity = new Vec3d(newVelocity.x, (newVelocity.y > FALL_SPEED) ? FALL_SPEED : newVelocity.y + 1, newVelocity.z);
				entity.setVelocity(newVelocity);
			}
		}
		super.onTick();
	}
	
	@Override
	public void onDisable() {
		ChatUtils.message("Flight Disabled");
		mc.player.getAbilities().flying = false;
		super.onDisable();
	}
}
