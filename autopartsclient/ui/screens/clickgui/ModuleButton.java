package autopartsclient.ui.screens.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.BooleanSetting;
import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.Setting;
import autopartsclient.ui.screens.clickgui.setting.CheckBox;
import autopartsclient.ui.screens.clickgui.setting.Component;
import autopartsclient.ui.screens.clickgui.setting.ModeBox;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ModuleButton {

	public Mod module;
	public Frame parent;
	public int offset;
	public List<Component> components;
	public boolean extended;

	public ModuleButton(Mod module, Frame parent, int offset) {
		this.module = module;
		this.parent = parent;
		this.offset = offset;
		this.components = new ArrayList<>();
		this.extended = false;

		int setOffset = parent.height;
		for (Setting setting : module.getSettings()) {
			if (setting instanceof BooleanSetting) {
				components.add(new CheckBox(setting, this, setOffset));
			} else if (setting instanceof ModeSetting) {
				components.add(new ModeBox(setting, this, setOffset));
			}
			setOffset += parent.height;
		}
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width,
				parent.y + offset + parent.height, new Color(0, 0, 0, 160).getRGB());
		if (isHovered(mouseX, mouseY))
			DrawableHelper.fill(matrices, parent.x, parent.y + offset, parent.x + parent.width,
					parent.y + offset + parent.height, new Color(0, 0, 0, 160).getRGB());
		int textOffset = ((parent.height / 2) - parent.mc.textRenderer.fontHeight / 2);
		parent.mc.textRenderer.drawWithShadow(matrices, module.getName(), parent.x + textOffset,
				parent.y + offset + textOffset, module.isEnabled() ? Color.GREEN.getRGB() : -1);

		if (extended) {
			for (Component component : components) {
				component.render(matrices, mouseX, mouseY, delta);
			}
		}

	}

	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (isHovered(mouseX, mouseY)) {
			if (button == 0) {
				module.toggle();
			} else if (button == 1) {
				extended = !extended;
				parent.updateButtons();
			}
		}

		for (Component component : components) {
			if (extended == true) {
				component.mouseClicked(mouseX, mouseY, button);
			}
		}
	}

	public void mouseReleased(double mouseX, double mouseY, int button) {
		for (Component component : components) {
			component.mouseReleased(mouseX, mouseY, button);
		}
	}

	public boolean isHovered(double mouseX, double mouseY) {
		return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset
				&& mouseY < parent.y + offset + parent.height;
	}
}
