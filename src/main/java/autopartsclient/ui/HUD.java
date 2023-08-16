package autopartsclient.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import autopartsclient.SharedVaribles;
import autopartsclient.module.Mod;
import autopartsclient.module.ModuleManager;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class HUD {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean DoWelcome = true;

    public static void Render(MatrixStack matrices, float tickDelta) {
	if (DoWelcome == true) {
	    var username = MinecraftClient.getInstance().getSession().getUsername();
	    ChatUtils.message("Welcome Back " + username + "!");
	    ChatUtils.message("Type '.help' to get a list of commands");
	    DoWelcome = false;
	}

	if (SharedVaribles.showArrayList) {
	    renderArrayList(matrices);
	}
    }

    public static void renderArrayList(MatrixStack matrices) {

	int index = 0;

	int sWidth = mc.getWindow().getScaledWidth();
	int sHeight = mc.getWindow().getScaledHeight();

	List<Mod> enabled = ModuleManager.INSTANCE.getEnabledModules();

	// enabled.sort(Comparator.comparingInt(n -> mc.textRenderer.getWidth(((Mod)
	// n).getDisplayName())));
	// Collections.sort((List<T>) enabled);
	// enabled.stream().map(s -> s.getDisplayName()).forEach(System.out::print);
	sort(enabled);

	for (Mod mod : enabled) {
	    if (mod.showInArray == true) {
		mc.textRenderer.drawWithShadow(matrices, ">" + mod.getDisplayName(), (5),
			16 + index * mc.textRenderer.fontHeight, Color.YELLOW.getRGB());
		index++;
	    }
	}
    }

    public static void sort(List<Mod> enabled) {

	enabled.sort((o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName()));
    }
}
