package autopartsclient.module.Render;

import org.apache.logging.log4j.LogManager;

import autopartsclient.module.Mod;
import autopartsclient.module.Mod.Category;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.RenderUtils;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ESP extends Mod {
	
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder bufferBuilder = tessellator.getBuffer();

	org.apache.logging.log4j.Logger logger = LogManager.getLogger(ESP.class);

	public ESP() {
		super("ESP", "", Category.RENDER);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("ESP Enabled");
	}

	@Override
	public void onDisable() {
		ChatUtils.message("ESP Disabled");
	}

	@Override
	public void render(MatrixStack matrices, float partialTicks) {
		ColorUtil HostileColor = new ColorUtil(255, 0, 0);
		ColorUtil PlayerColor = new ColorUtil(66, 135, 245);
		ColorUtil PassiveColor = new ColorUtil(0, 255, 0);
		
		for (Entity entity : mc.world.getEntities()) {
			if (entity instanceof HostileEntity && entity != mc.player) {
				//
			}
			if (entity instanceof PlayerEntity && entity != mc.player) {
				//RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(), entity.getPos(), PlayerColor);
			}
			if (entity instanceof PassiveEntity && entity != mc.player) {
				//RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(), entity.getPos(), PassiveColor);
			}
		}
	}
}