package com.kcn.screen.handler;

import com.kcn.Main;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.Objects;

public class RemoveEnchantTableScreenHandler extends ScreenHandler {
    public Inventory inventory;

    public RemoveEnchantTableScreenHandler(int synId, PlayerInventory playerInventory) {
        this(synId, playerInventory, new SimpleInventory(3));
    }

    public RemoveEnchantTableScreenHandler(int synId, PlayerInventory playerInventory, Inventory inventory) {
        super(Main.REMOVE_ENCHANT_TABLE_SCREEN_HANDLER, synId);
        this.inventory = inventory;
        checkSize(inventory, 3);
        this.addSlot(new Slot(this.inventory, 0, 56, 17) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 56, 53) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.BOOK;
            }
        });
        this.addSlot(new Slot(this.inventory, 2, 106, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                World world = player.getWorld();
                if (!world.isClient) {
                    int i = (int) (Math.random() * 50 + 1);
                    int j = (int) (Math.random() * 500 + 1);
                    if (player.totalExperience >= j) {
                        player.addExperience(-j);
                    } else {
                        player.addExperience(-j);
                        player.setHealth(player.getHealth() - 4);
                    }
                    Item item = inventory.getStack(0).getItem();
                    int damage0 = Objects.requireNonNull(inventory.getStack(0).getNbt()).getInt("Damage");
                    int broke = Math.abs(damage0) + Math.abs(i);
                    if (broke < inventory.getStack(0).getMaxDamage()) {
                        inventory.setStack(0, new ItemStack(item));
                        Objects.requireNonNull(inventory.getStack(0).getNbt()).putInt("Damage", Math.abs(broke));
                    } else {
                        inventory.setStack(0, ItemStack.EMPTY);
                        player.playSound(SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 3.0F, 1.0F);
                    }
                    inventory.getStack(1).decrement(1);
                    super.onTakeItem(player, stack);
                }
            }
        });
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void close(PlayerEntity player) {
        World world = player.getWorld();
        world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), this.inventory.getStack(0)));
        this.inventory.setStack(0, ItemStack.EMPTY);
        world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), this.inventory.getStack(1)));
        this.inventory.setStack(1, ItemStack.EMPTY);
        this.inventory.setStack(2, ItemStack.EMPTY);
        super.close(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            ItemStack itemStack3 = this.inventory.getStack(0);
            ItemStack itemStack4 = this.inventory.getStack(1);
            if (index == 2) {
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index == 0 || index == 1 ? !this.insertItem(itemStack2, 3, 39, false) : (itemStack3.isEmpty() || itemStack4.isEmpty() ? !this.insertItem(itemStack2, 0, 2, false) : (index >= 3 && index < 30 ? !this.insertItem(itemStack2, 30, 39, false) : index >= 30 && index < 39 && !this.insertItem(itemStack2, 3, 30, false)))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }
}
