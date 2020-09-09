package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityLeafGenerator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockLeafGenerator extends BlockContainerBase {

    public BlockLeafGenerator() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityLeafGenerator();
    }
}
