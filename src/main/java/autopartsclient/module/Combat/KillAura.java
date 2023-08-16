package autopartsclient.module.Combat;

import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.ModeSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import com.google.common.collect.Streams;

public class KillAura extends Mod {

	public ModeSetting Mode = new ModeSetting("Mode", "Single", "Multi", "Single");
	public ModeSetting Priority = new ModeSetting("Priority", "Closest", "Player", "Closest");
	public BooleanSetting AllEntitys = new BooleanSetting("All", true);
	public BooleanSetting switchItem = new BooleanSetting("Switch", true);

	public KillAura() {
		super("KillAura", "AutoAttacks", Category.COMBAT);
		this.setKey(GLFW.GLFW_KEY_R);
		addSetting(Mode, Priority, AllEntitys, switchItem);
	}

	@Override
	public void onTick() {
		if (mc.player == null || mc.world == null)
			return;
		if (mc.player.getAttackCooldownProgress(0) < 1)
			return;

		try {
			List<Entity> filtered;
			if (!AllEntitys.isEnabled()) {
				filtered = Streams.stream(mc.world.getEntities())
						.filter(e -> e instanceof PlayerEntity && mc.player.distanceTo(e) <= 10 && e != mc.player)
						.collect(Collectors.toList());
			} else {
				filtered = Streams.stream(mc.world.getEntities())
						.filter(e -> e instanceof Entity && mc.player.distanceTo(e) <= 10 && e != mc.player)
						.collect(Collectors.toList());
			}

			for (Entity entity : filtered) {
				if (entity != null) {
					// Don't attack dead/non living entities, ones we can't attack, and end crystals
					if (entity.isLiving() && entity.isAttackable() && !(entity.getClass() == EndCrystalEntity.class)) {
						//switch items
						if (switchItem.isEnabled()) {
							for (int i = 0; i < 9; i++) {
								if (mc.player.getInventory().getStack(i).getItem() instanceof SwordItem)
									mc.player.getInventory().selectedSlot = i;
							}
						}
						
						//attack && soon to be priority
						if(Criticals.isToggled) {
						    Criticals.sendCritPackets();
						}
						mc.interactionManager.attackEntity(mc.player, entity);
						mc.player.swingHand(Hand.MAIN_HAND);
						
						//Multi or single
						if (Mode.getMode() == "Single") {
							break;
						}
					}
				}
			}

		} catch (Exception ignored) {
		}
	}
}
