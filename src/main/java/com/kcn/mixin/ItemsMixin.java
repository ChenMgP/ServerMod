package com.kcn.mixin;

import com.kcn.data.FoodComponents;
import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Items.class)
public class ItemsMixin {

    @Inject(at = @At("HEAD"), method = "register(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", cancellable = true)
    private static void register(String id, Item item, CallbackInfoReturnable<Item> cir) {
        if (Objects.equals(id, "wheat_seeds")) {
            Item WHEAT_SEEDS = (Item) new AliasedBlockItem(Blocks.WHEAT, new Item.Settings().group(ItemGroup.MATERIALS).food(FoodComponents.SEEDS));
            cir.setReturnValue(Registry.register(Registry.ITEM, id, WHEAT_SEEDS));
        } else if (Objects.equals(id, "melon_seeds")) {
            Item MELON_SEEDS = (Item) new AliasedBlockItem(Blocks.MELON_STEM, new Item.Settings().group(ItemGroup.MATERIALS).food(FoodComponents.SEEDS));
            cir.setReturnValue(Registry.register(Registry.ITEM, id, MELON_SEEDS));
        } else if (Objects.equals(id, "beetroot_seeds")) {
            Item BEETROOT_SEEDS = (Item) new AliasedBlockItem(Blocks.BEETROOTS, new Item.Settings().group(ItemGroup.MATERIALS).food(FoodComponents.SEEDS));
            cir.setReturnValue(Registry.register(Registry.ITEM, id, BEETROOT_SEEDS));
        } else if (Objects.equals(id, "pumpkin_seeds")) {
            Item PUMPKIN_SEEDS = (Item) new AliasedBlockItem(Blocks.PUMPKIN_STEM, new Item.Settings().group(ItemGroup.MATERIALS).food(FoodComponents.SEEDS));
            cir.setReturnValue(Registry.register(Registry.ITEM, id, PUMPKIN_SEEDS));
        }
    }
}
