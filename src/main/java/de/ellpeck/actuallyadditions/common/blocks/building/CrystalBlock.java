package de.ellpeck.actuallyadditions.common.blocks.building;

import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

public class CrystalBlock extends ActuallyBlock {
    private final boolean isEmpowered;

    public CrystalBlock(boolean isEmpowered) {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1));

        this.isEmpowered = isEmpowered;
    }

    @Override
    public BlockItem createBlockItem() {
        return new BlockItem(this, getItemProperties()) {
            @Override
            public boolean hasEffect(ItemStack stack) {
                return isEmpowered;
            }
        };
    }
}
