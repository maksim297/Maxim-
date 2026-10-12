
package com.berserkofgluttony;

import java.util.function.Supplier;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

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
        System.out.println("Berserk of Gluttony loaded!");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(GREED);
        }
    }
}
