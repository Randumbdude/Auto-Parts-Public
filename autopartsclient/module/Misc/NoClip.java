package autopartsclient.module.Misc;

import autopartsclient.event.events.EventClientMove;
import autopartsclient.eventbus.Subscribe;
import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Box;

public class NoClip extends Mod {
	public int key;

	public NoClip() {
		super("BoatClip", "Weird", Category.MISC);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("BoatClip Enabled");
	}

	@Override
	public void onDisable() {
		ChatUtils.message("BoatClip Disabled");
	}

	@Override
	public void onTick() {
        if (!(mc.player.getVehicle() instanceof BoatEntity)) {
            return;
        }
        mc.player.getVehicle().noClip = true;
        mc.player.getVehicle().setNoGravity(true);
        mc.player.setBoundingBox(new Box(0, 0, 0, 0, 0, 0));
        mc.player.noClip = true;
        if(mc.options.jumpKey.isPressed()) {
        	mc.player.getVehicle().setVelocity(0, 1, 0);
        }
        if(mc.options.backKey.isPressed()) {
        	mc.player.getVehicle().setVelocity(0, -1, 0);
        }
	}
	
	@Subscribe
	public void onClientMove(EventClientMove event) {
        if (!(mc.player.getVehicle() instanceof BoatEntity)) {
            return;
        }
        mc.player.getVehicle().noClip = true;
        mc.player.getVehicle().setNoGravity(true);
		mc.player.noClip = true;
		event.setCancelled(false);
	}
}
