package com.kcn.mixin;

import com.kcn.Main;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public class BrewingMixin {

    @Invoker("registerPotionRecipe")
    private static void registerPotionRecipe(Potion input, Item item, Potion output) {

    }

    @Inject(at = @At("TAIL"), method = "registerDefaults")
    private static void registerDefaults(CallbackInfo ci) {
        BrewingMixin.registerPotionRecipe(Potions.WATER, Main.ESSENCE_DUST, Main.HOLY_WATER);
        BrewingMixin.registerPotionRecipe(Potions.AWKWARD, Items.WITHER_ROSE, Main.WITHER);
    }
}


