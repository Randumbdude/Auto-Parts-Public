package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Movement.BoatClip;
import autopartsclient.module.Movement.PacketFly;
import autopartsclient.module.Movement.Phase;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayMixin {
	@Inject(at = { @At("HEAD") }, method = {
			"renderInWallOverlay(Lnet/minecraft/client/texture/Sprite;Lnet/minecraft/client/util/math/MatrixStack;)V" }, cancellable = true)
	private static void RenderInWallOverlay(Sprite sprite, MatrixStack matrices, CallbackInfo ci) {
		if(BoatClip.isToggled == true) {
			ci.cancel();
		}
		if(Phase.isToggled) {
		    ci.cancel();
		}
		if(PacketFly.isToggled) {
		    ci.cancel();
		}
	}
}
//renderInWallOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V