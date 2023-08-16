package autopartsclient.module.Render;

import java.awt.Color;

import org.apache.http.util.TextUtils;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;

import autopartsclient.module.Mod;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class Waypoint extends Mod{
	private static final Identifier texture = new Identifier("autoparts", "icon.png");
	
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

		ColorUtil color = new ColorUtil(255,0,255);
		
		RenderUtils.drawWaypoint(matrixStack, waypointVec, color);
	}
}
