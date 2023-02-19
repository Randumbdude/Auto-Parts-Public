
package autopartsclient.module.Render;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potions;

public class FullBright extends Mod{	
	
	private double oldGamma;
	
	public FullBright() {
        super("FullBright", "Maximum Brightness", Category.RENDER);
    }
	@Override
	public void onEnable() {
		oldGamma = mc.options.getGamma().getValue();
	}
	
	@Override
	public void onTick() {
		mc.options.getGamma().setValue(1000.0);
		StatusEffectInstance effect = new StatusEffectInstance(StatusEffect.byRawId(16), 999999999);
		mc.player.setStatusEffect(effect, mc.player);
	}

    @Override
    public void onDisable() {
		mc.options.getGamma().setValue(oldGamma);
    	Potions.NIGHT_VISION.getEffects().forEach(statusEffectInstance -> mc.player.removeStatusEffect(statusEffectInstance.getEffectType()));
		super.onDisable();
    }
}