package com.kcn.mixin;

import com.kcn.Main;
import com.kcn.data.PoopValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public class PoopSheepGrassMixin {

    private final PoopValue sv = new PoopValue();
    private final Entity s = ((Entity) (Object) this);

    @Inject(at = @At("TAIL"), method = "mobTick")
    protected void mobTick(CallbackInfo ci) {
        if (sv.time == 10 * 20) {
            s.dropItem(Main.POOP);
            sv.time = 0;
            sv.can_poop = false;
        } else if (sv.can_poop) {
            sv.time++;
        }
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("time", sv.time);
        nbt.putBoolean("can_poop", sv.can_poop);
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        sv.time = nbt.getInt("time");
        sv.can_poop = nbt.getBoolean("can_poop");
    }

    @Inject(at = @At("HEAD"), method = "onEatingGrass")
    public void onEatingGrass(CallbackInfo ci) {
        sv.can_poop = true;
    }

}
