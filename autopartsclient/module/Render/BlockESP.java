package autopartsclient.module.Render;

import org.apache.logging.log4j.LogManager;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.WorldUtils;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class BlockESP extends Mod {	
	org.apache.logging.log4j.Logger logger = LogManager.getLogger(BlockESP.class);
	
	public BooleanSetting Chests = new BooleanSetting("Chests", true);
	public BooleanSetting Furnaces = new BooleanSetting("Furnaces", true);
	public BooleanSetting Shulkers = new BooleanSetting("Shulkers", true);
	public BooleanSetting Spawners = new BooleanSetting("Spawners", true);

	public BlockESP() {
		super("BlockESP", "", Category.RENDER);
		addSetting(Chests, Furnaces, Shulkers, Spawners);
	}

	@Override
	public void render(MatrixStack matrices, float partialTicks) {
		ColorUtil ChestColor = new ColorUtil(255,0,0);
		ColorUtil FurnaceColor = new ColorUtil(3, 48, 252);
		ColorUtil ShulkerColor = new ColorUtil(255, 0, 255);
		ColorUtil EnderChest = new ColorUtil(195, 0, 255);
		ColorUtil SpawnerColor = new ColorUtil(252, 136, 30);
	
		
		for (BlockEntity be: WorldUtils.getBlockEntities()) {
			if (be instanceof FurnaceBlockEntity && Furnaces.isEnabled()) {
				
				Vec3d blockPosition = new Vec3d(be.getPos().getX(),be.getPos().getY(),be.getPos().getZ());
				
				RenderUtils.drawBlockBox(matrices, blockPosition, FurnaceColor);
			}
			if (be instanceof ChestBlockEntity && Chests.isEnabled()) {
				
				Vec3d blockPosition = new Vec3d(be.getPos().getX(),be.getPos().getY(),be.getPos().getZ());
				
				RenderUtils.drawBlockBox(matrices, blockPosition, ChestColor);
			}
			if (be instanceof EnderChestBlockEntity && Chests.isEnabled()) {
				
				Vec3d blockPosition = new Vec3d(be.getPos().getX(),be.getPos().getY(),be.getPos().getZ());
				
				RenderUtils.drawBlockBox(matrices, blockPosition, EnderChest);
			}
			if (be instanceof ShulkerBoxBlockEntity && Shulkers.isEnabled()) {
				
				Vec3d blockPosition = new Vec3d(be.getPos().getX(),be.getPos().getY(),be.getPos().getZ());
				
				RenderUtils.drawBlockBox(matrices, blockPosition, ShulkerColor);
			}
			if (be instanceof MobSpawnerBlockEntity && Chests.isEnabled()) {
				
				Vec3d blockPosition = new Vec3d(be.getPos().getX(),be.getPos().getY(),be.getPos().getZ());
				
				RenderUtils.drawBlockBox(matrices, blockPosition, SpawnerColor);
			}
		}
		
	}	
}
	
	