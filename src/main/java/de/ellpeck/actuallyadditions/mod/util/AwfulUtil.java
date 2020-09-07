package de.ellpeck.actuallyadditions.mod.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

//This is stuff copied from somewhere in vanilla and changed so that it works properly
//It's unpolished and vanilla-y, so don't look at it! O_O
public final class AwfulUtil {

    public static void fillInventory(LootTable table, IItemHandlerModifiable inventory, Random rand, LootContext context) {
        List<ItemStack> list = table.generateLootForPools(rand, context);
        List<Integer> list1 = getEmptySlotsRandomized(inventory, rand);
        shuffleItems(list, list1.size(), rand);

        for (ItemStack itemstack : list) {
            if (itemstack.isEmpty()) {
                inventory.setStackInSlot(list1.remove(list1.size() - 1), ItemStack.EMPTY);
            } else {
                inventory.setStackInSlot(list1.remove(list1.size() - 1), itemstack);
            }
        }
    }

    private static void shuffleItems(List<ItemStack> stacks, int someInt, Random rand) {
        List<ItemStack> list = Lists.newArrayList();
        Iterator<ItemStack> iterator = stacks.iterator();

        while (iterator.hasNext()) {
            ItemStack itemstack = iterator.next();

            if (itemstack.isEmpty()) {
                iterator.remove();
            } else if (itemstack.getCount() > 1) {
                list.add(itemstack);
                iterator.remove();
            }
        }

        someInt = someInt - stacks.size();

        while (someInt > 0 && list.size() > 0) {
            ItemStack itemstack2 = list.remove(MathHelper.getInt(rand, 0, list.size() - 1));
            int i = MathHelper.getInt(rand, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.splitStack(i);

            if (itemstack2.getCount() > 1 && rand.nextBoolean()) {
                list.add(itemstack2);
            } else {
                stacks.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && rand.nextBoolean()) {
                list.add(itemstack1);
            } else {
                stacks.add(itemstack1);
            }
        }

        stacks.addAll(list);
        Collections.shuffle(stacks, rand);
    }

    private static List<Integer> getEmptySlotsRandomized(IItemHandlerModifiable inventory, Random rand) {
        List<Integer> list = Lists.newArrayList();

        for (int i = 0; i < inventory.getSlots(); ++i) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, rand);
        return list;
    }

    public static void callTheFuckinPolice(Object... stuff) {
        int i = 0;
        String error = "Actually Additions: Something is very wrong.  This method was provided with ";
        for (Object k : stuff) {
            error += "\n" + i++ + ": " + (k == null ? "null" : k.getClass().getSimpleName() + " <- CLASS | INSTANCE -> " + k.toString() + ", ");
        }
        error += "\n" + "The current side is: " + FMLCommonHandler.instance().getEffectiveSide();
        error += "\n" + "Report this to https://github.com/Ellpeck/ActuallyAdditions/issues";
        throw new IllegalStateException(error);
    }
}
