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
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
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

        NeoForge.EVENT_BUS.addListener(this::onPlayerLevelChange);
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

        registrar.playToClient(
                StatSyncPayload.TYPE,
                StatSyncPayload.STREAM_CODEC,
                BerserkOfGluttony::handleStatSync
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

            int id = payload.statId();
            int direction = payload.direction();

            if (direction != 1 && direction != -1) {
                return;
            }

            int value = switch (id) {
                case 0 -> stats.getStrength();
                case 1 -> stats.getAgility();
                case 2 -> stats.getEndurance();
                case 3 -> stats.getMagic();
                default -> -1;
            };

            if (value < 10) {
                return;
            }

            if (direction == 1 && stats.getStatPoints() > 0) {
                value++;
                stats.setStatPoints(stats.getStatPoints() - 1);
            } else if (direction == -1 && value > 10) {
                value--;
                stats.setStatPoints(stats.getStatPoints() + 1);
            } else {
                return;
            }

            switch (id) {
                case 0 -> stats.setStrength(value);
                case 1 -> stats.setAgility(value);
                case 2 -> stats.setEndurance(value);
                case 3 -> stats.setMagic(value);
                default -> {
                    return;
                }
            }

            player.setData(RPG_STATS, stats);
            syncStats(player, stats);
        });
    }

    private static void handleStatSync(
            StatSyncPayload payload,
            IPayloadContext context
    ) {
        context.enqueueWork(() -> {
            if (context.player() == null) {
                return;
            }

            context.player().setData(
                    RPG_STATS,
                    new RpgStats(
                            payload.strength(),
                            payload.agility(),
                            payload.endurance(),
                            payload.magic(),
                            payload.statPoints()
                    )
            );
        });
    }

    private void onPlayerLevelChange(PlayerXpEvent.LevelChange event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int levelsGained = event.getLevels();

        if (levelsGained <= 0) {
            return;
        }

        RpgStats stats = player.getData(RPG_STATS);

        stats.setStatPoints(
                stats.getStatPoints() + levelsGained
        );

        player.setData(RPG_STATS, stats);
        syncStats(player, stats);
    }

    private static void syncStats(ServerPlayer player, RpgStats stats) {
        PacketDistributor.sendToPlayer(
                player,
                new StatSyncPayload(
                        stats.getStrength(),
                        stats.getAgility(),
                        stats.getEndurance(),
                        stats.getMagic(),
                        stats.getStatPoints()
                )
        );
    }
}
