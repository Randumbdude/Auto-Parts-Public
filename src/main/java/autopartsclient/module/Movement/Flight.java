package autopartsclient.module.Movement;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.Key.IKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.option.KeyBinding;

public class Flight extends Mod {
	
	public BooleanSetting DoAnticheat = new BooleanSetting("AntiKick", true);

	public ModeSetting testMode = new ModeSetting("Mode", "Normal", "Normal", "Bypass");
	
	public ModeSetting antiKickThing = new ModeSetting("Distance", "0.07", "0.1", "0.2", "0.07");
	
	public NumberSetting Speed = new NumberSetting("Speed", 0.1, 10, 0.1, 0.1);

	// bypass
	double MAX_SPEED = 1;
	double FALL_SPEED = -0.035;
	double acceleration = 1;
	// creative
	private int tickCounter = 0;
	private double antiKickDistance = 0.07;
	private double antiKickInterval = 30;

	MinecraftClient client = MinecraftClient.getInstance();

	public Flight() {
		super("Flight", "Fly Hack", Category.MOVEMENT);
		this.setKey(GLFW.GLFW_KEY_G);
		addSetting(Speed, testMode, DoAnticheat, antiKickThing);
	}

	@Override
	public void onTick() {
		if (testMode.getMode() == "Normal") {
			mc.player.getAbilities().allowFlying = true;
			mc.player.getAbilities().flying = true;
			mc.player.getAbilities().setFlySpeed(Speed.getValueFloat());
			
			if(!DoAnticheat.isEnabled()) {
				return;
			}
			
			else if(antiKickThing.getMode() == "0.07") {
				antiKickDistance = 0.07;
				doAntiKick();
			}
			else if(antiKickThing.getMode() == "0.1") {
				antiKickDistance = 0.01;
				doAntiKick();
			}
			else if(antiKickThing.getMode() == "0.2") {
				antiKickDistance = 0.2;
				doAntiKick();
			}
		} else if (testMode.getMode() == "Bypass") {
			mc.player.getAbilities().allowFlying = true;
			mc.player.getAbilities().flying = false;

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

				newVelocity = new Vec3d(newVelocity.x, (newVelocity.y > FALL_SPEED) ? FALL_SPEED : newVelocity.y + 1,
						newVelocity.z);
				entity.setVelocity(newVelocity);
			}
		} else if (testMode.getMode() == "Packet") {
			
		}
	}

	@Override
	public void onDisable() {
		mc.player.getAbilities().flying = false;
		mc.player.getAbilities().allowFlying = false;
		super.onDisable();
	}

	private void doAntiKick() {
		if (tickCounter > antiKickInterval + 2)
			tickCounter = 0;

		switch (tickCounter) {
		case 0 -> {
			if (mc.options.sneakKey.isPressed() && !mc.options.jumpKey.isPressed())
				tickCounter = 3;
			else
				setMotionY(-antiKickDistance);
		}

		case 1 -> setMotionY(antiKickDistance);

		case 2 -> setMotionY(0);

		case 3 -> restoreKeyPresses();
		}

		tickCounter++;
	}

	private void setMotionY(double motionY) {
		mc.options.sneakKey.setPressed(false);
		mc.options.jumpKey.setPressed(false);

		Vec3d velocity = mc.player.getVelocity();
		mc.player.setVelocity(velocity.x, motionY, velocity.z);
	}

	private void restoreKeyPresses() {
		KeyBinding[] bindings = { mc.options.jumpKey, mc.options.sneakKey };

		for (KeyBinding binding : bindings)
			((IKeyBinding) binding).resetPressedState();
	}
}
