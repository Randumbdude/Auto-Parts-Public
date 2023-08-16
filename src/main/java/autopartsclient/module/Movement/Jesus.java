package autopartsclient.module.Movement;

import autopartsclient.module.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Jesus extends Mod {
    public static boolean isToggled;

    public Jesus() {
	super("Jesus", "", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
	// TODO Auto-generated method stub
	isToggled = true;
	super.onEnable();
    }

    @Override
    public void onDisable() {
	// TODO Auto-generated method stub
	isToggled = false;
	super.onDisable();
    }

    @Override
    public void onTick() {
	Entity e = mc.player.getRootVehicle();

	if (e.isSneaking() || e.fallDistance > 3f)
	    return;

	if (isSubmerged(e.getPos().add(0, 0.3, 0))) {
	    e.setVelocity(e.getVelocity().x, 0.08, e.getVelocity().z);
	} else if (isSubmerged(e.getPos().add(0, 0.1, 0))) {
	    e.setVelocity(e.getVelocity().x, 0.05, e.getVelocity().z);
	} else if (isSubmerged(e.getPos().add(0, 0.05, 0))) {
	    e.setVelocity(e.getVelocity().x, 0.01, e.getVelocity().z);
	} else if (isSubmerged(e.getPos())) {
	    e.setVelocity(e.getVelocity().x, -0.005, e.getVelocity().z);
	    e.setOnGround(true);
	}
    }

    private boolean isSubmerged(Vec3d pos) {
	BlockPos bp = BlockPos.ofFloored(pos);
	FluidState state = mc.world.getFluidState(bp);

	return !state.isEmpty() && pos.y - bp.getY() <= state.getHeight();
    }
}
