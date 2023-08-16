package autopartsclient.module.Combat;

import autopartsclient.module.Mod;

public class NoShieldCool extends Mod{
    public static boolean isToggled;
    
    public NoShieldCool() {
	super("NoShieldCool", "u", Category.COMBAT);
    }
    
    @Override
    public void onEnable() {
        isToggled = true;
    }
    @Override
    public void onDisable() {
	isToggled = false;
    }
}
