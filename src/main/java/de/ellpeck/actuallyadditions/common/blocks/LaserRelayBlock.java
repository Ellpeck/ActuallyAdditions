package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.types.LaserRelays;
import net.minecraft.block.material.Material;

public class LaserRelayBlock extends ActuallyBlock {
    public LaserRelayBlock(LaserRelays relayType) {
        super(Properties.create(Material.ROCK));
    }
}
