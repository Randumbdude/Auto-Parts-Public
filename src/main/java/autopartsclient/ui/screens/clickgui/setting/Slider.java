package autopartsclient.ui.screens.clickgui.setting;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import autopartsclient.module.settings.NumberSetting;
import autopartsclient.module.settings.Setting;
import autopartsclient.ui.screens.clickgui.ModuleButton;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class Slider extends Component{
    
    public NumberSetting numSet = (NumberSetting)setting;
    
    private boolean sliding = false;

    public Slider(Setting setting, ModuleButton parent, int offset) {
	super(setting, parent, offset);
	// TODO Auto-generated constructor stub
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
	DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + parent.parent.width,
		parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());
	
	double diff = Math.min(parent.parent.width, Math.max(0, mouseX - parent.parent.x));	
	int renderWidth = (int)(parent.parent.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));
	
	DrawableHelper.fill(matrices, parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x + renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0, 210, 0, 100).getRGB());
	
	if(sliding) {
	    if(diff == 0) {
		numSet.setValue(numSet.getMin());
	    }else {
		numSet.setValue(roundToPlace((diff / parent.parent.width) * (numSet.getMax() - numSet.getMin()) + numSet.getMin(), 2));
	    }
	}
	
	int textOffset = ((parent.parent.height / 2) - mc.textRenderer.fontHeight / 2);
	mc.textRenderer.drawWithShadow(matrices, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 1), parent.parent.x + textOffset, parent.parent.y + parent.offset + offset + textOffset, -1);

        super.render(matrices, mouseX, mouseY, delta);
    }
    
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        // TODO Auto-generated method stub
	if(isHovered(mouseX, mouseY)) sliding = true;
        super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        // TODO Auto-generated method stub
	sliding = false;
        super.mouseReleased(mouseX, mouseY, button);
    }
    
    private double roundToPlace(double value, int place) {
	if (place < 0) {
	    return value;
	}
	
	BigDecimal bd = new BigDecimal(value);
	bd = bd.setScale(place, RoundingMode.HALF_UP);
	return bd.doubleValue();
    }
}
