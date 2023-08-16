package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.module.Player.NoSlow;
import autopartsclient.module.Movement.PacketFly;
import autopartsclient.module.Player.HClip;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

@Mixin(Block.class)
public abstract class BlockMixin implements ItemConvertible
{
	
	@Inject(at = {@At("HEAD")},
		method = {"getVelocityMultiplier()F"},
		cancellable = true)
	private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir)
	{
		if(!NoSlow.isToggled)
			return;
		
		if(cir.getReturnValueF() < 1)
			cir.setReturnValue(1F);
	}
	
	
    @Inject(method = "pushEntitiesUpBeforeBlockChange", at = @At("HEAD"), cancellable = true)
    private static void pushEntitiesUpBeforeBlockChange(BlockState from, BlockState to, WorldAccess worldAccess, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
    	if(HClip.isToggled) {
    		cir.cancel();
    	}
    	if(PacketFly.isToggled) {
    	    cir.cancel();
    	}
    }
}