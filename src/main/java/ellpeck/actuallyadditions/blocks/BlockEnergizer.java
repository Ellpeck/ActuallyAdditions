/*
 * This file ("BlockEnergizer.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntityEnergizer;
import ellpeck.actuallyadditions.tile.TileEntityEnervator;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockEnergizer extends BlockContainerBase implements IActAddItemOrBlock{

    private IIcon topIcon;
    private IIcon sideIcon;
    private boolean isEnergizer;

    public BlockEnergizer(boolean isEnergizer){
        super(Material.rock);
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
    public IIcon getIcon(int side, int meta){
        return side == 1 ? this.topIcon : (side == 0 ? this.blockIcon : this.sideIcon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Top");
        this.sideIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Side");
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
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return this.isEnergizer ? "blockEnergizer" : "blockEnervator";
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.uncommon;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
