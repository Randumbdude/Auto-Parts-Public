package autopartsclient.module.Combat;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class CrystalAura extends Mod{
	public CrystalAura() {
		super("CrystalAura", "Blow", Category.COMBAT);
	}
	
	@Override
	public void onEnable() {
		ChatUtils.message("CrystalAura Enabled");
	}
	@Override
	public void onDisable() {
		ChatUtils.message("CrystalAura Disabled");
	}
	
	@Override
	public void onTick() {
		for (Entity e : mc.world.getEntities()) {
			if(e == mc.world.getEntityById(200)){
				mc.player.attack(e);
			}
		}
	}
}
