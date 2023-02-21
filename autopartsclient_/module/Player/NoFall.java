package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.network.packet.c2s.play.*;

public class NoFall extends Mod{

    public NoFall() {
        super("NoFall", "Prevents the player from taking fall damage", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (mc.player != null) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    }

}