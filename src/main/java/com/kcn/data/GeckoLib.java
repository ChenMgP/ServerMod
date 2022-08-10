package com.kcn.data;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GeckoLib {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String ModID = "geckolib3";
    public static boolean hasInitialized;

    public static void initialize() {
        if (!hasInitialized) {
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                    .registerReloadListener(new IdentifiableResourceReloadListener() {
                        @Override
                        public Identifier getFabricId() {
                            return new Identifier(software.bernie.geckolib3.GeckoLib.ModID, "models");
                        }

                        @Override
                        public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager,
                                                              Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor,
                                                              Executor applyExecutor) {
                            return GeckoLibCache.getInstance().reload(synchronizer, manager, prepareProfiler,
                                    applyProfiler, prepareExecutor, applyExecutor);
                        }
                    });
        }
        hasInitialized = true;
    }

}
