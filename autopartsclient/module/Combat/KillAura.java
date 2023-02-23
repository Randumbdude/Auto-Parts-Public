package autopartsclient.module.Combat;

import autopartsclient.module.Mod;
import autopartsclient.module.Player.Timer;
import autopartsclient.module.settings.BooleanSetting;
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
			if(Timer.isToggled) {
				if(mc.player.getAttackCooldownProgress(0) < 1.5) return;
			}
			if(mc.player.getAttackCooldownProgress(0) < 1) return;
			
			if(e instanceof PlayerEntity && e != mc.player && e.distanceTo(mc.player) <= 10 && players.isEnabled() && e.isAlive() == true){
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
			if(e instanceof HostileEntity && e != mc.player && e.distanceTo(mc.player) <= 10 && hostileMobs.isEnabled() && e.isAlive() == true){
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
			if(e instanceof PassiveEntity && e != mc.player && e.distanceTo(mc.player) <= 10 && passiveMobs.isEnabled() && e.isAlive() == true){
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
		}
	}
}
