package autopartsclient.util.FreecamUtils;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;

public class FreeCamera extends ClientPlayerEntity {
	
	static MinecraftClient mc = MinecraftClient.getInstance();

    private static final ClientPlayNetworkHandler NETWORK_HANDLER = new ClientPlayNetworkHandler(mc, mc.currentScreen, mc.getNetworkHandler().getConnection(), mc.getCurrentServerEntry(), new GameProfile(UUID.randomUUID(), "FreeCamera"), mc.getTelemetryManager().createWorldSession(false, null)) {
        @Override
        public void sendPacket(Packet<?> packet) {
        }
    };

    public FreeCamera(int id) {
        this(id, new FreecamPosition(mc.player));
    }

    public FreeCamera(int id, FreecamPosition position) {
        super(mc, mc.world, NETWORK_HANDLER, mc.player.getStatHandler(), mc.player.getRecipeBook(), false, false);

        setId(id);
        refreshPositionAndAngles(position.x, position.y, position.z, position.yaw, position.pitch);
        super.setPose(position.pose);
        renderPitch = getPitch();
        renderYaw = getYaw();
        lastRenderPitch = renderPitch; // Prevents camera from rotating upon entering freecam.
        lastRenderYaw = renderYaw;
        getAbilities().flying = true;
        input = new KeyboardInput(mc.options);
    }

    public void spawn() {
        if (clientWorld != null) {
            clientWorld.addEntity(getId(), this);
        }
    }

    public void despawn() {
        if (clientWorld != null && clientWorld.getEntityById(getId()) != null) {
            clientWorld.removeEntity(getId(), RemovalReason.DISCARDED);
        }
    }

    // Prevents fall damage sound when FreeCamera touches ground with noClip disabled.
    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    // Needed for hand swings to be shown in freecam since the player is replaced by FreeCamera in HeldItemRenderer.renderItem()
    @Override
    public float getHandSwingProgress(float tickDelta) {
        return mc.player.getHandSwingProgress(tickDelta);
    }

    // Needed for item use animations to be shown in freecam since the player is replaced by FreeCamera in HeldItemRenderer.renderItem()
    @Override
    public int getItemUseTimeLeft() {
        return mc.player.getItemUseTimeLeft();
    }

    // Also needed for item use animations to be shown in freecam.
    @Override
    public boolean isUsingItem() {
        return mc.player.isUsingItem();
    }

    // Prevents slow down from ladders/vines.
    @Override
    public boolean isClimbing() {
        return false;
    }

    // Prevents slow down from water.
    @Override
    public boolean isTouchingWater() {
        return false;
    }

    // Makes night vision apply to FreeCamera when Iris is enabled.
    @Override
    public StatusEffectInstance getStatusEffect(StatusEffect effect) {
        return mc.player.getStatusEffect(effect);
    }

    // Prevents pistons from moving FreeCamera when noClip is enabled.
    @Override
    public PistonBehavior getPistonBehavior() {
        return true ? PistonBehavior.IGNORE : PistonBehavior.NORMAL;
    }

    // Prevents pose from changing when clipping through blocks.
    @Override
    public void setPose(EntityPose pose) {
        if (pose.equals(EntityPose.STANDING) || (pose.equals(EntityPose.CROUCHING) && !getPose().equals(EntityPose.STANDING))) {
            super.setPose(pose);
        }
    }

    @Override
    public void tickMovement() {
        noClip = true;
        if (true) {
            getAbilities().setFlySpeed(0);
            Motion.doMotion(this, 1, 1);
        } else {
            getAbilities().setFlySpeed(1);
        }
        super.tickMovement();
        getAbilities().flying = true;
        onGround = false;
    }
}