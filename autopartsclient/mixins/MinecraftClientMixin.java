package autopartsclient.mixins;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.Client;
import autopartsclient.module.Render.Freecam;
import autopartsclient.util.FontUtils;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	MinecraftClient mc = MinecraftClient.getInstance();
	
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void onTick(CallbackInfo ci) {		
		Client.INSTANCE.onTick();
		
		/*
		//bugs
		if (Freecam.isToggled) {
            if (mc.player != null && mc.player.input instanceof KeyboardInput && !FreecamUtil.isPlayerControlEnabled()) {
                Input input = new Input();
                input.sneaking = mc.player.input.sneaking; // Makes player continue to sneak after freecam is enabled.
                mc.player.input = input;
            }
            mc.gameRenderer.setRenderHand(true);

            if (FreecamUtil.disableNextTick()) {
                FreecamUtil.toggle();
                FreecamUtil.setDisableNextTick(false);
            }
        }
        */
	}
}
