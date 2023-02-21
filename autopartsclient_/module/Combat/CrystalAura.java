package autopartsclient.module.Combat;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Hand;

public class CrystalAura extends Mod{
	public CrystalAura() {
		super("CrystalAura", "Blow", Category.COMBAT);
	}
	
	@Override
	public void onTick() {
		for (Entity e : mc.world.getEntities()) {
			if(e instanceof EndCrystalEntity && e.distanceTo(mc.player) <= 7){
				/*        saver
				if(e.getY() <= mc.player.getY() + 1.5) {
					return;
				}
				*/
				mc.interactionManager.attackEntity(mc.player, e);
				mc.player.swingHand(Hand.MAIN_HAND);
			}
		}
	}
}
