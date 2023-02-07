package autopartsclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum ChatUtils
{
	;
	
	private static final MinecraftClient MC = MinecraftClient.getInstance();
	
	public static final String AutoParts_Prefix =
		"\u00a7r[\u00A72Auto Parts\u00a7r]: ";
	
	private static boolean enabled = true;
	
	public static void setEnabled(boolean enabled)
	{
		ChatUtils.enabled = enabled;
	}
	
	public static void component(Text component)
	{
		if(!enabled)
			return;
		
		ChatHud chatHud = MC.inGameHud.getChatHud();
		MutableText prefix = Text.literal(AutoParts_Prefix);
		chatHud.addMessage(prefix.append(component));
	}
	
	public static void message(String message)
	{
		component(Text.literal(message));
	}
}