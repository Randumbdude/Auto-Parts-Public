package autopartsclient.mixins;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.chat.ChatEncryption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;

@Mixin(ChatHud.class)
public class ChatHudMixin extends DrawableHelper {
    @Shadow
    private List<ChatHudLine.Visible> visibleMessages;
    @Shadow
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", cancellable = true)
    private void onAddMessage(Text message, @Nullable MessageSignatureData signature,
	    @Nullable MessageIndicator indicator, CallbackInfo ci) {
	
	ChatEncryption.handleSentMessage(message.getString());

	shadow$logChatMessage(message, indicator);
	shadow$addMessage(message, signature, client.inGameHud.getTicks(), indicator, false);
	ci.cancel();
    }

    @Shadow
    private void shadow$logChatMessage(Text message, @Nullable MessageIndicator indicator) {

    }

    @Shadow
    private void shadow$addMessage(Text message, @Nullable MessageSignatureData signature, int ticks,
	    @Nullable MessageIndicator indicator, boolean refresh) {

    }
}
