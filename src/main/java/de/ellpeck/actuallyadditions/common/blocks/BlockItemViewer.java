package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityItemViewer;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class BlockItemViewer extends BlockContainerBase {

    public BlockItemViewer() {
        super(Properties.create(Material.ROCK)
                .harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance(1.5f, 10.0f)
                .sound(SoundType.STONE));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityItemViewer();
    }
}
