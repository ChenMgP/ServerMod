package com.kcn.commands;

import com.kcn.data.InF.CommandMethod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class InformationCommand implements CommandMethod {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("information")
                .then(literal("characteristic").executes(
                        context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            player.sendMessage(Text.of(characteristic), false);
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("command").executes(
                        context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            player.sendMessage(Text.of(command), false);
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("sign").executes(
                        context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            try {
                                player.sendMessage(Text.of(CommandMethod.sign()), false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("rule").executes(
                        context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            player.sendMessage(Text.of(rule), false);
                            return Command.SINGLE_SUCCESS;
                        }
                )));
    }

}
