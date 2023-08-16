package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Combat.NoShieldCool;
import autopartsclient.module.Misc.TestModule;
import autopartsclient.module.Movement.PacketFly;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    MinecraftClient mc = MinecraftClient.getInstance();
    
    @Inject(method = "collideWithEntity", at = @At("HEAD"), cancellable = true)
    private void onCollideWithEntity(Entity entity, CallbackInfo ci) {
        if (PacketFly.isToggled) ci.cancel();
    }
    
    @Inject(method = "updatePose", at = @At("HEAD"), cancellable = true)
    private void onUpdatePose(CallbackInfo ci) {
        if (PacketFly.isToggled) {
            mc.player.setPose(EntityPose.STANDING);
            ci.cancel();
        }
    }
    
    @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    private void onDisableShield(boolean sprinting, CallbackInfo ci) {
	if(NoShieldCool.isToggled) {
	    ci.cancel();
	}
    }
}
