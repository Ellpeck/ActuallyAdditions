package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.CrateBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;

public class Crate extends BlockContainerBase {
    private final Size size;
    public Crate(Size size) {
        super(ActuallyBlocks.defaultPickProps().pushReaction(PushReaction.BLOCK));
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public enum Size {
        SMALL(9, 4),
        MEDIUM(18, 8),
        LARGE(27, 16); //TODO Maybe?

        Size(int slotCount, int stackBoost) {
            this.slots = slotCount;
            this.stackBoost = stackBoost;
        }
        private final int slots;
        private final int stackBoost;

        public int getSlots() {
            return slots;
        }
        public int getStackBoost() {
            return stackBoost;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrateBE(pPos, pState, this.size);
    }
}
