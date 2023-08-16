package autopartsclient.module;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import autopartsclient.module.Mod.Category;
import autopartsclient.module.Combat.ArrowDamage;
import autopartsclient.module.Combat.AutoAim;
import autopartsclient.module.Combat.AutoAttack;
import autopartsclient.module.Combat.AutoTotem;
import autopartsclient.module.Combat.Criticals;
import autopartsclient.module.Combat.CrystalAura;
import autopartsclient.module.Combat.KillAura;
import autopartsclient.module.Combat.NoShieldCool;
import autopartsclient.module.Exploit.Disabler;
import autopartsclient.module.Exploit.Dupe;
import autopartsclient.module.Exploit.PacketMine;
import autopartsclient.module.Exploit.Ride;
import autopartsclient.module.Exploit.SoundLocate;
import autopartsclient.module.Misc.AirPlace;
import autopartsclient.module.Misc.AntiKick;
import autopartsclient.module.Misc.Coordinates;
import autopartsclient.module.Misc.FakePlayer;
import autopartsclient.module.Misc.Nuker;
import autopartsclient.module.Misc.TestModule;
import autopartsclient.module.Misc.Warn;
import autopartsclient.module.Movement.AntiRubberBand;
import autopartsclient.module.Movement.BoatClip;
import autopartsclient.module.Movement.Dolphin;
import autopartsclient.module.Movement.Flight;
import autopartsclient.module.Movement.Jesus;
import autopartsclient.module.Movement.PacketFly;
import autopartsclient.module.Movement.Phase;
import autopartsclient.module.Movement.Spider;
import autopartsclient.module.Movement.Sprint;
import autopartsclient.module.Movement.Step;
import autopartsclient.module.Player.AntiHunger;
import autopartsclient.module.Player.AntiKB;
import autopartsclient.module.Player.AntiLev;
import autopartsclient.module.Player.EntityDesync;
import autopartsclient.module.Player.HClip;
import autopartsclient.module.Player.InvMove;
import autopartsclient.module.Player.NoClickCooldown;
import autopartsclient.module.Player.NoEnderLook;
import autopartsclient.module.Player.NoFall;
import autopartsclient.module.Player.NoHurtCam;
import autopartsclient.module.Player.NoSlow;
import autopartsclient.module.Player.PacketCancellor;
import autopartsclient.module.Player.Reach;
import autopartsclient.module.Player.Timer;
import autopartsclient.module.Render.BlockESP;
import autopartsclient.module.Render.BlockOutline;
import autopartsclient.module.Render.BreakIndicators;
import autopartsclient.module.Render.ESP;
import autopartsclient.module.Render.Freecam;
import autopartsclient.module.Render.FullBright;
import autopartsclient.module.Render.HitIndicators;
import autopartsclient.module.Render.NoFog;
import autopartsclient.module.Render.NewChunks;
import autopartsclient.module.Render.Search;
import autopartsclient.module.Render.Tracers;
import autopartsclient.module.Render.Trajectories;
import autopartsclient.module.Render.UnfocusedCPU;
import autopartsclient.module.Render.WallHack;
import autopartsclient.module.Render.Waypoint;
import autopartsclient.module.Render.Xray;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

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
	// hacks
	modules.add(new Flight());
	modules.add(new Sprint());
	modules.add(new FullBright());
	modules.add(new Xray());
	modules.add(new FakePlayer());
	modules.add(new NoFall());
	modules.add(new Tracers());
	modules.add(new KillAura());
	modules.add(new AutoAim());
	modules.add(new CrystalAura());
	modules.add(new Spider());
	modules.add(new Dolphin());
	modules.add(new ESP());
	modules.add(new Step());
	modules.add(new BlockESP());
	modules.add(new Warn());
	modules.add(new BoatClip());
	//modules.add(new Waypoint());
	modules.add(new Freecam());
	modules.add(new NoHurtCam());
	modules.add(new SoundLocate());
	//modules.add(new Dupe());
	modules.add(new AntiLev());
	modules.add(new NoSlow());
	//modules.add(new AntiKB());
	modules.add(new NoClickCooldown());
	//modules.add(new HClip());
	// modules.add(new SelfPlace());
	//modules.add(new UnfocusedCPU());
	modules.add(new AntiRubberBand());
	modules.add(new AutoTotem());
	modules.add(new NewChunks());
	modules.add(new Trajectories());
	modules.add(new Timer());
	modules.add(new Reach());
	modules.add(new Nuker());
	modules.add(new AntiKick());
	modules.add(new PacketCancellor());
	modules.add(new EntityDesync());
	modules.add(new PacketFly());
	modules.add(new Criticals());
	modules.add(new AirPlace());
	modules.add(new BlockOutline());
	modules.add(new InvMove());
	modules.add(new AntiHunger());
	modules.add(new Search());
	modules.add(new Phase());
	//modules.add(new TestModule());
	//modules.add(new WallHack());
	modules.add(new Ride());
	modules.add(new Disabler());
	modules.add(new NoFog());
	//modules.add(new Coordinates());
	modules.add(new BreakIndicators());
	modules.add(new PacketMine());
	//modules.add(new HitIndicators());
	modules.add(new AutoAttack());
	modules.add(new NoShieldCool());
	modules.add(new ArrowDamage());
	//modules.add(new Jesus());
	modules.add(new NoEnderLook());
    }
}
