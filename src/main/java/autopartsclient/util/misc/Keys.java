package autopartsclient.util.misc;

import org.lwjgl.glfw.GLFW;

import autopartsclient.mixins.KeyBindingAccessor;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Keys {
    //private static final String CATEGORY = "Hypnotic Client";

    //public static KeyBinding OPEN_CLICK_GUI = new KeyBinding("key.hypnotic-client.open-click-gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, CATEGORY);

    public static int getKey(KeyBinding bind) {
        return ((KeyBindingAccessor) bind).getKey().getCode();
    }
}