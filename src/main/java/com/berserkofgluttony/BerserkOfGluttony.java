
package com.berserkofgluttony;

import java.util.function.Supplier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(BerserkOfGluttony.MODID)
public class BerserkOfGluttony {

    public static final String MODID = "berserkofgluttony";

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MODID);

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<RpgStats>> RPG_STATS =
            ATTACHMENT_TYPES.register(
                    "rpg_stats",
                    () -> AttachmentType.builder(RpgStats::new)
                            .serialize(RpgStats.CODEC)
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredItem<SwordItem> GREED =
            ITEMS.register("greed", () ->
                    new SwordItem(
                            Tiers.DIAMOND,
                            new Item.Properties().attributes(
                                    SwordItem.createAttributes(
                                            Tiers.DIAMOND, 21, -2.4F
                                    )
                            )
                    )
            );

    public BerserkOfGluttony(IEventBus modBus) {
        ITEMS.register(modBus);
        ATTACHMENT_TYPES.register(modBus);

        modBus.addListener(this::addCreative);
        modBus.addListener(this::registerPayloads);

        System.out.println("Berserk of Gluttony loaded!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(GREED);
        }
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                StatActionPayload.TYPE,
                StatActionPayload.STREAM_CODEC,
                BerserkOfGluttony::handleStatAction
        );
    }

    private static void handleStatAction(
            StatActionPayload payload,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }

            RpgStats stats = player.getData(RPG_STATS);

            int statId = payload.statId();
            int direction = payload.direction();

            if (direction != 1 && direction != -1) {
                return;
            }

            switch (statId) {
                case 0 -> changeStat(
                        stats, stats.getStrength(),
                        direction, 0, stats::setStrength
                );
                case 1 -> changeStat(
                        stats, stats.getAgility(),
                        direction, 1, stats::setAgility
                );
                case 2 -> changeStat(
                        stats, stats.getEndurance(),
                        direction, 2, stats::setEndurance
                );
                case 3 -> changeStat(
                        stats, stats.getMagic(),
                        direction, 3, stats::setMagic
                );
                default -> {
                    return;
                }
            }

            player.setData(RPG_STATS, stats);
        });
    }

    private static void changeStat(
            RpgStats stats,
            int current,
            int direction,
            int statId,
            java.util.function.IntConsumer setter
    ) {
        if (direction == 1 && stats.getStatPoints() > 0) {
            setter.accept(current + 1);
            stats.setStatPoints(stats.getStatPoints() - 1);
        } else if (direction == -1 && current > 10) {
            setter.accept(current - 1);
            stats.setStatPoints(stats.getStatPoints() + 1);
        }
    }      
}
