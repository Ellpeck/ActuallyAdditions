package de.ellpeck.actuallyadditions.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;

@Deprecated
public class BlockFluidFlowing extends FlowingFluidBlock {

//    private final String name;

    public BlockFluidFlowing(FlowingFluid fluid, Block.Properties builder) {
        super(fluid, builder);
//        this.name = unlocalizedName;
// todo: check this
//        this.displacements.put(this, false);

//        this.register();
    }

//    private void register() {
//        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());
//    }

//    protected String getBaseName() {
//        return this.name;
//    }

//    protected ItemBlockBase getItemBlock() {
//        return new ItemBlockBase(this);
//    }

    public boolean shouldAddCreative() {
        return false;
    }

// todo: reeval canDisplay and displaceIfPossible
//    @Override
//    public boolean canDisplace(IBlockAccess world, BlockPos pos) {
//        return !world.getBlockState(pos).getMaterial().isLiquid() && super.canDisplace(world, pos);
//    }
//
//    @Override
//    public boolean displaceIfPossible(World world, BlockPos pos) {
//        return !world.getBlockState(pos).getMaterial().isLiquid() && super.displaceIfPossible(world, pos);
//    }
////
//    @Override
//    public EnumRarity getRarity(ItemStack stack) {
//        return EnumRarity.EPIC;
//    }
}
