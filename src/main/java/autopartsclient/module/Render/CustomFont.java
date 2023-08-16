package autopartsclient.module.Render;

import autopartsclient.module.Mod;

public class CustomFont extends Mod{
    public static CustomFont INSTANCE;
    
    public static boolean isToggled;
    
    public CustomFont() {
	super("CustomFont", "", Category.MISC);
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
	isToggled = false;
        super.onDisable();
    }
}
