
package com.berserkofgluttony;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record StatSyncPayload(
        int strength,
        int endurance,
        int perception,
        int agility,
        int intelligence,
        int statPoints
) implements CustomPacketPayload {

    public static final Type<StatSyncPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            "berserkofgluttony",
                            "stat_sync"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, StatSyncPayload>
            STREAM_CODEC = StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, StatSyncPayload::strength,
                    ByteBufCodecs.VAR_INT, StatSyncPayload::endurance,
                    ByteBufCodecs.VAR_INT, StatSyncPayload::perception,
                    ByteBufCodecs.VAR_INT, StatSyncPayload::agility,
                    ByteBufCodecs.VAR_INT, StatSyncPayload::intelligence,
                    ByteBufCodecs.VAR_INT, StatSyncPayload::statPoints,
                    StatSyncPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
