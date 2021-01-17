package de.ellpeck.actuallyadditions.common.utilities;

import net.minecraft.client.resources.I18n;

public class ClientHelp {
    public static String i18n(String key, Object... args) {
        return I18n.format(Help.prefixActuallyId(key), args);
    }
}
