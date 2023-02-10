package autopartsclient.mixins;

import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Render.Freecam;
import autopartsclient.util.FreecamUtils.FreecamUtil;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    // Disables freecam if the player disconnects.
    @Inject(method = "handleDisconnection", at = @At("HEAD"))
    private void onHandleDisconnection(CallbackInfo ci) {
        if (Freecam.isToggled == true) {
            FreecamUtil.toggle();
        }
        FreecamUtil.clearTripods();
    }
}
