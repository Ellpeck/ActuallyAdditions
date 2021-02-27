/*
 * This file ("BlockMiner.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityMiner;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;

public class BlockMiner extends BlockContainerBase implements IHudDisplay {

    public BlockMiner() {
        super(Material.ROCK, this.name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(8F);
        this.setResistance(30F);
        this.setSoundType(SoundType.STONE);
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityMiner) {
                player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.MINER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityMiner();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        TileEntity tile = minecraft.world.getTileEntity(rayCast.getBlockPos());
        if (tile instanceof TileEntityMiner) {
            TileEntityMiner miner = (TileEntityMiner) tile;
            String info = miner.checkY == 0
                ? "Done Mining!"
                : miner.checkY == -1
                    ? "Calculating positions..."
                    : "Mining at " + (miner.getPos().getX() + miner.checkX) + ", " + miner.checkY + ", " + (miner.getPos().getZ() + miner.checkZ) + ".";
            minecraft.fontRenderer.drawStringWithShadow(info, resolution.getScaledWidth() / 2 + 5, resolution.getScaledHeight() / 2 - 20, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }
}
