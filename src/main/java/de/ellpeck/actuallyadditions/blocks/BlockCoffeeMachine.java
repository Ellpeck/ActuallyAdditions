/*
 * This file ("BlockCoffeeMachine.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCoffeeMachine extends BlockContainerBase{

    public BlockCoffeeMachine(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);

        float f = 1/16F;
        this.setBlockBounds(f, 0F, f, 1F-f, 1F-2*f, 1F-f);
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.coffeeMachineRenderId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntityCoffeeMachine machine = (TileEntityCoffeeMachine)world.getTileEntity(x, y, z);
            if(machine != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.COFFEE_MACHINE.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.coal_block.getIcon(0, 0);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCoffeeMachine();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if(rotation == 1){
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }
        if(rotation == 2){
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        if(rotation == 3){
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        super.onBlockPlacedBy(world, x, y, z, player, stack);
    }
}
