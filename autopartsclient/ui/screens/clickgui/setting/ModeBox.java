package autopartsclient.ui.screens.clickgui.setting;

import java.awt.Color;

import autopartsclient.module.settings.ModeSetting;
import autopartsclient.module.settings.Setting;
import autopartsclient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class ModeBox extends Component{
	
	private ModeSetting modeSet = (ModeSetting)setting;

	public ModeBox(Setting setting, ModuleButton parent, int offset) {
		super(setting, parent, offset);
		this.modeSet = (ModeSetting)setting;
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		
		DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width,
				parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());
		int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
		mc.textRenderer.drawWithShadow(matrices, modeSet.getName() + ": " + modeSet.getMode(), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, -1);
		
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if (isHovered(mouseX, mouseY) && button == 0) modeSet.cycle();
		super.mouseClicked(mouseX, mouseY, button);
	}
}
