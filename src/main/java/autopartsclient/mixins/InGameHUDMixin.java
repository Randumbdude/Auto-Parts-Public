package autopartsclient.mixins;

import java.awt.Color;
import java.text.DecimalFormat;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.SharedVaribles;
import autopartsclient.module.Misc.Coordinates;
import autopartsclient.module.Render.BreakIndicators;
import autopartsclient.module.Render.Freecam;
import autopartsclient.ui.HUD;
import autopartsclient.util.TPSutil;
import autopartsclient.util.Player.BreakProgressTracker;
import autopartsclient.util.Player.PlayerUtils;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.DimensionEffects.Overworld;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;

@Mixin(InGameHud.class)
public class InGameHUDMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    Identifier fontID = new Identifier("autoparts", "font.ttf");

    MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    public void renderHUD(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
	HUD.Render(matrices, tickDelta);
    }

    @Inject(at = @At("TAIL"), method = "render")
    public void renderCords(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
	MinecraftClient mc = MinecraftClient.getInstance();
	TextRenderer renderer = mc.textRenderer;

	int pX = (int) mc.player.getPos().x;
	int pY = (int) mc.player.getPos().y;
	int pZ = (int) mc.player.getPos().z;
	String fps = "FPS: " + ((MinecraftClientAccessor) mc).getCurrentFps();

	String HUDtoRender = "§aAuto Parts - " + SharedVaribles.version + "§f";

	// mc.textRenderer.drawWithShadow(matrices, "Auto Parts - " +
	// SharedVaribles.version, 5, 15, Color.GREEN.getRGB());

	if (SharedVaribles.showFPS) {
	    // renderer.drawWithShadow(matrices, fps, 100, 5, Color.WHITE.getRGB());
	    HUDtoRender += "   " + fps;
	}

	if (SharedVaribles.showCords) {
	    matrices.push();
	    
	    matrices.scale(Coordinates.scale, Coordinates.scale, 1);
	    int windowHeight = client.getWindow().getScaledHeight();
	    // int windowWidth = client.getWindow().getWidth();

	    String displayText;
	    if (PlayerUtils.getDimension() == "OVERWORLD") {
		//
		renderer.drawWithShadow(matrices, "Z: " + pZ + "   §4[" + pZ / 8 + "]", 5, (windowHeight - 30) + (20.0f + 50 / 2.0f),
			new Color(255, 255, 255).getRGB());
		renderer.drawWithShadow(matrices, "Y: " + pY + "   §4[" + pY + "]", 5, ((windowHeight - 30) + (20.0f + 50 / 2.0f)) - 10,
			new Color(255, 255, 255).getRGB());
		renderer.drawWithShadow(matrices, "X: " + pX + "   §4[" + pX / 8 + "]", 5, ((windowHeight - 30) + (20.0f + 50 / 2.0f)) - 20,
			new Color(255, 255, 255).getRGB());
		//
	    } else if (PlayerUtils.getDimension() == "NETHER") {
		//
		renderer.drawWithShadow(matrices, "§4Z: " + pZ + "   §a[" + pZ * 8 + "]", 5, (windowHeight - 30) + (20.0f + 50 / 2.0f),
			new Color(255, 255, 255).getRGB());
		renderer.drawWithShadow(matrices, "§4Y: " + pY + "   §a[" + pY + "]", 5, ((windowHeight - 30) + (20.0f + 50 / 2.0f)) - 10,
			new Color(255, 255, 255).getRGB());
		renderer.drawWithShadow(matrices, "§4X: " + pX + "   §a[" + pX * 8 + "]", 5, ((windowHeight - 30) + (20.0f + 50 / 2.0f)) - 20,
			new Color(255, 255, 255).getRGB());
		//
	    } else {
		renderer.drawWithShadow(matrices, "Z: " + pZ, 5, (windowHeight - 30) + (20.0f + 50 / 2.0f),
			new Color(255, 255, 255).getRGB());
		renderer.drawWithShadow(matrices, "Y: " + pY, 5, ((windowHeight - 30) + (20.0f + 50 / 2.0f)) - 10,
			new Color(255, 255, 255).getRGB());
		renderer.drawWithShadow(matrices, "X: " + pX, 5, ((windowHeight - 30) + (20.0f + 50 / 2.0f)) - 20,
			new Color(255, 255, 255).getRGB());
	    }


	    matrices.pop();
	}

	if (SharedVaribles.showTPS) {
	    DecimalFormat df = new DecimalFormat();
	    df.setMaximumFractionDigits(2);
	    // renderer.drawWithShadow(matrices, "TPS: " + (float) TPSutil.getAverageTPS(),
	    // 260, 5, Color.WHITE.getRGB());

	    HUDtoRender += "   TPS: " + df.format(TPSutil.getAverageTPS());
	    if (TPSutil.getTimeSinceLastTick() > 1) {
		// renderer.drawWithShadow(matrices, "Time Since Last Tick: " +
		// TPSutil.getTimeSinceLastTick(), 260, 20, Color.WHITE.getRGB());
		HUDtoRender += "   Time Since Last Tick: " + df.format(TPSutil.getTimeSinceLastTick());
	    }
	}

	if (BreakIndicators.isToggled) {
	    int windowHeight = client.getWindow().getScaledHeight();
	    int windowWidth = client.getWindow().getScaledWidth();

	    int progress = ((BreakProgressTracker) mc.interactionManager).getBreakProgressPercent(mc.getTickDelta());

	    if (progress != 0) {
		renderer.drawWithShadow(matrices, progress + "%", (windowWidth / 2) - 4.8f, (windowHeight / 2) - 15,
			new Color(255, 255, 255).getRGB());
	    }
	}

	renderer.drawWithShadow(matrices, HUDtoRender, 5, 5, Color.WHITE.getRGB());
	// renderer.drawWithShadow(matrices, new LiteralText("Hello!"), 20, 20, new
	// Color(255, 255, 255).getRGB());
    }

    // Makes HUD correspond to the player rather than the FreeCamera.
    @Inject(method = "getCameraPlayer", at = @At("HEAD"), cancellable = true)
    private void onGetCameraPlayer(CallbackInfoReturnable<PlayerEntity> cir) {
	if (Freecam.isToggled == true) {
	    cir.setReturnValue(mc.player);
	}
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void render(MatrixStack matrices, float f, CallbackInfo ci) {
	if (SharedVaribles.showMiniMe != true || client.currentScreen instanceof ChatScreen)
	    return;

	int windowHeight = client.getWindow().getScaledHeight();
	int windowWidth = client.getWindow().getScaledWidth();

	boolean dynamicScale = false;
	boolean swimFly = true;

	ClientPlayerEntity player = this.client.player;

	EntityRenderDispatcher entityRenderDispatcher = this.client.getEntityRenderDispatcher();

	// Store sensitive info to put back later
	float pitch = player.getPitch();
	float yaw = player.getYaw();
	float headYaw = player.headYaw;
	matrices.push();
	boolean entityShadows = entityRenderDispatcher.gameOptions.getEntityShadows() != null;

	int degrees = 20;

	// Setting the doll to be neutral, and position and scale
	player.setPitch(0.0F);
	player.setBodyYaw(180.0f - degrees);
	player.setHeadYaw(180.0f - degrees);
	float glY = (windowHeight - 50) + (20.0f + 50 / 2.0f);
	float glX = windowWidth - 25;
	int scaleY = MathHelper.ceil(50 / (dynamicScale ? player.getHeight() : 2.0f));
	int scaleX = MathHelper.ceil(25 / (dynamicScale ? player.getWidth() : 1.0f));
	float scale = Math.min(scaleX, scaleY) * -1.0f;

	matrices.translate(glX, glY - (swimFly && (player.isSwimming() || player.isFallFlying()) ? 50 / 2.0f : 0.0f),
		50.0f);
	matrices.scale(scale, scale, scale);

	// Render the doll
	entityRenderDispatcher.setRenderShadows(false);
	VertexConsumerProvider.Immediate immediate = this.client.getBufferBuilders().getEntityVertexConsumers();
	entityRenderDispatcher.render(player, 0.0, 0.0, 0.0, 0.0f, 1.0f, matrices, immediate, 0xF000F0);
	immediate.draw();

	// Put back the info we stored earlier
	entityRenderDispatcher.setRenderShadows(entityShadows);
	matrices.pop();
	player.setPitch(pitch);
	player.setBodyYaw(yaw);
	player.setHeadYaw(headYaw);
    }
}
//InGameOverlayRenderer.class