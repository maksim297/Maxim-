
package com.berserkofgluttony.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class StatusScreen extends Screen {

    public StatusScreen() {
        super(Component.literal("Статус персонажа"));
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        super.render(graphics, mouseX, mouseY, partialTick);

        int w = this.width;
        int h = this.height;

        graphics.fill(0, 0, w, h, 0xE5101018);

        int left = w / 2 - 125;
        int top = h / 2 - 90;
        int right = w / 2 + 125;
        int bottom = h / 2 + 90;

        // Золота рамка та темна панель
        graphics.fill(left, top, right, bottom, 0xFFFFD700);
        graphics.fill(left + 3, top + 3, right - 3, bottom - 3, 0xFF17131D);

        graphics.drawCenteredString(
                this.font,
                "СТАТУС ПЕРСОНАЖА",
                w / 2,
                top + 12,
                0xFFFFD700
        );

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player != null) {
            int y = top + 38;

            // Реальні показники Minecraft
            graphics.drawString(
                    this.font,
                    "Здоров'я: " +
                    String.format(java.util.Locale.ROOT, "%.1f / %.1f",
                            player.getHealth(), player.getMaxHealth()),
                    left + 14, y, 0xFFFF5555
            );

            graphics.drawString(
                    this.font,
                    "Броня: " + player.getArmorValue(),
                    left + 14, y + 20, 0xFFAAAAFF
            );

            graphics.drawString(
                    this.font,
                    "Рівень: " + player.experienceLevel,
                    left + 14, y + 40, 0xFFFFD700
            );

            graphics.drawString(
                    this.font,
                    "Досвід: " +
                    (int) (player.experienceProgress * 100) + "%",
                    left + 14, y + 60, 0xFF55FF55
            );

            graphics.drawString(
                    this.font,
                    "Сила: —",
                    left + 14, y + 85, 0xFFFF7777
            );

            graphics.drawString(
                    this.font,
                    "Спритність: —",
                    left + 14, y + 103, 0xFF55FFFF
            );

            graphics.drawString(
                    this.font,
                    "Магія: —",
                    left + 14, y + 121, 0xFFCC88FF
            );
        }

        graphics.drawCenteredString(
                this.font,
                "ESC — закрити",
                w / 2,
                bottom - 16,
                0xFFCCCCCC
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
