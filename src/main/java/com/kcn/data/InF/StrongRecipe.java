package com.kcn.data.InF;

import com.kcn.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;

public interface StrongRecipe {

    ArrayList<Item> inputItem = new ArrayList<>();
    ArrayList<Item> resultItem = new ArrayList<>();

    static void add(Item input, Item result) {
        inputItem.add(input);
        resultItem.add(result);
    }

    static boolean match(DefaultedList<ItemStack> de) {
        for (int i = 0; i < inputItem.size(); i++) {
            Item input = inputItem.get(i);
            Item result = resultItem.get(i);
            if (de.get(0).getItem() == input && de.get(1).getItem() == Main.HAMMER) {
                de.set(2, new ItemStack(result));
                return true;
            }
        }
        return false;
    }
}
