package com.kcn.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class ClearEffect extends InstantStatusEffect {
    public ClearEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xC4FF0E);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            remove(entity);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    private void remove(LivingEntity user) {
        ArrayList<StatusEffect> effects = new ArrayList<>();
        effects.add(StatusEffects.INSTANT_DAMAGE);
        effects.add(StatusEffects.BAD_OMEN);
        effects.add(StatusEffects.BLINDNESS);
        effects.add(StatusEffects.MINING_FATIGUE);
        effects.add(StatusEffects.WITHER);
        effects.add(StatusEffects.WEAKNESS);
        effects.add(StatusEffects.POISON);
        effects.add(StatusEffects.SLOWNESS);
        effects.add(StatusEffects.NAUSEA);
        effects.add(StatusEffects.UNLUCK);
        effects.add(StatusEffects.HUNGER);

        for (StatusEffect s : effects) {
            user.removeStatusEffect(s);
        }
    }
}
