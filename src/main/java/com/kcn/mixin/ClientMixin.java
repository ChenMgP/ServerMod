package com.kcn.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

@Mixin(ClientPlayerEntity.class)
public class ClientMixin implements KeyListener {

    private final Component c = new Component() {
        @Override
        public String getName() {
            return "KeyCode";
        }
    };
    private final KeyEvent e = new KeyEvent(c, 10,10, InputEvent.META_MASK | InputEvent.ALT_MASK, 89);

    @Inject(at = @At("TAIL"), method = "tick")
    public void tick(CallbackInfo ci) {
        keyPressed(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("YYY");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("YYY");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
