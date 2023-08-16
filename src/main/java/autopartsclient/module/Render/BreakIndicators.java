package autopartsclient.module.Render;

import autopartsclient.module.Mod;

public class BreakIndicators extends Mod{
    public static boolean isToggled;
    
    public BreakIndicators() {
	super("BreakProgress", "", Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        // TODO Auto-generated method stub
	isToggled = true;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        // TODO Auto-generated method stub
	isToggled = true;
        super.onDisable();
    }
}
