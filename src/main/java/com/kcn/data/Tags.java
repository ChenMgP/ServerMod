package com.kcn.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public final class Tags {

    public static final ArrayList<String> BOATS = Tags.register("minecraft:oak_boat", "minecraft:spruce_boat", "minecraft:birch_boat", "minecraft:jungle_boat", "minecraft:acacia_boat", "minecraft:dark_oak_boat");
    public static final ArrayList<String> BUTTONS = Tags.register("minecraft:oak_button", "minecraft:spruce_button", "minecraft:birch_button", "minecraft:jungle_button", "minecraft:acacia_button", "minecraft:dark_oak_button");
    public static final ArrayList<String> DEEPSLATE_ORES = Tags.register("minecraft:deepslate_iron_ore", "minecraft:deepslate_gold_ore", "minecraft:deepslate_coal_ore", "minecraft:deepslate_diamond_ore", "minecraft:deepslate_redstone_ore", "minecraft:deepslate_copper_ore", "minecraft:deepslate_emerald_ore", "minecraft:deepslate_lapis_ore");
    public static final ArrayList<String> DOORS = Tags.register("minecraft:oak_door", "minecraft:spruce_door", "minecraft:birch_door", "minecraft:jungle_door", "minecraft:acacia_door", "minecraft:dark_oak_door");
    public static final ArrayList<String> FENCE_GATES = Tags.register("minecraft:oak_fence_gate", "minecraft:spruce_fence_gate", "minecraft:birch_fence_gate", "minecraft:jungle_fence_gate", "minecraft:acacia_fence_gate", "minecraft:dark_oak_fence_gate");
    public static final ArrayList<String> FENCES = Tags.register("minecraft:oak_fence", "minecraft:spruce_fence", "minecraft:birch_fence", "minecraft:jungle_fence", "minecraft:acacia_fence", "minecraft:dark_oak_fence");
    public static final ArrayList<String> ORES = Tags.register("minecraft:iron_ore", "minecraft:gold_ore", "minecraft:coal_ore", "minecraft:diamond_ore", "minecraft:redstone_ore", "minecraft:copper_ore", "minecraft:emerald_ore", "minecraft:lapis_ore");
    public static final ArrayList<String> DUSTED_ITEM = Tags.register("minecraft:raw_iron", "minecraft:raw_gold", "minecraft:coal", "minecraft:diamond", "minecraft:redstone", "minecraft:raw_copper", "minecraft:emerald", "minecraft:lapis_lazuli");
    public static final ArrayList<String> PLANKS = Tags.register("minecraft:oak_planks", "minecraft:spruce_planks", "minecraft:birch_planks", "minecraft:jungle_planks", "minecraft:acacia_planks", "minecraft:dark_oak_planks");
    public static final ArrayList<String> PRESSURE_PLATES = Tags.register("minecraft:oak_pressure_plate", "minecraft:spruce_pressure_plate", "minecraft:birch_pressure_plate", "minecraft:jungle_pressure_plate", "minecraft:acacia_pressure_plate", "minecraft:dark_oak_pressure_plate");
    public static final ArrayList<String> SIGNS = Tags.register("minecraft:oak_sign", "minecraft:spruce_sign", "minecraft:birch_sign", "minecraft:jungle_sign", "minecraft:acacia_sign", "minecraft:dark_oak_sign");
    public static final ArrayList<String> SLABS = Tags.register("minecraft:oak_slab", "minecraft:spruce_slab", "minecraft:birch_slab", "minecraft:jungle_slab", "minecraft:acacia_slab", "minecraft:dark_oak_slab");
    public static final ArrayList<String> STAIRS = Tags.register("minecraft:oak_stairs", "minecraft:spruce_stairs", "minecraft:birch_stairs", "minecraft:jungle_stairs", "minecraft:acacia_stairs", "minecraft:dark_oak_stairs");
    public static final ArrayList<String> TRAPDOORS = Tags.register("minecraft:oak_trapdoor", "minecraft:spruce_trapdoor", "minecraft:birch_trapdoor", "minecraft:jungle_trapdoor", "minecraft:acacia_trapdoor", "minecraft:dark_oak_trapdoor");
    public static final ArrayList<String> WOODEN_TOOLS = Tags.register("minecraft:wooden_sword", "minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_shovel", "minecraft:wooden_hoe");
    public static final ArrayList<String> LOGS = Tags.register("minecraft:oak_log", "minecraft:spruce_log", "minecraft:birch_log", "minecraft:jungle_log", "minecraft:acacia_log", "minecraft:dark_oak_log");
    public static final ArrayList<String> STRIPPED_LOGS = Tags.register("minecraft:stripped_oak_log", "minecraft:stripped_spruce_log", "minecraft:stripped_birch_log", "minecraft:stripped_jungle_log", "minecraft:stripped_acacia_log", "minecraft:stripped_dark_oak_log");
    public static final ArrayList<String> WOODS = Tags.register("minecraft:oak_wood", "minecraft:spruce_wood", "minecraft:birch_wood", "minecraft:jungle_wood", "minecraft:acacia_wood", "minecraft:dark_oak_wood");
    public static final ArrayList<String> STRIPPED_WOODS = Tags.register("minecraft:stripped_oak_wood", "minecraft:stripped_spruce_wood", "minecraft:stripped_birch_wood", "minecraft:stripped_jungle_wood", "minecraft:stripped_acacia_wood", "minecraft:stripped_dark_oak_wood");

    private Tags() {
    }

    @Contract("_ -> new")
    private static @NotNull ArrayList<String> register(String... id) {
        return new ArrayList<>(Arrays.asList(id));
    }
}
