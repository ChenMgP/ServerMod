package com.kcn.blocks.entities;

import com.kcn.Main;
import com.kcn.screen.handler.RemoveEnchantTableScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;

public class RemoveEnchantTableEntity extends LootableContainerBlockEntity {
    DefaultedList<ItemStack> inv = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public RemoveEnchantTableEntity(BlockPos blockPos, BlockState blockState) {
        super(Main.REMOVE_ENCHANT_TABLE_ENTITY, blockPos, blockState);
    }

    public static void tick(RemoveEnchantTableEntity e) {
        if (!e.inv.get(0).isEmpty() && !e.inv.get(1).isEmpty()) {
            ItemStack stack = e.inv.get(0);
            if (stack.hasEnchantments()) {
                Map<Enchantment, Integer> map = EnchantmentHelper.get(stack);
                ItemStack book = Items.ENCHANTED_BOOK.getDefaultStack();
                EnchantmentHelper.set(map, book);
                e.inv.set(2, book);
            }
        } else {
            e.inv.set(2, ItemStack.EMPTY);
        }
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return inv;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        inv = list;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("RemoveEnchantTableEntity");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new RemoveEnchantTableScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inv);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inv);
    }
}
