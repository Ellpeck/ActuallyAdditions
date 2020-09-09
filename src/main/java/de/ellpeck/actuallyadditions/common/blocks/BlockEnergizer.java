package de.ellpeck.actuallyadditions.blocks;

import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.tile.TileEntityEnergizer;
import de.ellpeck.actuallyadditions.tile.TileEntityEnervator;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockEnergizer extends BlockContainerBase {

    private final boolean isEnergizer;

    public BlockEnergizer(boolean isEnergizer) {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(2.0f, 100f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));

        this.isEnergizer = isEnergizer;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return this.isEnergizer ? new TileEntityEnergizer() : new TileEntityEnervator();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            if (this.isEnergizer) {
                TileEntityEnergizer energizer = (TileEntityEnergizer) world.getTileEntity(pos);
                if (energizer != null) {
                    player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.ENERGIZER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            } else {
                TileEntityEnervator energizer = (TileEntityEnervator) world.getTileEntity(pos);
                if (energizer != null) {
                    player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.ENERVATOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }
}
