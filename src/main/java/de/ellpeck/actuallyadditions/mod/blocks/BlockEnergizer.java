/*
 * This file ("BlockEnergizer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnergizer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEnergizer extends BlockContainerBase{

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    private boolean isEnergizer;

    public BlockEnergizer(boolean isEnergizer, String name){
        super(Material.rock, name);
        this.isEnergizer = isEnergizer;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isEnergizer ? new TileEntityEnergizer() : new TileEntityEnervator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        return side == 1 ? this.topIcon : (side == 0 ? this.blockIcon : this.sideIcon);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            if(this.isEnergizer){
                TileEntityEnergizer energizer = (TileEntityEnergizer)world.getTileEntity(x, y, z);
                if(energizer != null){
                    player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.ENERGIZER.ordinal(), world, x, y, z);
                }
            }
            else{
                TileEntityEnervator energizer = (TileEntityEnervator)world.getTileEntity(x, y, z);
                if(energizer != null){
                    player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.ENERVATOR.ordinal(), world, x, y, z);
                }
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Top");
        this.sideIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Side");
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
