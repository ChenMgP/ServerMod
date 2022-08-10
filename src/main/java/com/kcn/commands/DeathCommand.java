package com.kcn.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class DeathCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("death").executes(
                context -> {
                    context.getSource().getPlayer().kill();
                    return Command.SINGLE_SUCCESS;
                }
        ));
    }

}
