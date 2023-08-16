package autopartsclient.module.Player;

import autopartsclient.module.Mod;

public class PacketCancellor extends Mod {
	public static boolean isToggled;
	
	public PacketCancellor() {
		super("PacketCancellor", "", Category.PLAYER);
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
