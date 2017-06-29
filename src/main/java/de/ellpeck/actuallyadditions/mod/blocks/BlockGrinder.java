/*
 * This file ("BlockGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinderDouble;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGrinder extends BlockContainerBase{

    private final boolean isDouble;

    public BlockGrinder(boolean isDouble, String name){
        super(Material.ROCK, name);
        this.isDouble = isDouble;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isDouble ? new TileEntityGrinderDouble() : new TileEntityGrinder();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
        if(state.getValue(BlockFurnaceDouble.IS_ON)){
            for(int i = 0; i < 5; i++){
                double xRand = rand.nextDouble()/0.75D-0.5D;
                double zRand = rand.nextDouble()/0.75D-0.5D;
                world.spawnParticle(EnumParticleTypes.CRIT, (double)pos.getX()+0.4F, (double)pos.getY()+0.8F, (double)pos.getZ()+0.4F, xRand, 0.5D, zRand);
            }
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)pos.getX()+0.5F, (double)pos.getY()+1.0F, (double)pos.getZ()+0.5F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityGrinder grinder = (TileEntityGrinder)world.getTileEntity(pos);
            if(grinder != null){
                player.openGui(ActuallyAdditions.instance, this.isDouble ? GuiHandler.GuiTypes.GRINDER_DOUBLE.ordinal() : GuiHandler.GuiTypes.GRINDER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
        return this.getMetaFromState(state) == 1 ? 12 : 0;
    }

    @Override
    public int damageDropped(IBlockState state){
        return 0;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }


    @Override
    public IBlockState getStateFromMeta(int meta){
        boolean isOn = meta == 1;
        return this.getDefaultState().withProperty(BlockFurnaceDouble.IS_ON, isOn);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(BlockFurnaceDouble.IS_ON) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockFurnaceDouble.IS_ON);
    }
}
