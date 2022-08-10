package com.kcn.screen;

import com.kcn.screen.handler.DustMachineScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DustMachineScreen extends HandledScreen<DustMachineScreenHandler> {

    private static final Identifier TEXTURE = new Identifier("kcn", "textures/gui/container/dust.png");

    public DustMachineScreen(DustMachineScreenHandler handler, PlayerInventory inventory, Text title) {
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
        int fuel = this.handler.getFuel();
        int time = (this.handler.getTime()) / 20;
        if (fuel > 0) {
            this.drawTexture(matrices, i + 135, j + 34, 176, 12, 8, 21);
        }
        this.drawTexture(matrices, i + 58, j + 37, 176, 0, 12, time);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
