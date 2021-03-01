/*
 * This file ("LensDisruption.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.lens;

import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Random;


public class LensDisruption extends Lens {

    private static final int ENERGY_USE = 150000;

    @Override
    public boolean invoke(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (ConfigIntValues.ELEVEN.getValue() == 11 && tile.getEnergy() >= ENERGY_USE && hitBlock != null && !hitState.getBlock().isAir(hitState, tile.getWorldObject(), hitBlock)) {
            int range = 2;
            ArrayList<ItemEntity> items = (ArrayList<ItemEntity>) tile.getWorldObject().getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(hitBlock.getX() - range, hitBlock.getY() - range, hitBlock.getZ() - range, hitBlock.getX() + range, hitBlock.getY() + range, hitBlock.getZ() + range));
            for (ItemEntity item : items) {
                ItemStack stack = item.getItem();
                if (item.isAlive() && StackUtil.isValid(stack)) {
                    if (!stack.hasTag() || !stack.getOrCreateTag().getBoolean(ActuallyAdditions.MODID + "DisruptedAlready")) {

                        ItemStack newStack;
                        do {
                            if (tile.getWorldObject().rand.nextBoolean()) {
                                newStack = this.getRandomItemFromRegistry(tile.getWorldObject().rand);//new ItemStack(Item.REGISTRY.getRandomObject(tile.getWorldObject().rand));
                            } else {
                                newStack = this.getRandomBlockFromRegistry(tile.getWorldObject().rand);//new ItemStack(Block.REGISTRY.getRandomObject(tile.getWorldObject().rand));
                            }
                        } while (!StackUtil.isValid(newStack));

                        newStack.setCount(stack.getCount());
                        newStack.getOrCreateTag().putBoolean(ActuallyAdditions.MODID + "DisruptedAlready", true);

                        item.remove();

                        ItemEntity newItem = new ItemEntity(tile.getWorldObject(), item.getPosX(), item.getPosY(), item.getPosZ(), newStack);
                        tile.getWorldObject().addEntity(newItem);

                        tile.extractEnergy(ENERGY_USE);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public float[] getColor() {
        return new float[]{246F / 255F, 255F / 255F, 183F / 255F};
    }

    @Override
    public int getDistance() {
        return 3;
    }

    @Override
    public boolean canInvoke(IAtomicReconstructor tile, Direction sideToShootTo, int energyUsePerShot) {
        return tile.getEnergy() - energyUsePerShot >= ENERGY_USE;
    }

    private ItemStack getRandomItemFromRegistry(Random random) {
        return new ItemStack((Item) ForgeRegistries.ITEMS.getValues().toArray()[random.nextInt(ForgeRegistries.ITEMS.getValues().size())]);
    }

    private ItemStack getRandomBlockFromRegistry(Random random) {
        return new ItemStack((Block) ForgeRegistries.BLOCKS.getValues().toArray()[random.nextInt(ForgeRegistries.BLOCKS.getValues().size())]);
    }
}
