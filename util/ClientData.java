package com.kcn.util;

import com.kcn.Main;
import com.kcn.data.InF.CommandMethod;
import com.kcn.data.InF.Dust;
import com.kcn.data.InF.Fuel;
import com.kcn.data.InF.StrongRecipe;
import com.kcn.data.Tags;
import com.kcn.items.ModItems;
import net.minecraft.item.Items;

public class ClientData implements CommandMethod, StrongRecipe, Dust{

    public static void ClientDataRegister() {
        addDust();
        addFuel();
        addStrongRecipe();
        addTimeFuel();
    }

    public static void addDust() {
        Dust.add(ModItems.ESSENCE_STONE, ModItems.ESSENCE_DUST);
        Dust.add(Items.IRON_INGOT, ModItems.IRON_DUST);
        Dust.add(Items.GOLD_INGOT, ModItems.GOLD_DUST);
        Dust.add(Items.DIAMOND, ModItems.DIAMOND_DUST);
        Dust.add(Items.LAPIS_LAZULI, ModItems.LAPIS_LAZULI_DUST);
        Dust.add(Items.COAL, ModItems.COAL_DUST);
        Dust.add(Items.EMERALD, ModItems.EMERALD_DUST);
        Dust.add(Items.COPPER_INGOT, ModItems.COPPER_DUST);
        Dust.add(Tags.ORES, Tags.DUSTED_ITEM, ModItems.CRUSHED_STONE);
        Dust.add(Tags.DEEPSLATE_ORES, Tags.DUSTED_ITEM, ModItems.CRUSHED_DEEPSLATE_STONE);
    }

    public static void addStrongRecipe() {
        StrongRecipe.add(Items.IRON_INGOT, ModItems.IRON_SHEET);
        StrongRecipe.add(Items.GOLD_INGOT, ModItems.GOLD_SHEET);
    }

    public static void addTimeFuel() {
        Fuel.addFuel(Items.COAL, 5);
        Fuel.addFuel(Items.LAVA_BUCKET, 70);
        Fuel.addFuel(Items.COAL_BLOCK, 45);
        Fuel.addFuel(Items.CHARCOAL, 5);
        Fuel.addFuel(Items.STICK, 1);
        Fuel.addFuel(Items.CHEST, 8);
        Fuel.addFuel(Items.CRAFTING_TABLE, 4);
        Fuel.addFuel(Items.JUKEBOX, 10);
        Fuel.addFuel(Items.LADDER, 1);
        Fuel.addFuel(Items.SCAFFOLDING, 1);
        Fuel.addFuel(Items.LOOM, 2);
        Fuel.addFuel(Items.COMPOSTER, 6);
        Fuel.addFuel(Items.BARREL, 8);
        Fuel.addFuel(Items.CARTOGRAPHY_TABLE, 4);
        Fuel.addFuel(Items.FLETCHING_TABLE, 4);
        Fuel.addFuel(Items.BOOKSHELF, 6);
        Fuel.addFuel(Items.LECTERN, 10);
        Fuel.addFuel(Items.TRAPPED_CHEST, 8);
        Fuel.addFuel(Items.NOTE_BLOCK, 8);
        Fuel.addFuel(Items.BLAZE_ROD, 20);
        Fuel.addFuel(Items.DRIED_KELP_BLOCK, 2);
        Fuel.addFuel(Items.FISHING_ROD, 1);
        Fuel.addFuel(Items.BOWL, 1);
        Fuel.addFuel(Items.CROSSBOW, 2);
        Fuel.addFuel(Items.BOW, 1);
        Fuel.addFuel(Tags.WOODS, 4);
        Fuel.addFuel(Tags.LOGS, 4);
        Fuel.addFuel(Tags.STRIPPED_WOODS, 4);
        Fuel.addFuel(Tags.STRIPPED_LOGS, 4);
        Fuel.addFuel(Tags.BUTTONS, 1);
        Fuel.addFuel(Tags.DOORS, 6);
        Fuel.addFuel(Tags.FENCES, 5);
        Fuel.addFuel(Tags.FENCE_GATES, 4);
        Fuel.addFuel(Tags.PLANKS, 1);
        Fuel.addFuel(Tags.PRESSURE_PLATES, 2);
        Fuel.addFuel(Tags.SIGNS, 3);
        Fuel.addFuel(Tags.SLABS, 1);
        Fuel.addFuel(Tags.STAIRS, 2);
        Fuel.addFuel(Tags.TRAPDOORS, 3);
        Fuel.addFuel(Tags.WOODEN_TOOLS, 2);
        Fuel.addFuel(Tags.BOATS, 5);
    }

    public static void addFuel() {
        Fuel.addFuel(Items.COAL);
        Fuel.addFuel(Items.LAVA_BUCKET);
        Fuel.addFuel(Items.COAL_BLOCK);
        Fuel.addFuel(Items.CHARCOAL);
        Fuel.addFuel(Items.STICK);
        Fuel.addFuel(Items.CHEST);
        Fuel.addFuel(Items.CRAFTING_TABLE);
        Fuel.addFuel(Items.JUKEBOX);
        Fuel.addFuel(Items.LADDER);
        Fuel.addFuel(Items.SCAFFOLDING);
        Fuel.addFuel(Items.LOOM);
        Fuel.addFuel(Items.COMPOSTER);
        Fuel.addFuel(Items.BARREL);
        Fuel.addFuel(Items.CARTOGRAPHY_TABLE);
        Fuel.addFuel(Items.FLETCHING_TABLE);
        Fuel.addFuel(Items.BOOKSHELF);
        Fuel.addFuel(Items.LECTERN);
        Fuel.addFuel(Items.TRAPPED_CHEST);
        Fuel.addFuel(Items.NOTE_BLOCK);
        Fuel.addFuel(Items.BLAZE_ROD);
        Fuel.addFuel(Items.DRIED_KELP_BLOCK);
        Fuel.addFuel(Items.FISHING_ROD);
        Fuel.addFuel(Items.BOWL);
        Fuel.addFuel(Items.CROSSBOW);
        Fuel.addFuel(Items.BOW);
        Fuel.addFuel(Tags.WOODS, 4);
        Fuel.addFuel(Tags.LOGS, 4);
        Fuel.addFuel(Tags.STRIPPED_WOODS, 4);
        Fuel.addFuel(Tags.STRIPPED_LOGS, 4);
        Fuel.addFuel(Tags.BUTTONS, 1);
        Fuel.addFuel(Tags.DOORS, 6);
        Fuel.addFuel(Tags.FENCES, 5);
        Fuel.addFuel(Tags.FENCE_GATES, 4);
        Fuel.addFuel(Tags.PLANKS, 1);
        Fuel.addFuel(Tags.PRESSURE_PLATES, 2);
        Fuel.addFuel(Tags.SIGNS, 3);
        Fuel.addFuel(Tags.SLABS, 1);
        Fuel.addFuel(Tags.STAIRS, 2);
        Fuel.addFuel(Tags.TRAPDOORS, 3);
        Fuel.addFuel(Tags.WOODEN_TOOLS, 2);
        Fuel.addFuel(Tags.BOATS, 5);
    }

}
