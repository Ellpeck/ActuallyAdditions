package de.ellpeck.actuallyadditions.mod.util;

import net.neoforged.neoforge.energy.IEnergyStorage;

import java.text.NumberFormat;

@Deprecated
public class Lang {
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
     *
     * @return a pretty string
     */
    public static String humanReadableValue(int value) {
        if (value < 1000) {
            return String.valueOf(value);
        }

        int exp = (int) (Math.log(value) / Math.log(1000));
        return String.format(
            "%,d%c",
            (int) (value / Math.pow(1000, exp)),
            "KMGTPE_____".charAt(exp - 1)
        );
    }
}
