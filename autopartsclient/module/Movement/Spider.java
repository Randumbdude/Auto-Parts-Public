package autopartsclient.module.Movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import net.minecraft.util.math.Box;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class Spider extends Mod {
	public int key;

	public Spider() {
		super("Spider", "Climb Walls", Category.MOVEMENT);
	}


	@Override
	public void onTick() {
		if (mc.options.forwardKey.isPressed()) {
			if (mc.options.forwardKey.isPressed() && mc.options.sneakKey.isPressed()) {
				mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0));
				mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(),
						mc.player.getY() + 0.32f, mc.player.getZ(), true));
				mc.player.setSneaking(false);
				mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0));
				mc.player.addVelocity(0, -0.32f, 0);
			} else {
				return;
			}
		}
	}
}