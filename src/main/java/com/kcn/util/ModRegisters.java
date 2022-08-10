package com.kcn.util;

import com.kcn.Main;
import com.kcn.entity.ModEntities;
import com.kcn.entity.custom.RaccoonEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModRegisters {

    public static void registerModStuffs() {
        registerAttributes();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(Main.RACCOON, RaccoonEntity.setAttributes());
    }

}
