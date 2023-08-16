package autopartsclient.module.Player;

import autopartsclient.module.Mod;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class AntiHunger extends Mod{
	public static boolean lastOnGround;
	public static  boolean sendOnGroundTruePacket;
	public static  boolean ignorePacket;
	
	public AntiHunger() {
		super("AntiHunger", "", Category.PLAYER);
	}
	
	
	
	public void onTick() {
		if (mc.player.isOnGround() && !lastOnGround && !sendOnGroundTruePacket) sendOnGroundTruePacket = true;

        if (mc.player.isOnGround() && sendOnGroundTruePacket) {
            ignorePacket = true;
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            ignorePacket = false;

            sendOnGroundTruePacket = false;
        }

        lastOnGround = mc.player.isOnGround();
		super.onTick();
	}
}
