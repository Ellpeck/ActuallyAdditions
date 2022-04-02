package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class CanolaSeed extends ItemBase {
    public boolean empowered;
    public CanolaSeed(boolean empowered) {
        this.empowered = empowered;
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return empowered;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level.isClientSide) {
            if (stack != null) {
                BlockPos pos = entity.blockPosition();
                BlockState state = entity.level.getBlockState(pos);
                Block block = state.getBlock();

                if (block instanceof FlowingFluidBlock && state.getFluidState().isSource()) {
                    Fluid fluid = ((FlowingFluidBlock) block).getFluid();
                    if (fluid != null && fluid == (empowered
                        ? InitFluids.CRYSTALLIZED_OIL.get()
                        : InitFluids.REFINED_CANOLA_OIL.get())) {
                        entity.kill();
                        entity.level.setBlockAndUpdate(pos, (empowered
                            ? InitFluids.EMPOWERED_OIL.getBlock()
                            : InitFluids.CRYSTALLIZED_OIL.getBlock()).defaultBlockState());
                    }
                }
            }
        }

        return super.onEntityItemUpdate(stack, entity);
    }
}
