package de.ellpeck.actuallyadditions.common.misc.apiimpl.farmer.exu;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ExUPlantFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 600;
        if (farmer.getEnergy() >= use) {
            if (StackUtil.isValid(seed)) {
                Item item = seed.getItem();
                ResourceLocation reg = item.getRegistryName();

                if (reg != null && this.getPlantName().equals(reg.toString())) {
                    if (item instanceof ItemBlock) {
                        Block block = ((ItemBlock) item).getBlock();
                        if (block != null) {
                            IBlockState stateThere = world.getBlockState(pos);
                            Block blockThere = stateThere.getBlock();

                            if (world.isAirBlock(pos) || blockThere.isReplaceable(world, pos)) {
                                BlockPos posBelow = pos.down();
                                IBlockState stateBelow = world.getBlockState(posBelow);

                                if (this.canPlaceOn(stateBelow.getBlock())) {
                                    world.setBlockState(pos, block.getDefaultState(), 2);
                                    farmer.extractEnergy(use);

                                    return FarmerResult.SUCCESS;
                                }
                            }

                            return FarmerResult.STOP_PROCESSING;
                        }
                    }
                }
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
        int use = 600;
        if (farmer.getEnergy() >= use) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            ResourceLocation reg = block.getRegistryName();

            if (reg != null && this.getPlantName().equals(reg.toString())) {
                if (block.getMetaFromState(state) >= this.getMaxStage()) {
                    NonNullList<ItemStack> drops = NonNullList.create();
                    block.getDrops(drops, world, pos, state, 0);
                    if (StackUtil.isEmpty(drops)) return FarmerResult.FAIL;
                    for (ItemStack stack : drops) {
                        if (StackUtil.isValid(stack)) {
                            ResourceLocation itemReg = stack.getItem().getRegistryName();
                            if (itemReg != null && this.getPlantName().equals(itemReg.toString())) {
                                if (stack.getCount() <= 1) {
                                    drops.remove(stack);
                                    break;
                                } else {
                                    stack.shrink(1);
                                }
                            }
                        }
                    }

                    if (farmer.canAddToOutput(drops)) {
                        farmer.addToOutput(drops);

                        world.playEvent(2001, pos, Block.getStateId(state));
                        world.setBlockState(pos, block.getDefaultState(), 2);

                        farmer.extractEnergy(use);

                        return FarmerResult.SUCCESS;
                    }
                }

                return FarmerResult.STOP_PROCESSING;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public int getPriority() {
        return 10;
    }

    protected abstract String getPlantName();

    protected abstract boolean canPlaceOn(Block block);

    protected abstract int getMaxStage();
}
