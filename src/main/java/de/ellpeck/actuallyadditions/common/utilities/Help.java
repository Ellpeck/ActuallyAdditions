package de.ellpeck.actuallyadditions.common.utilities;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * HELP! FIRE!... I mean, this class should stop fires cure my ocd...
 */
public class Help {
    /**
     * Builds a TranslationTextComponent using the MOD_ID prefix and a remaining key.
     *
     * @param key remaining key after prefix of the {@link ActuallyAdditions} MOD_ID
     * @return pretty prefixed translated string
     */
    public static TranslationTextComponent trans(String key, Object... args) {
        return new TranslationTextComponent(String.format("%s.%s", ActuallyAdditions.MOD_ID, key), args);
    }
}
