package dev.i3vie.progressiontweaks.rot;

import dev.i3vie.progressiontweaks.items.RottenMeat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.awt.event.ContainerEvent;
import java.util.Random;

@EventBusSubscriber
public class MeatAgingHandler {
    private static final long FRESH_TO_OLD_TICKS = 36_000L;
    private static final long OLD_TO_ROTTEN_TICKS = 72_000L;
    public static final TagKey<Item> RAW_MEAT = TagKey.create(Registries.ITEM, Identifier.parse("c:foods/raw_meat"));

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        long time = player.level().getGameTime();
        if (time % (20 * 60) != 0) return; // once per minute

        var items = player.getInventory().getNonEquipmentItems();

        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty()) continue;

            ItemStack enforced = MeatTooltipHandler.enforceDecay(stack, player.level());
            if (enforced != stack) {
                items.set(i, enforced);
            }
        }
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;

        AbstractContainerMenu menu = event.getContainer();
        Level level = player.level();

        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            ItemStack enforced = MeatTooltipHandler.enforceDecay(stack, level);
            if (enforced != stack) {
                slot.set(enforced);
            }
        }
    }


}
