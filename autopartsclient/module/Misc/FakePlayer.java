package autopartsclient.module.Misc;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;

public class FakePlayer extends Mod{
	public FakePlayer() {super("FakePlayer","Spawns fake player or some fuckshit", Category.MISC);}
	
	OtherClientPlayerEntity player = new OtherClientPlayerEntity(mc.world, new GameProfile(mc.player.getUuid(), mc.player.getName().getString() + " - Copy"));
	
			//new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("0f75a81d-70e5-43c5-b892-f33c524284f2"), "Dummy"), null);
			
	@Override
    public void onEnable() {
        if (mc.world == null || mc.player == null) {onDisable(); return;}
        
        player.copyPositionAndRotation(mc.player);
        //player.setHeadYaw(mc.player.headYaw);
        mc.world.addEntity(-100, player);
        
        ChatUtils.message("Spawned Fake Player");
    }

    @Override
    public void onDisable() {
        if (mc.world != null) mc.world.removeEntity(-100, Entity.RemovalReason.DISCARDED);
        player.discard();
        ChatUtils.message("Despawned Fake Player");
    }
}
