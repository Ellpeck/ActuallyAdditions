package de.ellpeck.actuallyadditions.common.utilities;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import java.text.NumberFormat;

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
        return new TranslationTextComponent(prefixActuallyId(key), args);
    }

    /**
     * Prefixes any string with a {@link ActuallyAdditions#MOD_ID} then a (dot) with the remaining text
     * @param text suffix of mod id
     */
    public static String prefixActuallyId(String text) {
        return String.format("%s.%s", ActuallyAdditions.MOD_ID, text);
    }

    /**
     * Cleans up any values into either a short variant {1M / 1M} or a long variant
     * like {1,000,000 / 1,000,000}
     *
     * @return a cleaned and formatting version of any energy values.
     */
    public static String cleanEnergyValues(int energy, int maxEnergy, boolean small) {
        String cleanEnergy, cleanMaxEnergy;

        if (small) {
            cleanEnergy = humanReadableValue(energy);
            cleanMaxEnergy = humanReadableValue(maxEnergy);
        } else {
            NumberFormat format = NumberFormat.getInstance();
            cleanEnergy = format.format(energy);
            cleanMaxEnergy = format.format(maxEnergy);
        }

        return String.format("%s / %s", cleanEnergy, cleanMaxEnergy);
    }

    public static String cleanEnergyValues(IEnergyStorage energy, boolean small) {
        return cleanEnergyValues(energy.getEnergyStored(), energy.getMaxEnergyStored(), small);
    }

    /**
     * Pretty values, turns numbers like 100000000 into 100M
     *
     * @param value value you need prettified
     * @return a pretty string
     */
    public static String humanReadableValue(int value) {
        if (value < 1000)
            return String.valueOf(value);

        int exp = (int) (Math.log(value) / Math.log(1000));
        return String.format("%,d%c",
                (int) (value / Math.pow(1000, exp)),
                "KMGTPE_____".charAt(exp - 1));
    }
}
