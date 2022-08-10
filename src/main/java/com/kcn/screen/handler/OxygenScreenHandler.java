package com.kcn.screen.handler;

import com.kcn.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class OxygenScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    public Inventory inventory;

    public OxygenScreenHandler(int synId, PlayerInventory playerInventory) {
        this(synId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(3));
    }

    public OxygenScreenHandler(int synId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(Main.OXYGEN_SCREEN_HANDLER, synId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
        checkSize(inventory, 3);
        checkDataCount(propertyDelegate, 3);
        this.addSlot(new Slot(this.inventory, 0, 77, 29) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.WATER_BUCKET;
            }
        });
        this.addSlot(new Slot(this.inventory, 1, 107, 29) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.GLASS_BOTTLE;
            }
        });
        this.addSlot(new Slot(this.inventory, 2, 147, 29) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
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

    public int getValue() {
        return this.propertyDelegate.get(2);
    }

    public int getWater() {
        return this.propertyDelegate.get(1);
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
