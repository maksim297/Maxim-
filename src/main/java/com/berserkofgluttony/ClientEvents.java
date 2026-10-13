
package com.berserkofgluttony;

import com.berserkofgluttony.client.StatusScreen;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;

import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(
        modid = BerserkOfGluttony.MODID,
        value = Dist.CLIENT,
        bus = EventBusSubscriber.Bus.MOD
)
public class ClientEvents {

    public static final KeyMapping OPEN_STATUS_KEY =
            new KeyMapping(
                    "key.berserkofgluttony.open_status",
                    KeyConflictContext.IN_GAME,
                    KeyModifier.NONE,
                    GLFW.GLFW_KEY_N,
                    KeyMapping.Category.MISC
            );

    @SubscribeEvent
    public static void registerKeyMappings(
            RegisterKeyMappingsEvent event
    ) {
        event.register(OPEN_STATUS_KEY);
    }

    @EventBusSubscriber(
            modid = BerserkOfGluttony.MODID,
            value = Dist.CLIENT,
            bus = EventBusSubscriber.Bus.GAME
    )
    public static class ClientGameEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();

            while (OPEN_STATUS_KEY.consumeClick()) {
                if (minecraft.player != null
                        && minecraft.screen == null) {
                    minecraft.setScreen(new StatusScreen());
                }
            }
        }
    }
}
