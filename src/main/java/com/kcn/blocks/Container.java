package com.kcn.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Container extends TransparentBlock {
    private static final IntProperty LAVA = IntProperty.of("lava", 0, 5);
    private static final IntProperty WATER = IntProperty.of("water", 0, 5);
    private static final IntProperty MILK = IntProperty.of("milk", 0, 5);
    private static final BooleanProperty OTHER = BooleanProperty.of("other");

    public Container(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(LAVA, 0).with(WATER, 0).with(MILK, 0).with(OTHER, true));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAVA, WATER, MILK, OTHER);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item hold = player.getMainHandStack().getItem();
        if (state.get(LAVA) < 5 && state.get(WATER) < 5 && state.get(MILK) < 5) {
            if (hold == Items.LAVA_BUCKET && state.get(WATER) == 0 && state.get(MILK) == 0) {
                world.setBlockState(pos, state.with(LAVA, state.get(LAVA) + 1).with(WATER, state.get(WATER)).with(MILK, state.get(MILK)).with(OTHER, false));
                if (!player.isCreative()){
                    player.getMainHandStack().decrement(1);
                    player.giveItemStack(Items.BUCKET.getDefaultStack());
                }
                return ActionResult.SUCCESS;
            } else if (hold == Items.WATER_BUCKET && state.get(LAVA) == 0 && state.get(MILK) == 0) {
                world.setBlockState(pos, state.with(LAVA, state.get(LAVA)).with(WATER, state.get(WATER) + 1).with(MILK, state.get(MILK)).with(OTHER, false));
                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                    player.giveItemStack(Items.BUCKET.getDefaultStack());
                }
                return ActionResult.SUCCESS;
            } else if (hold == Items.MILK_BUCKET && state.get(WATER) == 0 && state.get(LAVA) == 0) {
                world.setBlockState(pos, state.with(LAVA, state.get(LAVA)).with(WATER, state.get(WATER)).with(MILK, state.get(MILK) + 1).with(OTHER, false));
                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                    player.giveItemStack(Items.BUCKET.getDefaultStack());
                }
                return ActionResult.SUCCESS;
            }
        }
        if (hold == Items.BUCKET) {
            if (state.get(LAVA) > 0 && state.get(WATER) == 0 && state.get(MILK) == 0) {
                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                    player.giveItemStack(Items.LAVA_BUCKET.getDefaultStack());
                }
                world.setBlockState(pos, state.with(LAVA, state.get(LAVA) - 1).with(WATER, state.get(WATER)).with(MILK, state.get(MILK)).with(OTHER, false));
                if (state.get(LAVA) == 1) {
                    world.setBlockState(pos, state.with(LAVA, 0).with(WATER, 0).with(MILK, 0).with(OTHER, true));
                }
                return ActionResult.SUCCESS;
            } else if (state.get(WATER) > 0 && state.get(LAVA) == 0 && state.get(MILK) == 0) {
                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                    player.giveItemStack(Items.WATER_BUCKET.getDefaultStack());
                }
                world.setBlockState(pos, state.with(LAVA, state.get(LAVA)).with(WATER, state.get(WATER) - 1).with(MILK, state.get(MILK)).with(OTHER, false));
                if (state.get(WATER) == 1) {
                    world.setBlockState(pos, state.with(LAVA, 0).with(WATER, 0).with(MILK, 0).with(OTHER, true));
                }
                return ActionResult.SUCCESS;
            } else if (state.get(MILK) > 0 && state.get(WATER) == 0 && state.get(LAVA) == 0) {
                if (!player.isCreative()) {
                    player.getMainHandStack().decrement(1);
                    player.giveItemStack(Items.MILK_BUCKET.getDefaultStack());
                }
                world.setBlockState(pos, state.with(LAVA, state.get(LAVA)).with(WATER, state.get(WATER)).with(MILK, state.get(MILK) - 1).with(OTHER, false));
                if (state.get(MILK) == 1) {
                    world.setBlockState(pos, state.with(LAVA, 0).with(WATER, 0).with(MILK, 0).with(OTHER, true));
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
