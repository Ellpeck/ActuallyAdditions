/*
 * This file ("BlockGrinder.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinderDouble;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;

import java.util.Random;

public class BlockGrinder extends BlockContainerBase {

    private final boolean isDouble;

    public BlockGrinder(boolean isDouble) {
        super(Material.ROCK, this.name);
        this.isDouble = isDouble;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return this.isDouble
            ? new TileEntityGrinderDouble()
            : new TileEntityGrinder();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(BlockFurnaceDouble.IS_ON)) {
            for (int i = 0; i < 5; i++) {
                double xRand = rand.nextDouble() / 0.75D - 0.5D;
                double zRand = rand.nextDouble() / 0.75D - 0.5D;
                world.spawnParticle(EnumParticleTypes.CRIT, (double) pos.getX() + 0.4F, (double) pos.getY() + 0.8F, (double) pos.getZ() + 0.4F, xRand, 0.5D, zRand);
            }
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) pos.getX() + 0.5F, (double) pos.getY() + 1.0F, (double) pos.getZ() + 0.5F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntityGrinder grinder = (TileEntityGrinder) world.getTileEntity(pos);
            if (grinder != null) {
                player.openGui(ActuallyAdditions.INSTANCE, this.isDouble
                    ? GuiHandler.GuiTypes.GRINDER_DOUBLE.ordinal()
                    : GuiHandler.GuiTypes.GRINDER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockAccess world, BlockPos pos) {
        return this.getMetaFromState(state) == 1
            ? 12
            : 0;
    }

    @Override
    public int damageDropped(BlockState state) {
        return 0;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        boolean isOn = meta == 1;
        return this.getDefaultState().withProperty(BlockFurnaceDouble.IS_ON, isOn);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(BlockFurnaceDouble.IS_ON)
            ? 1
            : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockFurnaceDouble.IS_ON);
    }
}
