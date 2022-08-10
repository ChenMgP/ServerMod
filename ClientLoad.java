package com.kcn;

import com.kcn.blocks.ModBlocks;
import com.kcn.data.InF.CommandMethod;
import com.kcn.data.InF.Dust;
import com.kcn.data.InF.StrongRecipe;
import com.kcn.entity.ModEntities;
import com.kcn.entity.client.RaccoonRenderer;
import com.kcn.screen.ModScreens;
import com.kcn.util.ClientData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;

public class ClientLoad implements ClientModInitializer, CommandMethod, StrongRecipe, Dust {

    @Override
    public void onInitializeClient() {
        ModScreens.registryScreen();
        EntityRendererRegistry.register(ModEntities.RACCOON, RaccoonRenderer::new);
        BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.CONTAINER, RenderLayer.getCutout());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.TEMPERED_GLASS, RenderLayer.getCutout());
        BlockRenderLayerMapImpl.INSTANCE.putBlock(ModBlocks.REMOVE_ENCHANT_TABLE, RenderLayer.getCutout());

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
    }
}

