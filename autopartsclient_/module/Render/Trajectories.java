package autopartsclient.module.Render;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import autopartsclient.module.Mod;
import autopartsclient.util.Render.RenderUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class Trajectories extends Mod {
	// private List<Triple<List<Vec3d>, Entity, BlockPos>> poses = new
	// ArrayList<>();

	static double angleToHit = 0;

	final boolean smoothLines = false;
	
	public int[] getConfigColor() {
		return new int[] {255, 0, 0, 255};
	}	

	public Trajectories() {
		super("Trajectories", "", Category.RENDER);
	}

	@Override
	public void onTick() {
		// Rendering
		Set<Item> itemsSimple = new HashSet<>();
		itemsSimple.add(Items.ENDER_PEARL.asItem());
		itemsSimple.add(Items.SNOWBALL.asItem());
		// items.add(Items.SPLASH_POTION.asItem());
		itemsSimple.add(Items.EGG.asItem());

		Set<Item> itemsComplex = new HashSet<>();
		itemsComplex.add(Items.BOW.asItem());

		MinecraftClient client = MinecraftClient.getInstance();

		WorldRenderEvents.AFTER_ENTITIES.register((WorldRenderContext context) -> {
			MatrixStack stack = context.matrixStack();

			RenderSystem.setShader(GameRenderer::getPositionColorProgram);
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableTexture();
			if (smoothLines)
				GL11.glEnable(GL11.GL_LINE_SMOOTH);

			stack.push();
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder buffer = tessellator.getBuffer();
			buffer.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

			RenderSystem.applyModelViewMatrix();
			Camera camera = client.gameRenderer.getCamera();
			Vec3d cameraPos = camera.getPos();

			// GL11.glLineWidth(2.0f);
			// GL11.glBegin(GL11.GL_LINES);

			PlayerEntity playerEntity = client.player;
			World world = client.world;
			BlockPos blockPos = new BlockPos(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ());
			float pitch = playerEntity.getPitch();
			float yaw = playerEntity.getYaw();
			double eye = playerEntity.getEyeY();
			ItemStack itemStack = playerEntity.getMainHandStack();
			ItemStack itemStackAlt = playerEntity.getOffHandStack();

			// Set mainHand to true/false based on which hand is holding a item which is a
			// projectile.
			boolean mainHand = true;
			if (itemsSimple.contains(itemStack.getItem()) || itemsComplex.contains(itemStack.getItem())) {
				mainHand = true;
			} else if (itemsSimple.contains(itemStackAlt.getItem()) || itemsComplex.contains(itemStackAlt.getItem())) {
				mainHand = false;
			}
			;

			if (itemsSimple.contains(itemStack.getItem()) || itemsSimple.contains(itemStackAlt.getItem())) {
				//System.out.println("A");
				float speed = 1.5f;
				
				int[] color = getConfigColor();
				
				renderCurve(stack, buffer, camera, world, blockPos, pitch, yaw, eye, playerEntity,
						color, speed, 0.03f, mainHand); //color errors
			} else if (itemsComplex.contains(itemStack.getItem()) || itemsComplex.contains(itemStackAlt.getItem())) {
				//System.out.println("B");
				float bowMultiplier = (72000.0f - playerEntity.getItemUseTimeLeft()) / 20.0f;
				bowMultiplier = (bowMultiplier * bowMultiplier + bowMultiplier * 2.0f) / 3.0f;
				if (bowMultiplier > 1.0f) {
					bowMultiplier = 1.0f;
				}

				float speed = bowMultiplier * 3.0f;
				
				int[] color = getConfigColor();
				
				renderCurve(stack, buffer, camera, world, blockPos, pitch, yaw, eye, playerEntity,
						color, speed, 0.05f, mainHand); //color errors
			}

			// GL11.glEnd();
			tessellator.draw();
			stack.pop();
			RenderSystem.applyModelViewMatrix();
			RenderSystem.setShaderColor(1, 1, 1, 1);

			RenderSystem.disableBlend();
			RenderSystem.enableTexture();
			if (smoothLines)
				GL11.glDisable(GL11.GL_LINE_SMOOTH);
		});

		// TrajectoryCommands.registerCommands();
	}

	public static void renderCurve(MatrixStack stack, VertexConsumer buffer, Camera camera, World world, BlockPos pos,
			float pitch, float yaw, double eye, PlayerEntity player, int[] color, float speed, float gravity,
			boolean mainHand) {
		double d0 = camera.getPos().x;
		double d1 = camera.getPos().y - .005D;
		double d2 = camera.getPos().z;
		double accurateX = player.getX();
		double accurateY = player.getY();
		double accurateZ = player.getZ();

		// Simulation

		// float ngravity = 0.03f;
		float drag = 0.99f;

		float entityVelX = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		float entityVelY = -MathHelper.sin(pitch * 0.017453292F);
		float entityVelZ = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		Vec3d vec3d = (new Vec3d(entityVelX, entityVelY, entityVelZ)).normalize().multiply(speed);
		Vec3d playerVelocity = player.getVelocity();
		vec3d = vec3d.add(playerVelocity.x, player.isOnGround() ? 0.0D : playerVelocity.y, playerVelocity.z);
		Vec3d entityVelocity = new Vec3d(vec3d.x, vec3d.y, vec3d.z);
		Vec3d entityPosition = new Vec3d(0, 0 + 1.5, 0);

		int playerside = 1;
		playerside = (mainHand ? 1 : -1);

		double offsetX = 0 * Math.cos(Math.toRadians((yaw + 90) * -1))
				+ playerside * Math.sin(Math.toRadians((yaw + 90) * -1));
		double offsetZ = 0 * Math.sin(Math.toRadians((yaw + 90) * -1))
				+ playerside * Math.cos(Math.toRadians((yaw + 90) * -1));

		double prevX = 0;
		double prevZ = 0;

		SnowballEntity tempEntity = new SnowballEntity(world, player);

		for (int i = 0; i < 100; i++) {
			HitResult hitResult = world.raycast(new RaycastContext(
					new Vec3d(accurateX + entityPosition.x, accurateY + entityPosition.y, accurateZ + entityPosition.z),
					new Vec3d(accurateX + entityPosition.x, accurateY + entityPosition.y, accurateZ + entityPosition.z)
							.add(entityVelocity),
					RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, tempEntity));
			if (hitResult.getType() != HitResult.Type.MISS) {
				double hitDistance = hitResult.getPos().distanceTo(player.getPos());
				double boxSize = hitDistance > 30 ? hitDistance / 70 : 0.5;
				double defaultBoxSize = 0.5; // 0.5 = full block
				 // Box visibility
					renderBox(stack, buffer, hitResult.getPos().x - defaultBoxSize - d0,
							hitResult.getPos().y - defaultBoxSize - d1, hitResult.getPos().z - defaultBoxSize - d2,
							hitResult.getPos().x + defaultBoxSize - d0, hitResult.getPos().y + defaultBoxSize - d1,
							hitResult.getPos().z + defaultBoxSize - d2, color);
				
				 // ApproxBox visibility
					
					renderBox(stack, buffer, hitResult.getPos().x - boxSize - d0, hitResult.getPos().y - boxSize - d1,
							hitResult.getPos().z - boxSize - d2, hitResult.getPos().x + boxSize - d0,
							hitResult.getPos().y + boxSize - d1, hitResult.getPos().z + boxSize - d2, color);
				
					
				angleToHit = Math.acos(playerside / hitDistance); // finds the angle the line needs to point at to hit
																	// the target (since it's coming from the side, and
																	// changes depending on distance),
				// the "1" represents the opposite side, which we know. (opposite/adjacent).
				//
				// Good reference:
				// https://www.khanacademy.org/math/geometry/hs-geo-trig/hs-geo-solve-for-an-angle/a/inverse-trig-functions-intro
				break;
			}

			double startx = prevX + offsetX + (accurateX - d0);
			double starty = entityPosition.y + (accurateY - d1);
			double startz = prevZ + offsetZ + (accurateZ - d2);

			entityPosition = entityPosition.add(entityVelocity);
			entityVelocity = entityVelocity.multiply(drag);
			entityVelocity = new Vec3d(entityVelocity.x, entityVelocity.y - gravity, entityVelocity.z);
			double newX = entityPosition.x * Math.cos(angleToHit - 1.5708)
					- entityPosition.z * Math.sin(angleToHit - 1.5708); // Rotation to point trajectory curve towards
																		// hit mark.
			double newZ = entityPosition.x * Math.sin(angleToHit - 1.5708)
					+ entityPosition.z * Math.cos(angleToHit - 1.5708); //

			 // Line visibility
				double endx = newX + offsetX + (accurateX - d0);
				double endy = entityPosition.y + (accurateY - d1);
				double endz = newZ + offsetZ + (accurateZ - d2);
				RenderUtils.renderSingleLine(stack, buffer, (float) startx, (float) starty, (float) startz,
						(float) endx, (float) endy, (float) endz, color[0] / 255f, color[1] / 255f, color[2] / 255f,
						(float) color[3] / 100);

			

			prevX = newX;
			prevZ = newZ;
		}

	}

	public static void renderBox(MatrixStack stack, VertexConsumer buffer, double x1, double y1, double z1, double x2,
			double y2, double z2, int[] color) {
		Box aabb = new Box(x1, y1, z1, x2, y2, z2);
		WorldRenderer.drawBox(stack, buffer, aabb, color[0], color[1], color[2], 1);
	}
}
