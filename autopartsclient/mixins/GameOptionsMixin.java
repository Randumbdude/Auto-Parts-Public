package autopartsclient.mixins;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Render.Freecam;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    // Prevents switching to third person in freecam.
    @Inject(method = "setPerspective", at = @At("HEAD"), cancellable = true)
    private void onSetPerspective(CallbackInfo ci) {
        if (Freecam.isToggled) {
            ci.cancel();
        }
    }
}
