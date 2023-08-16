package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.module.Player.NoEnderLook;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(EndermanEntity.class)
public class EnderManMixin {

    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    void onEnderManLook(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
	if (NoEnderLook.isToggled) {
	    cir.setReturnValue(false);
	}
    }
}
