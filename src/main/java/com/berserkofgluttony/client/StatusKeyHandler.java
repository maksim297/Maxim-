
package com.berserkofgluttony.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "berserkofgluttony", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class StatusKeyHandler {

    public static final KeyMapping OPEN_STATUS =
        new KeyMapping(
            "key.berserkofgluttony.status",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "key.categories.berserkofgluttony"
        );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(OPEN_STATUS);
    }

    @EventBusSubscriber(modid = "berserkofgluttony", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();

            while (OPEN_STATUS.consumeClick()) {
                if (minecraft.player != null && minecraft.screen == null) {
                    minecraft.setScreen(new StatusScreen());
                }
            }
        }
    }
}
