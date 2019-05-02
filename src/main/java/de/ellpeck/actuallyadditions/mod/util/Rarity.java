package de.ellpeck.actuallyadditions.mod.util;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IRarity;

public class Rarity implements IRarity {

    TextFormatting color;
    String name;

    public Rarity(TextFormatting color, String name) {
        this.color = color;
        this.name = name;
    }

    @Override
    public TextFormatting getColor() {
        return color;
    }

    @Override
    public String getName() {
        return name;
    }

}
