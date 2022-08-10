package com.kcn.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

@Mixin(WitherEntity.class)
public abstract class WitherMixin extends LivingEntity {
    public WitherMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    private static Path path = FabricLoader.getInstance().getGameDir();

    @Inject(at = @At("HEAD"), method = "canDestroy", cancellable = true)
    private static void canDestroy(BlockState block, CallbackInfoReturnable<Boolean> cir) throws Exception{
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(new FileReader(path + "KCN\\data.json"));
        if (object.get("witherBreak") == null) {
            object.addProperty("witherBreak", true);
            String s = object.toString();
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\data.json"));
            bw.write(s);
            bw.close();
        } else {
            boolean witherBreak = object.get("witherBreak").getAsBoolean();
            if (!witherBreak) {
                cir.cancel();
                cir.setReturnValue(false);
            }
        }
    }
}
