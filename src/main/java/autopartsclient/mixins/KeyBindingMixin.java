package autopartsclient.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import autopartsclient.util.Key.IKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin implements IKeyBinding
{
	@Shadow
	private InputUtil.Key boundKey;
	
	@Override
	public boolean isActallyPressed()
	{
		long handle = MinecraftClient.getInstance().getWindow().getHandle();
		int code = boundKey.getCode();
		return InputUtil.isKeyPressed(handle, code);
	}
	
	@Override
	public void resetPressedState()
	{
		setPressed(isActallyPressed());
	}
	
	@Shadow
	public abstract void setPressed(boolean pressed);
}