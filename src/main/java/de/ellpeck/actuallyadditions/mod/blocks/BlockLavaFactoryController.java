/*
 * This file ("BlockLavaFactoryController.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLavaFactoryController;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.api.distmarker.Dist;


public class BlockLavaFactoryController extends BlockContainerBase implements IHudDisplay {

    public BlockLavaFactoryController() {
        super(Material.ROCK, this.name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(20.0F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityLavaFactoryController();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        TileEntityLavaFactoryController factory = (TileEntityLavaFactoryController) minecraft.world.getTileEntity(rayCast.getBlockPos());
        if (factory != null) {
            int state = factory.isMultiblock();
            if (state == TileEntityLavaFactoryController.NOT_MULTI) {
                StringUtil.drawSplitString(minecraft.fontRenderer, StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".factory.notPart.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 5, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
            } else if (state == TileEntityLavaFactoryController.HAS_AIR || state == TileEntityLavaFactoryController.HAS_LAVA) {
                StringUtil.drawSplitString(minecraft.fontRenderer, StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".factory.works.desc"), resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 + 5, 200, StringUtil.DECIMAL_COLOR_WHITE, true);
            }
        }
    }
}
