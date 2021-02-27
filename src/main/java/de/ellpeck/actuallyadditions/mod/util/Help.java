package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.ForgeI18n;

public class Help {
    public static TranslationTextComponent Trans(String prefix, String suffix, Object... args) {
        return new TranslationTextComponent(String.format("%s.%s.%s", prefix, ActuallyAdditions.MODID, suffix));
    }

    public static String TransI18n(String prefix, String suffix, Object... args) {
        return ForgeI18n.parseFormat(String.format("%s.%s.%s", prefix, ActuallyAdditions.MODID, suffix), args);
    }
}
