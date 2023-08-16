package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.block.AbstractBlock.AbstractBlockState;

@Mixin(AbstractBlockState.class)
public class AbstractBlockMixin {
	@Inject(method = "getOpacity", at = @At("HEAD"))
	public int blockOpacity() {
		return 1;
	}
}
