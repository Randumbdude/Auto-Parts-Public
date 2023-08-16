package autopartsclient.module.Player;

import java.util.ArrayList;
import java.util.Arrays;

import autopartsclient.module.Mod;
import autopartsclient.util.Key.IKeyBinding;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.KeyBinding;

public class InvMove extends Mod {
    public static boolean isToggled;
    
    public InvMove() {
	super("InvMove", "", Category.PLAYER);
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

    @Override
    public void onTick() {
	Screen screen = mc.currentScreen;
	if (screen == null)
	    return;

	if (!isAllowedScreen(screen))
	    return;

	ArrayList<KeyBinding> keys = new ArrayList<>(
		Arrays.asList(mc.options.forwardKey, mc.options.backKey, mc.options.leftKey, mc.options.rightKey));

	keys.add(mc.options.sneakKey);
	keys.add(mc.options.sprintKey);
	keys.add(mc.options.jumpKey);

	for (KeyBinding key : keys)
	    ((IKeyBinding) key).resetPressedState();
    }

    private boolean isAllowedScreen(Screen screen) {
	if (screen instanceof AbstractInventoryScreen && !isCreativeSearchBarOpen(screen))
	    return true;

	if (screen instanceof HandledScreen && !hasTextBox(screen))
	    return true;

	return false;
    }

    private boolean isCreativeSearchBarOpen(Screen screen) {
	if (!(screen instanceof CreativeInventoryScreen))
	    return false;
	return true;

	// return CreativeInventoryScreen.selectedTab == ItemGroups.SEARCH;
    }

    private boolean hasTextBox(Screen screen) {
	return screen.children().stream().anyMatch(e -> e instanceof TextFieldWidget);
    }
}
