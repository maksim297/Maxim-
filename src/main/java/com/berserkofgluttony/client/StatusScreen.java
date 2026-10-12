
package com.berserkofgluttony.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StatusScreen extends Screen {

    public StatusScreen() {
        super(Component.literal("Статус персонажа"));
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int w = this.width;
        int h = this.height;

        graphics.fill(0, 0, w, h, 0xE5101018);

        int left = w / 2 - 120;
        int top = h / 2 - 90;
        int right = w / 2 + 120;
        int bottom = h / 2 + 90;

        graphics.fill(left, top, right, bottom, 0xFFB8860B);
        graphics.fill(left + 3, top + 3, right - 3, bottom - 3, 0xFF17131D);

        graphics.drawCenteredString(
            this.font, "СТАТУС ПЕРСОНАЖА",
            w / 2, top + 12, 0xFFFFD700
        );

        graphics.drawString(this.font, "Рівень: 1", left + 16, top + 40, 0xFFFFFFFF);
        graphics.drawString(this.font, "Сила: 10", left + 16, top + 58, 0xFFFF5555);
        graphics.drawString(this.font, "Спритність: 10", left + 16, top + 76, 0xFF55FFFF);
        graphics.drawString(this.font, "Витривалість: 10", left + 16, top + 94, 0xFF55FF55);
        graphics.drawString(this.font, "Магія: 10", left + 16, top + 112, 0xFFAA55FF);

        graphics.drawCenteredString(
            this.font, "Натисни ESC, щоб закрити",
            w / 2, bottom - 18, 0xFFCCCCCC
        );

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
