package com.kcn.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class WaterData {

    private final int water = 10;

    public int getWater(NbtCompound nbt) {
        return nbt.getInt("water");
    }

    public void saveWater(NbtCompound nbt) {
        nbt.putInt("water", water);
    }

    public void setWater(NbtCompound nbt, int value) {
        nbt.putInt("water", value);
    }

    public void registerWater(PlayerEntity p) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("water", 10);
        p.writeNbt(nbt);
    }
}
