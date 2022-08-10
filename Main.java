package com.kcn;

import com.kcn.blocks.ModBlockEntities;
import com.kcn.blocks.ModBlocks;
import com.kcn.effect.ModEffects;
import com.kcn.items.ModItems;
import com.kcn.potion.ModPotions;
import com.kcn.recipes.ModRecipes;
import com.kcn.screen.ModScreenHandlers;
import com.kcn.screen.ModScreens;
import com.kcn.sounds.ModSounds;
import com.kcn.util.ModRegisters;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


public class Main implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("kcn");


    @Override
    public void onInitialize() {

        ModItems.ModItemRegister();
        ModBlocks.ModBlockRegister();
        ModBlockEntities.ModBlockEntityRegister();
        ModScreenHandlers.ModScreenHandlerRegister();
        ModScreens.ModScreenRegister();
        ModEffects.ModEffectRegister();
        ModPotions.ModPotionRegister();
        ModRecipes.ModRecipeRegister();
        ModSounds.ModSoundRegister();

        ModBlockEntities.registryBlockEntity();
        ModRecipes.registryRecipe();
        ModScreenHandlers.registryScreenHandler();

        ModRegisters.registerModStuffs();
        GeckoLib.initialize();

    }
}
