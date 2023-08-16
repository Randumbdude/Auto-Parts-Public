package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Movement.PacketFly;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.PendingUpdateManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Mixin(PendingUpdateManager.class)
public class PendingUpdateManagerMixin {
    @Inject(method = "processPendingUpdates", at = @At("HEAD"), cancellable = true)
    private void onPendingUpdates(int maxProcessableSequence, ClientWorld world, CallbackInfo ci) {
	if (PacketFly.isToggled) {
	    ci.cancel();
	}
    }
}
