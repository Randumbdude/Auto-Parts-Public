package autopartsclient.module.Combat;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class KillAura extends Mod{
	public BooleanSetting players = new BooleanSetting("players", true);
	public BooleanSetting passiveMobs = new BooleanSetting("Passive", false);
	public BooleanSetting hostileMobs = new BooleanSetting("Hostile", true);
	
	public KillAura() {
		super("KillAura", "AutoAttacks", Category.COMBAT);
		addSetting(hostileMobs,players,passiveMobs);
	}
	
	@Override
	public void onTick() {
		for (Entity e : mc.world.getEntities()) {
			if(mc.player.getAttackCooldownProgress(0) < 1) return;
			
			if(e instanceof PlayerEntity && e != mc.player && e.distanceTo(mc.player) <= 7 && players.isEnabled() && e.isAlive() == true){
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
			if(e instanceof HostileEntity && e != mc.player && e.distanceTo(mc.player) <= 7 && hostileMobs.isEnabled() && e.isAlive() == true){
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
			if(e instanceof PassiveEntity && e != mc.player && e.distanceTo(mc.player) <= 7 && passiveMobs.isEnabled() && e.isAlive() == true){
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
		}
	}
}
