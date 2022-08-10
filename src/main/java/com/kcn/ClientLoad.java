package com.kcn;

import com.kcn.commands.*;
import com.kcn.data.InF.CommandMethod;
import com.kcn.data.InF.Dust;
import com.kcn.data.InF.StrongRecipe;
import com.kcn.entity.ModEntities;
import com.kcn.entity.client.RaccoonRenderer;
import com.kcn.screen.DustMachineScreen;
import com.kcn.screen.OxygenScreen;
import com.kcn.screen.RemoveEnchantTableScreen;
import com.kcn.screen.StrongSmithingTableScreen;
import com.kcn.util.ClientData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;

public class ClientLoad implements ClientModInitializer, CommandMethod, StrongRecipe, Dust {


    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Main.RACCOON, RaccoonRenderer::new);
        ScreenRegistry.register(Main.STRONG_SMITHING_TABLE_SCREEN_HANDLER, StrongSmithingTableScreen::new);
        ScreenRegistry.register(Main.OXYGEN_SCREEN_HANDLER, OxygenScreen::new);
        ScreenRegistry.register(Main.DUST_MACHINE_SCREEN_HANDLER, DustMachineScreen::new);
        ScreenRegistry.register(Main.REMOVE_ENCHANT_TABLE_SCREEN_HANDLER, RemoveEnchantTableScreen::new);
        BlockRenderLayerMapImpl.INSTANCE.putBlock(Main.CONTAINER, RenderLayer.getCutout());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(Main.TEMPERED_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(Main.REMOVE_ENCHANT_TABLE, RenderLayer.getCutout());

        ClientLifecycleEvents.CLIENT_STARTED.register(
                client -> {
                    try {
                        ClientData.ClientDataRegister();
                        CommandMethod.file();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        ClientPlayConnectionEvents.JOIN.register(
                (handler, sender, client) -> {
                    ClientPlayerEntity player = client.player;
                    assert player != null;
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
                    DamageCommand.register(dispatcher);
                    NbtCommand.register(dispatcher);
                }
        );
    }
}

