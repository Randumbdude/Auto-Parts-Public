package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Movement.PacketFly;
import autopartsclient.module.Movement.Phase;
import autopartsclient.module.Player.HClip;
import autopartsclient.module.Render.Freecam;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin {
    MinecraftClient MC = MinecraftClient.getInstance();

    // Makes mouse input rotate the FreeCamera.
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void onChangeLookDirection(double x, double y, CallbackInfo ci) {
	if (Freecam.isToggled && this.equals(MC.player) && !FreecamUtil.isPlayerControlEnabled()) {
	    FreecamUtil.getFreeCamera().changeLookDirection(x, y);
	    ci.cancel();
	}
    }

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    public void onPushAwayFrom(Entity entity, CallbackInfo ci) {
	if (entity == MC.player) {
	    if (HClip.isToggled) {
		ci.cancel();
	    }
	    if(PacketFly.isToggled) {
		ci.cancel();
	    }
	}

	if (Phase.isToggled) {
	    if (entity == MC.player) {
		ci.cancel();
	    }
	}
	
	if(PacketFly.isToggled) {
	    ci.cancel();
	}
    }
}
