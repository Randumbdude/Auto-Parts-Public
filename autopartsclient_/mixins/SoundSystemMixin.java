package autopartsclient.mixins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Exploit.ThunderHack;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundEvents;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {
	public Logger logger = LogManager.getLogger(SoundSystemMixin.class);
	
    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    private void onPlay(SoundInstance soundInstance, CallbackInfo info) {        
        if(soundInstance.getId().equals(SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT.getId()) || soundInstance.getId().equals(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER.getId())){     
        	if(ThunderHack.isToggled)
        	ChatUtils.message("Thunder Located [X: " + soundInstance.getX() + " Y: " + soundInstance.getY() + " Z: " + soundInstance.getZ() + "]");
        }
    }
}
