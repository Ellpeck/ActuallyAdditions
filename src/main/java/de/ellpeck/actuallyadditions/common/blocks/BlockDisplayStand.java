package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.common.tile.TileEntityDisplayStand;
import de.ellpeck.actuallyadditions.common.util.ItemUtil;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockDisplayStand extends BlockContainerBase {

    public BlockDisplayStand() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDisplayStand();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BlockSlabs.AABB_BOTTOM_HALF;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (!world.isRemote) {
            TileEntityDisplayStand stand = (TileEntityDisplayStand) world.getTileEntity(pos);
            if (stand != null) {
                ItemStack display = stand.inv.getStackInSlot(0);
                if (StackUtil.isValid(heldItem)) {
                    if (!StackUtil.isValid(display)) {
                        ItemStack toPut = heldItem.copy();
                        toPut.setCount(1);
                        stand.inv.setStackInSlot(0, toPut);
                        if (!player.capabilities.isCreativeMode) heldItem.shrink(1);
                        return true;
                    } else if (ItemUtil.canBeStacked(heldItem, display)) {
                        int maxTransfer = Math.min(display.getCount(), heldItem.getMaxStackSize() - heldItem.getCount());
                        if (maxTransfer > 0) {
                            heldItem.grow(maxTransfer);
                            ItemStack newDisplay = display.copy();
                            newDisplay.shrink(maxTransfer);
                            stand.inv.setStackInSlot(0, newDisplay);
                            return true;
                        }
                    }
                } else {
                    if (StackUtil.isValid(display)) {
                        player.setHeldItem(hand, display.copy());
                        stand.inv.setStackInSlot(0, StackUtil.getEmpty());
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.DOWN) return BlockFaceShape.SOLID;
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
