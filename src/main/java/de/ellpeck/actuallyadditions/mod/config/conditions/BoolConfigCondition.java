package de.ellpeck.actuallyadditions.mod.config.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public record BoolConfigCondition(String boolConfig) implements ICondition {
    public static Codec<BoolConfigCondition> CODEC = RecordCodecBuilder.create(
            builder -> builder
                    .group(
                            Codec.STRING.fieldOf("boolConfig").forGetter(BoolConfigCondition::boolConfig))
                    .apply(builder, BoolConfigCondition::new));
    private static final ResourceLocation NAME = new ResourceLocation(ActuallyAdditions.MODID, "bool_config_condition");

    @Override
    public boolean test(IContext condition) {
        switch (boolConfig) {
            default:
                return true;
            case "tinyCoalStuff":
                return CommonConfig.Other.TINY_COAL_STUFF.get();
        }
    }

    @Override
    public Codec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return "ConfigEnabled(\"" + boolConfig + "\")";
    }
}
