package autopartsclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;

public class FakePlayerEntity extends AbstractClientPlayerEntity {
	
	static MinecraftClient mc = MinecraftClient.getInstance();

	public FakePlayerEntity() {
		super(mc.world, mc.player.getGameProfile());
		ClientPlayerEntity player = mc.player;
		this.setPos(player.getPos().x, player.getPos().y, player.getPos().z);
		this.setRotation(player.getYaw(mc.getTickDelta()),
				player.getPitch(mc.getTickDelta()));
		//this.inventory = player.getInventory();
	}

	public void despawn() {
		this.remove(RemovalReason.DISCARDED);
	}
}