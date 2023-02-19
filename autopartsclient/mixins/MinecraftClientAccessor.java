package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public interface MinecraftClientAccessor {
	@Accessor
	int getCurrentFps();
}