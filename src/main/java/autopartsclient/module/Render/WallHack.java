package autopartsclient.module.Render;

import org.lwjgl.glfw.GLFW;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.NumberSetting;

public class WallHack extends Mod{
    public static boolean isToggled;
    
    public static int alpha = 175;
    
    public NumberSetting AlphaSetting = new NumberSetting("Alpha", 1, 255, 175, 1);
    
    public WallHack() {
	super("WallHack", "", Category.RENDER);
	addSetting(AlphaSetting);
	this.setKey(GLFW.GLFW_KEY_H);
    }
    
    @Override
    public void onEnable() {
	mc.chunkCullingEnabled = false;
        mc.worldRenderer.reload();
        // TODO Auto-generated method stub
	isToggled =true;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
	mc.chunkCullingEnabled = true;
        mc.worldRenderer.reload();
        // TODO Auto-generated method stub
	isToggled = false;
        super.onDisable();
    }
    
    @Override
    public void onTick() {
	int oldAlpha = alpha;
	
	alpha  = AlphaSetting.getValueInt();
	if(oldAlpha != alpha) {
	    mc.worldRenderer.reload();
	}
    }
}
