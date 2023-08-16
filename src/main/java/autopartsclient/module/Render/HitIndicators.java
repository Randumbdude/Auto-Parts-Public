package autopartsclient.module.Render;

import autopartsclient.module.Mod;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class HitIndicators extends Mod {
    public static boolean isToggled;

    private static DamageSource attackSource;

    public HitIndicators() {
	super("HitIndicator", "", Category.RENDER);
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

    public static void updateAttacker(DamageSource damageSource) {
	    attackSource = damageSource;
    }

    @Override
    public void render(MatrixStack matrixStack, float partialTicks) {
	ColorUtil color = new ColorUtil(255, 0, 0);
	if (attackSource.getAttacker() instanceof LivingEntity && attackSource.getAttacker() != mc.player) {
	    
	    RenderUtils.draw3Dbox(matrixStack, attackSource.getAttacker().getBoundingBox().getCenter(),
		    attackSource.getAttacker().getBoundingBox().getCenter(), color,
		    attackSource.getAttacker().getHeight(), attackSource.getAttacker().getWidth());

	}
    }
}
