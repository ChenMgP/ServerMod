package com.kcn.data;

import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public final class Tag {

    public ArrayList<Item> load(ArrayList<String> arr) {
        ArrayList<Item> items = new ArrayList<>();
        for (String itemId : arr) {
            Item item = Registry.ITEM.getOrEmpty(new Identifier(itemId)).orElseThrow(() -> new JsonSyntaxException("No such item " + itemId));
            items.add(item);
        }
        return items;
    }

    public boolean forEach(Map<ArrayList<Item>, Item> map, Item item, DefaultedList<ItemStack> de, int slot) {
        Set<ArrayList<Item>> arrayLists = map.keySet();
        for (ArrayList<Item> arr : arrayLists) {
            for (Item i : arr) {
                if (item == i) {
                    if (de.get(3).isEmpty()) {
                        Item item1 = map.get(arr);
                        de.set(slot, new ItemStack(item1));
                    } else {
                        de.get(3).increment(1);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
