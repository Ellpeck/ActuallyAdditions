/*
 * This file ("BlockGrinder.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.ActuallyAdditions;
import de.ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockGrinder extends BlockContainerBase{

    private final boolean isDouble;
    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon onIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;

    public BlockGrinder(boolean isDouble, String name){
        super(Material.rock, name);
        this.isDouble = isDouble;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isDouble ? new TileEntityGrinder.TileEntityGrinderDouble() : new TileEntityGrinder();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(side == 1 && meta != 1){
            return this.topIcon;
        }
        if(side == 1){
            return this.onIcon;
        }
        if(side == 0){
            return this.bottomIcon;
        }
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand){
        int meta = world.getBlockMetadata(x, y, z);

        if(meta == 1){
            for(int i = 0; i < 5; i++){
                double xRand = rand.nextDouble()/0.75D-0.5D;
                double zRand = rand.nextDouble()/0.75D-0.5D;
                world.spawnParticle("crit", (double)x+0.4F, (double)y+0.8F, (double)z+0.4F, xRand, 0.5D, zRand);
            }
            world.spawnParticle(ClientProxy.bulletForMyValentine ? "heart" : "smoke", (double)x+0.5F, (double)y+1.0F, (double)z+0.5F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityGrinder grinder = (TileEntityGrinder)world.getTileEntity(x, y, z);
            if(grinder != null){
                player.openGui(ActuallyAdditions.instance, this.isDouble ? GuiHandler.GuiTypes.GRINDER_DOUBLE.ordinal() : GuiHandler.GuiTypes.GRINDER.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":blockGrinderTop");
        this.onIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":blockGrinderOn");
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":blockGrinderBottom");
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) == 1 ? 12 : 0;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }
}
