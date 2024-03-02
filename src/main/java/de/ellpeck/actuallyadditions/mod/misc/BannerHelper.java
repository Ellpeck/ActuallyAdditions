/*
 * This file ("BannerHelper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.world.item.ItemStack;

public final class BannerHelper {

    public static void init() {
        addCraftingPattern("drill", new ItemStack(ActuallyItems.DRILL_MAIN.get()));
        addCraftingPattern("leaf_blo", new ItemStack(ActuallyItems.LEAF_BLOWER.get()));
        addCraftingPattern("phan_con", new ItemStack(ActuallyItems.PHANTOM_CONNECTOR.get()));
        addCraftingPattern("book", new ItemStack(ActuallyItems.ITEM_BOOKLET.get()));
    }

    /**
     * (Excerpted from Additional Banners by Darkhax with permission, thanks!)
     * <p>
     * Adds a new banner pattern to the game. This banner pattern will be applied by using the
     * provided item in a crafting recipe with the banner.
     *
     * @param name          The name of the banner pattern. This is used for the texture file, and is
     *                      also converted into upper case and used for the enum entry. Given how this
     *                      system works, it's critical that this value is unique, consider adding the
     *                      mod id to the name.
     *                      //@param id            A small string used to represent the pattern without taking up much space. An
     *                      example of this is "bri". Given how the system works, it is critical that
     *                      this is a unique value. please consider adding the mod id to the pattern id.
     * @param craftingStack An ItemStack which is used in the crafting recipe for this pattern.
     *                      An example of this would be the creeper skull being used for the creeper
     *                      pattern.
     */
    public static void addCraftingPattern(String name, ItemStack craftingStack) {
        Class<?>[] paramTypes = {String.class, String.class, ItemStack.class};
        Object[] paramValues = {ActuallyAdditions.MODID + "_" + name, ActuallyAdditions.MODID + "_" + name, craftingStack};
        // EnumHelper.addEnum(BannerPattern.class, (ActuallyAdditions.MODID + "_" + name).toUpperCase(Locale.ROOT), paramTypes, paramValues); //TODO wth banners
    }

}
