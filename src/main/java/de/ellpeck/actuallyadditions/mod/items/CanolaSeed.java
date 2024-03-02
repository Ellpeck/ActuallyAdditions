package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

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

                if (block instanceof LiquidBlock && state.getFluidState().isSource()) {
                    Fluid fluid = ((LiquidBlock) block).getFluid();
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
