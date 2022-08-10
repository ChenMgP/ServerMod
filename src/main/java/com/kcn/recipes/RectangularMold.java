package com.kcn.recipes;

import com.google.common.collect.Lists;
import com.kcn.Main;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RectangularMold extends SpecialCraftingRecipe {

    int damage = 0;

    public RectangularMold(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        ArrayList<ItemStack> arr = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Main.GOLD_SHEET) {
                    arr.add(stack);
                } else if (stack.getItem() == Main.HAMMER) {
                    arr.add(stack);
                } else if (stack.getItem() == Main.RECTANGULAR_EMBRYO.asItem()) {
                    arr.add(stack);
                }
            }
        }
        return arr.size() == 3;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ArrayList<ItemStack> arr = Lists.newArrayList();
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Main.GOLD_SHEET) {
                    arr.add(stack);
                } else if (stack.getItem() == Main.HAMMER) {
                    arr.add(stack);
                    assert stack.getNbt() != null;
                    damage = stack.getNbt().getInt("Damage");
                } else if (stack.getItem() == Main.RECTANGULAR_EMBRYO.asItem()) {
                    arr.add(stack);
                }
            }
        }
        if (arr.size() == 3) {
            return new ItemStack(Main.RECTANGULAR_MOLD);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> de = DefaultedList.ofSize(9, ItemStack.EMPTY);
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Main.HAMMER) {
                    assert stack.getNbt() != null;
                    ItemStack stack1 = new ItemStack(Main.HAMMER);
                    assert stack1.getNbt() != null;
                    if (damage + 1 - new ItemStack(Main.HAMMER).getMaxDamage() <= 0) {
                        stack1.getNbt().putInt("Damage", damage + 1);
                        de.set(i, stack1);
                    }
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
        return Main.RECTANGULAR_MOLD_RECIPE;
    }
}
