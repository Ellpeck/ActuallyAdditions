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

    /**
     * Pretty values, turns numbers like 100000000 into 100M
     *
     * @param value value you need prettified
     * @return a pretty string
     */
    public static String compressedValue(int value) {
        if (value < 1000)
            return String.valueOf(value);

        int exp = (int) (Math.log(value) / Math.log(1000));
        return String.format("%,d%c",
                (int) (value / Math.pow(1000, exp)),
                "KMGTPE_____".charAt(exp - 1));
    }
}
