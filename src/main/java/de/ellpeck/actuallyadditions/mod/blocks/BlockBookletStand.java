/*
 * This file ("BlockBookletStand.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.api.block.IHudDisplay;
import de.ellpeck.actuallyadditions.api.internal.EntrySet;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBookletStand;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public int getRenderType(){
        return AssetUtil.TESR_RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
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
        Position thePos = Position.fromBlockPos(pos);

        if(rotation == 0){
            thePos.setMetadata(world, 2, 2);
        }
        if(rotation == 1){
            thePos.setMetadata(world, 1, 2);
        }
        if(rotation == 2){
            thePos.setMetadata(world, 0, 2);
        }
        if(rotation == 3){
            thePos.setMetadata(world, 3, 2);
        }

        TileEntityBookletStand tile = (TileEntityBookletStand)world.getTileEntity(pos);
        if(tile != null){
            //Assign a UUID
            if(tile.assignedPlayer == null){
                tile.assignedPlayer = player.getName();
                tile.markDirty();
                tile.sendUpdate();
            }
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityBookletStand();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, MovingObjectPosition posHit, Profiler profiler, ScaledResolution resolution){
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
            minecraft.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW+""+EnumChatFormatting.ITALIC+strg1, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+8, StringUtil.DECIMAL_COLOR_WHITE);
            minecraft.fontRendererObj.drawStringWithShadow(EnumChatFormatting.YELLOW+""+EnumChatFormatting.ITALIC+strg2, resolution.getScaledWidth()/2+25, resolution.getScaledHeight()/2+18, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}
