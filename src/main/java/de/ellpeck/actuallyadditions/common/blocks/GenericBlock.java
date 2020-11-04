package de.ellpeck.actuallyadditions.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class GenericBlock extends ActuallyBlock {
    public GenericBlock() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5F, 10.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(0)
                .sound(SoundType.STONE));
    }
}