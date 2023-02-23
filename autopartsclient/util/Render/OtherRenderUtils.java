package autopartsclient.util.Render;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferBuilder.BuiltBuffer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class OtherRenderUtils {
	
	private static final Box DEFAULT_BOX = new Box(0, 0, 0, 1, 1, 1);
	private static MinecraftClient mc = MinecraftClient.getInstance();
	
	public static Vec3d getEyesPos()
	{
		ClientPlayerEntity player = mc.player;
		
		return new Vec3d(player.getX(),
			player.getY() + player.getEyeHeight(player.getPose()),
			player.getZ());
	}
	//Filled Box------------------------------------
	
	public static void drawSolidBox(MatrixStack matrixStack)
	{
		drawSolidBox(DEFAULT_BOX, matrixStack);
	}
	
	public static void drawSolidBox(Box bb, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionProgram);
		
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
			VertexFormats.POSITION);
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
			.next();
		tessellator.draw();
	}
	
	public static void drawSolidBox(Box bb, VertexBuffer vertexBuffer)
	{
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
			VertexFormats.POSITION);
		drawSolidBox(bb, bufferBuilder);
		BuiltBuffer buffer = bufferBuilder.end();
		
		vertexBuffer.bind();
		vertexBuffer.upload(buffer);
		VertexBuffer.unbind();
	}
	
	public static void drawSolidBox(Box bb, BufferBuilder bufferBuilder)
	{
		bufferBuilder.vertex(bb.minX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.minY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.minZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.minX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.minZ).next();
	}
	
	//Outline box-----------------------------------
	public static void drawOutlinedBox(MatrixStack matrixStack)
	{
		drawOutlinedBox(DEFAULT_BOX, matrixStack);
	}
	
	public static void drawOutlinedBox(Box bb, MatrixStack matrixStack)
	{
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionProgram);
		
		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
			VertexFormats.POSITION);
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
			.next();
		bufferBuilder
			.vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
			.next();
		tessellator.draw();
	}
	
	public static void drawOutlinedBox(Box bb, VertexBuffer vertexBuffer)
	{
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		
		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
			VertexFormats.POSITION);
		drawOutlinedBox(bb, bufferBuilder);
		BuiltBuffer buffer = bufferBuilder.end();
		
		vertexBuffer.bind();
		vertexBuffer.upload(buffer);
		VertexBuffer.unbind();
	}
	
	public static void drawOutlinedBox(Box bb, BufferBuilder bufferBuilder)
	{
		bufferBuilder.vertex(bb.minX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.minY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.minY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.minY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.minZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.minZ).next();
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.maxX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.maxZ).next();
		
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.maxZ).next();
		bufferBuilder.vertex(bb.minX, bb.maxY, bb.minZ).next();
	}
}
