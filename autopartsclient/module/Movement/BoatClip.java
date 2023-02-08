package autopartsclient.module.Movement;

import autopartsclient.event.events.EventClientMove;
import autopartsclient.eventbus.Subscribe;
import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Box;

public class BoatClip extends Mod {
	
	public static boolean isToggled;
	
	public int key;

	public BoatClip() {
		super("BoatClip", "Weird", Category.MOVEMENT);
	}

	@Override
	public void onEnable() {
		ChatUtils.message("BoatClip Enabled");
		isToggled = true;
	}

	@Override
	public void onDisable() {
		ChatUtils.message("BoatClip Disabled");
		isToggled = false;
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
        else if(mc.options.backKey.isPressed()) {
        	mc.player.getVehicle().setVelocity(0, -1, 0);
        }
        else {
        	mc.player.getVehicle().setVelocityClient(0, 0, 0);
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
