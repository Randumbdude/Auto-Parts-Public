package autopartsclient.mixins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import autopartsclient.module.Player.AntiLev;
import autopartsclient.module.Player.NoSlow;
import autopartsclient.module.Player.Phase;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
	public Logger logger = LogManager.getLogger(ClientPlayerEntityMixin.class);

	MinecraftClient mc = MinecraftClient.getInstance();

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
		// TODO Auto-generated constructor stub
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0), method = "tickMovement()V")
	private boolean IsUsingItem(ClientPlayerEntity player) {
		if (NoSlow.isToggled)
			return false;

		return player.isUsingItem();
	}

	@Override
	public boolean hasStatusEffect(StatusEffect effect) {
		if (effect == StatusEffects.LEVITATION && AntiLev.isToggled)
			return false;

		return super.hasStatusEffect(effect);
	}

	@Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
	public void pushOut(double x, double y, CallbackInfo ci) {
		if (Phase.isToggled) {
			ci.cancel();
		}
	}

	
	
}
