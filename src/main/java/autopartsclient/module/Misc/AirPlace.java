package autopartsclient.module.Misc;

import com.mojang.blaze3d.systems.RenderSystem;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.OtherRenderUtils;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AirPlace extends Mod {
	public ModeSetting Mode = new ModeSetting("Mode", "Multi", "Single", "Multi");

	public static boolean isToggled;
	private boolean pressed;

	public AirPlace() {
		super("AirPlace", "", Category.MISC);
		//addSetting(Mode);
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		isToggled = true;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		isToggled = false;
		super.onDisable();
	}

	@Override
	public void onTick() {
		boolean isKeyUsePressed = mc.options.useKey.isPressed();

		if (!canPlaceAtCrosshair()) {
			return;
		}

		if (!pressed && isKeyUsePressed) {
			mc.getNetworkHandler().sendPacket(
					new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, (BlockHitResult) mc.crosshairTarget, 0));
			pressed = true;
		} else if (!isKeyUsePressed) {
			pressed = false;
		}

	}

	@Override
	public void render(MatrixStack matrixStack, float partialTicks) {
		if (!canPlaceAtCrosshair()) {
			return;
		}

		BlockPos pos = ((BlockHitResult) mc.crosshairTarget).getBlockPos();
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderUtils.drawBlockBox(matrixStack, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), new ColorUtil(255, 255, 255));
	}

	private boolean canPlaceAtCrosshair() {
		return mc.crosshairTarget instanceof BlockHitResult && mc.world
				.getBlockState(((BlockHitResult) mc.crosshairTarget).getBlockPos()).getMaterial().isReplaceable();
	}
}
