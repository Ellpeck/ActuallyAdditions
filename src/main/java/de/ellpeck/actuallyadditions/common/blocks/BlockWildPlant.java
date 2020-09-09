package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockBushBase;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockPlant;
import de.ellpeck.actuallyadditions.common.blocks.metalists.TheWildPlants;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWildPlant extends BlockBushBase {

    public static final TheWildPlants[] ALL_WILD_PLANTS = TheWildPlants.values();
    public static final PropertyEnum<TheWildPlants> TYPE = PropertyEnum.create("type", TheWildPlants.class);

    public BlockWildPlant() {
        super(Properties.create(Material.PLANTS));
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        BlockPos offset = pos.down();
        IBlockState offsetState = world.getBlockState(offset);
        Block offsetBlock = offsetState.getBlock();
        return state.getValue(TYPE) == TheWildPlants.RICE ? offsetState.getMaterial() == Material.WATER : offsetBlock.canSustainPlant(offsetState, world, offset, EnumFacing.UP, this);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        BlockPlant normal = (BlockPlant) state.getValue(TYPE).getNormalVersion();
        return new ItemStack(normal.seedItem);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int j = 0; j < ALL_WILD_PLANTS.length; j++) {
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Block normal = state.getValue(TYPE).getNormalVersion();
        normal.getDrops(drops, world, pos, normal.getDefaultState().withProperty(BlockCrops.AGE, 7), fortune);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    protected BlockItemBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public boolean shouldAddCreative() {
        return false;
    }

    @Override
    public void registerRendering() {
        for (int i = 0; i < ALL_WILD_PLANTS.length; i++) {
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName() + "=" + ALL_WILD_PLANTS[i].getName());
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, TheWildPlants.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_WILD_PLANTS.length ? EnumRarity.COMMON : ALL_WILD_PLANTS[stack.getItemDamage()].getRarity();
    }

    public static class TheItemBlock extends BlockItem {

        public TheItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return stack.getItemDamage() >= ALL_WILD_PLANTS.length ? StringUtil.BUGGED_ITEM_NAME : this.getTranslationKey() + "_" + ALL_WILD_PLANTS[stack.getItemDamage()].getName();
        }
    }
}
