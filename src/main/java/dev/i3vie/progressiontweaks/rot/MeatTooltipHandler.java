package dev.i3vie.progressiontweaks.rot;

import dev.i3vie.progressiontweaks.items.RottenMeat;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static dev.i3vie.progressiontweaks.rot.MeatAgingHandler.RAW_MEAT;
import static dev.i3vie.progressiontweaks.rot.MeatDecayHelpers.FRESH_TICKS;
import static dev.i3vie.progressiontweaks.rot.MeatDecayHelpers.OLD_TICKS;

@EventBusSubscriber
public final class MeatTooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (!stack.is(RAW_MEAT)) return;

        Level level = event.getContext().level();
        if (level == null) return;

        // If this stack SHOULD be rotten, don't lie in the tooltip
        ItemStack enforced = enforceDecay(stack, level);
        if (enforced != stack) {
            return;
        }

        long now = level.getGameTime();
        long born = stack.getOrDefault(
                MeatDecayHelpers.MEAT_BIRTH_TICK.get(),
                now
        );

        long age = now - born;

        if (age < FRESH_TICKS) {
            addFreshTooltip(event, FRESH_TICKS - age);
        }
        else if (age < OLD_TICKS) {
            addOldTooltip(event, OLD_TICKS - age);
        }
    }

    private static void addFreshTooltip(ItemTooltipEvent event, long ticksRemaining) {
        int minutes = ticksToMinutes(ticksRemaining);

        event.getToolTip().add(
                Component.translatable(
                        "tooltip.meat_decay.fresh",
                        minutes
                )
        );
    }

    public static ItemStack enforceDecay(ItemStack stack, Level level) {
        if (!stack.is(RAW_MEAT)) return stack;

        long now = level.getGameTime();
        long born = stack.getOrDefault(
                MeatDecayHelpers.MEAT_BIRTH_TICK.get(),
                now
        );

        long age = now - born;

        if (age >= OLD_TICKS) {
            return new ItemStack(RottenMeat.ROTTEN_MEAT.get(), stack.getCount());
        }

        return stack;
    }

    private static void addOldTooltip(ItemTooltipEvent event, long ticksRemaining) {
        int minutes = ticksToMinutes(ticksRemaining);

        event.getToolTip().add(
                Component.translatable(
                        "tooltip.meat_decay.old",
                        minutes
                )
        );
    }

    private static int ticksToMinutes(long ticks) {
        return Math.max(1, (int) (ticks / 1200L)); // 1200 ticks = 1 minute
    }
}