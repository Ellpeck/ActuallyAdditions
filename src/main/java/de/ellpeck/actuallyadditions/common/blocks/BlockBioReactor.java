package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityBioReactor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockBioReactor extends BlockContainerBase {

    public BlockBioReactor() {
        super(STONE_PROPS.hardnessAndResistance(2f, 10.0f));
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new TileEntityBioReactor();
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        if (!world.isRemote) {
            if (world.getTileEntity(pos) instanceof TileEntityBioReactor) {
                // todo open gui:
                //  player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.BIO_REACTOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return ActionResultType.SUCCESS;
    }
}
