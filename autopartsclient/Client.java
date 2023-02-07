package autopartsclient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.ModuleManager;
import autopartsclient.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Client implements ModInitializer {

	public static final Client INSTANCE = new Client();

	public Logger logger = LogManager.getLogger(Client.class);

	private MinecraftClient mc = MinecraftClient.getInstance();

	@Override
	public void onInitialize() {
		logger.info("Auto Parts Client Launched");
		logger.info("Auto Parts Client By O_R1ley");
	}

	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			for (Mod module : ModuleManager.INSTANCE.getModules()) {
				if (key == module.getKey())
					module.toggle();
			}
			if (key == GLFW.GLFW_KEY_RIGHT_SHIFT)
				mc.setScreen(ClickGUI.INSTANCE);

		}
	}

	public void onTick() {
		if (mc.player != null) {
			for (Mod module : ModuleManager.INSTANCE.getEnabledModules()) {
				module.onTick();
			}
		}
	}
	
	public void render(MatrixStack matrices, float partialTicks) {
		if (mc.player != null) {
			for (Mod module : ModuleManager.INSTANCE.getEnabledModules()) {
				module.render(matrices,partialTicks);
			}
		}
	}
}
