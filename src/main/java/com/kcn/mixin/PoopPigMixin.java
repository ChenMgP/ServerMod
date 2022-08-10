package com.kcn.mixin;

import com.kcn.Main;
import com.kcn.data.PoopValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class PoopPigMixin extends MobEntity {

    private final PoopValue sv = new PoopValue();
    private final AnimalEntity s = ((AnimalEntity) (Object) this);

    protected PoopPigMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (s instanceof PigEntity) {
            nbt.putInt("time", sv.time);
            nbt.putBoolean("can_poop", sv.can_poop);
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (s instanceof PigEntity) {
            sv.time = nbt.getInt("time");
            sv.can_poop = nbt.getBoolean("can_poop");
        }
    }

    @Inject(at = @At("HEAD"), method = "isBreedingItem")
    public void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        sv.can_poop = true;
    }

    @Override
    public void mobTick() {
        if (s instanceof PigEntity) {
            if (sv.time == 10 * 20) {
                s.dropItem(Main.POOP);
                sv.time = 0;
                sv.can_poop = false;
            } else if (sv.can_poop) {
                sv.time++;
            }
        }
        super.mobTick();
    }

}
