package autopartsclient.module.Render;

import org.apache.logging.log4j.LogManager;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.RenderUtils;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ESP extends Mod {

	public static boolean isToggled = false;

	org.apache.logging.log4j.Logger logger = LogManager.getLogger(ESP.class);

	public BooleanSetting players = new BooleanSetting("players", true);
	public BooleanSetting passiveMobs = new BooleanSetting("Passive", true);
	public BooleanSetting hostileMobs = new BooleanSetting("Hostile", true);
	public BooleanSetting Items = new BooleanSetting("Items", false);

	public ESP() {
		super("ESP", "", Category.RENDER);
		addSetting(players, passiveMobs, hostileMobs, Items);
	}

	@Override
	public void onEnable() {
		isToggled = true;
	}

	@Override
	public void onDisable() {
		isToggled = false;
	}

	@Override
	public void render(MatrixStack matrices, float partialTicks) {
		ColorUtil HostileColor = new ColorUtil(255, 0, 0);
		ColorUtil PlayerColor = new ColorUtil(255, 0, 255);
		ColorUtil PassiveColor = new ColorUtil(0, 255, 0);
		ColorUtil ItemColor = new ColorUtil(52, 192, 235);

		for (Entity entity : mc.world.getEntities()) {
			if (entity instanceof HostileEntity && entity != mc.player && hostileMobs.isEnabled()) {
				RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
						entity.getBoundingBox().getCenter(), HostileColor);
			}
			if (entity instanceof PlayerEntity && entity != mc.player && players.isEnabled()) {
				
				if(entity == FreecamUtil.getFreeCamera()) {
					return;
				}
				
				RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
						entity.getBoundingBox().getCenter(), PlayerColor);
			}
			if (entity instanceof PassiveEntity && entity != mc.player && passiveMobs.isEnabled()) {
				RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
						entity.getBoundingBox().getCenter(), PassiveColor);
			}
		}
		for (Entity entity : mc.world.getEntities()) {
			if (entity instanceof ItemEntity && entity != mc.player && Items.isEnabled()) {
				RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
						entity.getBoundingBox().getCenter(), ItemColor);
			}
		}
	}
}