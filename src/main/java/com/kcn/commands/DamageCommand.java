package com.kcn.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DamageCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("damage").requires(commandSource -> commandSource.hasPermissionLevel(4)).then(argument("value", IntegerArgumentType.integer()).requires(commandSource -> commandSource.hasPermissionLevel(4)).executes(
                context -> broadcast(IntegerArgumentType.getInteger(context, "value"), context.getSource().getPlayer())
        )));
    }

    public static int broadcast(int value, PlayerEntity player) {
        ItemStack mainHandStack = player.getMainHandStack();
        mainHandStack.damage(value, player, c -> c.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return Command.SINGLE_SUCCESS;
    }
}
