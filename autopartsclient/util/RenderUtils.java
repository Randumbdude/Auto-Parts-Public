package autopartsclient.util;

import org.apache.logging.log4j.LogManager;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {

	private static MinecraftClient mc = MinecraftClient.getInstance();

	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(RenderUtils.class);

	public static void drawBlockBox(MatrixStack matrices, Vec3d pos, ColorUtil color) {
		matrices.push();

		Matrix4f matrix4 = matrices.peek().getPositionMatrix();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		Vec3d start = transformVec3d(pos);
		Vec3d end = transformVec3d(pos);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x + 1, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x + 1, (float) end.y + 1, (float) end.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
//---------------------------------------------------------------------------------------------------------------------
		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y + 1, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y + 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y + 1, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y, (float) end.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x + 1, (float) end.y, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 1, (float) start.y+1, (float) start.z + 1)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x + 1, (float) end.y+1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();

		tessellator.draw();

		matrices.pop();
	}

	public static void draw3Dbox(MatrixStack matrices, Vec3d pos1, Vec3d pos2, ColorUtil color) {
		matrices.push();

		Matrix4f matrix4 = matrices.peek().getPositionMatrix();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		Vec3d start = transformVec3d(pos1);
		Vec3d end = transformVec3d(pos2);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

//set of parallel verticle lines--------------------------------------------------------------------------------------------------------------
		bufferBuilder.vertex(matrix4, (float) start.x - 0.5f, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x - 0.5f, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y - 1, (float) start.z + 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z + 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

//set of parallel verticle lines----------------------------------------------------------------------------------------------------------------
		bufferBuilder.vertex(matrix4, (float) start.x + 0.5f, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x + 0.5f, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y - 1, (float) start.z - 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z - 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

//set of parallel horizontal lines------------------------------------------------------------------------------------------------------------------
		bufferBuilder.vertex(matrix4, (float) start.x - 0.5f, (float) start.y + 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z + 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 0.5f, (float) start.y + 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z - 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x - 0.5f, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y - 1, (float) end.z + 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 0.5f, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y - 1, (float) end.z - 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
//set of parallel horizontal lines------------------------------------------------------------------------------------------------------------------
		bufferBuilder.vertex(matrix4, (float) start.x - 0.5f, (float) start.y + 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z - 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 0.5f, (float) start.y + 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z + 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x - 0.5f, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y - 1, (float) end.z - 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) start.x + 0.5f, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y - 1, (float) end.z + 0.5f)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();

		tessellator.draw();

		matrices.pop();
		// logger.info("X: " + pos2.x + " Y: " + pos2.y + " Z: " + pos2.z);
	}

	public static void drawEntityLine(MatrixStack matrices, Vec3d pos1, Vec3d pos2, ColorUtil color) {
		matrices.push();

		Matrix4f matrix4 = matrices.peek().getPositionMatrix();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		Vec3d start = transformVec3d(pos1);
		Vec3d end = transformVec3d(pos2);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

		bufferBuilder.vertex(matrix4, (float) start.x, (float) start.y - 1, (float) start.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y + 1, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();

		tessellator.draw();

		matrices.pop();
		// logger.info("X: " + pos2.x + " Y: " + pos2.y + " Z: " + pos2.z);
	}

	public static void drawLine3D(MatrixStack matrices, Vec3d pos1, Vec3d pos2, ColorUtil color) {
		matrices.push();

		Matrix4f matrix4 = matrices.peek().getPositionMatrix();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		Vec3d end = transformVec3d(pos2);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

		bufferBuilder.vertex(matrix4, (float) pos1.x, (float) pos1.y, (float) pos1.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) end.x, (float) end.y, (float) end.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();

		RenderSystem.disableDepthTest();
		RenderSystem.disableBlend();

		tessellator.draw();

		matrices.pop();
	}
	
	public static void drawWaypoint(MatrixStack matrices, Vec3d pos1, ColorUtil color) {
		matrices.push();

		Matrix4f matrix4 = matrices.peek().getPositionMatrix();

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();

		Vec3d pos2 = transformVec3d(pos1);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

		bufferBuilder.vertex(matrix4, (float) pos2.x, (float) pos2.y - 64, (float) pos2.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		bufferBuilder.vertex(matrix4, (float) pos2.x, (float) pos2.y + 300, (float) pos2.z)
				.color(color.getRedFloat(), color.getGreenFloat(), color.getBlueFloat(), 255).next();
		

		tessellator.draw();

		matrices.pop();
	}

	public static Vec3d getClientLookVec() {
		ClientPlayerEntity player = mc.player;
		float f = 0.017453292F;
		float pi = (float) Math.PI;

		float f1 = MathHelper.cos(-player.getYaw() * f - pi);
		float f2 = MathHelper.sin(-player.getYaw() * f - pi);
		float f3 = -MathHelper.cos(-player.getPitch() * f);
		float f4 = MathHelper.sin(-player.getPitch() * f);

		return new Vec3d(f2 * f3, f4, f1 * f3);
	}

	private static Vec3d transformVec3d(Vec3d in) {
		Camera camera = mc.gameRenderer.getCamera();
		Vec3d camPos = camera.getPos();
		return in.subtract(camPos);
	}
}