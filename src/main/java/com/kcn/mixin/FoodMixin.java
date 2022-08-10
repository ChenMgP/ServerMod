package com.kcn.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;

@Mixin(Item.class)
public class FoodMixin {

    private Path path = FabricLoader.getInstance().getGameDir();

    @Inject(at = @At("HEAD"), method = "finishUsing")
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getItem() == Items.BEEF || stack.getItem() == Items.PORKCHOP || stack.getItem() == Items.MUTTON || stack.getItem() == Items.CHICKEN || stack.getItem() == Items.RABBIT) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 10, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20 * 10, 2));
            cir.setReturnValue(user.eatFood(world, stack));
        } else if (stack.getItem() == Items.COD || stack.getItem() == Items.SALMON || stack.getItem() == Items.TROPICAL_FISH) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 10, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20 * 10, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 10, 2));
            cir.setReturnValue(user.eatFood(world, stack));
        }
    }
}
