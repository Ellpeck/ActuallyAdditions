package de.ellpeck.actuallyadditions.mod.items.misc;


import de.ellpeck.actuallyadditions.mod.items.base.ActuallyItem;

// Superseded by ItemDrillUpgrade
@Deprecated
public class DrillAugmentItem extends ActuallyItem {
    private final AugmentType type;

    public DrillAugmentItem(AugmentType type) {
        super(baseProps().stacksTo(1));
        this.type = type;
    }

    public AugmentType getType() {
        return type;
    }

    public enum AugmentType {
        SPEED_AUGMENT_I(50),
        SPEED_AUGMENT_II(75),
        SPEED_AUGMENT_III(175),
        SILK_TOUCH_AUGMENT(100),
        FORTUNE_AUGMENT_I(40),
        FORTUNE_AUGMENT_II(80),
        MINING_AUGMENT_I(10),
        MINING_AUGMENT_II(30),
        BLOCK_PLACING_AUGMENT(0);

        int energyCost;

        AugmentType(int energyCost) {
            this.energyCost = energyCost;
        }

        public int getEnergyCost() {
            return energyCost;
        }
    }
}
