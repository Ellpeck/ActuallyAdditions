/*
 * This file ("BlockCoffeeMachine.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

//TODO Fix bounding box
public class BlockCoffeeMachine extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);

    public BlockCoffeeMachine(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(SoundType.STONE);
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntityCoffeeMachine machine = (TileEntityCoffeeMachine)world.getTileEntity(pos);
            if(machine != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.COFFEE_MACHINE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCoffeeMachine();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            PosUtil.setMetadata(pos, world, 0, 2);
        }
        if(rotation == 1){
            PosUtil.setMetadata(pos, world, 3, 2);
        }
        if(rotation == 2){
            PosUtil.setMetadata(pos, world, 1, 2);
        }
        if(rotation == 3){
            PosUtil.setMetadata(pos, world, 2, 2);
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }
}
