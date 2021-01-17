package de.ellpeck.actuallyadditions.common.blocks.functional;

import de.ellpeck.actuallyadditions.common.blocks.BlockShapes;
import de.ellpeck.actuallyadditions.common.blocks.FunctionalBlock;
import de.ellpeck.actuallyadditions.common.items.useables.BatteryItem;
import de.ellpeck.actuallyadditions.common.tiles.BatteryBoxTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BatteryBoxBlock extends FunctionalBlock {
    public BatteryBoxBlock() {
        super(Properties.create(Material.ROCK), BatteryBoxTile::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BlockShapes.BATTERYBOX_SHAPE;
    }

    @Override
    public ActionResultType onRightClick(ActivatedContext context) {
        ItemStack held = context.getHeldItem();
        ItemStack invItem = context.getInv().map(e -> e.getStackInSlot(0)).orElse(ItemStack.EMPTY);

        if (!held.isEmpty()) {
            // Place the battery onto the battery box
            if (held.getItem() instanceof BatteryItem && invItem.isEmpty()) {
                ItemStack inserted = context.getInv().map(e -> e.insertItem(0, held.copy(), false)).orElse(held);

                // Only remove from the player if the insert was successful.
                if (inserted.isEmpty()) {
                    context.getPlayer().setHeldItem(context.getHand(), ItemStack.EMPTY);
                }
            }
        } else {
            if (!invItem.isEmpty()) {
                ItemStack removed = context.getInv().map(e -> e.extractItem(0, 1, false)).orElse(ItemStack.EMPTY);

                // Only remove from the player if the insert was successful.
                if (!removed.isEmpty()) {
                    context.getPlayer().setHeldItem(context.getHand(), removed);
                }
            }
        }

        return ActionResultType.SUCCESS;
    }
}
