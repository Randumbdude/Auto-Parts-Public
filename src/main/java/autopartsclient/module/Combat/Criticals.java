package autopartsclient.module.Combat;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Criticals extends Mod {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean isToggled;

    private static ModeSetting mode = new ModeSetting("Mode", "Mini Jump", "Full Jump", "Mini Jump");

    public Criticals() {
	super("Criticals", "", Category.COMBAT);
	addSetting(mode);
    }

    @Override
    public void onEnable() {
	// TODO Auto-generated method stub
	isToggled = true;
	super.onEnable();
    }

    @Override
    public void onDisable() {
	// TODO Auto-generated method stub
	isToggled = false;
	super.onDisable();
    }

    public static void sendCritPackets() {
	if (mc.player.isClimbing() || mc.player.isTouchingWater() || mc.player.hasStatusEffect(StatusEffects.BLINDNESS)
		|| mc.player.hasVehicle()) {
	    return;
	}

	boolean sprinting = mc.player.isSprinting();
	if (sprinting) {
	    mc.player.setSprinting(false);
	    mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, Mode.STOP_SPRINTING));
	}

	if (mc.player.isOnGround()) {
	    double x = mc.player.getX();
	    double y = mc.player.getY();
	    double z = mc.player.getZ();
	    if (mode.getMode()== "Mini Jump") {
		mc.player.networkHandler
			.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0633, z, false));
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
	    } else {
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.42, z, false));
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.65, z, false));
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.72, z, false));
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.53, z, false));
		mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.32, z, false));
	    }
	}

	if (sprinting) {
	    mc.player.setSprinting(true);
	    mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, Mode.START_SPRINTING));
	}
    }
}
