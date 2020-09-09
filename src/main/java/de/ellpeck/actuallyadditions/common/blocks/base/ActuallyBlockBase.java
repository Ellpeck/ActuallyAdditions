package de.ellpeck.actuallyadditions.common.blocks.base;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public abstract class ActuallyBlockBase extends Block {
    protected static final Properties STONE_PROPS = Block.Properties.create(Material.ROCK)
            .harvestTool(ToolType.PICKAXE)
            .sound(SoundType.STONE);

    protected static final Properties STONE_PROPS_WITH_TICK = STONE_PROPS.tickRandomly();
    protected static final Properties STONE_PROPS_WITH_HARDNESS = STONE_PROPS.hardnessAndResistance(1.5f, 10.0f);

    public ActuallyBlockBase(Properties properties) {
        super(properties);
    }

    /**
     * Define custom BlockItem Properties for a specific block. This is useful for
     * when you need to modify the stack size. I'd recommend using super.getBlockItemProps()
     * and adding to this response.
     *
     * @return default item properties for our blockItems
     */
    public Item.Properties getBlockItemProps() {
        return new Item.Properties().group(ActuallyAdditions.aaGroup);
    }

    /**
     * If the block has a custom BlockItem to define custom behavior then
     * override this method and return true to stop auto registration
     */
    public boolean hasCustomBlockItem() {
        return false;
    }
}
