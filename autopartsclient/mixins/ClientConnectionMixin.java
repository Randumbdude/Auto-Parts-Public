package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Movement.AntiRubberBand;
import autopartsclient.module.Render.Freecam;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.Channel;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
	@Shadow private Channel channel;
	
	Freecam fc = new Freecam();

    // Disables freecam if the player disconnects.
    @Inject(method = "handleDisconnection", at = @At("HEAD"))
    private void onHandleDisconnection(CallbackInfo ci) {
        if (Freecam.isToggled == true) {
            FreecamUtil.toggle();
            fc.toggle();
        }
        FreecamUtil.clearTripods();
    }
    
    
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callback) {
		if (this.channel.isOpen() && packet != null) {
			if(AntiRubberBand.isToggled) {
				callback.cancel();
			}
		}
	}
}
