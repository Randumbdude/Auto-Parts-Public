package autopartsclient.module.Misc;

import autopartsclient.module.Mod;
import autopartsclient.module.settings.NumberSetting;

public class Coordinates extends Mod{
    public static boolean isToggled;
    
    public static NumberSetting xValue = new NumberSetting("X", -1000, 1000, 50, 0.0001);
    public static NumberSetting yValue = new NumberSetting("Y", -500, 500, 50, 0.0001);
    public static NumberSetting scaleValue = new NumberSetting("Scale", 0.1f, 5, 0.9f, 0.001f);
    
    public static int x = 5;
    public static int y = 5;
    public static float scale = 0.9f;
    
    public Coordinates() {
	super("Coordinates", "", Category.MISC);
	this.showInArray = false;
	addSetting(xValue, yValue, scaleValue);
    }
    
    @Override
    public void onTick() {
        x = xValue.getValueInt();
        y = yValue.getValueInt();
        scale = scaleValue.getValueFloat();
        super.onTick();
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
