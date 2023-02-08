package autopartsclient.module.Misc;

import java.util.ArrayList;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class Warn extends Mod {
	private static ArrayList<String> PlayersInRange = new ArrayList<>();

	public Warn() {
		super("Warn", "", Category.MISC);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("Warn Enabled");
		super.onEnable();
	}

	@Override
	public void onDisable() {
		ChatUtils.message("Warn Disabled");
		super.onEnable();
	}

	@Override
	public void onTick() {
		for (Entity e : mc.world.getEntities()) {
			if (e instanceof PlayerEntity && e != mc.player && mc.player.distanceTo(e) <= 115) {
				if (PlayersInRange.contains(e.getEntityName())) {
					return;
				} else {
					PlayersInRange.add(e.getEntityName());
					ChatUtils.message(e.getEntityName() + " Entered Range");
				}
			} else if (e instanceof PlayerEntity && e != mc.player && mc.player.distanceTo(e) >= 115) {
				if (PlayersInRange.contains(e.getEntityName())) {
					PlayersInRange.remove(e.getEntityName());
					ChatUtils.message(e.getEntityName() + " Left Range");
				} else {
					return;
				}
			}
		}
	}
}
