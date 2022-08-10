package com.kcn.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

@Mixin(WitherSkullEntity.class)
public abstract class WitherSkullMixin extends Entity {
    public WitherSkullMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    Path path = FabricLoader.getInstance().getGameDir();

    @Inject(at = @At("HEAD"),method = "onCollision", cancellable = true)
    private void onCollision(HitResult hitResult, CallbackInfo ci) throws Exception {
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
                ci.cancel();
                this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1f, Explosion.DestructionType.NONE);
                this.discard();
            }
        }
    }
}
