/*
 * This file ("ItemGrowthRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ItemGrowthRing extends ItemEnergy {

    public ItemGrowthRing() {
        super(1000000, 2000);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!(entity instanceof Player player) || world.isClientSide || entity.isShiftKeyDown()) {
            return;
        }

	    ItemStack equipped = player.getMainHandItem();

        int energyUse = 300;
        if (!equipped.isEmpty() && equipped == stack && this.getEnergyStored(stack) >= energyUse) {
            List<BlockPos> blocks = new ArrayList<>();

            //Adding all possible Blocks
            if (player.level().getGameTime() % 30 == 0) {
                int range = 3;
                for (int x = -range; x < range + 1; x++) {
                    for (int z = -range; z < range + 1; z++) {
                        for (int y = -range; y < range + 1; y++) {
                            int theX = Mth.floor(player.getX() + x);
                            int theY = Mth.floor(player.getY() + y);
                            int theZ = Mth.floor(player.getZ() + z);
                            BlockPos posInQuestion = new BlockPos(theX, theY, theZ);
                            Block theBlock = world.getBlockState(posInQuestion).getBlock();
                            if ((theBlock instanceof BonemealableBlock /*|| theBlock instanceof IPlantable*/) && !(theBlock instanceof GrassBlock)) {
                                blocks.add(posInQuestion);
                            }
                        }
                    }
                }

                //Fertilizing the Blocks
                if (!blocks.isEmpty()) {
                    for (int i = 0; i < 45; i++) {
                        if (this.getEnergyStored(stack) >= energyUse) {
                            BlockPos pos = blocks.get(world.random.nextInt(blocks.size()));

                            BlockState state = world.getBlockState(pos);
                            state.randomTick((ServerLevel) world, pos, world.random);

                            //Show Particles if Metadata changed
                            BlockState newState = world.getBlockState(pos);
                            if (newState != state) {
                                world.levelEvent(2005, pos, 0);
                            }

                            if (!player.isCreative()) {
                                this.extractEnergy(stack, energyUse, false);
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }
}
