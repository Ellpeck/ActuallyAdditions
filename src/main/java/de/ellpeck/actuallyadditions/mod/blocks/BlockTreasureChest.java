/*
 * This file ("BlockTreasureChest.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockTreasureChest extends BlockBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);

    public BlockTreasureChest(String name){
        super(Material.wood, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(300.0F);
        this.setResistance(50.0F);
        this.setStepSound(SoundType.WOOD);
        this.setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
        for(int i = 0; i < 2; i++){
            for(float f = 0; f <= 3; f += 0.5){
                float particleX = rand.nextFloat();
                float particleZ = rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, (double)pos.getX()+particleX, (double)pos.getY()+f+1, (double)pos.getZ()+particleZ, 0.0D, 0.2D, 0.0D);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3){
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            //TODO Fix sounds
            //world.playSoundAtEntity(player, "random.chestopen", 0.2F, Util.RANDOM.nextFloat()*0.1F+0.9F);
            this.dropItems(world, pos);
            world.setBlockToAir(pos);

            //player.triggerAchievement(TheAchievements.OPEN_TREASURE_CHEST.ach);
        }
        return true;
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
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
        return false;
    }

    private void dropItems(World world, BlockPos pos){
        for(int i = 0; i < MathHelper.getRandomIntegerInRange(Util.RANDOM, 3, 6); i++){
            TreasureChestLoot theReturn = WeightedRandom.getRandomItem(Util.RANDOM, ActuallyAdditionsAPI.treasureChestLoot);
            ItemStack itemStack = theReturn.returnItem.copy();
            itemStack.stackSize = MathHelper.getRandomIntegerInRange(Util.RANDOM, theReturn.minAmount, theReturn.maxAmount);

            float dX = Util.RANDOM.nextFloat()*0.8F+0.1F;
            float dY = Util.RANDOM.nextFloat()*0.8F+0.1F;
            float dZ = Util.RANDOM.nextFloat()*0.8F+0.1F;
            EntityItem entityItem = new EntityItem(world, pos.getX()+dX, pos.getY()+dY, pos.getZ()+dZ, itemStack.copy());
            if(itemStack.hasTagCompound()){
                entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
            }
            float factor = 0.05F;
            entityItem.motionX = Util.RANDOM.nextGaussian()*factor;
            entityItem.motionY = Util.RANDOM.nextGaussian()*factor+0.2F;
            entityItem.motionZ = Util.RANDOM.nextGaussian()*factor;
            world.spawnEntityInWorld(entityItem);
            itemStack.stackSize = 0;
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }
}
