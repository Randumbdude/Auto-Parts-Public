package autopartsclient.module.Misc;

import autopartsclient.module.Mod;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Nuker extends Mod {
    private int radius = 3;

    public Nuker() {
	super("Nuker", "", Category.MISC);
    }

    @Override
    public void onTick() {
	int rad = radius;
	for (int x = -rad; x < rad; x++) {
	    for (int y = rad; y > -rad; y--) {
		for (int z = -rad; z < rad; z++) {
		    BlockPos blockpos = new BlockPos(mc.player.getBlockX() + x, (int) mc.player.getBlockY() + y,
			    (int) mc.player.getBlockZ() + z);
		    Block block = mc.world.getBlockState(blockpos).getBlock();
		    if (block == Blocks.AIR)
			continue;

		    mc.player.networkHandler.sendPacket(
			    new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockpos, Direction.NORTH));
		    mc.player.networkHandler.sendPacket(
			    new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, blockpos, Direction.NORTH));
		}
	    }
	}
    }

    @Override
    public void render(MatrixStack matrixStack, float partialTicks) {
	int rad = radius;
	for (int x = -rad; x < rad; x++) {
	    for (int y = rad; y > -rad; y--) {
		for (int z = -rad; z < rad; z++) {
		    BlockPos blockpos = new BlockPos(mc.player.getBlockX() + x, mc.player.getBlockY() + y,
			    mc.player.getBlockZ() + z);
		    Block block = mc.world.getBlockState(blockpos).getBlock();

		    if (block == Blocks.AIR || block == Blocks.WATER || block == Blocks.LAVA)
			continue;

		    Vec3d pos = new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());

		    RenderUtils.drawBlockBox(matrixStack, pos, new ColorUtil(255, 0, 0));
		}
	    }
	}
    }
}
