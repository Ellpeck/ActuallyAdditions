package de.ellpeck.actuallyadditions.common.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public abstract class ActuallyBlockBase extends Block {
    private static final Properties STONE_PROPS = Block.Properties.create(Material.ROCK)
            .harvestTool(ToolType.PICKAXE)
            .sound(SoundType.STONE);

    private static final Properties STONE_PROPS_WITH_TICK = STONE_PROPS.tickRandomly();
    private static final Properties STONE_PROPS_WITH_HARDNESS = STONE_PROPS.hardnessAndResistance(1.5f, 10.0f);

    public ActuallyBlockBase(Properties properties) {
        super(properties);
    }

    
}
