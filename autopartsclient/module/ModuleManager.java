package autopartsclient.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autopartsclient.module.Mod.Category;
import autopartsclient.module.Combat.AutoAim;
import autopartsclient.module.Combat.CrystalAura;
import autopartsclient.module.Exploit.NoFall;
import autopartsclient.module.Misc.Blink;
import autopartsclient.module.Misc.FakePlayer;
import autopartsclient.module.Misc.Warn;
import autopartsclient.module.Movement.BoatClip;
import autopartsclient.module.Movement.Dolphin;
import autopartsclient.module.Movement.Flight;
import autopartsclient.module.Movement.Spider;
import autopartsclient.module.Movement.Sprint;
import autopartsclient.module.Movement.Step;
import autopartsclient.module.Render.BlockESP;
import autopartsclient.module.Render.ESP;
import autopartsclient.module.Render.FullBright;
import autopartsclient.module.Render.Tracers;
import autopartsclient.module.Render.Waypoint;
import autopartsclient.module.Render.Xray;
import net.minecraft.client.MinecraftClient;

public class ModuleManager {

	public static final ModuleManager INSTANCE = new ModuleManager();
	private List<Mod> modules = new ArrayList<>();
	public Logger logger = LogManager.getLogger(ModuleManager.class);
	MinecraftClient mc = MinecraftClient.getInstance();

	public ModuleManager() {
		addModules();
	}

	public List<Mod> getModules() {
		return modules;
	}

	public List<Mod> getEnabledModules() {
		List<Mod> enabled = new ArrayList<>();
		for (Mod module : modules) {
			if (module.isEnabled())
				enabled.add(module);
		}
		return enabled;
	}

	public List<Mod> getModulesInCategory(Category category) {
		List<Mod> categoryModules = new ArrayList<>();

		for (Mod mod : modules) {
			if (mod.getCategory() == category) {
				categoryModules.add(mod);
			}
		}

		return categoryModules;
	}

	public void addModules() {
		modules.add(new Flight());
		modules.add(new Sprint());
		modules.add(new FullBright());
		modules.add(new Xray());
		modules.add(new FakePlayer());
		modules.add(new NoFall());
		modules.add(new Tracers());
		modules.add(new AutoAim());
		modules.add(new CrystalAura());
		modules.add(new Spider());
		modules.add(new Dolphin());
		modules.add(new Blink());
		modules.add(new ESP());
		modules.add(new Step());
		modules.add(new BlockESP());
		modules.add(new Warn());
		modules.add(new BoatClip());
		modules.add(new Waypoint());
	}
}
