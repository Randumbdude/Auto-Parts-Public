package autopartsclient.module.Render;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import autopartsclient.module.Mod;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.OtherRenderUtils;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

public class Trajectories extends Mod {
	public Trajectories() {
		super("Trajectories", "", Category.RENDER);
	}

	@Override
	public void render(MatrixStack matrixStack, float partialTicks) {
		matrixStack.push();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		// RenderUtils.applyCameraRotationOnly();

		ArrayList<Vec3d> path = getPath(partialTicks);
		
		Vec3d camPos = RenderUtils.getClientLookVec();
		Vec3d startCam = RenderUtils.transformVec3d(camPos);

		drawLine(matrixStack, path, startCam);

		if (!path.isEmpty()) {
			
			Vec3d end = path.get(path.size() - 1);
			Vec3d endPos = RenderUtils.transformVec3d(end);
			drawEndOfLine(matrixStack, endPos, startCam);
		}

		RenderSystem.setShaderColor(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		matrixStack.pop();
	}

	private void drawLine(MatrixStack matrixStack, ArrayList<Vec3d> path, Vec3d camPos) {
		Matrix4f matrix = matrixStack.peek().getPositionMatrix();
		Tessellator tessellator = RenderSystem.renderThreadTesselator();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionProgram);

		bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);
		float[] colorF = { 255, 0, 255 };
		RenderSystem.setShaderColor(colorF[0], colorF[1], colorF[2], 1F);

		for (Vec3d point : path) {
			point = RenderUtils.transformVec3d(point);
		
			bufferBuilder.vertex(matrix, (float) (point.x), (float) (point.y),
					(float) (point.z)).next();
		//			bufferBuilder.vertex(matrix, (float) (point.x - camPos.x), (float) (point.y - camPos.y), (float) (point.z - camPos.z)).next();
		}
		tessellator.draw();
	}

	private void drawEndOfLine(MatrixStack matrixStack, Vec3d end, Vec3d camPos) {
		double renderX = end.x - camPos.x;
		double renderY = end.y - camPos.y;
		double renderZ = end.z - camPos.z;
		float[] colorF = { 255, 0, 255 };

		matrixStack.push();
		matrixStack.translate(renderX - 0.5, renderY - 0.5, renderZ - 0.5);

		RenderSystem.setShaderColor(colorF[0], colorF[1], colorF[2], 1F);
		//OtherRenderUtils.drawSolidBox(matrixStack);
		RenderUtils.drawBlockBox(matrixStack, end, new ColorUtil(255,0,0));

		RenderSystem.setShaderColor(colorF[0], colorF[1], colorF[2], 1F);
		//OtherRenderUtils.drawOutlinedBox(matrixStack);

		matrixStack.pop();
	}

	private ArrayList<Vec3d> getPath(float partialTicks) {
		ClientPlayerEntity player = mc.player;
		ArrayList<Vec3d> path = new ArrayList<>();

		ItemStack stack = player.getMainHandStack();
		Item item = stack.getItem();

		// check if item is throwable
		if (stack.isEmpty() || !isThrowable(item))
			return path;

		// calculate starting position
		double arrowPosX = player.lastRenderX + (player.getX() - player.lastRenderX) * partialTicks
				- Math.cos(Math.toRadians(player.getYaw())) * 0.16;

		double arrowPosY = player.lastRenderY + (player.getY() - player.lastRenderY) * partialTicks
				+ player.getStandingEyeHeight() - 0.1;

		double arrowPosZ = player.lastRenderZ + (player.getZ() - player.lastRenderZ) * partialTicks
				- Math.sin(Math.toRadians(player.getYaw())) * 0.16;

		// Motion factor. Arrows go faster than snowballs and all that...
		double arrowMotionFactor = item instanceof RangedWeaponItem ? 1.0 : 0.4;

		double yaw = Math.toRadians(player.getYaw());
		double pitch = Math.toRadians(player.getPitch());

		// calculate starting motion
		double arrowMotionX = -Math.sin(yaw) * Math.cos(pitch) * arrowMotionFactor;
		double arrowMotionY = -Math.sin(pitch) * arrowMotionFactor;
		double arrowMotionZ = Math.cos(yaw) * Math.cos(pitch) * arrowMotionFactor;

		// 3D Pythagorean theorem. Returns the length of the arrowMotion vector.
		double arrowMotion = Math
				.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);

		arrowMotionX /= arrowMotion;
		arrowMotionY /= arrowMotion;
		arrowMotionZ /= arrowMotion;

		// apply bow charge
		if (item instanceof RangedWeaponItem) {
			float bowPower = (72000 - player.getItemUseTimeLeft()) / 20.0f;
			bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f;

			if (bowPower > 1 || bowPower <= 0.1F)
				bowPower = 1;

			bowPower *= 3F;
			arrowMotionX *= bowPower;
			arrowMotionY *= bowPower;
			arrowMotionZ *= bowPower;

		} else {
			arrowMotionX *= 1.5;
			arrowMotionY *= 1.5;
			arrowMotionZ *= 1.5;
		}

		double gravity = getProjectileGravity(item);
		Vec3d eyesPos = OtherRenderUtils.getEyesPos();

		for (int i = 0; i < 1000; i++) {
			// add to path
			Vec3d arrowPos = new Vec3d(arrowPosX, arrowPosY, arrowPosZ);
			path.add(arrowPos);

			// apply motion
			arrowPosX += arrowMotionX * 0.1;
			arrowPosY += arrowMotionY * 0.1;
			arrowPosZ += arrowMotionZ * 0.1;

			// apply air friction
			arrowMotionX *= 0.999;
			arrowMotionY *= 0.999;
			arrowMotionZ *= 0.999;

			// apply gravity
			arrowMotionY -= gravity * 0.1;

			// check for collision
			RaycastContext context = new RaycastContext(eyesPos, arrowPos, RaycastContext.ShapeType.COLLIDER,
					RaycastContext.FluidHandling.NONE, mc.player);
			if (mc.world.raycast(context).getType() != HitResult.Type.MISS)
				break;
		}

		return path;
	}

	private double getProjectileGravity(Item item) {
		if (item instanceof BowItem || item instanceof CrossbowItem)
			return 0.05;

		if (item instanceof PotionItem)
			return 0.4;

		if (item instanceof FishingRodItem)
			return 0.15;

		if (item instanceof TridentItem)
			return 0.015;

		return 0.03;
	}

	private boolean isThrowable(Item item) {
		return item instanceof BowItem || item instanceof CrossbowItem || item instanceof SnowballItem
				|| item instanceof EggItem || item instanceof EnderPearlItem || item instanceof SplashPotionItem
				|| item instanceof LingeringPotionItem || item instanceof FishingRodItem || item instanceof TridentItem;
	}
}
