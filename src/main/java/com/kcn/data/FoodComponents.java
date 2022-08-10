package com.kcn.data;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class FoodComponents {

    public static final FoodComponent SEEDS = new FoodComponent.Builder().hunger(1).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 15, 1), 0.3f).alwaysEdible().build();
    public static final FoodComponent BAKED_SEEDS = new FoodComponent.Builder().hunger(1).alwaysEdible().build();

}
