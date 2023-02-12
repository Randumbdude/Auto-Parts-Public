package autopartsclient.module.Misc;

import java.util.ArrayList;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Warn extends Mod {
	private static ArrayList<String> PlayersInRange = new ArrayList<>();

	public BooleanSetting CreeperAlert = new BooleanSetting("Creeper", true);

	public Warn() {
		super("Warn", "", Category.MISC);
		addSetting(CreeperAlert);
	}
	@Override
	public void onDisable() {
		PlayersInRange.removeAll(PlayersInRange);

		super.onEnable();
	}

	@Override
	public void onTick() {
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
	}
}