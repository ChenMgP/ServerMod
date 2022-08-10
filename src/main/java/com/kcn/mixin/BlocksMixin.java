package com.kcn.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlocksMixin {

    @Inject(at = @At("HEAD"), method = "onLandedUpon", cancellable = true)
    private void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player) {
            if (!player.isCreative()) {
                BlockPos fallPos = new BlockPos(player.getX(), player.getY() - 1, player.getZ());
                Block b = world.getBlockState(fallPos).getBlock();
                if (b == Blocks.ACACIA_LEAVES || b == Blocks.AZALEA_LEAVES || b == Blocks.BIRCH_LEAVES || b == Blocks.DARK_OAK_LEAVES || b == Blocks.FLOWERING_AZALEA_LEAVES || b == Blocks.JUNGLE_LEAVES || b == Blocks.OAK_LEAVES || b == Blocks.SPRUCE_LEAVES) {
                    int i = (int) (Math.random() * 7);
                    if (i == 2) {
                        ci.cancel();
                        player.playSound(SoundEvents.ENTITY_PLAYER_SMALL_FALL, 1, 1);
                    }
                }
            }
        }
    }
}