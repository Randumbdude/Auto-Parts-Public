/*
package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Render.HitIndicators;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {
    
    //"onDamage(Lnet/minecraft/entity/damage/DamageSource;)V"
    
    @Inject(method = "onDamage(Lnet/minecraft/entity/damage/DamageSource;FFLorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V", at = @At("HEAD"))
    private void onDamageIntercepted(DamageSource damageSource, float originalHealth, float damage, CallbackInfo ci) {
	//System.out.println(damageSource.getName());
	HitIndicators.updateAttacker(damageSource);
    }
    
}
*/