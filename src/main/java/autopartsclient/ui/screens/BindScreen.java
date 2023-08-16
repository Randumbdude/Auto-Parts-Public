package autopartsclient.ui.screens;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class BindScreen extends Screen {
    private MinecraftClient mc = MinecraftClient.getInstance();

    private Mod mod;
    private Screen prevScreen;

    public BindScreen(Mod mod, Screen prevScreen) {
	super(Text.literal("BindingScreen"));
	this.mod = mod;
	this.prevScreen = prevScreen;
    }

    @Override
    protected void init() {
	super.init();
	// this.addDrawableChild(new ButtonWidget(this.width / 2 - 50, height / 2 + 100,
	// 100, 20, Text.literal("Back"), (button) -> {mod.setBinding(false);
	// MinecraftClient.getInstance().setScreen(prevScreen);}, null)).active = true;

	// this.addDrawableChild(new ButtonWidget(this.width / 2 - 50, height / 2 + 80,
	// 100, 20, Text.literal("Re-bind"), (button) -> {mod.setBinding(true);},
	// null)).active = !mod.isBinding();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
	this.renderBackground(matrices);
	matrices.push();
	matrices.translate(width / 2, height / 2 - 100, 0);
	matrices.scale(2.5f, 2.5f, 0);

	Screen.drawCenteredTextWithShadow(matrices, textRenderer, "Binding " + mod.getName(), 0, 0, -1);
	matrices.pop();
	if (!mod.isBinding()) {
	    matrices.push();
	    matrices.translate(width / 2, height / 2 - 50, 0);
	    matrices.scale(1.5f, 1.5f, 0);
	    if (mod.getKey() != GLFW.GLFW_KEY_ESCAPE && mod.getKey() != GLFW.GLFW_KEY_UNKNOWN && mod.getKey() != 0) {
		try {
		    Screen.drawCenteredTextWithShadow(matrices, textRenderer, "Bound " + mod.getName() + " to "
			    + (GLFW.glfwGetKeyName(mod.getKey(), GLFW.glfwGetKeyScancode(mod.getKey())).toUpperCase()),
			    0, 0, -1);
		} catch (Exception e) {
		    // TODO: handle exception
		}

	    } else {
		Screen.drawCenteredTextWithShadow(matrices, textRenderer, "Unbound " + mod.getName(), 0, 0, -1);
	    }
	    matrices.pop();
	}
	super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
	if (mod.isBinding()) {
	    // Set bind to key pressed or unbind if the key is escape
	    if (keyCode != GLFW.GLFW_KEY_ESCAPE && keyCode != GLFW.GLFW_KEY_UNKNOWN) {
		mod.setKey(keyCode);
		mod.setBinding(false);
	    } else {
		mod.setKey(0);
		mod.setBinding(false);

	    }
	} else {
	    if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
		mc.setScreen(null);
	    }
	}
	return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
	mod.setBinding(false);
	super.close();
    }

    @Override
    public boolean shouldCloseOnEsc() {
	return false;
    }
}