/*
 * This file ("BlockFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceDouble;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockFurnaceDouble extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 7);

    public BlockFurnaceDouble(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFurnaceDouble();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
        int meta = this.getMetaFromState(state);

        if(meta > 3){
            float f = (float)pos.getX()+0.5F;
            float f1 = (float)pos.getY()+0.0F+rand.nextFloat()*6.0F/16.0F;
            float f2 = (float)pos.getZ()+0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat()*0.6F-0.3F;

            if(meta == 6){
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f-f3), (double)f1, (double)(f2+f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.FLAME, (double)(f-f3), (double)f1, (double)(f2+f4), 0.0D, 0.0D, 0.0D);
            }
            if(meta == 7){
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f+f3), (double)f1, (double)(f2+f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.FLAME, (double)(f+f3), (double)f1, (double)(f2+f4), 0.0D, 0.0D, 0.0D);
            }
            if(meta == 4){
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f+f4), (double)f1, (double)(f2-f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.FLAME, (double)(f+f4), (double)f1, (double)(f2-f3), 0.0D, 0.0D, 0.0D);
            }
            if(meta == 5){
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)(f+f4), (double)f1, (double)(f2+f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.FLAME, (double)(f+f4), (double)f1, (double)(f2+f3), 0.0D, 0.0D, 0.0D);
            }

            for(int i = 0; i < 5; i++){
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double)pos.getX()+0.5F, (double)pos.getY()+1.0F, (double)pos.getZ()+0.5F, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityFurnaceDouble furnace = (TileEntityFurnaceDouble)world.getTileEntity(pos);
            if(furnace != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.FURNACE_DOUBLE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
        return this.getMetaFromState(state) > 3 ? 12 : 0;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            world.setBlockState(pos, this.getStateFromMeta(0), 2);
        }
        if(rotation == 1){
            world.setBlockState(pos, this.getStateFromMeta(3), 2);
        }
        if(rotation == 2){
            world.setBlockState(pos, this.getStateFromMeta(1), 2);
        }
        if(rotation == 3){
            world.setBlockState(pos, this.getStateFromMeta(2), 2);
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
            tooltip.add(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".previouslyDoubleFurnace"));
        }
    }
}
