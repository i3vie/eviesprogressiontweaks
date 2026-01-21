package dev.i3vie.progressiontweaks.rot;

import dev.i3vie.progressiontweaks.EviesProgressionTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber
public class MeatDropHandler {
    private static final TagKey<Item> RAW_MEAT = MeatAgingHandler.RAW_MEAT;

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide()) return;

        long now = event.getEntity().level().getGameTime();

        for (ItemEntity drop : event.getDrops()) {
            ItemStack stack = drop.getItem();
            if (!stack.is(RAW_MEAT)) continue;

            stack.set(MeatDecayHelpers.MEAT_BIRTH_TICK.get(), now);
        }
    }
}
