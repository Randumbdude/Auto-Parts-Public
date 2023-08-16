package autopartsclient.mixins;

import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Player.InvMove;

@Mixin(Screen.class)
public class ScreenMixin {
    /*
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V"), method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V", cancellable = true)
    private void onDrawBackground(CallbackInfo info) {
	if (InvMove.isToggled) {
	    info.cancel();
	}
    }
    */
}