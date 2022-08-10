package com.kcn.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.literal;

public class NbtCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("nbt").requires(commandSource -> commandSource.hasPermissionLevel(4)).executes(context -> broadcast(context.getSource().getPlayer())));
    }

    public static int broadcast(PlayerEntity player) {
        ItemStack stack = player.getMainHandStack();
        assert stack.getNbt() != null;
        String nbt = stack.getNbt().toString();
        player.sendMessage(new LiteralText(nbt), false);
        return Command.SINGLE_SUCCESS;
    }
}
