package com.kcn.blocks.entities;

import com.kcn.Main;
import com.kcn.screen.handler.OxygenScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class OxygenMachineEntity extends LootableContainerBlockEntity {
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int tick = 0;
    private int water = 0;
    private int value = 0;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> tick;
                case 1 -> water;
                case 2 -> value;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    OxygenMachineEntity.this.tick = value;
                case 1:
                    OxygenMachineEntity.this.water = value;
                case 2:
                    OxygenMachineEntity.this.value = value;
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    public OxygenMachineEntity(BlockPos pos, BlockState state) {
        super(Main.OXYGEN_MACHINE_ENTITY, pos, state);
    }

    public static void tick(OxygenMachineEntity entity) {
        if (entity.inventory.get(0).getItem() == Items.WATER_BUCKET && entity.water < 25) {
            entity.water++;
            entity.inventory.set(0, new ItemStack(Items.BUCKET, 1));
        }
        if (entity.water > 0 && entity.inventory.get(1).getItem() == Items.GLASS_BOTTLE && entity.inventory.get(2).getCount() < 16) {
            entity.tick++;
            if (entity.tick % 20 == 0) {
                entity.value++;
            }
            if (entity.tick == 20 * 8) {
                entity.tick = 0;
                entity.value = 0;
                entity.water = entity.water - 1;
                entity.inventory.get(1).decrement(1);
                if (entity.inventory.get(2).isEmpty()) {
                    entity.inventory.set(2, new ItemStack(Main.OXYGEN_BOTTLE, 1));
                } else {
                    entity.inventory.get(2).increment(1);
                }
            }
        } else {
            entity.tick = 0;
            entity.value = 0;
        }
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("Oxygen Machine");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new OxygenScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new OxygenScreenHandler(i, playerInventory, this, propertyDelegate);
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("value", value);
        nbt.putInt("tick", tick);
        nbt.putInt("water", water);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.value = nbt.getInt("value");
        this.tick = nbt.getInt("tick");
        this.water = nbt.getInt("water");
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }
}
