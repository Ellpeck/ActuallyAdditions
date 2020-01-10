/*
 * This file ("TheColoredLampColors.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.block;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.Tags;


public enum LampColor {

    WHITE(Tags.Items.DYES_WHITE, "white_lamp"),
    ORANGE(Tags.Items.DYES_ORANGE, "orange_lamp"),
    MAGENTA(Tags.Items.DYES_MAGENTA, "magenta_lamp"),
    LIGHT_BLUE(Tags.Items.DYES_LIGHT_BLUE, "light_blue_lamp"),
    YELLOW(Tags.Items.DYES_YELLOW, "yellow_lamp"),
    LIME(Tags.Items.DYES_LIME, "lime_lamp"),
    PINK(Tags.Items.DYES_PINK, "pink_lamp"),
    GRAY(Tags.Items.DYES_GRAY, "gray_lamp"),
    LIGHT_GRAY(Tags.Items.DYES_LIGHT_GRAY, "light_gray_lamp"),
    CYAN(Tags.Items.DYES_CYAN, "cyan_lamp"),
    PURPLE(Tags.Items.DYES_PURPLE, "purple_lamp"),
    BLUE(Tags.Items.DYES_BLUE, "blue_lamp"),
    BROWN(Tags.Items.DYES_BROWN, "brown_lamp"),
    GREEN(Tags.Items.DYES_GREEN, "green_lamp"),
    RED(Tags.Items.DYES_RED, "red_lamp"),
    BLACK(Tags.Items.DYES_BLACK, "black_lamp");

    private Tag<Item> tag;
    private String registryName;

    LampColor(Tag<Item> tag, String registryName) {
        this.tag = tag;
        this.registryName = registryName;
    }


    @Nullable
    public static LampColor getColorFromStack(ItemStack stack) {
        for (LampColor c : LampColor.values()) {
            if (c.tag.contains(stack.getItem()))
                return c;
        }
        return null;
    }


    public Tag<Item> getTag() {
        return this.tag;
    }


    public String getRegistryName() {
        return this.registryName;
    }

}