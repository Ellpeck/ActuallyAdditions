/*
 * This file ("BlockItemRepairer.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityItemRepairer;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
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

public class BlockItemRepairer extends BlockContainerBase implements IActAddItemOrBlock{

    private IIcon topIcon;
    private IIcon bottomIcon;

    public BlockItemRepairer(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(20.0F);
        this.setResistance(15.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityItemRepairer();
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 1){
            return this.topIcon;
        }
        if(side == 0){
            return this.bottomIcon;
        }
        return this.blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityItemRepairer repairer = (TileEntityItemRepairer)world.getTileEntity(x, y, z);
            if(repairer != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.REPAIRER.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Top");
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Bottom");
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) == 1 ? 12 : 0;
    }

    @Override
    public String getName(){
        return "blockItemRepairer";
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
