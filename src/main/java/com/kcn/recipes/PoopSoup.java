package com.kcn.recipes;

import com.kcn.Main;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Objects;

public class PoopSoup extends SpecialCraftingRecipe {
    private int waterSlot = 0;

    public PoopSoup(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ArrayList<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Main.POOP) {
                    list.add(stack);
                }else if (stack.getItem() == Items.BOWL) {
                    list.add(stack);
                } else if (Objects.equals(stack.getNbt(), Items.POTION.getDefaultStack().getNbt())) {
                    list.add(stack);
                }
            }
        }
        return list.size() == 3;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        boolean hasWater = false;
        boolean hasPoop = false;
        boolean hasBowl = false;
        int count = 0;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                count++;
            }
            if (Objects.equals(stack.getNbt(), Items.POTION.getDefaultStack().getNbt())) {
                waterSlot = i;
                hasWater = true;
            } else if (stack.getItem() == Main.POOP) {
                hasPoop = true;
            } else if (stack.getItem() == Items.BOWL) {
                hasBowl = true;
            }
        }
        if (hasWater && hasPoop && hasBowl && count == 3) {
            return Main.POOP_SOUP.getDefaultStack();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> de = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        de.set(waterSlot, Items.GLASS_BOTTLE.getDefaultStack());
        return de;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Main.POOP_SOUP_RECIPE;
    }
}
