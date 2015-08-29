/*
 * This file ("BlockSmileyCloud.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntitySmileyCloud;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSmileyCloud extends BlockContainerBase implements INameableItem{

    public BlockSmileyCloud(){
        super(Material.cloth);
        this.setHardness(0.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeCloth);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;

        if(meta == 0){
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F-f*3F);
        }
        if(meta == 1){
            this.setBlockBounds(0F, 0F, 0F, 1F-f*3F, 1F, 1F);
        }
        if(meta == 2){
            this.setBlockBounds(0F, 0F, f*3F, 1F, 1F, 1F);
        }
        if(meta == 3){
            this.setBlockBounds(f*3F, 0F, 0F, 1F, 1F, 1F);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileEntitySmileyCloud){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.CLOUD.ordinal(), world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if (rotation == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        if (rotation == 1) world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        if (rotation == 2) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        if (rotation == 3) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.wool.getIcon(0, 0);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.SMILEY_CLOUD_RENDER_ID;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntitySmileyCloud();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return "blockSmileyCloud";
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
