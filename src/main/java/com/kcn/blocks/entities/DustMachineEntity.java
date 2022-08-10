package com.kcn.blocks.entities;

import com.kcn.Main;
import com.kcn.data.InF.Dust;
import com.kcn.screen.handler.DustMachineScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class DustMachineEntity extends LootableContainerBlockEntity implements Dust {
    private int fuel;

    public void addFuel(int fuel) {
        this.fuel = this.fuel + fuel;
    }

    private int time;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> fuel;
                case 1 -> time;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    DustMachineEntity.this.fuel = value;
                case 1:
                    DustMachineEntity.this.time = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };
    private DefaultedList<ItemStack> inv = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public DustMachineEntity(BlockPos blockPos, BlockState blockState) {
        super(Main.DUST_MACHINE_ENTITY, blockPos, blockState);
    }

    public static void tick(DustMachineEntity entity) {
        if (!entity.inv.get(1).isEmpty()) {
            Dust.fuel(entity.inv, entity);
        }
        if (entity.inv.get(0).isEmpty() || entity.inv.get(2).getCount() >= entity.inv.get(2).getMaxCount() || entity.fuel == 0) {
            entity.time = 0;
        }
        if (Dust.check(entity.inv)) {
            entity.time++;
            if (entity.time == 20 * 12) {
                entity.time = 0;
                entity.fuel--;
                Dust.match(entity.inv);
            }
        } else {
            entity.time = 0;
        }
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inv;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inv = list;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("Dust Machine");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new DustMachineScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inv);
        nbt.putInt("fuel", fuel);
        nbt.putInt("time", time);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.fuel = nbt.getInt("fuel");
        this.time = nbt.getInt("time");
        Inventories.readNbt(nbt, inv);
    }
}
