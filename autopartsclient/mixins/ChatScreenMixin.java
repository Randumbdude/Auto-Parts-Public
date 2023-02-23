package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.cmd.CommandHandler;
import net.minecraft.client.gui.screen.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {	
	@Inject(at = @At("HEAD"), method = "sendMessage(Ljava/lang/String;Z)Z", cancellable = true)
	public void onSendMessage(String message, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
		if (message.startsWith(".")) {
			CommandHandler.handleCommand(message);
			cir.setReturnValue(true);
		}
	}
}