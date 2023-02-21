package autopartsclient.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.include.com.google.common.base.Objects;

import autopartsclient.Client;
import autopartsclient.module.Player.NoClickCooldown;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	
    @Shadow
    private int itemUseCooldown;
    
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
	
    @Redirect(method = "handleInputEvents", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I"))
    public int replaceItemUseCooldown(MinecraftClient minecraftClient) {
        if (NoClickCooldown.isToggled) {
            return 0;
        } else {
            return this.itemUseCooldown;
        }
    }
}
