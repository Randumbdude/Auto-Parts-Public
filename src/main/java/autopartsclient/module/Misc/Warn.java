package autopartsclient.module.Misc;

import java.util.ArrayList;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Warn extends Mod {
	public static boolean isToggled;
	
	private static ArrayList<String> PlayersInRange = new ArrayList<>();

	public BooleanSetting CreeperAlert = new BooleanSetting("Creeper", true);

	public Warn() {
		super("Warn", "", Category.MISC);
		addSetting(CreeperAlert);
	}
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		isToggled = true;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		PlayersInRange.removeAll(PlayersInRange);
		isToggled = false;
		super.onEnable();
	}

	@Override
	public void onTick() {
		/*
		for (Entity e : mc.world.getEntities()) {
			if (e instanceof CreeperEntity && e != mc.player && mc.player.distanceTo(e) <= 7
					&& CreeperAlert.isEnabled()) {
				ChatUtils.message("Creeper In Your Personal Space!!!");
			}

			if (e instanceof PlayerEntity && e != mc.player && mc.player.distanceTo(e) <= 115) {
				if (PlayersInRange.contains(e.getEntityName())) {
					return;
				} else {
					PlayersInRange.add(e.getEntityName());
					ChatUtils.message(e.getEntityName() + " Entered Range (" + (int) e.getX() + "," + (int) e.getY()
							+ ", " + (int) e.getZ() + ")");
				}
			} else if (e instanceof PlayerEntity && e != mc.player && mc.player.distanceTo(e) >= 115) {
				if (PlayersInRange.contains(e.getEntityName())) {
					PlayersInRange.remove(e.getEntityName());
					ChatUtils.message(e.getEntityName() + " Left Range(" + (int) e.getX() + "," + (int) e.getY() + ", "
							+ (int) e.getZ() + ")");
				} else {
					return;
				}
			}
		}
		*/
	}

	@Override
	public void render(MatrixStack matrixStack, float partialTicks) {
		/*
		for (Entity e : mc.world.getEntities()) {
			if (e instanceof PlayerEntity && e != mc.player) {
				RenderUtils.drawWaypoint(matrixStack, e.getPos(), new ColorUtil(255,0,0));
			}
		}
		*/
	}
}
/*
 * for (Entity e : mc.world.getEntities()) { if (e instanceof CreeperEntity && e
 * != mc.player && mc.player.distanceTo(e) <= 7 && CreeperAlert.isEnabled()) {
 * ChatUtils.message("Creeper In Your Personal Space!!!"); }
 * 
 * if (e instanceof PlayerEntity && e != mc.player && mc.player.distanceTo(e) <=
 * 115) { if (PlayersInRange.contains(e.getEntityName())) { return; } else {
 * PlayersInRange.add(e.getEntityName()); ChatUtils.message(e.getEntityName() +
 * " Entered Range (" + (int) e.getX() + "," + (int) e.getY() + ", " + (int)
 * e.getZ() + ")"); } } else if (e instanceof PlayerEntity && e != mc.player &&
 * mc.player.distanceTo(e) >= 115) { if
 * (PlayersInRange.contains(e.getEntityName())) {
 * PlayersInRange.remove(e.getEntityName()); ChatUtils.message(e.getEntityName()
 * + " Left Range(" + (int) e.getX() + "," + (int) e.getY() + ", " + (int)
 * e.getZ() + ")"); } else { return; } } }
 */