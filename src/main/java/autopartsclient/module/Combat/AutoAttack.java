package autopartsclient.module.Combat;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoAttack extends Mod{
    public BooleanSetting targetAnimals = new BooleanSetting("Target Animal", true);
    public BooleanSetting targetMobs = new BooleanSetting("Target Mob", true);
    public BooleanSetting targetPlayers = new BooleanSetting("Target Player", true);
    
    public AutoAttack() {
	super("AutoAttack", "Yea", Category.COMBAT);
	
	this.addSetting(targetAnimals, targetMobs, targetPlayers);
    }
    
    @Override
    public void onTick() {
	HitResult ray = mc.crosshairTarget;
	
	// If the target is an Entity, attack it.
	if(ray != null && ray.getType()==HitResult.Type.ENTITY) {
		EntityHitResult entityResult = (EntityHitResult) ray;
		Entity ent = entityResult.getEntity();
		if(ent instanceof AnimalEntity && !this.targetAnimals.isEnabled()) return;
		if(ent instanceof PlayerEntity && !this.targetPlayers.isEnabled()) return;
		if(ent instanceof Monster && !this.targetMobs.isEnabled()) return;
		
		if(mc.player.getAttackCooldownProgress(0) == 1) {
			mc.interactionManager.attackEntity(mc.player, entityResult.getEntity());
			mc.player.swingHand(Hand.MAIN_HAND);
		}
	}
    }
}
