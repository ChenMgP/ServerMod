package com.kcn.commands;

import com.kcn.data.InF.CommandMethod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class WitherBreakCommand implements CommandMethod {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("witherBreak").requires(commandSource -> commandSource.hasPermissionLevel(4)).then(literal("close").requires(commandSource -> commandSource.hasPermissionLevel(4)).executes(
                        context -> {
                            try {
                                CommandMethod.witherBreak(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            context.getSource().getServer().getPlayerManager().broadcast(Text.of("§b服务器§e关闭§b了凋灵破坏方块"), MessageType.SYSTEM, context.getSource().getPlayer().getUuid());
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("open").requires(commandSource -> commandSource.hasPermissionLevel(4)).executes(
                        context -> {
                            try {
                                CommandMethod.witherBreak(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            context.getSource().getServer().getPlayerManager().broadcast(Text.of("§b服务器§e开启§b了凋灵破坏方块"), MessageType.SYSTEM, context.getSource().getPlayer().getUuid());
                            return Command.SINGLE_SUCCESS;
                        }
                )));
    }
}
