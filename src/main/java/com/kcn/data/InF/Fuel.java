package com.kcn.data.InF;

import com.kcn.data.Tag;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Fuel {

    ArrayList<Item> fuel = new ArrayList<>();
    Map<Item, Integer> fuelTime = new HashMap<>();

     static void addFuel(Item item) {
        fuel.add(item);
    }

    static void addFuel(Item item, Integer second) {
         fuelTime.put(item, second);
    }

    static void addFuel(ArrayList<String> arr, Integer value) {
        Tag tag = new Tag();
        ArrayList<Item> items = tag.load(arr);
        for (Item item : items) {
            fuelTime.put(item, value);
        }
    }

    static void addFuel(ArrayList<String> arr) {
        Tag tag = new Tag();
        ArrayList<Item> items = tag.load(arr);
        fuel.addAll(items);
    }
}
