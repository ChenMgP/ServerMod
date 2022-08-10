package com.kcn;

import com.kcn.commands.*;
import com.kcn.data.InF.CommandMethod;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

@Environment(EnvType.SERVER)
public class ServerLoad implements DedicatedServerModInitializer, CommandMethod {

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(
                server -> {
                    try {
                        CommandMethod.file();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        ServerPlayConnectionEvents.JOIN.register(
                (handler, sender, server) -> {
                    ServerPlayerEntity player = handler.getPlayer();
                    player.sendMessage(Text.of("请输入 /information rule 命令了解服务器规则"), false);
                }
        );

        CommandRegistrationCallback.EVENT.register(
                (dispatcher, dedicated) -> {
                    GetUUIDCommand.register(dispatcher);
                    TpaCommand.register(dispatcher);
                    DeathCommand.register(dispatcher);
                    CreeperExplosionCommand.register(dispatcher);
                    WitherBreakCommand.register(dispatcher);
                    PointCommand.register(dispatcher);
                    InformationCommand.register(dispatcher);
                    GetPosCommand.register(dispatcher);
                }
        );
    }
}

