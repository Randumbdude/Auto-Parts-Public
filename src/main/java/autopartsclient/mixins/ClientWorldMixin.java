package autopartsclient.mixins;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Misc.Warn;
import autopartsclient.module.Movement.PacketFly;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.FreecamUtils.FreeCamera;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {

    private MinecraftClient mc = MinecraftClient.getInstance();

    @Shadow
    @Nullable
    public abstract Entity getEntityById(int id);

    @Inject(method = "addEntityPrivate", at = @At("TAIL"))
    private void onAddEntityPrivate(int id, Entity entity, CallbackInfo info) {
	if (entity == null || entity == FreecamUtil.getFreeCamera())
	    return;
	if (Warn.isToggled) {
	    if (entity != mc.player) {
		if (entity instanceof PlayerEntity) {
		    ChatUtils.message(entity.getEntityName() + " Entered Visual Range(" + (int) entity.getX() + ","
			    + (int) entity.getY() + ", " + (int) entity.getZ() + ")");
		}
	    }
	}
    }

    @Inject(method = "removeEntity", at = @At("HEAD"))
    private void onRemoveEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo info) {

	Entity entity = getEntityById(entityId);
	if (entity == null || entity == FreecamUtil.getFreeCamera())
	    return;

	if (Warn.isToggled) {
	    if (entity instanceof PlayerEntity) {
		ChatUtils.message(entity.getEntityName() + " Left Visual Range(" + (int) entity.getX() + ","
			+ (int) entity.getY() + ", " + (int) entity.getZ() + ")");
	    }

	}
    }

    @Inject(method = "processPendingUpdate", at = @At("HEAD"), cancellable = true)
    private void onPendingUpdate(BlockPos pos, BlockState state, Vec3d playerPos, CallbackInfo ci) {
	if (PacketFly.isToggled) {
	    ci.cancel();
	}
    }
}
