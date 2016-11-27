/*
 * This file ("BlockDistributorItem.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityDistributorItem;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockDistributorItem extends BlockContainerBase implements IHudDisplay{

    public BlockDistributorItem(String name){
        super(Material.ROCK, name);

        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.75F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityDistributorItem();
    }

    @Override
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
        if(tile instanceof TileEntityDistributorItem){
            TileEntityDistributorItem distributor = (TileEntityDistributorItem)tile;
            ItemStack slot = distributor.getStackInSlot(0);

            String strg;
            if(!StackUtil.isValid(slot)){
                strg = StringUtil.localize("info."+ModUtil.MOD_ID+".noItem");
            }
            else{
                strg = slot.getItem().getItemStackDisplayName(slot);
                AssetUtil.renderStackToGui(slot, resolution.getScaledWidth()/2+15, resolution.getScaledHeight()/2-19, 1F);
            }
            minecraft.fontRendererObj.drawStringWithShadow(TextFormatting.YELLOW+""+TextFormatting.ITALIC+strg, resolution.getScaledWidth()/2+35, resolution.getScaledHeight()/2-15, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }
}
