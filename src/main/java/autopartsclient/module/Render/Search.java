package autopartsclient.module.Render;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.systems.RenderSystem;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.NumberSetting;
import autopartsclient.util.ColorUtil;
import autopartsclient.util.WorldUtils;
import autopartsclient.util.Render.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.UnloadChunkS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.chunk.Chunk;

public class Search extends Mod {

    public Set<Block> blocks = new HashSet<>();
    private static Set<BlockPos> foundBlocks = new HashSet<>();

    private static ExecutorService chunkSearchers = Executors.newFixedThreadPool(4);
    private static Map<ChunkPos, Future<Set<BlockPos>>> chunkFutures = new HashMap<>();

    private static Queue<ChunkPos> queuedChunks = new ArrayDeque<>();
    private static Queue<ChunkPos> queuedUnloads = new ArrayDeque<>();
    private static Queue<Pair<BlockPos, BlockState>> queuedBlocks = new ArrayDeque<>();

    private static Set<Block> prevBlockList = new HashSet<>();

    private int oldViewDistance = -1;

    public BooleanSetting Tracers = new BooleanSetting("Tracers", true);

    public BooleanSetting EndStuff = new BooleanSetting("EndStuff", true);
    
    public NumberSetting BlockHue = new NumberSetting("Hue", 1, 360, 300, 1);

    public Search() {
	super("Search", "", Category.RENDER);
	addSetting(Tracers, BlockHue);
	blocks.add(Blocks.NETHER_PORTAL);
	blocks.add(Blocks.SHULKER_BOX);
	blocks.add(Blocks.BARRIER);
	blocks.add(Blocks.COMMAND_BLOCK);
	blocks.add(Blocks.CHAIN_COMMAND_BLOCK);
	blocks.add(Blocks.REPEATING_COMMAND_BLOCK);

	if (EndStuff.isEnabled()) {
	    blocks.add(Blocks.END_GATEWAY);
	    blocks.add(Blocks.END_PORTAL);
	    blocks.add(Blocks.END_PORTAL_FRAME);
	}
    }

    @Override
    public void onTick() {
	
	try {
	    Set<Block> blockList = (Set<Block>) blocks;

	    if (!prevBlockList.equals(blockList) || oldViewDistance != mc.options.getViewDistance().getValue()) {
		reset();

		for (Chunk chunk : WorldUtils.getLoadedChunks()) {
		    submitChunk(chunk.getPos(), chunk);
		}

		prevBlockList = new HashSet<>(blockList);
		oldViewDistance = mc.options.getViewDistance().getValue();
		return;
	    }

	    while (!queuedBlocks.isEmpty()) {
		Pair<BlockPos, BlockState> blockPair = queuedBlocks.poll();

		if (blocks.contains(blockPair.getRight().getBlock())) {
		    foundBlocks.add(blockPair.getLeft());
		} else {
		    foundBlocks.remove(blockPair.getLeft());
		}
	    }

	    while (!queuedUnloads.isEmpty()) {
		ChunkPos chunkPos = queuedUnloads.poll();
		queuedChunks.remove(chunkPos);

		for (BlockPos pos : new HashSet<>(foundBlocks)) {
		    if (pos.getX() >= chunkPos.getStartX() && pos.getX() <= chunkPos.getEndX()
			    && pos.getZ() >= chunkPos.getStartZ() && pos.getZ() <= chunkPos.getEndZ()) {
			foundBlocks.remove(pos);
		    }
		}
	    }

	    while (!queuedChunks.isEmpty()) {
		submitChunk(queuedChunks.poll());
	    }

	    for (Entry<ChunkPos, Future<Set<BlockPos>>> f : new HashMap<>(chunkFutures).entrySet()) {
		if (f.getValue().isDone()) {
		    try {
			foundBlocks.addAll(f.getValue().get());

			chunkFutures.remove(f.getKey());
		    } catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		    }
		}
	    }
	} catch (Exception e) {
	    System.out.println("error");
	    e.printStackTrace();
	}
    }

    @Override
    public void render(MatrixStack matrixStack, float partialTicks) {
	ColorUtil Color = new ColorUtil(BlockHue.getValueFloat());
	
	for (BlockPos pos : foundBlocks) {
	    BlockState state = mc.world.getBlockState(pos);
	    VoxelShape voxelShape = state.getOutlineShape(mc.world, pos);
	    if (voxelShape.isEmpty()) {
		voxelShape = VoxelShapes.cuboid(0, 0, 0, 1, 1, 1);
	    }
	    RenderSystem.setShaderColor(195, 0, 255, 1);
	    // RenderUtils.line(RenderUtils.center(), new Vec3d(pos.getX() + 0.5, pos.getY()
	    // + 0.5, pos.getZ() + 0.5),Color.CYAN, event.getMatrices());
	    RenderUtils.drawBlockBox(matrixStack, new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
		    new ColorUtil(195, 0, 255));
	    if (Tracers.isEnabled()) {
		if (Freecam.isToggled) {
		    RenderUtils.drawLine3D(matrixStack, RenderUtils.freecamLookVec(),
			    new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
			    Color);

		} else {
		    RenderUtils.drawLine3D(matrixStack, RenderUtils.getClientLookVec(),
			    new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5),
			    Color);
		}
	    }
	}
    }

    @Override
    public void onDisable() {
	reset();
	super.onDisable();
    }

    private void submitChunk(ChunkPos pos) {
	submitChunk(pos, mc.world.getChunk(pos.x, pos.z));
    }

    private void submitChunk(ChunkPos pos, Chunk chunk) {
	chunkFutures.put(chunk.getPos(), chunkSearchers.submit(new Callable<Set<BlockPos>>() {

	    @Override
	    public Set<BlockPos> call() {
		Set<BlockPos> found = new HashSet<>();

		for (int x = 0; x < 16; x++) {
		    for (int y = mc.world.getBottomY(); y <= mc.world.getTopY(); y++) {
			for (int z = 0; z < 16; z++) {
			    BlockPos pos = new BlockPos(chunk.getPos().x * 16 + x, y, chunk.getPos().z * 16 + z);
			    BlockState state = chunk.getBlockState(pos);

			    if (blocks.contains(state.getBlock())) {
				found.add(pos);
			    }
			}
		    }
		}

		return found;
	    }
	}));
    }

    private static void reset() {
	chunkSearchers.shutdownNow();
	chunkSearchers = Executors.newFixedThreadPool(4);

	chunkFutures.clear();
	foundBlocks.clear();
	queuedChunks.clear();
	queuedUnloads.clear();
	prevBlockList.clear();
    }

    public static void receivePacket(Packet<?> packet1) {
	if (packet1 instanceof DisconnectS2CPacket || packet1 instanceof GameJoinS2CPacket
		|| packet1 instanceof PlayerRespawnS2CPacket) {
	    reset();
	} else if (packet1 instanceof BlockUpdateS2CPacket) {
	    BlockUpdateS2CPacket packet = (BlockUpdateS2CPacket) packet1;

	    queuedBlocks.add(Pair.of(packet.getPos(), packet.getState()));
	} else if (packet1 instanceof ExplosionS2CPacket) {
	    ExplosionS2CPacket packet = (ExplosionS2CPacket) packet1;

	    for (BlockPos pos : packet.getAffectedBlocks()) {
		queuedBlocks.add(Pair.of(pos, Blocks.AIR.getDefaultState()));
	    }
	} else if (packet1 instanceof ChunkDeltaUpdateS2CPacket) {
	    ChunkDeltaUpdateS2CPacket packet = (ChunkDeltaUpdateS2CPacket) packet1;

	    packet.visitUpdates((pos, state) -> queuedBlocks.add(Pair.of(pos.toImmutable(), state)));
	} else if (packet1 instanceof ChunkDataS2CPacket) {
	    ChunkDataS2CPacket packet = (ChunkDataS2CPacket) packet1;

	    ChunkPos cp = new ChunkPos(packet.getX(), packet.getZ());
	    queuedChunks.add(cp);
	    queuedUnloads.remove(cp);
	} else if (packet1 instanceof UnloadChunkS2CPacket) {
	    UnloadChunkS2CPacket packet = (UnloadChunkS2CPacket) packet1;

	    queuedUnloads.add(new ChunkPos(packet.getX(), packet.getZ()));
	}
    }
}
