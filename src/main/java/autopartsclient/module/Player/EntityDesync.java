package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;

public class EntityDesync extends Mod{
	
	Entity ridingEntity;
	public static boolean isToggled;
	
	public EntityDesync() {
		super("EntityDesync", "", Category.PLAYER);
	}
	
	@Override
	public void onEnable() {
		isToggled = true;
		
		if (mc.player != null) {
			if (!mc.player.isRiding()) {
				ridingEntity = mc.player.getVehicle();
				mc.player.dismountVehicle();
				try {
					mc.world.removeEntity(ridingEntity.getId(), RemovalReason.UNLOADED_TO_CHUNK);
				} catch (Exception e) {
					ChatUtils.message("You Need To Be Riding Something");
					this.toggle();
				}
			} else {
				ChatUtils.message("You Need To Be Riding Something");
				ridingEntity = null;
				this.toggle();
			}
		} else {
			ridingEntity = null;
			this.toggle();
			return;
		}
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		isToggled = false;
		
		if (ridingEntity != null) {
			if (mc.player.isRiding()) {
				mc.world.spawnEntity(ridingEntity);
				mc.player.startRiding(ridingEntity, true);
			}
			ridingEntity = null;
			ChatUtils.message("Remounted");
		}
		super.onDisable();
	}
	
	@Override
	public void onTick() {
		if (ridingEntity == null) return;
//		mc.player.setOnGround(true);
		ridingEntity.setPos(mc.player.getX(), mc.player.getY(), mc.player.getZ());
		mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(ridingEntity));
		super.onTick();
	}
}
