/*
 * This file ("BlockPlayerInterface.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPlayerInterface;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPlayerInterface extends BlockContainerBase implements IHudDisplay{

    public BlockPlayerInterface(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityPlayerInterface();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack){
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityPlayerInterface){
            TileEntityPlayerInterface face = (TileEntityPlayerInterface)tile;
            if(face.connectedPlayer == null){
                face.connectedPlayer = player.getUniqueID();
                face.playerName = player.getName();
                face.markDirty();
                face.sendUpdate();
            }
        }

        super.onBlockPlacedBy(world, pos, state, player, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
        if(tile != null){
            if(tile instanceof TileEntityPlayerInterface){
                TileEntityPlayerInterface face = (TileEntityPlayerInterface)tile;
                String name = face.playerName == null ? "Unknown" : face.playerName;
                minecraft.fontRendererObj.drawStringWithShadow("Bound to: "+TextFormatting.RED+name, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+5, StringUtil.DECIMAL_COLOR_WHITE);
                minecraft.fontRendererObj.drawStringWithShadow("UUID: "+TextFormatting.DARK_GREEN+face.connectedPlayer, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+15, StringUtil.DECIMAL_COLOR_WHITE);
            }
        }
    }
}
