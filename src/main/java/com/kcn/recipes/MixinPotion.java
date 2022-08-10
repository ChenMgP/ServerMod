package com.kcn.recipes;

import com.google.common.collect.Lists;
import com.kcn.Main;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.*;

public class MixinPotion extends SpecialCraftingRecipe {
    private boolean register = true;

    public MixinPotion(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ArrayList<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.hasNbt()) {
                assert stack.getNbt() != null;
                String potion = stack.getNbt().getString("Potion");
                if (!Objects.equals(potion, "minecraft:water") && !Objects.equals(potion, "minecraft:mundane") && !Objects.equals(potion, "minecraft:thick") && !Objects.equals(potion, "minecraft:awkward")) {
                    list.add(stack);
                } else {
                    return false;
                }
            }
        }
        return list.size() >= 2;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.hasNbt()) {
                assert stack.getNbt() != null;
                String potion = stack.getNbt().getString("Potion");
                if (!Objects.equals(potion, "minecraft:water") && !Objects.equals(potion, "minecraft:mundane") && !Objects.equals(potion, "minecraft:thick") && !Objects.equals(potion, "minecraft:awkward")) {
                    arr.add(i);
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }
        if (arr.size() >= 2) {
            Set<StatusEffectInstance> effects = new HashSet<>();
            ItemStack stack = new ItemStack(Items.POTION);
            for (Integer slot : arr) {
                effects.addAll(PotionUtil.getPotionEffects(inventory.getStack(slot)));
            }
            Collection<StatusEffectInstance> arr1 = new ArrayList<>(effects);
            return PotionUtil.setCustomPotionEffects(stack, arr1);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> de = DefaultedList.ofSize(9, ItemStack.EMPTY);
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && stack.hasNbt()) {
                assert stack.getNbt() != null;
                String potion = stack.getNbt().getString("Potion");
                if (!Objects.equals(potion, "minecraft:water") && !Objects.equals(potion, "minecraft:mundane") && !Objects.equals(potion, "minecraft:thick") && !Objects.equals(potion, "minecraft:awkward")) {
                    de.set(i, Items.GLASS_BOTTLE.getDefaultStack());
                }
            }
        }
        return de;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Main.MIXIN_POTION_RECIPE;
    }

}
