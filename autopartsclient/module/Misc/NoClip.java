package autopartsclient.module.Misc;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import net.minecraft.util.math.Box;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;

public class NoClip extends Mod {
	public int key;

	public NoClip() {
		super("NoClip", "Weird", Category.MISC);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("NoClip Enabled");
	}

	@Override
	public void onDisable() {
		ChatUtils.message("NoClip Disabled");
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

		/*
		 * mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0));
		 * if(mc.options.sneakKey.isPressed()) { mc.getNetworkHandler().sendPacket(new
		 * PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() +
		 * 0.32f, mc.player.getZ(), true)); mc.player.setSneaking(false);
		 * mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0)); mc.player.addVelocity(0,
		 * -0.32f, 0); }
		 */
	}
}

/*
 * 
 * 
 * 
 * mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0));
 * if(mc.options.sneakKey.isPressed()) { mc.getNetworkHandler().sendPacket(new
 * PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() +
 * 0.32f, mc.player.getZ(), true)); mc.player.setSneaking(false);
 * mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0)); mc.player.addVelocity(0,
 * -0.32f, 0); }
 */
