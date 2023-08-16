package autopartsclient.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;

public class TPSutil {

	public static TPSutil INSTANCE = new TPSutil();
	private static MinecraftClient mc = MinecraftClient.getInstance();
    static List<Long> reports = new ArrayList<>();
    static long timeLastTimeUpdate = -1;
    static long timeGameJoined;

    public static double getTPS(int averageOfSeconds) {
        if (reports.size() < 2) {
            return 20.0; // we can't compare yet
        }

        long currentTimeMS = reports.get(reports.size() - 1);
        long previousTimeMS = reports.get(reports.size() - averageOfSeconds);

        // on average, how long did it take for 20 ticks to execute? (ideal value: 1 second)
        double longTickTime = Math.max((currentTimeMS - previousTimeMS) / (1000.0 * (averageOfSeconds - 1)), 1.0);
        return 20 / longTickTime;
    }

    public static double getAverageTPS() {
       return getTPS(reports.size());
    }
    
    
    public static void run(Packet<?> packet) {
        if (mc.player == null || mc.world == null || mc.player.age < 20) {
            reports.clear();
        }
        if (packet instanceof WorldTimeUpdateS2CPacket) {
            reports.add(System.currentTimeMillis());
        }
        if (packet instanceof WorldTimeUpdateS2CPacket) {
        	timeLastTimeUpdate = System.currentTimeMillis();
        }
    }
    
    public static void joinGame() {
    	timeLastTimeUpdate = -1;
        timeGameJoined = System.currentTimeMillis();
    }
    
    public static float getTimeSinceLastTick() {
        if (System.currentTimeMillis() - timeGameJoined < 4000) return 0;
        return (System.currentTimeMillis() - timeLastTimeUpdate) / 1000f;
    }
}