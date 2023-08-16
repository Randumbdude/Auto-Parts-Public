package autopartsclient.module.Render;

import autopartsclient.module.Mod;

public class NoFog extends Mod{
    public static boolean isToggled;
    
    public NoFog() {
	super("NoFog", "", Category.RENDER);
    }
    
    @Override
    public void onDisable() {
        // TODO Auto-generated method stub
	isToggled = false;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        // TODO Auto-generated method stub
	isToggled = true;
        super.onEnable();
    }
}
