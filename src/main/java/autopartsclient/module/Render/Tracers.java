package autopartsclient.module.Render;
//page 19 - https://github.com/search?l=Java&p=19&q=hack+client&type=Repositories

import org.apache.logging.log4j.LogManager;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Tracers extends Mod {

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(Tracers.class);

    public BooleanSetting players = new BooleanSetting("players", true);
    public BooleanSetting passiveMobs = new BooleanSetting("Passive", false);
    public BooleanSetting hostileMobs = new BooleanSetting("Hostile", false);

    public NumberSetting PassiveHue = new NumberSetting("PassiveHue", 1, 360, 100, 1);
    public NumberSetting HostileHue = new NumberSetting("HostileHue", 1, 360, 1, 1);
    public NumberSetting PlayerHue = new NumberSetting("PlayerHue", 1, 360, 294, 1);

    public Tracers() {
	super("Tracers", "", Category.RENDER);
	addSetting(players, passiveMobs, hostileMobs, PassiveHue, HostileHue, PlayerHue);
    }

    @Override
    public void render(MatrixStack matrices, float partialTicks) {
	ColorUtil HostileColor = new ColorUtil(HostileHue.getValueFloat());
	ColorUtil PlayerColor = new ColorUtil(PlayerHue.getValueFloat());
	ColorUtil PassiveColor = new ColorUtil(PassiveHue.getValueFloat());

	for (Entity entity : mc.world.getEntities()) {
	    if (entity instanceof HostileEntity && entity != mc.player && hostileMobs.isEnabled()) {
		if (Freecam.isToggled) {
		    RenderUtils.drawLine3D(matrices, RenderUtils.freecamLookVec(), entity.getBoundingBox().getCenter(),
			    HostileColor);
		} else {
		    RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(),
			    entity.getBoundingBox().getCenter(), HostileColor);
		}
		if (ESP.isToggled == false) {
		    RenderUtils.drawEntityLine(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), HostileColor);
		}

	    }
	    if (entity instanceof PlayerEntity && entity != mc.player && players.isEnabled()) {

		if (entity == FreecamUtil.getFreeCamera()) {
		    return;
		}

		if (Freecam.isToggled) {
		    RenderUtils.drawLine3D(matrices, RenderUtils.freecamLookVec(), entity.getBoundingBox().getCenter(),
			    PlayerColor);
		} else {
		    RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(),
			    entity.getBoundingBox().getCenter(), PlayerColor);
		}

		if (ESP.isToggled == false) {
		    RenderUtils.drawEntityLine(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), PlayerColor);
		}
	    }
	    if (entity instanceof PassiveEntity && entity != mc.player && passiveMobs.isEnabled()) {

		if (Freecam.isToggled) {
		    RenderUtils.drawLine3D(matrices, RenderUtils.freecamLookVec(), entity.getBoundingBox().getCenter(),
			    PassiveColor);

		} else {
		    RenderUtils.drawLine3D(matrices, RenderUtils.getClientLookVec(),
			    entity.getBoundingBox().getCenter(), PassiveColor);
		}

		if (ESP.isToggled == false) {
		    RenderUtils.drawEntityLine(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), PassiveColor);
		}
	    }
	}
    }
}
