package com.kcn.blocks.entities;

import com.kcn.Main;
import com.kcn.data.InF.StrongRecipe;
import com.kcn.screen.handler.StrongSmithingTableScreenHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.nio.file.Path;


public class StrongSmithingTableEntity
        extends LootableContainerBlockEntity
        implements StrongRecipe {
    public static Integer c = 1;
    protected static Path path = FabricLoader.getInstance().getGameDir();
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public StrongSmithingTableEntity(BlockPos pos, BlockState state) {
        super(Main.STRONG_SMITHING_TABLE_ENTITY, pos, state);
    }

    public static void tick(StrongSmithingTableEntity entity) throws Exception {
        StrongRecipe.match(entity.inventory);
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
        return new TranslatableText("Strong Smithing Table");
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new StrongSmithingTableScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }
}

