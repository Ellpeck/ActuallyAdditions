/*
 * This file ("LensMining.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.crafting.MiningLensRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LensMining extends Lens {
    @Override
    public boolean invoke(BlockState hitState, BlockPos hitPos, IAtomicReconstructor tile) {
        final int energyUse = CommonConfig.Machines.MINER_LENS_ENERGY.get();
        if (!tile.getWorldObject().isEmptyBlock(hitPos)) {
            if (tile.getEnergy() >= energyUse) {
                int adaptedUse = energyUse;

                List<MiningLensRecipe> ores = new ArrayList<>();

                Block hitBlock = hitState.getBlock();
                ItemStack item = new ItemStack(hitBlock.asItem());

                for(RecipeHolder<MiningLensRecipe> r:ActuallyAdditionsAPI.MINING_LENS_RECIPES) {
                    if (r.value().matches(item))
                        ores.add(r.value());
                }


/*                if (hitBlock.is(Tags.Blocks.STONE)) { //TODO maybe?
                    ores = ActuallyAdditionsAPI.STONE_ORES;
                } else if (hitBlock instanceof NetherrackBlock) {
                    ores = ActuallyAdditionsAPI.NETHERRACK_ORES;
                    adaptedUse += 10000; //TODO uh oh!?
                }*/

                if (!ores.isEmpty()) {
                    int totalWeight = WeightedRandom.getTotalWeight(ores);
                    Optional<MiningLensRecipe> ore = WeightedRandom.getRandomItem(tile.getWorldObject().random, ores);

                    ItemStack stack = ore.map(recipe -> recipe.getResultItem(tile.getWorldObject().registryAccess()).copy()).orElse(ItemStack.EMPTY);

                    if (!stack.isEmpty() && stack.getItem() instanceof BlockItem) {
                        Block toPlace = Block.byItem(stack.getItem());
                        BlockState state2Place = toPlace.defaultBlockState();
                        tile.getWorldObject().setBlock(hitPos, state2Place, 2);
                        tile.getWorldObject().levelEvent(2001, hitPos, Block.getId(state2Place));
                        tile.extractEnergy(adaptedUse);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getColor() {
        return 0x4C4C4C;
    }

    @Override
    public int getDistance() {
        return 10;
    }
}
