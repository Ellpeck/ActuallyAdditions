/*
 * This file ("MelonPumpkinFarmerBehavior.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.apiimpl.farmer;

import de.ellpeck.actuallyadditions.api.farmer.FarmerResult;
import de.ellpeck.actuallyadditions.api.farmer.IFarmerBehavior;
import de.ellpeck.actuallyadditions.api.internal.IFarmer;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MelonPumpkinFarmerBehavior implements IFarmerBehavior {

    @Override
    public FarmerResult tryPlantSeed(ItemStack seed, Level world, BlockPos pos, IFarmer farmer) {
        int use = 350;
        if (farmer.getEnergy() >= use * 2) {
            if (StackUtil.isValid(seed)) {
                Item seedItem = seed.getItem();
                boolean isPumpkin = seedItem == Items.PUMPKIN_SEEDS;
                if (isPumpkin || seedItem == Items.MELON_SEEDS) {
                    if (pos.getX() % 2 == 0 == (pos.getZ() % 2 == 0)) {
                        BlockState toPlant = (isPumpkin
                            ? Blocks.PUMPKIN_STEM
                            : Blocks.MELON_STEM).defaultBlockState();
                        if (DefaultFarmerBehavior.defaultPlant(world, pos, toPlant, farmer, use)) {
                            return FarmerResult.SUCCESS;
                        }
                    }
                    return FarmerResult.STOP_PROCESSING;
                }
                return FarmerResult.FAIL;
            }
        }
        return FarmerResult.FAIL;
    }

    @Override
    public FarmerResult tryHarvestPlant(ServerLevel world, BlockPos pos, IFarmer farmer) {
        int use = 500;
        if (farmer.getEnergy() >= use) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == Blocks.PUMPKIN || block == Blocks.MELON) {
                List<ItemStack> drops = state.getDrops(new LootParams.Builder(world)
                    .withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY));
                if (!drops.isEmpty()) {
                    if (farmer.canAddToOutput(drops)) {
                        world.levelEvent(2001, pos, Block.getId(state));
                        world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

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
