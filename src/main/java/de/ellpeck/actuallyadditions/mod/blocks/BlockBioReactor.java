package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBioReactor;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockBioReactor extends BlockContainerBase {

    public BlockBioReactor() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(2f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new TileEntityBioReactor();
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit){
        if (!world.isRemote) {
            if (world.getTileEntity(pos) instanceof TileEntityBioReactor) {
                // todo open gui: player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.BIO_REACTOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return ActionResultType.SUCCESS;
    }
    
    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
}
