package autopartsclient.ui;

import java.awt.Color;
import java.util.Comparator;
import java.util.List;

import autopartsclient.module.Mod;
import autopartsclient.module.ModuleManager;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class HUD {
	private static MinecraftClient mc = MinecraftClient.getInstance();

	public static boolean DoWelcome = true;

	public static void Render(MatrixStack matrices, float tickDelta) {
		if (DoWelcome == true) {
			var username = MinecraftClient.getInstance().getSession().getUsername();
			ChatUtils.message("Welcome Back " + username + "!");
			DoWelcome = false;
		}
		mc.textRenderer.drawWithShadow(matrices, "Auto Parts - 1.0", 5, 5, Color.GREEN.getRGB());
		renderArrayList(matrices);
	}

	public static void renderArrayList(MatrixStack matrices) {

		int index = 0;

		int sWidth = mc.getWindow().getScaledWidth();
		int sHeight = mc.getWindow().getScaledHeight();

		List<Mod> enabled = ModuleManager.INSTANCE.getEnabledModules();

		enabled.sort(Comparator.comparingInt(n -> mc.textRenderer.getWidth(((Mod) n).getDisplayName())).reversed());

		for (Mod mod : enabled) {
			
			mc.textRenderer.drawWithShadow(matrices, mod.getDisplayName(),
					(sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()),
					10 + index * mc.textRenderer.fontHeight, Color.YELLOW.getRGB());
			index++;
			
		}
	}
}
