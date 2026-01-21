package dev.i3vie.progressiontweaks.rot;

import dev.i3vie.progressiontweaks.rot.MeatDecayHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber
public final class MeatInitHandler {

    private static final TagKey<Item> RAW_MEAT = MeatAgingHandler.RAW_MEAT;

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        // once per second
        if (player.level().getGameTime() % 20 != 0) return;

        long now = event.getEntity().level().getGameTime();

        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.isEmpty()) continue;
            if (!stack.is(RAW_MEAT)) continue;

            if (!stack.has(MeatDecayHelpers.MEAT_BIRTH_TICK.get())) {
                stack.set(MeatDecayHelpers.MEAT_BIRTH_TICK.get(), now);
            }
        }
    }
}
