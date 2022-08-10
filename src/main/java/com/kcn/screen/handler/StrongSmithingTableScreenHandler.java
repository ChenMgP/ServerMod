package com.kcn.screen.handler;

import com.kcn.Main;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import static com.kcn.blocks.entities.StrongSmithingTableEntity.c;

public class StrongSmithingTableScreenHandler extends ScreenHandler {
    public Inventory inventory;

    public StrongSmithingTableScreenHandler(int synId, PlayerInventory playerInventory) {
        this(synId, playerInventory, new SimpleInventory(3));
    }

    public StrongSmithingTableScreenHandler(int synId, PlayerInventory playerInventory, Inventory inventory) {
        super(Main.STRONG_SMITHING_TABLE_SCREEN_HANDLER, synId);
        this.inventory = inventory;
        checkSize(inventory, 3);
        this.addSlot(new Slot(this.inventory, 0, 44, 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 82, 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        this.addSlot(new Slot(this.inventory, 2, 122, 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                slots.get(0).getStack().decrement(c);
                slots.get(1).getStack().damage(1, player, c -> c.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                player.playSound(Main.SMITHING_EVENT, 1.0f, 1.0f);
                super.onTakeItem(player, stack);
            }
        });
        int j;
        for (j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
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

    @Override
    public void close(PlayerEntity player) {
        World world = player.getWorld();
        world.spawnEntity(new ItemEntity(world, player.getX(),player.getY(),player.getZ(), this.inventory.getStack(0)));
        this.inventory.setStack(0, ItemStack.EMPTY);
        world.spawnEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), this.inventory.getStack(1)));
        this.inventory.setStack(1, ItemStack.EMPTY);
        this.inventory.setStack(2, ItemStack.EMPTY);
        super.close(player);
    }
}
