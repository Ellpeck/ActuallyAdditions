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
import ellpeck.actuallyadditions.booklet.EntrySet;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.tile.TileEntityBookletStand;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockBookletStand extends BlockContainerBase implements IHudDisplay{

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
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.planks.getIcon(0, 0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
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

        super.onBlockPlacedBy(world, x, y, z, player, stack);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityBookletStand();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, MovingObjectPosition posHit, Profiler profiler, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.blockX, posHit.blockY, posHit.blockZ);
        if(tile instanceof TileEntityBookletStand){
            EntrySet set = ((TileEntityBookletStand)tile).assignedEntry;

            String strg1;
            String strg2;
            if(set.entry == null){
                strg1 = "No entry saved! Save one if";
                strg2 = "you are the player who placed it!";
            }
            else if(set.chapter == null){
                strg1 = set.entry.getLocalizedName();
                strg2 = "Page "+set.pageInIndex;
            }
            else{
                strg1 = set.chapter.getLocalizedName();
                strg2 = "Page "+set.page.getID();

                AssetUtil.renderStackToGui(set.chapter.displayStack != null ? set.chapter.displayStack : new ItemStack(InitItems.itemBooklet), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+10, 1F);
            }
            minecraft.fontRenderer.drawStringWithShadow(EnumChatFormatting.YELLOW+""+EnumChatFormatting.ITALIC+strg1, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+8, StringUtil.DECIMAL_COLOR_WHITE);
            minecraft.fontRenderer.drawStringWithShadow(EnumChatFormatting.YELLOW+""+EnumChatFormatting.ITALIC+strg2, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+18, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}
