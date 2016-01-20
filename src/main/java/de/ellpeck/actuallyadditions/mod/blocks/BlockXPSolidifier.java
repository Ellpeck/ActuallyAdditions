/*
 * This file ("BlockXPSolidifier.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityXPSolidifier;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockXPSolidifier extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);

    public BlockXPSolidifier(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(2.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityXPSolidifier();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityXPSolidifier solidifier = (TileEntityXPSolidifier)world.getTileEntity(pos);
            if(solidifier != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.XP_SOLIDIFIER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
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
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityXPSolidifier){
            TileEntityXPSolidifier solidifier = (TileEntityXPSolidifier)tile;
            if(solidifier.amount > 0){
                int stacks = solidifier.amount/64;
                int rest = solidifier.amount%64;
                for(int i = 0; i < stacks; i++){
                    this.spawnItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(InitItems.itemSolidifiedExperience, 64));
                }
                this.spawnItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(InitItems.itemSolidifiedExperience, rest));
                solidifier.amount = 0;
            }
        }

        super.breakBlock(world, pos, state);
    }

    private void spawnItem(World world, int x, int y, int z, ItemStack stack){
        float dX = Util.RANDOM.nextFloat()*0.8F+0.1F;
        float dY = Util.RANDOM.nextFloat()*0.8F+0.1F;
        float dZ = Util.RANDOM.nextFloat()*0.8F+0.1F;
        EntityItem entityItem = new EntityItem(world, x+dX, y+dY, z+dZ, stack);
        float factor = 0.05F;
        entityItem.motionX = Util.RANDOM.nextGaussian()*factor;
        entityItem.motionY = Util.RANDOM.nextGaussian()*factor+0.2F;
        entityItem.motionZ = Util.RANDOM.nextGaussian()*factor;
        world.spawnEntityInWorld(entityItem);
    }
}
