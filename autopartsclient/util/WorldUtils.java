package autopartsclient.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.chunk.WorldChunk;

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
}
