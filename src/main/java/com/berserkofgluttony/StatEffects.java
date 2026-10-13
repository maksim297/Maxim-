
package com.berserkofgluttony;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class StatEffects {

    private static final ResourceLocation STRENGTH_ID =
            ResourceLocation.fromNamespaceAndPath(
                    BerserkOfGluttony.MODID, "strength_damage");

    private static final ResourceLocation AGILITY_ID =
            ResourceLocation.fromNamespaceAndPath(
                    BerserkOfGluttony.MODID, "agility_speed");

    private static final ResourceLocation ENDURANCE_ID =
            ResourceLocation.fromNamespaceAndPath(
                    BerserkOfGluttony.MODID, "endurance_health");

    private static final ResourceLocation PERCEPTION_ID =
            ResourceLocation.fromNamespaceAndPath(
                    BerserkOfGluttony.MODID, "perception_damage");

    private static final ResourceLocation INTELLIGENCE_ID =
            ResourceLocation.fromNamespaceAndPath(
                    BerserkOfGluttony.MODID, "intelligence_damage");

    private StatEffects() {
    }

    public static void apply(ServerPlayer player) {
        RpgStats stats = player.getData(BerserkOfGluttony.RPG_STATS);

        double strengthBonus =
                Math.max(0, stats.getStrength() - 10) * 0.5;

        double perceptionBonus =
                Math.max(0, stats.getPerception() - 10) * 0.7;

        double intelligenceBonus =
                Math.max(0, stats.getIntelligence() - 10) * 0.5;

        replaceModifier(
                player.getAttribute(Attributes.ATTACK_DAMAGE),
                STRENGTH_ID,
                strengthBonus
        );

        replaceModifier(
                player.getAttribute(Attributes.ATTACK_DAMAGE),
                PERCEPTION_ID,
                perceptionBonus
        );

        replaceModifier(
                player.getAttribute(Attributes.ATTACK_DAMAGE),
                INTELLIGENCE_ID,
                intelligenceBonus
        );

        replaceModifier(
                player.getAttribute(Attributes.ATTACK_SPEED),
                AGILITY_ID,
                Math.max(0, stats.getAgility() - 10) * 0.1
        );

        AttributeInstance health =
                player.getAttribute(Attributes.MAX_HEALTH);

        double oldMaxHealth = player.getMaxHealth();

        replaceModifier(
                health,
                ENDURANCE_ID,
                Math.max(0, stats.getEndurance() - 10) * 2.0
        );

        double newMaxHealth = player.getMaxHealth();

        if (newMaxHealth > oldMaxHealth) {
            player.setHealth((float) Math.min(
                    newMaxHealth,
                    player.getHealth() + newMaxHealth - oldMaxHealth
            ));
        } else if (player.getHealth() > newMaxHealth) {
            player.setHealth((float) newMaxHealth);
        }
    }

    private static void replaceModifier(
            AttributeInstance attribute,
            ResourceLocation id,
            double amount
    ) {
        if (attribute == null) {
            return;
        }

        attribute.removeModifier(id);

        if (amount > 0) {
            attribute.addTransientModifier(
                    new AttributeModifier(
                            id,
                            amount,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }
    }

    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            apply(player);
        }
    }

    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            apply(player);
        }
    }
}
