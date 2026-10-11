package com.berserkofgluttony;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(
        modid = BerserkOfGluttony.MODID,
        value = Dist.CLIENT,
        bus = EventBusSubscriber.Bus.MOD
)
public class ClientEvents {

    public static final KeyMapping STATUS_KEY = new KeyMapping(
            "key.berserkofgluttony.status",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            "key.categories.berserkofgluttony"
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(STATUS_KEY);
    }

    @EventBusSubscriber(
            modid = BerserkOfGluttony.MODID,
            value = Dist.CLIENT
    )
    public static class ClientGameEvents {

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();

            while (STATUS_KEY.consumeClick()) {
                if (minecraft.player != null) {
                    minecraft.setScreen(new StatusScreen());
                }
            }
        }
    }
}
