package com.kcn.commands;

import com.kcn.data.InF.CommandMethod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;

import static net.minecraft.server.command.CommandManager.literal;

public class PointCommand implements CommandMethod {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("point")
                .then(literal("remove").executes(
                        context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            try {
                                CommandMethod.removeFile(player);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("set").executes(
                        context -> {
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            DecimalFormat df = new DecimalFormat("0.000");
                            double x = Double.parseDouble(df.format(player.getPos().x));
                            double y = Double.parseDouble(df.format(player.getPos().y));
                            double z = Double.parseDouble(df.format(player.getPos().z));
                            try {
                                CommandMethod.createFile(x, y, z, player);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String s = "§b传送点已设置在坐标§ax=" + x + "  y=" + y + "  z=" + z;
                            player.sendMessage(Text.of(s), false);
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("goto").executes(
                        context -> {
                            try {
                                CommandMethod.teleport(context.getSource().getPlayer());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return Command.SINGLE_SUCCESS;
                        }
                ))
                .then(literal("death").executes(
                        context -> {
                            try {
                                CommandMethod.death(context.getSource().getPlayer());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return Command.SINGLE_SUCCESS;
                        }
                ))
        );
    }

}
