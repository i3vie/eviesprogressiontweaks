package dev.i3vie.progressiontweaks.items;

import dev.i3vie.progressiontweaks.EviesProgressionTweaks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class HorseMeats {

    public static final DeferredItem<Item> RAW_HORSE_MEAT =
            EviesProgressionTweaks.ITEMS.registerItem(
                    "raw_horse_meat",
                    props -> new Item(
                            props.food(new FoodProperties.Builder()
                                    .nutrition(3)
                                    .saturationModifier(0.3F)
                                    .build()
                            )
                    )
            );

    public static final DeferredItem<Item> COOKED_HORSE_MEAT =
            EviesProgressionTweaks.ITEMS.registerItem(
                    "cooked_horse_meat",
                    props -> new Item(
                            props.food(new FoodProperties.Builder()
                                    .nutrition(8)
                                    .saturationModifier(0.8F)
                                    .build()
                            )
                    )
            );

    public static void register() {
        // forces class load
    }
}
