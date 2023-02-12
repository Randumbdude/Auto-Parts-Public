package autopartsclient.module.Render;

import java.awt.Color;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class Waypoint extends Mod{
	private static final Identifier texture =
			new Identifier("way", "icon.png");
	
	Vec3d waypointVec;
	
	public Waypoint() {
		super("Waypoint", "", Category.RENDER);
	}
	
	@Override
	public void onEnable() {		
		waypointVec = new Vec3d(mc.player.getX(), 0, mc.player.getZ());
		
		super.onEnable();
	}
	
	@Override
	public void render(MatrixStack matrixStack, float partialTicks) {
		int wordX = (int) (200/(200+ waypointVec.getZ())*(waypointVec.getX()));
		int wordY = (int) (200/(200+ waypointVec.getZ())*(waypointVec.getY()));

		ColorUtil color = new ColorUtil(255,0,0);
		
		RenderUtils.drawWaypoint(matrixStack, waypointVec, color);
		
		mc.textRenderer.drawWithShadow(matrixStack, "Distance", wordX, wordY, Color.GREEN.getRGB());
	}
}
