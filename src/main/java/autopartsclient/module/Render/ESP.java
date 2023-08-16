package autopartsclient.module.Render;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;

import com.google.gson.JsonSyntaxException;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ESP extends Mod {

    public static boolean isToggled = false;

    org.apache.logging.log4j.Logger logger = LogManager.getLogger(ESP.class);


    public BooleanSetting players = new BooleanSetting("players", true);
    public BooleanSetting passiveMobs = new BooleanSetting("Passive", true);
    public BooleanSetting hostileMobs = new BooleanSetting("Hostile", true);
    public BooleanSetting Items = new BooleanSetting("Items", false);
    public static ModeSetting Mode = new ModeSetting("Mode", "Box", "Shader", "Box");

    public NumberSetting PassiveHue = new NumberSetting("PassiveHue", 1, 360, 100, 1);
    public NumberSetting HostileHue = new NumberSetting("HostileHue", 1, 360, 1, 1);
    public NumberSetting PlayerHue = new NumberSetting("PlayerHue", 1, 360, 294, 1);
    public NumberSetting ItemHue = new NumberSetting("ItemHue", 1, 360, 200, 1);

    public ESP() {
	super("ESP", "", Category.RENDER);
	addSetting(players, passiveMobs, hostileMobs, Items, Mode, PassiveHue, HostileHue, PlayerHue, ItemHue);
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
	ColorUtil HostileColor = new ColorUtil(HostileHue.getValueFloat());
	ColorUtil PlayerColor = new ColorUtil(PlayerHue.getValueFloat());
	ColorUtil PassiveColor = new ColorUtil(PassiveHue.getValueFloat());
	ColorUtil ItemColor = new ColorUtil(ItemHue.getValueFloat());

	if (Mode.getMode() == "Box") {
	    for (Entity entity : mc.world.getEntities()) {
		if (entity instanceof HostileEntity && entity != mc.player && hostileMobs.isEnabled()) {
		    RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), HostileColor, entity.getHeight(), entity.getWidth());
		    // OtherRenderUtils.drawBoxOutline(matrices, entity.getBoundingBox(),
		    // QuadColor.single(255,0,0, 255), 1);
		}
		if (entity instanceof PlayerEntity && entity != mc.player && players.isEnabled()) {

		    if (entity == FreecamUtil.getFreeCamera()) {
			return;
		    }
		    // RenderSystem.setShaderColor(255, 0, 0, 1F);
		    RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), PlayerColor, entity.getHeight(), entity.getWidth());
		}
		if (entity instanceof PassiveEntity && entity != mc.player && passiveMobs.isEnabled()) {
		    RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), PassiveColor, entity.getHeight(), entity.getWidth());
		}
	    }
	    for (Entity entity : mc.world.getEntities()) {
		if (entity instanceof ItemEntity && entity != mc.player && Items.isEnabled()) {
		    RenderUtils.draw3Dbox(matrices, entity.getBoundingBox().getCenter(),
			    entity.getBoundingBox().getCenter(), ItemColor, entity.getHeight(), entity.getWidth());
		}
	    }
	}
    }
}