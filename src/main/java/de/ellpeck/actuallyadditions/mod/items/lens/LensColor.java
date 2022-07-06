/*
 * This file ("LensColor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.IColorLensChanger;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class LensColor extends Lens {

    //1k to fire, 200 per item
    public static final int ENERGY_USE = 200;
    //Thanks to xdjackiexd for this, as I couldn't be bothered
    public static final int[] POSSIBLE_COLORS = new int[]{
        0x9E2B27, //Red
        0xEA7E35, //Orange
        0xC2B41C, //Yellow
        0x39BA2E, //Lime Green
        0x364B18, //Green
        0x6387D2, //Light Blue
        0x267191, //Cyan
        0x253293, //Blue
        0x7E34BF, //Purple
        0xBE49C9, //Magenta
        0xD98199, //Pink
        0x56331C, //Brown
    };
    private final Random rand = new Random();

    @Override
    public boolean invoke(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (hitBlock != null) {
            if (tile.getEnergy() >= ENERGY_USE) {
                BlockState state = tile.getWorldObject().getBlockState(hitBlock);
                Block block = state.getBlock();
                //                int meta = block.getMetaFromState(state);
                ItemStack returnStack = this.tryConvert(new ItemStack(block), hitState, hitBlock, tile);
                if (returnStack != null && returnStack.getItem() instanceof BlockItem) {
                    Block toPlace = Block.byItem(returnStack.getItem());
                    BlockState state2Place = toPlace.defaultBlockState();
                    //getStateForPlacement(tile.getWorldObject(), hitBlock, Direction.UP, 0, 0, 0, FakePlayerFactory.getMinecraft((ServerWorld) tile.getWorldObject()), Hand.MAIN_HAND); //TODO
                    tile.getWorldObject().setBlock(hitBlock, state2Place, 2);
                    tile.extractEnergy(ENERGY_USE);
                }
            }

            List<ItemEntity> items = tile.getWorldObject().getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX() + 1, hitBlock.getY() + 1, hitBlock.getZ() + 1));
            for (ItemEntity item : items) {
                if (item.isAlive() && StackUtil.isValid(item.getItem()) && tile.getEnergy() >= ENERGY_USE) {
                    ItemStack newStack = this.tryConvert(item.getItem(), hitState, hitBlock, tile);
                    if (StackUtil.isValid(newStack)) {
                        item.remove();

                        ItemEntity newItem = new ItemEntity(tile.getWorldObject(), item.getX(), item.getY(), item.getZ(), newStack);
                        tile.getWorldObject().addFreshEntity(newItem);

                        tile.extractEnergy(ENERGY_USE);
                    }
                }
            }
        }
        return false;
    }

    private ItemStack tryConvert(ItemStack stack, BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (StackUtil.isValid(stack)) {
            Item item = stack.getItem();
            for (Map.Entry<Item, IColorLensChanger> changer : ActuallyAdditionsAPI.RECONSTRUCTOR_LENS_COLOR_CHANGERS.entrySet()) {
                if (item == changer.getKey()) {
                    return changer.getValue().modifyItem(stack, hitState, hitBlock, tile);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getColor() {
        return POSSIBLE_COLORS[this.rand.nextInt(POSSIBLE_COLORS.length)];
    }

    @Override
    public int getDistance() {
        return 10;
    }

    @Override
    public boolean canInvoke(IAtomicReconstructor tile, Direction sideToShootTo, int energyUsePerShot) {
        return tile.getEnergy() - energyUsePerShot >= ENERGY_USE;
    }
}
