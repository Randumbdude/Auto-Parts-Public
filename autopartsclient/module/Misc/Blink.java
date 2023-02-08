package autopartsclient.module.Misc;

import autopartsclient.event.events.EventClientMove;
import autopartsclient.event.events.EventOpenScreen;
import autopartsclient.event.events.EventPacket;
import autopartsclient.event.events.EventTick;
import autopartsclient.eventbus.Subscribe;
import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.PlayerCopyEntity;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class Blink extends Mod {

	private PlayerCopyEntity dummy;
	private double[] playerPos;
	private float[] playerRot;
	private Entity riding;

	private boolean prevFlying;
	private float prevFlySpeed;

	public Blink() {
		super("Blink?", "", Category.MISC);
	}

	@Override
	public void onTick() {
	}

	@Override
	public void onEnable() {
		ChatUtils.message("Blink? Enabled");

		mc.chunkCullingEnabled = false;

		playerPos = new double[] { mc.player.getX(), mc.player.getY(), mc.player.getZ() };
		playerRot = new float[] { mc.player.getYaw(), mc.player.getPitch() };

		dummy = new PlayerCopyEntity(mc.player);

		dummy.spawn();

		if (mc.player.getVehicle() != null) {
			riding = mc.player.getVehicle();
			mc.player.getVehicle().removeAllPassengers();
		}

		if (mc.player.isSprinting()) {
			mc.player.networkHandler.sendPacket((Packet<?>) new ClientCommandC2SPacket(mc.player, Mode.STOP_SPRINTING));
		}

		prevFlying = mc.player.getAbilities().flying;
		prevFlySpeed = mc.player.getAbilities().getFlySpeed();
	}

	@Override
	public void onDisable() {
		ChatUtils.message("Blink? Disabled");

		mc.chunkCullingEnabled = true;

		dummy.despawn();
		mc.player.noClip = false;
		mc.player.getAbilities().flying = prevFlying;
		mc.player.getAbilities().setFlySpeed(prevFlySpeed);

		mc.player.refreshPositionAndAngles(playerPos[0], playerPos[1], playerPos[2], playerRot[0], playerRot[1]);
		mc.player.setVelocity(Vec3d.ZERO);

		if (riding != null && mc.world.getEntityById(riding.getId()) != null) {
			mc.player.startRiding(riding);
		}
	}
	
	@Subscribe
	public void sendPacket(EventPacket.Send event) {
		if (event.getPacket() instanceof ClientCommandC2SPacket || event.getPacket() instanceof PlayerMoveC2SPacket) {
			event.setCancelled(true);
		}
	}

	@Subscribe
	public void onOpenScreen(EventOpenScreen event) {
		if (riding instanceof AbstractHorseEntity) {
			if (event.getScreen() instanceof InventoryScreen) {
				mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.OPEN_INVENTORY));
				event.setCancelled(true);
			}
		}
	}

	@Subscribe
	public void onClientMove(EventClientMove event) {
		mc.player.noClip = true;
	}

	@Subscribe
	public void onTick(EventTick event) {
		mc.player.setOnGround(false);
		mc.player.getAbilities().setFlySpeed(1);
		mc.player.getAbilities().flying = true;
		mc.player.setPose(EntityPose.STANDING);
	}
}
