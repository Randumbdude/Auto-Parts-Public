package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.module.Combat.Criticals;
import autopartsclient.module.Exploit.Disabler;
import autopartsclient.module.Exploit.Ride;
import autopartsclient.module.Misc.AntiKick;
import autopartsclient.module.Movement.AntiRubberBand;
import autopartsclient.module.Movement.Phase;
import autopartsclient.module.Player.AntiHunger;
import autopartsclient.module.Player.EntityDesync;
import autopartsclient.module.Player.PacketCancellor;
import autopartsclient.module.Render.Freecam;
import autopartsclient.module.Render.Search;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.TPSutil;
import autopartsclient.util.FreecamUtils.FreecamUtil;
import autopartsclient.util.Player.PlayerInteractEntityC2SUtils;
import autopartsclient.util.Player.PlayerInteractEntityC2SUtils.InteractType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.TimeoutException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.PacketEncoderException;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    @Shadow
    private Channel channel;

    Freecam fc = new Freecam();

    // Disables freecam if the player disconnects.
    @Inject(method = "handleDisconnection", at = @At("HEAD"))
    private void onHandleDisconnection(CallbackInfo ci) {
	if (Freecam.isToggled == true) {
	    FreecamUtil.toggle();
	    fc.setEnabled(false);
	}
	FreecamUtil.clearTripods();

	if (PacketCancellor.isToggled) {
	    PacketCancellor packetCancellor = new PacketCancellor();
	    packetCancellor.setEnabled(false);
	}
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo callback) {
	if (this.channel.isOpen() && packet != null) {
	    if (AntiRubberBand.isToggled) {
		callback.cancel();
	    }
	}
    }

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void exceptionCaught(ChannelHandlerContext context, Throwable throwable, CallbackInfo ci) {
	if (!(throwable instanceof TimeoutException) && !(throwable instanceof PacketEncoderException)) {
	    // if (apk.logExceptions.get()) apk.warning("Caught exception: %s", throwable);
	    ci.cancel();
	}
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void receive(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
	if (Disabler.isToggled) {
	    if (mc.player != null && mc.world != null && mc.player.age <= 40) {
		if (packet instanceof PlayerPositionLookS2CPacket && Disabler.doVersus == false) {
		    // System.out.println("not doing versus");
		    ci.cancel();
		} else if (Disabler.doVersus = true) {
		    // System.out.println("doing versus");
//	            	if (event.getPacket() instanceof S32PacketConfirmTransaction) {
//	        			event.setCancelled(true);
//	        		}
		    if (packet instanceof KeepAliveS2CPacket) {
			ci.cancel();
		    }
		}
	    }
	}

	if (Ride.isToggled) {
	    if (mc.player != null && mc.player.hasVehicle() && !mc.player.input.sneaking
		    && (packet instanceof PlayerPositionLookS2CPacket
			    || packet instanceof EntityPassengersSetS2CPacket)) {
		ci.cancel();
	    }
	}

	Search.receivePacket(packet);

	TPSutil.run(packet);

	if (EntityDesync.isToggled) {
	    if (packet instanceof ClientCommandC2SPacket) {
		ClientCommandC2SPacket packet1 = (ClientCommandC2SPacket) packet;
		if (packet1.getMode() == Mode.PRESS_SHIFT_KEY) {
		    ChatUtils.message("Dismounted");
		    EntityDesync eDesync = new EntityDesync();
		    eDesync.toggle();
		}
	    }
	}
    }

    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    public void coffee_preventThrow(ChannelHandlerContext context, Throwable ex, CallbackInfo ci) {
	ex.printStackTrace();
	if (AntiKick.isToggled) {
	    ci.cancel();
	}
    }

    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;)V", at = @At("HEAD"), cancellable = true)
    private void send(Packet<?> packet, PacketCallbacks packetCallback, CallbackInfo callback) {

	if (Disabler.isToggled) {
	    if (Disabler.doVersus == true) {
		// System.out.println("doing versus");
		if (packet instanceof PlayerInteractEntityC2SPacket) {
		    callback.cancel();
		}
		if (packet instanceof PlayerActionC2SPacket) {
		    mc.player.networkHandler.sendPacket(new PlayerInputC2SPacket(0, 0, false, false));
		}
		if (packet instanceof PlayerInputC2SPacket) {
		    final PlayerInputC2SPacket packet1 = (PlayerInputC2SPacket) packet;
//	                mc.player.networkHandler.sendPacket(new PlayerInputC2SPacket(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, mc.player.age % 2 == 0, mc.player.age % 2 != 0));
		    ((PlayerInputC2SPacketAccessor) packet1).setForward(Float.POSITIVE_INFINITY);
		    ((PlayerInputC2SPacketAccessor) packet1).setSideways(Float.POSITIVE_INFINITY);
		    ((PlayerInputC2SPacketAccessor) packet1).setJumping(mc.player.age % 2 == 0);
		    ((PlayerInputC2SPacketAccessor) packet1).setSneaking(mc.player.age % 2 != 0);
		}
	    }
	}

	if (Ride.isToggled) {
	    if (packet instanceof VehicleMoveC2SPacket && mc.player.hasVehicle()) {
		mc.interactionManager.interactEntity(mc.player, mc.player.getVehicle(), Hand.MAIN_HAND);
	    }
	}
	AntiHunger antiHunger = new AntiHunger();

	if (antiHunger.isEnabled()) {
	    // System.out.println("yuh");
	    if (AntiHunger.ignorePacket)
		return;

	    if (packet instanceof ClientCommandC2SPacket) {
		ClientCommandC2SPacket.Mode mode = (((ClientCommandC2SPacket) packet).getMode());

		if (mode == ClientCommandC2SPacket.Mode.START_SPRINTING
			|| mode == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
		    callback.cancel();
		}
	    }

	    if (packet instanceof PlayerMoveC2SPacket && mc.player.isOnGround() && mc.player.fallDistance <= 0.0
		    && !mc.interactionManager.isBreakingBlock()) {
		// mc.player.setOnGround(false);
		((PlayerMoveC2SPacketAccessor) packet).setOnGround(false);
	    }
	}

	if (Criticals.isToggled) {
	    if (packet instanceof PlayerInteractEntityC2SPacket) {
		PlayerInteractEntityC2SPacket packet1 = (PlayerInteractEntityC2SPacket) packet;
		if (PlayerInteractEntityC2SUtils.getInteractType(packet1) == InteractType.ATTACK
			&& PlayerInteractEntityC2SUtils.getEntity(packet1) instanceof LivingEntity) {
		    //System.out.print("Hey");
		    Criticals.sendCritPackets();
		}
	    }
	}

	if (Phase.isToggled) {
	    if (packet instanceof PlayerMoveC2SPacket && !(packet instanceof PlayerMoveC2SPacket.PositionAndOnGround)) {
		callback.cancel();
	    }
	}

	if (PacketCancellor.isToggled) {
	    callback.cancel();
	}
    }
}
