package autopartsclient.module;

import java.util.ArrayList;
import java.util.List;

import autopartsclient.SharedVaribles;
import autopartsclient.module.settings.KeySetting;
import autopartsclient.module.settings.Setting;
import autopartsclient.util.ChatUtils;
import autopartsclient.util.Player.IClientPlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class Mod {
    
    private String name;
    private String displayName;
    private String description;
    private Category category;
    private int key;
    private boolean enabled;
    public boolean showInArray = true;

    private boolean binding;

    private List<Setting> settings = new ArrayList<>();

    protected static MinecraftClient mc = MinecraftClient.getInstance();

    public Mod(String name, String description, Category category) {
	this.name = name;
	this.displayName = name;
	this.description = description;
	this.category = category;
	
	settings.add(new KeySetting("KeyBindSetting"));
    }

    public void toggle() {
	this.enabled = !this.enabled;

	// mc.getSoundManager().play(PositionedSoundInstance.ambient(SoundEvents.UI_BUTTON_CLICK,
	// enabled ? 1 : 0.8f, 1));

	if (enabled) {
	    if (SharedVaribles.doChatMod)
		ChatUtils.message(name + " Enabled");
	    onEnable();
	} else {
	    if (SharedVaribles.doChatMod)
		ChatUtils.message(name + " Disabled");
	    onDisable();
	}
    }

    public List<Setting> getSettings() {
	return settings;
    }

    public void addSetting(Setting setting) {
	settings.add(setting);
    }

    public void addSetting(Setting... settings) {
	for (Setting setting : settings)
	    addSetting(setting);
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onTick() {
    }

    public void render(MatrixStack matrixStack, float partialTicks) {
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public int getKey() {
	return key;
    }

    public void setKey(int key) {
	this.key = key;
    }

    public boolean isEnabled() {
	return enabled;
    }

    public void setEnabled(boolean enabled) {
	this.enabled = enabled;

	if (enabled) {
	    onEnable();
	} else {
	    onDisable();
	}
    }

    public Category getCategory() {
	return category;
    }

    public enum Category {
	COMBAT("Combat"), MOVEMENT("Movement"), PLAYER("Player"), RENDER("Render"), EXPLOIT("Exploit"), MISC("Misc");

	public String name;

	private Category(String name) {
	    this.name = name;
	}
    }

    public boolean isBinding() {
	return binding;
    }

    public void setBinding(boolean binding) {
	this.binding = binding;
    }
}
