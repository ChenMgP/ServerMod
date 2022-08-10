package com.kcn.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Blocks.class)
public class BedRockMixin {
    @Inject(at = @At("HEAD"), method = "register", cancellable = true)
    private static void register(String id, Block block, CallbackInfoReturnable<Block> cir) {
        if (Objects.equals(id, "bedrock")) {
            Block B = new Block(AbstractBlock.Settings.of(Material.STONE).strength(1.0f));
            cir.setReturnValue((Block) Registry.register(Registry.BLOCK, id, B));
        }
    }
}