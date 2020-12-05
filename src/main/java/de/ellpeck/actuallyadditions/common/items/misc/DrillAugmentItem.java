package de.ellpeck.actuallyadditions.common.items.misc;

import de.ellpeck.actuallyadditions.common.items.ActuallyItem;

public class DrillAugmentItem extends ActuallyItem {
    private final AugmentType type;

    public DrillAugmentItem(AugmentType type) {
        super(baseProps().maxStackSize(1));
        this.type = type;
    }

    public AugmentType getType() {
        return type;
    }

    public enum AugmentType {
        SPEED_AUGMENT_I,
        SPEED_AUGMENT_II,
        SPEED_AUGMENT_III,
        SILK_TOUCH_AUGMENT,
        FORTUNE_AUGMENT_I,
        FORTUNE_AUGMENT_II,
        MINING_AUGMENT_I,
        MINING_AUGMENT_II,
        BLOCK_PLACING_AUGMENT
    }
}
