package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Render.WallHack;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

@Mixin(BlockModelRenderer.class)
public class BlockModelRendererMixin {
    
    @Unique
    private final ThreadLocal<Integer> alphas = new ThreadLocal<>();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrices,
	    VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, CallbackInfo info) {
	
	if (WallHack.isToggled) {
	    int alpha = WallHack.alpha;

	    if (alpha == 0)
		info.cancel();
	    else
		alphas.set(alpha);
	}
	
    }

    
    @Inject(method = "renderQuad", at = @At("TAIL"))
    private void onRenderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer,
	    MatrixStack.Entry matrixEntry, BakedQuad quad, float brightness0, float brightness1, float brightness2,
	    float brightness3, int light0, int light1, int light2, int light3, int overlay, CallbackInfo ci) {
	if (WallHack.isToggled) {
	    int alpha = WallHack.alpha;
	    if (alpha != -1)
		rewriteBuffer(vertexConsumer, alpha);
	}
    }

    @Unique
    private void rewriteBuffer(VertexConsumer vertexConsumer, int alpha) {
	if (vertexConsumer instanceof BufferBuilder bufferBuilder) {
	    BufferBuilderAccessor bufferBuilderAccessor = ((BufferBuilderAccessor) bufferBuilder);

	    int prevOffset = bufferBuilderAccessor.getElementOffset();

	    if (prevOffset > 0) {
		int i = bufferBuilderAccessor.getVertexFormat().getVertexSizeByte();

		for (int l = 1; l <= 4; l++) {
		    bufferBuilderAccessor.setElementOffset(prevOffset - i * l);
		    bufferBuilder.putByte(15, (byte) (alpha));
		}

		bufferBuilderAccessor.setElementOffset(prevOffset);
	    }
	}
    }
}
