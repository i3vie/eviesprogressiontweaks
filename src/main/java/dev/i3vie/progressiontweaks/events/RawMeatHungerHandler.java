package dev.i3vie.progressiontweaks.events;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;


@EventBusSubscriber
public final class RawMeatHungerHandler {

    private static final TagKey<Item> RAW_MEAT_TAG =
            TagKey.create(Registries.ITEM, Identifier.parse("c:foods/raw_meat"));

    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) {
            return;
        }

        if (!(entity instanceof Player player)) {
            return;
        }

        ItemStack stack = event.getItem();

        if (!stack.is(RAW_MEAT_TAG)) {
            return;
        }

        // 30% chance
        if (player.getRandom().nextFloat() < 0.3f) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.HUNGER,
                    30 * 20,   // 7 seconds
                    0,        // Hunger I
                    false,
                    true
            ));
        }
    }
}
