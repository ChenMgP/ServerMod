package com.kcn.items;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class OxygenBottle extends Item {

    public OxygenBottle(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isCreative()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20 * 50));
            return super.use(world, user, hand);
        } else {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 20 * 50));
            user.getMainHandStack().decrement(1);
            user.giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
            return super.use(world, user, hand);
        }
    }
}
