package autopartsclient.module.Render;

import com.mojang.blaze3d.systems.RenderSystem;

import autopartsclient.module.Mod;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.hit.HitResult.Type;

public class BlockOutline extends Mod {
    public BlockOutline() {
	super("BlockOutline", "", Category.RENDER);
	this.showInArray = false;
    }

    @Override
    public void render(MatrixStack matrixStack, float partialTicks) {
	HitResult target = mc.crosshairTarget;
	if (target != null) {
	    if (target.getType() == Type.BLOCK) {
		RenderSystem.setShaderColor(0, 0, 0, 1);
		BlockPos pos = (BlockPos) ((BlockHitResult) target).getBlockPos();
		RenderUtils.drawBlockBox(matrixStack, new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
			new ColorUtil(0, 0, 0));
		RenderSystem.setShaderColor(1, 1, 1, 1);
	    }
	}
    }
}
