package autopartsclient.mixins;

import java.awt.Color;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import autopartsclient.util.FontUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen{		
	
	protected TitleScreenMixin(Text title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Inject(method = "render", at = @At("TAIL"), cancellable = true)
	private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		FontUtils.drawStringWithShadow(matrices, "Auto Parts Version 1.0", 5, 5, Color.WHITE.getRGB());
	}
}
