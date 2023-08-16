package autopartsclient.ui.screens.clickgui.setting;

import java.awt.Color;

import autopartsclient.module.settings.KeySetting;
import autopartsclient.module.settings.Setting;
import autopartsclient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class BindBox extends Component {

    private KeySetting keySet = (KeySetting) setting;

    public BindBox(Setting setting, ModuleButton parent, int offset) {
	super(setting, parent, offset);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
	DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset,
		parent.parent.x + parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height,
		new Color(0, 100, 0, 160).getRGB());

	int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);

	String bindText;
	if (parent.module.getKey() == 0) {
	    bindText = "Bind: none";
	} else {
	    bindText = "Bind: " + (char) parent.module.getKey();
	}

	mc.textRenderer.drawWithShadow(matrices, bindText, parent.parent.x + textOffset,
		parent.parent.y + parent.offset + offset + textOffset, -1);
	super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
	if (isHovered(mouseX, mouseY) && button == 0) {
	    mc.setScreen(null);
	    parent.module.setBinding(true);
	}
	super.mouseClicked(mouseX, mouseY, button);
    }
}
