package autopartsclient.mixins;

import com.mojang.authlib.GameProfile;

import autopartsclient.util.Player.IClientPlayerEntity;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author gt3ch1
 * @version 12/31/2022
 */
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin2 extends AbstractClientPlayerEntity implements IClientPlayerEntity {
    @Shadow
    public float nextNauseaStrength;
    @Shadow
    @Final
    public
    ClientPlayNetworkHandler networkHandler;
    @Shadow
    public float lastNauseaStrength;

    public ClientPlayerEntityMixin2(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Shadow
    public abstract void sendMessage(Text message, boolean overlay);

    public boolean isOnGround() {
        return super.isOnGround();
    }

    public void setPitch(float pitch) {
        super.setPitch(pitch);
    }

    public void setYaw(float yaw) {
        super.setYaw(yaw);
    }

    public Vec3d getPos() {
        return super.getPos();
    }

    @Override
    public double getPrevX() {
        return super.prevX;
    }

    @Override
    public double getPrevY() {
        return super.prevY;
    }

    @Override
    public double getPrevZ() {
        return super.prevZ;
    }

    @Override
    public double getEyeHeight() {
        return super.getStandingEyeHeight();
    }

    @Override
    public EntityPose getPose() {
        return super.getPose();
    }

    @Override
    public boolean isNoClip() {
        return super.noClip;
    }

    @Override
    public float getEyeHeight(EntityPose pose) {
        return super.getEyeHeight(pose);
    }

    @Override
    public float getAttackCoolDownProgress(float f) {
        return super.getAttackCooldownProgress(f);
    }

    @Override
    public boolean tryAttack(Entity target) {
        return super.tryAttack(target);
    }

    @Shadow
    public abstract void swingHand(Hand hand);

    @Override
    public PlayerAbilities getAbilities() {
        return super.getAbilities();
    }

    @Override
    public double getFallDistance() {
        return super.fallDistance;
    }

    @Override
    public ClientPlayNetworkHandler getNetworkHandler() {
        return networkHandler;
    }

    @Shadow
    public abstract boolean isSubmergedInWater();

    @Shadow
    public abstract boolean isSneaking();

    @Override
    public BlockPos getBlockPos() {
        return super.getBlockPos();
    }

    @Override
    public ItemStack getMainHandStack() {
        return super.getMainHandStack();
    }

    @Override
    public boolean isFallFlying() {
        return super.isFallFlying();
    }

    @Override
    public GameProfile getGameProfile() {
        return super.getGameProfile();
    }

    @Override
    public float getBodyYaw() {
        return super.bodyYaw;
    }

    @Override
    public float getHeadYaw() {
        return super.headYaw;
    }


    @Inject(method = "updateNausea", at = @At("HEAD"), cancellable = true)
    public void cancelNausea(CallbackInfo ci) {
    	/*
        if (Mods.getMod(Type.NO_NAUSEA).isActive()) {
            this.lastNauseaStrength = 0.0f;
            this.nextNauseaStrength = 0.0f;
            ci.cancel();
        }
        */
    }
}