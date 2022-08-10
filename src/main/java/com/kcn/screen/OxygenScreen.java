package com.kcn.screen;

import com.kcn.screen.handler.OxygenScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OxygenScreen extends HandledScreen<OxygenScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("kcn", "textures/gui/container/oxygen_machine.png");

    public OxygenScreen(OxygenScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        this.backgroundHeight = 167;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    /**
     * x:目标x坐标 -1
     * y:目标y坐标 -1
     * u:替代x坐标 -1
     * v:替代y坐标 -1
     * w:替代的宽度
     * h:替代的高度
     */

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = this.y;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        int value = this.handler.getValue();
        int water = this.handler.getWater();
        if (value > 0) {
            this.drawTexture(matrices, i + 128, j + 35, 176, 4, value * 2, 3);
        }
        if (water > 0 && water <= 25) {
            this.drawTexture(matrices, i + 24, j + 24, 176, 11, 13, (((water - 1) / 5) + 1) * 5);
            if (water == 25) {
                this.drawTexture(matrices, i + 28, j + 21, 176, 38, 5, 2);
            }
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
