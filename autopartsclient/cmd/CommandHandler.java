package autopartsclient.cmd;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autopartsclient.SharedVaribles;
import autopartsclient.util.ChatUtils;

public class CommandHandler {
	public static Logger logger = LogManager.getLogger(CommandHandler.class);

	public static void handleCommand(String command) {
		String parsedCommand;

		parsedCommand = command.substring(1);
		parsedCommand = parsedCommand.toLowerCase();

		logger.info(parsedCommand);

		switch (parsedCommand) {
		case "help": {
			ChatUtils.message("§lAuto Parts 1.0 - O_R1ley");
			ChatUtils.message(".arraylist - Toggles the ArrayList");
			ChatUtils.message(".fps - Toggles the FPS counter");
			ChatUtils.message(".cords - Toggles Cordinates");
			ChatUtils.message(".modchat - Toggles Module Chat System");
			ChatUtils.message(".armorhud - Toggles ArmorHUD");
			break;
		}
		
		case "arraylist":{
			ChatUtils.message("Toggled ArrayList");
			if(SharedVaribles.showArrayList == false) {
			SharedVaribles.showArrayList = true;
			}
			else if (SharedVaribles.showArrayList == true) {
				SharedVaribles.showArrayList = false;
			}
			break;
		}
		
		case "fps":{
			ChatUtils.message("Toggled FPS Counter");
			if(SharedVaribles.showFPS == false) {
			SharedVaribles.showFPS = true;
			}
			else if (SharedVaribles.showFPS == true) {
				SharedVaribles.showFPS = false;
			}
			break;
		}
		
		case "cords":{
			ChatUtils.message("Toggled Cordinates");
			if(SharedVaribles.showCords == false) {
			SharedVaribles.showCords = true;
			}
			else if (SharedVaribles.showCords == true) {
				SharedVaribles.showCords = false;
			}
			break;
		}
		
		case "modchat":{
			ChatUtils.message("Toggled modChat");
			if(SharedVaribles.doChatMod == false) {
			SharedVaribles.doChatMod = true;
			}
			else if (SharedVaribles.doChatMod == true) {
				SharedVaribles.doChatMod = false;
			}
			break;
		}
		case "tps":{
			ChatUtils.message("Toggled TPS");
			if(SharedVaribles.showTPS == false) {
			SharedVaribles.showTPS = true;
			}
			else if (SharedVaribles.showTPS == true) {
				SharedVaribles.showTPS = false;
			}
			break;
		}
		case "armorhud":{
			ChatUtils.message("Toggled ArmorHUD");
			if(SharedVaribles.showArmorHUD == false) {
			SharedVaribles.showArmorHUD = true;
			}
			else if (SharedVaribles.showArmorHUD == true) {
				SharedVaribles.showArmorHUD = false;
			}
			break;
		}
		default:
			ChatUtils.message("There Was An Error In Your Command, Use .help for a list of commands");
		}
	}
}
