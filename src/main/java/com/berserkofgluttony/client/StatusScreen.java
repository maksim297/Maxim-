package com.berserkofgluttony.client;

import com.berserkofgluttony.BerserkOfGluttony;
import com.berserkofgluttony.RpgStats;
import com.berserkofgluttony.StatActionPayload;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

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

        left = width / 2 - 155;
        top = Math.max(10, height / 2 - 165);
        right = width / 2 + 155;
        bottom = Math.min(height - 10, top + 330);

        int buttonX = right - 76;
        int y = top + 38;

        addStatButtons(buttonX, y + 92, 0);
        addStatButtons(buttonX, y + 116, 1);
        addStatButtons(buttonX, y + 140, 2);
        addStatButtons(buttonX, y + 164, 3);
        addStatButtons(buttonX, y + 188, 4);
    }

    private void addStatButtons(int x, int y, int statId) {
        addRenderableWidget(
                Button.builder(
                        Component.literal("−"),
                        button -> sendStatAction(statId, -1)
                ).bounds(x, y, 26, 18).build()
        );

        addRenderableWidget(
                Button.builder(
                        Component.literal("+"),
                        button -> sendStatAction(statId, 1)
                ).bounds(x + 30, y, 26, 18).build()
        );
    }

    private void sendStatAction(int statId, int direction) {
        PacketDistributor.sendToServer(
                new StatActionPayload(statId, direction)
        );
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
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
            int y = top + 38;

            graphics.drawString(font,
                    "Ім'я: " + player.getName().getString(),
                    x, y, 0xFFFFFFFF);

            graphics.drawString(font,
                    String.format(java.util.Locale.ROOT,
                            "Здоров'я: %.1f / %.1f",
                            player.getHealth(), player.getMaxHealth()),
                    x, y + 18, 0xFFFF5555);

            graphics.drawString(font,
                    "Броня: " + player.getArmorValue(),
                    x, y + 36, 0xFFAAAAFF);

            graphics.drawString(font,
                    "Рівень: " + player.experienceLevel,
                    x, y + 54, 0xFFFFD700);

            graphics.drawString(font,
                    "Досвід: " + (int) (player.experienceProgress * 100) + "%",
                    x, y + 72, 0xFF55FF55);

            graphics.drawString(font,
                    "Сила: " + stats.getStrength(),
                    x, y + 96, 0xFFFF7777);

            graphics.drawString(font,
                    "Витривалість: " + stats.getEndurance(),
                    x, y + 120, 0xFFCC88FF);

            graphics.drawString(font,
                    "Сприйняття: " + stats.getPerception(),
                    x, y + 144, 0xFFFFFF55);

            graphics.drawString(font,
                    "Спритність: " + stats.getAgility(),
                    x, y + 168, 0xFF55FFFF);

            graphics.drawString(font,
                    "Інтелект: " + stats.getIntelligence(),
                    x, y + 192, 0xFF55FFAA);

            graphics.drawString(font,
                    "Очки характеристик: " + stats.getStatPoints(),
                    x, y + 216, 0xFFFFFF55);

            graphics.drawString(font,
                    "Клас: Берсерк",
                    x, y + 234, 0xFFFFD700);

            graphics.drawString(font,
                    "Мова гри: " +
                            (minecraft.options.languageCode.startsWith("uk")
                                    ? "Українська"
                                    : minecraft.options.languageCode),
                    x, y + 252, 0xFF55FFFF);

            graphics.drawString(font,
                    "Навичка: Поглинання",
                    x, y + 270, 0xFF55FF55);
        }

        graphics.drawCenteredString(
                font,
                "ESC — закрити",
                width / 2,
                Math.min(height - 15, bottom - 16),
                0xFFCCCCCC
        );

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
