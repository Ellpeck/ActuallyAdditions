/*
 * This file ("BlockWildPlant.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBushBase;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockWildPlant extends BlockBushBase {

    //    public static final TheWildPlants[] ALL_WILD_PLANTS = TheWildPlants.values();
    //    public static final PropertyEnum<TheWildPlants> TYPE = PropertyEnum.create("type", TheWildPlants.class);

    public BlockWildPlant() {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).strength(0, 0));
        //        this.setSoundType(SoundType.PLANT);
    }

    //    TODO: [port] ADD BACK
    //    @Override
    //    public boolean canBlockStay(World world, BlockPos pos, BlockState state) {
    //        BlockPos offset = pos.down();
    //        BlockState offsetState = world.getBlockState(offset);
    //        Block offsetBlock = offsetState.getBlock();
    //        return state.getValue(TYPE) == TheWildPlants.RICE
    //            ? offsetState.getMaterial() == Material.WATER
    //            : offsetBlock.canSustainPlant(offsetState, world, offset, Direction.UP, this);
    //    }
    //
    //    @Override
    //    public ItemStack getPickBlock(BlockState state, RayTraceResult target, World world, BlockPos pos, PlayerEntity player) {
    //        BlockPlant normal = (BlockPlant) state.getValue(TYPE).getNormalVersion();
    //        return new ItemStack(normal.seedItem);
    //    }
    //
    //    @Override
    //    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
    //        for (int j = 0; j < ALL_WILD_PLANTS.length; j++) {
    //            list.add(new ItemStack(this, 1, j));
    //        }
    //    }
    //
    //    @Override
    //    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, BlockState state, int fortune) {
    //        Block normal = state.getValue(TYPE).getNormalVersion();
    //        normal.getDrops(drops, world, pos, normal.getDefaultState().withProperty(BlockCrops.AGE, 7), fortune);
    //    }
    //
    //    @Override
    //    public boolean canSilkHarvest(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    //        return false;
    //    }
    //
    //    @Override
    //    protected ItemBlockBase getItemBlock() {
    //        return new TheItemBlock(this);
    //    }
    //
    //    @Override
    //    public boolean shouldAddCreative() {
    //        return false;
    //    }
    //
    //    @Override
    //    public void registerRendering() {
    //        for (int i = 0; i < ALL_WILD_PLANTS.length; i++) {
    //            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName() + "=" + ALL_WILD_PLANTS[i].getName());
    //        }
    //    }
    //
    //    @Override
    //    public BlockState getStateFromMeta(int meta) {
    //        return this.getDefaultState().withProperty(TYPE, TheWildPlants.values()[meta]);
    //    }
    //
    //    @Override
    //    public int getMetaFromState(BlockState state) {
    //        return state.getValue(TYPE).ordinal();
    //    }
    //
    //    @Override
    //    protected BlockStateContainer createBlockState() {
    //        return new BlockStateContainer(this, TYPE);
    //    }
    //
    //    @Override
    //    public EnumRarity getRarity(ItemStack stack) {
    //        return stack.getItemDamage() >= ALL_WILD_PLANTS.length
    //            ? EnumRarity.COMMON
    //            : ALL_WILD_PLANTS[stack.getItemDamage()].getRarity();
    //    }
    //
    //    public static class TheItemBlock extends ItemBlockBase {
    //
    //        public TheItemBlock(Block block) {
    //            super(block);
    //            this.setHasSubtypes(true);
    //            this.setMaxDamage(0);
    //        }
    //
    //        @Override
    //        public String getTranslationKey(ItemStack stack) {
    //            return stack.getItemDamage() >= ALL_WILD_PLANTS.length
    //                ? StringUtil.BUGGED_ITEM_NAME
    //                : this.getTranslationKey() + "_" + ALL_WILD_PLANTS[stack.getItemDamage()].getName();
    //        }
    //    }
}
