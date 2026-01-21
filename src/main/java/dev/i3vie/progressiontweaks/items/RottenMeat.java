package dev.i3vie.progressiontweaks.items;

import dev.i3vie.progressiontweaks.EviesProgressionTweaks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class RottenMeat {

    public static final DeferredItem<Item> ROTTEN_MEAT =
            EviesProgressionTweaks.ITEMS.registerItem(
                    "rotten_meat",
                    props -> new Item(
                            props.food(new FoodProperties.Builder()
                                    .nutrition(-3)
                                    .saturationModifier(-0.25F)
                                    .build()
                            )
                    )
            );

    public static void register() {
        // forces class load
    }
}
