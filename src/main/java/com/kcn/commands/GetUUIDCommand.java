package com.kcn.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class GetUUIDCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("getUUID").requires(commandSource -> commandSource.hasPermissionLevel(4)).then(argument("targets", EntityArgumentType.players()).requires(commandSource -> commandSource.hasPermissionLevel(4)).executes(
                context -> broadcast(context, context.getSource().getPlayer())
        )));
    }

    public static int broadcast(CommandContext<ServerCommandSource> source, PlayerEntity p) {
        final String uuid = p.getUuid().toString();
        try {
            source.getSource().getPlayer().sendMessage(Text.of(p.getEntityName()+": "+uuid),false);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }
}
