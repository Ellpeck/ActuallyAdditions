package de.ellpeck.actuallyadditions.mod.config.conditions;

import com.google.gson.JsonObject;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class BoolConfigCondition implements ICondition {
    private static final ResourceLocation NAME = new ResourceLocation(ActuallyAdditions.MODID, "bool_config_condition");
    private final String boolConfig;

    public BoolConfigCondition(String config) {
        this.boolConfig = config;
    }

    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test() {
        switch (boolConfig) {
            default:
                return true;
            case "tinyCoalStuff":
                return CommonConfig.Other.TINY_COAL_STUFF.get();
        }
    }

    public static class Serializer implements IConditionSerializer<BoolConfigCondition> {
        public static final BoolConfigCondition.Serializer INSTANCE = new BoolConfigCondition.Serializer();

        @Override
        public void write(JsonObject json, BoolConfigCondition value) {
            json.addProperty("config_name", value.boolConfig);
        }

        @Override
        public BoolConfigCondition read(JsonObject json) {
            return new BoolConfigCondition(json.get("config_name").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return BoolConfigCondition.NAME;
        }
    }
}
