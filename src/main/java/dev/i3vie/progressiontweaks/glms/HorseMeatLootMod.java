package dev.i3vie.progressiontweaks.glms;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.i3vie.progressiontweaks.items.HorseMeats;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class HorseMeatLootMod extends LootModifier {
    public static final MapCodec<HorseMeatLootMod> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    Codec.INT.fieldOf("min").forGetter(e -> e.min),
                    Codec.INT.fieldOf("max").forGetter(e -> e.max)
            )).apply(inst, HorseMeatLootMod::new)
    );
    private final int min;
    private final int max;

    public HorseMeatLootMod(LootItemCondition[] conditions, int min, int max) {
        super(conditions);
        this.min = min;
        this.max = max;
    }

    @Override
    public @NotNull MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (!context.hasParameter(LootContextParams.THIS_ENTITY)) return generatedLoot;
        if (!(context.getParameter(LootContextParams.THIS_ENTITY) instanceof Horse)) return generatedLoot;

        int count = context.getRandom().nextInt(min, max + 1);
        Entity source = context.getOptionalParameter(LootContextParams.THIS_ENTITY);

        if (source == null) return generatedLoot;

        Item drop = source.isOnFire()
                ? HorseMeats.COOKED_HORSE_MEAT.get() : HorseMeats.RAW_HORSE_MEAT.get();

        generatedLoot.add(new ItemStack(drop, count));
        return generatedLoot;
    }
}