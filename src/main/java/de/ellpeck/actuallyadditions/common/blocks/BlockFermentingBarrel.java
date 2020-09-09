package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.common.tile.TileEntityFermentingBarrel;
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

public class BlockFermentingBarrel extends BlockContainerBase {

    public BlockFermentingBarrel() {
        super(Block.Properties.create(Material.WOOD)
                .hardnessAndResistance(0.5f, 5.0f)
                .harvestTool(ToolType.AXE)
                .sound(SoundType.WOOD));
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityFermentingBarrel();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            TileEntityFermentingBarrel press = (TileEntityFermentingBarrel) world.getTileEntity(pos);
            if (press != null) {
                if (!this.tryUseItemOnTank(player, hand, press.canolaTank) && !this.tryUseItemOnTank(player, hand, press.oilTank)) {
                    player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.FERMENTING_BARREL.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
