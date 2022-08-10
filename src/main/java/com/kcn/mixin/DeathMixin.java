package com.kcn.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.text.DecimalFormat;

@Mixin(PlayerEntity.class)
public abstract class DeathMixin {

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci) throws Exception {
        PlayerEntity p = (PlayerEntity) (Object) this;
        if (p.isDead()) {
            String uuid = String.valueOf(p.getUuid());
            Path path = FabricLoader.getInstance().getGameDir();
            JsonParser parser = new JsonParser();
            JsonObject playerObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\death.json"));
            JsonObject object = new JsonObject();
            DecimalFormat df = new DecimalFormat("0.000");
            double x = Double.parseDouble(df.format(p.getPos().x));
            double y = Double.parseDouble(df.format(p.getPos().y));
            double z = Double.parseDouble(df.format(p.getPos().z));
            object.addProperty("x", x);
            object.addProperty("y", y);
            object.addProperty("z", z);
            playerObject.add(uuid, object);
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\death.json"));
            bw.write(playerObject.toString());
            bw.close();
        }
    }
}
