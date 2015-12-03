/*
 * This file ("BlockBookletStand.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntityBookletStand;
import ellpeck.actuallyadditions.util.AssetUtil;
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

public class BlockBookletStand extends BlockContainerBase{

    public BlockBookletStand(String name){
        super(Material.wood, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setResistance(4.0F);
        this.setStepSound(soundTypeWood);

        float f = 1/16F;
        this.setBlockBounds(f, 0F, f, 1F-f, 1F-4*f, 1F-f);
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.bookletStandRenderId;
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
        player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.BOOK_STAND.ordinal(), world, x, y, z);
        return true;
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

        TileEntityBookletStand tile = (TileEntityBookletStand)world.getTileEntity(x, y, z);
        if(tile != null){
            //Assign a UUID
            if(tile.assignedPlayer == null){
                tile.assignedPlayer = player.getCommandSenderName();
                tile.markDirty();
                tile.sendUpdate();
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.planks.getIcon(0, 0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityBookletStand();
    }
}
