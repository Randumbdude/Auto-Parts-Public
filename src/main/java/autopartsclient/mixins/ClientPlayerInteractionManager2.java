package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.util.Player.BreakProgressTracker;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.MathHelper;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManager2 implements BreakProgressTracker{
    @Unique
    private float lastBreakingProgress;

    @Shadow
    @Final
    private float currentBreakingProgress;

    @Override
    public float getBreakProgress(float tickDelta) {
	if (this.currentBreakingProgress == 0) {
	    return 0;
	}

	return MathHelper.lerp(tickDelta, this.lastBreakingProgress, this.currentBreakingProgress);
    }

    @Inject(method = "updateBlockBreakingProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;calcBlockBreakingDelta(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"))
    public void updateLastBreakingProgress(CallbackInfoReturnable<Boolean> ci) {
	this.lastBreakingProgress = this.currentBreakingProgress;
    }
}
