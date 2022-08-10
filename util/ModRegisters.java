package com.kcn.util;

import com.kcn.commands.*;
import com.kcn.entity.ModEntities;
import com.kcn.entity.custom.RaccoonEntity;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModRegisters {

    public static void registerModStuffs() {
        registerAttributes();
        registerCommands();
    }

    private static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.RACCOON, RaccoonEntity.setAttributes());
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(CreeperExplosionCommand::register);
        CommandRegistrationCallback.EVENT.register(DamageCommand::register);
        CommandRegistrationCallback.EVENT.register(DeathCommand::register);
        CommandRegistrationCallback.EVENT.register(GetPosCommand::register);
        CommandRegistrationCallback.EVENT.register(GetUUIDCommand::register);
        CommandRegistrationCallback.EVENT.register(InformationCommand::register);
        CommandRegistrationCallback.EVENT.register(NbtCommand::register);
        CommandRegistrationCallback.EVENT.register(PointCommand::register);
        CommandRegistrationCallback.EVENT.register(TpaCommand::register);
        CommandRegistrationCallback.EVENT.register(WitherBreakCommand::register);
    }
}
