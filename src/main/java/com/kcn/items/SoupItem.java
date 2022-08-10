package com.kcn.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SoupItem extends Item {
    public SoupItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (((PlayerEntity) user).isCreative()) {
            return stack;
        }
        ((PlayerEntity) user).giveItemStack(Items.BOWL.getDefaultStack());
        return super.finishUsing(stack, world, user);
    }


}