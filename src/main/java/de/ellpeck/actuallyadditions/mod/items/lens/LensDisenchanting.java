/*
 * This file ("LensDisenchanting.java") is part of the Actually Additions mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LensDisenchanting extends Lens {

    public static final int ENERGY_USE = 250000;

    @Override
    public boolean invoke(BlockState hitState, BlockPos hitBlock, IAtomicReconstructor tile) {
        if (tile.getEnergy() >= ENERGY_USE) {
            List<ItemEntity> items = tile.getWorldObject().getEntitiesOfClass(ItemEntity.class, new AABB(hitBlock.getX(), hitBlock.getY(), hitBlock.getZ(), hitBlock.getX() + 1, hitBlock.getY() + 1, hitBlock.getZ() + 1));
            if (items != null && !items.isEmpty()) {
                ItemEntity book = null;
                ItemEntity toDisenchant = null;
                for (ItemEntity item : items) {
                    if (item != null && item.isAlive()) {
                        ItemStack stack = item.getItem();
                        if (StackUtil.isValid(stack) && stack.getCount() == 1) {
                            Item stackItem = stack.getItem();
                            if (stackItem == Items.BOOK || stackItem == Items.ENCHANTED_BOOK) {
                                if (book == null) {
                                    book = item;
                                } else {
                                    return false;
                                }
                            } else {
                                ItemEnchantments enchants = stack.getAllEnchantments(tile.getWorldObject().registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
                                if (enchants != null && !enchants.isEmpty()) {
                                    if (toDisenchant == null) {
                                        toDisenchant = item;
                                    } else {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }

                if (book != null && toDisenchant != null) {
                    ItemStack disenchantStack = toDisenchant.getItem();
                    ItemStack bookStack = book.getItem();

                    ItemEnchantments enchants = disenchantStack.getAllEnchantments(tile.getWorldObject().registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
                    if (!enchants.isEmpty()) {
                        Holder<Enchantment> enchant = enchants.keySet().iterator().next();
                        int level = enchants.getLevel(enchant);

                        ItemStack newDisenchantStack = disenchantStack.copy();
                        ItemStack newBookStack;
                        if (bookStack.getItem() == Items.BOOK) {
                            newBookStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchant, level));
                        } else {
                            newBookStack = bookStack.copy();
                        }

                        ItemUtil.removeEnchantment(newDisenchantStack, enchant, tile.getWorldObject().registryAccess());

                        ItemEntity disenchanted = new ItemEntity(toDisenchant.getCommandSenderWorld(), toDisenchant.getX(), toDisenchant.getY(), toDisenchant.getZ(), newDisenchantStack);
                        ItemEntity newBook = new ItemEntity(book.getCommandSenderWorld(), book.getX(), book.getY(), book.getZ(), newBookStack);
                        toDisenchant.discard();
                        book.discard();
                        tile.getWorldObject().addFreshEntity(newBook);
                        tile.getWorldObject().addFreshEntity(disenchanted);

                        tile.extractEnergy(ENERGY_USE);

                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int getColor() {
        return 0xEBADFF;
    }

    @Override
    public int getDistance() {
        return 5;
    }

    @Override
    public boolean canInvoke(IAtomicReconstructor tile, Direction sideToShootTo, int energyUsePerShot) {
        return tile.getEnergy() - energyUsePerShot >= ENERGY_USE;
    }
}
