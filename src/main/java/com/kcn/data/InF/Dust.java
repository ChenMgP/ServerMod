package com.kcn.data.InF;

import com.google.gson.JsonSyntaxException;
import com.kcn.blocks.entities.DustMachineEntity;
import com.kcn.data.Tag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface Dust extends Fuel {

    ArrayList<Item> inputItem = new ArrayList<>();
    ArrayList<Item> resultItem = new ArrayList<>();
    Map<ArrayList<Item>, Item> afterproductItem = new HashMap<>();
    Map<Item, Integer> fuel = Fuel.fuelTime;

    static void add(Item input, Item result) {
        inputItem.add(input);
        resultItem.add(result);
    }

    static void add(ArrayList<String> arr, Item result) {
        Tag tag = new Tag();
        ArrayList<Item> items = tag.load(arr);
        inputItem.addAll(items);
        resultItem.add(result);
    }

    static void add(ArrayList<String> arr, ArrayList<String> result, Item afterproduct) {
        Tag tag = new Tag();
        ArrayList<Item> items = tag.load(arr);
        ArrayList<Item> items1 = tag.load(result);
        inputItem.addAll(items);
        resultItem.addAll(items1);
        afterproductItem.put(items, afterproduct);
    }

    static void match(DefaultedList<ItemStack> de) {
        for (int i = 0; i < inputItem.size(); i++) {
            Item input = inputItem.get(i);
            Item result = resultItem.get(i);
            if (de.get(0).getItem() == input) {
                new Tag().forEach(afterproductItem, input, de, 3);
                de.get(0).decrement(1);
                if (de.get(2).isEmpty()) {
                    de.set(2, new ItemStack(result));
                } else {
                    de.get(2).increment(1);
                }
            }
        }
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

    static boolean forEach(ArrayList<String> arr, DefaultedList<ItemStack> de, int slot) {
        for (String itemId : arr) {
            Item item = Registry.ITEM.getOrEmpty(new Identifier(itemId)).orElseThrow(() -> new JsonSyntaxException("No such item " + itemId));
            if (item == de.get(slot).getItem()) {
                return true;
            }
        }
        return false;
    }
}
