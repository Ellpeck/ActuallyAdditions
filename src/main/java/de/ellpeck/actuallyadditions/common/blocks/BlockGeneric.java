package de.ellpeck.actuallyadditions.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockGeneric extends Block {

    public BlockGeneric() {
        this(Material.ROCK, SoundType.STONE, 1.5F, 10.0F, ToolType.PICKAXE, 0);
    }

    public BlockGeneric(Material material, SoundType sound, float hardness, float resistance, ToolType harvestTool, int harvestLevel) {
        super(Properties.create(material)
                .hardnessAndResistance(hardness, resistance)
                .harvestTool(harvestTool)
                .harvestLevel(harvestLevel)
                .sound(sound));
    }
}
