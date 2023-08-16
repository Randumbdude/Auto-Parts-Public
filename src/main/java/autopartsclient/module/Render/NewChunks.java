package autopartsclient.module.Render;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import autopartsclient.module.Mod;
import autopartsclient.module.ModuleManager;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.BlockUtils;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;

public class NewChunks extends Mod {
    private final static Set<ChunkPos> newChunks = Collections.synchronizedSet(new HashSet<>());
    private final static Set<ChunkPos> oldChunks = Collections.synchronizedSet(new HashSet<>());
    private final static Set<ChunkPos> dontCheckAgain = Collections.synchronizedSet(new HashSet<>());

    private final static Set<BlockPos> newChunkReasons = Collections.synchronizedSet(new HashSet<>());
    private final static Set<BlockPos> oldChunkReasons = Collections.synchronizedSet(new HashSet<>());

    public static boolean isToggled;

    private int yOffset = 0;

    public static NumberSetting drawHeight = new NumberSetting("Height", -64, 128, 0, 1);

    public static BooleanSetting showOldChunks = new BooleanSetting("OldChunks", true);
    public static BooleanSetting showReasons = new BooleanSetting("Reasons", false);

    public NewChunks() {
	super("NewChunks", "Shows new chunks and old chunks for base finding", Category.RENDER);
	addSetting(drawHeight, showOldChunks, showReasons);
    }

    @Override
    public void onEnable() {
	// TODO Auto-generated method stub
	isToggled = true;

	oldChunks.clear();
	newChunks.clear();
	dontCheckAgain.clear();
	oldChunkReasons.clear();
	newChunkReasons.clear();

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
	yOffset = drawHeight.getValueInt();
    }

    @Override
    public void render(MatrixStack matrixStack, float partialTicks) {
	int renderY = (int) (mc.world.getBottomY() + yOffset);

	int color = Color.RED.getRGB();
	// QuadColor outlineColor = QuadColor.single(0xff000000 | color);

	synchronized (newChunks) {
	    for (ChunkPos c : newChunks) {
		if (mc.getCameraEntity().getBlockPos().isWithinDistance(c.getStartPos(), 1024)) {

		    Vec3d start = new Vec3d(c.getStartX(), renderY, c.getStartZ());
		    Vec3d end = new Vec3d(c.getStartX() + 16, renderY, c.getStartZ() + 16);

		    RenderUtils.drawNewChunks(matrixStack, start, end, new ColorUtil(255, 0, 0));
		}
	    }
	}

	if (showOldChunks.isEnabled()) {
	    synchronized (oldChunks) {
		for (ChunkPos c : oldChunks) {
		    if (mc.getCameraEntity().getBlockPos().isWithinDistance(c.getStartPos(), 1024)) {

			Vec3d start = new Vec3d(c.getStartX(), renderY, c.getStartZ());
			Vec3d end = new Vec3d(c.getStartX() + 16, renderY, c.getStartZ() + 16);

			RenderUtils.drawNewChunks(matrixStack, start, end, new ColorUtil(0, 0, 255));
		    }
		}
	    }
	}

	if (showReasons.isEnabled()) {
	    synchronized (newChunkReasons) {
		for (BlockPos c : newChunkReasons) {
		    if (mc.getCameraEntity().getBlockPos().isWithinDistance(c.toCenterPos(), 1024)) {

			Vec3d start = new Vec3d(c.getX(), renderY, c.getZ());

			RenderUtils.drawBlockBox(matrixStack, start, new ColorUtil(255, 0, 0));
		    }
		}
	    }

	    synchronized (oldChunkReasons) {
		for (BlockPos c : oldChunkReasons) {
		    if (mc.getCameraEntity().getBlockPos().isWithinDistance(c.toCenterPos(), 1024)) {

			Vec3d start = new Vec3d(c.getX(), renderY, c.getZ());
			Vec3d end = new Vec3d(c.getX() + 16, renderY, c.getZ() + 16);

			RenderUtils.drawBlockBox(matrixStack, start, new ColorUtil(0, 0, 255));
		    }
		}
	    }
	}
    }

    public static void afterLoadChunk(int x, int z) {
	if (!isToggled)
	    return;

	WorldChunk chunk = mc.world.getChunk(x, z);
	new Thread(() -> checkLoadedChunk(chunk), "NewChunks " + chunk.getPos()).start();
    }

    private static void checkLoadedChunk(WorldChunk chunk) {
	ChunkPos chunkPos = chunk.getPos();
	if (newChunks.contains(chunkPos) || oldChunks.contains(chunkPos) || dontCheckAgain.contains(chunkPos))
	    return;

	int minX = chunkPos.getStartX();
	int minY = chunk.getBottomY();
	int minZ = chunkPos.getStartZ();
	int maxX = chunkPos.getEndX();
	int maxY = chunk.getHighestNonEmptySectionYOffset() + 16;
	int maxZ = chunkPos.getEndZ();

	for (int x = minX; x <= maxX; x++)
	    for (int y = minY; y <= maxY; y++)
		for (int z = minZ; z <= maxZ; z++) {
		    BlockPos pos = new BlockPos(x, y, z);
		    FluidState fluidState = chunk.getFluidState(pos);

		    if (fluidState.isEmpty() || fluidState.isStill())
			continue;

		    // Liquid always generates still, the flowing happens later
		    // through block updates. Therefore any chunk that contains
		    // flowing liquids from the start should be an old chunk.
		    oldChunks.add(chunkPos);
		    oldChunkReasons.add(pos);
		    /*
		     * if(logChunks.isChecked()) System.out.println("old chunk at " + chunkPos);
		     */
		    return;
		}

	// If the whole loop ran through without finding anything, make sure it
	// never runs again on that chunk, as that would be a huge waste of CPU
	// time.
	dontCheckAgain.add(chunkPos);
    }

    public static void afterUpdateBlock(BlockPos pos) {
	if (!isToggled)
	    return;

	// Liquid starts flowing -> probably a new chunk
	FluidState fluidState = BlockUtils.getState(pos).getFluidState();
	if (fluidState.isEmpty() || fluidState.isStill())
	    return;

	ChunkPos chunkPos = new ChunkPos(pos);
	if (newChunks.contains(chunkPos) || oldChunks.contains(chunkPos))
	    return;

	newChunks.add(chunkPos);
	newChunkReasons.add(pos);
	/*
	 * if(logChunks.isChecked()) System.out.println("new chunk at " + chunkPos);
	 */
    }
}
