/*
 * This file ("BlockBookletStand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.booklet.entry.EntrySet;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class BlockBookletStand extends BlockContainerBase implements IHudDisplay{

    public BlockBookletStand(String name){
        super(Material.WOOD, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setResistance(4.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.BOOK_STAND.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
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

        TileEntityBookletStand tile = (TileEntityBookletStand)world.getTileEntity(pos);
        if(tile != null){
            if(tile.assignedPlayer == null){
                tile.assignedPlayer = player.getName();
                tile.markDirty();
                tile.sendUpdate();
            }
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int par2){
        return new TileEntityBookletStand();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, Profiler profiler, ScaledResolution resolution){
        TileEntity tile = minecraft.theWorld.getTileEntity(posHit.getBlockPos());
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

                AssetUtil.renderStackToGui(set.chapter.getDisplayItemStack() != null ? set.chapter.getDisplayItemStack() : new ItemStack(InitItems.itemBooklet), resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+10, 1F);
            }
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg1, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+8, StringUtil.DECIMAL_COLOR_WHITE);
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg2, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+18, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}
