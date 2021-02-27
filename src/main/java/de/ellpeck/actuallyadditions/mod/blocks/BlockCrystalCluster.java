/*
 * This file ("BlockCrystalCluster.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.gen.WorldGenLushCaves;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingBlock;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class BlockCrystalCluster extends BlockBase implements IColorProvidingBlock, IColorProvidingItem {

    private final TheCrystals crystal;

    public BlockCrystalCluster(TheCrystals crystal) {
        super(Material.GLASS, name);
        this.crystal = crystal;

        this.setHardness(0.25F);
        this.setResistance(1.0F);
        this.setSoundType(SoundType.GLASS);
        this.setLightOpacity(1);
        this.setLightLevel(0.7F);
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(World world, BlockPos pos, Direction side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase base) {
        return this.getStateFromMeta(side.ordinal());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(BlockDirectional.FACING, Direction.byIndex(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    public BlockState withRotation(BlockState state, Rotation rot) {
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    public BlockState withMirror(BlockState state, Mirror mirror) {
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor() {
        return (state, world, pos, tintIndex) -> BlockCrystalCluster.this.crystal.clusterColor;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        return (stack, tintIndex) -> BlockCrystalCluster.this.crystal.clusterColor;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune) {
        return InitItems.itemCrystalShard;
    }

    @Override
    public int damageDropped(BlockState state) {
        return ArrayUtils.indexOf(WorldGenLushCaves.CRYSTAL_CLUSTERS, this);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
        return new ItemStack(this);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(5) + 2;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        return true;
    }
}
