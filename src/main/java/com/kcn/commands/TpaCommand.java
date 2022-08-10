package com.kcn.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class TpaCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tpa").then(argument("targets", EntityArgumentType.players()).executes(
                context -> broadcast(context, context.getSource().getPlayer())
        )));
    }

    public static int broadcast(CommandContext<ServerCommandSource> source, PlayerEntity p) {
        final double x = p.getX();
        final double y = p.getY();
        final double z = p.getZ();
        try {
            ServerPlayerEntity player = source.getSource().getPlayer();
            player.teleport(source.getSource().getWorld(), x, y, z, 0, 0);
            player.sendMessage(Text.of("已传送至" + p.getEntityName()), false);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }
}
