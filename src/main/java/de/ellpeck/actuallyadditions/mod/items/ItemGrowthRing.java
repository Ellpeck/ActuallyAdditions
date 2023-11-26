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
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class ItemGrowthRing extends ItemEnergy {

    public ItemGrowthRing() {
        super(1000000, 2000);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!(entity instanceof PlayerEntity) || world.isClientSide || entity.isShiftKeyDown()) {
            return;
        }

        PlayerEntity player = (PlayerEntity) entity;
        ItemStack equipped = player.getMainHandItem();

        int energyUse = 300;
        if (StackUtil.isValid(equipped) && equipped == stack && this.getEnergyStored(stack) >= energyUse) {
            List<BlockPos> blocks = new ArrayList<>();

            //Adding all possible Blocks
            if (player.level.getGameTime() % 30 == 0) {
                int range = 3;
                for (int x = -range; x < range + 1; x++) {
                    for (int z = -range; z < range + 1; z++) {
                        for (int y = -range; y < range + 1; y++) {
                            int theX = MathHelper.floor(player.getX() + x);
                            int theY = MathHelper.floor(player.getY() + y);
                            int theZ = MathHelper.floor(player.getZ() + z);
                            BlockPos posInQuestion = new BlockPos(theX, theY, theZ);
                            Block theBlock = world.getBlockState(posInQuestion).getBlock();
                            if ((theBlock instanceof IGrowable || theBlock instanceof IPlantable) && !(theBlock instanceof GrassBlock)) {
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
                            state.randomTick((ServerWorld) world, pos, world.random);

                            //Show Particles if Metadata changed
                            BlockState newState = world.getBlockState(pos);
                            if (newState != state) {
                                world.levelEvent(2005, pos, 0);
                            }

                            if (!player.isCreative()) {
                                this.extractEnergyInternal(stack, energyUse, false);
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
