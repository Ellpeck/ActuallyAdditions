package de.ellpeck.actuallyadditions.common.misc.apiimpl.farmer;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MelonPumpkinFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, World world, BlockPos pos, IFarmer farmer) {
        int use = 350;
        if (farmer.getEnergy() >= use * 2) {
            if (StackUtil.isValid(seed)) {
                Item seedItem = seed.getItem();
                boolean isPumpkin = seedItem == Items.PUMPKIN_SEEDS;
                if (isPumpkin || seedItem == Items.MELON_SEEDS) {
                    if (pos.getX() % 2 == 0 == (pos.getZ() % 2 == 0)) {
                        IBlockState toPlant = (isPumpkin ? Blocks.PUMPKIN_STEM : Blocks.MELON_STEM).getDefaultState();
                        if (DefaultFarmerBehavior.defaultPlant(world, pos, toPlant, farmer, use)) return FarmerResult.SUCCESS;
                    }
                    return FarmerResult.STOP_PROCESSING;
                }
                return FarmerResult.FAIL;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(World world, BlockPos pos, IFarmer farmer) {
        int use = 500;
        if (farmer.getEnergy() >= use) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.PUMPKIN || block == Blocks.MELON_BLOCK) {
                NonNullList<ItemStack> drops = NonNullList.create();
                block.getDrops(drops, world, pos, state, 0);
                if (!drops.isEmpty()) {
                    if (farmer.canAddToOutput(drops)) {
                        world.playEvent(2001, pos, Block.getStateId(state));
                        world.setBlockToAir(pos);

                        farmer.extractEnergy(use);
                        farmer.addToOutput(drops);

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
}
