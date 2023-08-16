package autopartsclient.util.Player;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * The interface for the player entity.
 *
 * @author gt3ch1
 * @version 12/31/2022
 */
public interface IClientPlayerEntity {

    /**
     * Whether the player is on the ground.
     *
     * @return Whether the player is on the ground.
     */
    boolean isOnGround();

    /**
     * Sets the player's pitch.
     *
     * @param pitch - The pitch to set.
     */
    void setPitch(float pitch);

    /**
     * Sets the player's yaw.
     *
     * @param yaw - The yaw to set.
     */
    void setYaw(float yaw);

    /**
     * Gets the current position of the player.
     *
     * @return The current position of the player.
     */
    Vec3d getPos();

    /**
     * Gets the previous x position of the player.
     *
     * @return The previous x position of the player.
     */
    double getPrevX();

    /**
     * Gets the previous y position of the player.
     *
     * @return The previous y position of the player.
     */
    double getPrevY();

    /**
     * Gets the previous z position of the player.
     *
     * @return The previous z position of the player.
     */
    double getPrevZ();

    /**
     * Gets the eye height of the player.
     *
     * @return The eye height of the player.
     */
    double getEyeHeight();

    /**
     * Gets the eye height of the player in the given pose.
     *
     * @param pose - The pose to get the eye height for.
     * @return The eye height of the player in the given pose.
     */
    float getEyeHeight(EntityPose pose);

    /**
     * Gets the player's current pose.
     *
     * @return The player's current pose.
     */
    EntityPose getPose();

    /**
     * Gets the Y coordinate.
     *
     * @return The Y coordinate.
     */
    double getY();

    /**
     * Gets the Z coordinate.
     *
     * @return The Z coordinate.
     */
    double getZ();

    /**
     * Gets the head yaw of the player.
     *
     * @return The head yaw of the player.
     */
    float getHeadYaw();

    /**
     * Gets the body yaw of the player.
     *
     * @return The body yaw of the player.
     */
    float getBodyYaw();

    /**
     * Gets the game profile of the player.
     *
     * @return The game profile of the player.
     */
    GameProfile getGameProfile();

    /**
     * Whether the player can currently no-clip.
     *
     * @return Whether the player can currently no-clip.
     */
    boolean isNoClip();

    /**
     * Get the current attack cool down progress.
     *
     * @param f - The partial ticks.
     * @return The current attack cool down progress.
     */
    float getAttackCoolDownProgress(float f);

    /**
     * Trys to attack the given entity.
     *
     * @param e - The entity to attack.
     * @return Whether the attack was successful.
     */
    boolean tryAttack(Entity e);

    /**
     * Swings the given hand.
     *
     * @param h - The hand to swing.
     */
    void swingHand(Hand h);

    /**
     * Gets the player's abilities.
     *
     * @return The player's abilities.
     */
    PlayerAbilities getAbilities();

    /**
     * Gets the square distance to the given entity.
     *
     * @param e - The entity to get the distance to.
     * @return The square distance to the given entity.
     */
    double squaredDistanceTo(Entity e);

    /**
     * Whether the player is fall flying.
     *
     * @return Whether the player is fall flying.
     */
    boolean isFallFlying();

    /**
     * The velocity of the player.
     *
     * @return The velocity of the player.
     */
    Vec3d getVelocity();

    /**
     * Gets the fall distance of the player.
     *
     * @return The fall distance of the player.
     */
    double getFallDistance();

    /**
     * Whether the player is sneaking.
     *
     * @return Whether the player is sneaking.
     */
    boolean isSneaking();

    /**
     * Gets the network handler for the player.
     *
     * @return The network handler for the player.
     */
    ClientPlayNetworkHandler getNetworkHandler();

    /**
     * Sets the player's position.
     *
     * @param x - The x coordinate.
     * @param y - The y coordinate.
     * @param z - The z coordinate.
     */
    void setPos(double x, double y, double z);

    /**
     * Sends a chat message.
     *
     * @param message   - The message to send.
     * @param actionBar - Whether the message should be sent as an action bar message.
     */
    void sendMessage(Text message, boolean actionBar);

    /**
     * Get the block position of the player.
     *
     * @return The block position of the player.
     */
    BlockPos getBlockPos();

    /**
     * Gets the item in the main hand.
     *
     * @return The item in the main hand.
     */
    ItemStack getMainHandStack();

    /**
     * Whether the player is currently submerged in water.
     *
     * @return Whether the player is currently submerged in water.
     */
    boolean isSubmergedInWater();
}