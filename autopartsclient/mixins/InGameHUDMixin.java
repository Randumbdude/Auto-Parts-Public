package autopartsclient.mixins;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.ui.HUD;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

@Mixin(InGameHud.class)
public class InGameHUDMixin {		
	@Inject(method = "render", at = @At("RETURN"), cancellable = true)
	public void renderHUD(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		HUD.Render(matrices, tickDelta);
	}

	@Inject(at = @At("TAIL"), method = "render")
	public void renderFps(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		MinecraftClient mc = MinecraftClient.getInstance();
		TextRenderer renderer = mc.textRenderer;
		
		String fps = ((MinecraftClientAccessor) mc).getCurrentFps() + " Fps";
		
		renderer.drawWithShadow(matrices, fps, 100, 5, Color.WHITE.getRGB());
	}
	
	@Inject(at = @At("TAIL"), method = "render")
	public void renderCords(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		MinecraftClient mc = MinecraftClient.getInstance();
		TextRenderer renderer = mc.textRenderer;
		
		int pX = (int)mc.player.getPos().x;
		int pY = (int)mc.player.getPos().y;
		int pZ = (int)mc.player.getPos().z;
		
		renderer.drawWithShadow(matrices, "X: " + pX + " Y: " + pY + " Z: " + pZ, 150, 5, Color.WHITE.getRGB());
	}
}
