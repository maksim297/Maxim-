package com.berserkofgluttony.client;

import com.berserkofgluttony.BerserkOfGluttony;
import com.berserkofgluttony.RpgStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class StatusScreen extends Screen {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public StatusScreen() {
        super(Component.literal("Статус персонажа"));
    }

    @Override
    protected void init() {
        super.init();

        left = this.width / 2 - 155;
        top = this.height / 2 - 165;
        right = this.width / 2 + 155;
        bottom = this.height / 2 + 165;

        int buttonX = right - 78;
        int buttonWidth = 30;
        int buttonHeight = 18;

        addStatButtons("strength", top + 98, buttonX, buttonWidth, buttonHeight);
        addStatButtons("agility", top + 122, buttonX, buttonWidth, buttonHeight);
        addStatButtons("endurance", top + 146, buttonX, buttonWidth, buttonHeight);
        addStatButtons("magic", top + 170, buttonX, buttonWidth, buttonHeight);
    }

    private void addStatButtons(
            String stat,
            int y,
            int x,
            int width,
            int height
    ) {
        addRenderableWidget(
                Button.builder(Component.literal("+"), button -> {
                    // Наступним кроком підключимо серверне збільшення характеристики.
                }).bounds(x + 36, y, width, height).build()
        );

        addRenderableWidget(
                Button.builder(Component.literal("-"), button -> {
                    // Наступним кроком підключимо повернення витраченого очка.
                }).bounds(x, y, width, height).build()
        );
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);

        graphics.fill(0, 0, width, height, 0xE5101018);
        graphics.fill(left, top, right, bottom, 0xFFFFD700);
        graphics.fill(
                left + 3, top + 3,
                right - 3, bottom - 3,
                0xFF17131D
        );

        graphics.drawCenteredString(
                font,
                "СТАТУС ПЕРСОНАЖА",
                width / 2,
                top + 12,
                0xFFFFD700
        );

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player != null) {
            RpgStats stats = player.getData(BerserkOfGluttony.RPG_STATS);

            int x = left + 14;
            int y = top + 36;

            graphics.drawString(
                    font,
                    "Ім'я: " + player.getName().getString(),
                    x, y, 0xFFFFFFFF
            );

            graphics.drawString(
                    font,
                    "Здоров'я: " + String.format(
                            java.util.Locale.ROOT,
                            "%.1f / %.1f",
                            player.getHealth(),
                            player.getMaxHealth()
                    ),
                    x, y + 18, 0xFFFF5555
            );

            graphics.drawString(
                    font,
                    "Броня: " + player.getArmorValue(),
                    x, y + 36, 0xFFAAAAFF
            );

            graphics.drawString(
                    font,
                    "Рівень: " + player.experienceLevel,
                    x, y + 54, 0xFFFFD700
            );

            graphics.drawString(
                    font,
                    "Досвід: " +
                            (int) (player.experienceProgress * 100) + "%",
                    x, y + 72, 0xFF55FF55
            );

            graphics.drawString(
                    font,
                    "Сила: " + stats.getStrength(),
                    x, y + 96, 0xFFFF7777
            );

            graphics.drawString(
                    font,
                    "Спритність: " + stats.getAgility(),
                    x, y + 120, 0xFF55FFFF
            );

            graphics.drawString(
                    font,
                    "Витривалість: " + stats.getEndurance(),
                    x, y + 144, 0xFFCC88FF
            );

            graphics.drawString(
                    font,
                    "Магія: " + stats.getMagic(),
                    x, y + 168, 0xFF55FFAA
            );

            graphics.drawString(
                    font,
                    "Очки характеристик: " + stats.getStatPoints(),
                    x, y + 194, 0xFFFFFF55
            );

            graphics.drawString(
                    font,
                    "Клас: Берсерк",
                    x, y + 212, 0xFFFFD700
            );

            graphics.drawString(
                    font,
                    "Мова гри: " + (
                            minecraft.options.languageCode.startsWith("uk")
                                    ? "Українська"
                                    : minecraft.options.languageCode
                    ),
                    x, y + 230, 0xFF55FFFF
            );

            graphics.drawString(
                    font,
                    "Навичка: Поглинання",
                    x, y + 248, 0xFF55FF55
            );
        }

        graphics.drawCenteredString(
                font,
                "ESC — закрити",
                width / 2,
                bottom - 16,
                0xFFCCCCCC
        );

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
