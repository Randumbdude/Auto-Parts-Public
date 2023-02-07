package autopartsclient.module.Render;

import java.util.ArrayList;

import autopartsclient.module.Mod;
import autopartsclient.util.ChatUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potions;

public class Xray extends Mod {

	public static ArrayList<String> interrestingBlocks = new ArrayList<>();
	
	public static boolean isToggled = false;
	
	public Xray() {
		super("Xray", "See shit through blocks", Category.RENDER);
		
		interrestingBlocks.add(Blocks.COAL_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.COPPER_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.IRON_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.GOLD_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DIAMOND_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.EMERALD_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.REDSTONE_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.LAPIS_ORE.getTranslationKey());

        interrestingBlocks.add(Blocks.DEEPSLATE_COAL_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_COPPER_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_IRON_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_GOLD_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_DIAMOND_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_EMERALD_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_REDSTONE_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.DEEPSLATE_LAPIS_ORE.getTranslationKey());

        interrestingBlocks.add(Blocks.ANCIENT_DEBRIS.getTranslationKey());
        interrestingBlocks.add(Blocks.NETHER_GOLD_ORE.getTranslationKey());
        interrestingBlocks.add(Blocks.NETHER_QUARTZ_ORE.getTranslationKey());

        interrestingBlocks.add(Blocks.LAVA.getTranslationKey());
        interrestingBlocks.add(Blocks.WATER.getTranslationKey());

        interrestingBlocks.add(Blocks.END_PORTAL_FRAME.getTranslationKey());
        interrestingBlocks.add(Blocks.NETHER_PORTAL.getTranslationKey());
        interrestingBlocks.add(Blocks.SPAWNER.getTranslationKey());
        
        interrestingBlocks.add(Blocks.CHEST.getTranslationKey());
        interrestingBlocks.add(Blocks.ENDER_CHEST.getTranslationKey());
	}

	
	
	
	@Override
    public void onEnable() {
		ChatUtils.message("Xray Enabled");
		mc.chunkCullingEnabled = false;
        mc.worldRenderer.reload();
        isToggled = true;
    }

    @Override
    public void onDisable() {
    	ChatUtils.message("Xray Disabled");
		mc.chunkCullingEnabled = true;
        mc.worldRenderer.reload();
        isToggled = false;
    }
    
    public static ArrayList<String> getInterrestingBlocks() {
        return interrestingBlocks;
    }
}