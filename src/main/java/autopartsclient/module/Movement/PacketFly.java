package autopartsclient.module.Movement;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.NumberSetting;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import java.lang.*;

public class PacketFly extends Mod {
    public static boolean isToggled;

    public static double speed;

    private static final double MAGIC_OFFSET = .200009968835369999878673424677777777777761;

    private static NumberSetting speedSetting = new NumberSetting("Speed", 0.1, 5, 1, 0.1);

    public PacketFly() {
	super("PacketFly", "", Category.MOVEMENT);
	addSetting(speedSetting);
	// this.setBinding(GLFW.key);
    }

    @Override
    public void onEnable() {
	// TODO Auto-generated method stub
	isToggled = true;

	mc.player.noClip = true;
    }

    @Override
    public void onDisable() {
	// TODO Auto-generated method stub
	isToggled = false;
	mc.player.noClip = false;
    }

    @Override
    public void onTick() {
	
	speed = speedSetting.getValue();
	// TODO Auto-generated method stub

	if (mc.world == null)
	    return;
	Direction f = mc.player.getHorizontalFacing();
	Box bb = mc.player.getBoundingBox();
	Vec3d center = bb.getCenter();
	Vec3d offset = new Vec3d(f.getUnitVector());

	Vec3d fin = merge(Vec3d.of(BlockPos.ofFloored(center)).add(.5, 0, .5).add(offset.multiply(MAGIC_OFFSET)), f);
	mc.player.setPosition(fin.x == 0 ? mc.player.getX() : fin.x, mc.player.getY(),
		fin.z == 0 ? mc.player.getZ() : fin.z);

	super.onTick();
    }
    
    private Vec3d merge(Vec3d a, Direction facing) {
        return new Vec3d(a.x * Math.abs(facing.getUnitVector().x()), a.y * Math.abs(facing.getUnitVector().y()), a.z * Math.abs(facing.getUnitVector().z()));
    }
    /*
     * private boolean checkHitBoxes() { return
     * !(mc.world.getBlockCollisions(mc.player,
     * mc.player.getBoundingBox().expand(-0.0625,-0.0625,-0.0625)).count() == 0); }
     */
}
