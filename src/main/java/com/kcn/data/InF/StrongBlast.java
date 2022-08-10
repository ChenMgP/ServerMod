package com.kcn.data.InF;

import com.kcn.blocks.entities.DustMachineEntity;
import com.kcn.data.Tag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface StrongBlast {


    ArrayList<Item> inputItem = new ArrayList<>();
    ArrayList<Item> resultItem = new ArrayList<>();
    Map<Item, Integer> fuel = new HashMap<>();

    static void add(Item input, Item result) {
        inputItem.add(input);
        resultItem.add(result);
    }

    static boolean match(DefaultedList<ItemStack> de) {
        for (int i = 0; i < inputItem.size(); i++) {
            Item input = inputItem.get(i);
            Item result = resultItem.get(i);
            if (de.get(0).getItem() == input) {
                de.get(0).decrement(1);
                if (de.get(2).isEmpty()) {
                    de.set(2, new ItemStack(result));
                } else {
                    de.get(2).increment(1);
                }
                return true;
            }
        }
        return false;
    }

    static boolean check(DefaultedList<ItemStack> de) {
        for (Item input : inputItem) {
            if (de.get(0).getItem() == input) {
                return true;
            }
        }
        return false;
    }

    static boolean fuel(DefaultedList<ItemStack> de, DustMachineEntity e) {
        Set<Item> items = fuel.keySet();
        for (Item i : items) {
            Integer integer = fuel.get(i);
            if (de.get(1).getItem() == Items.STICK && de.get(1).getCount() >= 2) {
                e.addFuel(integer);
                de.get(1).decrement(2);
                break;
            }
            if (i == de.get(1).getItem() && de.get(1).getItem() != Items.STICK) {
                e.addFuel(integer);
                if (i == Items.LAVA_BUCKET) {
                    de.set(1, new ItemStack(Items.BUCKET, 1));
                } else {
                    de.get(1).decrement(1);
                }
                break;
            }
        }
        return false;
    }

}
