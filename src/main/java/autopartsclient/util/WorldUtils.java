package autopartsclient.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.spongepowered.include.com.google.common.base.Predicate;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

public class WorldUtils {
    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static List<WorldChunk> getLoadedChunks() {
	List<WorldChunk> chunks = new ArrayList<>();

	int viewDist = mc.options.getViewDistance().getValue();

	for (int x = -viewDist; x <= viewDist; x++) {
	    for (int z = -viewDist; z <= viewDist; z++) {
		WorldChunk chunk = mc.world.getChunkManager().getWorldChunk((int) mc.player.getX() / 16 + x,
			(int) mc.player.getZ() / 16 + z);

		if (chunk != null) {
		    chunks.add(chunk);
		}
	    }
	}

	return chunks;
    }

    public static List<BlockEntity> getBlockEntities() {
	List<BlockEntity> list = new ArrayList<>();
	for (WorldChunk chunk : getLoadedChunks())
	    list.addAll(chunk.getBlockEntities().values());
	return list;
    }

    public static List<BlockPos> getLoadedNetherPortals() {
	BlockPos pPos = mc.player.getBlockPos();
	List<BlockPos> blocks = new ArrayList<>();
	ServerWorld sw = null;

	int radius = Math.max(2, mc.options.getClampedViewDistance()) + 3;
	int diameter = radius * 2 + 1;

	ChunkPos center = mc.player.getChunkPos();
	ChunkPos min = new ChunkPos(center.x - radius, center.z - radius);
	ChunkPos max = new ChunkPos(center.x + radius, center.z + radius);

	ArrayList<ChunkPos> chunks = Stream.<ChunkPos>iterate(min, pos -> {
	    int x = pos.x;
	    int z = pos.z;

	    x++;
	    if (x > max.x) {
		x = min.x;
		z++;
	    }

	    return new ChunkPos(x, z);
	}).limit(diameter * diameter).filter(c -> mc.world.isChunkLoaded(c.x, c.z))
		.collect(Collectors.toCollection(ArrayList::new));

	if (mc.player.world.getRegistryKey() != null)
	    sw = mc.getServer().getWorld(mc.player.world.getRegistryKey());

	Predicate<RegistryEntry<PointOfInterestType>> predicate = (
		poi) -> poi.getKey().get() == PointOfInterestTypes.NETHER_PORTAL;

	for (ChunkPos chunk : chunks) {
	    ArrayList<PointOfInterest> poiList = sw.getPointOfInterestStorage()
		    .getInChunk(predicate, chunk, PointOfInterestStorage.OccupationStatus.ANY)
		    .collect(Collectors.toCollection(ArrayList::new));
	    for (PointOfInterest poi : poiList) {
		blocks.add(poi.getPos());
	    }
	}

	return blocks;

    }

    public static boolean doesBoxCollide(Box box) {
	for (int x = (int) Math.floor(box.minX); x < Math.ceil(box.maxX); x++) {
	    for (int y = (int) Math.floor(box.minY); y < Math.ceil(box.maxY); y++) {
		for (int z = (int) Math.floor(box.minZ); z < Math.ceil(box.maxZ); z++) {
		    int fx = x, fy = y, fz = z;
		    if (mc.world.getBlockState(new BlockPos(x, y, z)).getCollisionShape(mc.world, new BlockPos(x, y, z))
			    .getBoundingBoxes().stream().anyMatch(b -> b.offset(fx, fy, fz).intersects(box))) {
			return true;
		    }
		}
	    }
	}

	return false;
    }
}
