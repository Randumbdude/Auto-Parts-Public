package autopartsclient.cmd;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.arguments.StringArgumentType;

import autopartsclient.SharedVaribles;
import autopartsclient.module.Mod;
import autopartsclient.module.Movement.Flight;
import autopartsclient.module.Render.NoFog;
import autopartsclient.ui.screens.BindScreen;
import autopartsclient.ui.screens.clickgui.ClickGUI;
import autopartsclient.util.ChatUtils;
import net.minecraft.client.MinecraftClient;

public class CommandHandler {
    public static Mod mod;

    public static Logger logger = LogManager.getLogger(CommandHandler.class);

    public static void handleCommand(String command) {
	String parsedCommand;

	parsedCommand = command.substring(1);
	parsedCommand = parsedCommand.toLowerCase();

	logger.info(parsedCommand);

	switch (parsedCommand) {
	case "help": {
	    ChatUtils.message("§lAuto Parts " + SharedVaribles.version + " - O_R1ley");
	    ChatUtils.message(".hacklist - Toggles the HackList");
	    ChatUtils.message(".fps - Toggles the FPS counter");
	    ChatUtils.message(".cords - Toggles Cordinates");
	    ChatUtils.message(".modchat - Toggles Module Chat System");
	    ChatUtils.message(".tps - Toggles TPS Counter");
	    ChatUtils.message(".armorhud - Toggles ArmorHUD");
	    ChatUtils.message(".playerhearts - Toggles Player Nametag Hearts");
	    ChatUtils.message(".minime - Toggles Mini Me HUD");
	    ChatUtils.message("!<your message> - Sends an Encrypted Message");
	    break;
	}

	case "hacklist": {
	    ChatUtils.message("Toggled HackList");
	    if (SharedVaribles.showArrayList == false) {
		SharedVaribles.showArrayList = true;
	    } else if (SharedVaribles.showArrayList == true) {
		SharedVaribles.showArrayList = false;
	    }
	    break;
	}

	case "fps": {
	    ChatUtils.message("Toggled FPS Counter");
	    if (SharedVaribles.showFPS == false) {
		SharedVaribles.showFPS = true;
	    } else if (SharedVaribles.showFPS == true) {
		SharedVaribles.showFPS = false;
	    }
	    break;
	}

	case "cords": {
	    ChatUtils.message("Toggled Cordinates");
	    if (SharedVaribles.showCords == false) {
		SharedVaribles.showCords = true;
	    } else if (SharedVaribles.showCords == true) {
		SharedVaribles.showCords = false;
	    }
	    break;
	}
	case "cord": {
	    ChatUtils.message("Toggled Cordinates");
	    if (SharedVaribles.showCords == false) {
		SharedVaribles.showCords = true;
	    } else if (SharedVaribles.showCords == true) {
		SharedVaribles.showCords = false;
	    }
	    break;
	}

	case "modchat": {
	    ChatUtils.message("Toggled modChat");
	    if (SharedVaribles.doChatMod == false) {
		SharedVaribles.doChatMod = true;
	    } else if (SharedVaribles.doChatMod == true) {
		SharedVaribles.doChatMod = false;
	    }
	    break;
	}
	case "tps": {
	    ChatUtils.message("Toggled TPS");
	    if (SharedVaribles.showTPS == false) {
		SharedVaribles.showTPS = true;
	    } else if (SharedVaribles.showTPS == true) {
		SharedVaribles.showTPS = false;
	    }
	    break;
	}
	case "armorhud": {
	    ChatUtils.message("Toggled ArmorHUD");
	    if (SharedVaribles.showArmorHUD == false) {
		SharedVaribles.showArmorHUD = true;
	    } else if (SharedVaribles.showArmorHUD == true) {
		SharedVaribles.showArmorHUD = false;
	    }
	    break;
	}
	case "playerhearts": {
	    ChatUtils.message("Toggled PlayerHearts");
	    if (SharedVaribles.showPlayerHealth == false) {
		SharedVaribles.showPlayerHealth = true;
	    } else if (SharedVaribles.showPlayerHealth == true) {
		SharedVaribles.showPlayerHealth = false;
	    }
	    break;
	}
	case "minime": {
	    ChatUtils.message("Toggled MiniMe");
	    if (SharedVaribles.showMiniMe == false) {
		SharedVaribles.showMiniMe = true;
	    } else if (SharedVaribles.showMiniMe == true) {
		SharedVaribles.showMiniMe = false;
	    }
	    break;
	}

	default:
	    ChatUtils.message("There Was An Error In Your Command, Use .help for a list of commands");
	}
    }
}
