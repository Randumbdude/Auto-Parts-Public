package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class AntiKB extends Mod{
	public static boolean isToggled;
	
	public static int Percent = -300;

	public AntiKB() {
		super("AntiKB", "", Category.PLAYER);
	}
	
	@Override
	public void onEnable() {
		isToggled = true;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		isToggled = false;
		super.onDisable();
	}
	
	@Override
	public void onTick() {		
		if(mc.player.hurtTime > 0) {			
	        float perc = Percent / 100.0f;
	        if (Percent == 0)
	            mc.player.setVelocity(0,0,0);
	        else {
	        	mc.player.setVelocity(mc.player.getVelocity().getX() * perc, mc.player.getVelocity().getY() * perc, mc.player.getVelocity().getZ() * perc);
	        }
		}
		else {
			mc.player.getAir();
		}		
	}
}
