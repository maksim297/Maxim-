package com.berserkofgluttony;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StatusScreen extends Screen {

    public StatusScreen() {
        super(Component.literal("STATUS"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        graphics.fill(centerX - 150, centerY - 100,
                      centerX + 150, centerY + 100, 0xE6101020);

        graphics.drawCenteredString(this.font, "STATUS",
                      centerX, centerY - 80, 0xFFFFD700);

        graphics.drawString(this.font, "LEVEL: I",
                      centerX - 120, centerY - 45, 0xFFFFFFFF);

        graphics.drawString(this.font, "STRENGTH: I",
                      centerX - 120, centerY - 20, 0xFFFFFFFF);

        graphics.drawString(this.font, "AGILITY: I",
                      centerX - 120, centerY + 5, 0xFFFFFFFF);

        graphics.drawString(this.font, "ENDURANCE: I",
                      centerX - 120, centerY + 30, 0xFFFFFFFF);

        graphics.drawString(this.font, "MAGIC: I",
                      centerX - 120, centerY + 55, 0xFFFFFFFF);

        super.render(graphics, mouseX, mouseY, partialTick);
    }
}
