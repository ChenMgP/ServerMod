package com.kcn;

import com.kcn.blocks.*;
import com.kcn.blocks.entities.*;
import com.kcn.data.FoodComponents;
import com.kcn.data.GeckoLib;
import com.kcn.effect.ClearEffect;
import com.kcn.entity.ModEntities;
import com.kcn.entity.custom.RaccoonEntity;
import com.kcn.items.OxygenBottle;
import com.kcn.items.PoopItem;
import com.kcn.items.SoupItem;
import com.kcn.recipes.MixinPotion;
import com.kcn.recipes.PoopSoup;
import com.kcn.recipes.RectangularMold;
import com.kcn.screen.handler.DustMachineScreenHandler;
import com.kcn.screen.handler.OxygenScreenHandler;
import com.kcn.screen.handler.RemoveEnchantTableScreenHandler;
import com.kcn.screen.handler.StrongSmithingTableScreenHandler;
import com.kcn.util.ModRegisters;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;


public class Main implements ModInitializer {

    public static final EntityType<RaccoonEntity> RACCOON = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("kcn", "raccoon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, RaccoonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.3f)).build());

    public static final SpawnEggItem RACCOON_SPAWN_EGG = new SpawnEggItem(Main.RACCOON, 0x948e8d, 0x3b3635, new Item.Settings().group(ItemGroup.MISC));
    public static final RectangularEmbryo RECTANGULAR_EMBRYO = new RectangularEmbryo(AbstractBlock.Settings.of(Material.STONE).breakInstantly());
    public static final Item RECTANGULAR_MOLD = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CRUSHED_STONE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CRUSHED_DEEPSLATE_STONE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final RecipeSerializer<PoopSoup> POOP_SOUP_RECIPE = RecipeSerializer.register("crafting_special_poop_soup", new SpecialRecipeSerializer<>(PoopSoup::new));
    public static final RecipeSerializer<MixinPotion> MIXIN_POTION_RECIPE = RecipeSerializer.register("crafting_special_potion", new SpecialRecipeSerializer<>(MixinPotion::new));
    public static final RecipeSerializer<RectangularMold> RECTANGULAR_MOLD_RECIPE = RecipeSerializer.register("crafting_special_rectangular_mold", new SpecialRecipeSerializer<>(RectangularMold::new));
    public static final PoopItem POOP = new PoopItem(new Item.Settings().group(ItemGroup.MISC).food(new FoodComponent.Builder().alwaysEdible().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 30, 2), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 20, 2), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 20, 1), 0.3f).build()));
    public static final Item COAL_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item IRON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item GOLD_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DIAMOND_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item LAPIS_LAZULI_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item EMERALD_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COPPER_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final SmeltingFurnace SMELTING_FURNACE = new SmeltingFurnace(AbstractBlock.Settings.of(Material.METAL).strength(3f));
    public static final DustMachine DUST_MACHINE = new DustMachine(AbstractBlock.Settings.of(Material.METAL).strength(3f));
    public static final RemoveEnchantTable REMOVE_ENCHANT_TABLE = new RemoveEnchantTable(AbstractBlock.Settings.of(Material.METAL).strength(4f).nonOpaque().luminance(s -> 10));
    public static final Item IRON_SHEET = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item GOLD_SHEET = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item HAMMER = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(3).maxDamage(15));
    public static final StrongSmithingTable STRONG_SMITHING_TABLE = new StrongSmithingTable(AbstractBlock.Settings.of(Material.METAL));
    public static final ScreenHandlerType<StrongSmithingTableScreenHandler> STRONG_SMITHING_TABLE_SCREEN_HANDLER;
    public static final ScreenHandlerType<OxygenScreenHandler> OXYGEN_SCREEN_HANDLER;
    public static final ScreenHandlerType<DustMachineScreenHandler> DUST_MACHINE_SCREEN_HANDLER;
    public static final ScreenHandlerType<RemoveEnchantTableScreenHandler> REMOVE_ENCHANT_TABLE_SCREEN_HANDLER;
    public static final Identifier SMITHING_ID = new Identifier("kcn:smithing");
    public static final SoundEvent SMITHING_EVENT = new SoundEvent(SMITHING_ID);
    public static final Block MACHINE_FRAME = new Block(AbstractBlock.Settings.of(Material.METAL).strength(3f));
    public static final OxygenMachine OXYGEN_MACHINE = new OxygenMachine(AbstractBlock.Settings.of(Material.GLASS).nonOpaque().solidBlock(Main::never).strength(1.0f));
    public static final Block TEMPERED_GLASS = new Block(AbstractBlock.Settings.of(Material.GLASS).strength(1.5f, 20f).sounds(BlockSoundGroup.GLASS).nonOpaque().solidBlock(Main::never).allowsSpawning(Main::never));
    public static final Container CONTAINER = new Container(AbstractBlock.Settings.of(Material.GLASS).strength(1.0f).nonOpaque().solidBlock(Main::never).allowsSpawning(Main::never));
    public static final Item ESSENCE_DUST = new Item(new Item.Settings().rarity(Rarity.UNCOMMON).group(ItemGroup.MISC));
    public static final Item ESSENCE_STONE = new Item(new Item.Settings().rarity(Rarity.UNCOMMON).group(ItemGroup.MISC));
    public static final Item BATTERY = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CONTROL_PANEL = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final OxygenBottle OXYGEN_BOTTLE = new OxygenBottle(new Item.Settings().group(ItemGroup.MISC).maxCount(16));
    public static final Item BAKED_WHEAT_SEEDS = new Item(new Item.Settings().food(FoodComponents.BAKED_SEEDS).group(ItemGroup.FOOD));
    public static final Item BAKED_MELON_SEEDS = new Item(new Item.Settings().food(FoodComponents.BAKED_SEEDS).group(ItemGroup.FOOD));
    public static final Item BAKED_PUMPKIN_SEEDS = new Item(new Item.Settings().food(FoodComponents.BAKED_SEEDS).group(ItemGroup.FOOD));
    public static final Item BAKED_BEETROOT_SEEDS = new Item(new Item.Settings().food(FoodComponents.BAKED_SEEDS).group(ItemGroup.FOOD));
    public static final ClearEffect CLEAR = new ClearEffect();
    public static final Potion HOLY_WATER = new Potion(new StatusEffectInstance(Main.CLEAR, 1));
    public static final Potion WITHER = new Potion(new StatusEffectInstance(StatusEffects.WITHER, 20 * 40, 1));
    public static final SoupItem MEAT_SOUP = new SoupItem(new Item.Settings().food(new FoodComponent.Builder().hunger(20).saturationModifier(1.0f).statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 30, 1), 1.0f).build()).maxCount(5).group(ItemGroup.FOOD));
    public static final SoupItem FISH_SOUP = new SoupItem(new Item.Settings().food(new FoodComponent.Builder().hunger(10).saturationModifier(0.8f).statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 15, 0), 1.0f).build()).maxCount(5).group(ItemGroup.FOOD));
    public static final SoupItem VEGETABLE_SOUP = new SoupItem(new Item.Settings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.3f).build()).maxCount(5).group(ItemGroup.FOOD));
    public static final SoupItem POOP_SOUP = new SoupItem(new Item.Settings().food(new FoodComponent.Builder().alwaysEdible().hunger(4).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20 * 30, 2), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 20, 2), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 20, 1), 0.3f).build()).group(ItemGroup.MISC));
    public static BlockEntityType<OxygenMachineEntity> OXYGEN_MACHINE_ENTITY;
    public static BlockEntityType<StrongSmithingTableEntity> STRONG_SMITHING_TABLE_ENTITY;
    public static BlockEntityType<DustMachineEntity> DUST_MACHINE_ENTITY;
    public static BlockEntityType<RemoveEnchantTableEntity> REMOVE_ENCHANT_TABLE_ENTITY;
    public static BlockEntityType<SmeltingFurnaceEntity> SMELTING_FURNACE_ENTITY;

    static {
        REMOVE_ENCHANT_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("kcn", "remove_enchant_table"), RemoveEnchantTableScreenHandler::new);
        DUST_MACHINE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("kcn", "dust_machine"), DustMachineScreenHandler::new);
        OXYGEN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("kcn", "oxygen_machine"), OxygenScreenHandler::new);
        STRONG_SMITHING_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("kcn", "strong_smithing_table"), StrongSmithingTableScreenHandler::new);
    }


    private static boolean never(BlockState state, BlockView view, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    private static boolean never(BlockState state, BlockView view, BlockPos pos) {
        return false;
    }


    @Override
    public void onInitialize() {

        ModRegisters.registerModStuffs();

        SMELTING_FURNACE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("kcn", "smelting_furnace_entity"), FabricBlockEntityTypeBuilder.create(SmeltingFurnaceEntity::new, SMELTING_FURNACE).build());
        REMOVE_ENCHANT_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("kcn", "remove_enchant_table_entity"), FabricBlockEntityTypeBuilder.create(RemoveEnchantTableEntity::new, REMOVE_ENCHANT_TABLE).build());
        DUST_MACHINE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("kcn", "dust_machine_entity"), FabricBlockEntityTypeBuilder.create(DustMachineEntity::new, DUST_MACHINE).build());
        STRONG_SMITHING_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("kcn", "strong_smithing_table_entity"), FabricBlockEntityTypeBuilder.create(StrongSmithingTableEntity::new, STRONG_SMITHING_TABLE).build());
        OXYGEN_MACHINE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("kcn", "oxygen_machine_entity"), FabricBlockEntityTypeBuilder.create(OxygenMachineEntity::new, OXYGEN_MACHINE).build());
        Registry.register(Registry.ITEM, new Identifier("kcn", "iron_sheet"), IRON_SHEET);
        Registry.register(Registry.ITEM, new Identifier("kcn", "gold_sheet"), GOLD_SHEET);
        Registry.register(Registry.POTION, new Identifier("kcn", "holy_water"), HOLY_WATER);
        Registry.register(Registry.POTION, new Identifier("kcn", "wither"), WITHER);
        Registry.register(Registry.ITEM, new Identifier("kcn", "essence_dust"), ESSENCE_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "essence_stone"), ESSENCE_STONE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "meat_soup"), MEAT_SOUP);
        Registry.register(Registry.ITEM, new Identifier("kcn", "fish_soup"), FISH_SOUP);
        Registry.register(Registry.ITEM, new Identifier("kcn", "vegetable_soup"), VEGETABLE_SOUP);
        Registry.register(Registry.ITEM, new Identifier("kcn", "baked_wheat_seeds"), BAKED_WHEAT_SEEDS);
        Registry.register(Registry.ITEM, new Identifier("kcn", "baked_melon_seeds"), BAKED_MELON_SEEDS);
        Registry.register(Registry.ITEM, new Identifier("kcn", "baked_pumpkin_seeds"), BAKED_PUMPKIN_SEEDS);
        Registry.register(Registry.ITEM, new Identifier("kcn", "baked_beetroot_seeds"), BAKED_BEETROOT_SEEDS);
        Registry.register(Registry.ITEM, new Identifier("kcn", "oxygen_bottle"), OXYGEN_BOTTLE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "battery"), BATTERY);
        Registry.register(Registry.ITEM, new Identifier("kcn", "control_panel"), CONTROL_PANEL);
        Registry.register(Registry.ITEM, new Identifier("kcn", "coal_dust"), COAL_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "iron_dust"), IRON_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "gold_dust"), GOLD_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "diamond_dust"), DIAMOND_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "lapis_lazuli_dust"), LAPIS_LAZULI_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "emerald_dust"), EMERALD_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "copper_dust"), COPPER_DUST);
        Registry.register(Registry.ITEM, new Identifier("kcn", "hammer"), HAMMER);
        Registry.register(Registry.ITEM, new Identifier("kcn", "poop"), POOP);
        Registry.register(Registry.ITEM, new Identifier("kcn", "poop_soup"), POOP_SOUP);
        Registry.register(Registry.ITEM, new Identifier("kcn", "crushed_stone"), CRUSHED_STONE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "crushed_deepslate"), CRUSHED_DEEPSLATE_STONE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "rectangular_mold"), RECTANGULAR_MOLD);
        Registry.register(Registry.ITEM, new Identifier("kcn", "raccoon_spawn_egg"), RACCOON_SPAWN_EGG);
        Registry.register(Registry.BLOCK, new Identifier("kcn", "rectangular_embryo"), RECTANGULAR_EMBRYO);
        Registry.register(Registry.ITEM, new Identifier("kcn", "rectangular_embryo"), new BlockItem(RECTANGULAR_EMBRYO, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "container"), CONTAINER);
        Registry.register(Registry.ITEM, new Identifier("kcn", "container"), new BlockItem(CONTAINER, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "tempered_glass"), TEMPERED_GLASS);
        Registry.register(Registry.ITEM, new Identifier("kcn", "tempered_glass"), new BlockItem(TEMPERED_GLASS, new Item.Settings()));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "oxygen_machine"), OXYGEN_MACHINE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "oxygen_machine"), new BlockItem(OXYGEN_MACHINE, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "strong_smithing_table"), STRONG_SMITHING_TABLE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "strong_smithing_table"), new BlockItem(STRONG_SMITHING_TABLE, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "machine_frame"), MACHINE_FRAME);
        Registry.register(Registry.ITEM, new Identifier("kcn", "machine_frame"), new BlockItem(MACHINE_FRAME, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "dust_machine"), DUST_MACHINE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "dust_machine"), new BlockItem(DUST_MACHINE, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "remove_enchant_table"), REMOVE_ENCHANT_TABLE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "remove_enchant_table"), new BlockItem(REMOVE_ENCHANT_TABLE, new Item.Settings().group(ItemGroup.MISC)));
        Registry.register(Registry.BLOCK, new Identifier("kcn", "smelting_furnace"), SMELTING_FURNACE);
        Registry.register(Registry.ITEM, new Identifier("kcn", "smelting_furnace"), new BlockItem(SMELTING_FURNACE, new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(Registry.STATUS_EFFECT, new Identifier("kcn", "clear_effect"), CLEAR);
        Registry.register(Registry.SOUND_EVENT, SMITHING_ID, SMITHING_EVENT);
    }
}
