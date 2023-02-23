package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.module.Player.Reach;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	MinecraftClient MC = MinecraftClient.getInstance();

	// Prevents attacking self.
	@Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
	private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
		if (target.equals(MC.player)) {
			ci.cancel();
		}
	}

	@Inject(at = { @At("HEAD") }, method = { "getReachDistance()F" }, cancellable = true)
	private void onGetReachDistance(CallbackInfoReturnable<Float> ci) {
		if (Reach.isToggled = false)
			return;

		ci.setReturnValue(Reach.reachlength);
	}

	@Inject(at = { @At("HEAD") }, method = { "hasExtendedReach()Z" }, cancellable = true)
	private void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
		if (Reach.isToggled = false)
			return;

		cir.setReturnValue(true);
	}
}
