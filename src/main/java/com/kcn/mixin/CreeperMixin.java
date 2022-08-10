package com.kcn.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

@Mixin(CreeperEntity.class)
public abstract class CreeperMixin extends Entity {

    public CreeperMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    Path path = FabricLoader.getInstance().getGameDir();

    @Inject(at = @At("HEAD"), method = "explode", cancellable = true)
    private void explode(CallbackInfo ci) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(new FileReader(path + "KCN\\data.json"));
        if (object.get("creeperExplosion") == null) {
            object.addProperty("creeperExplosion", true);
            String s = object.toString();
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\data.json"));
            bw.write(s);
            bw.close();
        } else {
            boolean creeperExplosion = object.get("creeperExplosion").getAsBoolean();
            if (!creeperExplosion) {
                ci.cancel();
                this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 6f, Explosion.DestructionType.NONE);
                this.discard();
            }
        }
    }
}
