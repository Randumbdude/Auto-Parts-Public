package autopartsclient.module.Misc;

import autopartsclient.module.Mod;

public class TestModule extends Mod {

    public static boolean isToggled = false;

    public TestModule() {
	super("TestModule", "", Category.MISC);
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
