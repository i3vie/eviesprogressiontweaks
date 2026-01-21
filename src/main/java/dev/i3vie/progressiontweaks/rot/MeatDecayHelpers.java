package dev.i3vie.progressiontweaks.rot;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static dev.i3vie.progressiontweaks.EviesProgressionTweaks.COMPONENTS;

public class MeatDecayHelpers {
    public static final long FRESH_TICKS = 36_000L; // 1.5 days
    public static final long OLD_TICKS   = 72_000L; // 3 days total

    public static final Supplier<DataComponentType<Long>> MEAT_BIRTH_TICK =
            COMPONENTS.register("meat_birth_tick", () ->
                    DataComponentType.<Long>builder()
                            .persistent(Codec.LONG)
                            .networkSynchronized(ByteBufCodecs.fromCodec(Codec.LONG))
                            .build()
            );

    public static void register() {
        // forces class load
    }
}
