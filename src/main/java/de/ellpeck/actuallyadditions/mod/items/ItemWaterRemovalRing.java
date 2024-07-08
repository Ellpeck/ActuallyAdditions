/*
 * This file ("ItemWaterRemovalRing.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ItemWaterRemovalRing extends ItemEnergy {

    public ItemWaterRemovalRing() {
        super(800000, 1000);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity player, int itemSlot, boolean isSelected) {
        if (!(player instanceof Player) || player.level().isClientSide || player.isShiftKeyDown()) {
            return;
        }

        ItemStack equipped = ((Player) player).getMainHandItem();

        int energyUse = 150;
        if (StackUtil.isValid(equipped) && equipped == stack && this.getEnergyStored(stack) >= energyUse) {

            //Setting everything to air
            int range = 3;
            for (int x = -range; x < range + 1; x++) {
                for (int z = -range; z < range + 1; z++) {
                    for (int y = -range; y < range + 1; y++) {
                        int theX = Mth.floor(player.getX() + x);
                        int theY = Mth.floor(player.getY() + y);
                        int theZ = Mth.floor(player.getZ() + z);

                        //Remove Water
                        BlockPos pos = new BlockPos(theX, theY, theZ);
                        Block block = world.getBlockState(pos).getBlock();
                        // TODO: Ensure water check is correct
                        if ((block == Blocks.WATER) && this.getEnergyStored(stack) >= energyUse) {
                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                            if (!((Player) player).isCreative()) {
                                this.extractEnergy(stack, energyUse, false);
                            }
                        }
                        //Remove Lava
                        // TODO: Ensure lava check is correct
                        else if ((block == Blocks.LAVA) && this.getEnergyStored(stack) >= energyUse * 2) {
                            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                            if (!((Player) player).isCreative()) {
                                this.extractEnergy(stack, energyUse * 2, false);
                            }
                        }
                    }
                }
            }
        }
    }
}
