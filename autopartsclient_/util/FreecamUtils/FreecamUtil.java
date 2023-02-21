package autopartsclient.util.FreecamUtils;

import java.util.HashMap;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.text.Text;
import net.minecraft.util.math.ChunkPos;

public class FreecamUtil implements ClientModInitializer{
	public static final MinecraftClient MC = MinecraftClient.getInstance();

    private static KeyBinding freecamBind;
    private static KeyBinding playerControlBind;
    private static KeyBinding tripodResetBind;
    private static boolean freecamEnabled = false;
    private static boolean tripodEnabled = false;
    private static boolean playerControlEnabled = false;
    private static boolean disableNextTick = false;
    private static Integer activeTripod = null;
    private static FreeCamera freeCamera;
    private static HashMap<Integer, FreecamPosition> overworld_tripods = new HashMap<>();
    private static HashMap<Integer, FreecamPosition> nether_tripods = new HashMap<>();
    private static HashMap<Integer, FreecamPosition> end_tripods = new HashMap<>();
    
	@Override
	public void onInitializeClient() {
		// TODO Auto-generated method stub
		
	}

    public static void toggle() {
        if (tripodEnabled) {
            toggleTripod(activeTripod);
            return;
        }

        if (freecamEnabled) {
            onDisableFreecam();
        } else {
            onEnableFreecam();
        }
        freecamEnabled = !freecamEnabled;
    }

    private static void toggleTripod(Integer keyCode) {
        if (keyCode == null) {
            return;
        }

        if (tripodEnabled) {
            if (activeTripod.equals(keyCode)) {
                onDisableTripod();
                tripodEnabled = false;
            } else {
                onDisableTripod();
                onEnableTripod(keyCode);
            }
        } else {
            if (freecamEnabled) {
                toggle();
            }
            onEnableTripod(keyCode);
            tripodEnabled = true;
        }
    }

    public static void switchControls() {
        if (!isEnabled()) {
            return;
        }

        if (playerControlEnabled) {
            freeCamera.input = new KeyboardInput(MC.options);
        } else {
            MC.player.input = new KeyboardInput(MC.options);
            freeCamera.input = new Input();
        }
        playerControlEnabled = !playerControlEnabled;
    }

    private static void onEnableTripod(int keyCode) {
        onEnable();

        FreecamPosition position = getTripodsForDimension().get(keyCode);
        boolean chunkLoaded = false;
        if (position != null) {
            ChunkPos chunkPos = position.getChunkPos();
            chunkLoaded = MC.world.getChunkManager().isChunkLoaded(chunkPos.x, chunkPos.z);
        }

        if (!chunkLoaded) {
            resetCamera(keyCode);
            position = null;
        }

        if (position == null) {
            freeCamera = new FreeCamera(-420 - (keyCode % GLFW.GLFW_KEY_0));
        } else {
            freeCamera = new FreeCamera(-420 - (keyCode % GLFW.GLFW_KEY_0), position);
        }

        freeCamera.spawn();
        MC.setCameraEntity(freeCamera);
        activeTripod = keyCode;
    }

    private static void onDisableTripod() {
        getTripodsForDimension().put(activeTripod, new FreecamPosition(freeCamera));
        onDisable();

        if (MC.player != null) {
        }
        activeTripod = null;
    }

    private static void onEnableFreecam() {
        onEnable();
        freeCamera = new FreeCamera(-420);
        freeCamera.spawn();
        MC.setCameraEntity(freeCamera);
    }

    private static void onDisableFreecam() {
        onDisable();
    }

    private static void onEnable() {
        MC.chunkCullingEnabled = false;
        MC.gameRenderer.setRenderHand(true);

        if (MC.gameRenderer.getCamera().isThirdPerson()) {
            MC.options.setPerspective(Perspective.FIRST_PERSON);
        }
    }

    private static void onDisable() {
        MC.chunkCullingEnabled = true;
        MC.gameRenderer.setRenderHand(true);
        MC.setCameraEntity(MC.player);
        playerControlEnabled = false;
        freeCamera.despawn();
        freeCamera.input = new Input();
        freeCamera = null;

        if (MC.player != null) {
            MC.player.input = new KeyboardInput(MC.options);
        }
    }

    private static void resetCamera(int keyCode) {
        if (tripodEnabled && activeTripod != null && activeTripod == keyCode && freeCamera != null) {
            freeCamera.copyPositionAndRotation(MC.player);
        } else {
            getTripodsForDimension().put(keyCode, null);
        }
    }

    public static void clearTripods() {
        overworld_tripods = new HashMap<>();
        nether_tripods = new HashMap<>();
        end_tripods = new HashMap<>();
    }

    public static FreeCamera getFreeCamera() {
        return freeCamera;
    }

    public static HashMap<Integer, FreecamPosition> getTripodsForDimension() {
        HashMap<Integer, FreecamPosition> result;
        switch (MC.world.getDimensionKey().getValue().getPath()) {
            case "the_nether":
                result = nether_tripods;
                break;
            case "the_end":
                result = end_tripods;
                break;
            default:
                result = overworld_tripods;
                break;
        }
        return result;
    }

    public static KeyBinding getFreecamBind() {
        return freecamBind;
    }

    public static KeyBinding getTripodResetBind() {
        return tripodResetBind;
    }

    public static boolean disableNextTick() {
        return disableNextTick;
    }

    public static void setDisableNextTick(boolean damage) {
        disableNextTick = damage;
    }

    public static boolean isEnabled() {
        return freecamEnabled || tripodEnabled;
    }

    public static boolean isPlayerControlEnabled() {
        return playerControlEnabled;
    }
}
