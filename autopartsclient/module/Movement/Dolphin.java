
package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class Dolphin extends Mod{	
	
	public Dolphin() {
        super("Dolphin", "Swim Fasttt", Category.MOVEMENT);
    }
	@Override
	public void onEnable() {
		ChatUtils.message("Dolphin Enabled");
	}
	
	@Override
	public void onTick() {
		StatusEffectInstance dolphinGrace = new StatusEffectInstance(StatusEffect.byRawId(30), 999999999);
		mc.player.setStatusEffect(dolphinGrace, mc.player);
	}

    @Override
    public void onDisable() {
    	ChatUtils.message("Dolphin Disabled");
    	//Potions.NIGHT_VISION.getEffects().forEach(statusEffectInstance -> mc.player.removeStatusEffect(statusEffectInstance.getEffectType()));
    	mc.player.removeStatusEffect(StatusEffect.byRawId(30));
		super.onDisable();
    }
}