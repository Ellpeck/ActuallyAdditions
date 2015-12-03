/*
 * This file ("BlockGiantChest.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.blocks.base.BlockContainerBase;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityGiantChest;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGiantChest extends BlockContainerBase{

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;

    public BlockGiantChest(String name){
        super(Material.wood, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(0.5F);
        this.setResistance(15.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityGiantChest();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return side == 1 ? this.topIcon : (side == 0 ? this.bottomIcon : this.blockIcon);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityGiantChest chest = (TileEntityGiantChest)world.getTileEntity(x, y, z);
            if(chest != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.GIANT_CHEST.ordinal(), world, x, y, z);
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
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+"Bottom");
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
