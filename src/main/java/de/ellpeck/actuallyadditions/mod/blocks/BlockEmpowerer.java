/*
 * This file ("BlockEmpowerer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEmpowerer;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

public class BlockEmpowerer extends BlockContainerBase{

    public BlockEmpowerer(String name){
        super(Material.ROCK, name);

        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityEmpowerer();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BlockSlabs.AABB_BOTTOM_HALF;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        ItemStack heldItem = player.getHeldItem(hand);
        if(!world.isRemote){
            TileEntityEmpowerer empowerer = (TileEntityEmpowerer)world.getTileEntity(pos);
            if(empowerer != null){
                ItemStack stackThere = empowerer.slots.getStackInSlot(0);
                if(StackUtil.isValid(heldItem)){
                    if(!StackUtil.isValid(stackThere) && !TileEntityEmpowerer.getRecipesForInput(heldItem).isEmpty()){
                        ItemStack toPut = heldItem.copy();
                        toPut = StackUtil.setStackSize(toPut, 1);
                        empowerer.slots.setStackInSlot(0, toPut);
                        player.setHeldItem(hand, StackUtil.addStackSize(heldItem, -1));
                        return true;
                    }
                    else if(ItemUtil.canBeStacked(heldItem, stackThere)){
                        int maxTransfer = Math.min(StackUtil.getStackSize(stackThere), heldItem.getMaxStackSize()-StackUtil.getStackSize(heldItem));
                        if(maxTransfer > 0){
                            player.setHeldItem(hand, StackUtil.addStackSize(heldItem, maxTransfer));
                            ItemStack newStackThere = stackThere.copy();
                            newStackThere = StackUtil.addStackSize(newStackThere, -maxTransfer);
                            empowerer.slots.setStackInSlot(0, StackUtil.validateCheck(newStackThere));
                            return true;
                        }
                    }
                }
                else{
                    if(StackUtil.isValid(stackThere)){
                        player.setHeldItem(hand, stackThere.copy());
                        empowerer.slots.setStackInSlot(0, StackUtil.getNull());
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
