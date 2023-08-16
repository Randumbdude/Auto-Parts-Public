package autopartsclient.mixins;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import autopartsclient.module.Mod;
import autopartsclient.module.ModuleManager;
import autopartsclient.module.Misc.TestModule;
import autopartsclient.module.Movement.PacketFly;
import autopartsclient.module.Movement.Phase;
import autopartsclient.module.Player.AntiKB;
import autopartsclient.module.Player.AntiLev;
import autopartsclient.module.Player.HClip;
import autopartsclient.module.Player.InvMove;
import autopartsclient.module.Player.NoSlow;
import autopartsclient.ui.screens.BindScreen;
import autopartsclient.ui.screens.clickgui.ClickGUI;
import autopartsclient.util.Player.IClientPlayerEntity;
import autopartsclient.util.Player.PlayerUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow
    public float nextNauseaStrength;
    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;
    @Shadow
    public float lastNauseaStrength;

    public Logger logger = LogManager.getLogger(ClientPlayerEntityMixin.class);

    MinecraftClient mc = MinecraftClient.getInstance();

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
	super(world, profile);
	// TODO Auto-generated constructor stub
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0), method = "tickMovement()V")
    private boolean IsUsingItem(ClientPlayerEntity player) {
	if (NoSlow.isToggled)
	    return false;

	return player.isUsingItem();
    }

    @Override
    public boolean hasStatusEffect(StatusEffect effect) {
	if (effect == StatusEffects.LEVITATION && AntiLev.isToggled)
	    return false;

	return super.hasStatusEffect(effect);
    }

    @Inject(method = "pushOutOfBlocks", at = @At("HEAD"), cancellable = true)
    public void pushOut(double x, double y, CallbackInfo ci) {
	if (HClip.isToggled || Phase.isToggled || PacketFly.isToggled) {
	    ci.cancel();
	}

	/*
	 * EventPushOutOfBlocks event = new EventPushOutOfBlocks(); event.call(); if
	 * (event.isCancelled() == true) { logger.info("cancelled"); ci.cancel(); }
	 */
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
	if (AntiKB.isToggled) {
	    // mc.player.setVelocity(0, 0, 0);
	}
    }

    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void move(MovementType type, Vec3d movement, CallbackInfo info) {
	if (PacketFly.isToggled) {
	    double movespeed = PacketFly.speed;
	    
	    mc.world.getBlockCollisions(mc.player, mc.player.getBoundingBox().expand(-0.0625,-0.0625,-0.0625));
	    mc.player.getBoundingBox().expand(-0.0625,-0.0625,-0.0625);
	    info.cancel();
	    
	    
	    final double[] speed = PlayerUtils.directionSpeed(movespeed);

	    if (mc.options.forwardKey.isPressed() || mc.options.backKey.isPressed() || mc.options.leftKey.isPressed()
		    || mc.options.rightKey.isPressed()) {

		 mc.getNetworkHandler().sendPacket(new
		 PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX() + speed[0],
		 mc.player.getY(), mc.player.getZ() + speed[1], mc.player.isOnGround()));

		PlayerUtils.teleport(
			new Vec3d(mc.player.getX() + speed[0], mc.player.getY(), mc.player.getZ() + speed[1]));

		// mc.getNetworkHandler().sendPacket(new
		// PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), 0,
		// mc.player.getZ(), mc.player.isOnGround()));

	    }
	    if (mc.options.sneakKey.isPressed()) {
		/*
		 * mc.getNetworkHandler().sendPacket(new
		 * PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() -
		 * 1, mc.player.getZ(), mc.player.isOnGround()));
		 * mc.getNetworkHandler().sendPacket(new
		 * PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), 0,
		 * mc.player.getZ(), mc.player.isOnGround()));
		 */
		PlayerUtils.teleport(
			new Vec3d(mc.player.getX() + speed[0], mc.player.getY()-movespeed, mc.player.getZ() + speed[1]));
	    }

	    if (mc.options.jumpKey.isPressed()) {
		/*
		 * mc.getNetworkHandler().sendPacket(new
		 * PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() +
		 * 1, mc.player.getZ(), mc.player.isOnGround()));
		 * mc.getNetworkHandler().sendPacket(new
		 * PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), 0,
		 * mc.player.getZ(), mc.player.isOnGround()));
		 */
		PlayerUtils.teleport(
			new Vec3d(mc.player.getX() + speed[0], mc.player.getY()+movespeed, mc.player.getZ() + speed[1]));
	    }
	}

	if (Phase.isToggled) {
	    if (Phase.Mode.getMode() == "Blink") {
		mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
	    }
	}
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
	for (Mod mod : ModuleManager.INSTANCE.getModules()) {
	    if (mc.player != null) {
		if (mod.isBinding() && mc.currentScreen == null) {
		    try {
			mc.setScreen(new BindScreen(mod, null));
			// mod.setBinding(false);
		    } catch (Exception e) {

		    }
		}
	    }
	}
    }
}
