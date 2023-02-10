package autopartsclient.ui.screens.clickgui;

import java.util.ArrayList;
import java.util.List;

import autopartsclient.module.Mod.Category;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ClickGUI extends Screen{
	
	public static final ClickGUI INSTANCE = new ClickGUI();
	
	private List<Frame> frames;
	
	private ClickGUI() {
		super(Text.literal("Click GUI"));
		
		frames = new ArrayList<>();
		
		int offset = 5;
		for (Category category : Category.values()) {
			//stuff
			frames.add(new Frame(category, offset, 25, 65, 15));
			offset += 75;
		}
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		for (Frame frame : frames) {
			frame.render(matrices, mouseX, mouseY, delta);
			frame.updatePosition(mouseX, mouseY);
		}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		for (Frame frame : frames) {
			frame.mouseClicked(mouseX, mouseY, button);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		for (Frame frame : frames) {
			frame.mouseRelease(mouseX, mouseY, button);
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}
}