package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.ModeSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall extends Mod{
	
	public static ModeSetting Mode = new ModeSetting("Mode", "Normal", "Packet", "Normal");

    public NoFall() {
        super("NoFall", "Prevents the player from taking fall damage", Category.PLAYER);
        addSetting(Mode);
    }

    @Override
    public void onTick() {
        if (mc.player != null && Mode.getMode() == "Normal") {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    	
    	if (mc.player.fallDistance > 2.5f && Mode.getMode() == "Packet") {
			if (mc.player.isFallFlying())
				return;
			mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
		}
    }

}