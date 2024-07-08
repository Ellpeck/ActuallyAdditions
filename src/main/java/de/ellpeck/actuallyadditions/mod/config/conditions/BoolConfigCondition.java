package de.ellpeck.actuallyadditions.mod.config.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import net.neoforged.neoforge.common.conditions.ICondition;

public record BoolConfigCondition(String boolConfig) implements ICondition {
    public static MapCodec<BoolConfigCondition> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            Codec.STRING.fieldOf("boolConfig").forGetter(BoolConfigCondition::boolConfig))
                    .apply(builder, BoolConfigCondition::new));

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
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return "ConfigEnabled(\"" + boolConfig + "\")";
    }
}
