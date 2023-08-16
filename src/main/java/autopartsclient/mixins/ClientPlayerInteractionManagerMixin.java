package autopartsclient.mixins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import autopartsclient.Client;
import autopartsclient.module.Combat.ArrowDamage;
import autopartsclient.module.Exploit.PacketMine;
import autopartsclient.module.Player.Reach;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    private Logger logger = LogManager.getLogger(Client.class);
    
    MinecraftClient MC = MinecraftClient.getInstance();

    // Prevents attacking self.
    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
	if (target.equals(MC.player)) {
	    ci.cancel();
	}
    }

    @Inject(at = { @At("HEAD") }, method = { "getReachDistance()F" }, cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> ci) {
	if (Reach.isToggled = false)
	    return;
	else {
	    ci.setReturnValue(Reach.reachlength);
	}
    }

    @Inject(at = { @At("HEAD") }, method = { "hasExtendedReach()Z" }, cancellable = true)
    private void hasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
	if (Reach.isToggled = false)
	    return;

	else {
	    cir.setReturnValue(true);
	}
    }

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    private void onAttackBlock(BlockPos blockPos, Direction direction, CallbackInfoReturnable<Boolean> info) {
	if (PacketMine.isToggled) {
	    MC.player.swingHand(Hand.MAIN_HAND);

	    if (blockPos == null)
		return;

	    MC.getNetworkHandler().sendPacket(
		    new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, direction));
	    MC.getNetworkHandler().sendPacket(
		    new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, direction));

	    PacketMine.onDamgeBlock(blockPos, direction);
	    info.cancel(); // info. //blockPos = null; }
	}
    }
    
    @Inject(method = "stopUsingItem", at = @At("HEAD"))
    public void onStopUsingItem(PlayerEntity player, CallbackInfo ci) {
        if(ArrowDamage.isToggled && MC.player.getActiveItem().getItem() == Items.BOW) {
            ClientPlayerEntity p = MC.player;

            p.networkHandler.sendPacket(
                    new ClientCommandC2SPacket(p, ClientCommandC2SPacket.Mode.START_SPRINTING));

            double x = p.getX();
            double y = p.getY();
            double z = p.getZ();

            for (int i = 0; i < ArrowDamage.packetCount.getValue() / 2; i++) {
                p.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x,
                        y - 1e-10, z, true));
                p.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x,
                        y + 1e-10, z, false));
            }
        }
    }
}
