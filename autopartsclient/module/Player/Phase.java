package autopartsclient.module.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autopartsclient.module.Mod;
import autopartsclient.util.Player.PlayerUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Phase extends Mod{
	public Logger logger = LogManager.getLogger(Phase.class);
	
	public static boolean isToggled;
	
	public Phase() {
		super("PhaseTP", "", Category.PLAYER);
	}
	
	@Override
	public void onEnable() {
		isToggled = true;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		isToggled =false;
		super.onDisable();
	}
	
	@Override
	public void onTick() {
		if (mc.player.horizontalCollision && mc.options.sneakKey.isPressed()) {
			Vec3i v31 = mc.player.getMovementDirection().getVector();
	        Vec3d v3 = new Vec3d(v31.getX(), 0, v31.getZ());
	        for (double o = 2; o < 100; o++) {
	            Vec3d coff = v3.multiply(o);
	            BlockPos cpos = mc.player.getBlockPos().add(new Vec3i(coff.x, coff.y, coff.z));
	            BlockState bs1 = mc.world.getBlockState(cpos);
	            BlockState bs2 = mc.world.getBlockState(cpos.up());
	            if (!bs1.getMaterial().blocksMovement() && !bs2.getMaterial().blocksMovement() && bs1.getBlock() != Blocks.LAVA && bs2.getBlock() != Blocks.LAVA) {
	                mc.player.updatePosition(cpos.getX() + 0.5, cpos.getY(), cpos.getZ() + 0.5);
	                PlayerUtils.blinkToPos(new Vec3d(cpos.getX(), cpos.getY(), cpos.getZ()), new BlockPos(cpos.getX() + 0.5, cpos.getY(), cpos.getZ() + 0.5), 5, new double[] {1, 2, 42});
	                break;
	            }
	        }
		}
		super.onTick();
	}
}
