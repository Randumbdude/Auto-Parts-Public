package autopartsclient.module.Misc;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.FakePlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Freecam extends Mod{
	
	private FakePlayerEntity fakePlayer;
	
	public Freecam() {
		super("Freecam", "", Category.MISC);
	}
	
	@Override
	public void onTick() {
		ClientPlayerEntity player = mc.player;
		player.noClip = true;
		player.setOnGround(false);
		float speed = 1;
		if (mc.options.sprintKey.isPressed()) {
			speed *= 1.5;
		}
		player.setVelocity(new Vec3d(0,0,0));
		player.setMovementSpeed(speed * 0.2f);
		player.airStrafingSpeed = speed * 0.2f;
		
		Vec3d vec = new Vec3d(0,0,0);
		if (mc.options.jumpKey.isPressed()) {
			vec = new Vec3d(0,speed * 0.2f,0);
		}
		if (mc.options.sneakKey.isPressed()) {
			vec = new Vec3d(0,-speed * 0.2f,0);
		}

		player.setVelocity(vec);
	}
	
	@Override
	public void onEnable() {
		ChatUtils.message("Freecam Enabled");
		
		ClientPlayerEntity player = mc.player;
		fakePlayer = new FakePlayerEntity();
		fakePlayer.copyFrom(player);
		fakePlayer.headYaw = player.headYaw;
		mc.world.addEntity(-3, fakePlayer);
		
	}
	@Override
	public void onDisable() {
		ChatUtils.message("Freecam Disabled");
		
		ClientPlayerEntity player = mc.player;
		mc.player.noClip = false;
		player.setVelocity(0, 0, 0);
		player.copyFrom(fakePlayer);
		fakePlayer.despawn();
	}
}
