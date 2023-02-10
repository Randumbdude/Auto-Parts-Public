package autopartsclient.module.Render;
//page 19 - https://github.com/search?l=Java&p=19&q=hack+client&type=Repositories

import org.apache.logging.log4j.LogManager;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Tracers extends Mod {

	org.apache.logging.log4j.Logger logger = LogManager.getLogger(Tracers.class);

	public BooleanSetting players = new BooleanSetting("players", true);
	public BooleanSetting passiveMobs = new BooleanSetting("Passive", false);
	public BooleanSetting hostileMobs = new BooleanSetting("Hostile", true);

	public Tracers() {
		super("Tracers", "", Category.RENDER);
		addSetting(players, passiveMobs, hostileMobs);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("Tracers Enabled");
	}

	@Override
	public void onDisable() {
		ChatUtils.message("Tracers Disabled");
	}

	@Override
	public void render(MatrixStack matrices, float partialTicks) {
		ColorUtil HostileColor = new ColorUtil(255, 0, 0);
		ColorUtil PlayerColor = new ColorUtil(255, 0, 255);
		ColorUtil PassiveColor = new ColorUtil(0, 255, 0);

		for (Entity entity : mc.world.getEntities()) {
			if (entity instanceof HostileEntity && entity != mc.player && hostileMobs.isEnabled()) {
				
				RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(), entity.getBoundingBox().getCenter(),HostileColor);
				if (ESP.isToggled == false) {
					RenderUtils.drawEntityLine(matrices, entity.getBoundingBox().getCenter(),
							entity.getBoundingBox().getCenter(), HostileColor);
				}

			}
			if (entity instanceof PlayerEntity && entity != mc.player && players.isEnabled()) {
				RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(), entity.getBoundingBox().getCenter(),
						PlayerColor);
				if (ESP.isToggled == false) {
					RenderUtils.drawEntityLine(matrices, entity.getBoundingBox().getCenter(),
							entity.getBoundingBox().getCenter(), PlayerColor);
				}
			}
			if (entity instanceof PassiveEntity && entity != mc.player && passiveMobs.isEnabled()) {
				RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(), entity.getBoundingBox().getCenter(),
						PassiveColor);
				if (ESP.isToggled == false) {
					RenderUtils.drawEntityLine(matrices, entity.getBoundingBox().getCenter(),
							entity.getBoundingBox().getCenter(), PassiveColor);
				}
			}
		}
	}
}
