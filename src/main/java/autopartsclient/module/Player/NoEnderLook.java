package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class NoEnderLook extends Mod{
    public static boolean isToggled = false;
    
    public NoEnderLook() {
	super("NoEnderLook", "Stops Endermen from getting angry when you look at them", Category.PLAYER);
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
