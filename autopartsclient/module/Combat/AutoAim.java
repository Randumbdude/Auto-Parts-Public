package autopartsclient.module.Combat;

import org.apache.logging.log4j.LogManager;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

public class AutoAim extends Mod {
	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(AutoAim.class);
	
	public BooleanSetting players = new BooleanSetting("players", true);
	public BooleanSetting passiveMobs = new BooleanSetting("Passive", false);
	public BooleanSetting hostileMobs = new BooleanSetting("Hostile", true);
	
	private static float yaw;
	private static float pitch;

	public AutoAim() {
		super("AutoAim", "Auto Aims To Target", Category.COMBAT);
		addSetting(players, passiveMobs, hostileMobs);
	}

	@Override
	public void onTick() {

		for (Entity entity : mc.world.getEntities()) {
			if (entity instanceof PlayerEntity & entity != mc.player && players.isEnabled()) {
				
				float distanceTo = mc.player.distanceTo(entity);
				
				if (distanceTo <= 5 && entity.isAlive() == true) {
					
					getRotationToEntity(entity);

					mc.player.setYaw(yaw);
					mc.player.setPitch(pitch);
				}
			}
			else if (entity instanceof HostileEntity & entity != mc.player && hostileMobs.isEnabled()) {
				
				float distanceTo = mc.player.distanceTo(entity);
				
				if (distanceTo <= 5 && entity.isAlive() == true) {
					
					getRotationToEntity(entity);

					mc.player.setYaw(yaw);
					mc.player.setPitch(pitch);
				}
			}
			else if (entity instanceof PassiveEntity & entity != mc.player && passiveMobs.isEnabled()) {
				
				float distanceTo = mc.player.distanceTo(entity);
				
				if (distanceTo <= 5 && entity.isAlive() == true) {
					
					getRotationToEntity(entity);

					mc.player.setYaw(yaw);
					mc.player.setPitch(pitch);
				}
			}
		}
	}

	private static void getRotationToEntity(Entity entity) {
		MinecraftClient mc = MinecraftClient.getInstance();

		var playerPos = new Vec3d(mc.player.prevX, mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()),
				mc.player.getZ());
		var diffX = entity.getX() - playerPos.x;
		var diffY = (entity.getBoundingBox().getCenter().y - playerPos.y);
		var diffZ = entity.getZ() - playerPos.z;
		var diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

	}
}
