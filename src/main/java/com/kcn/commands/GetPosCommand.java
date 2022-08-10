package com.kcn.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.text.DecimalFormat;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.world.World.*;

public class GetPosCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("getPos").requires(commandSource -> commandSource.hasPermissionLevel(4)).then(argument("targets", EntityArgumentType.players()).requires(commandSource -> commandSource.hasPermissionLevel(4)).executes(
                context -> broadcast(context, context.getSource().getPlayer())
        )));
    }

    public static int broadcast(CommandContext<ServerCommandSource> source, PlayerEntity p) {
        DecimalFormat df = new DecimalFormat("0.000");
        final double x = Double.parseDouble(df.format(p.getX()));
        final double y = Double.parseDouble(df.format(p.getY()));
        final double z = Double.parseDouble(df.format(p.getZ()));
        try {
            String biome = p.getWorld().getBiome(p.getBlockPos()).value().toString();
            if (p.getWorld().getRegistryKey() == OVERWORLD) {
                source.getSource().getPlayer().sendMessage(Text.of("§e" + p.getEntityName() + "§f位于§a主世界§f,群系: §b" + biome + "§f,坐标: §bx=" + x + "  y=" + y + "  z=" + z), false);
            } else if (p.getWorld().getRegistryKey() == NETHER) {
                source.getSource().getPlayer().sendMessage(Text.of("§e" + p.getEntityName() + "§f位于§c地狱§f,群系: §b" + biome + "§f坐标: §bx=" + x + "  y=" + y + "  z=" + z), false);
            } else if (p.getWorld().getRegistryKey() == END) {
                source.getSource().getPlayer().sendMessage(Text.of("§e" + p.getEntityName() + "§f位于§d末地§f,群系: §b" + biome + "§f坐标: §bx=" + x + "  y=" + y + "  z=" + z), false);
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }
}
