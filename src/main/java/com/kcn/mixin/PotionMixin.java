package com.kcn.mixin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Objects;

@Mixin(PotionItem.class)
public class PotionMixin {

    private Path path = FabricLoader.getInstance().getGameDir();

    @Inject(at = @At("HEAD"), method = "finishUsing")
    public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (Objects.equals(stack.getNbt(), Items.POTION.getDefaultStack().getNbt())) {
            try {
                get((PlayerEntity) user, 3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 15, 2));
        } else {
            try {
                get((PlayerEntity) user, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void get(PlayerEntity player, int value) throws Exception {
        String uuid = String.valueOf(player.getUuid());
        JsonParser parser = new JsonParser();
        JsonObject playerObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\health.json"));
        JsonObject object = playerObject.get(uuid).getAsJsonObject();
        int water = object.get("water").getAsInt();
        water = water + value;
        if (water > 20) {
            water = 20;
        }
        object.addProperty("water", water);
        playerObject.add(uuid, object);
        BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\health.json"));
        bw.write(playerObject.toString());
        bw.close();
    }

}
